--                        Coupon Table
ALTER TABLE coupon ADD is_for_all_areas bit(1) DEFAULT NULL ;
ALTER TABLE coupon ADD is_for_all_brands bit(1) DEFAULT NULL ;
ALTER TABLE coupon ADD is_for_all_products bit(1) DEFAULT NULL ;
ALTER TABLE coupon ADD is_for_all_b2b bit(1) DEFAULT NULL ;
ALTER TABLE coupon ADD is_for_all_b2c bit(1) DEFAULT NULL ;

--                        Coupon Core Audit Table
ALTER TABLE coupon_core_audit ADD is_for_all_areas bit(1) DEFAULT NULL ;
ALTER TABLE coupon_core_audit ADD is_for_all_brands bit(1) DEFAULT NULL ;
ALTER TABLE coupon_core_audit ADD is_for_all_products bit(1) DEFAULT NULL ;
ALTER TABLE coupon_core_audit ADD is_for_all_b2b bit(1) DEFAULT NULL ;
ALTER TABLE coupon_core_audit ADD is_for_all_b2c bit(1) DEFAULT NULL ;

--                        Coupon Discount Request Table
ALTER TABLE coupon_discount_req ADD patient_brand_id int(10) unsigned DEFAULT NULL ;
ALTER TABLE coupon_discount_req ADD area_id int(10) unsigned DEFAULT NULL ;
ALTER TABLE coupon_discount_req ADD referrer_id int(10) unsigned DEFAULT NULL ;

-- ALTER TABLE coupon_discount_req ADD FOREIGN KEY (patient_brand_id) REFERENCES brands(id) ;
-- ALTER TABLE coupon_discount_req ADD FOREIGN KEY (area_id) REFERENCES areas(id) ;
-- ALTER TABLE coupon_discount_req ADD FOREIGN KEY (referrer_id) REFERENCES referrers(id) ;

--                        Coupon Discount Table
ALTER TABLE coupon_discount ADD patient_brand_id int(10) unsigned DEFAULT NULL ;
ALTER TABLE coupon_discount ADD area_id int(10) unsigned DEFAULT NULL ;
ALTER TABLE coupon_discount ADD referrer_id int(10) unsigned DEFAULT NULL ;


--                        Coupon Discount Request Audit Table
ALTER TABLE coupon_disc_req_audit ADD patient_brand_id int(10) unsigned DEFAULT NULL ;
ALTER TABLE coupon_disc_req_audit ADD area_id int(10) unsigned DEFAULT NULL ;
ALTER TABLE coupon_disc_req_audit ADD referrer_id int(10) unsigned DEFAULT NULL ;

-- ALTER TABLE coupon_disc_req_audit ADD FOREIGN KEY (patient_brand_id) REFERENCES brands(id) ;
-- ALTER TABLE coupon_disc_req_audit ADD FOREIGN KEY (area_id) REFERENCES areas(id) ;
-- ALTER TABLE coupon_disc_req_audit ADD FOREIGN KEY (referrer_id) REFERENCES referrers(id) ;

CREATE TABLE IF NOT EXISTS `coupon_area_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `area_id` int(10) unsigned NOT NULL,
  `applicable` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `coupon_referrer_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `referrer_id` int(10) unsigned NOT NULL,
  `applicable` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `coupon_area_mapping_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_audit_id` int(11) DEFAULT NULL,
  `area_id` int(10) unsigned NOT NULL,
  `applicable` BIT(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_audit_id`) REFERENCES `coupon_audit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `coupon_referrer_mapping_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_audit_id` int(11) DEFAULT NULL,
  `referrer_id` int(10) unsigned NOT NULL,
  `applicable` BIT(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_audit_id`) REFERENCES `coupon_audit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

ALTER TABLE coupon_audit ADD areas_update bit(1) DEFAULT NULL ;
ALTER TABLE coupon_audit ADD referrers_update bit(1) DEFAULT NULL ;

ALTER TABLE coupon_discount ADD discount_amount double precision DEFAULT NULL ;
