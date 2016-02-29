-- ---------------------------------------------------------------------------------------------------- --
-- 				                     	Portea Communication Platform					                --
-- 			                       Table Removal Script for tables for SMS Engine				        --
-- ---------------------------------------------------------------------------------------------------- --
START TRANSACTION;

SET foreign_key_checks = 0;

DROP TABLE auth_users;
DROP TABLE auth_user_role_mapping;
DROP TABLE auth_roles;
DROP TABLE patient_details;
DROP TABLE brands;
DROP TABLE sms_template;
DROP TABLE smsen_sms_sender;
DROP TABLE smsen_sms_gateway_log;
DROP TABLE smsen_sms_type;
DROP TABLE smsen_sms_gateway;
DROP TABLE smsen_sms_group_gateway_mapping;
DROP TABLE smsen_sms_type_gateway_mapping;
DROP TABLE smsen_sms_group;
DROP TABLE smsen_sms_gateway_status_audit;
DROP TABLE smsen_sms_notification;
DROP TABLE smsen_sms_queue;
DROP TABLE smsen_sms_assembly;
DROP TABLE smsen_sms_record;
DROP TABLE smsen_sms_audit;
DROP TABLE smsen_sms_group_audit;
DROP TABLE smsen_sms_type_audit;
DROP TABLE smsen_sms_sent_cooling_data;

SET foreign_key_checks = 1;


commit;
