/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50726
Source Host           : 127.0.0.1:3306
Source Database       : sf

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-06-01 14:50:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for form
-- ----------------------------
DROP TABLE IF EXISTS `form`;
CREATE TABLE `form` (
  `formId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `formName` varchar(255) COLLATE utf8_bin NOT NULL,
  `size` int(11) NOT NULL DEFAULT '0' COMMENT 'inputData.rowIndex的游标',
  PRIMARY KEY (`formId`),
  KEY `fk_form_userId` (`userId`),
  CONSTRAINT `fk_form_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of form
-- ----------------------------
INSERT INTO `form` VALUES ('1', '2', '发', '1');

-- ----------------------------
-- Table structure for input
-- ----------------------------
DROP TABLE IF EXISTS `input`;
CREATE TABLE `input` (
  `inputId` int(11) NOT NULL AUTO_INCREMENT,
  `formId` int(11) NOT NULL,
  `colIndex` tinyint(4) unsigned NOT NULL,
  `type` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '例如(text,password,email,number,radio,checkbox)',
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '(例如：男|女)',
  PRIMARY KEY (`inputId`),
  KEY `fk_input_formId` (`formId`),
  CONSTRAINT `fk_input_formId` FOREIGN KEY (`formId`) REFERENCES `form` (`formId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of input
-- ----------------------------
INSERT INTO `input` VALUES ('1', '1', '0', 'sys', 'IP地址', null);
INSERT INTO `input` VALUES ('2', '1', '1', 'sys', '提交时间', null);
INSERT INTO `input` VALUES ('3', '1', '2', 'text', '文本框', '');

-- ----------------------------
-- Table structure for inputdata
-- ----------------------------
DROP TABLE IF EXISTS `inputdata`;
CREATE TABLE `inputdata` (
  `inputDataId` int(11) NOT NULL AUTO_INCREMENT,
  `inputId` int(11) NOT NULL,
  `rowIndex` int(11) NOT NULL,
  `value` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`inputDataId`),
  KEY `fk_inputData_inputId` (`inputId`),
  CONSTRAINT `fk_inputData_inputId` FOREIGN KEY (`inputId`) REFERENCES `input` (`inputId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of inputdata
-- ----------------------------
INSERT INTO `inputdata` VALUES ('1', '1', '0', '127.0.0.1');
INSERT INTO `inputdata` VALUES ('2', '2', '0', '2019-09-19 01:05:38');
INSERT INTO `inputdata` VALUES ('3', '3', '0', '666');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(32) COLLATE utf8_bin NOT NULL,
  `pwd` varchar(16) COLLATE utf8_bin NOT NULL,
  `role` tinyint(4) NOT NULL COMMENT '0=管理员, 1=普通用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '744138210@qq.com', '123456', '0');
INSERT INTO `user` VALUES ('2', 'test@qq.com', '123456', '1');
