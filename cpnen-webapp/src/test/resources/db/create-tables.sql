-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 23, 2015 at 11:31 PM
-- Server version: 5.6.19-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: ``
--

-- --------------------------------------------------------

--
-- Table structure for table `coupon`
--

CREATE TABLE IF NOT EXISTS `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `applicable_from` datetime DEFAULT NULL,
  `applicable_till` datetime DEFAULT NULL,
  `applicable_use_count` int(11) DEFAULT NULL,
  `application_type` varchar(255) DEFAULT NULL,
  `channel_name` varchar(128) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `deactivated_on` datetime DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `inclusive` bit(1) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `prod_count_span` bit(1) DEFAULT NULL,
  `prod_count_max` int(11) DEFAULT NULL,
  `prod_count_min` int(11) DEFAULT NULL,
  `trans_val_max` int(11) DEFAULT NULL,
  `trans_val_min` int(11) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `deactivated_by` int(11) DEFAULT NULL,
  `published_by` datetime DEFAULT NULL,
  `published_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qnl09bomguvj0vuqksq3e4gb8` (`created_by`),
  KEY `FK_7e3f8uxuc68moy85gumhv8o2n` (`deactivated_by`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_category_map`
--

CREATE TABLE IF NOT EXISTS `coupon_category_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `applicable` bit(1) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hwj2xfl9rio4njcgjks7p9lrh` (`coupon_id`,`category_id`),
  KEY `FK_9u6to2xhlxipa5u9h0g3oncou` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_code`
--

CREATE TABLE IF NOT EXISTS `coupon_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(128) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `deactivated_on` datetime DEFAULT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `deactivated_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rp6gs6naditexdpsmfoum2taf` (`coupon_id`),
  KEY `FK_mc02d6pj3bykobybucq0l8eua` (`created_by`),
  KEY `FK_l4i9kik5jt52xus0cdup1dwso` (`deactivated_by`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_code_reservation`
--

CREATE TABLE IF NOT EXISTS `coupon_code_reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reservation_from` datetime DEFAULT NULL,
  `reservation_till` datetime DEFAULT NULL,
  `event_type` varchar(128) DEFAULT NULL,
  `code_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sin0gpbukgebu0k0ise50d6lp` (`code_id`),
  KEY `FK_tkah45e3s5w22pk1dkpw4lc4l` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_discount`
--

CREATE TABLE IF NOT EXISTS `coupon_discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `user_phone` varchar(255) DEFAULT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2q4l5ws2pf1at1hb3uypuhfqs` (`coupon_id`),
  KEY `FK_dp0wnbdxesg3xc713u77gt3c3` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_discounting_rule`
--

CREATE TABLE IF NOT EXISTS `coupon_discounting_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `deactivated_on` datetime DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `disc_flat_amt` int(11) DEFAULT NULL,
  `disc_percentage` int(11) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1s2wuk0293qn9w1yb2rv87k5t` (`coupon_id`),
  KEY `FK_rcr82ja4jx7ta0iqiqup7deq8` (`created_by`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_discounting_rule_interval`
--

CREATE TABLE IF NOT EXISTS `coupon_discounting_rule_interval` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interval_end` int(11) DEFAULT NULL,
  `interval_start` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `rule_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gb1py7grpokuubu5lvn3a331m` (`rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_discount_code`
--

CREATE TABLE IF NOT EXISTS `coupon_discount_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code_id` int(11) DEFAULT NULL,
  `coupon_disc_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m48qglovju1v8rmgoqvqa3ywa` (`code_id`),
  KEY `FK_ocy3ip7m9037i43a1e6l9se97` (`coupon_disc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_discount_product`
--

CREATE TABLE IF NOT EXISTS `coupon_discount_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_count` int(11) DEFAULT NULL,
  `product_unit_price` int(11) DEFAULT NULL,
  `coupon_disc_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2anktmwul46do6hhglxb2o4gg` (`coupon_disc_id`),
  KEY `FK_md67nwsxu3q20l40kpe9rh7pk` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_discount_req`
--

CREATE TABLE IF NOT EXISTS `coupon_discount_req` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `completed` bit(1) DEFAULT NULL,
  `latest_updated_on` datetime DEFAULT NULL,
  `user_phone` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_j7tyxk9w5sf0q4rbu7fl2ub6i` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_discount_req_code`
--

CREATE TABLE IF NOT EXISTS `coupon_discount_req_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `latest_status` varchar(255) DEFAULT NULL,
  `code_id` int(11) DEFAULT NULL,
  `coupon_disc_req_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jgxiy222jqxth7ine58d6rwfv` (`code_id`),
  KEY `FK_2bjiywxb2mmapu6j1u8vtb9mk` (`coupon_disc_req_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_discount_req_prod`
--

CREATE TABLE IF NOT EXISTS `coupon_discount_req_prod` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_count` int(11) DEFAULT NULL,
  `product_unit_price` int(11) DEFAULT NULL,
  `remarks` varchar(256) DEFAULT NULL,
  `coupon_disc_req_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3t0aj8bwilwjt4cpkkujx0cjf` (`coupon_disc_req_id`),
  KEY `FK_hmb88fmgapwwuhoo94h81ewpo` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_discount_summary`
--

CREATE TABLE IF NOT EXISTS `coupon_discount_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_use_date` datetime DEFAULT NULL,
  `latest_use_date` datetime DEFAULT NULL,
  `total_use_count` int(11) DEFAULT NULL,
  `trans_val_avg` int(11) DEFAULT NULL,
  `trans_val_highest` int(11) DEFAULT NULL,
  `trans_val_lowest` int(11) DEFAULT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jr35rxuwxs7jodmp9w90a6g8v` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_disc_req_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_disc_req_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `user_phone` varchar(255) DEFAULT NULL,
  `coupon_disc_req_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2ets8xynpnmo9v4wfgickyk5r` (`coupon_disc_req_id`),
  KEY `FK_8gxxyiv54ce8aewodkfw6w4ii` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_disc_req_code_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_disc_req_code_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `code_id` int(11) DEFAULT NULL,
  `cdr_audit_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_s1mkih6sb66k92lpmnxe2de7f` (`code_id`),
  KEY `FK_s2a5unsktm31lw2d52eb8pvxw` (`cdr_audit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_disc_req_prod_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_disc_req_prod_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_count` int(11) DEFAULT NULL,
  `product_uint_price` int(11) DEFAULT NULL,
  `remarks` varchar(256) DEFAULT NULL,
  `cdr_audit_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_adg9da2vd2k8w0ugjqnooj4qw` (`cdr_audit_id`),
  KEY `FK_95u5jwecq7sdvwwvqkv6hri5j` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_engine_config`
--

CREATE TABLE IF NOT EXISTS `coupon_engine_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_engine_config_audit`
--

CREATE TABLE IF NOT EXISTS `coupon_engine_config_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `new_value` varchar(256) DEFAULT NULL,
  `old_value` varchar(256) DEFAULT NULL,
  `cpn_engine_conf_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b2oemqt1mdtir97hk1rjkbcka` (`cpn_engine_conf_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_event_type`
--

CREATE TABLE IF NOT EXISTS `coupon_event_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_product_map`
--

CREATE TABLE IF NOT EXISTS `coupon_product_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `applicable` bit(1) DEFAULT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_nw0c4lumv693ggxwswel42n7t` (`coupon_id`),
  KEY `FK_jeclss59g3pbbk0v8fycttf8d` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `test_category`
--

CREATE TABLE IF NOT EXISTS `test_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `parent_category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b7if9nydxkiw5s2w8d85mrr9e` (`parent_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `test_product`
--

CREATE TABLE IF NOT EXISTS `test_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `unit_price` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `parent_product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1pvcyxgqp5q63bx44l7q2li27` (`category_id`),
  KEY `FK_c4d299tcho2ffr8q1hd5q7295` (`parent_product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `test_user`
--

CREATE TABLE IF NOT EXISTS `test_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dob` date DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `phone_number` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `coupon`
--
ALTER TABLE `coupon`
  ADD CONSTRAINT `FK_7e3f8uxuc68moy85gumhv8o2n` FOREIGN KEY (`deactivated_by`) REFERENCES `test_user` (`id`),
  ADD CONSTRAINT `FK_qnl09bomguvj0vuqksq3e4gb8` FOREIGN KEY (`created_by`) REFERENCES `test_user` (`id`);

--
-- Constraints for table `coupon_category_map`
--
ALTER TABLE `coupon_category_map`
  ADD CONSTRAINT `FK_4f806ch52n7l55r9c3ft72tlo` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`),
  ADD CONSTRAINT `FK_9u6to2xhlxipa5u9h0g3oncou` FOREIGN KEY (`category_id`) REFERENCES `test_category` (`id`);

--
-- Constraints for table `coupon_code`
--
ALTER TABLE `coupon_code`
  ADD CONSTRAINT `FK_l4i9kik5jt52xus0cdup1dwso` FOREIGN KEY (`deactivated_by`) REFERENCES `test_user` (`id`),
  ADD CONSTRAINT `FK_mc02d6pj3bykobybucq0l8eua` FOREIGN KEY (`created_by`) REFERENCES `test_user` (`id`),
  ADD CONSTRAINT `FK_rp6gs6naditexdpsmfoum2taf` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`);

--
-- Constraints for table `coupon_code_reservation`
--
ALTER TABLE `coupon_code_reservation`
  ADD CONSTRAINT `FK_tkah45e3s5w22pk1dkpw4lc4l` FOREIGN KEY (`user_id`) REFERENCES `test_user` (`id`),
  ADD CONSTRAINT `FK_sin0gpbukgebu0k0ise50d6lp` FOREIGN KEY (`code_id`) REFERENCES `coupon_code` (`id`);

--
-- Constraints for table `coupon_discount`
--
ALTER TABLE `coupon_discount`
  ADD CONSTRAINT `FK_dp0wnbdxesg3xc713u77gt3c3` FOREIGN KEY (`user_id`) REFERENCES `test_user` (`id`),
  ADD CONSTRAINT `FK_2q4l5ws2pf1at1hb3uypuhfqs` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`);

--
-- Constraints for table `coupon_discounting_rule`
--
ALTER TABLE `coupon_discounting_rule`
  ADD CONSTRAINT `FK_rcr82ja4jx7ta0iqiqup7deq8` FOREIGN KEY (`created_by`) REFERENCES `test_user` (`id`),
  ADD CONSTRAINT `FK_1s2wuk0293qn9w1yb2rv87k5t` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`);

--
-- Constraints for table `coupon_discounting_rule_interval`
--
ALTER TABLE `coupon_discounting_rule_interval`
  ADD CONSTRAINT `FK_gb1py7grpokuubu5lvn3a331m` FOREIGN KEY (`rule_id`) REFERENCES `coupon_discounting_rule` (`id`);

--
-- Constraints for table `coupon_discount_code`
--
ALTER TABLE `coupon_discount_code`
  ADD CONSTRAINT `FK_ocy3ip7m9037i43a1e6l9se97` FOREIGN KEY (`coupon_disc_id`) REFERENCES `coupon_discount` (`id`),
  ADD CONSTRAINT `FK_m48qglovju1v8rmgoqvqa3ywa` FOREIGN KEY (`code_id`) REFERENCES `coupon_code` (`id`);

--
-- Constraints for table `coupon_discount_product`
--
ALTER TABLE `coupon_discount_product`
  ADD CONSTRAINT `FK_md67nwsxu3q20l40kpe9rh7pk` FOREIGN KEY (`product_id`) REFERENCES `test_product` (`id`),
  ADD CONSTRAINT `FK_2anktmwul46do6hhglxb2o4gg` FOREIGN KEY (`coupon_disc_id`) REFERENCES `coupon_discount` (`id`);

--
-- Constraints for table `coupon_discount_req`
--
ALTER TABLE `coupon_discount_req`
  ADD CONSTRAINT `FK_j7tyxk9w5sf0q4rbu7fl2ub6i` FOREIGN KEY (`user_id`) REFERENCES `test_user` (`id`);

--
-- Constraints for table `coupon_discount_req_code`
--
ALTER TABLE `coupon_discount_req_code`
  ADD CONSTRAINT `FK_2bjiywxb2mmapu6j1u8vtb9mk` FOREIGN KEY (`coupon_disc_req_id`) REFERENCES `coupon_discount_req` (`id`),
  ADD CONSTRAINT `FK_jgxiy222jqxth7ine58d6rwfv` FOREIGN KEY (`code_id`) REFERENCES `coupon_code` (`id`);

--
-- Constraints for table `coupon_discount_req_prod`
--
ALTER TABLE `coupon_discount_req_prod`
  ADD CONSTRAINT `FK_hmb88fmgapwwuhoo94h81ewpo` FOREIGN KEY (`product_id`) REFERENCES `test_product` (`id`),
  ADD CONSTRAINT `FK_3t0aj8bwilwjt4cpkkujx0cjf` FOREIGN KEY (`coupon_disc_req_id`) REFERENCES `coupon_discount_req` (`id`);

--
-- Constraints for table `coupon_discount_summary`
--
ALTER TABLE `coupon_discount_summary`
  ADD CONSTRAINT `FK_jr35rxuwxs7jodmp9w90a6g8v` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`);

--
-- Constraints for table `coupon_disc_req_audit`
--
ALTER TABLE `coupon_disc_req_audit`
  ADD CONSTRAINT `FK_8gxxyiv54ce8aewodkfw6w4ii` FOREIGN KEY (`user_id`) REFERENCES `test_user` (`id`),
  ADD CONSTRAINT `FK_2ets8xynpnmo9v4wfgickyk5r` FOREIGN KEY (`coupon_disc_req_id`) REFERENCES `coupon_discount_req` (`id`);

--
-- Constraints for table `coupon_disc_req_code_audit`
--
ALTER TABLE `coupon_disc_req_code_audit`
  ADD CONSTRAINT `FK_s2a5unsktm31lw2d52eb8pvxw` FOREIGN KEY (`cdr_audit_id`) REFERENCES `coupon_disc_req_audit` (`id`),
  ADD CONSTRAINT `FK_s1mkih6sb66k92lpmnxe2de7f` FOREIGN KEY (`code_id`) REFERENCES `coupon_code` (`id`);

--
-- Constraints for table `coupon_disc_req_prod_audit`
--
ALTER TABLE `coupon_disc_req_prod_audit`
  ADD CONSTRAINT `FK_95u5jwecq7sdvwwvqkv6hri5j` FOREIGN KEY (`product_id`) REFERENCES `test_product` (`id`),
  ADD CONSTRAINT `FK_adg9da2vd2k8w0ugjqnooj4qw` FOREIGN KEY (`cdr_audit_id`) REFERENCES `coupon_disc_req_audit` (`id`);

--
-- Constraints for table `coupon_engine_config_audit`
--
ALTER TABLE `coupon_engine_config_audit`
  ADD CONSTRAINT `FK_b2oemqt1mdtir97hk1rjkbcka` FOREIGN KEY (`cpn_engine_conf_id`) REFERENCES `coupon_engine_config` (`id`);

--
-- Constraints for table `coupon_product_map`
--
ALTER TABLE `coupon_product_map`
  ADD CONSTRAINT `FK_jeclss59g3pbbk0v8fycttf8d` FOREIGN KEY (`product_id`) REFERENCES `test_product` (`id`),
  ADD CONSTRAINT `FK_nw0c4lumv693ggxwswel42n7t` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`);

--
-- Constraints for table `test_category`
--
ALTER TABLE `test_category`
  ADD CONSTRAINT `FK_b7if9nydxkiw5s2w8d85mrr9e` FOREIGN KEY (`parent_category_id`) REFERENCES `test_category` (`id`);

--
-- Constraints for table `test_product`
--
ALTER TABLE `test_product`
  ADD CONSTRAINT `FK_c4d299tcho2ffr8q1hd5q7295` FOREIGN KEY (`parent_product_id`) REFERENCES `test_product` (`id`),
  ADD CONSTRAINT `FK_1pvcyxgqp5q63bx44l7q2li27` FOREIGN KEY (`category_id`) REFERENCES `test_category` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;