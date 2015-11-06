-- ---------------------------------------------------------------------------------------------------- --
-- 				                     	Portea Communication Platform					                --
-- 			                       Populate Data Script for tables for SMS Engine				        --
-- ---------------------------------------------------------------------------------------------------- --

INSERT INTO `auth_user` (`id`) VALUES ('1');

-- ------------------------------------------------------------------------

INSERT INTO `brands` (`id`, `canSendSms`) VALUES (NULL, b'1');

-- ------------------------------------------------------------------------

INSERT INTO `patient_details` (`id`, `sendSmsAlert`, `loginId`) VALUES (NULL, b'1', '1');

-- ------------------------------------------------------------------------

INSERT INTO `sms_gateway` (`id`, `name`, `status`) VALUES
(NULL, 'PINNACLE', 'ACTIVE'),
(NULL, 'KAPS', 'ACTIVE'),
(NULL, 'MGAGE', 'ACTIVE'),
(NULL, 'MGAGE_PRIORITY', 'ACTIVE'),
(NULL, 'DUMMY_GATEWAY_1', 'ACTIVE'),
(NULL, 'DUMMY_GATEWAY_2', 'ACTIVE');

-- ------------------------------------------------------------------------

INSERT INTO `sms_type` (`id`, `clng_pd_content_match_type`, `clng_pd_content_match_val`,
 `description`, `expires_in`, `name`, `clng_pd_type_match_type`, `clng_pd_type_match_val`) VALUES
 (NULL, 'CALENDAR_DAY', '2', NULL, '120', 'DummySmsType1', 'ABSOLUTE_PERIOD', '1200'),
 (NULL, 'CALENDAR_DAY', '2', NULL, '120', 'DummySmsType2', 'ABSOLUTE_PERIOD', '1200');

-- ------------------------------------------------------------------------

INSERT INTO `sms_group` (`id`, `clng_pd_content_match_type`, `clng_pd_content_match_val`,
            `description`, `is_bulk`, `name`, `priority`, `clng_pd_type_match_type`,
            `clng_pd_type_match_val`, `type_id`)
VALUES (NULL, 'CALENDAR_DAY', '3', NULL, b'1', 'DummyGroup1', '1', 'ABSOLUTE_PERIOD', '1800',
            (select id from `sms_type` where name = 'DummySmsType1')),
       (NULL, 'CALENDAR_DAY', '3', NULL, b'1', 'DummyGroup2', '4', 'ABSOLUTE_PERIOD', '1800',
            (select id from `sms_type` where name = 'DummySmsType2'));

-- ------------------------------------------------------------------------

INSERT INTO `sms_template` (`id`, `is_active`, `templateName`, `templateId`, `sms_group_id`) VALUES
(NULL, b'1', 'DummyTemplate1', '2233419018', (select id from `sms_group` where name = 'DummyGroup1')),
(NULL, b'1', 'DummyTemplate2', '2233419019', (select id from `sms_group` where name = 'DummyGroup2'));

-- ------------------------------------------------------------------------

INSERT INTO `sms_group_gateway_mapping` (`id`, `priority`, `gateway_id`, `sms_group_id`)
VALUES (NULL, 10,
           (select id from `sms_gateway` where name = 'DUMMY_GATEWAY_1'),
           (select id from sms_group where name = 'DummyGroup1')),
       (NULL, 9,
           (select id from `sms_gateway` where name = 'DUMMY_GATEWAY_2'),
           (select id from sms_group where name = 'DummyGroup1')),
       (NULL, 9,
          (select id from `sms_gateway` where name = 'DUMMY_GATEWAY_1'),
          (select id from sms_group where name = 'DummyGroup2')),
       (NULL, 10,
          (select id from `sms_gateway` where name = 'DUMMY_GATEWAY_2'),
          (select id from sms_group where name = 'DummyGroup2'));

-- ------------------------------------------------------------------------

INSERT INTO `sms_queue` (`id`, `country_code`, `createdOn`, `message`, `mobileNumber`, `receiverType`,
`scheduledId`, `scheduledTime`, `scheduled_time_zone`, `scheduledType`, `send_before`, `brandId`, `sms_group_id`,
`template_id`, `userId`) VALUES (NULL, 'IND', '2015-09-01 00:00:00', 'hi, bye.', '938383821', 'clinician', '1',
'2015-09-08 00:00:00', 'IST', 'Appointment', '2015-09-23 00:00:00', '1', '1', '1', '1');

-- ------------------------------------------------------------------------

commit;