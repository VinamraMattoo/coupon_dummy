-- ---------------------------------------------------------------------------------------------------- --
-- 				                      	Portea Common Platform				                                        --
-- 				       Creation Index Script for common configuration tables                                  --
-- ---------------------------------------------------------------------------------------------------- --

START TRANSACTION;

CREATE INDEX `type_name` ON `cmn_config_target_type` (`type_name`);

CREATE INDEX `name` ON `cmn_config_param` (`name`);

CREATE INDEX `active` ON `cmn_target_config` (`active`);
CREATE INDEX `target_id` ON `cmn_target_config` (`target_id`);

COMMIT;