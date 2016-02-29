START TRANSACTION;

CREATE TABLE IF NOT EXISTS `auth_users` (
  `id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `patient_details` (
  `id` int(10) unsigned NOT NULL,
  `sendSmsAlert` tinyint(1) NOT NULL DEFAULT '1',
  `loginId` int(11) DEFAULT NULL,
  `brandId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginId` (`loginId`),
  KEY `brandId` (`brandId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `brands` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(64) NOT NULL,
  `canSendSms` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `sms_template` (
  `id` int(10) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `templateName` varchar(256) NOT NULL,
  `templateId` varchar(255) NOT NULL COMMENT 'Registered template Id as per SMS vendor',
  `sms_group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `smsen_sms_type` (
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

CREATE TABLE IF NOT EXISTS `smsen_sms_group` (
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
  FOREIGN KEY (`type_id`) REFERENCES `smsen_sms_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `smsen_sms_gateway` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `failure_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `smsen_sms_group_gateway_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `priority` int(11) DEFAULT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  `sms_group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_grp_gw` (`sms_group_id`,`gateway_id`),
  FOREIGN KEY (`sms_group_id`) REFERENCES `smsen_sms_group` (`id`),
  FOREIGN KEY (`gateway_id`) REFERENCES `smsen_sms_gateway` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `smsen_sms_type_gateway_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `priority` int(11) DEFAULT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  `sms_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_type_gw` (`sms_type_id`,`gateway_id`),
  FOREIGN KEY (`sms_type_id`) REFERENCES `smsen_sms_type` (`id`),
  FOREIGN KEY (`gateway_id`) REFERENCES `smsen_sms_gateway` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `smsen_sms_gateway_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(200) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `message` varchar(1024) DEFAULT NULL,
  `mobile_number` varchar(15) DEFAULT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`gateway_id`) REFERENCES `smsen_sms_gateway` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `smsen_sms_gateway_status_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `smsen_sms_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `smsen_sms_queue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country_code` varchar(8) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
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


CREATE TABLE IF NOT EXISTS `smsen_sms_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(200) DEFAULT NULL,
  `country_code` varchar(8) DEFAULT NULL,
  `gateway_status` varchar(32) DEFAULT NULL,
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
  `brand_id` int(10) unsigned NOT NULL,
  `gateway_id` int(11) DEFAULT NULL,
  `sms_group_id` int(11) DEFAULT NULL,
  `template_id` int(10) NOT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  `sms_requested_at` datetime DEFAULT NULL,
  `status_check_trial_count` int(11) DEFAULT NULL,
  `correlation_id_check_count` int(11) DEFAULT NULL,
  `gateway_finding_failure_count` int(11) DEFAULT NULL,
  `received_correlation_at` datetime DEFAULT NULL,
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

CREATE TABLE IF NOT EXISTS `smsen_sms_assembly` (
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
  `brand_id` int(10) unsigned NOT NULL,
  `sms_group_id` int(11) DEFAULT NULL,
  `template_id` int(10) NOT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  `record_id` bigint(20) DEFAULT NULL,
  `sms_requested_at` datetime DEFAULT NULL,
  `correlation_id_check_count` int(11) DEFAULT NULL,
  `status_check_trial_count` int(11) DEFAULT NULL,
  `gateway_finding_failure_count` int(11) DEFAULT NULL,
  `received_correlation_at` datetime DEFAULT NULL,
  `source_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `auth_users` (`id`),
  FOREIGN KEY (`template_id`) REFERENCES `sms_template` (`id`),
  FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  FOREIGN KEY (`record_id`) REFERENCES `smsen_sms_record` (`id`),
  FOREIGN KEY (`sms_group_id`) REFERENCES `smsen_sms_group` (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `smsen_sms_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlation_id` varchar(200) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
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


CREATE TABLE IF NOT EXISTS `smsen_sms_type_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clng_pd_content_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_content_match_val` int(11) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `expires_in` int(11) DEFAULT NULL,
  `clng_pd_type_match_type` varchar(32) DEFAULT NULL,
  `clng_pd_type_match_val` int(11) DEFAULT NULL,
  `sms_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`sms_type_id`) REFERENCES `smsen_sms_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



CREATE TABLE IF NOT EXISTS `smsen_sms_group_audit` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`clng_pd_content_match_type` varchar(32) DEFAULT NULL,
`clng_pd_content_match_val` int(11) DEFAULT NULL,
`created_on` datetime DEFAULT NULL,
`priority` int(11) DEFAULT NULL,
`clng_pd_type_match_type` varchar(32) DEFAULT NULL,
`clng_pd_type_match_val` int(11) DEFAULT NULL,
`sms_group_id` int(11) DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`sms_group_id`) REFERENCES `smsen_sms_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `smsen_sms_sent_cooling_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT NULL,
  `mobile_number` varchar(15) NOT NULL,
  `message_hash` int(11) DEFAULT NULL,
  `message` varchar(1024) DEFAULT NULL,
  `sms_type_name` varchar(32) DEFAULT NULL,
  `time_zone` varchar(32) NOT NULL,
  `sent_at` datetime DEFAULT NULL,
  `sms_type_expires` datetime DEFAULT NULL,
  `msg_content_expires` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `auth_users` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

commit;