-- ---------------------------------------------------------------------------------------------------- --
-- 				        	Portea Common Platform				                                        --
-- 				       Table Removal Script for common configuration tables                             --
-- ---------------------------------------------------------------------------------------------------- --

START TRANSACTION;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS cmn_config_target_type;
DROP TABLE IF EXISTS cmn_config_param;
DROP TABLE IF EXISTS cmn_target_config;
DROP TABLE IF EXISTS cmn_target_config_audit;
DROP TABLE IF EXISTS cmn_target_config_value;
SET foreign_key_checks = 1;

COMMIT;