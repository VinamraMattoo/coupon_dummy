-- ---------------------------------------------------------------------------------------------------- --
-- 				        	Portea Common Platform				                                        --
-- 				       Data Removal Script for common configuration tables                              --
-- ---------------------------------------------------------------------------------------------------- --

SET foreign_key_checks = 0;
TRUNCATE TABLE cmn_config_target_type;
TRUNCATE TABLE cmn_config_param;
TRUNCATE TABLE cmn_target_config;
TRUNCATE TABLE cmn_target_config_audit;
TRUNCATE TABLE cmn_target_config_value;
SET foreign_key_checks = 1;

commit;