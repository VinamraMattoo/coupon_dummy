package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountReqProdDao;
import com.portea.cpnen.domain.CouponDiscountRequestProduct;
import com.portea.cpnen.vo.ProductVo;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class CouponDiscountReqProdJpaDao extends BaseJpaDao<Integer, CouponDiscountRequestProduct> implements CouponDiscountReqProdDao{

    public CouponDiscountReqProdJpaDao() {
        super(CouponDiscountRequestProduct.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ProductVo> getProductVos(Integer cdrId) {
        Query query = entityManager.createNamedQuery("getProductVos",ProductVo.class).setParameter("id",cdrId);
        List<ProductVo> productVos = query.getResultList();
        return productVos;
    }

}
