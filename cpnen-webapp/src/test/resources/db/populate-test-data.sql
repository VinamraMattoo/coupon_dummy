--  -----------------------------------------------------------------------------------
--            Script to populate test data which is used by unit tests             --
--  -----------------------------------------------------------------------------------

INSERT INTO `test_user` (`id`, `dob`, `name`, `password`, `phone_number`) VALUES
(NULL, '2015-08-02', 'tester', '$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq', '857456452');

INSERT INTO `test_category` (`id`, `name`, `parent_category_id`) VALUES
(1, 'Nursing', NULL),
(2, 'Physiotherapy', NULL),
(3, 'doctor visit', NULL),
(4, 'Nursing Attendant', NULL);


INSERT INTO `test_product` (`id`, `name`, `unit_price`, `category_id`, `parent_product_id`) VALUES
(1, 'Wound Care', 100, 1, NULL),
(2, '12 Hr Nursing', 250, 1, NULL),
(3, 'Followup Visit', 132, 1, NULL),
(4, 'GroupOn Physio', 1212, 2, NULL),
(5, 'MAX PHYSIO', 131, 2, NULL),
(6, '12Hr Nursing attendant', 200, 4, NULL),
(7, 'Placement-NA', 100, NULL, NULL),
(8, 'RA Aide- 4hrs', 120, 3, NULL),
(9, 'RA Aide- 12hrs', 400, 4, NULL),
(10, 'Corporate Visit', 123, 1, NULL),
(11, 'Doctor home visits on call', 400, 3, NULL);

INSERT INTO `coupon` (`id`, `applicable_from`, `applicable_till`, `applicable_use_count`, `application_type`, `channel_name`, `created_on`, `deactivated_on`, `description`, `inclusive`, `name`, `prod_count_span`, `prod_count_max`, `prod_count_min`, `trans_val_max`, `trans_val_min`, `created_by`, `deactivated_by`) VALUES
(1, DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 15 MINUTE), 12, 'MANY_TIMES', 'advertisement', '2015-08-02 00:00:00', NULL, 'the first coupon', b'0', 'health coupon', b'0', 10, 1, 1000, 100, 1, NULL),
(2, DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 15 MINUTE), 2, 'MANY_TIMES', 'twitter', '2015-08-02 00:00:00', NULL, 'the second coupon', b'0', 'more coupon', b'0', 10, 1, 1000, 100, 1, NULL),
(3, DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 15 MINUTE), 2, 'MANY_TIMES', 'fb', '2015-08-02 00:00:00', NULL, 'the third coupon', b'0', '3 coupon', b'0', 8, 1, 1000, 100, 1, NULL),
(4, DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 15 MINUTE), 10, 'MANY_TIMES', 'fb', '2015-08-03 00:00:00', NULL, '4th coupon', b'1', '4th coupon', b'1', 12, 1, 10010, 10, 1, NULL),
(5, DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 15 MINUTE), 10, 'MANY_TIMES', 'twitter', '2015-08-03 00:00:00', NULL, '5th coupon', b'1', '5th coupon', b'1', 20, 1, 99, 1, 1, NULL),
(6, DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 15 MINUTE), 10, 'MANY_TIMES', 'fb', '2015-08-03 00:00:00', NULL, 'coupon', b'1', '6th coupon', b'1', 19, 1, 990, 199, 1, NULL),
(7, DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 15 MINUTE), 10, 'MANY_TIMES', 'fb', '2015-08-03 00:00:00', NULL, 'coupon', b'1', '6th coupon', b'1', 15, 1, 1037, 10, 1, NULL),
(8, DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 15 MINUTE), 10, 'MANY_TIMES', 'twitter', '2015-08-03 00:00:00', '2015-08-10 00:00:00', 'coupon', b'1', '6th coupon', b'1', 17, 1, 1093, 103, 1, 1),
(9, DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL -15 MINUTE), 10, 'MANY_TIMES', 'billboard', '2015-08-03 00:00:00', NULL, 'coupon', b'1', '6th coupon', b'1', 80, 1, 1000, 1, 1, NULL),
(10, DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 15 MINUTE), 10, 'MANY_TIMES', 'advertisement', '2015-08-03 00:00:00', NULL, 'coupon', b'1', '6th coupon', b'1', 111, 1, 1000, 13, 1, NULL),
(11, DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 15 MINUTE), 10, 'MANY_TIMES', 'fb', '2015-08-09 00:00:00', NULL, 'coupon11', b'1', 'testcoupon', b'1', 20, 1, 5000, 100, 1, NULL),
(12, DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 15 MINUTE), 13, 'MANY_TIMES', 'fb', '2015-08-02 00:00:00', NULL, 'test coupon 12', b'1', 'test coupon', b'1', 15, 1, 3000, 100, 1, NULL);


INSERT INTO `coupon_code` (`id`, `code`, `created_on`, `deactivated_on`, `coupon_id`, `created_by`, `deactivated_by`) VALUES
(1, 'C1-D1', '2015-07-08 00:00:00', NULL, 1, 1, NULL),
(2, 'C1-D2', '2015-08-02 00:00:00', NULL, 2, 1, NULL),
(3, 'C1-D3', '2015-08-03 00:00:00', NULL, 3, 1, NULL),
(4, 'C1-D4', '2015-08-02 00:00:00', NULL, 4, 1, NULL),
(5, 'C1-D5', '2015-08-02 00:00:00', NULL, 5, 1, NULL),
(6, 'C1-D6', '2015-08-02 00:00:00', '2015-08-03 00:00:00', 6, 1, 1),
(7, 'C1-D7', '2015-08-02 00:00:00', NULL, 7, 1, NULL),
(8, 'C1-D8', '2015-08-02 00:00:00', NULL, 8, 1, NULL),
(9, 'C1-D9', '2015-08-02 00:00:00', NULL, 9, 1, NULL),
(10, 'C1-D10', '2015-08-02 00:00:00', NULL, 10, 1, NULL),
(11, 'C1-D11', '2015-08-02 00:00:00', NULL, 11, 1, NULL),
(12, 'C1-D12', '2015-08-02 00:00:00', NULL, 12, 1, NULL);

INSERT INTO `coupon_discounting_rule` (`id`, `type`, `created_on`, `deactivated_on`, `description`, `disc_flat_amt`, `disc_percentage`, `priority`, `coupon_id`, `created_by`) VALUES
(1, 'FLAT', '2015-08-02 00:00:00', NULL, 'flat rule', 50, NULL, 1, 1, 1),
(2, 'FLAT', '2015-08-01 00:00:00', NULL, 'blah', 145, NULL, 1, 2, 1),
(3, 'PERCENT', '2015-08-02 00:00:00', NULL, 'discount!', NULL, 10, 3, 3, 1);


INSERT INTO `coupon_discounting_rule_interval` (`id`, `interval_end`, `interval_start`, `type`, `value`, `rule_id`) VALUES
 (NULL, '300', '100', 'TRANSACTION_VALUE', '50', '1'),
(NULL, '900', '301', 'TRANSACTION_VALUE', '100', '1'),
 (NULL, '1000', '901', 'TRANSACTION_VALUE', '150', '1'),
(NULL, '133', '1333', 'PRODUCT_COUNT', '123', '2');


UPDATE  `test_product` SET  `parent_product_id` =  '11' WHERE  `test_product`.`id` =8;
UPDATE  `test_category` SET  `parent_category_id` =  '2' WHERE  `test_category`.`id` =3;
UPDATE  `test_product` SET  `parent_product_id` =  '10' WHERE  `test_product`.`id` =10;
INSERT INTO `coupon_product_map` (`id`, `applicable`, `coupon_id`, `product_id`) VALUES (NULL, b'1', '1', '1');

INSERT INTO `coupon_discount_req` (`id`, `completed`, `latest_updated_on`, `user_phone`, `user_id`) VALUES
(1, b'0', '2015-08-18 19:29:27', '857456452', 1);


INSERT INTO `coupon_discount_req_code` (`id`, `created_on`, `latest_status`, `code_id`, `coupon_disc_req_id`) VALUES
(1, '2015-08-18 19:29:27', 'REJECTED', 1, 1);


INSERT INTO `coupon_discount_req_prod` (`id`, `product_count`, `product_unit_price`, `remarks`, `coupon_disc_req_id`, `product_id`) VALUES
(1, 2, 100, NULL, 1, 1);