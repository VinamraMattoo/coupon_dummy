-- ---------------------------------------------------------------------------------------------------- -- 
-- 				                     	Portea Communication Platform					                --
-- 			                       Creation Script for tables for SMS Engine				            --
-- ---------------------------------------------------------------------------------------------------- -- 

-- 'auth_user' is an existing table which will not be created in production. 
-- 'auth_user' may be used for testing before integration with Portea CRM

CREATE TABLE IF NOT EXISTS `auth_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 'patient_details' is an existing table which will not be created in production. 
-- 'patient_details' may be used for testing before integration with Portea CRM

CREATE TABLE IF NOT EXISTS `patient_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sendSmsAlert` bit(1) DEFAULT NULL,
  `loginId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 'brands' is an existing table which will not be created in production. 
-- 'brands' may be used for testing before integration with Portea CRM

CREATE TABLE IF NOT EXISTS `brands` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canSendSms` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 'sms_template' is an existing table which will not be created in production. 
-- 'sms_template' may be used for testing before integration with Portea CRM

CREATE TABLE IF NOT EXISTS `sms_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) DEFAULT NULL,
  `templateName` varchar(256) DEFAULT NULL,
  `templateId` varchar(255) DEFAULT NULL,
  `sms_group_id` int(11),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `sms_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clng_pd_content_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_content_match_val` int(11) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `expires_in` int(11) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `clng_pd_type_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_type_match_val` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `sms_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clng_pd_content_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_content_match_val` int(11) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `is_bulk` bit(1) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `clng_pd_type_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_type_match_val` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`type_id`) REFERENCES `sms_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `sms_gateway` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


-- --------------------------------------------------------


CREATE TABLE IF NOT EXISTS `sms_group_gateway_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `priority` int(11) DEFAULT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  `sms_group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1in0if1pv7d3bku872n9i47rb` (`sms_group_id`,`gateway_id`,`priority`),
  FOREIGN KEY (`sms_group_id`) REFERENCES `sms_group` (`id`),
  FOREIGN KEY (`gateway_id`) REFERENCES `sms_gateway` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

-- 'sms_gateway_log' is an existing table which will not be created in production. 
-- 'sms_gateway_log' may be used for testing before integration with Portea CRM

CREATE TABLE IF NOT EXISTS `sms_gateway_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(200) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `message` varchar(1024) DEFAULT NULL,
  `mobile_number` varchar(15) DEFAULT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`gateway_id`) REFERENCES `sms_gateway` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `sms_gateway_status_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `sms_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

-- 'sms_queue' is an existing table which will not be created in production. 
-- 'sms_queue' may be used for testing before integration with Portea CRM

CREATE TABLE IF NOT EXISTS `sms_queue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country_code` varchar(8) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `message` varchar(1024) DEFAULT NULL,
  `mobileNumber` varchar(15) DEFAULT NULL,
  `receiverType` varchar(128) DEFAULT NULL,
  `scheduledId` varchar(32) DEFAULT NULL,
  `scheduledTime` datetime DEFAULT NULL,
  `scheduled_time_zone` varchar(32) DEFAULT NULL,
  `scheduledType` varchar(32) DEFAULT NULL,
  `send_before` datetime DEFAULT NULL,
  `brandId` int(11) DEFAULT NULL,
  `sms_group_id` int(11) DEFAULT NULL,
  `template_id` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`userId`) REFERENCES `auth_user` (`id`),
  FOREIGN KEY (`sms_group_id`) REFERENCES `sms_group` (`id`),
  FOREIGN KEY (`brandId`) REFERENCES `brands` (`id`),
  FOREIGN KEY (`template_id`) REFERENCES `sms_template` (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- --------------------------------------------------------


CREATE TABLE IF NOT EXISTS `sms_assembly` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(200) DEFAULT NULL,
  `country_code` varchar(8) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `message` varchar(1024) DEFAULT NULL,
  `message_hash` int(11) DEFAULT NULL,
  `mobile_number` varchar(15) DEFAULT NULL,
  `receiver_type` varchar(128) DEFAULT NULL,
  `retry_count` int(11) DEFAULT NULL,
  `scheduled_id` varchar(32) DEFAULT NULL,
  `scheduled_time` datetime DEFAULT NULL,
  `scheduled_time_zone` varchar(32) DEFAULT NULL,
  `scheduled_type` varchar(32) DEFAULT NULL,
  `send_before` datetime DEFAULT NULL,
  `primary_processing_status` varchar(32) DEFAULT NULL,
  `secondary_processing_status` varchar(32) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  `sms_group_id` int(11) DEFAULT NULL,
  `template_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`),
  FOREIGN KEY (`template_id`) REFERENCES `sms_template` (`id`),
  FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  FOREIGN KEY (`sms_group_id`) REFERENCES `sms_group` (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `sms_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(200) DEFAULT NULL,
  `country_code` varchar(8) DEFAULT NULL,
  `gateway_status_code` varchar(32) DEFAULT NULL,
  `last_updated_on` datetime DEFAULT NULL,
  `message` varchar(1024) DEFAULT NULL,
  `message_hash` int(11) DEFAULT NULL,
  `mobile_number` varchar(15) DEFAULT NULL,
  `receiver_type` varchar(128) DEFAULT NULL,
  `retry_count` int(11) DEFAULT NULL,
  `scheduled_time` datetime DEFAULT NULL,
  `scheduled_time_zone` varchar(32) DEFAULT NULL,
  `scheduled_id` varchar(32) DEFAULT NULL,
  `scheduled_type` varchar(32) DEFAULT NULL,
  `send_before` datetime DEFAULT NULL,
  `primary_processing_status` varchar(255) DEFAULT NULL,
  `secondary_processing_status` varchar(255) DEFAULT NULL,
  `status_reason` varchar(256) DEFAULT NULL,
  `status_remarks` varchar(512) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  `sms_group_id` int(11) DEFAULT NULL,
  `template_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`),
  FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  FOREIGN KEY (`gateway_id`) REFERENCES `sms_gateway` (`id`),
  FOREIGN KEY (`template_id`) REFERENCES `sms_template` (`id`),
  FOREIGN KEY (`sms_group_id`) REFERENCES `sms_group` (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `sms_audit` (
  `id` bigint(20) NOT NULL,
  `correlation_id` varchar(200) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `gateway_status_code` varchar(255) DEFAULT NULL,
  `primary_processing_status` varchar(255) DEFAULT NULL,
  `secondary_processing_status` varchar(255) DEFAULT NULL,
  `status_reason` varchar(256) DEFAULT NULL,
  `status_remarks` varchar(512) DEFAULT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  `record_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`record_id`) REFERENCES `sms_record` (`id`),
  FOREIGN KEY (`gateway_id`) REFERENCES `sms_gateway` (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `sms_type_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clng_pd_content_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_content_match_val` int(11) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `expires_in` int(11) DEFAULT NULL,
  `clng_pd_type_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_type_match_val` int(11) DEFAULT NULL,
  `sms_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`sms_type_id`) REFERENCES `sms_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `sms_group_audit` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`clng_pd_content_match_type` varchar(32) DEFAULT NULL,
`clng_pd_content_match_val` int(11) DEFAULT NULL,
`created_on` datetime DEFAULT NULL,
`priority` int(11) DEFAULT NULL,
`clng_pd_type_match_type` varchar(32) DEFAULT NULL,
`clng_pd_type_match_val` int(11) DEFAULT NULL,
`sms_group_id` int(11) DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`sms_group_id`) REFERENCES `sms_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- -----------------------------------------------------------------------------

commit;