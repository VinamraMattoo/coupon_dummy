-- ---------------------------------------------------------------------------------------------------- --
-- 				                     	Portea Communication Platform					                --
-- 			                       Populate Data Script for tables for SMS Engine				        --
-- ---------------------------------------------------------------------------------------------------- --

START TRANSACTION;

INSERT INTO `auth_users` (`id`, `login`, `password`, `firstName`, `middleName`, `lastName`) VALUES
  (1, 'tester', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName'),
  (2, 'tester1', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName'),
  (3, 'tester2', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName'),
  (4, 'tester3', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName'),
  (5, 'tester4', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName'),
  (6, 'tester5', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName'),
  (7, 'tester6', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName'),
  (8, 'tester7', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName'),
  (9, 'tester8', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName'),
  (10, 'tester9', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName',
   'lastName');

-- ------------------------------------------------------------------------

INSERT INTO `auth_roles` (`id`, `parent_id`, `name`, `description`) VALUES
  (1, -1, 'Basic', '..'),
  (2, 5, 'Sms manager', '..'),
  (3, -1, 'Sales', '..'),
  (4, -1, 'Center Manager', '..'),
  (5, 4, 'WFM Admin', '..'),
  (6, -1, 'Ops', '..');

-- ------------------------------------------------------------------------

INSERT INTO `auth_user_role_mapping` (`id`, `role_id`, `user_id`) VALUES
  (1, 4, 1),
  (2, 2, 1),
  (3, 3, 1),
  (4, 5, 1),
  (5, 1, 2),
  (6, 2, 3),
  (7, 5, 4);

-- ------------------------------------------------------------------------

INSERT INTO `brands` (`id`, `name`, `canSendSms`) VALUES (1, 'Portea', b'1');
INSERT INTO `brands` (`id`, `name`, `canSendSms`) VALUES (2, 'Manipal', b'1');

-- ------------------------------------------------------------------------

INSERT INTO `patient_details` (`id`, `sendSmsAlert`, `loginId`, `brandId`) VALUES
  (1, b'1', '1', (SELECT id
                  FROM brands
                  WHERE name = 'Portea'));

-- ------------------------------------------------------------------------

INSERT INTO `smsen_sms_gateway` (`id`, `name`, `status`) VALUES
  (NULL, 'PINNACLE', 'ACTIVE'),
  (NULL, 'MGAGE', 'ACTIVE'),
  (NULL, 'MGAGE_PRIORITY', 'ACTIVE'),
  (NULL, 'DUMMY_GATEWAY_1', 'ACTIVE'),
  (NULL, 'DUMMY_GATEWAY_2', 'ACTIVE');

-- ------------------------------------------------------------------------

INSERT INTO `smsen_sms_type` (`id`, `clng_pd_content_match_type`, `clng_pd_content_match_val`, `description`, `expires_in`, `name`, `clng_pd_type_match_type`, `clng_pd_type_match_val`)
VALUES
  (NULL, NULL, NULL, NULL, '120', 'DummySmsType1', NULL, NULL),
  (NULL, 'CALENDAR_DAY', '2', NULL, '120', 'DummySmsType2', 'ABSOLUTE_PERIOD', '1200');

-- ------------------------------------------------------------------------

INSERT INTO `smsen_sms_group` (`id`, `clng_pd_content_match_type`, `clng_pd_content_match_val`, `description`, `is_bulk`, `name`, `priority`, `clng_pd_type_match_type`, `clng_pd_type_match_val`, `type_id`)
VALUES (NULL, 'CALENDAR_DAY', '3', NULL, b'1', 'DummyGroup1', '1', 'ABSOLUTE_PERIOD', '1800', (SELECT id
                                                                                               FROM `smsen_sms_type`
                                                                                               WHERE name =
                                                                                                     'DummySmsType2')),
  (NULL, 'CALENDAR_DAY', '3', NULL, b'1', 'DummyGroup2', '2', 'ABSOLUTE_PERIOD', '1800', (SELECT id
                                                                                          FROM `smsen_sms_type`
                                                                                          WHERE
                                                                                            name = 'DummySmsType1')),
  (NULL, 'CALENDAR_DAY', '3', NULL, b'1', 'DummyGroup3', '1', 'ABSOLUTE_PERIOD', '1800', (SELECT id
                                                                                          FROM `smsen_sms_type`
                                                                                          WHERE
                                                                                            name = 'DummySmsType2')),
  (NULL, NULL, NULL, NULL, b'1', 'DummyGroup4', '3', NULL, NULL, (SELECT id
                                                                  FROM `smsen_sms_type`
                                                                  WHERE name = 'DummySmsType1'));

-- ------------------------------------------------------------------------

INSERT INTO `sms_template` (`id`, `is_active`, `templateName`, `templateId`, `sms_group_id`) VALUES
  (1, b'1', 'DummyTemplate1', '2233419018', (SELECT id
                                             FROM `smsen_sms_group`
                                             WHERE name = 'DummyGroup1')),
  (2, b'1', 'DummyTemplate2', '2233419019', (SELECT id
                                             FROM `smsen_sms_group`
                                             WHERE name = 'DummyGroup2'));

-- ------------------------------------------------------------------------

INSERT INTO `smsen_sms_group_gateway_mapping` (`id`, `priority`, `gateway_id`, `sms_group_id`)
VALUES (NULL, 1,
        (SELECT id
         FROM `smsen_sms_gateway`
         WHERE name = 'PINNACLE'),
        (SELECT id
         FROM smsen_sms_group
         WHERE name = 'DummyGroup1')),
  (NULL, 2,
   (SELECT id
    FROM `smsen_sms_gateway`
    WHERE name = 'MGAGE'),
   (SELECT id
    FROM smsen_sms_group
    WHERE name = 'DummyGroup1')),
  (NULL, 3,
   (SELECT id
    FROM `smsen_sms_gateway`
    WHERE name = 'MGAGE_PRIORITY'),
   (SELECT id
    FROM smsen_sms_group
    WHERE name = 'DummyGroup1')),
  (NULL, 1,
   (SELECT id
    FROM `smsen_sms_gateway`
    WHERE name = 'MGAGE'),
   (SELECT id
    FROM smsen_sms_group
    WHERE name = 'DummyGroup2')),
  (NULL, 1,
   (SELECT id
    FROM `smsen_sms_gateway`
    WHERE name = 'MGAGE_PRIORITY'),
   (SELECT id
    FROM smsen_sms_group
    WHERE name = 'DummyGroup3')),
  (NULL, 1,
   (SELECT id
    FROM `smsen_sms_gateway`
    WHERE name = 'DUMMY_GATEWAY_1'),
   (SELECT id
    FROM smsen_sms_group
    WHERE name = 'DummyGroup4'));

-- ------------------------------------------------------------------------

INSERT INTO `smsen_sms_type_gateway_mapping` (`id`, `priority`, `gateway_id`, `sms_type_id`)
VALUES (NULL, 1,
        (SELECT id
         FROM `smsen_sms_gateway`
         WHERE name = 'PINNACLE'),
        (SELECT id
         FROM smsen_sms_type
         WHERE name = 'DummySmsType1')),
  (NULL, 2,
   (SELECT id
    FROM `smsen_sms_gateway`
    WHERE name = 'MGAGE'),
   (SELECT id
    FROM smsen_sms_type
    WHERE name = 'DummySmsType1')),
  (NULL, 3,
   (SELECT id
    FROM `smsen_sms_gateway`
    WHERE name = 'MGAGE_PRIORITY'),
   (SELECT id
    FROM smsen_sms_type
    WHERE name = 'DummySmsType1')),
  (NULL, 4,
   (SELECT id
    FROM `smsen_sms_gateway`
    WHERE name = 'DUMMY_GATEWAY_1'),
   (SELECT id
    FROM smsen_sms_type
    WHERE name = 'DummySmsType1')),
  (NULL, 1,
   (SELECT id
    FROM `smsen_sms_gateway`
    WHERE name = 'DUMMY_GATEWAY_1'),
   (SELECT id
    FROM smsen_sms_type
    WHERE name = 'DummySmsType2')),
  (NULL, 2,
   (SELECT id
    FROM `smsen_sms_gateway`
    WHERE name = 'PINNACLE'),
   (SELECT id
    FROM smsen_sms_type
    WHERE name = 'DummySmsType2')),
  (NULL, 3,
   (SELECT id
    FROM `smsen_sms_gateway`
    WHERE name = 'MGAGE'),
   (SELECT id
    FROM smsen_sms_type
    WHERE name = 'DummySmsType2'));

-- ------------------------------------------------------------------------

COMMIT;