package com.portea.cpnen.dao;

import com.portea.cpnen.domain.CouponDiscountRequestProduct;
import com.portea.cpnen.vo.ProductVo;
import com.portea.dao.Dao;

import java.util.List;

public interface CouponDiscountReqProdDao extends Dao<Integer, CouponDiscountRequestProduct> {

    List<ProductVo> getProductVos(Integer cdrId);

}
