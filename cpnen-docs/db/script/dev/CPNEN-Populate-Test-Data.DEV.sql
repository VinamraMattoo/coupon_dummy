  --  -----------------------------------------------------------------------------------
  --            Script to populate test data which is used by unit tests             --
  --  -----------------------------------------------------------------------------------

  INSERT INTO `auth_users` (`id`, `login`    , `name`  , `password`                                                    , `firstName`, `middleName`, `lastName`, `phoneNumber`, `mobileNumber`, `date_of_birth`,`type`) VALUES
                           (1, 'tester', 'sample tester', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'mobileNumber', '2015-11-02', 'staff'),
                           (2, 'tester1', 'sample tester1', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'mobileNumber', '2015-11-02', 'patient'),
                           (3, 'tester2', 'Admin', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'mobileNumber', '2015-11-02', 'patient'),
                           (4, 'tester3', 'Autumn', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'mobileNumber', '2015-11-02', 'patient'),
                           (5, 'tester4', 'talentedBob', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'mobileNumber', '2015-11-02', 'health_professional'),
                           (6, 'tester5', 'Daisy', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'mobileNumber', '2015-11-02', 'health_professional'),
                           (7, 'tester6', 'Ivy', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'mobileNumber', '2015-11-02', 'patient'),
                           (8, 'tester7', 'Imagine', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'mobileNumber', '2015-11-02', 'patient'),
                           (9, 'tester8', 'Phoenix', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'mobileNumber', '2015-11-02', 'patient'),
                           (10, 'tester9', 'September', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'mobileNumber', '2015-11-02', 'patient');

  INSERT INTO `auth_roles` (`id`, `parent_id`, `name`,                 `description`) VALUES
                           (1,     -1        , 'Basic',                '..'),
                           (2,      5        , 'Coupon Admin',    '..'),
                           (3,     -1        , 'Sales',                '..'),
                           (4,     -1        , 'Center Manager',       '..'),
                           (5,      4        , 'WFM Admin',            '..'),
                           (6,     -1        , 'Ops',                  '..'),
                           (7,      2        , 'Coupon Manager Sales',                  '..'),
                           (8,      2        , 'Coupon Manager Marketing',                  '..'),
                           (9,      2        , 'Coupon Manager Ops',                  '..'),
                           (10,     2        , 'Coupon Manager Engagement',                  '..');

  INSERT INTO `auth_user_role_mapping` (`id`, `role_id`, `user_id`) VALUES
                                       (1,    4,         1),
                                       (2,    2,         1),
                                       (3,    3,         1),
                                       (4,    5,         1),
                                       (5,    1,         2),
                                       (6,    2,         3),
                                       (7,    5,         4);

  INSERT INTO `brands` (`id` , `name`) VALUES
                       (1 , 'Portea'),
                       (2 , 'Manipal'),
                       (3 , 'Medanta'),
                       (4 , 'Cloudine'),
                       (5 , 'Pushpanjali'),
                       (6 , 'Religare'),
                       (7 , 'ICICI-Labs');


  INSERT INTO `areas` (`id` , `name`) VALUES
                       (1 , 'Hyderabad'),
                       (2 , 'Bangalore'),
                       (3 , 'Pune'),
                       (4 , 'Chennai'),
                       (5 , 'NCR'),
                       (6 , 'Gwalior'),
                       (7 , 'Cochin');

  INSERT INTO `referrers` (`id` , `name`, `brandId`, `referrerType`) VALUES
                            (1 , 'JustDial', 1, 'B2C'),
                            (2 , 'Apna Care', 1, 'B2B'),
                            (3 , 'Max', 4, 'B2B'),
                            (4 , 'Religare', 1, 'B2B'),
                            (5 , 'Practo', 1, 'B2C'),
                            (6 , 'Groupon', 1, 'B2C'),
                            (7 , 'Sulekha', 1, 'B2C'),
                            (8 , 'Vikram Hospital', 1, 'B2B'),
                            (9 , 'Medanta Hospital', 7, 'B2B'),
                            (10 , 'Manipal Hospital(Old Airport Road)', 1, 'B2B');



  INSERT INTO `services` (`id`, `sub_service`, `name`          , `display_name`, `parentId`, `deleted`) VALUES
                         (1,  0            , 'DummyService1' ,  'Service1'     , 0         , 1 ),
                         (2,  0            , 'DummyService2' ,  'Service2'     , 0         , 0 ),
                         (3,  0            , 'DummyService3' ,  'Service3'     , 1         , 0 ),
                         (4,  1            , 'DummyService4' ,  'Service4'     , 0         , 0 ),
                         (5,  0            , 'DummyService5' ,  'Service5'     , 0         , 0 ),
                         (6,  0            , 'DummyService6' ,  'Service6'     , 0         , 0 ),
                         (7,  0            , 'DummyService7' ,  'Service7'     , 0         , 0 ),
                         (8,  0            , 'DummyService8' ,  'Service8'     , 0         , 0 ),
                         (9,  0            , 'DummyService9' ,  'Service9'     , 1         , 0 ),
                         (10, 1            , 'DummyService10' , 'Service10'    , 0         , 0 ),
                         (11, 0            , 'DummyService11' , 'Service11'    , 0         , 0 ),
                         (12, 0            , 'DummyService12' , 'Service12'    , 0         , 0 ),
                         (13, 0            , 'DummyService13' , 'Service13'    , 0         , 0 ),
                         (14, 0            , 'DummyService14' , 'Service14'    , 1         , 0 ),
                         (15, 1            , 'DummyService15' , 'Service15'    , 0         , 0 ),
                         (16, 0            , 'DummyService16' , 'Service16'    , 0         , 0 ),
                         (17, 0            , 'DummyService17' , 'Service17'    , 0         , 0 ),
                         (18, 0            , 'DummyService18' , 'Service18'    , 0         , 0 ),
                         (19, 0            , 'DummyService19' , 'Service19'    , 1         , 0 ),
                         (20, 1            , 'DummyService20' , 'Service20'    , 0         , 0 ),
                         (21, 0            , 'DummyService21' , 'Service21'    , 0         , 0 );


  INSERT INTO `packages` (`id`, `name`          , `description` , `deleted`) VALUES
                         (1,  'DummyPackage1' ,  'Package1'    , 0     ),
                         (2,  'DummyPackage2' ,  'Package2'    , 0     ),
                         (3,  'DummyPackage3' ,  'Package3'    , 1     ),
                         (4,  'DummyPackage4' ,  'Package4'    , 0     ),
                         (5,  'DummyPackage5' ,  'Package5'    , 0     ),
                         (6,  'DummyPackage6' ,  'Package6'    , 0     ),
                         (7,  'DummyPackage7' ,  'Package7'    , 0     ),
                         (8,  'DummyPackage8' ,  'Package8'    , 0     ),
                         (9,  'DummyPackage9' ,  'Package9'    , 1     ),
                         (10, 'DummyPackage10' , 'Packag10'    , 0     ),
                         (11, 'DummyPackage11' , 'Packag11'    , 0     ),
                         (12, 'DummyPackage12' , 'Packag12'    , 0     ),
                         (13, 'DummyPackage13' , 'Packag13'    , 0     ),
                         (14, 'DummyPackage14' , 'Packag14'    , 1     ),
                         (15, 'DummyPackage15' , 'Packag15'    , 0     ),
                         (16, 'DummyPackage16' , 'Packag16'    , 0     ),
                         (17, 'DummyPackage17' , 'Packag17'    , 0     ),
                         (18, 'DummyPackage18' , 'Packag18'    , 0     ),
                         (19, 'DummyPackage19' , 'Packag19'    , 1     ),
                         (20, 'DummyPackage20' , 'Packag20'    , 0     ),
                         (21, 'DummyPackage21' , 'Packag21'    , 0     );

  INSERT INTO `coupon`
  (`id`, `applicable_from`                  , `applicable_till`                 ,     `applicable_use_count`,   `category`,     `application_type`,             `actor_type`,           `created_on`,         `deactivated_on`,        `description`,                  `inclusive`,  `name`,                                            `global`,  `is_for_all_areas`,   `is_for_all_products`,   `is_for_all_brands`,   `is_for_all_b2b`,    `is_for_all_b2c`,      `discount_amt_max`,   `discount_amt_min`, `trans_val_max`, `trans_val_min`, `created_by`, `deactivated_by`,       `context_type`,          `last_updated_on`,                  `last_updated_by`, `nth_time`, `nth_time_recurring`,`track_use_across_codes`,`published_by`,`published_on`) VALUES
  (1,    DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 1 DAY),               12,                    'SALES',          'NTH_TIME',                     'CUSTOMER',            '2015-08-02 00:00:00', NULL,                    'the first coupon',             b'0',         'nth-time non-recurring global',                    b'1',           b'1',                   b'1',                   b'1',                  b'1',                b'1',                410,                   1,                  1000,             100,            1,             NULL,            'APPOINTMENT',           '2015-08-02 00:00:00',                    1,                 4,           b'0',               b'0',                  3,             '2015-08-02 00:00:00'),
  (2,    DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 3 DAY),               2,                     'MARKETING',      'ONE_TIME',                     'CUSTOMER',            '2015-08-02 00:00:00', NULL,                    'the second coupon',            b'0',         'one-time-coupon-global-coupon',                    b'1',           b'1',                   b'1',                   b'1',                  b'1',                b'1',                410,                   1,                  1000,             100,            1,             NULL,            'SUBSCRIPTION',          '2015-08-02 00:00:00',                    1,                 4,           b'1',               b'0',                  3,             '2015-08-02 00:00:00'),
  (3,    DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 5 DAY),               2,                     'ENGAGEMENT',     'ONE_TIME_PER_USER',            'STAFF',               '2015-08-02 00:00:00', NULL,                    'the third coupon',             b'0',         'one-time-per-user-global-coupon',                  b'1',           b'1',                   b'1',                   b'1',                  b'1',                b'1',                548,                   1,                  1000,             100,            1,             NULL,            'APPOINTMENT',           '2015-08-02 00:00:00',                    1,                 4,           b'0',               b'0',                  3,             '2015-08-02 00:00:00'),
  (4,    DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 7 DAY),               10,                    'SALES',          'ONE_TIME_PER_USER_FIFO',       'STAFF',               '2015-08-03 00:00:00', NULL,                    '4th coupon',                   b'1',         'one-time-per-user-fifo-global-coupon',             b'1',           b'1',                   b'1',                   b'1',                  b'1',                b'1',                512,                   1,                  10010,            10,             1,             NULL,            'SUBSCRIPTION',          '2015-08-02 00:00:00',                    1,                 4,           b'0',               b'0',                  3,             '2015-08-02 00:00:00'),
  (5,    DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 9 DAY),               10,                    'MARKETING',      'MANY_TIMES',                   'CUSTOMER',            '2015-08-03 00:00:00', NULL,                    '5th coupon',                   b'1',         'many-times-global-coupon',                         b'1',           b'0',                   b'1',                   b'1',                  b'1',                b'1',                520,                   1,                  920,              1,              1,             NULL,            'APPOINTMENT',           '2015-08-02 00:00:00',                    1,                 4,           b'1',               b'0',                  3,             '2015-08-02 00:00:00'),
  (6,    DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 11 DAY),              10,                    'SALES',          'NTH_TIME_PER_SUBSCRIPTION',    'STAFF',               '2015-08-03 00:00:00', NULL,                    'coupon',                       b'1',         'nth-time-per-subscription-global-coupon',          b'1',           b'1',                   b'1',                   b'1',                  b'1',                b'1',                519,                   1,                  990,              199,            1,             NULL,            'APPOINTMENT',           '2015-08-02 00:00:00',                    1,                 4,           b'0',               b'0',                  3,             '2015-08-02 00:00:00'),
  (7,    DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 13 DAY),              10,                    'ENGAGEMENT',     'NTH_TIME_AB_PER_SUBSCRIPTION', 'STAFF',               '2015-08-03 00:00:00', NULL,                    'coupon',                       b'1',         'nth-time-ab-per-subscription-global-coupon',       b'1',           b'1',                   b'1',                   b'1',                  b'1',                b'1',                215,                   1,                  1037,             10,             1,             NULL,            'SUBSCRIPTION',          '2015-08-02 00:00:00',                    1,                 4,           b'0',               b'0',                  3,             '2015-08-02 00:00:00'),
  (8,    DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 17 DAY),              10,                    'MARKETING',      'MANY_TIMES',                   'CUSTOMER',            '2015-08-03 00:00:00', '2015-08-10 00:00:00',   'coupon',                       b'1',         'Deactivated coupon',                               b'1',           b'1',                   b'1',                   b'1',                  b'1',                b'1',                217,                   1,                  1093,             103,            1,             1,               'APPOINTMENT',           '2015-08-02 00:00:00',                    1,                 4,           b'1',               b'0',                  3,             '2015-08-02 00:00:00'),
  (9,    DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 20 DAY),              10,                    'SALES',          'MANY_TIMES',                   'CUSTOMER',            '2015-08-03 00:00:00', NULL,                    'coupon',                       b'1',         'Outside validity coupon',                          b'1',           b'1',                   b'1',                   b'1',                  b'1',                b'1',                280,                   1,                  1000,             1,              1,             NULL,            'APPOINTMENT',           '2015-08-02 00:00:00',                    1,                 4,           b'1',               b'0',                  3,             '2015-08-02 00:00:00'),
  (10,   DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 22 DAY),              10,                    'OPs',            'NTH_TIME',                     'CUSTOMER',            '2015-08-03 00:00:00', NULL,                    'coupon',                       b'1',         'nth-time recurring global',                        b'1',           b'1',                   b'1',                   b'1',                  b'1',                b'1',                111,                   1,                  1000,             13,             1,             NULL,            'APPOINTMENT',           '2015-08-02 00:00:00',                    1,                 4,           b'1',               b'0',                  3,             '2015-08-02 00:00:00'),
  (11,   DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 23 DAY),              10,                    'SALES',          'MANY_TIMES',                   'STAFF',               '2015-08-09 00:00:00', NULL,                    'coupon11',                     b'1',         'testcoupon',                                       b'0',           b'1',                   b'1',                   b'1',                  b'1',                b'1',                120,                   1,                  5000,             100,            1,             NULL,            'APPOINTMENT',           '2015-08-02 00:00:00',                    1,                 4,           b'0',               b'0',                  3,             '2015-08-02 00:00:00'),
  (12,   DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 28 DAY),              13,                    'OPs',            'MANY_TIMES',                   'STAFF',               '2015-08-02 00:00:00', NULL,                    'test coupon 12',               b'1',         'test coupon',                                      b'0',           b'1',                   b'1',                   b'1',                  b'1',                b'1',                1115,                  1,                  3000,             100,            1,             NULL,            'APPOINTMENT',           '2015-08-02 00:00:00',                    1,                 4,           b'0',               b'0',                  3,             '2015-08-02 00:00:00');

  INSERT INTO `coupon_code`
  (`id`, `code`,        `created_on`,            `deactivated_on`,       `coupon_id`,      `created_by`,    `deactivated_by`, `channel_name`) VALUES
  (1,    'C1-D1',      '2015-07-08 00:00:00',    NULL,                   1,                1,               NULL,             NULL),
  (2,    'C1-D2',      '2015-08-02 00:00:00',    NULL,                   2,                1,               NULL,             NULL),
  (3,    'C1-D3',      '2015-08-03 00:00:00',    NULL,                   3,                1,               NULL,             NULL),
  (4,    'C1-D4',      '2015-08-02 00:00:00',    NULL,                   4,                1,               NULL,             NULL),
  (5,    'C1-D5',      '2015-08-02 00:00:00',    NULL,                   5,                1,               NULL,             NULL),
  (6,    'C1-D6',      '2015-08-02 00:00:00',    NULL,                   6,                1,               NULL,             NULL),
  (7,    'C1-D7',      '2015-08-02 00:00:00',    NULL,                   7,                1,               NULL,             NULL),
  (8,    'C1-D8',      '2015-08-02 00:00:00',    NULL,                   8,                1,               NULL,             NULL),
  (9,    'C1-D9',      '2015-08-02 00:00:00',    NULL,                   9,                1,               NULL,             NULL),
  (10,   'C1-D10',     '2015-08-02 00:00:00',    NULL,                   10,               1,               NULL,             NULL),
  (11,   'C1-D11',     '2015-08-02 00:00:00',    NULL,                   11,               1,               NULL,             NULL),
  (12,   'C1-D12',     '2015-08-02 00:00:00',    NULL,                   12,               1,               NULL,             NULL);

  INSERT INTO `coupon_discounting_rule`
  (`id`,    `type`,       `created_on`,             `deactivated_on`, `description`, `disc_flat_amt`, `disc_percentage`, `coupon_id`, `created_by`) VALUES
  (NULL,    'FLAT',       '2015-08-02 00:00:00',    NULL,            'flat rule',    50,              NULL,              1,           1),
  (NULL,    'FLAT',       '2015-08-01 00:00:00',    NULL,            'blah',         145,             NULL,              2,           1),
  (NULL,    'PERCENTAGE', '2015-08-02 00:00:00',    NULL,            'discount!',    NULL,            10,                3,           1),
  (NULL,    'PERCENTAGE', '2015-08-02 00:00:00',    NULL,            'discount!',    NULL,            20,                4,           1),
  (NULL,    'PERCENTAGE', '2015-08-02 00:00:00',    NULL,            'discount!',    NULL,            30,                5,           1),
  (NULL,    'PERCENTAGE', '2015-08-02 00:00:00',    NULL,            'discount!',    NULL,            12,                6,           1),
  (NULL,    'PERCENTAGE', '2015-08-02 00:00:00',    NULL,            'discount!',    NULL,            11,                7,           1),
  (NULL,    'PERCENTAGE', '2015-08-02 00:00:00',    NULL,            'discount!',    NULL,            14,                8,           1),
  (NULL,    'PERCENTAGE', '2015-08-02 00:00:00',    NULL,            'discount!',    NULL,            16,                9,           1),
  (NULL,    'PERCENTAGE', '2015-08-02 00:00:00',    NULL,            'discount!',    NULL,            19,                10,          1),
  (NULL,    'PERCENTAGE', '2015-08-02 00:00:00',    NULL,            'discount!',    NULL,            55,                11,          1),
  (NULL,    'PERCENTAGE', '2015-08-02 00:00:00',    NULL,            'discount!',    NULL,            22,                12,          1);

  INSERT INTO `coupon_area_mapping`
  (`id`, `coupon_id`, `area_id`, `applicable`) VALUES
    (1,     1,         1,            1),
    (2,     2,         1,            1),
    (3,     3,         2,            1),
    (4,     1,         3,            1),
    (5,     1,         4,            1),
    (6,     6,         5,            1),
    (7,     5,         1,            1);

  INSERT INTO `coupon_brand_mapping`
  (`id`, `coupon_id`, `brand_id`, `applicable`) VALUES
    (1,     1,         1,            1),
    (2,     2,         1,            1),
    (3,     3,         2,            1),
    (4,     1,         3,            1),
    (5,     1,         4,            1),
    (6,     6,         5,            1),
    (7,     5,         1,            1);

  INSERT INTO `coupon_referrer_mapping`
  (`id`, `coupon_id`, `referrer_id`, `applicable`) VALUES
    (1,     1,         1,            1),
    (2,     2,         1,            1),
    (3,     3,         2,            1),
    (4,     1,         3,            1),
    (5,     1,         4,            1),
    (6,     6,         5,            1),
    (7,     5,         1,            1);

  INSERT INTO `coupon_discount_req`
  (`id`, `requester_id`, `beneficiary_id`, `area_id`, `patient_brand_id`, `referrer_id`, `client_context_id`, `client_context_type`, `total_cost`, `latest_updated_on`, `completed`, `status`, `source_name`, `within_subscription`) VALUES
    (1,       1,              1,                1,            1,              1,                NULL,                  'APPOINTMENT',    100,      '2016-02-11 16:19:12',   b'0',   'APPLIED',  'CLINICIAN_APP', b'1'),
    (2,       1,              1,                1,            2,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:13',    b'1',    'APPLIED',   'CLINICIAN_APP', b'1'),
    (3,       2,              2,                1,            3,              1,                '2',                  'APPOINTMENT',    100,      '2016-02-11 16:19:13',    b'1',    'APPLIED',   'CLINICIAN_APP', b'1'),
    (4,       1,              1,                2,            4,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'APPLIED',   'CLINICIAN_APP', b'1'),
    (5,       1,              1,                3,            5,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'APPLIED',   'CLINICIAN_APP', b'1'),
    (6,       1,              1,                4,            6,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'APPLIED',   'CLINICIAN_APP', b'1'),
    (7,       1,              1,                6,            1,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'APPLIED',   'CLINICIAN_APP', b'1'),
    (8,       1,              1,                4,            2,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'APPLIED',   'CLINICIAN_APP', b'1'),
    (9,       1,              1,                4,            3,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'APPLIED',   'CLINICIAN_APP', b'1'),
    (10,       1,              1,                3,            3,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'APPLIED',   'CLINICIAN_APP', b'1'),
    (11,       1,              1,                5,            3,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'APPLIED',   'CLINICIAN_APP', b'1'),
    (12,       1,              1,                4,            1,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'CANCELED',   'CLINICIAN_APP', b'1'),
    (13,       1,              1,                7,            2,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'CANCELED',   'CLINICIAN_APP', b'1'),
    (14,       1,              1,                3,            3,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'CANCELED',   'CLINICIAN_APP', b'1'),
    (15,       1,              1,                1,            4,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'CANCELED',   'CLINICIAN_APP', b'1'),
    (16,       1,              1,                1,            5,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'REQUESTED',   'CLINICIAN_APP', b'1'),
    (17,       1,              1,                2,            6,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'REQUESTED',   'CLINICIAN_APP', b'1'),
    (18,       1,              1,                3,            7,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'REQUESTED',   'CLINICIAN_APP', b'1'),
    (19,       1,              1,                4,            1,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'REQUESTED',   'CLINICIAN_APP', b'1'),
    (20,       1,              1,                5,            2,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'REQUESTED',   'CLINICIAN_APP', b'1'),
    (21,       1,              1,                6,            3,              1,                '1',                  'APPOINTMENT',    100,      '2016-02-11 16:19:14',    b'1',    'REQUESTED',   'CLINICIAN_APP', b'1');

  INSERT INTO `coupon_discount`
  (`id`, `coupon_disc_req_id`, `requester_id`, `beneficiary_id`, `patient_brand_id`, `area_id`, `referrer_id`, `client_context_id`, `client_context_type`, `total_cost`, `discount_amount`, `created_on`) VALUES
    (1,         2,                    1,            1,                  1,                1,          1,                '1',            'APPOINTMENT',          100,        10,       '2016-02-11 16:19:13'),
    (2,         3,                    2,            2,                  2,                1,          2,                '2',            'APPOINTMENT',          100,        20,       '2016-02-11 16:19:13'),
    (3,         4,                    1,            1,                  3,                2,          3,                '1',            'APPOINTMENT',          100,        30,       '2016-02-11 16:19:14'),
    (4,         4,                    1,            1,                  4,                3,          4,                '1',            'APPOINTMENT',          100,        40,       '2016-02-11 16:19:14'),
    (5,         4,                    1,            1,                  5,                4,          5,                '1',            'APPOINTMENT',          100,        20,       '2016-02-11 16:19:14'),
    (6,         4,                    1,            1,                  1,                5,          1,                '1',            'APPOINTMENT',          100,        10,       '2016-02-11 16:19:14'),
    (7,         4,                    1,            1,                  2,                1,          2,                '1',            'APPOINTMENT',          100,        11,       '2016-02-11 16:19:14'),
    (8,         4,                    1,            1,                  3,                2,          3,                '1',            'APPOINTMENT',          100,        12,       '2016-02-11 16:19:14'),
    (9,         4,                    1,            1,                  4,                1,          4,                '1',            'APPOINTMENT',          100,        13,       '2016-02-11 16:19:14'),
    (10,         4,                    1,            1,                  1,                2,          1,                '1',            'APPOINTMENT',          100,        25,       '2016-02-11 16:19:14'),
    (11,         5,                    1,            1,                  1,                1,          1,                '1',            'APPOINTMENT',          100,        45,       '2016-02-11 16:19:14');