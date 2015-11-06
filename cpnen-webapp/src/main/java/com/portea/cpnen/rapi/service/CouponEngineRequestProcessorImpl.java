package com.portea.cpnen.rapi.service;

import com.portea.cpnen.dao.*;
import com.portea.cpnen.domain.*;
import com.portea.cpnen.rapi.domain.ApplicableDiscountResp;
import com.portea.cpnen.rapi.domain.CouponDiscountRequestCreateReq;
import com.portea.cpnen.rapi.domain.CouponDiscountRequestUpdateReq;
import com.portea.cpnen.rapi.domain.SelectedProduct;
import com.portea.cpnen.rapi.exception.*;
import com.portea.cpnen.vo.ProductVo;
import com.portea.dao.JpaDao;
import com.portea.util.BeanUtil;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.InternalServerErrorException;
import java.util.*;

@Stateless
public class CouponEngineRequestProcessorImpl implements CouponEngineRequestProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(CouponEngineRequestProcessorImpl.class);

    @Inject @JpaDao
    CouponCodeDao couponCodeDao;

    @Inject @JpaDao
    UserDao userDao;

    @Inject @JpaDao
    ProductDao productDao;

    @Inject @JpaDao
    CategoryDao categoryDao;

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
    CouponCategoryMapDao couponCategoryMapDao;

    @Inject @JpaDao
    CouponProductMapDao couponProductMapDao;

    @Inject @JpaDao
    CouponDiscountingRuleDao couponDiscountingRuleDao;

    @Inject @JpaDao
    CouponDiscountingRuleIntervalDao couponDiscountingIntervalRuleDao;

    @Override
    public CouponDiscountRequest createCouponDiscountRequest(CouponDiscountRequestCreateReq request) {

        if (LOG.isTraceEnabled()) {
            LogUtil.entryTrace("createCouponDiscountRequest", LOG,
                    "Specified user is {}, selected products are {} and specified codes are {}",
                    request.getUserId(),
                    BeanUtil.collect(request.getProducts(), new String[]{"count"}, true),
                    request.getCodes());
        }

        int userId = request.getUserId();

        List<SelectedProduct> selectedProducts = request.getProducts();

        String[] codes = request.getCodes();

        validateRequest(userId, codes, selectedProducts);
        LOG.debug("Request validated");

        CouponDiscountRequest couponDiscountRequest = createCouponRequest(userId, codes, selectedProducts);
        LOG.debug("Coupon discount request created");

        createCouponRequestAudit(couponDiscountRequest, userId, codes, selectedProducts);
        LOG.debug("Coupon discount request audit created");

        LogUtil.exitTrace("createCouponDiscountRequest", LOG, "Returning created cdr");

        return couponDiscountRequest;
    }

    private void validateRequest(int userId, String[] codes, List<SelectedProduct> selectedProducts) {
        validateConsumer(userId);
        validateProducts(extractIds(selectedProducts));
        validateCodes(codes);
        validateCoupons(codes);
        validateCouponProductAssociation(codes, selectedProducts);
    }

    /**
     * A selected code is validated to check if its product association is satisfied or not.
     * Because the validation is at coupon level if a code belongs to the same coupon that was validated before
     * no validation is required for that code.
     */
    private void validateCouponProductAssociation(String[] codes, List<SelectedProduct> selectedProducts) {
        int productCount = getProductCount(selectedProducts);
        int totalCost = getProductsTotalCost(selectedProducts);

        Map<Integer, String> couponToCodeMap = new HashMap<>(); // Coupon ID, Coupon Code

        for(String code : codes) {
            CouponCode couponCode = couponCodeDao.getCouponCode(code);
            Coupon coupon = couponCode.getCoupon();

            if(couponToCodeMap.get(coupon.getId()) == null) {
                couponToCodeMap.put(coupon.getId(), code);
                validateEachCouponProdAssociation(productCount, totalCost, code, coupon, selectedProducts);
            }
        }
    }

    private Integer[] extractIds(List<SelectedProduct> selectedProducts) {
        Integer[] productIds = new Integer[selectedProducts.size()];

        for(int i = 0 ; i < selectedProducts.size() ; i++) {
            productIds[i] = selectedProducts.get(i).getId();
        }
        return productIds;
    }

    /**
     * Following Coupon Validations are done for the given codes
     * <li>Whether coupon validity has expired</li>
     * <li>Whether coupon has been deactivated</li>
     * <li>Whether multiple exclusive coupons have been specified</li>
     */
    private void validateCoupons(String[] codes) {
        String prevExclusiveCode = null;

        for(String code : codes) {
            CouponCode couponCode = couponCodeDao.getCouponCode(code);
            Coupon coupon = couponCode.getCoupon();

            Date applicableFrom = coupon.getApplicableFrom();
            Date applicableTill = coupon.getApplicableTill();
            Date currentDate = new Date();

            boolean isOutsideApplicableRange = currentDate.before(applicableFrom) || currentDate.after(applicableTill);

            if (isOutsideApplicableRange) {
                throw new CouponValidityExpiredException(couponCode.getCode(), applicableFrom, applicableTill);
            }

            boolean isDeactivated = (coupon.getDeactivatedBy() != null);

            if (isDeactivated) {
                throw new InactiveCouponException(couponCode.getCode());
            }

            if(prevExclusiveCode != null && coupon.isInclusive() == false) {
                throw new MultipleExclusiveCouponsException(prevExclusiveCode, couponCode.getCode());
            }

            if(coupon.isInclusive() == false) {
                prevExclusiveCode = couponCode.getCode();
            }
        }
    }

    /**
     * Every coupon has an acceptable product range and if product span is true that implies total product
     * count should be with in acceptable product range. If product span is false that implies that each
     * selected product should be with in acceptable product range.
     */
    private void validateEachCouponProdAssociation(int productCount, int totalCost, String code,
                                                   Coupon coupon, List<SelectedProduct> products) {
        int productMinCount = coupon.getProductMinCount();
        int productMaxCount = coupon.getProductMaxCount();
        if(coupon.isProductCountSpanApplicable()) {

            boolean isOutSideProductApplicableRange =
                    !(productCount >= productMinCount && productCount <= productMaxCount);

            if(isOutSideProductApplicableRange) {
                throw new ProductSpanCountOutOfRangeException(code, productMinCount, productMaxCount);
            }
        }else{

            for(SelectedProduct selectedProduct : products) {
                int selectedProdCount = selectedProduct.getCount();
                boolean isOutSideSelectedProdApplicableRange =
                        !(selectedProdCount >= productMinCount && selectedProdCount <= productMaxCount);

                if(isOutSideSelectedProdApplicableRange) {
                    throw new ProductCountOutOfRangeException(code,
                            String.valueOf(selectedProduct.getId()), productMinCount, productMaxCount);
                }
            }
        }


        boolean isOutSideTransactionRange =
                (totalCost < coupon.getTransactionMinValue() || totalCost > coupon.getTransactionMaxValue());

        if(isOutSideTransactionRange) {
            throw new TransactionValueOutOfRangeException(
                    code, coupon.getTransactionMinValue(), coupon.getTransactionMaxValue());
        }

        List<Integer> couponDiscountingRuleIds = couponDiscountingRuleDao.getRuleIds(coupon.getId());

        if(couponDiscountingRuleIds.size() == 0) {
            throw new InternalServerErrorException("No existing rules found for coupon : " + coupon.getId());
        }

        boolean foundRule = false;

        for(Integer couponDiscountingRuleId : couponDiscountingRuleIds) {
            foundRule = isRuleApplicable(couponDiscountingRuleId, totalCost, productCount);
            if(foundRule) {
                break;
            }
        }

        if(foundRule == false) {
            throw new InternalServerErrorException("No applicable rules found for coupon : " + coupon.getId());
        }

        outerLoop:
        for(SelectedProduct selectedProduct : products) {
            Integer prodId = selectedProduct.getId();
            Set<Integer> validProductIds = getProductAncestry(prodId);
            validProductIds.add(prodId);
            boolean isCategoryApplicableForCoupon = false;
            for(Integer validProdId : validProductIds) {
                isCategoryApplicableForCoupon = isCategoryApplicableForCoupon(coupon.getId(), validProdId);
                if(isCategoryApplicableForCoupon) {
                    break outerLoop;
                }
            }

            boolean isAnyProductApplicable = couponProductMapDao.isAnyProductApplicable(coupon.getId(), validProductIds);
            if(isAnyProductApplicable == false) {
                throw new InapplicableCouponException(code, String.valueOf(prodId));
            }
        }
    }

    private int getProductsTotalCost(List<SelectedProduct> products) {
        int totalCost = 0;
        for(SelectedProduct selectedProduct:products) {
            int prodId = selectedProduct.getId();
            Product product = productDao.find(prodId);
            totalCost += product.getUnitPrice()*selectedProduct.getCount();
        }
        return totalCost;
    }

    private boolean isCategoryApplicableForCoupon(int couponId, Integer prodId) {
        Product product = productDao.find(prodId);
        Category category = product.getCategory();
        if(category == null) {
            return false;
        }
        Set<Integer> applicableCategoryIds = getCategoryAncestry(category.getId());
        applicableCategoryIds.add(category.getId());
        return couponCategoryMapDao.isAnyCategoryApplicable(couponId, applicableCategoryIds);
    }

    private Set<Integer> getCategoryAncestry(int categoryId) {
        Set<Integer> categoryAncestryIds = new HashSet<>();
        int totalSize = categoryDao.getCountOfCategories();
        int count = 0;
        while(true) {
            Integer obj = categoryDao.getParentCategoryId(categoryId);
            if(obj == null) break;
            categoryId = obj;
            categoryAncestryIds.add(obj);
            if(count > totalSize) {
                throw new InternalServerErrorException("Possible circular hierarchy detected for category : " + categoryId);
            }
            count++;
        }
        return categoryAncestryIds;
    }

    private Set<Integer> getProductAncestry(Integer productId) {
        Set<Integer> prodAncestryIds = new HashSet<>();
        int totalSize = productDao.getCountOfProducts();

        int count = 0;
        while(true) {
            productId = productDao.getParentProdId(productId);
            if(productId == null) break;
            prodAncestryIds.add(productId);
            if(count > totalSize) {
                throw new InternalServerErrorException("Possible circular hierarchy detected for product : " + productId);
            }
            count++;
        }
        return prodAncestryIds;
    }

    /**
     * Checks for a valid interval for the given transactionValue or the productCount. If it finds then that implies that
     * the rule has successfully found an discount interval.
     * If it can't find any interval for the given transactionValue or the productCount a further check is made to find
     * if any intervals are mentioned. If no intervals are mentioned it implies that default values specified in rule
     * should be used.
     */
    private boolean isRuleApplicable(Integer couponDiscountingRuleId, int totalCost, int productCount) {
        boolean isRuleApplicable = false;
        try{
            couponDiscountingIntervalRuleDao.getDiscount(couponDiscountingRuleId, totalCost, productCount);
            isRuleApplicable = true;
        }catch (NoResultException e) {
            Integer intervalCount = couponDiscountingIntervalRuleDao.getIntervalCount(couponDiscountingRuleId);

            if (intervalCount == 0) {
                isRuleApplicable = true;
            }
        }

        return isRuleApplicable;
    }

    /**
     * Checks if all the listed codes are registered and active.
     */
    private void validateCodes(String[] codes) {
        List<CouponCode> couponCodeList = couponCodeDao.getCouponCodes(codes);
        Map<String, CouponCode> CouponCodeMap = new HashMap<>(); // Code, CouponCode

        for(CouponCode code : couponCodeList) {
            CouponCodeMap.put(code.getCode(), code);
            if(code.getDeactivatedBy() != null) {
                throw new InactiveCouponException(code.getCode());
            }
        }

        for(String code : codes) {
            if(CouponCodeMap.get(code) == null) {
                throw new InvalidCouponException(code);
            }

        }
    }

    /**
     * Checks if The given Product Ids are valid or not.
     */
    private void validateProducts(Integer[] productIds) {
        List<Product> availableProducts = productDao.getProductsForIds(productIds);
        Map<Integer, Product> prodIdToProdMap = new HashMap<>();      //productId : Product
        for(Product product : availableProducts) {
            prodIdToProdMap.put(product.getId(), product);
        }

        for(Integer selectedProdId : productIds) {
           if(prodIdToProdMap.get(selectedProdId) == null) {
               throw new InvalidProductException(String.valueOf(selectedProdId));
           }
        }
    }

    /**
     * Checks if the given User Id is valid or not
     */
    private void validateConsumer(int userId) {
        User user = userDao.find(userId);

        if(user == null) {
            throw new InvalidConsumerException(String.valueOf(userId));
        }
    }

    private void createCouponRequestAudit(CouponDiscountRequest couponDiscountRequest,
                                          int userId, String[] codes, List<SelectedProduct> selectedProducts) {
        CouponDiscountRequestAudit cdrAudit = new CouponDiscountRequestAudit();
        cdrAudit.setCouponDiscountRequest(couponDiscountRequest);
        User user = userDao.find(userId);
        cdrAudit.setUser(user);
        cdrAudit.setCreatedOn(new Date());
        cdrAudit.setUserPhone(user.getPhoneNumber());
        couponDiscountRequestAuditDao.create(cdrAudit);

        for(int index = 0 ; index < codes.length ; index++) {
            CouponDiscountRequestCodeAudit cdrCodeAudit = new CouponDiscountRequestCodeAudit();
            CouponCode couponCode = couponCodeDao.getCouponCode(codes[index]);
            cdrCodeAudit.setCouponCode(couponCode);
            cdrCodeAudit.setCouponDiscReqAudit(cdrAudit);
            cdrCodeAudit.setStatus(CouponDiscountRequestStatus.LOCKED);
            cdrCodeAudit.setCreatedOn(new Date());
            couponDiscountReqCodeAuditDao.create(cdrCodeAudit);
        }

        for(int index = 0 ; index < selectedProducts.size() ; index++) {
            SelectedProduct selectedProduct = selectedProducts.get(index);
            CouponDiscountReqProdAudit cdrProdAudit = new CouponDiscountReqProdAudit();
            cdrProdAudit.setCouponDiscReqAudit(cdrAudit);
            Product product = productDao.find(selectedProduct.getId());
            cdrProdAudit.setProduct(product);
            cdrProdAudit.setProductCount(selectedProduct.getCount());
            cdrProdAudit.setProductUnitPrice(product.getUnitPrice());
            couponDiscountReqProdAuditDao.create(cdrProdAudit);
        }
    }

    private CouponDiscountRequest createCouponRequest(int userId, String[] codes, List<SelectedProduct> selectedProducts) {

        CouponDiscountRequest cdr = new CouponDiscountRequest();
        cdr.setCompleted(false);
        User user = userDao.find(userId);
        cdr.setUser(user);
        cdr.setUserPhone(user.getPhoneNumber());
        cdr.setLatestUpdatedOn(new Date());
        couponDiscountRequestDao.create(cdr);

        for(int index = 0 ; index < codes.length ; index++) {
            CouponDiscountRequestCode cdrCode = new CouponDiscountRequestCode();
            CouponCode couponCode = couponCodeDao.getCouponCode(codes[index]);
            cdrCode.setCouponCode(couponCode);
            cdrCode.setCouponDiscountRequest(cdr);
            cdrCode.setStatus(CouponDiscountRequestStatus.LOCKED);
            cdrCode.setCreatedOn(new Date());
            couponDiscountRequestCodeDao.create(cdrCode);
        }

        for(int index = 0 ; index < selectedProducts.size() ; index++) {
            SelectedProduct selectedProduct = selectedProducts.get(index);
            Product product = productDao.find(selectedProduct.getId());
            CouponDiscountRequestProduct cdrProduct = new CouponDiscountRequestProduct();
            cdrProduct.setCouponDiscountRequest(cdr);
            cdrProduct.setProduct(product);
            cdrProduct.setProductCount(selectedProduct.getCount());
            cdrProduct.setProductUnitPrice(product.getUnitPrice());
            couponDiscountReqProdDao.create(cdrProduct);
        }

        return cdr;
    }

    @Override
    public void updateCouponDiscountRequest(CouponDiscountRequestUpdateReq request) {
        // TODO Complete Implementation
    }

    @Override
    public void deleteCouponDiscountRequest(Integer cdrId) {
        // TODO Complete Implementation
    }

    @Override
    public ApplicableDiscountResp getCurrentApplicableDiscount(Integer cdrId) {

        boolean isAnyCodeRejected = couponDiscountRequestCodeDao.isCodeRejected(cdrId);

        if(isAnyCodeRejected) {
            throw new InvalidCouponDiscountRequestException(String.valueOf(cdrId));
        }

        List<String> couponCodes = couponDiscountRequestCodeDao.getCouponCodes(cdrId);
        String[] codes =   couponCodes.toArray(new String[couponCodes.size()]);
        ApplicableDiscountResp applicableDiscountResp = new ApplicableDiscountResp();

        if(codes.length == 0) {
            return applicableDiscountResp;
        }

        validateCoupons(codes);

        List<ProductVo> productVos = couponDiscountReqProdDao.getProductVos(cdrId);
        int totalDiscount = calculateDiscount(codes, productVos);
        applicableDiscountResp.setDiscountAmount(totalDiscount);
        return applicableDiscountResp;
    }

    /**
     * For all the given codes discount is calculated.
     */
    private int calculateDiscount(String[] codes, List<ProductVo> productVos) {
        int totalDiscount = 0;

        for(String code : codes) {
            CouponCode couponCode = couponCodeDao.getCouponCode(code);
            Coupon coupon = couponCode.getCoupon();
            totalDiscount += getDiscountForCoupon(coupon.getId(), productVos);
        }

        return totalDiscount;
    }

    /**
     * For each Rule a check is made to see if any Interval is matching the product Count or Transaction value. If
     * such a requirement is met then discount is calculated from the interval discount.
     * If not intervals are specified for a rule then the rule's default discount is given.
     */
    private int getDiscountForCoupon(int couponId, List<ProductVo> productVos) {
        List<Integer> couponDiscountingRuleIds = couponDiscountingRuleDao.getRuleIds(couponId);
        int discountForCoupon = 0;
        boolean foundDiscount = false;

        int totalCost = 0;
        int productCount = 0;
        for (ProductVo productVo : productVos) {
            totalCost += productVo.getUnitCost() * productVo.getCount();
            productCount += productVo.getCount();
        }

        for(Integer couponDiscRuleId:couponDiscountingRuleIds) {
            Integer discount;
            CouponDiscountingRule couponDiscountingRule = couponDiscountingRuleDao.find(couponDiscRuleId);
            try {
                discount = couponDiscountingIntervalRuleDao.getDiscount(couponDiscRuleId, totalCost, productCount);
                foundDiscount = true;
                if(couponDiscountingRule.getCouponDiscRuleType().equals(CouponDiscountingRuleType.FLAT)) {
                    discountForCoupon =  discount;
                }
                else if(couponDiscountingRule.getCouponDiscRuleType().equals(CouponDiscountingRuleType.PERCENTAGE)) {
                    discountForCoupon = totalCost * (discount / 100);
                }
            }catch (NoResultException e) {
                Integer intervalCount = couponDiscountingIntervalRuleDao.getIntervalCount(couponDiscRuleId);
                if (intervalCount == 0) {
                    foundDiscount = true;
                    if(couponDiscountingRule.getCouponDiscRuleType().equals(CouponDiscountingRuleType.FLAT)) {
                        discountForCoupon = couponDiscountingRule.getDiscountFlatAmount();
                    }
                    else if(couponDiscountingRule.getCouponDiscRuleType().equals(CouponDiscountingRuleType.PERCENTAGE)) {
                        discountForCoupon =  totalCost * (couponDiscountingRule.getDiscountPercentage() / 100);
                    }
                }
            }
            if(foundDiscount) {
                break;
            }
        }
        return  discountForCoupon;
    }

    private int getProductCount(List<SelectedProduct> selectedProducts) {
        int productCount = 0;

        for(SelectedProduct product : selectedProducts) {
            productCount += product.getCount();
        }

        return productCount;
    }
}
