package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponDiscountRequest;
import com.portea.cpnen.domain.CouponDiscountRequestProduct;
import com.portea.cpnen.domain.ProductType;
import com.portea.cpnen.vo.ProductVo;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import java.util.List;

public interface CouponDiscountReqProdDao extends Dao<Integer, CouponDiscountRequestProduct> {

    List<CouponDiscountRequestProduct> getProducts(CouponDiscountRequest cdr) ;

    CouponDiscountRequestProduct getRequestProductById(Integer cdrId, Integer productId, ProductType productType) throws NoResultException;

}
