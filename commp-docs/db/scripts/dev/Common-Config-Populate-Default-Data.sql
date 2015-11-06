-- ---------------------------------------------------------------------------------------------------- --
-- 				             	Portea Common Platform				                                    --
-- 				       Populate test data for common configuration tables                               --
-- ---------------------------------------------------------------------------------------------------- --

INSERT INTO `cmn_config_target_type` (`id`, `description`, `type_name`)
VALUES (1, NULL, 'SMS_GATEWAY'),
(2, NULL, 'SMS_TEMPLATE'),
(3, NULL, 'SMS_ENGINE');

-- --------------------------------------------------------

INSERT INTO `cmn_config_param` (`id`, `name`, `value_data_type`, `config_target_type_id`, `code`) VALUES
(1, 'ENDPOINT_URL_SUBMISSION', 'TEXT', 1, '1'),
(2, 'RESTRICTED_CHARACTERS', 'TEXT', 1, '2'),
(3, 'INACTIVE_PERIOD_START', 'DATE', 1, '3'),
(4, 'INACTIVE_PERIOD_END', 'DATE', 1, '4'),
(5, 'ENDPOINT_URL_POLLING', 'TEXT', 1, '5'),
(6, 'RETRY_COUNT', 'NUMBER', 2, '6'),
(7, 'MESSAGE_EXPIRY', 'DATE', 2, '7'),
(8, 'GATEWAY_STATUS_CHECKER_TASK_INTERVAL', 'NUMBER', 3, '8'),
(9, 'STATUS_POLL_RETRY_LIMIT', 'NUMBER', 3, '9'),
(10, 'MAX_SMS_LENGTH', 'NUMBER', 3, '10'),
(11, 'RETRY_SAME_GATEWAY', 'BOOLEAN', 3, '11'),
(12, 'SMS_STATUS_WAIT_TIME', 'NUMBER', 3, '12'),
(13, 'NEW_SMS_LOAD_WINDOW', 'NUMBER', 3, '13'),
(14, 'NEXT_SMS_BATCH_LOAD_WINDOW', 'NUMBER', 3, '14'),
(15, 'CORRELATION_ID_WAIT_TIME', 'NUMBER', 3, '15'),
(16, 'GATEWAY_FAILURE_COUNT_THRESHOLD', 'NUMBER', 3, '16'),
(17, 'NEW_SMS_LOADER_INTERVAL', 'NUMBER', 3, '17'),
(18, 'NEXT_SMS_BATCH_LODER_INTERVAL', 'NUMBER', 3, '18');

-- --------------------------------------------------------

INSERT INTO `cmn_target_config` (`id`, `active`, `target_id`, `config_param_id`, `config_target_type_id`) VALUES
(1, b'1', '1', '1', '1'),
(2, b'1', '2', '1', '1'),
(3, b'1', NULL, '17', '3'),
(4, b'1', NULL, '13', '3'),
(5, b'1', NULL, '18', '3'),
(6, b'1', NULL, '14', '3');

-- ------------------------------------------------------------------------

INSERT INTO `cmn_target_config_value` (`id`, `value`, `target_config_id`) VALUES
(NULL, 'http://www.smsjust.com/blank/sms/user/urlsms.php', 1),
(NULL, 'http://trans.kapsystem.com/api/web2sms.php', 2),
(NULL, '14', 3),
(NULL, '15', 4),
(NULL, '5', 5),
(NULL, '15', 6);

-- ------------------------------------------------------------------------

commit;