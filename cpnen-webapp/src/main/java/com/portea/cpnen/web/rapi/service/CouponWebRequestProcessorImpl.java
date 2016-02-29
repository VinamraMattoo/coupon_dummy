package com.portea.cpnen.web.rapi.service;

import com.portea.cpnen.dao.*;
import com.portea.cpnen.domain.*;
import com.portea.cpnen.domain.CouponDiscountRequestStatus;
import com.portea.cpnen.domain.Package;
import com.portea.cpnen.rapi.domain.*;
import com.portea.cpnen.web.rapi.domain.*;
import com.portea.cpnen.web.rapi.exception.CodeCreationException;
import com.portea.cpnen.web.rapi.exception.CouponCreationException;
import com.portea.cpnen.web.rapi.exception.CouponUpdateException;
import com.portea.dao.JpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.*;

@Stateless
public class CouponWebRequestProcessorImpl implements CouponWebRequestProcessor {

    private static final Integer DEAULT_OFFSET = 0;
    private static final Integer NUMBER_OF_DAYS = 7;
    private static final Integer NUMBER_OF_WEEKS = 4;
    private static final Integer START_RANGE = 250;
    private static final Integer MAXIMUM_DISCOUNT_VALUE = 10000;
    private static final Integer DEAULT_LIMIT = 10;
    private static final Integer MAX_RECORDS = 150;
    private static final String DELIMITER = ",";
    private static final String COUPON_ALREADY_PUBLISHED = "Coupon is already published by ";
    private static final String COUPON_IS_NOT_PUBLISHED = "Coupon is not published";
    private static final String ASC = "asc";
    private static final String DESC = "desc";
    @Inject
    @JpaDao
    UserDao userDao;

    @Inject
    @JpaDao
    RoleDao roleDao;

    @Inject
    @JpaDao
    UserRoleMappingDao userRoleMappingDao;

    @Inject
    @JpaDao
    CouponDao couponDao;

    @Inject
    @JpaDao
    CouponCodeDao couponCodeDao;

    @PersistenceContext(unitName = "cpnenPU")
    private EntityManager entityManager;

    @Inject
    @JpaDao
    CouponProductAdapterMappingDao couponProductAdapterMappingDao;

    @Inject
    @JpaDao
    CouponBrandMappingDao couponBrandMappingDao;

    @Inject
    @JpaDao
    CouponAreaMappingDao couponAreaMappingDao;

    @Inject
    @JpaDao
    CouponReferrerMappingDao couponReferrerMappingDao;

    @Inject
    @JpaDao
    BrandDao brandDao;

    @Inject
    @JpaDao
    AreaDao areaDao;

    @Inject
    @JpaDao
    ReferrerDao referrerDao;

    @Inject
    @JpaDao
    private ProductAdapterDao productAdapterDao;

    @Inject
    @JpaDao
    CouponDiscountingRuleDao couponDiscountingRuleDao;

    @Inject
    @JpaDao
    ServiceDao serviceDao;

    @Inject
    @JpaDao
    PackageDao packageDao;

    @Inject
    @JpaDao
    CouponCodeReservationDao couponCodeReservationDao;

    @Inject
    @JpaDao
    CouponProductAdapterMappingAuditDao couponProductAdapterMappingAuditDao;

    @Inject
    @JpaDao
    CouponAuditDao couponAuditDao;

    @Inject
    @JpaDao
    CouponBrandMappingAuditDao couponBrandMappingAuditDao;

    @Inject
    @JpaDao
    CouponAreaMappingAuditDao couponAreaMappingAuditDao;

    @Inject
    @JpaDao
    CouponReferrerMappingAuditDao couponReferrerMappingAuditDao;

    @Inject
    @JpaDao
    CouponCoreAuditDao couponCoreAuditDao;

    @Inject
    @JpaDao
    CouponDiscountingRuleAuditDao couponDiscountingRuleAuditDao;

    @Inject @JpaDao
    private CouponDiscountDao couponDiscountDao;

    @Inject @JpaDao
    private CouponDiscountRequestDao couponDiscountRequestDao;

    private static final Logger LOG = LoggerFactory.getLogger(CouponWebRequestProcessorImpl.class);

    @Override
    public Coupon createCoupon(Integer userId, CouponCRUDRequest couponCRUDRequest) {

        validateCouponName(couponCRUDRequest.getName());

        validateCouponAttributes(couponCRUDRequest);

        if(!validateUserRole(userId, couponCRUDRequest.getCategory())) {
            throw new CouponCreationException("User cannot create a department specific coupon to which he isn't assigned to");
        }

        Coupon coupon = new Coupon();

        coupon.setName(couponCRUDRequest.getName());
        coupon.setCategory(couponCRUDRequest.getCategory());
        coupon.setApplicationType(couponCRUDRequest.getApplicationType());
        coupon.setApplicableFrom(couponCRUDRequest.getApplicableFrom());

        coupon.setApplicableTill(couponCRUDRequest.getApplicableTill());
        coupon.setTransactionMaxValue(couponCRUDRequest.getTransactionValMax());
        coupon.setTransactionMinValue(couponCRUDRequest.getTransactionValMin());

        coupon.setDescription(couponCRUDRequest.getDescription());
        coupon.setInclusive(couponCRUDRequest.getInclusive());

        coupon.setActorType(couponCRUDRequest.getActorType());
        coupon.setContextType(couponCRUDRequest.getContextType());

        coupon.setDiscountAmountMin(couponCRUDRequest.getDiscountAmountMin());
        coupon.setDiscountAmountMax(couponCRUDRequest.getDiscountAmountMax());

        coupon.setGlobal(couponCRUDRequest.getGlobal());
        coupon.setIsForAllProducts(couponCRUDRequest.getIsAllProducts());
        coupon.setIsForAllAreas(couponCRUDRequest.getIsAllAreas());
        coupon.setIsForAllBrands(couponCRUDRequest.getIsAllBrands());
        coupon.setIsForAllB2B(couponCRUDRequest.getIsB2B());
        coupon.setIsForAllB2C(couponCRUDRequest.getIsB2C());

        coupon.setNthTime(couponCRUDRequest.getNthTime());
        coupon.setNthTimeRecurring(
                (couponCRUDRequest.getNthTimeRecurring() == null) ?
                        false : couponCRUDRequest.getNthTimeRecurring());
        coupon.setApplicableUseCount(couponCRUDRequest.getApplicableUseCount());

        User user = userDao.find(userId);
        coupon.setCreatedBy(user);

        if (couponCRUDRequest.getPublished()) {
            coupon.setPublishedBy(user);
            coupon.setPublishedOn(new Date());
        }

        Date createdOn = new Date();
        coupon.setLastUpdatedBy(user);
        coupon.setLastUpdatedOn(createdOn);

        coupon.setCreatedOn(createdOn);
        coupon = couponDao.create(coupon);

        CouponAudit couponAudit = createCouponAudit(coupon, false, false, false, false, false, false);

        createCouponProductMapping(couponCRUDRequest, coupon, couponAudit, false);

        createCouponBrandMapping(couponCRUDRequest, coupon, couponAudit, false);

        createCouponAreaMapping(couponCRUDRequest, coupon, couponAudit, false);

        createCouponReferrerMapping(couponCRUDRequest, coupon, couponAudit, false);

        createCouponDiscountRule(couponCRUDRequest, coupon, user, couponAudit, false);

        return coupon;
    }

    private void createCouponCoreAudit(Coupon coupon, CouponCRUDRequest couponCRUDRequest, CouponAudit couponAudit) {
        CouponCoreAudit couponCoreAudit = new CouponCoreAudit();
        couponCoreAudit.setCouponAudit(couponAudit);
        couponCoreAudit.setName(couponCRUDRequest.getName());
        couponCoreAudit.setDescription(couponCRUDRequest.getDescription());
        couponCoreAudit.setActorType(couponCRUDRequest.getActorType());
        couponCoreAudit.setApplicableFrom(couponCRUDRequest.getApplicableFrom());
        couponCoreAudit.setApplicableTill(couponCRUDRequest.getApplicableTill());
        couponCoreAudit.setApplicableUseCount(couponCRUDRequest.getApplicableUseCount());
        couponCoreAudit.setContextType(couponCRUDRequest.getContextType());
        couponCoreAudit.setDeactivatedOn(coupon.getDeactivatedOn());
        couponCoreAudit.setDeactivatedBy(coupon.getDeactivatedBy());
        couponCoreAudit.setCategory(couponCRUDRequest.getCategory());
        couponCoreAudit.setTransactionMaxValue(coupon.getTransactionMaxValue());
        couponCoreAudit.setTransactionMinValue(coupon.getTransactionMinValue());
        couponCoreAudit.setDiscountAmountMax(couponCRUDRequest.getDiscountAmountMax());
        couponCoreAudit.setDiscountAmountMin(couponCRUDRequest.getDiscountAmountMin());
        couponCoreAudit.setGlobal(couponCRUDRequest.getGlobal());
        couponCoreAudit.setIsForAllProducts(couponCRUDRequest.getIsAllProducts());
        couponCoreAudit.setIsForAllAreas(couponCRUDRequest.getIsAllAreas());
        couponCoreAudit.setIsForAllBrands(couponCRUDRequest.getIsAllBrands());
        couponCoreAudit.setIsForAllB2B(couponCRUDRequest.getIsB2B());
        couponCoreAudit.setIsForAllB2C(couponCRUDRequest.getIsB2C());
        couponCoreAudit.setInclusive(couponCRUDRequest.getInclusive());
        couponCoreAudit.setLastUpdatedBy(coupon.getLastUpdatedBy());
        couponCoreAudit.setLastUpdatedOn(couponCRUDRequest.getLastUpdatedOn());
        couponCoreAudit.setNthTime(couponCRUDRequest.getNthTime());
        couponCoreAudit.setNthTimeRecurring(couponCRUDRequest.getNthTimeRecurring());
        couponCoreAudit.setApplicationType(couponCRUDRequest.getApplicationType());

        couponCoreAuditDao.create(couponCoreAudit);
        couponAudit.setCoreUpdate(true);

    }

    private CouponAudit createCouponAudit(Coupon coupon, boolean isUpdatedAreaMapping, boolean isUpdatedReferrersMapping, boolean isUpdatedProductAdapterMapping,
                                          boolean isBrandMappingUpdated, boolean isDiscountingRuleUpdated, boolean isCouponCoreUpdate) {
        CouponAudit couponAudit = new CouponAudit();

        couponAudit.setCoupon(coupon);
        couponAudit.setBrandsUpdate(isBrandMappingUpdated);
        couponAudit.setAreasUpdate(isUpdatedAreaMapping);
        couponAudit.setReferrerUpdate(isUpdatedReferrersMapping);
        couponAudit.setProductsUpdate(isUpdatedProductAdapterMapping);
        couponAudit.setDiscountingRuleUpdate(isDiscountingRuleUpdated);
        couponAudit.setCoreUpdate(isCouponCoreUpdate);
        couponAudit.setCreatedOn(coupon.getCreatedOn());

        couponAuditDao.create(couponAudit);

        return couponAudit;
    }

    /**
     * Validate if a user is privileged enough to do operations( like create/update/deactivate a coupon) on the given departmental category
     */
    private boolean validateUserRole(Integer userId, CouponCategory couponCategory) {
        boolean isValid = false;
        List<Integer> assignedRoles = userRoleMappingDao.getUserRoleIds(userId);
        List<String> categoryRoleNames = roleDao.getRoles(assignedRoles);
        for (String categoryRoleName : categoryRoleNames) {
            switch (categoryRoleName) {
                case "Coupon Admin":
                    isValid = true;
                    break;

                case "Coupon Manager Sales":
                    if(categoryRoleName.toUpperCase().contains(couponCategory.name()))
                        isValid = true;

                    break;

                case "Coupon Manager Engagement":
                    if(categoryRoleName.toUpperCase().contains(couponCategory.name()))
                        isValid = true;

                    break;

                case "Coupon Manager Marketing":
                    if(categoryRoleName.toUpperCase().contains(couponCategory.name()))
                        isValid = true;

                    break;

                case "Coupon Manager Ops":
                    if(categoryRoleName.toUpperCase().contains(couponCategory.name().toUpperCase()))
                        isValid = true;

            }
        }
        return isValid;
    }

    private void validateCouponAttributes(CouponCRUDRequest couponCRUDRequest) {
        Boolean published = couponCRUDRequest.getPublished();
        if (published != null && published) {

            validateRequest(couponCRUDRequest);

            validateDiscount(couponCRUDRequest.getDiscountAmountMin(), couponCRUDRequest.getDiscountAmountMax());

            validateTransactionLimits(couponCRUDRequest.getTransactionValMin(), couponCRUDRequest.getTransactionValMax());

            validateApplicableRange(couponCRUDRequest.getApplicableFrom(), couponCRUDRequest.getApplicableTill());

            validateProductMapping(couponCRUDRequest.getGlobal(), couponCRUDRequest.getIsAllProducts(), couponCRUDRequest.getProductMapping());

            validateBrandMapping(couponCRUDRequest.getGlobal(), couponCRUDRequest.getIsAllBrands(), couponCRUDRequest.getBrandMapping());

            validateAreaMapping(couponCRUDRequest.getGlobal(), couponCRUDRequest.getIsAllAreas(), couponCRUDRequest.getAreaMapping());

            validateReferrerMapping(couponCRUDRequest.getGlobal(), couponCRUDRequest.getReferrerMapping());

            validateApplicationTypeRequirements(couponCRUDRequest.getApplicationType(),
                    couponCRUDRequest.getNthTime(), couponCRUDRequest.getApplicableUseCount());

            validateDiscountRule(couponCRUDRequest.getDiscountRule());

            removeUnUsedData(couponCRUDRequest);
        }
        // if draft mode coupon then just validate the discount amount max/min, transaction max/min, applicable from/till and discount rule values
        else if(published != null && !published) {

            //validate Description length
            if(couponCRUDRequest.getDescription().length() > 512) {
                throw new CouponCreationException("Coupon Description cannot exceed 512 characters");
            }

            validateDiscount(couponCRUDRequest.getDiscountAmountMin(), couponCRUDRequest.getDiscountAmountMax());

            validateTransactionLimits(couponCRUDRequest.getTransactionValMin(), couponCRUDRequest.getTransactionValMax());

            validateApplicableRange(couponCRUDRequest.getApplicableFrom(), couponCRUDRequest.getApplicableTill());

            //validate invalid product mappings
            for (CouponCRUDRequest.ProductMapping productMapping : couponCRUDRequest.getProductMapping()) {
                Integer prodId = productMapping.getProductId();

                if (productMapping.getType().equals(ProductType.SERVICE)) {

                    Service service = serviceDao.find(prodId);
                    if (service == null) {
                        throw new CouponCreationException("No service found for the specified id " + productMapping.getProductId() );
                    }

                    if(service != null && !service.getName().equalsIgnoreCase(productMapping.getName())) {
                        throw new CouponCreationException("Service id and Service name do not match");
                    }

                    if (service.getSubService()) {
                        throw new CouponCreationException("Selected service is a sub-service and it cannot be mapped with a coupon");
                    }

                } else if (productMapping.getType().equals(ProductType.PACKAGE)) {

                    Package aPackage = packageDao.find(prodId);
                    if (aPackage == null ) {
                        throw new CouponCreationException("No package found for the specified id " + productMapping.getProductId() );
                    }
                    if(aPackage != null && !aPackage.getName().equalsIgnoreCase(productMapping.getName())) {
                        throw new CouponCreationException("Package id and Package name do not match");
                    }
                    if (aPackage.getDeleted()) {
                        throw new CouponCreationException("Package specified with id " + productMapping.getProductId() + " is discontinued");
                    }

                }
            }

            //validate invalid brand mappings
            for (CouponCRUDRequest.BrandMapping brandMapping : couponCRUDRequest.getBrandMapping()) {

                Integer brandId = brandMapping.getBrandId();

                if (brandId == null) {
                    throw new CouponCreationException("Brand id must be specified");
                }

                Brand brand = brandDao.find(brandId);

                if (brand == null) {
                    throw new CouponCreationException("No brand found for the specified id " + brandId);
                }
            }

            //validate referrer mappings
            for (CouponCRUDRequest.ReferrerMapping referrerMapping : couponCRUDRequest.getReferrerMapping()) {
                Integer referrerId = referrerMapping.getReferrerId();

                Referrer referrer = referrerDao.find(referrerId);
                if (referrer == null) {
                    throw new CouponCreationException("No referrer source found for the specified id " + referrerMapping.getReferrerId() );
                }
                if(referrer != null && !referrer.getName().equalsIgnoreCase(referrerMapping.getName())) {
                    throw new CouponCreationException("Referrer id and Referrer name do not match");
                }
            }

            // validate area mappings
            for (CouponCRUDRequest.AreaMapping areaMapping : couponCRUDRequest.getAreaMapping()) {

                Integer areaId = areaMapping.getAreaId();

                if (areaId == null) {
                    throw new CouponCreationException("Area id must be specified");
                }

                Area area = areaDao.find(areaId);

                if (area == null) {
                    throw new CouponCreationException("No area found for the specified id " + areaId);
                }
            }

            validateDiscountRule(couponCRUDRequest.getDiscountRule());
        }
    }

    private void removeUnUsedData(CouponCRUDRequest couponCRUDRequest) {
        CouponApplicationType type = couponCRUDRequest.getApplicationType();

        if (type != null) {

            switch (type) {
                case NTH_TIME:
                    couponCRUDRequest.setApplicableUseCount(null);
                    break;
                case MANY_TIMES:
                    couponCRUDRequest.setNthTime(null);
                    couponCRUDRequest.setNthTimeRecurring(null);
                    couponCRUDRequest.setApplicableUseCount(null);
                    break;
                case NTH_TIME_PER_SUBSCRIPTION:
                    couponCRUDRequest.setApplicableUseCount(null);
                    break;
                case NTH_TIME_AB_PER_SUBSCRIPTION:
                    couponCRUDRequest.setApplicableUseCount(null);
                    break;
                case ONE_TIME:
                    couponCRUDRequest.setNthTime(null);
                    couponCRUDRequest.setNthTimeRecurring(null);
                    couponCRUDRequest.setApplicableUseCount(null);
                    break;
                case ONE_TIME_PER_USER_FIFO:
                    couponCRUDRequest.setNthTime(null);
                    couponCRUDRequest.setNthTimeRecurring(null);
                    break;
                case ONE_TIME_PER_USER:
                    couponCRUDRequest.setNthTime(null);
                    couponCRUDRequest.setNthTimeRecurring(null);
                    couponCRUDRequest.setApplicableUseCount(null);
                    break;
            }
        }
    }

    private void  validateRequest(CouponCRUDRequest request) {
        String nullParam = request.inspectNullParameters();

        if (nullParam != null) {
            throw new CouponCreationException("Parameter "+nullParam+" is incomplete");
        }

        if(request.getDescription().length() > 512) {
            throw new CouponCreationException("Coupon Description cannot exceed 512 characters");
        }

        List<CouponCRUDRequest.ProductMapping> productMappings = request.getProductMapping();
        List<CouponCRUDRequest.BrandMapping> brandMappings = request.getBrandMapping();
        CouponCRUDRequest.DiscountRule discountRule = request.getDiscountRule();

        productMappings.forEach(productMapping -> {

            if (productMapping == null) {
                throw new CouponCreationException("Parameter productMapping is incomplete");
            }
            String prodParam = productMapping.inspectNullParameters();

            if (prodParam != null) {
                throw new CouponCreationException("Parameter " + prodParam + " is incomplete");
            }
        });

        brandMappings.forEach(brandMapping -> {

            if (brandMapping == null) {
                throw new CouponCreationException("Parameter brandMapping is incomplete");
            }
            String brandParam = brandMapping.inspectNullParameters();

            if (brandParam != null) {
                throw new CouponCreationException("Parameter "+brandParam+" is incomplete");
            }
        });

        String discParam = discountRule.inspectNullParameters();

        if (discParam != null) {
            throw new CouponCreationException("Parameter "+discParam+" is incomplete");
        }
    }

    private void validateDiscountRule(CouponCRUDRequest.DiscountRule discountRule) {
        if(discountRule.getRuleType() != null) {
            switch (discountRule.getRuleType()) {
                case PERCENTAGE:
                    if (discountRule.getDiscountPercentage() != null && (discountRule.getDiscountPercentage() < 1 || discountRule.getDiscountPercentage() > 100)) {

                        throw new CouponCreationException("The discount rule percentage must be within [1,100]");
                    }
                    break;
                case FLAT:
                    if (discountRule.getDiscountFlatAmount() != null && discountRule.getDiscountFlatAmount() <= 0) {

                        throw new CouponCreationException("The discount rule flat value must be positive and non-zero");
                    }
                    break;
            }
        }
    }

    private void validateApplicationTypeRequirements(CouponApplicationType type, Integer nthTime, Integer applicableUseCount) {
        switch (type) {
            case NTH_TIME:
            case NTH_TIME_PER_SUBSCRIPTION:
            case NTH_TIME_AB_PER_SUBSCRIPTION:
                if (nthTime == null) {
                    throw new CouponCreationException("For this Coupon type, the value of 'n' must be specified");
                }
                if (nthTime <= 0) {
                    throw new CouponCreationException("The value of 'n' must be a positive non-zero value");
                }
                break;
            case ONE_TIME_PER_USER_FIFO:
                if (applicableUseCount == null) {
                    throw new CouponCreationException("For this coupon type, applicable count must be specified");
                }
                if (applicableUseCount <= 0) {
                    throw new CouponCreationException("The value of applicable use count must be a positive non-zero value");
                }
                break;
        }
    }

    private void validateCouponName(String name) {

        List<Coupon> coupons = couponDao.find(name);

        coupons.forEach(coupon -> {
            if (coupon.isActive()) {
                throw new CouponCreationException("An active coupon with this name already exists");
            }
        });

        if(name == null || name.equalsIgnoreCase("") || name.equalsIgnoreCase(null)) {
            throw new CouponCreationException("Coupon name cannot have null or empty values");
        }

        if(name.length() > 128) {
            throw new CouponCreationException("Coupon name cannot exceed 128 characters");
        }

        if(name.charAt(0)=='-' || name.charAt(0) == '_' || name.charAt(0) == ' ') {
            throw new CouponCreationException("Coupon name cannot start with '_' or '-' or ' '");
        }

        String pattern= "^[a-zA-Z0-9\\-\\_\\s]*$";
        if(!name.matches(pattern)) {
            throw new CouponCreationException("Coupon name can have only alphanumeric and - or _ or space' characters");
        }
    }

    private void validateBrandMapping(Boolean global, Boolean isAllBrands, List<CouponCRUDRequest.BrandMapping> brandMappings) {

        if (!global && (brandMappings.size() == 0 && ! isAllBrands) ){
            throw new CouponCreationException("For a non-global coupon, brand mapping must be specified");
        }
        for (CouponCRUDRequest.BrandMapping brandMapping : brandMappings) {

            Integer brandId = brandMapping.getBrandId();

            if (brandId == null) {
                throw new CouponCreationException("Brand id must be specified");
            }

            Brand brand = brandDao.find(brandId);

            if (brand == null) {
                throw new CouponCreationException("No brand found for the specified id " + brandId);
            }
        }
    }

    private void validateAreaMapping(Boolean global, Boolean isAllAreas, List<CouponCRUDRequest.AreaMapping> areaMappings) {

        if (!global && (areaMappings.size() == 0 && ! isAllAreas)) {
            throw new CouponCreationException("For a non-global coupon, area mapping must be specified");
        }
        for (CouponCRUDRequest.AreaMapping areaMapping : areaMappings) {

            Integer areaId = areaMapping.getAreaId();

            if (areaId == null) {
                throw new CouponCreationException("Area id must be specified");
            }

            Area area = areaDao.find(areaId);

            if (area == null) {
                throw new CouponCreationException("No area found for the specified id " + areaId);
            }
        }
    }

    private void validateProductMapping(Boolean global, Boolean isAllProducts,  List<CouponCRUDRequest.ProductMapping> productMappings) {

        if (!global && (productMappings.size() == 0 && ! isAllProducts)) {

            throw new CouponCreationException("For a non-global coupon, product mapping must be specified");
        }

        for (CouponCRUDRequest.ProductMapping productMapping : productMappings) {
            Integer prodId = productMapping.getProductId();

            if (productMapping.getType().equals(ProductType.SERVICE)) {

                Service service = serviceDao.find(prodId);
                if (service == null) {
                    throw new CouponCreationException("No service found for the specified id " + productMapping.getProductId() );
                }

                if(service != null && !service.getName().equalsIgnoreCase(productMapping.getName())) {
                    throw new CouponCreationException("Service id and Service name do not match");
                }

                if (service.getSubService()) {
                    throw new CouponCreationException("Selected service is a sub-service and it cannot be mapped with a coupon");
                }

            } else if (productMapping.getType().equals(ProductType.PACKAGE)) {

                Package aPackage = packageDao.find(prodId);
                if (aPackage == null ) {
                    throw new CouponCreationException("No package found for the specified id " + productMapping.getProductId() );
                }
                if(aPackage != null && !aPackage.getName().equalsIgnoreCase(productMapping.getName())) {
                    throw new CouponCreationException("Package id and Package name do not match");
                }
                if (aPackage.getDeleted()) {
                    throw new CouponCreationException("Package specified with id " + productMapping.getProductId() + " is discontinued");
                }

            }
        }
    }

    private void validateReferrerMapping(Boolean global, List<CouponCRUDRequest.ReferrerMapping> referrerMappings) {

        if (referrerMappings.size() == 0 && ! global) {

            throw new CouponCreationException("For a non-global coupon, referrer mapping must be specified");
        }

        for (CouponCRUDRequest.ReferrerMapping referrerMapping : referrerMappings) {
            Integer referrerId = referrerMapping.getReferrerId();

            Referrer referrer = referrerDao.find(referrerId);
            if (referrer == null) {
                throw new CouponCreationException("No referrer source found for the specified id " + referrerMapping.getReferrerId() );
            }
            if(referrer != null && !referrer.getName().equalsIgnoreCase(referrerMapping.getName())) {
                throw new CouponCreationException("Referrer id and Referrer name do not match");
            }
        }
    }

    private void validateTransactionLimits(Integer transactionValMin, Integer transactionValMax) {

        if (transactionValMin != null) {
            if (transactionValMin <= 0) {
                throw new CouponCreationException("Coupon transaction minimum value has to be a positive non-zero value");
            }
        }

        if (transactionValMax != null) {
            if (transactionValMax <= 0) {
                throw new CouponCreationException("Coupon transaction maximum value has to be a positive non-zero value");
            }
        }

        if (transactionValMin != null && transactionValMax != null) {

            if (transactionValMin > transactionValMax) {
                throw new CouponCreationException("coupon transaction minimum value is greater than transaction maximum value");
            }

        }

    }

    private void validateApplicableRange(Date applicableFrom, Date applicableTill) {

        if(applicableFrom != null && applicableTill != null) {
            if(applicableFrom.getTime() < 0 || applicableTill.getTime() < 0) {
                throw new CouponCreationException("Negative epoch values at the time of coupon creation are not allowed");
            }
            if (applicableFrom.compareTo(applicableTill) > 0) {
                String msg = "Coupon applicable from is greater than applicable till";
                throw new CouponCreationException(msg);
            }
        }
    }

    private void validateDiscount(Integer discountAmountMin, Integer discountAmountMax) {

        if (discountAmountMin != null && discountAmountMin <= 0) {
            throw new CouponCreationException("Coupon discount minimum value has to be a positive non-zero value");
        }

        if (discountAmountMax != null && discountAmountMax <= 0) {

            throw new CouponCreationException("Coupon discount maximum value has to be a positive non-zero value");
        }

        if (discountAmountMin != null && discountAmountMax != null) {

            if (discountAmountMin > discountAmountMax) {
                throw new CouponCreationException("Coupon discount minimum amount is greater than discount maximum amount");
            }
        }
    }

    private void createCouponDiscountRule(CouponCRUDRequest couponCRUDRequest, Coupon coupon, User user, CouponAudit couponAudit, boolean isUpdated) {
        CouponCRUDRequest.DiscountRule discountRule = couponCRUDRequest.getDiscountRule();

        if (discountRule != null) {
            CouponDiscountingRule couponDiscountingRule = new CouponDiscountingRule();
            setCouponDiscountRule(couponCRUDRequest, couponDiscountingRule, coupon, user);
            couponDiscountingRuleDao.create(couponDiscountingRule);

            if(isUpdated) {
                createCouponDiscountingRuleAudit(couponDiscountingRule, couponAudit, isUpdated);
                couponAudit.setDiscountingRuleUpdate(true);
            }
        }


    }

    private void setCouponDiscountRule(CouponCRUDRequest couponCRUDRequest, CouponDiscountingRule couponDiscountingRule, Coupon coupon, User user) {
        CouponCRUDRequest.DiscountRule discountRule = couponCRUDRequest.getDiscountRule();
        couponDiscountingRule.setCreatedOn(new Date());

        couponDiscountingRule.setCreatedBy(user);
        couponDiscountingRule.setCoupon(coupon);
        couponDiscountingRule.setDescription(discountRule.getDescription());

        couponDiscountingRule.setCouponDiscRuleType(discountRule.getRuleType());
        couponDiscountingRule.setDiscountFlatAmount(discountRule.getDiscountFlatAmount());
        couponDiscountingRule.setDiscountPercentage(discountRule.getDiscountPercentage());
    }

    private void createCouponBrandMapping(CouponCRUDRequest couponCRUDRequest, Coupon coupon, CouponAudit couponAudit, boolean isUpdated) {
        if (couponCRUDRequest.getBrandMapping() != null) {
            for (CouponCRUDRequest.BrandMapping brandMapping : couponCRUDRequest.getBrandMapping()) {
                CouponBrandMapping couponBrandMapping = new CouponBrandMapping();
                Brand brand = brandDao.find(brandMapping.getBrandId());
                couponBrandMapping.setCoupon(coupon);
                couponBrandMapping.setApplicable(true);
                couponBrandMapping.setBrand(brand);

                couponBrandMappingDao.create(couponBrandMapping);

                if(isUpdated) {
                    createCouponBrandMappingAudit(couponBrandMapping, couponAudit, brand, isUpdated);
                    couponAudit.setBrandsUpdate(true);
                }

            }
        }
    }

    private void createCouponAreaMapping(CouponCRUDRequest couponCRUDRequest, Coupon coupon, CouponAudit couponAudit, boolean isUpdated) {
        if (couponCRUDRequest.getAreaMapping() != null) {
            for (CouponCRUDRequest.AreaMapping areaMapping : couponCRUDRequest.getAreaMapping()) {
                CouponAreaMapping couponAreaMapping = new CouponAreaMapping();
                Area area = areaDao.find(areaMapping.getAreaId());
                couponAreaMapping.setCoupon(coupon);
                couponAreaMapping.setApplicable(true);
                couponAreaMapping.setArea(area);

                couponAreaMappingDao.create(couponAreaMapping);

                if(isUpdated) {
                    createCouponAreaMappingAudit(couponAreaMapping, couponAudit, area, isUpdated);
                    couponAudit.setAreasUpdate(true);
                }

            }
        }
    }

    private void createCouponReferrerMapping(CouponCRUDRequest couponCRUDRequest, Coupon coupon, CouponAudit couponAudit, boolean isUpdated) {
        if (couponCRUDRequest.getReferrerMapping() != null) {
            for (CouponCRUDRequest.ReferrerMapping referrerMapping : couponCRUDRequest.getReferrerMapping()) {
                CouponReferrerMapping couponReferrerMapping = new CouponReferrerMapping();
                Referrer referrer = referrerDao.find(referrerMapping.getReferrerId());
                couponReferrerMapping.setCoupon(coupon);
                couponReferrerMapping.setApplicable(true);
                couponReferrerMapping.setReferrer(referrer);

                couponReferrerMappingDao.create(couponReferrerMapping);

                if(isUpdated) {
                    createCouponReferrerMappingAudit(couponReferrerMapping, couponAudit, referrer, isUpdated);
                    couponAudit.setReferrerUpdate(true);
                }

            }
        }
    }

    private void createCouponProductMapping(CouponCRUDRequest couponCRUDRequest, Coupon coupon, CouponAudit couponAudit, boolean isUpdated) {
        if (couponCRUDRequest.getProductMapping() != null) {
            for (CouponCRUDRequest.ProductMapping productMapping : couponCRUDRequest.getProductMapping()) {

                CouponProductAdapterMapping couponProductAdapterMapping = new CouponProductAdapterMapping();

                couponProductAdapterMapping.setApplicable(true);
                couponProductAdapterMapping.setCoupon(coupon);

                ProductAdapter productAdapter;
                try {
                    productAdapter = productAdapterDao.
                            getProductAdapter(productMapping.getProductId(), productMapping.getType());
                } catch (NoResultException e) {
                    productAdapter = productAdapterDao.
                            create(productMapping.getProductId(), productMapping.getType(), new Date(), productMapping.getName());
                }

                couponProductAdapterMapping.setProductAdapter(productAdapter);

                couponProductAdapterMappingDao.create(couponProductAdapterMapping);

                if(isUpdated){
                    createCouponProductAdapterMappingAudit(couponProductAdapterMapping, couponAudit, productAdapter, isUpdated);
                    couponAudit.setProductsUpdate(true);
                }

            }
        }
    }

    private void createCouponProductAdapterMappingAudit(CouponProductAdapterMapping couponProductAdapterMapping, CouponAudit couponAudit, ProductAdapter productAdapter, boolean isUpdated) {
        CouponProductAdapterMappingAudit couponProductAdapterMappingAudit = new CouponProductAdapterMappingAudit();
        couponProductAdapterMappingAudit.setCouponAudit(couponAudit);
        couponProductAdapterMappingAudit.setProductAdapter(productAdapter);
        couponProductAdapterMappingAudit.setApplicable(couponProductAdapterMapping.getApplicable());

        couponProductAdapterMappingAuditDao.create(couponProductAdapterMappingAudit);

        couponAudit.setProductsUpdate(isUpdated);

    }

    private void createCouponDiscountingRuleAudit(CouponDiscountingRule couponDiscountingRule, CouponAudit couponAudit, boolean isUpdated) {
        CouponDiscountingRuleAudit couponDiscountingRuleAudit = new CouponDiscountingRuleAudit();
        couponDiscountingRuleAudit.setCouponAudit(couponAudit);
        couponDiscountingRuleAudit.setCreatedOn(couponDiscountingRule.getCreatedOn());
        couponDiscountingRuleAudit.setDeactivatedOn(couponDiscountingRule.getDeactivatedOn());
        couponDiscountingRuleAudit.setCoupon(couponDiscountingRule.getCoupon());
        couponDiscountingRuleAudit.setDescription(couponDiscountingRule.getDescription());
        couponDiscountingRuleAudit.setCouponDiscRuleType(couponDiscountingRule.getCouponDiscRuleType());
        couponDiscountingRuleAudit.setDiscountFlatAmount(couponDiscountingRule.getDiscountFlatAmount());
        couponDiscountingRuleAudit.setDiscountPercentage(couponDiscountingRule.getDiscountPercentage());
        couponDiscountingRuleAudit.setCreatedBy(couponDiscountingRule.getCreatedBy());
        couponDiscountingRuleAudit.setCreatedOn(couponDiscountingRule.getCreatedOn());

        couponDiscountingRuleAuditDao.create(couponDiscountingRuleAudit);

        couponAudit.setDiscountingRuleUpdate(isUpdated);

    }

    private void createCouponBrandMappingAudit(CouponBrandMapping couponBrandMapping, CouponAudit couponAudit, Brand brand, boolean isUpdated) {
        CouponBrandMappingAudit couponBrandMappingAudit = new CouponBrandMappingAudit();
        couponBrandMappingAudit.setCouponAudit(couponAudit);
        couponBrandMappingAudit.setBrand(brand);
        couponBrandMappingAudit.setApplicable(couponBrandMapping.getApplicable());

        couponBrandMappingAuditDao.create(couponBrandMappingAudit);

        couponAudit.setBrandsUpdate(isUpdated);

    }

    private void createCouponAreaMappingAudit(CouponAreaMapping couponAreaMapping, CouponAudit couponAudit, Area area, boolean isUpdated) {
        CouponAreaMappingAudit couponAreaMappingAudit = new CouponAreaMappingAudit();
        couponAreaMappingAudit.setCouponAudit(couponAudit);
        couponAreaMappingAudit.setArea(area);
        couponAreaMappingAudit.setApplicable(couponAreaMapping.getApplicable());

        couponAreaMappingAuditDao.create(couponAreaMappingAudit);

        couponAudit.setAreasUpdate(isUpdated);

    }

    private void createCouponReferrerMappingAudit(CouponReferrerMapping couponReferrerMapping, CouponAudit couponAudit, Referrer referrer, boolean isUpdated) {
        CouponReferrerMappingAudit couponReferrerMappingAudit = new CouponReferrerMappingAudit();
        couponReferrerMappingAudit.setCouponAudit(couponAudit);
        couponReferrerMappingAudit.setReferrer(referrer);
        couponReferrerMappingAudit.setApplicable(couponReferrerMapping.getApplicable());

        couponReferrerMappingAuditDao.create(couponReferrerMappingAudit);

        couponAudit.setReferrerUpdate(isUpdated);

    }

    @Override
    public CouponVO getCouponVO(Integer couponId) {
        Coupon coupon = couponDao.find(couponId);

        if (coupon == null) {
            return null;
        }
        CouponVO couponVO = new CouponVO();

        couponVO.setCouponId(coupon.getId());
        couponVO.setName(coupon.getName());

        couponVO.setDescription(coupon.getDescription());
        couponVO.setInclusive(coupon.getInclusive());
        couponVO.setApplicableFrom(coupon.getApplicableFrom());
        couponVO.setApplicableTill(coupon.getApplicableTill());

        couponVO.setApplicableUseCount(coupon.getApplicableUseCount());
        couponVO.setCategory(coupon.getCategory());
        couponVO.setApplicationType(coupon.getApplicationType());
        couponVO.setCreatedBy(getUserName(coupon.getCreatedBy()));

        couponVO.setCreatedOn(coupon.getCreatedOn());
        couponVO.setDeactivatedOn(coupon.getDeactivatedOn());
        couponVO.setDeactivatedBy(getUserName(coupon.getDeactivatedBy()));

        couponVO.setTransactionValMin(coupon.getTransactionMinValue());
        couponVO.setTransactionValMax(coupon.getTransactionMaxValue());
        couponVO.setPublishedBy(getUserName(coupon.getPublishedBy()));

        couponVO.setActorType(coupon.getActorType());
        couponVO.setContextType(coupon.getContextType());
        couponVO.setDiscountAmountMin(coupon.getDiscountAmountMin());

        couponVO.setDiscountAmountMax(coupon.getDiscountAmountMax());
        couponVO.setGlobal(coupon.getGlobal());
        couponVO.setIsB2C(coupon.getIsForAllB2C());
        couponVO.setIsB2B(coupon.getIsForAllB2B());
        couponVO.setIsAllAreas(coupon.getIsForAllAreas());
        couponVO.setIsAllBrands(coupon.getIsForAllBrands());
        couponVO.setIsAllProducts(coupon.getIsForAllProducts());
        couponVO.setNthTime(coupon.getNthTime());

        couponVO.setNthTimeRecurring(coupon.getNthTimeRecurring());
        couponVO.setPublishedOn(coupon.getPublishedOn());
        couponVO.setLastUpdatedBy(getUserName(coupon.getLastUpdatedBy()));

        couponVO.setLastUpdatedOn(coupon.getLastUpdatedOn());

        List<CouponProductAdapterMapping> productMappings = couponProductAdapterMappingDao.getMappings(coupon);
        for (CouponProductAdapterMapping mapping : productMappings) {
            ProductAdapter productAdapter = mapping.getProductAdapter();
            couponVO.addProductMapping(productAdapter.getProductId(), productAdapter.getProductType(), productAdapter.getName());
        }

        List<CouponReferrerMapping> referrerMappings = couponReferrerMappingDao.getMappings(coupon);
        for (CouponReferrerMapping mapping : referrerMappings) {
            Referrer referrer = mapping.getReferrer();
            couponVO.addReferrerMapping(referrer.getId(), referrer.getReferrerType(), referrer.getName());
        }

        List<CouponBrandMapping> brandMappings = couponBrandMappingDao.getMappings(coupon);
        for (CouponBrandMapping mapping : brandMappings) {
            Brand brand = mapping.getBrand();
            couponVO.addBrandMapping(brand.getId(), brand.getName());
        }

        List<CouponAreaMapping> areaMappings = couponAreaMappingDao.getMappings(coupon);
        for (CouponAreaMapping mapping : areaMappings) {
            Area area = mapping.getArea();
            couponVO.addAreaMapping(area.getId(), area.getName());
        }

        try {
            CouponDiscountingRule couponDiscountingRule = couponDiscountingRuleDao.getRule(coupon);
            CouponVO.DiscountRule rule = couponVO.getDiscountRule();
            rule.setId(couponDiscountingRule.getId());
            rule.setDiscountPercentage(couponDiscountingRule.getDiscountPercentage());
            rule.setDiscountFlatAmount(couponDiscountingRule.getDiscountFlatAmount());

            rule.setDescription(couponDiscountingRule.getDescription());
            rule.setRuleType(couponDiscountingRule.getCouponDiscRuleType());
            rule.setCreatedOn(couponDiscountingRule.getCreatedOn());

            rule.setCreatedBy(getUserName(couponDiscountingRule.getCreatedBy()));
        } catch (NoResultException e) {
            LOG.debug("No Discounting rules found for coupon " + coupon.getName());
        }

        return couponVO;
    }

    private Integer getUserId(User user) {
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    @Override
    public void updateCoupon(Integer userId, Integer couponId, CouponCRUDRequest couponCRUDRequest) {

        Coupon coupon = getCoupon(couponId);

        if(!validateUserRole(userId, couponCRUDRequest.getCategory())) {
            throw new CouponUpdateException("User cannot create a department specific coupon to which he isn't assigned to");
        }

        Date lastUpdatedDate = couponCRUDRequest.getLastUpdatedOn();

        validateCouponUpdate(coupon, (lastUpdatedDate == null)?null:lastUpdatedDate.getTime(), coupon.getPublishedBy(),
                input -> input != null, COUPON_ALREADY_PUBLISHED+getUserName(coupon.getPublishedBy()));

        validateCouponAttributes(couponCRUDRequest);

        if ( ! couponCRUDRequest.getName().equals(coupon.getName())) {
            validateCouponName(couponCRUDRequest.getName());
        }

        boolean isCouponCoreUpdate = isUpdatedCouponCore(couponCRUDRequest, coupon);

        coupon.setName(couponCRUDRequest.getName());
        coupon.setCategory(couponCRUDRequest.getCategory());
        coupon.setApplicationType(couponCRUDRequest.getApplicationType());
        coupon.setApplicableFrom(couponCRUDRequest.getApplicableFrom());

        coupon.setApplicableTill(couponCRUDRequest.getApplicableTill());
        coupon.setTransactionMaxValue(couponCRUDRequest.getTransactionValMax());
        coupon.setTransactionMinValue(couponCRUDRequest.getTransactionValMin());

        coupon.setDescription(couponCRUDRequest.getDescription());
        coupon.setInclusive(couponCRUDRequest.getInclusive());

        coupon.setActorType(couponCRUDRequest.getActorType());
        coupon.setContextType(couponCRUDRequest.getContextType());


        coupon.setDiscountAmountMin(couponCRUDRequest.getDiscountAmountMin());
        coupon.setDiscountAmountMax(couponCRUDRequest.getDiscountAmountMax());

        coupon.setGlobal(couponCRUDRequest.getGlobal());
        coupon.setIsForAllProducts(couponCRUDRequest.getIsAllProducts());
        coupon.setIsForAllAreas(couponCRUDRequest.getIsAllAreas());
        coupon.setIsForAllBrands(couponCRUDRequest.getIsAllBrands());
        coupon.setIsForAllB2C(couponCRUDRequest.getIsB2B());
        coupon.setIsForAllB2B(couponCRUDRequest.getIsB2C());

        coupon.setNthTime(couponCRUDRequest.getNthTime());
        coupon.setNthTimeRecurring(couponCRUDRequest.getNthTimeRecurring());
        coupon.setApplicableUseCount(couponCRUDRequest.getApplicableUseCount());

        User user = userDao.find(userId);
        coupon.setCreatedBy(user);
        coupon.setLastUpdatedBy(user);
        coupon.setLastUpdatedOn(new Date());

        if (couponCRUDRequest.getPublished()) {
            coupon.setPublishedBy(user);
            coupon.setPublishedOn(new Date());
        }

        couponDao.update(coupon);

        CouponAudit couponAudit = createCouponAudit(coupon, false, false, false, false, false, false);

        updateCouponProductMapping(couponCRUDRequest, coupon, couponAudit);

        updateCouponBrandMapping(couponCRUDRequest, coupon, couponAudit);

        updateCouponAreaMapping(couponCRUDRequest, coupon, couponAudit);

        updateCouponReferrerMapping(couponCRUDRequest, coupon, couponAudit);

        updateCouponDiscountRule(couponCRUDRequest, coupon, user, couponAudit);

        if(isCouponCoreUpdate) {
            createCouponCoreAudit(coupon, couponCRUDRequest, couponAudit);
        }

    }

    /**
     * To avoid concurrent modification of a coupon attributes, every coupon update request by the client must send back
     * the same lastUpdatedOn value which was initially received.
     */
    private void validateCouponUpdate(Coupon coupon, Long lastUpdatedOn,
                                      User input, java.util.function.Predicate<User> predicate, String errMsg) {

        if (predicate.test(input)) {
            throw new CouponUpdateException(errMsg);
        }

        String updateErrorMsg = "Coupon was modified by "
                + getUserName(coupon.getLastUpdatedBy()) + ". Please reload to get the latest data.";
        if (lastUpdatedOn == null  ) {
            if (coupon.getLastUpdatedOn() != null) {
                throw new CouponUpdateException(updateErrorMsg);
            }
        }
        else if( ! lastUpdatedOn.equals(coupon.getLastUpdatedOn().getTime())) {

            throw new CouponUpdateException(updateErrorMsg);
        }

    }

    private void updateCouponDiscountRule(CouponCRUDRequest couponCRUDRequest, Coupon coupon, User user, CouponAudit couponAudit) {
        boolean isUpdated = isUpdatedDiscountingRule(couponCRUDRequest, coupon);
        couponDiscountingRuleDao.delete(coupon);
        createCouponDiscountRule(couponCRUDRequest, coupon, user, couponAudit, isUpdated);
    }

    private void updateCouponBrandMapping(CouponCRUDRequest couponCRUDRequest, Coupon coupon, CouponAudit couponAudit) {
        boolean isUpdated = isUpdatedCouponBrandMapping(couponCRUDRequest, coupon);
        couponBrandMappingDao.delete(coupon);
        createCouponBrandMapping(couponCRUDRequest, coupon, couponAudit, isUpdated);
    }

    private void updateCouponAreaMapping(CouponCRUDRequest couponCRUDRequest, Coupon coupon, CouponAudit couponAudit) {
        boolean isUpdated = isUpdatedCouponAreaMapping(couponCRUDRequest, coupon);
        couponAreaMappingDao.delete(coupon);
        createCouponAreaMapping(couponCRUDRequest, coupon, couponAudit, isUpdated);
    }

    private void updateCouponReferrerMapping(CouponCRUDRequest couponCRUDRequest, Coupon coupon, CouponAudit couponAudit) {
        boolean isUpdated = isUpdatedCouponReferrerMapping(couponCRUDRequest, coupon);
        couponReferrerMappingDao.delete(coupon);
        createCouponReferrerMapping(couponCRUDRequest, coupon, couponAudit, isUpdated);
    }

    private void updateCouponProductMapping(CouponCRUDRequest couponCRUDRequest, Coupon coupon, CouponAudit couponAudit) {
        boolean isUpdated = isUpdatedCouponProductAdapterMapping(couponCRUDRequest, coupon);
        couponProductAdapterMappingDao.delete(coupon);
        createCouponProductMapping(couponCRUDRequest, coupon, couponAudit, isUpdated);
    }

    private boolean isUpdatedCouponBrandMapping(CouponCRUDRequest couponCRUDRequest, Coupon coupon) {

        List<Integer> brandIds = new ArrayList<Integer>();

        List<CouponBrandMapping> brandMappings = couponBrandMappingDao.getMappings(coupon);
        for (CouponBrandMapping mapping : brandMappings) {

            brandIds.add(mapping.getBrand().getId());
        }

        List<CouponCRUDRequest.BrandMapping> updatedBrandMappings  = couponCRUDRequest.getBrandMapping();

        for (CouponCRUDRequest.BrandMapping brandMapping : updatedBrandMappings) {

            if(brandIds.contains(brandMapping.getBrandId())) {
                continue;
            }
            else {
                return true;
            }
        }

        return false;
    }

    private boolean isUpdatedCouponAreaMapping(CouponCRUDRequest couponCRUDRequest, Coupon coupon) {

        List<Integer> areaIds = new ArrayList<Integer>();

        List<CouponAreaMapping> areaMappings = couponAreaMappingDao.getMappings(coupon);
        for (CouponAreaMapping mapping : areaMappings) {

            areaIds.add(mapping.getArea().getId());
        }

        List<CouponCRUDRequest.AreaMapping> updatedAreaMappings  = couponCRUDRequest.getAreaMapping();

        for (CouponCRUDRequest.AreaMapping areaMapping : updatedAreaMappings) {

            if(areaIds.contains(areaMapping.getAreaId())) {
                continue;
            }
            else {
                return true;
            }
        }

        return false;
    }

    private boolean isUpdatedCouponReferrerMapping(CouponCRUDRequest couponCRUDRequest, Coupon coupon) {

        List<Integer> referrerIds = new ArrayList<Integer>();

        List<CouponReferrerMapping> referrerMappings = couponReferrerMappingDao.getMappings(coupon);
        for (CouponReferrerMapping mapping : referrerMappings) {

            referrerIds.add(mapping.getReferrer().getId());
        }

        List<CouponCRUDRequest.ReferrerMapping> updatedReferrerMappings  = couponCRUDRequest.getReferrerMapping();

        for (CouponCRUDRequest.ReferrerMapping referrerMapping : updatedReferrerMappings) {

            if(referrerIds.contains(referrerMapping.getReferrerId())) {
                continue;
            }
            else {
                return true;
            }
        }

        return false;
    }

    private boolean isUpdatedCouponCore(CouponCRUDRequest couponCRUDRequest, Coupon coupon) {

        boolean isUpdated = false;

        if(((coupon.getName() == null && couponCRUDRequest.getName() != null) || coupon.getName() != null && couponCRUDRequest.getName() == null) ||
                (coupon.getName() != null && couponCRUDRequest.getName() != null && !coupon.getName().equals(couponCRUDRequest.getName()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getDescription() == null && couponCRUDRequest.getDescription() != null) || coupon.getDescription() != null && couponCRUDRequest.getDescription() == null) ||
                (coupon.getDescription() != null && couponCRUDRequest.getDescription() != null && !coupon.getDescription().equals(couponCRUDRequest.getDescription())))) {
            isUpdated = true;
        }

        if(((coupon.getDiscountAmountMax() == null && couponCRUDRequest.getDiscountAmountMax() != null) || coupon.getDiscountAmountMax() != null && couponCRUDRequest.getDiscountAmountMax() == null) ||
                (coupon.getDiscountAmountMax() != null && couponCRUDRequest.getDiscountAmountMax() != null && coupon.getDiscountAmountMax().intValue() != couponCRUDRequest.getDiscountAmountMax().intValue())) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getDiscountAmountMin() == null && couponCRUDRequest.getDiscountAmountMin() != null) || coupon.getDiscountAmountMin() != null && couponCRUDRequest.getDiscountAmountMin() == null) ||
                (coupon.getDiscountAmountMin() != null && couponCRUDRequest.getDiscountAmountMin() != null && coupon.getDiscountAmountMin().intValue() != couponCRUDRequest.getDiscountAmountMin().intValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getTransactionMaxValue() == null && couponCRUDRequest.getTransactionValMax() != null) || coupon.getTransactionMaxValue() != null && couponCRUDRequest.getTransactionValMax() == null) ||
                (coupon.getTransactionMaxValue() != null && couponCRUDRequest.getTransactionValMax() != null && coupon.getTransactionMaxValue().intValue() != couponCRUDRequest.getTransactionValMax().intValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getTransactionMinValue() == null && couponCRUDRequest.getTransactionValMin() != null) || coupon.getTransactionMinValue() != null && couponCRUDRequest.getTransactionValMin() == null) ||
                (coupon.getTransactionMinValue() != null && couponCRUDRequest.getTransactionValMin() != null && coupon.getTransactionMinValue().intValue() != couponCRUDRequest.getTransactionValMin().intValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getGlobal() == null && couponCRUDRequest.getGlobal() != null) || coupon.getGlobal() != null && couponCRUDRequest.getGlobal() == null) ||
                (coupon.getGlobal() != null && couponCRUDRequest.getGlobal() != null && coupon.getGlobal().booleanValue() != couponCRUDRequest.getGlobal().booleanValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getIsForAllAreas() == null && couponCRUDRequest.getGlobal() != null) || coupon.getIsForAllAreas() != null && couponCRUDRequest.getIsAllAreas() == null) ||
                (coupon.getIsForAllAreas() != null && couponCRUDRequest.getIsAllAreas() != null && coupon.getIsForAllAreas().booleanValue() != couponCRUDRequest.getIsAllAreas().booleanValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getIsForAllBrands() == null && couponCRUDRequest.getIsAllBrands() != null) || coupon.getIsForAllBrands() != null && couponCRUDRequest.getIsAllBrands() == null) ||
                (coupon.getIsForAllBrands() != null && couponCRUDRequest.getIsAllBrands() != null && coupon.getIsForAllBrands().booleanValue() != couponCRUDRequest.getIsAllBrands().booleanValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getIsForAllProducts() == null && couponCRUDRequest.getIsAllProducts() != null) || coupon.getIsForAllProducts() != null && couponCRUDRequest.getIsAllProducts() == null) ||
                (coupon.getIsForAllProducts() != null && couponCRUDRequest.getIsAllProducts() != null && coupon.getIsForAllProducts().booleanValue() != couponCRUDRequest.getIsAllProducts().booleanValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getIsForAllB2B() == null && couponCRUDRequest.getIsB2B() != null) || coupon.getIsForAllB2B() != null && couponCRUDRequest.getIsB2B() == null) ||
                (coupon.getIsForAllB2B() != null && couponCRUDRequest.getIsB2B() != null && coupon.getIsForAllB2B().booleanValue() != couponCRUDRequest.getIsB2B().booleanValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getIsForAllB2C() == null && couponCRUDRequest.getIsB2C() != null) || coupon.getIsForAllB2C() != null && couponCRUDRequest.getIsB2C() == null) ||
                (coupon.getIsForAllB2C() != null && couponCRUDRequest.getIsB2C() != null && coupon.getIsForAllB2C().booleanValue() != couponCRUDRequest.getIsB2C().booleanValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getInclusive() == null && couponCRUDRequest.getInclusive() != null) || coupon.getInclusive() != null && couponCRUDRequest.getInclusive() == null) ||
                (coupon.getInclusive() != null && couponCRUDRequest.getInclusive() != null && coupon.getInclusive().booleanValue() != couponCRUDRequest.getInclusive().booleanValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getCategory() == null && couponCRUDRequest.getCategory() != null) || coupon.getCategory() != null && couponCRUDRequest.getCategory() == null) ||
                (coupon.getCategory() != null && couponCRUDRequest.getCategory() != null && coupon.getCategory().name() != couponCRUDRequest.getCategory().name()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getApplicationType() == null && couponCRUDRequest.getApplicationType() != null) || coupon.getApplicationType() != null && couponCRUDRequest.getApplicationType() == null) ||
                (coupon.getApplicationType() != null && couponCRUDRequest.getApplicationType() != null && coupon.getApplicationType().name() != couponCRUDRequest.getApplicationType().name()))) {
            isUpdated = true;
        }

        if(!isUpdated && ((coupon.getActorType() == null && couponCRUDRequest.getActorType() != null) || coupon.getActorType() != null && couponCRUDRequest.getActorType() == null) ||
                (coupon.getActorType() != null && couponCRUDRequest.getActorType() != null && coupon.getActorType().name() != couponCRUDRequest.getActorType().name())) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getContextType() == null && couponCRUDRequest.getContextType() != null) || coupon.getContextType() != null && couponCRUDRequest.getContextType() == null) ||
                (coupon.getContextType() != null && couponCRUDRequest.getContextType() != null && coupon.getContextType().name() != couponCRUDRequest.getContextType().name()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getNthTimeRecurring() == null && couponCRUDRequest.getNthTimeRecurring() != null) || coupon.getNthTimeRecurring() != null && couponCRUDRequest.getNthTimeRecurring() == null) ||
                (coupon.getNthTimeRecurring() != null && couponCRUDRequest.getNthTimeRecurring() != null && coupon.getNthTimeRecurring().booleanValue() != couponCRUDRequest.getNthTimeRecurring().booleanValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getNthTime() == null && couponCRUDRequest.getNthTime() != null) || coupon.getNthTime() != null && couponCRUDRequest.getNthTime() == null) ||
                (coupon.getNthTime() != null && couponCRUDRequest.getNthTime() != null && coupon.getNthTime().intValue() != couponCRUDRequest.getNthTime().intValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getApplicableUseCount() == null && couponCRUDRequest.getApplicableUseCount() != null) || coupon.getApplicableUseCount() != null && couponCRUDRequest.getApplicableUseCount() == null) ||
                (coupon.getApplicableUseCount() != null && couponCRUDRequest.getApplicableUseCount() != null && coupon.getApplicableUseCount().intValue() != couponCRUDRequest.getApplicableUseCount().intValue()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getApplicableTill() == null && couponCRUDRequest.getApplicableTill() != null) || coupon.getApplicableTill() != null && couponCRUDRequest.getApplicableTill() == null) ||
                (coupon.getApplicableTill() != null && couponCRUDRequest.getApplicableTill() != null && coupon.getApplicableTill().getSeconds() != couponCRUDRequest.getApplicableTill().getSeconds()))) {
            isUpdated = true;
        }

        if(!isUpdated && (((coupon.getApplicableFrom() == null && couponCRUDRequest.getApplicableFrom() != null) || coupon.getApplicableFrom() != null && couponCRUDRequest.getApplicableFrom() == null) ||
                (coupon.getApplicableFrom() != null && couponCRUDRequest.getApplicableFrom() != null && coupon.getApplicableFrom().getSeconds() != couponCRUDRequest.getApplicableFrom().getSeconds()))) {
            isUpdated = true;
        }

        return isUpdated;
    }

    private boolean isUpdatedDiscountingRule(CouponCRUDRequest couponCRUDRequest, Coupon coupon) {

        CouponDiscountingRule existingCouponDiscountingRule = couponDiscountingRuleDao.getRule(coupon);

        CouponCRUDRequest.DiscountRule couponDiscountingRule = couponCRUDRequest.getDiscountRule();

        if(! (couponDiscountingRule.getRuleType() == null) && !couponDiscountingRule.getRuleType().equals(existingCouponDiscountingRule.getCouponDiscRuleType())) {
            return true;
        }
        else if(!(couponDiscountingRule.getRuleType() == null) && couponDiscountingRule.getRuleType().equals(CouponDiscountingRuleType.FLAT)) {
            if (couponDiscountingRule.getDiscountFlatAmount() != existingCouponDiscountingRule.getDiscountFlatAmount()) {
                return true;
            }
        }
        else if(!(couponDiscountingRule.getRuleType() == null) && couponDiscountingRule.getRuleType().equals(CouponDiscountingRuleType.PERCENTAGE)) {
            if(couponDiscountingRule.getDiscountPercentage() != existingCouponDiscountingRule.getDiscountPercentage()) {
                return true;
            }
        }

        return false;
    }

    private boolean isUpdatedCouponProductAdapterMapping(CouponCRUDRequest couponCRUDRequest, Coupon coupon) {

        List<Integer> productIds = new ArrayList<Integer>();

        List<CouponProductAdapterMapping> productMappings = couponProductAdapterMappingDao.getMappings(coupon);
        for (CouponProductAdapterMapping mapping : productMappings) {

            productIds.add(mapping.getProductAdapter().getProductId());
        }

        List<CouponCRUDRequest.ProductMapping> updatedProductAdapterMappings  = couponCRUDRequest.getProductMapping();

        for (CouponCRUDRequest.ProductMapping productMappingMapping : updatedProductAdapterMappings) {

            if(productIds.contains(productMappingMapping.getProductId())) {
                continue;
            }
            else {
                return true;
            }
        }

        return false;
    }

    @Override
    public CouponListResponse getCoupons(Integer userId, String name, String draft, String published,
                                         String deactivated, String createdFrom, String createdTill,
                                         String inclusive, String updateFrom, String updateTill,
                                         String deactivateFrom, String deactivateTill, String appDurationFrom,
                                         String appDurationTill, String publishedFrom, String publishedTill,
                                         String discountFrom, String discountTill, String transactionFrom,
                                         String transactionTill, String couponAppType, String actor,
                                         String contextType, String limit, String offset, String active,
                                         String global, String order, String sort) {

        Integer offsetVal = (offset == null || Objects.equals(offset, "")) ? DEAULT_OFFSET : Integer.parseInt(offset);
        Integer limitVal = (limit == null || Objects.equals(limit, "")) ? DEAULT_LIMIT : Integer.parseInt(limit);
        offsetVal = (offsetVal < 0) ? 0 : offsetVal;
        limitVal = (limitVal < 0) ? MAX_RECORDS : limitVal;

        sort = (sort == null || sort.equals("")) ? "createdOn" : sort;
        order = (order == null || order.equals("") ? "desc" : order);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Coupon> couponQuery = cb.createQuery(Coupon.class); //return type is specified here..

        Root<Coupon> coupon = couponQuery.from(Coupon.class); //setting root class from where navigation happens
        couponQuery.select(coupon);

        orderBy(coupon, cb, couponQuery, order, sort);

        List<Predicate> predicates = getCouponCreatePredicates(coupon, cb, name, draft, published,
                deactivated, createdFrom, createdTill, inclusive, updateFrom, updateTill,
                deactivateFrom, deactivateTill, appDurationFrom, appDurationTill, publishedFrom, publishedTill,
                transactionFrom, transactionTill, couponAppType, actor, contextType, getCommaSeparatedCategoryNames(userId),
                active, global);

        couponQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        Query query = entityManager.createQuery(couponQuery);

        query.setFirstResult(offsetVal);
        query.setMaxResults(limitVal);
        List coupons = query.getResultList();

        if (coupons.size() == 0) {
            query.setFirstResult(0);
            coupons = query.getResultList();
        }

        //Calculating total result set size.
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(Coupon.class)));

        countQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        Long size = entityManager.createQuery(countQuery).getSingleResult();

        return getCouponList((List<Coupon>) coupons, size);
    }

    private void orderBy(Root<?> entity, CriteriaBuilder cb, CriteriaQuery<?> cq, String order, String sort) {
        try {

            if (order != null && sort != null) {
                if (order.equals(ASC)) {

                    cq.orderBy(cb.asc(entity.get(sort)));
                } else if (order.equals(DESC)) {

                    cq.orderBy(cb.desc(entity.get(sort)));
                }
            }
        } catch (IllegalArgumentException e) {
            LOG.debug("Given attribute "+sort+" to sort is not valid");
        }
    }

    private List<Predicate> getCouponCreatePredicates(Root<Coupon> coupon, CriteriaBuilder cb, String name, String draft, String published,
                                                      String deactivated, String createdFrom, String createdTill,
                                                      String inclusive, String updateFrom, String updateTill,
                                                      String deactivateFrom, String deactivateTill, String appDurationFrom,
                                                      String appDurationTill, String publishedFrom, String publishedTill, String transactionFrom,
                                                      String transactionTill, String couponAppType, String actor,
                                                      String contextType, String category, String active, String global) {

        List<Predicate> predicates = new ArrayList<>();

        checkInputLikeFieldVal(cb, coupon, name, "name", predicates);

        checkNullFieldValBasedOnInput(coupon, draft, "publishedBy", predicates, true);

        checkNullFieldValBasedOnInput(coupon, published, "publishedBy", predicates, false);

        checkNullFieldValBasedOnInput(coupon, deactivated, "deactivatedBy", predicates, false);

        checkNullFieldValBasedOnInput(coupon, active, "deactivatedBy", predicates, true);

        checkInputIsEqualToFieldVal(cb, coupon, inclusive, "inclusive", predicates, Boolean.class);

        checkInputIsEqualToFieldVal(cb, coupon, global, "global", predicates, Boolean.class);

        checkFieldValInBetweenInputRange(cb, coupon, createdFrom, createdTill, "createdOn", predicates, Date.class);

        checkFieldValInBetweenInputRange(cb, coupon, updateFrom, updateTill, "lastUpdatedOn", predicates, Date.class);

        checkFieldValInBetweenInputRange(cb, coupon, deactivateFrom, deactivateTill, "deactivatedOn", predicates, Date.class);

        checkFieldValInBetweenInputRange(cb, coupon, publishedFrom, publishedTill, "publishedOn", predicates, Date.class);

        checkInputRangeIntersectsFieldRange(cb, coupon, appDurationFrom, appDurationTill, "applicableFrom", "applicableTill", predicates, Date.class);

        checkInputRangeIntersectsFieldRange(cb, coupon, transactionFrom, transactionTill, "transactionMinValue", "transactionMaxValue", predicates, Integer.class);

        checkMultipleInputIsEqualToFieldVal(coupon, couponAppType, "applicationType", predicates, CouponApplicationType.class);

        checkMultipleInputIsEqualToFieldVal(coupon, actor, "actorType", predicates, ActorType.class);

        checkMultipleInputIsEqualToFieldVal(coupon, contextType, "contextType", predicates, ContextType.class);

        checkMultipleInputIsEqualToFieldVal(coupon, category, "category", predicates, CouponCategory.class);

        return predicates;
    }

    private void checkMultipleInputIsEqualToFieldVal(Root<?> entity, String input, String property, List<Predicate> predicates,
                                                     Class<?> type) {
        if (validInput(input)) {
            String[] inputs = input.split(DELIMITER);

            Object[] objects = new Object[inputs.length];

            for (int i = 0; i < inputs.length; i++) {
                objects[i] = convertStringToCorrespondingObject(inputs[i].trim(), type);
            }
            predicates.add(entity.get(property).in(objects));
        }
    }


    private boolean validInput(String input) {
        return input != null && !Objects.equals(input, "");
    }

    /**
     * For a given field, the predicate restricts to records whose field values
     * that are either null or that match the given input.
     */
    private void checkInputIsEqualToFieldVal(CriteriaBuilder cb, Root<?> entity,
                                             String rawInput, String property, List<Predicate> predicates, Class type) {
        if (validInput(rawInput)) {

            Object input = convertStringToCorrespondingObject(rawInput, type);
            predicates.add(cb.or(cb.equal(entity.get(property), input), entity.get(property).isNull()));
        }
    }

    /**
     * For a given field, the predicate restricts to records whose field values
     * that are either null or that is less than or equal to the given input.
     */
    private void checkFieldValLessThanOrEqualToInput(CriteriaBuilder cb, Root<?> entity,
                                                     String rawInput, String property, List<Predicate> predicates, Class type) {
        if (validInput(rawInput)) {

            predicates.add(cb.or(cb.lessThanOrEqualTo(entity.get(property),
                    convertStringToCorrespondingObject(rawInput, type)), entity.get(property).isNull()));
        }
    }

    /**
     * For a given field, the predicate restricts to records whose field values
     * that are either null or that is greater than or equal to the given input.
     */
    private void checkFieldValGreaterThanOrEqualToInput(CriteriaBuilder cb, Root<?> entity,
                                                        String rawInput, String property, List<Predicate> predicates, Class type) {


        if (validInput(rawInput)) {

            predicates.add(cb.or(cb.greaterThanOrEqualTo(entity.get(property),
                    convertStringToCorrespondingObject(rawInput, type)), entity.get(property).isNull()));
        }
    }

    /**
     * Converts the string object to the corresponding Data type.
     */
    private Comparable<? super Object> convertStringToCorrespondingObject(String rawInput, Class type) {
        Comparable input;

        if (type.getName().equals(Date.class.getName())) {

            input = new Date(Long.parseLong(rawInput));
        } else if (type.getName().equals(Integer.class.getName())) {

            input = Integer.parseInt(rawInput);
        } else if (type.isEnum()) {

            input = findEnumFromClass(rawInput, type);
        } else if (type.equals(Boolean.class)) {

            input = Boolean.parseBoolean(rawInput);
        } else {

            input = rawInput;
        }
        return input;
    }

    private Comparable findEnumFromClass(String rawInput, Class type) {
        for (Object obj : type.getEnumConstants()) {
            Enum object = (Enum) obj;
            String enumName = object.name();
            if (Objects.equals(rawInput, enumName)) {
                return object;
            }
        }
        return null;
    }

    /**
     * For a given field, the predicate includes records whose field values
     * are either null or that are in between the given input.
     */
    private void checkFieldValIncNullInBetweenInputRange(CriteriaBuilder cb, Root<?> entity,
                                                  String inputFrom, String inputTill, String property, List<Predicate> predicates, Class type) {

        if (validInput(inputFrom)) {
            predicates.add
                    (cb.or(cb.greaterThanOrEqualTo(entity.get(property),
                                    convertStringToCorrespondingObject(inputFrom, type)),
                            entity.get(property).isNull()));
        }

        if (validInput(inputTill)) {
            predicates.add
                    (cb.or(cb.lessThanOrEqualTo(entity.get(property),
                                    convertStringToCorrespondingObject(inputTill, type)),
                            entity.get(property).isNull()));
        }
    }

    /**
     * For a given field, the predicate includes records whose field values
     * are in between the given input.
     */
    private void checkFieldValInBetweenInputRange(CriteriaBuilder cb, Root<?> entity,
                                                  String inputFrom, String inputTill, String property, List<Predicate> predicates, Class type) {

        if (validInput(inputFrom)) {
            predicates.add
                    (cb.greaterThanOrEqualTo(entity.get(property),
                            convertStringToCorrespondingObject(inputFrom, type)));
        }

        if (validInput(inputTill)) {
            predicates.add
                    (cb.lessThanOrEqualTo(entity.get(property),
                            convertStringToCorrespondingObject(inputTill, type)));
        }
    }

    /**
     * For a given field range, the predicate includes records whose field values
     * are either null or that are intersecting the given input range.
     */
    private void checkInputRangeIntersectsFieldRange(CriteriaBuilder cb, Root<?> entity,
                                                     String inputFrom, String inputTill,
                                                     String propertyFrom, String propertyTill,
                                                     List<Predicate> predicates, Class type) {
        //If input from is not given, get all the entities whose value is less than or equal to input till.
        if (! validInput(inputFrom) && validInput(inputTill)) {
            predicates.add(cb.or(
                            cb.lessThanOrEqualTo(entity.get(propertyTill),
                                    convertStringToCorrespondingObject(inputTill, type)),
                            entity.get(propertyTill).isNull()
                    )
            );
        }

        //If input till is not given, get all the entities whose value is greater than or equal to input from.
        if (validInput(inputFrom) && ! validInput(inputTill)) {
            predicates.add(cb.or(
                            cb.greaterThanOrEqualTo(entity.get(propertyFrom),
                                    convertStringToCorrespondingObject(inputFrom, type)),
                            entity.get(propertyFrom).isNull()
                    )
            );
        }

        //Find entities whose values are intersecting with input range.
        if (validInput(inputFrom) && validInput(inputTill)) {
            predicates.add(cb.or(
                    cb.and(
                            cb.or
                                    (cb.greaterThanOrEqualTo(entity.get(propertyFrom),
                                                    convertStringToCorrespondingObject(inputFrom, type)),
                                            entity.get(propertyFrom).isNull()),
                            cb.or
                                    (cb.lessThanOrEqualTo(entity.get(propertyFrom),
                                                    convertStringToCorrespondingObject(inputTill, type)),
                                            entity.get(propertyFrom).isNull()))
                    ,
                    cb.and(
                            cb.or
                                    (cb.greaterThanOrEqualTo(entity.get(propertyTill),
                                                    convertStringToCorrespondingObject(inputFrom, type)),
                                            entity.get(propertyTill).isNull()),
                            cb.or
                                    (cb.lessThanOrEqualTo(entity.get(propertyTill),
                                                    convertStringToCorrespondingObject(inputTill, type)),
                                            entity.get(propertyTill).isNull())),
                    cb.and(
                            cb.or
                                    (cb.greaterThanOrEqualTo(entity.get(propertyFrom),
                                                    convertStringToCorrespondingObject(inputFrom, type)),
                                            entity.get(propertyFrom).isNull()),
                            cb.or
                                    (cb.lessThanOrEqualTo(entity.get(propertyTill),
                                                    convertStringToCorrespondingObject(inputTill, type)),
                                            entity.get(propertyTill).isNull()))
            ));
        }
    }

    /**
     * For a given field, the predicate restricts to records whose field values
     * that are like the given input.
     */
    private void checkInputLikeFieldVal(CriteriaBuilder cb, Root<?> entity,
                                        String input, String property, List<Predicate> predicates) {
        if (validInput(input)) {

            predicates.add(cb.like(entity.get(property), "%" + input + "%"));
        }
    }


    /**
     * If input is true, Check for values of the property that have non null values and vice versa.
     * When flip is on reverse the check condition.
     * ex: If input is to check for all deactivated entities, deactivatedBy property
     * should be non-null and vice versa. flip is useful in reversing the condition when required.
     */
    private void checkNullFieldValBasedOnInput(Root<?> entity,
                                               String rawInput, String property, List<Predicate> predicates, Boolean flip) {
        if (rawInput != null && !Objects.equals(rawInput, "")) {
            Boolean input = Boolean.parseBoolean(rawInput);

            if (flip) {
                input = !input;
            }

            if (input) {
                predicates.add(entity.get(property).isNotNull());
            } else {
                predicates.add(entity.get(property).isNull());
            }
        }
    }

    @Override
    public CouponCodesResponse getCouponCodes(Integer couponId, Integer offset, Integer limit) {

        offset = (offset == null) ? DEAULT_OFFSET : offset;
        limit = (limit == null) ? DEAULT_LIMIT : limit;
        limit = (limit < 0) ? MAX_RECORDS : limit;
        offset = (offset < 0) ? 0 : offset;

        CouponCodesResponse couponCodesResponse = new CouponCodesResponse();

        List<CouponCode> codes = couponCodeDao.getCodes(couponId, offset, limit);
        List<CouponCodeVO> couponCodeVOs = new ArrayList<>();

        if (codes.size() == 0) {
            codes = couponCodeDao.getCodes(couponId, 0, limit);
        }

        codes.forEach((code) -> {

            CouponCodeVO codeVo = createCouponCodeVO(code);
            couponCodeVOs.add(codeVo);

        });

        couponCodesResponse.setRows(couponCodeVOs);
        couponCodesResponse.setTotal(couponCodeDao.getCodeCount(couponId));
        return couponCodesResponse;
    }

    private CouponCodeVO createCouponCodeVO(CouponCode code) {
        CouponCodeVO codeVo = new CouponCodeVO();

        codeVo.setId(code.getId());
        codeVo.setChannelName(code.getChannelName());
        codeVo.setCode(code.getCode());

        codeVo.setCreatedBy(getUserName(code.getCreatedBy()));
        codeVo.setCreatedOn(code.getCreatedOn());
        codeVo.setDeactivatedOn(code.getDeactivatedOn());

        Coupon coupon = code.getCoupon();
        codeVo.setCouponId(coupon.getId());
        codeVo.setDeactivatedBy(getUserName(code.getDeactivatedBy()));

        codeVo.setCouponName(coupon.getName());
        codeVo.setApplicableFrom(coupon.getApplicableFrom());
        codeVo.setApplicableTill(coupon.getApplicableTill());
        codeVo.setCategoryName(coupon.getCategory().name());

        return codeVo;
    }

    private String getUserName(User user) {
        if (user != null) {
            return user.getName();
        }
        return null;
    }

    @Override
    public CouponCodesResponse getCouponCodes(Integer userId, String offset, String limit, String name, String active,
                                              String deactivated, String createdFrom, String createdTill,
                                              String deactivateFrom, String deactivateTill, String channel,
                                              String order, String sort) {

        Integer offsetVal = (offset == null || Objects.equals(offset, "")) ? DEAULT_OFFSET : Integer.parseInt(offset);
        Integer limitVal = (limit == null || Objects.equals(limit, "")) ? DEAULT_LIMIT : Integer.parseInt(limit);
        offsetVal = (offsetVal < 0) ? 0 : offsetVal;
        limitVal = (limitVal < 0) ? MAX_RECORDS : limitVal;

        sort = (sort == null || sort.equals("")) ? "createdOn" : sort;
        order = (order == null || order.equals("") ? "desc" : order);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CouponCode> cQuery = cb.createQuery(CouponCode.class);
        Root<CouponCode> couponCode = cQuery.from(CouponCode.class);

        cQuery.select(couponCode);

        orderBy(couponCode, cb, cQuery, order, sort);

        List<Predicate> predicates = new ArrayList<>();

        checkInputLikeFieldVal(cb, couponCode, name, "code", predicates);

        checkNullFieldValBasedOnInput(couponCode, active, "deactivatedBy", predicates, true);

        checkNullFieldValBasedOnInput(couponCode, deactivated, "deactivatedBy", predicates, false);

        checkFieldValInBetweenInputRange(cb, couponCode, createdFrom, createdTill, "createdOn", predicates, Date.class);

        checkFieldValInBetweenInputRange(cb, couponCode, deactivateFrom, deactivateTill, "deactivatedOn", predicates, Date.class);

        checkInputLikeFieldVal(cb, couponCode, channel, "channelName", predicates);

        cQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        Query query = entityManager.createQuery(cQuery);

        query.setFirstResult(offsetVal);
        query.setMaxResults(limitVal);

        CouponCodesResponse couponCodesResponse = new CouponCodesResponse();

        List<CouponCode> codes = (List<CouponCode>) query.getResultList();
        List<CouponCodeVO> couponCodeVOs = new ArrayList<>();

        if (codes.size() == 0) {
            query.setFirstResult(0);
            codes = (List<CouponCode>) query.getResultList();
        }

        codes.forEach((code) -> {
            if(isAllowedCategory(code, getCommaSeparatedCategoryNames(userId))) {
                CouponCodeVO codeVo = createCouponCodeVO(code);
                couponCodeVOs.add(codeVo);
            }

        });

        couponCodesResponse.setRows(couponCodeVOs);

        //Calculating total result set size.
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(CouponCode.class)));
        countQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        Long size = entityManager.createQuery(countQuery).getSingleResult();

        couponCodesResponse.setTotal(size);
        return couponCodesResponse;
    }

    private boolean isAllowedCategory(CouponCode code, String categories) {
        boolean isValid = false;
        Coupon coupon = code.getCoupon();
        if(categories.contains(coupon.getCategory().name())) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public ProductListResponse getProducts(Integer offset, Integer limit) {

        offset = (offset == null) ? DEAULT_OFFSET : offset;
        limit = (limit == null) ? DEAULT_LIMIT : limit;
        limit = (limit < 0) ? MAX_RECORDS : limit;
        offset = (offset < 0) ? 0 : offset;

        ProductListResponse productListResponse = new ProductListResponse();
        List<Object[]> products = productAdapterDao.getProducts(offset, limit);  //TODO: use configured upper limit for validation.

        if (products.isEmpty()) {
            products = productAdapterDao.getProducts(0, limit);
            productListResponse.setOffset(0);
        } else {
            productListResponse.setOffset(offset);
        }


        productListResponse.setRows(getProductVos(products));
        Long total = productAdapterDao.getProductCount();
        productListResponse.setTotal(total);
        return productListResponse;
    }

    private List<ProductVO> getProductVos(List<Object[]> products) {
        List<ProductVO> productVOs = new ArrayList<>();
        for (Object[] product : products) {
            productVOs.add(new ProductVO((Integer) product[0], (String) product[1], (String) product[2]));
        }
        return productVOs;
    }

    @Override
    public List<ProductVO> getProducts() {
        return getProductVos(productAdapterDao.getProducts(null, null));
    }

    @Override
    public List<BrandVo> getBrands() {
        List<BrandVo> brandVos = new ArrayList<>();
        List<Brand> brands = brandDao.getBrands();
        for (Brand brand : brands) {
            BrandVo brandVo = new BrandVo();
            brandVo.setId(brand.getId());
            brandVo.setName(brand.getName());
            brandVos.add(brandVo);
        }

        return brandVos;
    }

    @Override
    public List<AreaVo> getAreas(){
        List<AreaVo> areaVos = new ArrayList<>();
        List<Area> areas = areaDao.getAreas();
        for (Area area : areas) {
            AreaVo areaVo = new AreaVo();
            areaVo.setId(area.getId());
            areaVo.setName(area.getName());
            areaVos.add(areaVo);
        }

        return areaVos;
    }

    @Override
    public List<ReferrersVo> getReferrers(){
        List<ReferrersVo> referrersVos = new ArrayList<>();
        List<Referrer> referrers = referrerDao.getReferrers(null, null);
        for (Referrer referrer : referrers) {
            ReferrersVo referrersVo = new ReferrersVo();
            referrersVo.setId(referrer.getId());
            referrersVo.setBrandId(referrer.getBrandId());
            referrersVo.setBrandName(brandDao.find(referrer.getBrandId()).getName());
            referrersVo.setName(referrer.getName());
            referrersVo.setType(referrer.getReferrerType());

            referrersVos.add(referrersVo);
        }

        return referrersVos;
    }

    @Override
    public void deactivateCoupon(Integer userId, Integer couponId, Long lastUpdatedOn) {
        Coupon coupon = getCoupon(couponId);

        if(!validateUserRole(userId, coupon.getCategory())) {
            throw new CouponCreationException("User cannot deactivate a department specific coupon, which he isn't assigned to");
        }

        validateCouponUpdate(coupon, lastUpdatedOn, coupon.getPublishedBy(),
                input -> input == null, COUPON_IS_NOT_PUBLISHED);

        User user = userDao.find(userId);
        coupon.setDeactivatedBy(user);
        coupon.setLastUpdatedBy(user);

        Date currentDate = new Date();
        coupon.setLastUpdatedOn(currentDate);
        coupon.setDeactivatedOn(currentDate);
        couponDao.update(coupon);

        couponCodeDao.deactivate(coupon, user);
    }

    @Override
    public void deleteCoupon(Integer couponId, Long lastUpdatedOn) {

        Coupon coupon = getCoupon(couponId);

        validateCouponUpdate(coupon, lastUpdatedOn, coupon.getPublishedBy(),
                input -> input != null, COUPON_ALREADY_PUBLISHED + getUserName(coupon.getPublishedBy()));

        List<CouponAudit> couponAudits = couponAuditDao.find(coupon);

        couponAudits.forEach(couponAudit -> {
            couponBrandMappingAuditDao.delete(couponAudit);
            couponProductAdapterMappingAuditDao.delete(couponAudit);
            couponAreaMappingAuditDao.delete(couponAudit);
            couponReferrerMappingAuditDao.delete(couponAudit);
            couponDiscountingRuleAuditDao.delete(couponAudit);
            couponCoreAuditDao.delete(couponAudit);
            couponAuditDao.delete(couponAudit);
        });

        couponBrandMappingDao.delete(coupon);
        couponProductAdapterMappingDao.delete(coupon);
        couponAreaMappingDao.delete(coupon);
        couponReferrerMappingDao.delete(coupon);
        couponDiscountingRuleDao.delete(coupon);
        couponDao.delete(coupon);
    }

    @Override
    public Integer createCouponCode(Integer couponId, CouponCodeCreationRequest couponCodeCreationRequest, Integer userId) {
        Coupon coupon = couponDao.find(couponId);
        if (coupon == null) {
            throw new RuntimeException("Given coupon Id " + couponId + " is not valid");
        }

        User user = userDao.find(userId);

        if (user == null) {
            throw new RuntimeException("Given user Id " + userId + " is not valid");
        }

        if(!validateUserRole(userId, coupon.getCategory())) {
            throw new CodeCreationException("User cannot create a department specific coupon code, which he isn't assigned to");
        }

        validateCouponCode(couponCodeCreationRequest.getCode());

        CouponCode couponCode = new CouponCode();
        couponCode.setCreatedOn(new Date());
        couponCode.setCreatedBy(user);

        couponCode.setChannelName(couponCodeCreationRequest.getChannelName());
        couponCode.setCode(couponCodeCreationRequest.getCode());

        couponCode.setCoupon(coupon);

        couponCodeDao.create(couponCode);

        if (couponCodeCreationRequest.getReservations() != null) {
            for (CouponCodeCreationRequest.Reservation reservation : couponCodeCreationRequest.getReservations()) {
                CouponCodeReservation couponCodeReservation = getCodeReservation(couponCode, reservation);
                if (couponCodeReservation == null) {
                    continue;
                }
                couponCodeReservationDao.create(couponCodeReservation);
            }
        }
        return couponCode.getId();
    }

    private void validateCouponCode(String couponCode) {
        if (couponCode == null) {
            throw new CodeCreationException("Code cannot be null");
        }

        List<CouponCode> codes = couponCodeDao.getCouponCodes(new String[]{couponCode});

        codes.forEach(code -> {
            if (code.isActive()) {
                throw new CodeCreationException("An active coupon code with this name already exists");
            }
        });

        if(couponCode.equalsIgnoreCase("") || couponCode.equalsIgnoreCase(null) || couponCode.contains(" ")) {
            throw new CodeCreationException("Coupon code can have only alphanumeric and '-' chanracters");
        }

        if(couponCode.charAt(0)=='-') {
            throw new CouponCreationException("Coupon code cannot start with '_' ");
        }

        if(couponCode.length() > 128) {
            throw new CouponCreationException("Coupon name cannot exceed 128 characters");
        }

        String pattern= "^[a-zA-Z0-9\\-]*$";
        if(!couponCode.matches(pattern)) {
            throw new CodeCreationException("Coupon code can have only alphanumeric and '-' chanracters");
        }
    }

    private CouponCodeReservation getCodeReservation(CouponCode couponCode, CouponCodeCreationRequest.Reservation reservation) {
        Integer reservationUserId = reservation.getUserId();
        User reservationForUser = userDao.find(reservationUserId);
        if (reservationUserId == null || reservationForUser == null) {
            LOG.warn("user not found for id " + reservationUserId + " for reservation ");
            return null;
        }
        CouponCodeReservation couponCodeReservation = new CouponCodeReservation();
        couponCodeReservation.setUser(reservationForUser);
        couponCodeReservation.setCouponCode(couponCode);
        couponCodeReservation.setRemarks(reservation.getRemarks());
        couponCodeReservation.setReservationFrom(reservation.getReservationFrom());

        couponCodeReservation.setReservationTill(reservation.getReservationTill());
        couponCodeReservation.setReservationType(reservation.getReservationType());
        return couponCodeReservation;
    }

    @Override
    public void createReservation(Integer couponId, Integer codeId, CouponCodeCreationRequest.Reservation reservation, Integer userId) {
        CouponCode couponCode = couponCodeDao.find(codeId);
        User user = userDao.find(userId);
        if (couponCode == null) {
            throw new RuntimeException("Given coupon code id " + codeId + " is not valid");
        }

        if (couponCode.getDeactivatedBy() != null || couponCode.getDeactivatedOn() != null) {
            throw new RuntimeException("Reservation cannot be made for a deactivated coupon code");
        }

        if (user == null) {
            throw new RuntimeException("Given user id " + userId + " is not valid");
        }

        CouponCodeReservation couponCodeReservation = getCodeReservation(couponCode, reservation);
        if (couponCodeReservation != null) {
            couponCodeReservationDao.create(couponCodeReservation);
        } else {
            LOG.warn("reservation not created");
        }
    }

    /**
     * Published coupons applicability upper limit value is updated only if the new value is
     * greater than the existing value.
     */
    @Override
    public void extendValidity(Integer userId, Long epochTime, Integer couponId, Long lastUpdatedOn) {

        Coupon coupon = getCoupon(couponId);

        if(!validateUserRole(userId, coupon.getCategory())) {
            throw new CouponUpdateException("User cannot extend validity of a department specific coupon, which he isn't assigned to");
        }

        validateCouponUpdate(coupon, lastUpdatedOn, coupon.getPublishedBy(),
                input -> input == null, COUPON_IS_NOT_PUBLISHED);


        Date applicableTill = coupon.getApplicableTill();
        Long applicableTillTime = applicableTill.getTime();

        if (epochTime < applicableTillTime) {
            throw new CouponUpdateException("Given applicability upper limit value should be greater than existing value");
        }

        User user = userDao.find(userId);

        coupon.setLastUpdatedBy(user);
        coupon.setLastUpdatedOn(new Date());
        Date newApplicableDate = new Date(epochTime);
        coupon.setApplicableTill(newApplicableDate);

        couponDao.update(coupon);
    }

    @Override
    public void deactivateCouponCode(Integer userId, Integer couponId, Integer codeId) {
        CouponCode couponCode = validateAndGetCouponCode(codeId, couponId);

        if(!validateUserRole(userId, couponDao.find(couponId).getCategory())) {
            throw new CouponUpdateException("User cannot deactivate a department specific coupon code, which he isn't assigned to");
        }

        if (couponCode.getDeactivatedBy() != null) {
            LOG.debug("coupon code " + couponCode.getCode() + " is already deactivated by user " + couponCode.getDeactivatedBy().getName());
            return;
        }

        couponCode.setDeactivatedOn(new Date());
        couponCode.setDeactivatedBy(userDao.find(userId));

        couponCodeDao.update(couponCode);
    }

    @Override
    public CouponCodeVO getCouponCode(Integer couponId, Integer codeId) {
        CouponCode couponCode = validateAndGetCouponCode(codeId, couponId);

        return createCouponCodeVO(couponCode);
    }

    @Override
    public List<String> getUserNames(String name, Integer limit) {
        if (limit == null || limit < 0) {
            limit = 3;
        }
        return userDao.getUserNames(name, limit);
    }

    @Override
    public Integer getUserId(String name) {
        try {
            return userDao.getUserId(name);
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    @Override
    public Integer getCouponId(String name) {
        if (name == null) {
            return null;
        }

        Integer couponId;
        try {

            couponId = couponDao.getCouponId(name);
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
        return couponId;
    }

    @Override
    public Integer getCouponCodeId(String code) {
        if (code == null) {
            return null;
        }

        Integer codeId;

        try {
            codeId = couponCodeDao.getActiveCodeId(code);
        } catch (NoResultException e) {
            return null;
        }
        return codeId;
    }

    private CouponCode findCode(Integer codeId) {

        if (codeId == null) {
            throw new RuntimeException("Coupon code id is not mentioned");
        }

        CouponCode couponCode = couponCodeDao.find(codeId);

        if (couponCode == null) {
            throw new RuntimeException("Invalid coupon code id " + codeId);
        }

        return couponCode;
    }

    private CouponCode validateAndGetCouponCode(Integer codeId, Integer couponId) {

        CouponCode couponCode = findCode(codeId);

        if (!couponCode.getCoupon().getId().equals(couponId)) {
            throw new RuntimeException("Coupon code " + couponCode.getCode() + " doesn't belong to given coupon id " + couponId);
        }

        return couponCode;
    }

    private CouponListResponse getCouponList(List<Coupon> coupons, Long size) {
        CouponListResponse couponListResponse = new CouponListResponse();
        List<CouponDataVO> list = new ArrayList<>();
        for (Coupon coupon : coupons) {

            CouponDataVO couponDataVO = new CouponDataVO();
            couponDataVO.setId(coupon.getId());
            couponDataVO.setName(coupon.getName());

            couponDataVO.setFrom(coupon.getApplicableFrom());
            couponDataVO.setTill(coupon.getApplicableTill());

            couponDataVO.setCreatedOn(coupon.getCreatedOn());
            couponDataVO.setCreatedBy(getUserName(coupon.getCreatedBy()));

            couponDataVO.setApplicationType(coupon.getApplicationType());
            couponDataVO.setAppUseCount(coupon.getApplicableUseCount());

            couponDataVO.setInclusive(coupon.getInclusive());
            couponDataVO.setGlobal(coupon.getGlobal());

            List<BrandVo> brandVos = new ArrayList<>();
            List<CouponBrandMapping> brandMappings = couponBrandMappingDao.getMappings(coupon);
            for (CouponBrandMapping mapping : brandMappings) {

                BrandVo brandVo = new BrandVo();
                Brand brand = mapping.getBrand();

                brandVo.setId(brand.getId());
                brandVo.setName(brand.getName());
                brandVos.add(brandVo);
            }
            couponDataVO.setBrandVos(brandVos);

            Long codeCount = couponCodeDao.getCodeCount(coupon.getId());
            couponDataVO.setCodes(codeCount);

            couponDataVO.setPublishedBy(getUserName(coupon.getPublishedBy()));
            couponDataVO.setPublishedOn(coupon.getPublishedOn());

            couponDataVO.setLastUpdatedOn(coupon.getLastUpdatedOn());
            couponDataVO.setLastUpdatedBy(getUserName(coupon.getLastUpdatedBy()));

            couponDataVO.setDeactivatedBy(getUserName(coupon.getDeactivatedBy()));
            couponDataVO.setDeactivatedOn(coupon.getDeactivatedOn());

            couponDataVO.setDescription(coupon.getDescription());
            list.add(couponDataVO);
        }
        couponListResponse.setRows(list);
        couponListResponse.setTotal(size);
        return couponListResponse;
    }

    @Override
    public void publishCoupon(Integer userId, Integer couponId, Long lastUpdatedOn) {

        Coupon coupon = getCoupon(couponId);

        if(!validateUserRole(userId, coupon.getCategory())) {
            throw new CouponUpdateException("User cannot publish a department specific coupon, which he isn't assigned to");
        }

        validateCouponUpdate(coupon, lastUpdatedOn, coupon.getPublishedBy(),
                input -> input != null, COUPON_ALREADY_PUBLISHED+getUserName(coupon.getPublishedBy()));

        User user = userDao.find(userId);
        coupon.setPublishedBy(user);

        Date currentDate = new Date();
        coupon.setPublishedOn(currentDate);
        coupon.setLastUpdatedBy(user);

        coupon.setLastUpdatedOn(new Date());
        couponDao.update(coupon);
    }

    private Coupon getCoupon(Integer couponId) {
        if (couponId == null) {
            throw new RuntimeException("coupon Id is not mentioned");
        }
        Coupon coupon = couponDao.find(couponId);
        if (coupon == null) {
            throw new RuntimeException("given coupon id " + couponId + " is not valid");
        }
        return coupon;
    }

    public CouponCategoriesResponse getCategories(Integer userId) {
        CouponCategoriesResponse couponCategoriesResponse = new CouponCategoriesResponse();
        List<Integer> assignedRoles = userRoleMappingDao.getUserRoleIds(userId);
        List<String> categoryRoleNames = roleDao.getRoles(assignedRoles);
        List<String> categories = new ArrayList<>();
        for (String categoryRoleName : categoryRoleNames) {
            switch (categoryRoleName) {
                case "Coupon Admin":
                    categories = new ArrayList<>();
                    categories.add("SALES");
                    categories.add("MARKETING");
                    categories.add("ENGAGEMENT");
                    categories.add("OPs");
                    break;

                case "Coupon Manager Sales":
                    if(! categories.contains("SALES")) {
                        categories.add("SALES");
                    }
                    break;

                case "Coupon Manager Engagement":
                    if(! categories.contains("ENGAGEMENT")) {
                        categories.add("ENGAGEMENT");
                    }
                    break;

                case "Coupon Manager Marketing":
                    if(! categories.contains("MARKETING")) {
                        categories.add("MARKETING");
                    }
                    break;

                case "Coupon Manager Ops":
                    if(! categories.contains("OPs")) {
                        categories.add("OPs");
                    }
            }
        }
        couponCategoriesResponse.setCategoryNames(categories);
        return couponCategoriesResponse;
    }

    private String getCommaSeparatedCategoryNames(Integer userId) {
        List<Integer> assignedRoles = userRoleMappingDao.getUserRoleIds(userId);
        List<String> categoryRoleNames = roleDao.getRoles(assignedRoles);
        StringBuilder commaSeparatedCategory = new StringBuilder();
        if(categoryRoleNames.size() < 1) {
            return  commaSeparatedCategory.toString();
        }

        for (String categoryRoleName : categoryRoleNames) {
            switch (categoryRoleName) {
                case "Coupon Admin":
                    return CouponCategory.SALES + "," + CouponCategory.MARKETING + "," + CouponCategory.ENGAGEMENT + "," + CouponCategory.OPs;

                case "Coupon Manager Sales":
                    commaSeparatedCategory.append(CouponCategory.SALES + "," );
                    break;

                case "Coupon Manager Engagement":
                    commaSeparatedCategory.append(CouponCategory.ENGAGEMENT + "," );
                    break;

                case "Coupon Manager Marketing":
                    commaSeparatedCategory.append(CouponCategory.MARKETING + "," );
                    break;

                case "Coupon Manager Ops":
                    commaSeparatedCategory.append(CouponCategory.OPs + "," );
                    break;
            }
        }

        return commaSeparatedCategory.toString().replaceAll(",$", "");
    }

// ---------------------------------------- DASHBOARD ----------------------------------------------//

    public List<CouponActorData> getNumOfCouponsCreatedByActorDetails() {
        Date date = new Date();
        List<Object[]> actorDetails = couponDao.getCouponActorDetails(date);
        List<CouponActorData> couponActorDetails = new ArrayList<>();
        actorDetails.forEach(actorData -> {
            CouponActorData couponActorData = new CouponActorData();
            couponActorData.setCount((Long) actorData[0]);
            couponActorData.setActorType((ActorType) actorData[1]);
            couponActorDetails.add(couponActorData);
        });
        return couponActorDetails;

    }

    public List<CouponCategoryData> getNumOfCouponsCreatedByCategoryDetails() {
        Date date = new Date();
        List<Object[]> categoryDetails = couponDao.getCouponCategoryDetails(date);
        List<CouponCategoryData> couponCategoryDetails = new ArrayList<>();
        categoryDetails.forEach(categoryData -> {
            CouponCategoryData couponCategoryData = new CouponCategoryData();
            couponCategoryData.setCount((Long) categoryData[0]);
            couponCategoryData.setCouponCategory((CouponCategory) categoryData[1]);
            couponCategoryDetails.add(couponCategoryData);
        });
        return couponCategoryDetails;

    }

    public List<CouponContextTypeData> getNumOfCouponsCreatedByContextTypeDetails() {
        Date date = new Date();
        List<Object[]> contextTypeDetails = couponDao.getCouponContextTypeDetails(date);
        List<CouponContextTypeData> couponContextTypeDetails = new ArrayList<>();
        contextTypeDetails.forEach(contextTypeDetail -> {
            CouponContextTypeData couponContextTypeData = new CouponContextTypeData();
            couponContextTypeData.setCount((Long) contextTypeDetail[0]);
            couponContextTypeData.setContextType((ContextType) contextTypeDetail[1]);
            couponContextTypeDetails.add(couponContextTypeData);
        });
        return couponContextTypeDetails;

    }

    public List<CouponAreaData> getNumOfCouponsCreatedByAreaDetails() {
        Date date = new Date();
        List<Object[]> areaDetails = couponAreaMappingDao.getCouponAreaDetails(date);
        List<CouponAreaData> couponAreaDetails = new ArrayList<>();
        areaDetails.forEach(areaDetail -> {
            CouponAreaData couponAreaData = new CouponAreaData();
            couponAreaData.setCount((Long) areaDetail[0]);
            couponAreaData.setArea(((CouponAreaMapping) areaDetail[1]).getArea().getName());
            couponAreaDetails.add(couponAreaData);
        });
        return couponAreaDetails;

    }

    public List<CouponAreaData> getNumOfCouponDiscountsGivenByArea() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        Date morning = getMorning(calendar, date, 0);
        Date midnight = getNight(calendar, date, 0);

        List<Object[]> areaDetails = couponDiscountDao.getCouponDiscountDetailsByArea(morning, midnight);
        List<CouponAreaData> couponAreaDetails = new ArrayList<>();
        areaDetails.forEach(areaDetail -> {
            CouponAreaData couponAreaData = new CouponAreaData();
            couponAreaData.setCount((Long) areaDetail[0]);
            couponAreaData.setArea(((CouponDiscount) areaDetail[1]).getAreaId().getName());
            couponAreaDetails.add(couponAreaData);
        });
        return couponAreaDetails;

    }

    public List<CouponDiscountAreaData> getCouponDiscountsGivenByArea() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        Date morning = getMorning(calendar, date, 0);
        Date midnight = getNight(calendar, date, 0);

        List<Object[]> areaDetails = couponDiscountDao.getCouponDiscountsGivenByArea(morning, midnight);
        List<CouponDiscountAreaData> couponAreaDetails = new ArrayList<>();
        areaDetails.forEach(areaDetail -> {
            CouponDiscountAreaData couponAreaData = new CouponDiscountAreaData();
            couponAreaData.setDiscountGiven((Double) areaDetail[0]);
            couponAreaData.setArea(((CouponDiscount) areaDetail[1]).getAreaId().getName());
            couponAreaDetails.add(couponAreaData);
        });
        return couponAreaDetails;

    }

    public List<CouponBrandData> getNumOfCouponDiscountsGivenByBrand() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        Date morning = getMorning(calendar, date, 0);
        Date midnight = getNight(calendar, date, 0);
        List<Object[]> brandDetails = couponDiscountDao.getCouponDiscountDetailsByBrand(morning, midnight);
        List<CouponBrandData> couponBrandDetails = new ArrayList<>();
        brandDetails.forEach(brandDetail -> {
            CouponBrandData couponBrandData = new CouponBrandData();
            couponBrandData.setCount((Long) brandDetail[0]);
            couponBrandData.setBrand(((CouponDiscount) brandDetail[1]).getPatientBrand().getName());
            couponBrandDetails.add(couponBrandData);
        });
        return couponBrandDetails;

    }

    public List<CouponDiscountBrandData> getCouponDiscountsGivenByBrand() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        Date morning = getMorning(calendar, date, 0);
        Date midnight = getNight(calendar, date, 0);

        List<Object[]> brandDetails = couponDiscountDao.getCouponDiscountsGivenByBrand(morning, midnight);
        List<CouponDiscountBrandData> couponBrandDetails = new ArrayList<>();
        brandDetails.forEach(brandDetail -> {
            CouponDiscountBrandData couponDiscountBrandData = new CouponDiscountBrandData();
            couponDiscountBrandData.setDiscountGiven((Double) brandDetail[0]);
            couponDiscountBrandData.setBrand(((CouponDiscount) brandDetail[1]).getPatientBrand().getName());
            couponBrandDetails.add(couponDiscountBrandData);
        });
        return couponBrandDetails;

    }

    public List<CouponReferrerData> getNumOfCouponDiscountsGivenByReferrerType() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        Date morning = getMorning(calendar, date, 0);
        Date midnight = getNight(calendar, date, 0);

        List<Object[]> referrerDetails = couponDiscountDao.getCouponDiscountDetailsByReferrerType(morning, midnight);
        List<CouponReferrerData> couponReferrerTypeDetails = new ArrayList<>();
        referrerDetails.forEach(referrerDetail -> {
            CouponReferrerData couponReferrerData = new CouponReferrerData();
            couponReferrerData.setCount((Long) referrerDetail[0]);
            couponReferrerData.setReferrerType(((CouponDiscount) referrerDetail[1]).getReferrerId().getReferrerType());
            couponReferrerTypeDetails.add(couponReferrerData);
        });
        return couponReferrerTypeDetails;

    }

    public CouponDiscountStatusWeeklyData getCouponDiscReqStatusWeeklyDetails() {
        CouponDiscountStatusWeeklyData couponDiscountStatusWeeklyData = new CouponDiscountStatusWeeklyData();
        List<CouponDiscountStatusData> couponStatusDetails = new ArrayList<>();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        for(int i = 0 ; i < NUMBER_OF_DAYS ; i++) {

            Date morning = getMorning(calendar, date, i);
            Date midnight = getNight(calendar, date, i);

            String dayOfWeek = new SimpleDateFormat("EE").format(midnight);

            Long requestedStatusDetails = couponDiscountRequestDao.getCouponDiscountRequestStatusDetails(morning, midnight, CouponDiscountRequestStatus.REQUESTED);
            Long cancelledStatusDetails = couponDiscountRequestDao.getCouponDiscountRequestStatusDetails(morning, midnight, CouponDiscountRequestStatus.CANCELED);
            Long appliedStatusDetails = couponDiscountRequestDao.getCouponDiscountRequestStatusDetails(morning, midnight, CouponDiscountRequestStatus.APPLIED);

            CouponDiscountStatusData couponDiscountStatusData = new CouponDiscountStatusData();
            couponDiscountStatusData.setApplied(appliedStatusDetails);
            couponDiscountStatusData.setCancelled(cancelledStatusDetails);
            couponDiscountStatusData.setRequested(requestedStatusDetails);
            couponDiscountStatusData.setDayOfWeek(dayOfWeek);

            couponStatusDetails.add(couponDiscountStatusData);

        }
        couponDiscountStatusWeeklyData.setDiscountData(couponStatusDetails);

        return couponDiscountStatusWeeklyData;

    }

    /**
     * Gets the early morning date given a date . for e.g given date - 12-Feb-2016T12:45:45, the returned value is 12-Feb-2016T00:00:00;
     */
    private Date getMorning(Calendar calendar, Date date, int i) {

        calendar.setTime(date);
        calendar.add(Calendar.DATE, -i);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date morning = calendar.getTime();

        return  morning;
    }

    /**
     * Gets the midnight date given a date . for e.g given date - 12-Feb-2016T12:45:45, the returned value is 12-Feb-2016T23:59:59;
     */
    private Date getNight(Calendar calendar, Date date, int i) {

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        Date midnight = calendar.getTime();

        return  midnight;
    }

    public CouponDiscountWeeklyData getCouponDiscountWeeklyDetails() {
        CouponDiscountWeeklyData couponDiscountWeeklyData = new CouponDiscountWeeklyData();
        List<CouponDiscountData> couponDiscountDetails = new ArrayList<>();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        for(int i = 0 ; i < NUMBER_OF_DAYS ; i++) {

            Date morning = getMorning(calendar, date, i);
            Date midnight = getNight(calendar, date, i);
            String dayOfWeek = new SimpleDateFormat("EE").format(midnight);

            List<Object[]> statusDetails = couponDiscountDao.getCouponDiscountDetails(morning, midnight);

            statusDetails.forEach(statusDetail -> {
                CouponDiscountData couponDiscountStatusData = new CouponDiscountData();
                couponDiscountStatusData.setMinDiscount((Double) statusDetail[0]);
                couponDiscountStatusData.setAvgDiscount((Double) statusDetail[1]);
                couponDiscountStatusData.setMaxDiscount((Double) statusDetail[2]);
                couponDiscountStatusData.setDayOfWeek(dayOfWeek);
                couponDiscountDetails.add(couponDiscountStatusData);
            });
        }
        couponDiscountWeeklyData.setDiscountData(couponDiscountDetails);

        return couponDiscountWeeklyData;

    }

    public CouponExpiryWeeklyData getCouponExpiryDetails() {
        CouponExpiryWeeklyData couponExpiryWeeklyData = new CouponExpiryWeeklyData();
        List<CouponExpiryData> couponExpiryDetails = new ArrayList<>();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        Date endDate = calendar.getTime();
        for(int i = 0 ; i < NUMBER_OF_WEEKS ; i++) {
            date = endDate;
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            startDate = calendar.getTime();
            int startMonth = calendar.get(Calendar.MONTH);
            int startDay = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.add(Calendar.DATE, NUMBER_OF_DAYS);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 59);
            endDate = calendar.getTime();
            int endMonth = calendar.get(Calendar.MONTH);
            int endDay = calendar.get(Calendar.DAY_OF_MONTH);

            String dateRange = startDay + "/" + startMonth + " - " + endDay + "/" + endMonth;

            List<Object[]> statusDetails = couponDao.getCouponExpiryDetails(startDate, endDate);

            statusDetails.forEach(statusDetail -> {
                CouponExpiryData couponExpiryData = new CouponExpiryData();
                couponExpiryData.setCouponsCount((Long) statusDetail[0]);
                couponExpiryData.setRange(dateRange);
                couponExpiryDetails.add(couponExpiryData);
            });
        }
        couponExpiryWeeklyData.setWeeklyData(couponExpiryDetails);

        return couponExpiryWeeklyData;

    }

    public List<CouponDiscountsRangeData> getNumOfCouponsGivenRangeDetails() {
        Date date = new Date();
        List<CouponDiscountsRangeData> couponDiscountsRangeDatas = new ArrayList<>();
        Integer startRange = 0;
        Integer endRange = 0;
        Integer temp = START_RANGE;
        for(int i=1 ;i<5 ;i++) {
            CouponDiscountsRangeData couponDiscountsRangeData = new CouponDiscountsRangeData();
            startRange = endRange;
            if(temp == START_RANGE * 8) {
                endRange = MAXIMUM_DISCOUNT_VALUE;
                couponDiscountsRangeData.setRange(startRange + "+" );
            }
            else {
                endRange = temp;
                couponDiscountsRangeData.setRange(startRange + "-" + endRange);
            }

            Long numberOfCoupons = couponDao.getCouponDiscountDetails(date, startRange, endRange);
            couponDiscountsRangeData.setNumberOfCoupons(numberOfCoupons);

            temp *= 2;

            couponDiscountsRangeDatas.add(couponDiscountsRangeData);

        }

        return couponDiscountsRangeDatas;
    }

}
