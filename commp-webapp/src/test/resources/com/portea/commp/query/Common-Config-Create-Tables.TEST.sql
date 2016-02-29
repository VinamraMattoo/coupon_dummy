-- ---------------------------------------------------------------------------------------------------- -- 
-- 				        	Portea Common Platform				                                        --
-- 				       Creation Script for common configuration tables                                  --
-- ---------------------------------------------------------------------------------------------------- -- 

START TRANSACTION;

CREATE TABLE IF NOT EXISTS `cmn_config_target_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(512) DEFAULT NULL,
  `type_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `cmn_config_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `value_data_type` varchar(32) DEFAULT NULL,
  `config_target_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_name_targetId` (`name`,`config_target_type_id`),
  FOREIGN KEY (`config_target_type_id`) REFERENCES `cmn_config_target_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `cmn_target_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `target_id` int(11) DEFAULT NULL,
  `config_param_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_targetId_param_Id` (`target_id`,`config_param_id`),
  FOREIGN KEY (`config_param_id`) REFERENCES `cmn_config_param` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;

-- --------------------------------------------------------


CREATE TABLE IF NOT EXISTS `cmn_target_config_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `new_value` varchar(128) DEFAULT NULL,
  `old_value` varchar(128) DEFAULT NULL,
  `target_config_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`target_config_id`) REFERENCES `cmn_target_config` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


-- --------------------------------------------------------


CREATE TABLE IF NOT EXISTS `cmn_target_config_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(256) DEFAULT NULL,
  `target_config_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_target_config_id` (`target_config_id`),
  FOREIGN KEY (`target_config_id`) REFERENCES `cmn_target_config` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ;


COMMIT;