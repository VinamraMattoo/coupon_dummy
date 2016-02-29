-- ---------------------------------------------------------------------------------------------------- --
-- 				                     	Portea Communication Platform					                --
-- 			                       Populate Data Script for tables for SMS Engine				        --
-- ---------------------------------------------------------------------------------------------------- --

START TRANSACTION;

INSERT INTO `smsen_sms_gateway` (`id`, `name`, `status`) VALUES
(NULL, 'PINNACLE', 'ACTIVE'),
(NULL, 'MGAGE', 'ACTIVE'),
(NULL, 'MGAGE_PRIORITY', 'ACTIVE'),
(NULL, 'DUMMY_GATEWAY_1', 'ACTIVE'),
(NULL, 'DUMMY_GATEWAY_2', 'ACTIVE');


INSERT INTO `smsen_sms_type` (`id`, `clng_pd_content_match_type`, `clng_pd_content_match_val`,`description`,              `expires_in`,  `name`,         `clng_pd_type_match_type`, `clng_pd_type_match_val`) VALUES
                              (NULL, 'ABSOLUTE_PERIOD' ,           NULL ,                     'Promotional SMS',          NULL,          'Promotional',   NULL ,                     NULL ),
                              (NULL, 'ABSOLUTE_PERIOD' ,           NULL ,                     'Regular SMS',              NULL,          'Regular',       NULL ,                     NULL ),
                              (NULL, 'ABSOLUTE_PERIOD' ,           NULL ,                     'Transactional SMS',        NULL,          'Transactional', NULL ,                     NULL );

INSERT INTO `smsen_sms_group` (`id`, `clng_pd_content_match_type`, `clng_pd_content_match_val`,`description`, `is_bulk`, `name`,                   `priority`, `clng_pd_type_match_type`,`clng_pd_type_match_val`, `type_id`) VALUES
                              (NULL, NULL ,                        NULL,                       NULL,          b'1',      'DummyGroup4',            '3',        NULL ,                    NULL ,                    (select id from `smsen_sms_type` where name = 'DummySmsType1')),
                              (NULL, NULL ,                        NULL,                       NULL,          b'1',      'Default_marketing',      '6',        NULL ,                    NULL ,                    (select id from `smsen_sms_type` where name = 'marketing')),
                              (NULL, NULL ,                        NULL,                       NULL,          b'1',      'Default_internal',       '6',        NULL ,                    NULL ,                    (select id from `smsen_sms_type` where name = 'internal')),
                              (NULL, NULL ,                        NULL,                       NULL,          b'1',      'Default_alert',          '6',        NULL ,                    NULL ,                    (select id from `smsen_sms_type` where name = 'alert')),
                              (NULL, NULL ,                        NULL,                       NULL,          b'1',      'Default_Promotional',    '8',        NULL ,                    NULL ,                    (select id from `smsen_sms_type` where name = 'Promotional')),
                              (NULL, NULL ,                        NULL,                       NULL,          b'1',      'Default_Regular',        '6',        NULL ,                    NULL ,                    (select id from `smsen_sms_type` where name = 'Regular')),
                              (NULL, NULL ,                        NULL,                       NULL,          b'1',      'Default_Transactional',  '1',        NULL ,                    NULL ,                    (select id from `smsen_sms_type` where name = 'Transactional')),
                              (NULL, NULL ,                        NULL,                       NULL,          b'1',      'Default_DummySmsType1',  '2',        NULL ,                    NULL ,                    (select id from `smsen_sms_type` where name = 'DummySmsType1'));

INSERT INTO `smsen_sms_group_gateway_mapping` (`id`, `priority`  , `gateway_id`                                                     ,           `sms_group_id`) VALUES
                                              (NULL, 1,          (select id from `smsen_sms_gateway` where name = 'DUMMY_GATEWAY_1'),           (select id from smsen_sms_group where name = 'DummyGroup4')),
                                              (NULL, 1,          (select id from `smsen_sms_gateway` where name = 'MGAGE'),                     (select id from smsen_sms_group where name = 'Default_marketing')),
                                              (NULL, 1,          (select id from `smsen_sms_gateway` where name = 'PINNACLE'),                  (select id from smsen_sms_group where name = 'Default_internal')),
                                              (NULL, 1,          (select id from `smsen_sms_gateway` where name = 'PINNACLE'),                  (select id from smsen_sms_group where name = 'Default_alert')),
                                              (NULL, 1,          (select id from `smsen_sms_gateway` where name = 'MGAGE'),                     (select id from smsen_sms_group where name = 'Default_Promotional')),
                                              (NULL, 1,          (select id from `smsen_sms_gateway` where name = 'PINNACLE'),                  (select id from smsen_sms_group where name = 'Default_Regular')),
                                              (NULL, 1,          (select id from `smsen_sms_gateway` where name = 'PINNACLE'),                  (select id from smsen_sms_group where name = 'Default_Transactional')),
                                              (NULL, 1,          (select id from `smsen_sms_gateway` where name = 'DUMMY_GATEWAY_1'),           (select id from smsen_sms_group where name = 'Default_DummySmsType1'));
COMMIT;