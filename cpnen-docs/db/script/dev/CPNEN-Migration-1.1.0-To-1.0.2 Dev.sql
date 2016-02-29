--                        Coupon Table
ALTER TABLE coupon DROP COLUMN is_for_all_areas;
ALTER TABLE coupon DROP COLUMN is_for_all_brands;
ALTER TABLE coupon DROP COLUMN is_for_all_products;
ALTER TABLE coupon DROP COLUMN is_for_all_b2b;
ALTER TABLE coupon DROP COLUMN is_for_all_b2c;

--                        Coupon Core Audit Table
ALTER TABLE coupon_core_audit DROP COLUMN is_for_all_areas;
ALTER TABLE coupon_core_audit DROP COLUMN is_for_all_brands;
ALTER TABLE coupon_core_audit DROP COLUMN is_for_all_products;
ALTER TABLE coupon_core_audit DROP COLUMN is_for_all_b2b;
ALTER TABLE coupon_core_audit DROP COLUMN is_for_all_b2c;

--                        Coupon Discount Request Table
ALTER TABLE coupon_discount_req DROP COLUMN patient_brand_id;
ALTER TABLE coupon_discount_req DROP COLUMN area_id;
ALTER TABLE coupon_discount_req DROP COLUMN referrer_id;

-- ALTER TABLE coupon_discount_req ADD FOREIGN KEY (patient_brand_id) REFERENCES brands(id) ;
-- ALTER TABLE coupon_discount_req ADD FOREIGN KEY (area_id) REFERENCES areas(id) ;
-- ALTER TABLE coupon_discount_req ADD FOREIGN KEY (referrer_id) REFERENCES referrers(id) ;

--                        Coupon Discount Table
ALTER TABLE coupon_discount DROP COLUMN patient_brand_id;
ALTER TABLE coupon_discount DROP COLUMN area_id;
ALTER TABLE coupon_discount DROP COLUMN referrer_id;


--                        Coupon Discount Request Audit Table
ALTER TABLE coupon_disc_req_audit DROP COLUMN patient_brand_id;
ALTER TABLE coupon_disc_req_audit DROP COLUMN area_id;
ALTER TABLE coupon_disc_req_audit DROP COLUMN referrer_id;

-- ALTER TABLE coupon_disc_req_audit ADD FOREIGN KEY (patient_brand_id) REFERENCES brands(id) ;
-- ALTER TABLE coupon_disc_req_audit ADD FOREIGN KEY (area_id) REFERENCES areas(id) ;
-- ALTER TABLE coupon_disc_req_audit ADD FOREIGN KEY (referrer_id) REFERENCES referrers(id) ;


DROP TABLE IF EXISTS areas;
DROP TABLE IF EXISTS referrers;
DROP TABLE IF EXISTS coupon_area_mapping;
DROP TABLE IF EXISTS coupon_referrer_mapping;
DROP TABLE IF EXISTS coupon_area_mapping_audit;
DROP TABLE IF EXISTS coupon_referrer_mapping_audit;

ALTER TABLE coupon_audit DROP COLUMN areas_update;
ALTER TABLE coupon_audit DROP COLUMN referrers_update;

ALTER TABLE coupon_discount DROP COLUMN discount_amount;
