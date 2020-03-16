/*
Navicat MySQL Data Transfer

Source Server         : local—MySQL
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2018-07-28 19:32:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `category` varchar(32) DEFAULT NULL COMMENT '文章所属分类id',
  `content` longtext NOT NULL COMMENT '文章内容',
  `source` int(1) unsigned zerofill NOT NULL COMMENT '文章来源',
  `source_url` varchar(255) DEFAULT NULL COMMENT '文章出处',
  `title` varchar(255) NOT NULL COMMENT '文章标题',
  `user_id` varchar(50) NOT NULL COMMENT '创建用户id',
  `publish_state` int(1) NOT NULL DEFAULT '0' COMMENT '发布状态（0：未发布1：发布2:被删除）',
  `publish_date` datetime NOT NULL COMMENT '发布时间',
  `update_date` datetime DEFAULT NULL COMMENT '最后更新日期',
  `like_count` int(10) DEFAULT '0' COMMENT '喜欢数',
  `visit_count` int(10) DEFAULT '0' COMMENT '阅读数',
  `comment_count` int(10) DEFAULT '0' COMMENT '评论数',
  `author` varchar(50) DEFAULT NULL COMMENT '作者',
  `tags` varchar(255) DEFAULT NULL,
  `comment_disabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -
-- ----------------------------
-- Table structure for article_comment
-- ----------------------------
DROP TABLE IF EXISTS `article_comment`;
CREATE TABLE `article_comment` (
  `id` varchar(50) NOT NULL,
  `content` varchar(2000) NOT NULL COMMENT '评论内容',
  `date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '评论时间',
  `user_id` varchar(50) NOT NULL COMMENT '评论用户id',
  `article_id` varchar(50) NOT NULL COMMENT '文章id',
  `pid` varchar(50) DEFAULT NULL COMMENT '父级id',
  `floor` int(5) DEFAULT NULL COMMENT '楼层',
  `to_user_id` varchar(50) DEFAULT NULL COMMENT '回复的用户id',
  PRIMARY KEY (`id`),
  KEY `article_comment_ibfk_3` (`to_user_id`),
  KEY `article_comment_ibfk_1` (`article_id`),
  KEY `article_comment_ibfk_2` (`user_id`),
  CONSTRAINT `article_comment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表';

-- ----------------------------
-- Records of article_comment
-- ----------------------------


-- ----------------------------
-- Table structure for article_like
-- ----------------------------
DROP TABLE IF EXISTS `article_like`;
CREATE TABLE `article_like` (
  `id` varchar(50) NOT NULL,
  `article_id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL COMMENT '用户id',
  `date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `article_like_ibfk_1` (`article_id`),
  KEY `article_like_ibfk_2` (`user_id`),
  CONSTRAINT `article_like_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_like_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article_like
-- ----------------------------

-- ----------------------------
-- Table structure for sys_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_action`;
CREATE TABLE `sys_action` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户id',
  `action` int(3) NOT NULL COMMENT '操作',
  `ip` varchar(255) NOT NULL,
  `agent` varchar(255) NOT NULL,
  `date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `sys_action_ibfk_1` (`user_id`),
  CONSTRAINT `sys_action_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_action
-- ----------------------------


-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `data_value` varchar(255) NOT NULL,
  `object_name` varchar(255) NOT NULL,
  `data_key` varchar(255) NOT NULL,
  PRIMARY KEY (`data_key`,`object_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('测试', 'article_category', '1');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` varchar(32) NOT NULL,
  `resource_name` varchar(255) NOT NULL,
  `resource_type` int(3) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', 'testresoure', '1', null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(255) NOT NULL,
  `role_desc` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', 'admin');
INSERT INTO `sys_role` VALUES ('2', 'actuator', 'actuator');

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `resource_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`),
  KEY `resource_id` (`resource_id`),
  KEY `roles_id` (`role_id`),
  CONSTRAINT `sys_role_resource_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role_resource_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `sys_resource` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('1', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(50) NOT NULL,
  `account_expired` bit(1) NOT NULL DEFAULT b'0' COMMENT '账户是否过期',
  `account_locked` bit(1) NOT NULL DEFAULT b'0' COMMENT '账户是否锁定',
  `credentials_expired` bit(1) NOT NULL DEFAULT b'0' COMMENT '凭证是否过期',
  `enabled` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可用',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `nickname` varchar(50) NOT NULL COMMENT '昵称',
  `avatar` varchar(255) NOT NULL COMMENT '头像',
  `create_date` timestamp NOT NULL  ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_un` (`username`),
  UNIQUE KEY `nickname` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_userON UPDATE CURRENT_TIMESTAMP
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '\0', '\0', '\0', '', '$2a$04$7WANcpSietQ5SO8ASBcVx.iUOa/2pu8FGdb2XYEZmsvQ5jgKyexhG', 'admin', 'wangjc', 'http://image.nullpointer.site/avatar/default/logo3.png', '2018-07-26 16:52:42');
INSERT INTO `sys_user` VALUES ('4028ed8163afaf5b0163afde88f70001', '\0', '\0', '\0', '', '$2a$04$DHcTBbeVv4bevxmC2fETlOiYdwaOmx200nE99.0lxXVatXQVDTEsq', 'test', 'test', 'http://image.nullpointer.site/avatar/default/logo3.png', '2018-07-24 11:40:46');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  KEY `FKdpvc6d7xqpqr43dfuk1s27cqh` (`role_id`),
  KEY `FKd0ut7sloes191bygyf7a3pk52` (`user_id`),
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('1', '2');

-- ----------------------------
-- View structure for view_common_article
-- ----------------------------
DROP VIEW IF EXISTS `view_common_article`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER  VIEW `view_common_article` AS SELECT
	id,
	content,
	category,
	title,
	publish_date,
	source,
	source_url,
	author,
	like_count,
	visit_count,
	comment_disabled,
	comment_count
FROM
	article
WHERE
	publish_state = 1 ;
