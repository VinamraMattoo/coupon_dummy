-- ---------------------------------------------------------------------------------------------------- --
-- 				                     	Portea Communication Platform					                --
-- 			                       Table Removal Script for tables for SMS Engine				        --
-- ---------------------------------------------------------------------------------------------------- --
START TRANSACTION;

SET foreign_key_checks = 0;

DROP TABLE IF EXISTS auth_users;
DROP TABLE IF EXISTS auth_user_role_mapping;
DROP TABLE IF EXISTS auth_roles;
DROP TABLE IF EXISTS patient_details;
DROP TABLE IF EXISTS brands;
DROP TABLE IF EXISTS sms_template;
DROP TABLE IF EXISTS smsen_sms_sender;
DROP TABLE IF EXISTS smsen_sms_gateway_log;
DROP TABLE IF EXISTS smsen_sms_type;
DROP TABLE IF EXISTS smsen_sms_gateway;
DROP TABLE IF EXISTS smsen_sms_group_gateway_mapping;
DROP TABLE IF EXISTS smsen_sms_sender_audit;
DROP TABLE IF EXISTS smsen_sms_group_gateway_mapping_audit;
DROP TABLE IF EXISTS smsen_sms_group;
DROP TABLE IF EXISTS smsen_sms_gateway_status_audit;
DROP TABLE IF EXISTS smsen_sms_notification;
DROP TABLE IF EXISTS smsen_sms_queue;
DROP TABLE IF EXISTS smsen_sms_assembly;
DROP TABLE IF EXISTS smsen_sms_record;
DROP TABLE IF EXISTS smsen_sms_audit;
DROP TABLE IF EXISTS smsen_sms_group_audit;
DROP TABLE IF EXISTS smsen_sms_type_audit;
DROP TABLE IF EXISTS smsen_sms_sent_cooling_data;
DROP TABLE IF EXISTS smsen_sms_user_throttling_data;
DROP TABLE IF EXISTS smsen_sms_lot;
DROP TABLE IF EXISTS smsen_sms_message_batch;
DROP TABLE IF EXISTS smsen_sms_message_batch_record_mapping;

SET foreign_key_checks = 1;


commit;
