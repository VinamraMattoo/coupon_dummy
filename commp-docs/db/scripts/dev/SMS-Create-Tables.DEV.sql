-- ---------------------------------------------------------------------------------------------------- -- 
-- 				                     	Portea Communication Platform					                --
-- 			                       Creation Script for tables for SMS Engine				            --
-- ---------------------------------------------------------------------------------------------------- --

START TRANSACTION;

-- 'auth_users' is an existing table which will not be created in production.
-- 'auth_users' may be used for testing before integration with Portea CRM

CREATE TABLE IF NOT EXISTS `auth_users` (
  `id` int(10) unsigned NOT NULL,
  `login` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `middleName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `mobileNumber` varchar(15) DEFAULT NULL,
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
-- table `auth_roles` - read only
--

CREATE TABLE IF NOT EXISTS `auth_roles` (
  `id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `description` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

-- 'patient_details' is an existing table which will not be created in production.
-- 'patient_details' may be used for testing before integration with Portea CRM

CREATE TABLE IF NOT EXISTS `patient_details` (
  `id` int(10) unsigned NOT NULL,
  `sendSmsAlert` tinyint(1) NOT NULL DEFAULT '1',
  `loginId` int(11) DEFAULT NULL,
  `brandId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginId` (`loginId`),
  KEY `brandId` (`brandId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 'brands' is an existing table which will not be created in production.
-- 'brands' may be used for testing before integration with Portea CRM

CREATE TABLE IF NOT EXISTS `brands` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(64) NOT NULL,
  `canSendSms` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 'sms_template' is an existing table which will not be created in production.
-- 'sms_template' may be used for testing before integration with Portea CRM

CREATE TABLE IF NOT EXISTS `sms_template` (
  `id` int(10) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `templateName` varchar(256) NOT NULL,
  `templateId` varchar(255) NOT NULL COMMENT 'Registered template Id as per SMS vendor',
  `sms_group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_sender` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `registered_on` datetime DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  `last_updated_on` datetime DEFAULT NULL,
  `last_updated_by` int(10) unsigned DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`created_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`last_updated_by`) REFERENCES `auth_users` (`id`),
  UNIQUE KEY `UK_sender_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_sender_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`created_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`sender_id`) REFERENCES `smsen_sms_sender` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clng_pd_content_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_content_match_val` int(11) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `expires_in` int(11) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `last_updated_on` datetime DEFAULT NULL,
  `last_updated_by` int(10) unsigned DEFAULT NULL,
  `clng_pd_type_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_type_match_val` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`last_updated_by`) REFERENCES `auth_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clng_pd_content_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_content_match_val` int(11) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `is_bulk` bit(1) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `last_updated_on` datetime DEFAULT NULL,
  `last_updated_by` int(10) unsigned DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `clng_pd_type_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_type_match_val` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`last_updated_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`type_id`) REFERENCES `smsen_sms_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_gateway` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `failure_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


-- --------------------------------------------------------


CREATE TABLE IF NOT EXISTS `smsen_sms_group_gateway_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `priority` int(11) DEFAULT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  `last_updated_on` datetime DEFAULT NULL,
  `last_updated_by` int(10) unsigned DEFAULT NULL,
  `sms_group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_grp_gw` (`sms_group_id`,`gateway_id`),
  FOREIGN KEY (`last_updated_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`sms_group_id`) REFERENCES `smsen_sms_group` (`id`),
  FOREIGN KEY (`gateway_id`) REFERENCES `smsen_sms_gateway` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------


CREATE TABLE IF NOT EXISTS `smsen_sms_group_gateway_mapping_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `old_priority` int(11) DEFAULT NULL,
  `new_priority` int(11) DEFAULT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  `sms_group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`created_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`sms_group_id`) REFERENCES `smsen_sms_group` (`id`),
  FOREIGN KEY (`gateway_id`) REFERENCES `smsen_sms_gateway` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_gateway_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(200) DEFAULT NULL,
  `created_on` DATETIME DEFAULT NULL ,
  `message` varchar(1024) DEFAULT NULL,
  `mobile_number` varchar(15) DEFAULT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`gateway_id`) REFERENCES `smsen_sms_gateway` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_gateway_status_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_on` DATETIME DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_queue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country_code` varchar(8) DEFAULT NULL,
  `createdOn` DATETIME DEFAULT NULL,
  `message` varchar(1024) DEFAULT NULL,
  `mobileNumber` varchar(15) DEFAULT NULL,
  `receiverType` varchar(128) DEFAULT NULL,
  `scheduleId` varchar(32) DEFAULT NULL,
  `scheduledTime` datetime DEFAULT NULL,
  `scheduled_time_zone` varchar(32) DEFAULT NULL,
  `scheduleType` varchar(32) DEFAULT NULL,
  `send_before` datetime DEFAULT NULL,
  `brandId` int(10) unsigned NOT NULL,
  `sms_group_id` int(11) DEFAULT NULL,
  `template_id` int(10) NOT NULL,
  `userId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`userId`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`sms_group_id`) REFERENCES `smsen_sms_group` (`id`),
  FOREIGN KEY (`brandId`) REFERENCES `brands` (`id`),
  FOREIGN KEY (`template_id`) REFERENCES `sms_template` (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- --------------------------------------------------------


CREATE TABLE IF NOT EXISTS `smsen_sms_lot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` DATETIME DEFAULT NULL,
  `sms_sender_id` INT(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`sms_sender_id`) REFERENCES `smsen_sms_sender` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- -----------------------------------------------------------------------------


CREATE TABLE IF NOT EXISTS `smsen_sms_message_batch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lot_id` INT(11) DEFAULT NULL,
  `type_id` INT(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`lot_id`) REFERENCES `smsen_sms_lot` (`id`),
  FOREIGN KEY (`type_id`) REFERENCES `smsen_sms_type` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(200) DEFAULT NULL,
  `country_code` varchar(8) DEFAULT NULL,
  `gateway_status` varchar(32) DEFAULT NULL,
  `last_updated_on` DATETIME DEFAULT NULL,
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
  `brand_id` int(10) unsigned NOT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  `sms_group_id` int(11) DEFAULT NULL,
  `template_id` int(10) DEFAULT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  `sms_requested_at` DATETIME,
  `status_check_trial_count` int(11) DEFAULT NULL,
  `correlation_id_check_count` int(11) DEFAULT NULL,
  `gateway_finding_failure_count` int(11) DEFAULT NULL,
  `received_correlation_at` DATETIME,
  `copy_of` bigint(20) DEFAULT NULL,
  `source_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  FOREIGN KEY (`gateway_id`) REFERENCES `smsen_sms_gateway` (`id`),
  FOREIGN KEY (`template_id`) REFERENCES `sms_template` (`id`),
  FOREIGN KEY (`copy_of`) REFERENCES `smsen_sms_record` (`id`),
  FOREIGN KEY (`sms_group_id`) REFERENCES `smsen_sms_group` (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- -----------------------------------------------------------------------------


CREATE TABLE IF NOT EXISTS `smsen_sms_message_batch_record_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_id` bigint(20) DEFAULT NULL,
  `message_batch_id` INT(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`record_id`) REFERENCES `smsen_sms_record` (`id`),
  FOREIGN KEY (`message_batch_id`) REFERENCES `smsen_sms_message_batch` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_assembly` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(200) DEFAULT NULL,
  `country_code` varchar(8) DEFAULT NULL,
  `created_on` DATETIME DEFAULT NULL,
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
  `brand_id` int(10) unsigned NOT NULL,
  `sms_group_id` int(11) DEFAULT NULL,
  `template_id` int(10) DEFAULT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  `record_id` bigint(20) DEFAULT NULL,
  `sms_requested_at` DATETIME DEFAULT NULL,
  `correlation_id_check_count` int(11) DEFAULT NULL,
  `status_check_trial_count` int(11) DEFAULT NULL,
  `gateway_finding_failure_count` int(11) DEFAULT NULL,
  `received_correlation_at` DATETIME DEFAULT NULL,
  `source_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`template_id`) REFERENCES `sms_template` (`id`),
  FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  FOREIGN KEY (`record_id`) REFERENCES `smsen_sms_record` (`id`),
  FOREIGN KEY (`sms_group_id`) REFERENCES `smsen_sms_group` (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;


-- --------------------------------------------------------


CREATE TABLE IF NOT EXISTS `smsen_sms_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(200) DEFAULT NULL,
  `created_on` DATETIME DEFAULT NULL,
  `gateway_status` varchar(32) DEFAULT NULL,
  `primary_processing_status` varchar(255) DEFAULT NULL,
  `secondary_processing_status` varchar(255) DEFAULT NULL,
  `status_reason` varchar(256) DEFAULT NULL,
  `status_remarks` varchar(512) DEFAULT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  `record_id` bigint(20) DEFAULT NULL,
  `response_code` varchar(128) DEFAULT NULL,
  `response_message` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`record_id`) REFERENCES `smsen_sms_record` (`id`),
  FOREIGN KEY (`gateway_id`) REFERENCES `smsen_sms_gateway` (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_type_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clng_pd_content_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_content_match_val` int(11) DEFAULT NULL,
  `created_on` DATETIME DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  `expires_in` int(11) DEFAULT NULL,
  `clng_pd_type_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_type_match_val` int(11) DEFAULT NULL,
  `sms_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`created_by`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`sms_type_id`) REFERENCES `smsen_sms_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_group_audit` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`clng_pd_content_match_type` varchar(32) DEFAULT NULL,
`clng_pd_content_match_val` int(11) DEFAULT NULL,
`created_on` DATETIME DEFAULT NULL,
`created_by` int(10) unsigned DEFAULT NULL,
`priority` int(11) DEFAULT NULL,
`clng_pd_type_match_type` varchar(32) DEFAULT NULL,
`clng_pd_type_match_val` int(11) DEFAULT NULL,
`sms_group_id` int(11) DEFAULT NULL,
 PRIMARY KEY (`id`),
 FOREIGN KEY (`created_by`) REFERENCES `auth_users` (`id`),
 FOREIGN KEY (`sms_group_id`) REFERENCES `smsen_sms_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `smsen_sms_sent_cooling_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT NULL,
  `mobile_number` varchar(15) NOT NULL,
  `message_hash` int(11) DEFAULT NULL,
  `message` varchar(1024) DEFAULT NULL,
  `sms_type_name` varchar(32) DEFAULT NULL,
  `time_zone` varchar(32) NOT NULL,
  `sent_at` DATETIME DEFAULT NULL,
  `sms_type_expires` datetime DEFAULT NULL,
  `msg_content_expires` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `auth_users` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `smsen_sms_user_throttling_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT NULL,
  `mobile_number` varchar(15) NOT NULL,
  `sms_type_name` varchar(32) DEFAULT NULL,
  `last_sent_at` DATETIME DEFAULT NULL,
  `begin_duration` datetime DEFAULT NULL,
  `end_duration` datetime DEFAULT NULL,
  `sent_count` int(11) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `auth_users` (`id`),
  UNIQUE KEY `UK_mn_tn_bd_ed` (`mobile_number`,`sms_type_name`,`begin_duration`,`end_duration`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- -----------------------------------------------------------------------------
commit;