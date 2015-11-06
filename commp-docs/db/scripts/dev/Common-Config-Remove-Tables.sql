-- ---------------------------------------------------------------------------------------------------- --
-- 				        	Portea Common Platform				                                        --
-- 				       Table Removal Script for common configuration tables                             --
-- ---------------------------------------------------------------------------------------------------- --


SET foreign_key_checks = 0;
DROP TABLE cmn_config_target_type;
DROP TABLE cmn_config_param;
DROP TABLE cmn_target_config;
DROP TABLE cmn_target_config_audit;
DROP TABLE cmn_target_config_value;
SET foreign_key_checks = 1;

commit;