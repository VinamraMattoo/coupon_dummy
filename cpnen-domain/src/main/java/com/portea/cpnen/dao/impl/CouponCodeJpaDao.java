package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponCodeDao;
import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.CouponCode;
import com.portea.cpnen.domain.User;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@JpaDao
@Dependent
public class CouponCodeJpaDao extends BaseJpaDao<Integer, CouponCode> implements CouponCodeDao {

    public CouponCodeJpaDao() {
        super(CouponCode.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CouponCode> getCouponCodes(String[] codes) {
        List<String> codeList = Arrays.asList(codes);
        Query query = entityManager.createNamedQuery("getCouponCodes", CouponCode.class).setParameter("codes", codeList);
        List<CouponCode> list = query.getResultList();
        return list;
    }

    @Override
    public CouponCode getCouponCode(String code) throws NoResultException{
        Query query = entityManager.createNamedQuery("getCouponCode", CouponCode.class).setParameter("code",code);
        return (CouponCode)query.getSingleResult();
    }

    @Override
    public List<CouponCode> getCodes(Integer couponId, Integer offset, Integer limit) {
        Query query = entityManager.createNamedQuery("getCodeForCouponId", CouponCode.class);
        query.setParameter("couponId", couponId);
        query.setFirstResult(offset);

        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<CouponCode> getCodes(Integer offset, Integer limit) {
        Query query = entityManager.createNamedQuery("getCodes", CouponCode.class);
        query.setFirstResult(offset);

        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Long getCodeCount(Integer couponId) {
        Query query = entityManager.createNamedQuery("getCodeCountForCoupon");
        query.setParameter("couponId", couponId);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getCodeCount() {
        Query query = entityManager.createNamedQuery("getCodeCount");
        return (Long) query.getSingleResult();
    }

    @Override
    public void delete(Coupon coupon) {
        Query query = entityManager.createNamedQuery("deleteCouponCodes");
        query.setParameter("coupon", coupon);
        query.executeUpdate();
    }

    @Override
    public Integer getActiveCodeId(String code) throws NoResultException,NonUniqueResultException{

        Query query = entityManager.createNamedQuery("getCodeIdForCode");
        query.setParameter("code", code);
        return (Integer) query.getSingleResult();
    }

    @Override
    public List<Object[]> getCouponCodeMap(List<String> codes) {
        codes.add("-1"); //TODO:: added dummy code to prevent query from failing
        Query query = entityManager.createNamedQuery("getCouponCodeMap");
        query.setParameter("codes", codes);
        return query.getResultList();
    }

    @Override
    public void deactivate(Coupon coupon, User user) {
        Query query = entityManager.createNamedQuery("deactivateCodes");
        query.setParameter("coupon", coupon);
        query.setParameter("deactivatedBy", user);
        query.setParameter("deactivatedOn", new Date());
        query.executeUpdate();
    }

}
