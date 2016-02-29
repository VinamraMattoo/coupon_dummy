--
-- table `auth_users` - read only
--

CREATE TABLE IF NOT EXISTS `auth_users` (
  `id` int(10) unsigned NOT NULL,
  `login` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `middleName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(25) DEFAULT NULL,
  `mobileNumber` varchar(15) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `type` enum('staff', 'health_professional', 'patient') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


--
-- table `auth_roles` - read only
--

CREATE TABLE IF NOT EXISTS `auth_roles` (
  `id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `description` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
-- table `auth_user_role_mapping` - read only
--

CREATE TABLE IF NOT EXISTS `auth_user_role_mapping` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
-- table `coupon`
--

  CREATE TABLE IF NOT EXISTS `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  `last_updated_on` datetime DEFAULT NULL,
  `last_updated_by` int(10) unsigned DEFAULT NULL,
  `deactivated_on` datetime DEFAULT NULL,
  `deactivated_by` int(10) unsigned DEFAULT NULL,
  `inclusive` bit(1) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `application_type` varchar(255) DEFAULT NULL,
  `actor_type` varchar(255) DEFAULT NULL,
  `context_type` varchar(255) DEFAULT NULL,
  `applicable_from` datetime DEFAULT NULL,
  `applicable_till` datetime DEFAULT NULL,
  `applicable_use_count` int(11) DEFAULT NULL,
  `trans_val_max` int(11) DEFAULT NULL,
  `trans_val_min` int(11) DEFAULT NULL,
  `discount_amt_max` int(11) DEFAULT NULL,
  `discount_amt_min` int(11) DEFAULT NULL,
  `global` bit(1) DEFAULT NULL,
  `is_for_all_products` bit(1) DEFAULT NULL,
  `is_for_all_areas` bit(1) DEFAULT NULL,
  `is_for_all_brands` bit(1) DEFAULT NULL,
  `is_for_all_b2b` bit(1) DEFAULT NULL,
  `is_for_all_b2c` bit(1) DEFAULT NULL,
  `nth_time` int(11) DEFAULT NULL,
  `nth_time_recurring` bit(1) DEFAULT NULL,
  `published_on` datetime DEFAULT NULL,
  `published_by` int(10) unsigned DEFAULT NULL,
  `track_use_across_codes` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`created_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`last_updated_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`published_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`deactivated_by`) REFERENCES `auth_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


--
-- table `coupon_product_adapter`
--

CREATE TABLE IF NOT EXISTS `coupon_product_adapter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `product_id` int(11) unsigned DEFAULT NULL,
  `product_type` varchar(32) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


--
-- table `packages` - read only
--

CREATE TABLE IF NOT EXISTS `packages` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


--
-- table `services` - read only
--

CREATE TABLE IF NOT EXISTS `services` (
  `id` int(10) unsigned NOT NULL,
  `sub_service` tinyint(1) DEFAULT '0',
  `name` varchar(64) DEFAULT NULL,
  `display_name` varchar(256) DEFAULT NULL,
  `parentId` int(11) DEFAULT '0',
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


--
-- table `coupon_code`
--

CREATE TABLE IF NOT EXISTS `coupon_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `code` varchar(128) DEFAULT NULL,
  `channel_name` varchar(128) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  `deactivated_on` datetime DEFAULT NULL,
  `deactivated_by` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`deactivated_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`created_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
-- table `coupon_code_reservation`
--

CREATE TABLE IF NOT EXISTS `coupon_code_reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reservation_from` datetime DEFAULT NULL,
  `reservation_till` datetime DEFAULT NULL,
  `reservation_type` varchar(128) DEFAULT NULL,
  `code_id` int(11) DEFAULT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  `remarks` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`code_id`) REFERENCES `coupon_code` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
-- table `coupon_usage_summary`
--

CREATE TABLE IF NOT EXISTS `coupon_usage_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `total_use_cnt` int(11) DEFAULT NULL,
  `first_use_date` datetime DEFAULT NULL,
  `latest_use_date` datetime DEFAULT NULL,
  `trans_val_avg` int(11) DEFAULT NULL,
  `trans_val_highest` int(11) DEFAULT NULL,
  `trans_val_lowest` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


--
--  table `coupon_discounting_rule`
--

CREATE TABLE IF NOT EXISTS `coupon_discounting_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  `deactivated_on` datetime DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `disc_flat_amt` int(11) DEFAULT NULL,
  `disc_percentage` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`created_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


--
--  table `coupon_product_adapter_mapping`
--

CREATE TABLE IF NOT EXISTS `coupon_product_adapter_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `coupon_product_adapter_id` int(11) DEFAULT NULL,
  `applicable` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_product_adapter_id`) REFERENCES `coupon_product_adapter` (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


--
--  table `brands` - read only
--

CREATE TABLE IF NOT EXISTS `brands` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1;

--
--  table `coupon_brand_mapping`
--

CREATE TABLE IF NOT EXISTS `coupon_brand_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `brand_id` int(10) unsigned DEFAULT NULL,
  `applicable` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
-- table `areas` - read only
--

CREATE TABLE IF NOT EXISTS `areas` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
--  table `coupon_area_mapping`
--

CREATE TABLE IF NOT EXISTS `coupon_area_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `area_id` int(10) unsigned DEFAULT NULL,
  `applicable` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`area_id`) REFERENCES `areas` (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
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

--
--  table `coupon_referrer_mapping`
--

CREATE TABLE IF NOT EXISTS `coupon_referrer_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `referrer_id` int(10) unsigned DEFAULT NULL,
  `applicable` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`referrer_id`) REFERENCES `referrers` (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;



-- --------------------------------------------------------

--
-- table `coupon_discount_req`
--

CREATE TABLE IF NOT EXISTS `coupon_discount_req` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `requester_id` int(10) unsigned DEFAULT NULL,
  `beneficiary_id` int(10) unsigned DEFAULT NULL,
  `area_id` int(10) unsigned DEFAULT NULL,
  `patient_brand_id` int(10) unsigned DEFAULT NULL,
  `referrer_id` int(10) unsigned DEFAULT NULL,
  `client_context_id` varchar(32) DEFAULT NULL,
  `client_context_type` varchar(32) DEFAULT NULL,
  `total_cost` double precision DEFAULT NULL,
  `latest_updated_on` datetime DEFAULT NULL,
  `completed` bit(1) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `source_name` varchar(128) DEFAULT NULL,
  `within_subscription` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`requester_id`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`beneficiary_id`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`patient_brand_id`) REFERENCES `brands` (`id`),
  FOREIGN KEY (`area_id`) REFERENCES `areas` (`id`),
  FOREIGN KEY (`referrer_id`) REFERENCES `referrers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

--
-- Table `coupon_discount`
--

CREATE TABLE IF NOT EXISTS `coupon_discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_disc_req_id` int(11) DEFAULT NULL,
  `requester_id` int(10) unsigned DEFAULT NULL,
  `beneficiary_id` int(10) unsigned DEFAULT NULL,
  `patient_brand_id` int(10) unsigned DEFAULT NULL,
  `area_id` int(10) unsigned DEFAULT NULL,
  `referrer_id` int(10) unsigned DEFAULT NULL,
  `client_context_id` varchar(32) DEFAULT NULL,
  `client_context_type` varchar(255) DEFAULT NULL,
  `total_cost` double precision DEFAULT NULL,
  `discount_amount` double precision DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_disc_req_id`) REFERENCES `coupon_discount_req` (`id`),
  FOREIGN KEY (`requester_id`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`beneficiary_id`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`patient_brand_id`) REFERENCES `brands` (`id`),
  FOREIGN KEY (`area_id`) REFERENCES `areas` (`id`),
  FOREIGN KEY (`referrer_id`) REFERENCES `referrers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

--
-- Table `coupon_discount_code`
--

CREATE TABLE IF NOT EXISTS `coupon_discount_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_disc_id` int(11) DEFAULT NULL,
  `code_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_disc_id`) REFERENCES `coupon_discount` (`id`),
  FOREIGN KEY (`code_id`) REFERENCES `coupon_code` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table `coupon_discount_product`
--

CREATE TABLE IF NOT EXISTS `coupon_discount_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_disc_id` int(11) DEFAULT NULL,
  `coupon_product_adapter_id` int(11) DEFAULT NULL,
  `product_count` int(11) DEFAULT NULL,
  `product_unit_price` double precision DEFAULT NULL,
  `purchase_instance_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_disc_id`) REFERENCES `coupon_discount` (`id`),
  FOREIGN KEY (`coupon_product_adapter_id`) REFERENCES `coupon_product_adapter` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- table `coupon_discount_req_code`
--

CREATE TABLE IF NOT EXISTS `coupon_discount_req_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_disc_req_id` int(11) DEFAULT NULL,
  `code_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_disc_req_id`) REFERENCES `coupon_discount_req` (`id`),
  FOREIGN KEY (`code_id`) REFERENCES `coupon_code` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

--
-- table `coupon_discount_req_prod`
--

CREATE TABLE IF NOT EXISTS `coupon_discount_req_prod` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_disc_req_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `product_type` varchar(255) DEFAULT NULL,
  `product_count` int(11) DEFAULT NULL,
  `product_unit_price` double precision DEFAULT NULL,
  `purchase_instance_count` int(11) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_disc_req_id`) REFERENCES `coupon_discount_req` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_discount_summary`
--

CREATE TABLE IF NOT EXISTS `coupon_discount_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_use_date` datetime DEFAULT NULL,
  `latest_use_date` datetime DEFAULT NULL,
  `total_use_count` int(11) DEFAULT NULL,
  `trans_val_avg` int(11) DEFAULT NULL,
  `trans_val_highest` int(11) DEFAULT NULL,
  `trans_val_lowest` int(11) DEFAULT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table `coupon_disc_req_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_disc_req_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_disc_req_id` int(11) DEFAULT NULL,
  `requester_id` int(10) unsigned DEFAULT NULL,
  `beneficiary_id` int(10) unsigned DEFAULT NULL,
  `patient_brand_id` int(10) unsigned DEFAULT NULL,
  `area_id` int(10) unsigned DEFAULT NULL,
  `referrer_id` int(10) unsigned DEFAULT NULL,
  `client_context_id` varchar(32) DEFAULT NULL,
  `client_context_type` varchar(255) DEFAULT NULL,
  `total_cost` double precision DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `source_name` varchar(128) DEFAULT NULL,
  `within_subscription` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_disc_req_id`) REFERENCES `coupon_discount_req` (`id`),
  FOREIGN KEY (`requester_id`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`beneficiary_id`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`patient_brand_id`) REFERENCES `brands` (`id`),
  FOREIGN KEY (`area_id`) REFERENCES `areas` (`id`),
  FOREIGN KEY (`referrer_id`) REFERENCES `referrers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table `coupon_disc_req_code_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_disc_req_code_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cdr_audit_id` int(11) DEFAULT NULL,
  `code_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`cdr_audit_id`) REFERENCES `coupon_disc_req_audit` (`id`),
  FOREIGN KEY (`code_id`) REFERENCES `coupon_code` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table `coupon_disc_req_prod_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_disc_req_prod_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cdr_audit_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `product_count` int(11) DEFAULT NULL,
  `product_unit_price` double precision DEFAULT NULL,
  `purchase_instance_count` int(11) DEFAULT NULL,
  `remarks` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`cdr_audit_id`) REFERENCES `coupon_disc_req_audit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_engine_config`
--

CREATE TABLE IF NOT EXISTS `coupon_engine_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_engine_config_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_engine_config_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `new_value` varchar(256) DEFAULT NULL,
  `old_value` varchar(256) DEFAULT NULL,
  `cpn_engine_conf_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`cpn_engine_conf_id`) REFERENCES `coupon_engine_config` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- table `coupon_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `core_update` BIT(1) DEFAULT NULL,
  `brands_update` BIT(1) DEFAULT NULL,
  `areas_update` BIT(1) DEFAULT NULL,
  `referrers_update` BIT(1) DEFAULT NULL,
  `products_update` BIT(1) DEFAULT NULL,
  `discounting_rule_update` BIT(1) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
-- table `coupon_core_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_core_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_audit_id` int(11) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `last_updated_on` datetime DEFAULT NULL,
  `last_updated_by` int(10) unsigned DEFAULT NULL,
  `deactivated_on` datetime DEFAULT NULL,
  `deactivated_by` int(10) unsigned DEFAULT NULL,
  `inclusive` BIT(1) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `application_type` varchar(64) DEFAULT NULL,
  `actor_type` varchar(64) DEFAULT NULL,
  `context_type` varchar(64) DEFAULT NULL,
  `applicable_from` datetime DEFAULT NULL,
  `applicable_till` datetime DEFAULT NULL,
  `applicable_use_count` int(11) DEFAULT NULL,
  `trans_val_min` int(11) DEFAULT NULL,
  `trans_val_max` int(11) DEFAULT NULL,
  `discount_amt_max` int(11) DEFAULT NULL,
  `discount_amt_min` int(11) DEFAULT NULL,
  `global` BIT(1) DEFAULT NULL,
  `is_for_all_products` bit(1) DEFAULT NULL,
  `is_for_all_areas` bit(1) DEFAULT NULL,
  `is_for_all_brands` bit(1) DEFAULT NULL,
  `is_for_all_b2b` bit(1) DEFAULT NULL,
  `is_for_all_b2c` bit(1) DEFAULT NULL,
  `nth_time` int(11) DEFAULT NULL,
  `nth_time_recurring` BIT(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_audit_id`) REFERENCES `coupon_audit` (`id`),
  FOREIGN KEY (`last_updated_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`deactivated_by`) REFERENCES `auth_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


--
-- table `coupon_brand_mapping_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_brand_mapping_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_audit_id` int(11) DEFAULT NULL,
  `brand_id` int(10) unsigned DEFAULT NULL,
  `applicable` BIT(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_audit_id`) REFERENCES `coupon_audit` (`id`),
  FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
-- table `coupon_area_mapping_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_area_mapping_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_audit_id` int(11) DEFAULT NULL,
  `area_id` int(10) unsigned DEFAULT NULL,
  `applicable` BIT(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_audit_id`) REFERENCES `coupon_audit` (`id`),
  FOREIGN KEY (`area_id`) REFERENCES `areas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
-- table `coupon_referrer_mapping_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_referrer_mapping_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_audit_id` int(11) DEFAULT NULL,
  `referrer_id` int(10) unsigned DEFAULT NULL,
  `applicable` BIT(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_audit_id`) REFERENCES `coupon_audit` (`id`),
  FOREIGN KEY (`referrer_id`) REFERENCES `referrers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
-- table `coupon_product_adapter_mapping_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_product_adapter_mapping_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_audit_id` int(11) DEFAULT NULL,
  `coupon_product_adapter_id` int(11) DEFAULT NULL,
  `applicable` BIT(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_audit_id`) REFERENCES `coupon_audit` (`id`),
  FOREIGN KEY (`coupon_product_adapter_id`) REFERENCES `coupon_product_adapter` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
-- table `coupon_discounting_rule_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_discounting_rule_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_audit_id` int(11) DEFAULT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  `deactivated_on` datetime DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `disc_flat_amt` int(11) DEFAULT NULL,
  `disc_percentage` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`coupon_audit_id`) REFERENCES `coupon_audit` (`id`),
  FOREIGN KEY (`created_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

--
-- table `coupon_engine_comm_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_engine_comm_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip4` varchar(15) DEFAULT NULL,
  `request_timestamp` datetime DEFAULT NULL,
  `request_url` varchar(1024) DEFAULT NULL,
  `request_data` TEXT DEFAULT NULL,
  `response_timestamp` datetime DEFAULT NULL,
  `response_data` TEXT DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1;