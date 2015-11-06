-- ---------------------------------------------------------------------------------------------------- --
-- 				                     	Portea Communication Platform					                --
-- 			                       Data Removal Script for tables for SMS Engine				        --
-- ---------------------------------------------------------------------------------------------------- --

SET foreign_key_checks = 0;
TRUNCATE TABLE auth_user;
TRUNCATE TABLE patient_details;
TRUNCATE TABLE brands;
TRUNCATE TABLE sms_template;
TRUNCATE TABLE sms_type;
TRUNCATE TABLE sms_gateway;
TRUNCATE TABLE sms_group_gateway_mapping;
TRUNCATE TABLE sms_group;
TRUNCATE TABLE sms_gateway_log;
TRUNCATE TABLE sms_gateway_status_audit;
TRUNCATE TABLE sms_notification;
TRUNCATE TABLE sms_queue;
TRUNCATE TABLE sms_assembly;
TRUNCATE TABLE sms_record;
TRUNCATE TABLE sms_audit;
TRUNCATE TABLE sms_group_audit;
TRUNCATE TABLE sms_type_audit;
SET foreign_key_checks = 1;

commit;