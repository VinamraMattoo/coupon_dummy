-- ---------------------------------------------------------------------------------------------------- --
-- 				                     	Portea Communication Platform					                --
-- 			                  Create Index Script for tables for SMS Engine				        --
-- ---------------------------------------------------------------------------------------------------- --

START TRANSACTION;

CREATE INDEX `status` ON `smsen_sms_gateway` (`status`);
CREATE INDEX `failure_count` ON `smsen_sms_gateway` (`failure_count`);

CREATE INDEX `scheduledTime` ON `smsen_sms_queue` (`scheduledTime`);
CREATE INDEX `send_before` ON `smsen_sms_queue` (`send_before`);

CREATE INDEX `scheduled_time` ON `smsen_sms_assembly` (`scheduled_time`);
CREATE INDEX `primary_processing_status` ON `smsen_sms_assembly` (`primary_processing_status`);
CREATE INDEX `secondary_processing_status` ON `smsen_sms_assembly` (`secondary_processing_status`);
CREATE INDEX `send_before` ON `smsen_sms_assembly` (`send_before`);
CREATE INDEX `correlation_id` ON `smsen_sms_assembly` (`correlation_id`);
CREATE INDEX `status_check_trial_count` ON `smsen_sms_assembly` (`status_check_trial_count`);

CREATE INDEX `mobile_number` ON `smsen_sms_sent_cooling_data` (`mobile_number`);
CREATE INDEX `message_hash` ON `smsen_sms_sent_cooling_data` (`message_hash`);
CREATE INDEX `msg_content_expires` ON `smsen_sms_sent_cooling_data` (`msg_content_expires`);
CREATE INDEX `sms_type_name` ON `smsen_sms_sent_cooling_data` (`sms_type_name`);
CREATE INDEX `sms_type_expires` ON `smsen_sms_sent_cooling_data` (`sms_type_expires`);

CREATE INDEX `name` ON `smsen_sms_group` (`name`);
CREATE INDEX `priority` ON `smsen_sms_group_gateway_mapping` (`priority`);
CREATE INDEX `created_on` ON `smsen_sms_audit` (`created_on`);
CREATE INDEX `primary_processing_status` ON `smsen_sms_audit` (`primary_processing_status`);
CREATE INDEX `secondary_processing_status` ON `smsen_sms_audit` (`secondary_processing_status`);
CREATE INDEX `source_name` ON `smsen_sms_record` (`source_name`);

COMMIT;

