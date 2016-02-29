-- ---------------------------------------------------------------------------------------------------- --
-- 				                	Portea Common Platform				                                        --
-- 				       Drop Index Script for common configuration tables                                --
-- ---------------------------------------------------------------------------------------------------- --

START TRANSACTION;

DROP INDEX `type_name` ON cmn_config_target_type;

DROP INDEX `name` ON cmn_config_param;

DROP INDEX `active` ON cmn_target_config;
DROP INDEX `target_id` ON cmn_target_config;

COMMIT;