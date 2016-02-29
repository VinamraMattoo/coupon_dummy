-- ---------------------------------------------------------------------------------------------------- --
-- 				             	Portea Common Platform				                                    --
-- 				       Populate test data for common configuration tables                               --
-- ---------------------------------------------------------------------------------------------------- --

START TRANSACTION;

INSERT INTO `cmn_config_target_type` (`id`, `description`, `type_name`)
VALUES (NULL, NULL, 'SMS_GATEWAY'),
  (NULL, NULL, 'SMS_TEMPLATE'),
  (NULL, NULL, 'SMS_ENGINE');

-- --------------------------------------------------------

INSERT INTO `cmn_config_param` (`id`, `name`, `description`, `value_data_type`, `config_target_type_id`) VALUES
  (NULL, 'ENDPOINT_URL_SUBMISSION', '', 'TEXT', (SELECT id
                                                 FROM cmn_config_target_type
                                                 WHERE type_name = 'SMS_GATEWAY')),
  (NULL, 'RESTRICTED_CHARACTERS', '', 'TEXT', (SELECT id
                                               FROM cmn_config_target_type
                                               WHERE type_name = 'SMS_GATEWAY')),
  (NULL, 'INACTIVE_PERIOD_START', '', 'DATE', (SELECT id
                                               FROM cmn_config_target_type
                                               WHERE type_name = 'SMS_GATEWAY')),
  (NULL, 'INACTIVE_PERIOD_END', '', 'DATE', (SELECT id
                                             FROM cmn_config_target_type
                                             WHERE type_name = 'SMS_GATEWAY')),
  (NULL, 'ENDPOINT_URL_POLLING', '', 'TEXT', (SELECT id
                                              FROM cmn_config_target_type
                                              WHERE type_name = 'SMS_GATEWAY')),
  (NULL, 'MESSAGE_EXPIRY', '', 'DATE', (SELECT id
                                        FROM cmn_config_target_type
                                        WHERE type_name = 'SMS_TEMPLATE')),
  (NULL, 'RETRY_SAME_GATEWAY', '', 'BOOLEAN', (SELECT id
                                               FROM cmn_config_target_type
                                               WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'NEW_SMS_LOAD_WINDOW', '', 'NUMBER', (SELECT id
                                               FROM cmn_config_target_type
                                               WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'NEXT_SMS_BATCH_LOAD_WINDOW', '', 'NUMBER', (SELECT id
                                                      FROM cmn_config_target_type
                                                      WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'NEW_SMS_LOADER_INTERVAL', '', 'NUMBER', (SELECT id
                                                   FROM cmn_config_target_type
                                                   WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'NEXT_SMS_BATCH_LODER_INTERVAL', '', 'NUMBER', (SELECT id
                                                         FROM cmn_config_target_type
                                                         WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'USERNAME', '', 'TEXT', (SELECT id
                                  FROM cmn_config_target_type
                                  WHERE type_name = 'SMS_GATEWAY')),
  (NULL, 'USER_CREDENTIAL_PWD', '', 'TEXT', (SELECT id
                                             FROM cmn_config_target_type
                                             WHERE type_name = 'SMS_GATEWAY')),
  (NULL, 'SENDER_BRAND_1', '', 'TEXT', (SELECT id
                                        FROM cmn_config_target_type
                                        WHERE type_name = 'SMS_GATEWAY')),
  (NULL, 'SENDER_BRAND_2', '', 'TEXT', (SELECT id
                                        FROM cmn_config_target_type
                                        WHERE type_name = 'SMS_GATEWAY')),
  (NULL, 'NEW_SMS_LOAD_LIMIT', 'maximum number of sms that can be loaded from sms queue', 'NUMBER',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'NEW_SMS_COOLING_PERIOD_CHECK', 'newly loaded sms will be checked for cooling period based on this parameter
                                                    value', 'BOOLEAN',
                                                    (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'NEW_SMS_PHONE_NUMBER_VALIDATION', 'newly loaded sms will be checked for phone number validation based on this
                                                    parameter value', 'BOOLEAN',
                                                    (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'NEW_SMS_USER_DND_VALIDATION', 'newly loaded sms will be checked for user dnd based on this parameter
                                                    value', 'BOOLEAN',
                                                    (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'NEW_SMS_MESSAGE_FORMAT_VALIDATION','newly loaded sms will be checked for message format validation based on
                                                    this parameter value', 'BOOLEAN',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'MAX_SUBMISSION_COUNT_LOADED_TO_QUEUES','Determines how many maximum sms can be loaded in all processing queues
                                                    combined', 'NUMBER',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'SMS_SUBMISSION_WORKER_BATCH_MAX_SIZE','Determines how many maximum sms can be batched in a single sms
                                                    submission worker', 'NUMBER',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'GATEWAY_FINDING_FAILURE_RETRY_THRESHOLD','Determines how many times to retry a sms when gateway mapping is not
                                                   found before sending the it to retry queue', 'NUMBER',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'CORRELATION_ID_CHECK_MIN_WAIT_TIME','Determines the minimum time to wait before asking checking for
                                                    correlation id', 'NUMBER',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'CORRELATION_ID_CHECK_MAX_COUNT','Determines how many times to check for correlation id before sending it to
                                                    retry queue', 'NUMBER',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'SMS_SUBMISSION_WORKER_BATCH_SLEEP_TIME','Determines thread sleep time after every sms in a batch is
                                                    addressed', 'NUMBER',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'SMS_SUBMISSION_GATEWAY_FAILURE_COUNT_THRESHOLD','Determines how many successive gateway failures
                                                    before marking it as unreachable', 'NUMBER',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'GATEWAY_USAGE_POLICY','Determines which gateway to use for subsequent tries to a gateway', 'TEXT',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'UNSENT_SMS_RETRIAL_POLICY','Determines if a sms should be tried again, once it is rejected by a
                                                  gateway', 'TEXT',
                                                  (SELECT id
                                                  FROM cmn_config_target_type
                                                  WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'MAX_STATUS_CHECK_COUNT_LOADED_TO_QUEUES','Determines how many maximum sms can be loaded in all processing queues
                                                    combined', 'NUMBER',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'STATUS_CHECK_MAX_COUNT','Determines how many times to check for sms status', 'NUMBER',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'STATUS_CHECK_MIN_WAIT_TIME','Determines the minimum time to wait before asking checking for
                                                    sms status', 'NUMBER',
                                                   (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'SMS_STATUS_WORKER_BATCH_SLEEP_TIME','Determines sms status batch sleep time after every cycle', 'NUMBER',
                                                    (SELECT id
                                                    FROM cmn_config_target_type
                                                    WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'SMS_STATUS_CHECK_TIME_OUT_DURATION','Determines time out duration after which sms status
                                                     wont be checked', 'NUMBER',
                                                     (SELECT id
                                                      FROM cmn_config_target_type
                                                      WHERE type_name = 'SMS_ENGINE')),
  (NULL, 'RETRY_COUNT','Determines the number of times sms can be retried', 'NUMBER',
                                                     (SELECT id
                                                      FROM cmn_config_target_type
                                                      WHERE type_name = 'SMS_ENGINE'))

;


-- --------------------------------------------------------

INSERT INTO `cmn_target_config` (`id`, `active`, `target_id`, `config_param_id`) VALUES
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'PINNACLE'), (SELECT id
                                           FROM cmn_config_param
                                           WHERE name = 'ENDPOINT_URL_SUBMISSION')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'DUMMY_GATEWAY_2'), (SELECT id
                                           FROM cmn_config_param
                                           WHERE name = 'ENDPOINT_URL_SUBMISSION')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'PINNACLE'), (SELECT id
                                           FROM cmn_config_param
                                           WHERE name = 'USERNAME')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'PINNACLE'), (SELECT id
                                           FROM cmn_config_param
                                           WHERE name = 'USER_CREDENTIAL_PWD')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'PINNACLE'), (SELECT id
                                           FROM cmn_config_param
                                           WHERE name = 'ENDPOINT_URL_POLLING')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'DUMMY_GATEWAY_2'), (SELECT id
                                           FROM cmn_config_param
                                           WHERE name = 'ENDPOINT_URL_POLLING')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'PINNACLE'), (SELECT id
                                           FROM cmn_config_param
                                           WHERE name = 'SENDER_BRAND_1')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'NEXT_SMS_BATCH_LOAD_WINDOW')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'RETRY_SAME_GATEWAY')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'MGAGE_PRIORITY'), (SELECT id
                                                 FROM cmn_config_param
                                                 WHERE name = 'USERNAME')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'MGAGE_PRIORITY'), (SELECT id
                                                 FROM cmn_config_param
                                                 WHERE name = 'USER_CREDENTIAL_PWD')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'MGAGE_PRIORITY'), (SELECT id
                                                 FROM cmn_config_param
                                                 WHERE name = 'ENDPOINT_URL_SUBMISSION')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'MGAGE_PRIORITY'), (SELECT id
                                                 FROM cmn_config_param
                                                 WHERE name = 'ENDPOINT_URL_POLLING')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'MGAGE_PRIORITY'), (SELECT id
                                                 FROM cmn_config_param
                                                 WHERE name = 'SENDER_BRAND_1')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'MGAGE'), (SELECT id
                                        FROM cmn_config_param
                                        WHERE name = 'USERNAME')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'MGAGE'), (SELECT id
                                        FROM cmn_config_param
                                        WHERE name = 'USER_CREDENTIAL_PWD')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'MGAGE'), (SELECT id
                                        FROM cmn_config_param
                                        WHERE name = 'ENDPOINT_URL_SUBMISSION')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'MGAGE'), (SELECT id
                                        FROM cmn_config_param
                                        WHERE name = 'ENDPOINT_URL_POLLING')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'MGAGE'), (SELECT id
                                        FROM cmn_config_param
                                        WHERE name = 'SENDER_BRAND_1')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'PINNACLE'), (SELECT id
                                           FROM cmn_config_param
                                           WHERE name = 'SENDER_BRAND_2')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'MGAGE_PRIORITY'), (SELECT id
                                                 FROM cmn_config_param
                                                 WHERE name = 'SENDER_BRAND_2')),
  (NULL, b'1', (SELECT id
                FROM smsen_sms_gateway
                WHERE name = 'MGAGE'), (SELECT id
                                        FROM cmn_config_param
                                        WHERE name = 'SENDER_BRAND_2')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'NEW_SMS_LOAD_LIMIT')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'NEW_SMS_COOLING_PERIOD_CHECK')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'NEW_SMS_PHONE_NUMBER_VALIDATION')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'NEW_SMS_USER_DND_VALIDATION')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'NEW_SMS_MESSAGE_FORMAT_VALIDATION')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'MAX_SUBMISSION_COUNT_LOADED_TO_QUEUES')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'SMS_SUBMISSION_WORKER_BATCH_MAX_SIZE')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'GATEWAY_FINDING_FAILURE_RETRY_THRESHOLD')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'CORRELATION_ID_CHECK_MIN_WAIT_TIME')),

  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'CORRELATION_ID_CHECK_MAX_COUNT')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'SMS_SUBMISSION_WORKER_BATCH_SLEEP_TIME')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'SMS_SUBMISSION_GATEWAY_FAILURE_COUNT_THRESHOLD')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'GATEWAY_USAGE_POLICY')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'UNSENT_SMS_RETRIAL_POLICY')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'NEW_SMS_LOAD_WINDOW')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'MAX_STATUS_CHECK_COUNT_LOADED_TO_QUEUES')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'STATUS_CHECK_MAX_COUNT')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'STATUS_CHECK_MIN_WAIT_TIME')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'SMS_STATUS_WORKER_BATCH_SLEEP_TIME')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'SMS_STATUS_CHECK_TIME_OUT_DURATION')),
  (NULL, b'1', NULL, (SELECT id
                      FROM cmn_config_param
                      WHERE name = 'RETRY_COUNT'))
;

-- ------------------------------------------------------------------------

INSERT INTO `cmn_target_config_value` (`id`, `value`, `target_config_id`) VALUES
  (NULL, 'http://www.smsjust.com/blank/sms/user/urlsms.php',
   (SELECT ctc.id
    FROM cmn_target_config ctc
    WHERE ctc.target_id = (SELECT id
                           FROM smsen_sms_gateway
                           WHERE name = 'PINNACLE')
          AND ctc.config_param_id = (SELECT id
                                     FROM cmn_config_param
                                     WHERE name = 'ENDPOINT_URL_SUBMISSION'))),
  (NULL, 'http://commp.localhost:8080/testrapi/pMockSms/submit?error=false',
   (SELECT ctc.id
    FROM cmn_target_config ctc
    WHERE ctc.target_id = (SELECT id
                           FROM smsen_sms_gateway
                           WHERE name = 'DUMMY_GATEWAY_2')
          AND ctc.config_param_id = (SELECT id
                                     FROM cmn_config_param
                                     WHERE name = 'ENDPOINT_URL_SUBMISSION'))),
  (NULL, 'healthvista', (SELECT ctc.id
                         FROM cmn_target_config ctc
                         WHERE ctc.target_id = (SELECT id
                                                FROM smsen_sms_gateway
                                                WHERE name = 'PINNACLE')
                               AND ctc.config_param_id = (SELECT id
                                                          FROM cmn_config_param
                                                          WHERE name = 'USERNAME'))),
  (NULL, 'health', (SELECT ctc.id
                    FROM cmn_target_config ctc
                    WHERE ctc.target_id = (SELECT id
                                           FROM smsen_sms_gateway
                                           WHERE name = 'PINNACLE')
                          AND ctc.config_param_id = (SELECT id
                                                     FROM cmn_config_param
                                                     WHERE name = 'USER_CREDENTIAL_PWD'))),
  (NULL, 'http://www.smsjust.com/blank/sms/user/response.php', (SELECT ctc.id
                                                                FROM cmn_target_config ctc
                                                                WHERE ctc.target_id = (SELECT id
                                                                                       FROM smsen_sms_gateway
                                                                                       WHERE name = 'PINNACLE')
                                                                      AND ctc.config_param_id = (SELECT id
                                                                                                 FROM cmn_config_param
                                                                                                 WHERE name =
                                                                                                       'ENDPOINT_URL_POLLING'))),
  (NULL, 'http://commp.localhost:8080/testrapi/pMockSms/status?error=false', (SELECT ctc.id
                                                                FROM cmn_target_config ctc
                                                                WHERE ctc.target_id = (SELECT id
                                                                                       FROM smsen_sms_gateway
                                                                                       WHERE name = 'DUMMY_GATEWAY_2')
                                                                      AND ctc.config_param_id = (SELECT id
                                                                                                 FROM cmn_config_param
                                                                                                 WHERE name =
                                                                                                       'ENDPOINT_URL_POLLING'))),
  (NULL, 'PORTEA', (SELECT ctc.id
                    FROM cmn_target_config ctc
                    WHERE ctc.target_id = (SELECT id
                                           FROM smsen_sms_gateway
                                           WHERE name = 'PINNACLE')
                          AND ctc.config_param_id = (SELECT id
                                                     FROM cmn_config_param
                                                     WHERE name = 'SENDER_BRAND_1'))),
  (NULL, '15000', (SELECT ctc.id
                FROM cmn_target_config ctc
                WHERE ctc.target_id IS NULL
                      AND ctc.config_param_id = (SELECT id
                                                 FROM cmn_config_param
                                                 WHERE name = 'NEXT_SMS_BATCH_LOAD_WINDOW'))),
  (NULL, 'false', (SELECT ctc.id
                   FROM cmn_target_config ctc
                   WHERE ctc.target_id IS NULL
                         AND ctc.config_param_id = (SELECT id
                                                    FROM cmn_config_param
                                                    WHERE name = 'RETRY_SAME_GATEWAY'))),
  (NULL, '573436', (SELECT ctc.id
                    FROM cmn_target_config ctc
                    WHERE ctc.target_id = (SELECT id
                                           FROM smsen_sms_gateway
                                           WHERE name = 'MGAGE_PRIORITY')
                          AND ctc.config_param_id = (SELECT id
                                                     FROM cmn_config_param
                                                     WHERE name = 'USERNAME'))),
  (NULL, 'por@2', (SELECT ctc.id
                   FROM cmn_target_config ctc
                   WHERE ctc.target_id = (SELECT id
                                          FROM smsen_sms_gateway
                                          WHERE name = 'MGAGE_PRIORITY')
                         AND ctc.config_param_id = (SELECT id
                                                    FROM cmn_config_param
                                                    WHERE name = 'USER_CREDENTIAL_PWD'))),
  (NULL, 'http://luna.a2wi.co.in:7501/failsafe/HttpLink', (SELECT ctc.id
                                                           FROM cmn_target_config ctc
                                                           WHERE ctc.target_id = (SELECT id
                                                                                  FROM smsen_sms_gateway
                                                                                  WHERE name = 'MGAGE_PRIORITY')
                                                                 AND ctc.config_param_id = (SELECT id
                                                                                            FROM cmn_config_param
                                                                                            WHERE name =
                                                                                                  'ENDPOINT_URL_SUBMISSION'))),
  (NULL, 'http://121.241.244.196/DnQueryHandler/dn', (SELECT ctc.id
                                                      FROM cmn_target_config ctc
                                                      WHERE ctc.target_id = (SELECT id
                                                                             FROM smsen_sms_gateway
                                                                             WHERE name = 'MGAGE_PRIORITY')
                                                            AND ctc.config_param_id = (SELECT id
                                                                                       FROM cmn_config_param
                                                                                       WHERE name =
                                                                                             'ENDPOINT_URL_POLLING'))),
  (NULL, 'PORTEA', (SELECT ctc.id
                    FROM cmn_target_config ctc
                    WHERE ctc.target_id = (SELECT id
                                           FROM smsen_sms_gateway
                                           WHERE name = 'MGAGE_PRIORITY')
                          AND ctc.config_param_id = (SELECT id
                                                     FROM cmn_config_param
                                                     WHERE name = 'SENDER_BRAND_1'))),
  (NULL, '572237', (SELECT ctc.id
                    FROM cmn_target_config ctc
                    WHERE ctc.target_id = (SELECT id
                                           FROM smsen_sms_gateway
                                           WHERE name = 'MGAGE')
                          AND ctc.config_param_id = (SELECT id
                                                     FROM cmn_config_param
                                                     WHERE name = 'USERNAME'))),
  (NULL, 'po@1', (SELECT ctc.id
                  FROM cmn_target_config ctc
                  WHERE ctc.target_id = (SELECT id
                                         FROM smsen_sms_gateway
                                         WHERE name = 'MGAGE')
                        AND ctc.config_param_id = (SELECT id
                                                   FROM cmn_config_param
                                                   WHERE name = 'USER_CREDENTIAL_PWD'))),
  (NULL, 'https://luna.a2wi.co.in/failsafe/HttpLink', (SELECT ctc.id
                                                       FROM cmn_target_config ctc
                                                       WHERE ctc.target_id = (SELECT id
                                                                              FROM smsen_sms_gateway
                                                                              WHERE name = 'MGAGE')
                                                             AND ctc.config_param_id = (SELECT id
                                                                                        FROM cmn_config_param
                                                                                        WHERE name =
                                                                                              'ENDPOINT_URL_SUBMISSION'))),
  (NULL, 'http://121.241.244.196/DnQueryHandler/dn', (SELECT ctc.id
                                                      FROM cmn_target_config ctc
                                                      WHERE ctc.target_id = (SELECT id
                                                                             FROM smsen_sms_gateway
                                                                             WHERE name = 'MGAGE')
                                                            AND ctc.config_param_id = (SELECT id
                                                                                       FROM cmn_config_param
                                                                                       WHERE name =
                                                                                             'ENDPOINT_URL_POLLING'))),
  (NULL, 'PORTEA', (SELECT ctc.id
                    FROM cmn_target_config ctc
                    WHERE ctc.target_id = (SELECT id
                                           FROM smsen_sms_gateway
                                           WHERE name = 'MGAGE')
                          AND ctc.config_param_id = (SELECT id
                                                     FROM cmn_config_param
                                                     WHERE name = 'SENDER_BRAND_1'))),
  (NULL, 'MANIPL', (SELECT ctc.id
                    FROM cmn_target_config ctc
                    WHERE ctc.target_id = (SELECT id
                                           FROM smsen_sms_gateway
                                           WHERE name = 'PINNACLE')
                          AND ctc.config_param_id = (SELECT id
                                                     FROM cmn_config_param
                                                     WHERE name = 'SENDER_BRAND_2'))),
  (NULL, 'MANPAL', (SELECT ctc.id
                    FROM cmn_target_config ctc
                    WHERE ctc.target_id = (SELECT id
                                           FROM smsen_sms_gateway
                                           WHERE name = 'MGAGE_PRIORITY')
                          AND ctc.config_param_id = (SELECT id
                                                     FROM cmn_config_param
                                                     WHERE name = 'SENDER_BRAND_2'))),
  (NULL, 'MANPAL', (SELECT ctc.id
                    FROM cmn_target_config ctc
                    WHERE ctc.target_id = (SELECT id
                                           FROM smsen_sms_gateway
                                           WHERE name = 'MGAGE')
                          AND ctc.config_param_id = (SELECT id
                                                     FROM cmn_config_param
                                                     WHERE name = 'SENDER_BRAND_2'))),
(NULL, '2000', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'NEW_SMS_LOAD_LIMIT'))),
(NULL, 'true', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'NEW_SMS_COOLING_PERIOD_CHECK'))),
(NULL, 'true', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'NEW_SMS_PHONE_NUMBER_VALIDATION'))),
(NULL, 'true', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'NEW_SMS_USER_DND_VALIDATION'))),
(NULL, 'true', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'NEW_SMS_MESSAGE_FORMAT_VALIDATION'))),
(NULL, '8000', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'MAX_SUBMISSION_COUNT_LOADED_TO_QUEUES'))),
(NULL, '50', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'SMS_SUBMISSION_WORKER_BATCH_MAX_SIZE'))),
(NULL, '3', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'GATEWAY_FINDING_FAILURE_RETRY_THRESHOLD'))),
(NULL, '2000', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'CORRELATION_ID_CHECK_MIN_WAIT_TIME'))),
(NULL, '20', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'CORRELATION_ID_CHECK_MAX_COUNT'))),
(NULL, '250', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'SMS_SUBMISSION_WORKER_BATCH_SLEEP_TIME'))),
(NULL, '3', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'SMS_SUBMISSION_GATEWAY_FAILURE_COUNT_THRESHOLD'))),
(NULL, 'RETRY_UNTIL_UNAVAILABLE', (SELECT ctc.id
                                   FROM cmn_target_config ctc
                                   WHERE ctc.target_id IS NULL
                                         AND ctc.config_param_id = (SELECT id
                                                                    FROM cmn_config_param
                                                                    WHERE name = 'GATEWAY_USAGE_POLICY'))),
(NULL, 'NO_RETRY', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'UNSENT_SMS_RETRIAL_POLICY'))),
(NULL, '15000', (SELECT ctc.id
               FROM cmn_target_config ctc
               WHERE ctc.target_id IS NULL
                     AND ctc.config_param_id = (SELECT id
                                                FROM cmn_config_param
                                                WHERE name = 'NEW_SMS_LOAD_WINDOW'))),
  (NULL, '8000', (SELECT ctc.id
                  FROM cmn_target_config ctc
                  WHERE ctc.target_id IS NULL
                        AND ctc.config_param_id = (SELECT id
                                                   FROM cmn_config_param
                                                   WHERE name = 'MAX_STATUS_CHECK_COUNT_LOADED_TO_QUEUES'))),
  (NULL, '30', (SELECT ctc.id
                FROM cmn_target_config ctc
                WHERE ctc.target_id IS NULL
                      AND ctc.config_param_id = (SELECT id
                                                 FROM cmn_config_param
                                                 WHERE name = 'STATUS_CHECK_MAX_COUNT'))),
  (NULL, '2000', (SELECT ctc.id
                FROM cmn_target_config ctc
                WHERE ctc.target_id IS NULL
                      AND ctc.config_param_id = (SELECT id
                                                 FROM cmn_config_param
                                                 WHERE name = 'STATUS_CHECK_MIN_WAIT_TIME'))),
  (NULL, '750', (SELECT ctc.id
                  FROM cmn_target_config ctc
                  WHERE ctc.target_id IS NULL
                        AND ctc.config_param_id = (SELECT id
                                                   FROM cmn_config_param
                                                   WHERE name = 'SMS_STATUS_WORKER_BATCH_SLEEP_TIME'))),
  (NULL, '86400000', (SELECT ctc.id
                 FROM cmn_target_config ctc
                 WHERE ctc.target_id IS NULL
                       AND ctc.config_param_id = (SELECT id
                                                  FROM cmn_config_param
                                                  WHERE name = 'SMS_STATUS_CHECK_TIME_OUT_DURATION'))),
  (NULL, '2', (SELECT ctc.id
                      FROM cmn_target_config ctc
                      WHERE ctc.target_id IS NULL
                            AND ctc.config_param_id = (SELECT id
                                                       FROM cmn_config_param
                                                       WHERE name = 'RETRY_COUNT')))



;
-- ------------------------------------------------------------------------

COMMIT;