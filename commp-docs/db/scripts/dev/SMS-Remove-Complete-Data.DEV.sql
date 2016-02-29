-- ---------------------------------------------------------------------------------------------------- --
-- 				                     	Portea Communication Platform					                --
-- 			                       Data Removal Script for tables for SMS Engine				        --
-- ---------------------------------------------------------------------------------------------------- --

START TRANSACTION;

SET foreign_key_checks = 0;
TRUNCATE TABLE auth_users;
TRUNCATE TABLE auth_user_role_mapping;
TRUNCATE TABLE auth_roles;
TRUNCATE TABLE patient_details;
TRUNCATE TABLE brands;
TRUNCATE TABLE sms_template;
TRUNCATE TABLE smsen_sms_sender;
TRUNCATE TABLE smsen_sms_gateway_log;
TRUNCATE TABLE smsen_sms_type;
TRUNCATE TABLE smsen_sms_gateway;
TRUNCATE TABLE smsen_sms_group_gateway_mapping;
TRUNCATE TABLE smsen_sms_sender_audit;
TRUNCATE TABLE smsen_sms_group_gateway_mapping_audit;
TRUNCATE TABLE smsen_sms_group;
TRUNCATE TABLE smsen_sms_gateway_status_audit;
TRUNCATE TABLE smsen_sms_notification;
TRUNCATE TABLE smsen_sms_queue;
TRUNCATE TABLE smsen_sms_assembly;
TRUNCATE TABLE smsen_sms_record;
TRUNCATE TABLE smsen_sms_audit;
TRUNCATE TABLE smsen_sms_group_audit;
TRUNCATE TABLE smsen_sms_type_audit;
TRUNCATE TABLE smsen_sms_sent_cooling_data;
TRUNCATE TABLE smsen_sms_lot;
TRUNCATE TABLE smsen_sms_message_batch;
TRUNCATE TABLE smsen_sms_message_batch_record_mapping;
TRUNCATE TABLE smsen_sms_user_throttling_data;
SET foreign_key_checks = 1;

commit;