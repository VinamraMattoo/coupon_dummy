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

--
-- table `areas` - read only
--

CREATE TABLE IF NOT EXISTS `areas` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
-- table `referrers` - read only
--

CREATE TABLE IF NOT EXISTS `referrers` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `brandId` int(11) unsigned DEFAULT NULL,
  `referrerType` enum('B2B', 'B2C') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--          Coupon Area Mapping Table
CREATE TABLE IF NOT EXISTS `coupon_area_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `area_id` int(10) unsigned NOT NULL,
  `applicable` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--          Coupon Referrer Mapping Table
CREATE TABLE IF NOT EXISTS `coupon_referrer_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `referrer_id` int(10) unsigned NOT NULL,
  `applicable` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--          Coupon Area Mapping Audit Table
CREATE TABLE IF NOT EXISTS `coupon_area_mapping_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_audit_id` int(11) DEFAULT NULL,
  `area_id` int(10) unsigned NOT NULL,
  `applicable` BIT(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_audit_id`) REFERENCES `coupon_audit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--          Coupon Referrer Mapping Audit Table
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
