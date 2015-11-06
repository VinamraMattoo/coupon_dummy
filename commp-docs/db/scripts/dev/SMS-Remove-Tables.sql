-- ---------------------------------------------------------------------------------------------------- --
-- 				                     	Portea Communication Platform					                --
-- 			                       Table Removal Script for tables for SMS Engine				        --
-- ---------------------------------------------------------------------------------------------------- --

SET foreign_key_checks = 0;
DROP TABLE auth_user;
DROP TABLE patient_details;
DROP TABLE brands;
DROP TABLE sms_template;
DROP TABLE sms_type;
DROP TABLE sms_gateway;
DROP TABLE sms_group_gateway_mapping;
DROP TABLE sms_group;
DROP TABLE sms_gateway_log;
DROP TABLE sms_gateway_status_audit;
DROP TABLE sms_notification;
DROP TABLE sms_queue;
DROP TABLE sms_assembly;
DROP TABLE sms_record;
DROP TABLE sms_audit;
DROP TABLE sms_group_audit;
DROP TABLE sms_type_audit;
SET foreign_key_checks = 1;

commit;