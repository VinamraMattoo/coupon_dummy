package com.portea.cpnen.rapi.service;

import com.portea.cpnen.rapi.domain.*;
import com.portea.cpnen.vo.ProductVo;
import com.portea.cpnen.web.rapi.domain.CouponCodeVO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CouponEngineRequestProcessor {

    CouponDiscountRequestResponse createCouponDiscountRequest(CouponDiscountRequestCreateReq request);

    void updateCouponDiscountRequest(Integer cdrId, CouponDiscountRequestUpdateReq request);

    void cancelCouponDiscountRequest(Integer cdrId);

    ApplicableDiscountResp getCurrentApplicableDiscount(Integer cdrId);

    void addCouponCodeToRequest(Integer cdrId, String code);

    void deleteCouponCodeFromRequest(Integer cdrId, String code);

    void addProductToRequest(Integer cdrId, ProductUpdateReq productUpdateReq);

    void deleteProductFromRequest(Integer cdrId, Integer productId, String ProductType, CostUpdateReq costUpdateReq);

    List<CouponCodeVO> getCouponCodes(Integer cdrId);

    List<ProductVo> getProducts(Integer cdrId);

    int commitCDR(Integer cdrId, String clientContextId);

    int applyCDR(Integer cdrId, String clientContextId);

    CouponDiscountRequestStatusResp getCDRStatus(Integer cdrId);

    CouponInfoResponse getCoupon(String couponCode);

}
