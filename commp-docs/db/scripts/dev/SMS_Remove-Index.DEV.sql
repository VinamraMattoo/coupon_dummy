-- ---------------------------------------------------------------------------------------------------- --
-- 				                     	Portea Communication Platform					                --
-- 			                   Drop Index Script for tables for SMS Engine				        --
-- ---------------------------------------------------------------------------------------------------- --

START TRANSACTION;

DROP INDEX `status` ON smsen_sms_gateway;
DROP INDEX `failure_count` ON smsen_sms_gateway;

DROP INDEX `scheduledTime` ON smsen_sms_queue;
DROP INDEX `send_before` ON smsen_sms_queue;

DROP INDEX `scheduled_time` ON smsen_sms_assembly;
DROP INDEX `primary_processing_status` ON smsen_sms_assembly;
DROP INDEX `secondary_processing_status` ON smsen_sms_assembly;
DROP INDEX `send_before` ON smsen_sms_assembly;
DROP INDEX `correlation_id` ON smsen_sms_assembly;
DROP INDEX `status_check_trial_count` ON smsen_sms_assembly;

DROP INDEX `mobile_number` ON smsen_sms_sent_cooling_data;
DROP INDEX `message_hash` ON smsen_sms_sent_cooling_data;
DROP INDEX `msg_content_expires` ON smsen_sms_sent_cooling_data;
DROP INDEX `sms_type_name` ON smsen_sms_sent_cooling_data;
DROP INDEX `sms_type_expires` ON smsen_sms_sent_cooling_data;

DROP INDEX `name` ON `smsen_sms_group`;
DROP INDEX `priority` ON `smsen_sms_group_gateway_mapping`;
DROP INDEX `created_on` ON `smsen_sms_audit`;
DROP INDEX `primary_processing_status` ON `smsen_sms_audit`;
DROP INDEX `secondary_processing_status` ON `smsen_sms_audit`;
DROP INDEX `source_name` ON `smsen_sms_record`;

COMMIT;