package com.portea.cpnen.rapi.service;

import com.portea.cpnen.dao.*;
import com.portea.cpnen.domain.*;
import com.portea.cpnen.domain.CouponDiscountRequestStatus;
import com.portea.cpnen.domain.Package;
import com.portea.cpnen.rapi.domain.*;
import com.portea.cpnen.rapi.exception.*;
import com.portea.cpnen.vo.ProductVo;
import com.portea.cpnen.web.rapi.domain.CouponCodeVO;
import com.portea.dao.JpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.InternalServerErrorException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@Stateless
public class CouponEngineRequestProcessorImpl implements CouponEngineRequestProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(CouponEngineRequestProcessorImpl.class);

    @Inject @JpaDao
    CouponCodeDao couponCodeDao;

    @Inject @JpaDao
    CouponDao couponDao;

    @Inject @JpaDao
    UserDao userDao;

    @Inject @JpaDao
    CouponDiscountRequestDao couponDiscountRequestDao;

    @Inject @JpaDao
    CouponDiscountRequestCodeDao couponDiscountRequestCodeDao;

    @Inject @JpaDao
    CouponDiscountReqProdDao couponDiscountReqProdDao;

    @Inject @JpaDao
    CouponDiscountRequestAuditDao couponDiscountRequestAuditDao;

    @Inject @JpaDao
    CouponDiscountReqCodeAuditDao couponDiscountReqCodeAuditDao;

    @Inject @JpaDao
    CouponDiscountReqProdAuditDao couponDiscountReqProdAuditDao;

    @Inject @JpaDao
    CouponDiscountingRuleDao couponDiscountingRuleDao;

    @Inject @JpaDao
    private ProductAdapterDao productAdapterDao;

    @Inject @JpaDao
    private CouponProductAdapterMappingDao couponProductAdapterMappingDao;

    @Inject @JpaDao
    private CouponDiscountCodeDao couponDiscountCodeDao;

    @Inject @JpaDao
    private BrandDao brandDao;

    @Inject @JpaDao
    private AreaDao areaDao;

    @Inject @JpaDao
    private ReferrerDao referrerDao;

    @Inject @JpaDao
    private CouponDiscountReqProdDao couponDiscountRequestProductDao;

    @Inject @JpaDao
    private CouponBrandMappingDao couponBrandMappingDao;

    @Inject @JpaDao
    private CouponAreaMappingDao couponAreaMappingDao;

    @Inject @JpaDao
    private CouponReferrerMappingDao couponReferrerMappingDao;

    @Inject @JpaDao
    private CouponDiscountDao couponDiscountDao;

    @Inject @JpaDao
    private CouponDiscountProductDao couponDiscountProductDao;

    @Inject @JpaDao
    private ServiceDao serviceDao;

    @Inject @JpaDao
    private PackageDao packageDao;


    @Override
    public CouponDiscountRequestResponse createCouponDiscountRequest(CouponDiscountRequestCreateReq request) {

        validateCdrRequest(request);

        Integer requesterId = request.getRequesterId();

        Integer beneficiaryId = (request.getBeneficiaryId() == null)? requesterId : request.getBeneficiaryId();

        List<SelectedProduct> selectedProducts = request.getProducts();

        String[] codes = request.getCodes();

        Double totalCost = request.getTotalCost();

        ContextType contextType = request.getContextType();

        Map<Coupon, List<String>> couponCodeMap = getCouponCodeMap(codes);

        List<ProductVo> productVos =  getProductVosForSelectedProducts(selectedProducts);

        String sourceName = request.getSourceName();

        Integer patientBrandId = request.getPatientBrandId();

        Integer areaId = request.getAreaId();

        Integer referrerId = request.getReferrerId();

        Boolean withinSubscription = (request.getWithinSubscription() == null) ? false : request.getWithinSubscription();

        initialValidation(requesterId, beneficiaryId);

        validateCDRequest(requesterId, contextType, beneficiaryId, couponCodeMap, totalCost, productVos, withinSubscription, codes, areaId, referrerId, patientBrandId);

        LOG.debug("Request validated");

        CouponDiscountRequest couponDiscountRequest = createCouponRequest(requesterId, beneficiaryId, contextType,
                codes, productVos, totalCost, sourceName, withinSubscription, areaId, referrerId, patientBrandId);
        LOG.debug("Coupon discount request created");

        createCouponRequestAudit(couponDiscountRequest);
        LOG.debug("Coupon discount request audit created");

        ApplicableDiscountResp applicableDiscountResp = getCurrentApplicableDiscount(couponDiscountRequest.getId());


        CouponDiscountRequestResponse couponDiscountRequestResponse = new CouponDiscountRequestResponse();

        couponDiscountRequestResponse.setCdrId(couponDiscountRequest.getId());
        couponDiscountRequestResponse.setDiscount(applicableDiscountResp.getDiscountAmount());
        return couponDiscountRequestResponse;
    }

    private void validateCdrRequest(CouponDiscountRequestCreateReq request) {

        checkForNullValues(request);

        validateRequestValues(request);
    }

    private void validateRequestValues(CouponDiscountRequestCreateReq request) {
        if( request.getTotalCost() < 0 ) {
            throw new InvalidRequestException("totalCost", String.valueOf(request.getTotalCost()));
        }

        request.getProducts().forEach(product -> {
            if (product.getPurchaseCount() < -1) {
                throw new InvalidRequestException("products.purchaseCount", String.valueOf(product.getPurchaseCount()));
            }

            if (product.getCount() < 0) {
                throw new InvalidRequestException("products.count", String.valueOf(product.getCount()));
            }

            if (product.getUnitCost() < 0) {
                throw new InvalidRequestException("products.unitCost", String.valueOf(product.getUnitCost()));
            }
        });
    }

    private void checkForNullValues(CouponDiscountRequestCreateReq request) {
        String nullField = request.inspectNullParameters();

        if (nullField != null) {
            throw new IncompleteRequestException(nullField);
        }

        if (request.getCodes().length == 0) {
            throw new IncompleteRequestException("codes");
        }

        if (request.getProducts().size() == 0) {
            throw new IncompleteRequestException("products");
        }

        for (String code : request.getCodes()) {
            if (code == null) {
                throw new IncompleteRequestException("code");
            }
        }

        for (SelectedProduct selectedProduct : request.getProducts()) {

            if (selectedProduct == null) {
                throw new IncompleteRequestException("product");
            }

            nullField = selectedProduct.inspectNullParameters();
            if (nullField != null) {
                throw new IncompleteRequestException(nullField);
            }
        }
    }

    private void checkForNullValues(CouponDiscountRequestUpdateReq request) {
        String nullField = request.inspectNullParameters();

        if (nullField != null) {
            throw new IncompleteRequestException(nullField);
        }

        if (request.getCodes().length == 0) {
            throw new IncompleteRequestException("codes");
        }

        if (request.getProducts().size() == 0) {
            throw new IncompleteRequestException("products");
        }

        for (String code : request.getCodes()) {
            if (code == null) {
                throw new IncompleteRequestException("code");
            }
        }

        for (SelectedProduct selectedProduct : request.getProducts()) {

            if (selectedProduct == null) {
                throw new IncompleteRequestException("product");
            }

            nullField = selectedProduct.inspectNullParameters();
            if (nullField != null) {
                throw new IncompleteRequestException(nullField);
            }
        }
    }

    private void checkForNullValues(ProductUpdateReq request) {
        String nullField = request.inspectNullParameters();

        if (nullField != null) {
            throw new IncompleteRequestException(nullField);
        }

        if (request.getProducts().size() == 0) {
            throw new IncompleteRequestException("products");
        }

        for (SelectedProduct selectedProduct : request.getProducts()) {

            if (selectedProduct == null) {
                throw new IncompleteRequestException("product");
            }

            nullField = selectedProduct.inspectNullParameters();
            if (nullField != null) {
                throw new IncompleteRequestException(nullField);
            }
        }
    }


    private void initialValidation(Integer requesterId, Integer beneficiaryId) {

        validateUser(requesterId);

        if ( ! Objects.equals(beneficiaryId, requesterId)) {

            validateUser(beneficiaryId);
        }
    }

    private void validateCDRequest(Integer requesterId, ContextType contextType, Integer beneficiaryId,
                                   Map<Coupon, List<String>> couponCodeMap, Double totalCost,
                                   List<ProductVo> productVos, Boolean withinSubscription,String[] codes, Integer areaId, Integer referrerId, Integer patientBrandId) {

        List<ProductAdapter> productAdapters = getProductAdapters(productVos);

        validateProducts(couponCodeMap, productVos, productAdapters);
        validateCouponProductAssociation(couponCodeMap, productAdapters);
        validateCouponBrandAssociation(couponCodeMap, patientBrandId);
        validateCouponAreaAssociation(couponCodeMap, areaId);
        validateCouponReferrerAssociation(couponCodeMap, referrerId);
        validateCodes(codes);

        validateCoupons(contextType, couponCodeMap, totalCost, requesterId);

        validateCouponApplicability(couponCodeMap, productVos, beneficiaryId, withinSubscription, contextType);
    }

    /**
     * Validates if the user id is provided also checks if the user is registered with Portea.
     *
     * @throws InvalidConsumerException If given user id is not registered with Portea.
     */
    private void validateUser(Integer userId) {

        User user = userDao.find(userId);

        if(user == null) {
            throw new InvalidConsumerException(String.valueOf(userId));
        }
    }

    /**
     * If selected products size doesn't match with product adapters, find which
     * selected product is not registered and report it as invalid product.
     *
     * @throws InvalidProductException If a selected product has no product adapter.
     */
    private void validateProducts(List<ProductVo> selectedProducts, List<ProductAdapter> productAdapters) {

        if(productAdapters.size() != selectedProducts.size()) {

            selectedProducts.forEach(selectedProduct -> {
                boolean found = false;
                for (ProductAdapter productAdapter : productAdapters) {
                    if(Objects.equals(selectedProduct.getId(), productAdapter.getProductId())
                            && Objects.equals(selectedProduct.getProductType(), productAdapter.getProductType())) {
                        found = true;
                        break;
                    }
                }

                if(! found) {
                    throw new InvalidProductException(String.valueOf(selectedProduct.getId()), selectedProduct.getProductType().name());
                }
            });
        }
    }

    /**
     *
     * Validates the given codes.
     *
     * @throws InactiveCouponException If the code is deactivated.
     * @throws InvalidCouponException If the code is not registered with Portea.
     */
    private void validateCodes(String[] codes) {
        List<CouponCode> couponCodeList = couponCodeDao.getCouponCodes(codes);

        Map<String, Boolean> couponCodeMap = new HashMap<>();

        couponCodeList.forEach(code -> {
            if( ! code.isActive()) {
                throw new InactiveCouponException(code.getCode());
            }
            couponCodeMap.put(code.getCode(), true);

        });

        if(codes.length != couponCodeMap.size()) {

            for(String code : codes) {
                if(couponCodeMap.get(code) == null) {
                    throw new InvalidCouponException(code);
                }

            }
        }

    }


    /**
     * For global coupons, check if the selected products are registered in Portea db.
     * For non-global coupons, check if product adapters are created for selected products.
     */
    private void validateProducts(Map<Coupon, List<String>> couponCodeMap, List<ProductVo> selectedProducts, List<ProductAdapter> productAdapters) {

        couponCodeMap.forEach((coupon, codes) -> {
            boolean global = coupon.getGlobal();
            if (global) {
                validateProducts(selectedProducts);
            }
            else {
                validateProducts(selectedProducts, productAdapters);
            }
        });
        validateProducts(selectedProducts);
    }

    /**
     * Checks if the selected products are registered products in Portea.
     *
     * @throws InvalidProductException If a selected product is not registered with protea.
     */
    private void validateProducts(List<ProductVo> selectedProducts) {
        Map<ProductType, List<Integer>> typeIdMap = getTypeIdMap(selectedProducts);

        typeIdMap.forEach((type, ids) -> {

            switch (type) {
                case SERVICE:
                    List<Service> services = serviceDao.getServices(ids);
                    if (ids.size() != services.size()) {

                        ids.stream().allMatch(selectedProdId -> {
                            boolean anyMatch = services.stream()
                                    .anyMatch(registeredService -> Objects.equals(registeredService.getId(), selectedProdId));
                            if (! anyMatch) {
                                throw new InvalidProductException(String.valueOf(selectedProdId), type.name());
                            }
                            return true;
                        });
                    }

                    services.forEach(service -> {
                        try {
                            productAdapterDao.getProductAdapter(service.getId(), ProductType.SERVICE);
                        } catch (NoResultException e) {
                            productAdapterDao.create(service.getId(), ProductType.SERVICE, new Date(), service.getName());
                        }
                    });

                    break;
                case PACKAGE:
                    List<Package> packages = packageDao.getPackages(ids);
                    if (ids.size() != packages.size()) {

                        ids.stream().allMatch(selectedProdId -> {
                            boolean anyMatch = packages.stream()
                                    .anyMatch(registeredPackage -> Objects.equals(registeredPackage.getId(), selectedProdId));
                            if (! anyMatch) {
                                throw new InvalidProductException(String.valueOf(selectedProdId), type.name());
                            }
                            return true;
                        });
                    }

                    packages.forEach(eachPackage -> {
                        try {
                            productAdapterDao.getProductAdapter(eachPackage.getId(), ProductType.PACKAGE);
                        } catch (NoResultException e) {
                            productAdapterDao.create(eachPackage.getId(), ProductType.PACKAGE, new Date(), eachPackage.getName());
                        }
                    });
                    break;
            }
        });
    }

    /**
     * Following Coupon Validations are done for the given codes
     * <li>Whether coupon validity has expired</li>
     * <li>Whether coupon has been deactivated</li>
     * <li>Whether multiple exclusive coupons have been specified</li>
     * <li>Given total cost is within transaction limits</li>
     * <li>Given coupon use count is within acceptable limit</li>
     * <li>Given coupon actor type is staff and user type should be staff</li>
     * <li>If a coupon context is application it is only applicable during appointment.
     *     If a coupon context is subscription it can be applied during subscription as well as appointment.</li>
     *
     * @param couponCodeMap Each coupon is mapped to selected codes
     */
    private void validateCoupons(ContextType cdrContextType, Map<Coupon,
            List<String>> couponCodeMap, Double totalCost, Integer requesterId) {
        final List<String> listExclusiveCodes = new ArrayList<>();

        couponCodeMap.forEach((coupon, codes) -> {

            String couponCode = codes.get(0);

            checkCouponValidity(coupon, couponCode);

            checkIsCouponDeactivated(coupon, couponCode);

            checkForMultipleCodes(codes);

            checkCouponIsExclusive(listExclusiveCodes, coupon, couponCode, codes.size());

            checkCouponIsInTransactionRange(coupon, couponCode, totalCost);

            checkCouponActorType(coupon, requesterId);

            checkCouponContextType(coupon, cdrContextType);

        });
    }

    private void checkForMultipleCodes(List<String> codes) {
        if (codes.size() > 1) {
            throw new MultipleCodesForCouponException(codes.get(0), codes.get(1));
        }
    }

    private void checkCouponContextType(Coupon coupon, ContextType cdrContextType) {

        ContextType couponContextType = coupon.getContextType();
        if (couponContextType.equals(ContextType.APPOINTMENT)
                && ! cdrContextType.equals(ContextType.APPOINTMENT)) {
            throw new ContextTypeInapplicableException(cdrContextType.name(), couponContextType.name());
        }
        else if (couponContextType.equals(ContextType.SUBSCRIPTION) &&
                ! (cdrContextType.equals(ContextType.APPOINTMENT) || cdrContextType.equals(ContextType.SUBSCRIPTION))) {
            throw new ContextTypeInapplicableException(cdrContextType.name(), couponContextType.name());
        }
    }

    private void checkCouponActorType(Coupon coupon, Integer requesterId) {
        User user = userDao.find(requesterId);
        if(coupon.getActorType().equals(ActorType.STAFF) && ! user.getType().equals(ActorType.STAFF.getName())) {

            throw new CouponActorTypeException(String.valueOf(requesterId));
        }
    }

    private void checkCouponIsInApplicableCount(Integer applicableUseCount, Coupon coupon,
                                                List<String> codes) {

        if (applicableUseCount != null && applicableUseCount != -1) {
            if (coupon.getTrackUseAcrossCodes() != null && coupon.getTrackUseAcrossCodes()) {

                long totalAppliedCount = couponDiscountCodeDao.getCouponAppliedCount(coupon);

                if (totalAppliedCount >= applicableUseCount) {
                    throw new ApplicableCountExceededException(applicableUseCount, codes.get(0));
                }
            } else {

                codes.forEach(code -> {
                    Long appliedCount = couponDiscountCodeDao.getCodeAppliedCount(code);
                    if (appliedCount >= applicableUseCount) {
                        throw new ApplicableCountExceededException(applicableUseCount, code);
                    }
                });

            }
        }

    }

    private void checkCouponIsInApplicableCount(Integer applicableUseCount, Coupon coupon,
                                                List<String> codes, User user) {

        if (applicableUseCount != null && applicableUseCount != -1) {
            if (coupon.getTrackUseAcrossCodes() != null && coupon.getTrackUseAcrossCodes()) {

                long totalAppliedCount = couponDiscountCodeDao.getCouponAppliedCount(coupon, user);

                if (totalAppliedCount >= applicableUseCount) {
                    throw new ApplicableCountExceededException(applicableUseCount, codes.get(0));
                }
            } else {

                codes.forEach(code -> {
                    Long appliedCount = couponDiscountCodeDao.getCodeAppliedCount(code, user);
                    if (appliedCount >= applicableUseCount) {
                        throw new ApplicableCountExceededException(applicableUseCount, code);
                    }
                });

            }
        }

    }

    private void checkCouponIsInTransactionRange(Coupon coupon, String couponCode, Double totalCost) {
        Integer minTransaction = coupon.getTransactionMinValue();
        Integer maxTransaction = coupon.getTransactionMaxValue();

        boolean isOutsideMinTransRange = (minTransaction != null && minTransaction != -1 && totalCost < minTransaction);
        boolean isOutsideMaxTransRange = (maxTransaction != null && maxTransaction != -1 && totalCost > maxTransaction);
        boolean isOutSideTransactionRange = (isOutsideMinTransRange || isOutsideMaxTransRange);

        if(isOutSideTransactionRange) {
            throw new TransactionValueOutOfRangeException(
                    couponCode, coupon.getTransactionMinValue(), coupon.getTransactionMaxValue());
        }
    }

    private void checkCouponIsExclusive(List<String> listExclusiveCodes, Coupon coupon, String couponCode, Integer codesCount) {
        if(listExclusiveCodes.size() > 0 && ! coupon.getInclusive()) {
            throw new MultipleExclusiveCouponsException(listExclusiveCodes.get(0), couponCode);
        }

        if( ! coupon.getInclusive()) {
            if(codesCount > 1) {
                throw new MultipleExclusiveCouponsException(couponCode, couponCode);
            }
            listExclusiveCodes.add(couponCode);
        }
    }

    private void checkIsCouponDeactivated(Coupon coupon, String couponCode) {
        boolean isDeactivated = (coupon.getDeactivatedBy() != null);

        if (isDeactivated) {
            throw new InactiveCouponException(couponCode);
        }
    }

    private void checkCouponValidity(Coupon coupon, String couponCode) {

        Date applicableFrom = coupon.getApplicableFrom();
        Date applicableTill = coupon.getApplicableTill();
        Date currentDate = new Date();

        boolean isOutsideApplicableRange = currentDate.before(applicableFrom) || currentDate.after(applicableTill); // todo within duration.

        if (isOutsideApplicableRange) {
            throw new CouponValidityExpiredException(couponCode, applicableFrom, applicableTill);
        }
    }

    /**
     * Checks if the given coupon is mapped with all products or not.
     *
     * @throws InapplicableCouponException If coupon is not mapped with any of the
     * product adapter from the given list.
     */
    private void validateCouponProductAssociation(Map<Coupon, List<String>> couponCodeMap,
                                                  List<ProductAdapter> productAdapters) {

        couponCodeMap.forEach((coupon, codes) -> {
        Boolean isGlobal = coupon.getGlobal();
        Boolean isAllProducts = coupon.getIsForAllProducts();
            if((isGlobal != null && ! isGlobal) && (isAllProducts != null && !isAllProducts)) {

                productAdapters.forEach(productAdapter -> {
                    try {
                        couponProductAdapterMappingDao.find(coupon, productAdapter);
                    }
                    catch (NoResultException e) {
                        throw new InapplicableCouponException(codes.get(0),
                                String.valueOf(productAdapter.getProductId()), productAdapter.getProductType().name());
                    }


                });
            }
        });
    }

    /**
     * Checks if the given coupon is mapped with all brands or not.
     *
     * @throws CouponBrandMappingException If coupon is not mapped with any of the
     * brands from the given list.
     */
    private void validateCouponBrandAssociation(Map<Coupon, List<String>> couponCodeMap,
                                                Integer patientBrandId) {

        couponCodeMap.forEach((coupon, codes) -> {

            Boolean isGlobal = coupon.getGlobal();
            Boolean isAllAreas = coupon.getIsForAllBrands();
            if((isGlobal != null && ! isGlobal) && (isAllAreas != null && !isAllAreas)) {
                try {
                    couponBrandMappingDao.find(coupon, brandDao.find(patientBrandId));
                }
                catch (NoResultException e) {
                    throw new CouponBrandMappingException(codes.get(0),
                            String.valueOf(patientBrandId));
                }
            }

        });
    }

    /**
     * Checks if the given coupon is mapped with all areas or not.
     *
     * @throws CouponAreaMappingException If coupon is not mapped with any of the
     * brands from the given list.
     */
    private void validateCouponAreaAssociation(Map<Coupon, List<String>> couponCodeMap,
                                                Integer areaId) {

        couponCodeMap.forEach((coupon, codes) -> {

            Boolean isGlobal = coupon.getGlobal();
            Boolean isAllAreas = coupon.getIsForAllAreas();
            if((isGlobal != null && ! isGlobal) && (isAllAreas != null && !isAllAreas)) {
                    try {
                        couponAreaMappingDao.find(coupon, areaDao.find(areaId));
                    }
                    catch (NoResultException e) {
                        throw new CouponAreaMappingException(codes.get(0),
                                String.valueOf(areaId));
                    }
            }

        });
    }

    /**
     * Checks if the given coupon is mapped with all referrers or not.
     *
     * @throws CouponBrandMappingException If coupon is not mapped with any of the
     * brands from the given list.
     */
    private void validateCouponReferrerAssociation(Map<Coupon, List<String>> couponCodeMap,
                                               Integer referrerId) {

        couponCodeMap.forEach((coupon, codes) -> {

            Boolean global = coupon.getGlobal();
            if(global != null && ! global) {
                try {
                    couponReferrerMappingDao.find(coupon, referrerDao.find(referrerId));
                }
                catch (NoResultException e) {
                    throw new CouponReferrerMappingException(codes.get(0),
                            String.valueOf(referrerId));
                }
            }

        });
    }

    /**
     * Every coupon has an application type and all the application types are mentioned
     * in {@link CouponApplicationType}. Checks if the discount can be given for a coupon's
     * application type.
     */
    private void validateCouponApplicability(Map<Coupon, List<String>> couponCodeMap,
                                             List<ProductVo> productVos, Integer beneficiaryId,
                                             Boolean withinSubscription, ContextType cdrContextType) {
        couponCodeMap.forEach((coupon, codes) -> {
            CouponApplicationType type = coupon.getApplicationType();
            Integer nthTime = coupon.getNthTime();

            productVos.forEach(productVo -> {

                Boolean nthRecurring = coupon.getNthTimeRecurring();

                switch (type) {
                    case NTH_TIME:
                        List<Integer> purchaseCounts = getPurchaseCounts(productVo);
                        long noOfTimesDiscountCanBeAvailed = getNoOfTimesDiscountCanBeAvailed(purchaseCounts,
                                nthTime, ! nthRecurring, purchaseCount -> purchaseCount % nthTime == 0);

                        if(noOfTimesDiscountCanBeAvailed < codes.size()) {

                            throw new CouponNthTimeApplicationException(type.name(), String.valueOf(nthTime), nthRecurring,
                                    purchaseCounts.toArray(new Integer[purchaseCounts.size()]));
                        }
                        break;
                    case ONE_TIME:

                        int applicableUseCount = 1;
                        checkCouponIsInApplicableCount(applicableUseCount, coupon, codes);
                        break;
                    case ONE_TIME_PER_USER:

                        applicableUseCount = 1;
                        checkCouponIsInApplicableCount(applicableUseCount, coupon, codes, userDao.find(beneficiaryId));
                        break;
                    case ONE_TIME_PER_USER_FIFO:

                        applicableUseCount = 1;
                        checkCouponIsInApplicableCount(coupon.getApplicableUseCount(), coupon, codes);
                        checkCouponIsInApplicableCount(applicableUseCount, coupon, codes,userDao.find(beneficiaryId));
                        break;
                    case MANY_TIMES:
                        break;

                    case NTH_TIME_PER_SUBSCRIPTION:

                        if ( ! cdrContextType.equals(ContextType.SUBSCRIPTION) && ! withinSubscription){

                                throw new CouponNthTimeNotWithinSubscriptionException(cdrContextType.name());
                        }
                        purchaseCounts = getPurchaseCounts(productVo);
                        noOfTimesDiscountCanBeAvailed = getNoOfTimesDiscountCanBeAvailed(purchaseCounts,
                                nthTime, ! nthRecurring, purchaseCount -> purchaseCount % nthTime == 0);

                        if(noOfTimesDiscountCanBeAvailed < codes.size()) {
                            throw new CouponNthTimeApplicationException(type.name(), String.valueOf(nthTime), nthRecurring,
                                    purchaseCounts.toArray(new Integer[purchaseCounts.size()]));
                        }
                        break;
                    case NTH_TIME_AB_PER_SUBSCRIPTION:

                        if ( ! cdrContextType.equals(ContextType.SUBSCRIPTION) && ! withinSubscription){

                            throw new CouponNthTimeNotWithinSubscriptionException(cdrContextType.name());
                        }
                        purchaseCounts = getPurchaseCounts(productVo);
                        noOfTimesDiscountCanBeAvailed = getNoOfTimesDiscountCanBeAvailed(purchaseCounts,
                                nthTime, false, purchaseCount -> purchaseCount >= nthTime);

                        if (noOfTimesDiscountCanBeAvailed < codes.size()) {
                            throw new CouponNthTimeApplicationException(type.name(), String.valueOf(nthTime), nthRecurring,
                                    purchaseCounts.toArray(new Integer[purchaseCounts.size()]));
                        }
                        break;
                }
            });

        });
    }

    private List<Integer> getPurchaseCounts(ProductVo productVo) {
        Integer purchasedCount = (productVo.getPurchaseCount() == null)?0:productVo.getPurchaseCount();
        Integer currentBuyCount = (productVo.getCount() == null)?0:productVo.getCount();
        List<Integer> purchaseCounts = new ArrayList<>();
        IntStream.rangeClosed(purchasedCount + 1, purchasedCount + currentBuyCount).forEach(purchaseCounts::add);
        return purchaseCounts;
    }

    /**
     *
     * @param purchaseCounts list of purchase counts.
     *                       ex: In a cdr if  the same product is been purchased 3 times and so far
     *                       it had been purchased 12 times, then this list has 13,14,15 values.
     * @param nthTime Indicates the nth time for which the discount has to be given.
     * @param checkOnlyNthTime If true indicates that the product can be purchased only nth time.
     * @param canApplyDiscount Checks for each purchase count can the discount be applied.
     */
    private long getNoOfTimesDiscountCanBeAvailed(List<Integer> purchaseCounts,
                                                  Integer nthTime, Boolean checkOnlyNthTime,
                                                   Predicate<Integer> canApplyDiscount) {

        long noOfTimesDiscountCanBeAvailed;

        noOfTimesDiscountCanBeAvailed = purchaseCounts
                .stream()
                .filter(currentPurchaseCount -> {
                    if (checkOnlyNthTime) {

                        return Objects.equals(currentPurchaseCount, nthTime);
                    } else {

                        return canApplyDiscount.test(currentPurchaseCount);
                    }
                })
                .count();
        return noOfTimesDiscountCanBeAvailed;
    }

    private void createCouponRequestAudit(CouponDiscountRequest couponDiscountRequest) {
        CouponDiscountRequestAudit cdrAudit = new CouponDiscountRequestAudit();
        cdrAudit.setCouponDiscountRequest(couponDiscountRequest);

        cdrAudit.setRequester(couponDiscountRequest.getRequester());
        cdrAudit.setBeneficiary(couponDiscountRequest.getBeneficiary());
        cdrAudit.setClientContextId(couponDiscountRequest.getClientContextId());
        cdrAudit.setReferrerId(couponDiscountRequest.getReferrerId());
        cdrAudit.setAreaId(couponDiscountRequest.getAreaId());
        cdrAudit.setPatientBrand(couponDiscountRequest.getPatientBrand());

        cdrAudit.setClientContextType(couponDiscountRequest.getClientContextType());
        cdrAudit.setTotalCost(couponDiscountRequest.getTotalCost());
        cdrAudit.setStatus(couponDiscountRequest.getStatus());

        cdrAudit.setSourceName(couponDiscountRequest.getSourceName());
        cdrAudit.setWithinSubscription(couponDiscountRequest.getWithinSubscription());
        cdrAudit.setCreatedOn(new Date());
        couponDiscountRequestAuditDao.create(cdrAudit);

        List<CouponDiscountRequestCode> codes = couponDiscountRequestCodeDao.getCouponCodes(couponDiscountRequest);

        codes.forEach(code -> {
            CouponDiscountRequestCodeAudit cdrCodeAudit = new CouponDiscountRequestCodeAudit();
            cdrCodeAudit.setCouponCode(code.getCouponCode());
            cdrCodeAudit.setCouponDiscReqAudit(cdrAudit);

            couponDiscountReqCodeAuditDao.create(cdrCodeAudit);
        });

        List<CouponDiscountRequestProduct> products = couponDiscountRequestProductDao.getProducts(couponDiscountRequest);

        products.forEach(product -> {
            CouponDiscountReqProdAudit cdrProdAudit = new CouponDiscountReqProdAudit();


            cdrProdAudit.setCouponDiscReqAudit(cdrAudit);
            cdrProdAudit.setProductId(product.getProductId());
            cdrProdAudit.setProductCount(product.getProductCount());
            cdrProdAudit.setProductUnitPrice(product.getProductUnitPrice());
            cdrProdAudit.setRemarks(product.getRemarks());
            cdrProdAudit.setPurchaseInstanceCount(product.getPurchaseInstanceCount());

            couponDiscountReqProdAuditDao.create(cdrProdAudit);
        });
    }

    private CouponDiscountRequest createCouponRequest(int userId, Integer beneficiaryId, ContextType cdrContextType,
                                                      String[] codes, List<ProductVo> productVos, Double totalCost,
                                                      String sourceName, Boolean withinSubscription, Integer areaId, Integer referrerId, Integer patientBrandId) {

        CouponDiscountRequest cdr = new CouponDiscountRequest();
        cdr.setCompleted(false);
        User user = userDao.find(userId);
        cdr.setRequester(user);

        if(beneficiaryId != null) {
            User beneficiary = userDao.find(beneficiaryId);
            cdr.setBeneficiary(beneficiary);
        }

        cdr.setClientContextType(cdrContextType);
        cdr.setAreaId(areaDao.find(areaId));
        cdr.setReferrerId(referrerDao.find(referrerId));
        cdr.setPatientBrand(brandDao.find(patientBrandId));
        cdr.setTotalCost(totalCost);
        cdr.setStatus(CouponDiscountRequestStatus.REQUESTED);
        cdr.setLatestUpdatedOn(new Date());
        cdr.setSourceName(sourceName);
        cdr.setWithinSubscription(withinSubscription);
        couponDiscountRequestDao.create(cdr);

        for (String code : codes) {
            CouponDiscountRequestCode cdrCode = new CouponDiscountRequestCode();
            CouponCode couponCode = couponCodeDao.getCouponCode(code);
            cdrCode.setCouponCode(couponCode);
            cdrCode.setCouponDiscountRequest(cdr);

            couponDiscountRequestCodeDao.create(cdrCode);
        }

        for (ProductVo productVo : productVos) {
            CouponDiscountRequestProduct cdrProduct = new CouponDiscountRequestProduct();
            cdrProduct.setCouponDiscountRequest(cdr);
            cdrProduct.setProductId(productVo.getId());
            cdrProduct.setProductCount(productVo.getCount());

            cdrProduct.setProductUnitPrice(productVo.getUnitCost());
            cdrProduct.setPurchaseInstanceCount(productVo.getPurchaseCount());

            cdrProduct.setProductType(productVo.getProductType());
            cdrProduct.setRemarks(productVo.getRemarks());
            couponDiscountReqProdDao.create(cdrProduct);
        }

        return cdr;
    }

    @Override
    public void updateCouponDiscountRequest(Integer cdrId, CouponDiscountRequestUpdateReq request) {

        CouponDiscountRequest cdr = validateAndGetCdr(cdrId);

        checkForNullValues(request);

        if (cdr.getStatus().equals(CouponDiscountRequestStatus.CANCELED)) {

            throw new IllegalCdrCancelStateException(String.valueOf(cdrId));

        } else if(cdr.getStatus().equals(CouponDiscountRequestStatus.APPLIED)) {

            throw new IllegalCdrApplyStateException(String.valueOf(cdrId));
        }
        cdr.setTotalCost(request.getTotalCost());

        List<CouponDiscountRequestCode> existingCodes = couponDiscountRequestCodeDao.getCouponCodes(cdr);

        existingCodes.forEach(couponDiscountRequestCodeDao::delete);

        cdr.setStatus(CouponDiscountRequestStatus.REQUESTED);


        List<CouponDiscountRequestProduct> requestProducts = couponDiscountReqProdDao.getProducts(cdr);
        for(CouponDiscountRequestProduct couponDiscountRequestProduct : requestProducts){
            couponDiscountReqProdDao.delete(couponDiscountRequestProduct);
        }

        String[] codes = request.getCodes();

        validateCDRequest(cdr.getRequester().getId(), cdr.getClientContextType(),
                cdr.getBeneficiary().getId(), getCouponCodeMap(request.getCodes()), cdr.getTotalCost(),
                getProductVosForSelectedProducts(request.getProducts()), cdr.getWithinSubscription(), codes,
                cdr.getAreaId().getId(), cdr.getReferrerId().getId(), cdr.getPatientBrand().getId());

        for (String code : codes) {
            try{
                CouponDiscountRequestCode cdrCode = new CouponDiscountRequestCode();
                CouponCode couponCode = couponCodeDao.getCouponCode(code);
                cdrCode.setCouponCode(couponCode);
                cdrCode.setCouponDiscountRequest(cdr);

                couponDiscountRequestCodeDao.create(cdrCode);
            }
            catch(NoResultException e) {
                throw new InvalidCouponException(code);
            }
        }

        List<SelectedProduct> products = request.getProducts();
        for(SelectedProduct selectedProduct : products) {
            CouponDiscountRequestProduct cdrProduct = new CouponDiscountRequestProduct();
            cdrProduct.setCouponDiscountRequest(cdr);
            cdrProduct.setProductId(selectedProduct.getId());
            cdrProduct.setProductType(selectedProduct.getProductType());
            cdrProduct.setProductCount(selectedProduct.getCount());
            cdrProduct.setProductUnitPrice(selectedProduct.getUnitCost());
            cdrProduct.setRemarks(selectedProduct.getRemarks());
            couponDiscountReqProdDao.create(cdrProduct);
        }

        createCouponRequestAudit(cdr);
    }

    @Override
    public void cancelCouponDiscountRequest(Integer cdrId) {
        CouponDiscountRequest couponDiscountRequest = validateAndGetCdr(cdrId);

        if (couponDiscountRequest.getStatus().equals(CouponDiscountRequestStatus.CANCELED)) {

            throw new IllegalCdrCancelStateException(String.valueOf(cdrId));

        } else if(couponDiscountRequest.getStatus().equals(CouponDiscountRequestStatus.APPLIED)) {

            throw new IllegalCdrApplyStateException(String.valueOf(cdrId));
        }

        couponDiscountRequest.setStatus(CouponDiscountRequestStatus.CANCELED);

        createCouponRequestAudit(couponDiscountRequest);

    }

    @Override
    public ApplicableDiscountResp getCurrentApplicableDiscount(Integer cdrId) {

        CouponDiscountRequest cdr = validateAndGetCdr(cdrId);

        String[] codes = getCodes(cdr);

        Map<Coupon, List<String>> couponCodeMap = getCouponCodeMap(codes);

        Double discount = getTotalDiscount(couponCodeMap, cdr.getTotalCost());

        ApplicableDiscountResp applicableDiscountResp = new ApplicableDiscountResp();

        applicableDiscountResp.setDiscountAmount(discount);

        return applicableDiscountResp;
    }

    private String[] getCodes(CouponDiscountRequest cdr) {

        List<CouponDiscountRequestCode> codes = couponDiscountRequestCodeDao.getCouponCodes(cdr);

        List<String> listCodes = new ArrayList<>();

        codes.forEach(code -> listCodes.add(code.getCouponCode().getCode()));

        return listCodes.toArray(new String[listCodes.size()]);
    }

    private CouponDiscountRequest validateAndGetCdr(Integer cdrId) {
        if(cdrId == null) {
            throw new IncompleteRequestException("cdrId");
        }

        CouponDiscountRequest cdr = couponDiscountRequestDao.find(cdrId);

        if(cdr == null) {
            throw new InvalidCouponDiscountRequestException(String.valueOf(cdrId));
        }
        return cdr;
    }

    public void addCouponCodeToRequest(Integer cdrId, String code){

        CouponDiscountRequest cdr = validateAndGetCdr(cdrId);

        if (cdr.getStatus().equals(CouponDiscountRequestStatus.CANCELED)) {

            throw new IllegalCdrCancelStateException(String.valueOf(cdrId));

        } else if(cdr.getStatus().equals(CouponDiscountRequestStatus.APPLIED)) {

            throw new IllegalCdrApplyStateException(String.valueOf(cdrId));
        }

        List<String> couponCodes = couponDiscountRequestCodeDao.getCouponCodes(cdrId);
        couponCodes.add(code);
        String[] codes = couponCodes.toArray(new String[couponCodes.size()]);

        // validate the coupon product mappings after adding the new coupon code
        validateCDRequest(cdr.getRequester().getId(), cdr.getClientContextType(),
                cdr. getBeneficiary().getId(), getCouponCodeMap(codes), cdr.getTotalCost(),
                getProductVos(couponDiscountRequestProductDao.getProducts(cdr)), cdr.getWithinSubscription(), codes,
                cdr.getAreaId().getId(), cdr.getReferrerId().getId(), cdr.getPatientBrand().getId());

        try {
            CouponCode couponCode = couponCodeDao.getCouponCode(code);
            CouponDiscountRequestCode couponDiscountRequestCode = new CouponDiscountRequestCode();
            couponDiscountRequestCode.setCouponCode(couponCode);
            couponDiscountRequestCode.setCouponDiscountRequest(cdr);

            couponDiscountRequestCodeDao.create(couponDiscountRequestCode);
        }
        catch(NoResultException e) {
            throw new InvalidCouponException(code);
        }


        createCouponRequestAudit(cdr);

    }

    public void deleteCouponCodeFromRequest(Integer cdrId, String code){
        boolean codeFound = false;
        CouponDiscountRequest couponDiscountRequest = validateAndGetCdr(cdrId);
        if (couponDiscountRequest.getStatus().equals(CouponDiscountRequestStatus.CANCELED)) {

            throw new IllegalCdrCancelStateException(String.valueOf(cdrId));

        } else if(couponDiscountRequest.getStatus().equals(CouponDiscountRequestStatus.APPLIED)) {

            throw new IllegalCdrApplyStateException(String.valueOf(cdrId));
        }

        List<CouponDiscountRequestCode> couponDiscountRequestCodes = couponDiscountRequestCodeDao.getCouponCodes(couponDiscountRequest);

        for(CouponDiscountRequestCode couponDiscountRequestCode : couponDiscountRequestCodes) {
            if(couponDiscountRequestCode.getCouponCode().getCode().equalsIgnoreCase(code)) {
                codeFound = true;
                couponDiscountRequestCodeDao.delete(couponDiscountRequestCode);
                break;
            }
        }
        if(!codeFound) {
            throw new InvalidCouponException(code);
        }

        createCouponRequestAudit(couponDiscountRequest);

    }

    public void addProductToRequest(Integer cdrId, ProductUpdateReq productUpdateReq){

        CouponDiscountRequest cdr = validateAndGetCdr(cdrId);

        checkForNullValues(productUpdateReq);

        if (cdr.getStatus().equals(CouponDiscountRequestStatus.CANCELED)) {

            throw new IllegalCdrCancelStateException(String.valueOf(cdrId));

        } else if(cdr.getStatus().equals(CouponDiscountRequestStatus.APPLIED)) {

            throw new IllegalCdrApplyStateException(String.valueOf(cdrId));
        }

        List<SelectedProduct> selectedProducts = productUpdateReq.getProducts();
        ProductVo productVo = new ProductVo();
        for(SelectedProduct selectedProduct : selectedProducts) {
            productVo.setId(selectedProduct.getId());
            productVo.setProductType(selectedProduct.getProductType());
            productVo.setUnitCost(selectedProduct.getUnitCost());
            productVo.setCount(selectedProduct.getCount());
            productVo.setPurchaseCount(selectedProduct.getPurchaseCount());
        }

        // Validate the request again
        List<String> couponCodes = couponDiscountRequestCodeDao.getCouponCodes(cdrId);
        String[] codes = couponCodes.toArray(new String[couponCodes.size()]);

        List<ProductVo> productVos = getProductVos(couponDiscountRequestProductDao.getProducts(cdr));
        productVos.add(productVo);

        validateCDRequest(cdr.getRequester().getId(), cdr.getClientContextType(),
                cdr.getBeneficiary().getId(), getCouponCodeMap(codes), cdr.getTotalCost(),
                productVos, cdr.getWithinSubscription(), codes,
                cdr.getAreaId().getId(), cdr.getReferrerId().getId(), cdr.getPatientBrand().getId());

        cdr.setTotalCost(productUpdateReq.getTotalCost());

        for(SelectedProduct selectedProduct : selectedProducts) {
            CouponDiscountRequestProduct cdrProduct = new CouponDiscountRequestProduct();
            cdrProduct.setProductId(selectedProduct.getId());
            cdrProduct.setProductType(selectedProduct.getProductType());
            cdrProduct.setProductUnitPrice(selectedProduct.getUnitCost());
            cdrProduct.setProductCount(selectedProduct.getCount());
            cdrProduct.setRemarks(selectedProduct.getRemarks());
            cdrProduct.setPurchaseInstanceCount(selectedProduct.getPurchaseCount());
            cdrProduct.setCouponDiscountRequest(cdr);

            couponDiscountReqProdDao.create(cdrProduct);
        }
        // TODO :: If we plan to update an existing product here

        createCouponRequestAudit(cdr);

    }

    public void deleteProductFromRequest(Integer cdrId, Integer productId, String productType, CostUpdateReq costUpdateReq){

        CouponDiscountRequest couponDiscountRequest = validateAndGetCdr(cdrId);

        if(costUpdateReq.inspectNullParameters() != null) {
            throw new IncompleteRequestException(costUpdateReq.inspectNullParameters());
        }

        if (couponDiscountRequest.getStatus().equals(CouponDiscountRequestStatus.CANCELED)) {

            throw new IllegalCdrCancelStateException(String.valueOf(cdrId));

        } else if(couponDiscountRequest.getStatus().equals(CouponDiscountRequestStatus.APPLIED)) {

            throw new IllegalCdrApplyStateException(String.valueOf(cdrId));
        }

        couponDiscountRequest.setTotalCost(costUpdateReq.getTotalCost());
        try{
            CouponDiscountRequestProduct couponDiscountRequestProduct = couponDiscountReqProdDao.getRequestProductById(cdrId, productId, (productType.equalsIgnoreCase(ProductType.PACKAGE.name()) ? ProductType.PACKAGE : ProductType.SERVICE));
            if(couponDiscountRequestProduct != null) {
                couponDiscountReqProdDao.delete(couponDiscountRequestProduct);
            }
        }
        catch(NoResultException e){
            throw new InvalidProductException(String.valueOf(productId), null);
        }

        createCouponRequestAudit(couponDiscountRequest);

    }

    public List<CouponCodeVO> getCouponCodes(Integer cdrId) {

        validateAndGetCdr(cdrId);

        List<CouponCodeVO> couponCodeVOs = new ArrayList<>();
        List<String> couponCodes = couponDiscountRequestCodeDao.getCouponCodes(cdrId);

        for (String code : couponCodes) {
            CouponCode couponCode = couponCodeDao.getCouponCode(code);
            CouponCodeVO couponCodeVO = new CouponCodeVO();
            couponCodeVO.setCode(couponCode.getCode());
            couponCodeVO.setChannelName(couponCode.getChannelName());
            couponCodeVO.setCreatedBy(couponCode.getCreatedBy().getName());
            couponCodeVO.setDeactivatedBy(getUserName(couponCode.getDeactivatedBy()));
            couponCodeVO.setDeactivatedOn(couponCode.getDeactivatedOn());
            couponCodeVO.setCreatedOn(couponCode.getCreatedOn());
            couponCodeVO.setCouponId(couponCode.getCoupon().getId());
            couponCodeVO.setId(couponCode.getId());
            couponCodeVOs.add(couponCodeVO);
        }

        return couponCodeVOs;
    }

    private String getUserName(User user) {
        if(user != null) {
            return user.getName();
        }
        return null;
    }

    public List<ProductVo> getProducts(Integer cdrId){

        CouponDiscountRequest couponDiscountRequest = validateAndGetCdr(cdrId);

        List<CouponDiscountRequestProduct> productsForCDR = couponDiscountReqProdDao.getProducts(couponDiscountRequest);

        return getProductVos(productsForCDR);
    }

    public int commitCDR(Integer cdrId, String clientContextId){

        CouponDiscountRequest couponDiscountRequest = validateAndGetCdr(cdrId);

        if(couponDiscountRequest.getStatus().equals(CouponDiscountRequestStatus.CANCELED)) {
            throw new IllegalCdrCancelStateException((String.valueOf(cdrId)));
        }

        if(couponDiscountRequest.getStatus().equals(CouponDiscountRequestStatus.REQUESTED)) {
            throw new IllegalCdrCommitStateException(String.valueOf(cdrId));
        }

        if((couponDiscountRequest.getStatus().equals(CouponDiscountRequestStatus.APPLIED) && couponDiscountRequest.getClientContextId() == null) && clientContextId == null) {
            throw new ContextIdRequiredException((String.valueOf(cdrId)));
        }

        if(couponDiscountRequest.getClientContextId() == null) {
            couponDiscountRequest.setClientContextId(clientContextId);
            createCouponRequestAudit(couponDiscountRequest);
        }
        else {
            throw new ContextIdAlreadyAppliedException(String.valueOf(cdrId));
        }
        return cdrId;

    }

    public int applyCDR(Integer cdrId, String clientContextId){
        CouponDiscountRequest cdr = validateAndGetCdr(cdrId);

        List<CouponDiscountRequestCode> codes = couponDiscountRequestCodeDao.getCouponCodes(cdr);

        if (cdr.getStatus().equals(CouponDiscountRequestStatus.CANCELED)) {

            throw new IllegalCdrCancelStateException(String.valueOf(cdrId));

        } else if(cdr.getStatus().equals(CouponDiscountRequestStatus.APPLIED)) {

            throw new IllegalCdrApplyStateException(String.valueOf(cdrId));
        }

        String[] couponCodes = getCodes(codes);
        Map<Coupon, List<String>> couponCodeMap = getCouponCodeMap(couponCodes);

        validateCDRequest(cdr.getRequester().getId(), cdr.getClientContextType(),
                cdr.getBeneficiary().getId(), couponCodeMap, cdr.getTotalCost(),
                getProductVos(couponDiscountRequestProductDao.getProducts(cdr)),cdr.getWithinSubscription(), couponCodes,
                cdr.getAreaId().getId(), cdr.getReferrerId().getId(), cdr.getPatientBrand().getId());

        cdr.setClientContextId(clientContextId);

        cdr.setCompleted(true);
        cdr.setStatus(CouponDiscountRequestStatus.APPLIED);

        couponDiscountRequestDao.update(cdr);
        createCouponDiscount(cdr);
        createCouponRequestAudit(cdr);
        return cdrId;
    }

    public String[] getCodes(List<CouponDiscountRequestCode> codes) {
        String[] couponCodes = new String[codes.size()];
        final Integer[] index = {0};

        codes.forEach(code -> {
            couponCodes[index[0]] = code.getCouponCode().getCode();
            index[0]++;
        });
        return couponCodes;
    }

    private void createCouponDiscount(CouponDiscountRequest cdr) {
        CouponDiscount couponDiscount = new CouponDiscount();
        couponDiscount.setClientContextId(cdr.getClientContextId());
        couponDiscount.setTotalCost(cdr.getTotalCost());
        couponDiscount.setDiscountAmount(getCurrentApplicableDiscount(cdr.getId()).getDiscountAmount());
        couponDiscount.setRequester(cdr.getRequester());
        couponDiscount.setBeneficiary(cdr.getBeneficiary());
        couponDiscount.setAreaId(cdr.getAreaId());
        couponDiscount.setReferrerId(cdr.getReferrerId());
        couponDiscount.setPatientBrand(cdr.getPatientBrand());
        couponDiscount.setClientContextType(cdr.getClientContextType());
        couponDiscount.setCreatedOn(new Date());
        couponDiscount.setCouponDiscountRequest(cdr);

        couponDiscountDao.create(couponDiscount);


        List<CouponDiscountRequestCode> codes = couponDiscountRequestCodeDao.getCouponCodes(cdr);

        codes.forEach(code -> {
            CouponDiscountCode couponDiscountCode = new CouponDiscountCode();

            CouponCode couponCode = code.getCouponCode();
            couponDiscountCode.setCouponCode(couponCode);
            couponDiscountCode.setCouponDiscount(couponDiscount);

            couponDiscountCodeDao.create(couponDiscountCode);
        });

        List<CouponDiscountRequestProduct> products = couponDiscountRequestProductDao.getProducts(cdr);

        products.forEach(product -> {

            CouponDiscountProduct couponDiscountProduct = new CouponDiscountProduct();
            couponDiscountProduct.setCouponDiscount(couponDiscount);
            ProductAdapter productAdapter = productAdapterDao.
                    getProductAdapter(product.getProductId(), product.getProductType());
            couponDiscountProduct.setProductAdapter(productAdapter);
            couponDiscountProduct.setProductCount(product.getProductCount());

            couponDiscountProduct.setProductUnitPrice(product.getProductUnitPrice());
            couponDiscountProduct.setPurchaseInstanceCount(product.getPurchaseInstanceCount());
            couponDiscountProductDao.create(couponDiscountProduct);
        });
    }

    public CouponDiscountRequestStatusResp getCDRStatus(Integer cdrId){

        CouponDiscountRequestStatusResp couponDiscountRequestStatusResp = new CouponDiscountRequestStatusResp();

        CouponDiscountRequest cdr = validateAndGetCdr(cdrId);
        if(cdr != null) {
            couponDiscountRequestStatusResp.setStatus(cdr.getStatus().name());
        }
        return couponDiscountRequestStatusResp;
    }

    private Map<ProductType, List<Integer>> getTypeIdMap(List<ProductVo> productVos) {

        Map<ProductType, List<Integer>> typeIdMap = new HashMap<>();

        productVos.forEach(productVo -> {

            ProductType type = productVo.getProductType();
            Integer id = productVo.getId();

            List<Integer> productIds = typeIdMap.get(type);

            if(productIds == null) {

                productIds = new ArrayList<>();
                typeIdMap.put(type , productIds);
            }
            productIds.add(id);
        });
        return typeIdMap;
    }

    /**
     * Groups all the codes that belong to a coupon by mapping coupon to list of codes.
     * List also includes repeated codes.
     */
    private Map<Coupon, List<String>> getCouponCodeMap(String[] codes) {

        List<String> couponCodes = new ArrayList<>();
        final Map<String, Integer> codeCount = new HashMap<>();

        for (String code : codes) {
            Integer currCount = (codeCount.get(code) == null)? 0 : codeCount.get(code);
            codeCount.put(code, ++currCount);

            couponCodes.add(code);
        }

        List<Object[]> objects = couponCodeDao.getCouponCodeMap(couponCodes);

        Map<Coupon, List<String>> couponCodeMap = new HashMap<>();

        objects.forEach(object -> {
            Coupon coupon = (Coupon) object[0];
            String code = (String) object[1];
            List<String> listCodes = couponCodeMap.get(coupon);
            if(listCodes == null){
                couponCodeMap.put(coupon, new ArrayList<>());
            }
            IntStream.rangeClosed(1, codeCount.get(code))
                     .forEach(index -> couponCodeMap.get(coupon).add(code));
        });
        return couponCodeMap;
    }

    /**
     * Groups all the products based on their type which is useful in
     * querying a bulk of similar products in a single query.
     * Returns list of product adapters for the selected products.
     */
    private List<ProductAdapter> getProductAdapters(List<ProductVo> productVos) {
        Map<ProductType, List<Integer>> typeIdMap = getTypeIdMap(productVos);

        List<ProductAdapter> productAdapters = new ArrayList<>();
        typeIdMap.forEach((type, productIds) ->
                productAdapters.addAll(productAdapterDao.getProductAdapters(productIds, type)));

        return productAdapters;
    }

    private Double getTotalDiscount(Map<Coupon, List<String>> couponCodeMap, Double totalCost) {

        List<Double> discounts = new ArrayList<>();

        couponCodeMap.forEach((coupon, codes) -> discounts.add(getDiscount(coupon,totalCost, codes.size())));

        return discounts.stream().map((discount) -> discount).reduce((sum, discount) -> sum + discount).get();
    }

    /**
     * Returns the discount to be given. Same discount is obtained for
     * all the codes that belongs to a coupon. Therefore total discount
     * for a given codeCount will be discount for one coupon * codeCount.
     *
     * @param coupon For which the discount is calculated.
     * @param totalCost Used to calculate discount.
     */
    private Double getDiscount(Coupon coupon, Double totalCost, Integer codeCount) {

        Double discount = 0.0;
        try{
            CouponDiscountingRule couponDiscountingRule = couponDiscountingRuleDao.getRule(coupon);
            CouponDiscountingRuleType type = couponDiscountingRule.getCouponDiscRuleType();


            if(type.equals(CouponDiscountingRuleType.FLAT)) {
                discount = Double.valueOf(couponDiscountingRule.getDiscountFlatAmount());
            }
            else if(type.equals(CouponDiscountingRuleType.PERCENTAGE)) {
                Integer percentAmt = couponDiscountingRule.getDiscountPercentage();
                discount = percentAmt * totalCost / 100;
            }
        }
        catch (NoResultException e) {
            throw new InternalServerErrorException("No existing discount rules found for coupon : " + coupon.getId());

        }
        if (coupon.getDiscountAmountMax() != null && discount > coupon.getDiscountAmountMax()) {
            discount = Double.valueOf(coupon.getDiscountAmountMax());
        }
        discount *= codeCount;
        return discount;
    }

    private List<ProductVo> getProductVosForSelectedProducts(List<SelectedProduct> selectedProducts) {
        final List<ProductVo> productVos = new ArrayList<>();
        selectedProducts.forEach(selectedProduct -> {
            ProductVo productVo = new ProductVo();

            productVo.setId(selectedProduct.getId());
            productVo.setCount(selectedProduct.getCount());

            productVo.setProductType(selectedProduct.getProductType());
            productVo.setPurchaseCount(selectedProduct.getPurchaseCount());

            productVo.setRemarks(selectedProduct.getRemarks());
            productVo.setUnitCost(selectedProduct.getUnitCost());
            productVos.add(productVo);
        });

        return productVos;
    }

    private List<ProductVo> getProductVos(List<CouponDiscountRequestProduct> couponDiscountRequestProducts) {
        final List<ProductVo> productVos = new ArrayList<>();
        couponDiscountRequestProducts.forEach(product -> {
            ProductVo productVo = new ProductVo();

            productVo.setId(product.getProductId());
            productVo.setCount(product.getProductCount());

            productVo.setProductType(product.getProductType());
            productVo.setPurchaseCount(product.getPurchaseInstanceCount());

            productVo.setRemarks(product.getRemarks());
            productVo.setUnitCost(product.getProductUnitPrice());
            productVos.add(productVo);
        });

        return productVos;
    }

    public CouponInfoResponse getCoupon(String couponCode) {

        CouponInfoResponse couponInfoResponse = new CouponInfoResponse();

        try {
            Coupon coupon = couponCodeDao.getCouponCode(couponCode).getCoupon();

            couponInfoResponse.setCouponId(coupon.getId());
            couponInfoResponse.setName(coupon.getName());

            couponInfoResponse.setDescription(coupon.getDescription());
            couponInfoResponse.setInclusive(coupon.getInclusive());

            couponInfoResponse.setApplicableUseCount(coupon.getApplicableUseCount());
            couponInfoResponse.setApplicationType(coupon.getApplicationType());

            couponInfoResponse.setActorType(coupon.getActorType());
            couponInfoResponse.setContextType(coupon.getContextType());

            couponInfoResponse.setGlobal(coupon.getGlobal());
            couponInfoResponse.setNthTime(coupon.getNthTime());

            couponInfoResponse.setNthTimeRecurring(coupon.getNthTimeRecurring());

            List<CouponProductAdapterMapping> productMappings = couponProductAdapterMappingDao.getMappings(coupon);
            for (CouponProductAdapterMapping mapping : productMappings) {
                ProductAdapter productAdapter = mapping.getProductAdapter();
                couponInfoResponse.addProductMapping(productAdapter.getProductId(), productAdapter.getProductType(), productAdapter.getName());
            }
        }
        catch(NoResultException e) {
            throw new InvalidCouponException(couponCode);

        }

        return couponInfoResponse;
    }

}
