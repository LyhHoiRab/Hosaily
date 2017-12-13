DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` varchar(32) NOT NULL,
  `loginname` varchar(60) DEFAULT NULL,
  `wechat` varchar(60) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginname_UNIQUE` (`loginname`),
  UNIQUE KEY `wechat_UNIQUE` (`wechat`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户表';


DROP TABLE IF EXISTS `account_course`;
CREATE TABLE `account_course` (
  `id` varchar(32) NOT NULL,
  `account_id` varchar(32) NOT NULL,
  `course_id` varchar(32) NOT NULL,
  `effective` int(1) NOT NULL DEFAULT '0',
  `force_time` datetime NOT NULL,
  `deadline` datetime NOT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `advisor`;
CREATE TABLE `advisor` (
  `id` varchar(32) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `wechat` varchar(60) DEFAULT NULL,
  `age` int(1) DEFAULT NULL,
  `introduction` mediumtext,
  `head_img_url` varchar(255) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `sort` int(1) DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `organization_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='导师表';


DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id` varchar(32) NOT NULL,
  `library_id` varchar(32) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `analysis` varchar(255) DEFAULT NULL,
  `is_true` tinyint(1) DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题库答案表';


DROP TABLE IF EXISTS `application`;
CREATE TABLE `application` (
  `id` varchar(32) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `app_id` varchar(45) NOT NULL,
  `secret` varchar(45) DEFAULT NULL,
  `aes_key` varchar(45) DEFAULT NULL,
  `token` varchar(45) NOT NULL,
  `type` tinyint(1) NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '0',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用表';


DROP TABLE IF EXISTS `attention`;
CREATE TABLE `attention` (
  `id` varchar(32) NOT NULL,
  `account_id` varchar(32) NOT NULL,
  `course_id` varchar(32) NOT NULL,
  `type` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程关注表';


DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` varchar(32) NOT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `course_id` varchar(32) NOT NULL,
  `sender` varchar(32) NOT NULL,
  `receiver` varchar(32) DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';


DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` varchar(32) NOT NULL,
  `type` tinyint(1) NOT NULL,
  `kind` tinyint(1) NOT NULL,
  `title` varchar(60) DEFAULT NULL,
  `introduction` mediumtext,
  `summary` varchar(255) DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `price` double DEFAULT '0',
  `likes` int(1) DEFAULT '0',
  `view` int(1) DEFAULT '0',
  `sort` int(1) DEFAULT '0',
  `comments` int(1) DEFAULT '0',
  `advisor_id` varchar(32) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';


DROP TABLE IF EXISTS `course_course`;
CREATE TABLE `course_course` (
  `parent_id` varchar(32) NOT NULL,
  `children_id` varchar(32) NOT NULL,
  PRIMARY KEY (`parent_id`,`children_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程层级表';


DROP TABLE IF EXISTS `course_level`;
CREATE TABLE `course_level` (
  `course_id` varchar(32) NOT NULL,
  `level_id` varchar(32) NOT NULL,
  PRIMARY KEY (`course_id`,`level_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程 -  等级关联表';


DROP TABLE IF EXISTS `course_media`;
CREATE TABLE `course_media` (
  `course_id` varchar(32) NOT NULL,
  `media_id` varchar(32) NOT NULL,
  PRIMARY KEY (`course_id`,`media_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程 - 媒体关联表';

DROP TABLE IF EXISTS `course_tag`;
CREATE TABLE `course_tag` (
  `course_id` varchar(32) NOT NULL,
  `tag_id` varchar(32) NOT NULL,
  PRIMARY KEY (`course_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程 - 标签关联表';


DROP TABLE IF EXISTS `customization`;
CREATE TABLE `customization` (
  `id` varchar(32) NOT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `title` varchar(60) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `introduction` mediumtext,
  `subscribe` int(1) DEFAULT NULL,
  `sort` int(1) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定制服务表';


DROP TABLE IF EXISTS `level`;
CREATE TABLE `level` (
  `id` varchar(32) NOT NULL,
  `name` varchar(60) DEFAULT NULL,
  `icon` varchar(256) DEFAULT NULL,
  `description` varchar(60) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='等级表';


DROP TABLE IF EXISTS `level_price`;
CREATE TABLE `level_price` (
  `id` varchar(32) NOT NULL,
  `level_id` varchar(32) NOT NULL,
  `price` double NOT NULL DEFAULT '0',
  `effective` int(1) NOT NULL DEFAULT '0',
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='等级价格表';


DROP TABLE IF EXISTS `media`;
CREATE TABLE `media` (
  `id` varchar(32) NOT NULL,
  `file_name` varchar(45) NOT NULL,
  `suffix` varchar(5) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `md5` varchar(32) NOT NULL,
  `size` int(1) DEFAULT NULL,
  `remark` varchar(60) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `md5_UNIQUE` (`md5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='媒体资源表';


DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` varchar(32) NOT NULL,
  `order_id` varchar(45) NOT NULL,
  `sales_user` varchar(32) NOT NULL,
  `sales_account` varchar(32) NOT NULL,
  `client_user` varchar(32) NOT NULL,
  `client_account` varchar(32) NOT NULL,
  `price` double NOT NULL DEFAULT '0',
  `pay` double NOT NULL DEFAULT '0',
  `remark` varchar(255) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';


DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id` varchar(32) NOT NULL,
  `name` varchar(60) DEFAULT NULL,
  `token` varchar(45) NOT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token_UNIQUE` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `pay_logs`;
CREATE TABLE `pay_logs` (
  `id` varchar(32) NOT NULL,
  `order_id` varchar(32) NOT NULL,
  `pay` double NOT NULL DEFAULT '0',
  `payment` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付记录表';


DROP TABLE IF EXISTS `payment_type`;
CREATE TABLE `payment_type` (
  `id` varchar(32) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付方式表';


DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` varchar(32) NOT NULL,
  `name` varchar(60) NOT NULL,
  `description` varchar(60) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `organization_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';



DROP TABLE IF EXISTS `test_library`;
CREATE TABLE `test_library` (
  `id` varchar(32) NOT NULL,
  `title` varchar(60) DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `kind` varchar(10) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题库表';


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `account_id` varchar(32) NOT NULL,
  `nickname` varchar(60) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `id_card` varchar(18) DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `position` varchar(60) DEFAULT NULL,
  `head_img_url` varchar(255) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `has_role` tinyint(1) NOT NULL DEFAULT '0',
  `code` int(1) NOT NULL AUTO_INCREMENT,
  `advisor_id` varchar(32) DEFAULT NULL,
  `wechat` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';


DROP TABLE IF EXISTS `user_expand`;
CREATE TABLE `user_expand` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `name` varchar(60) NOT NULL,
  `value` varchar(255) NOT NULL,
  `description` varchar(60) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息扩展表';


DROP TABLE IF EXISTS `wechat_account`;
CREATE TABLE `wechat_account` (
  `id` varchar(32) NOT NULL,
  `app_id` varchar(60) NOT NULL,
  `open_id` varchar(60) NOT NULL,
  `union_id` varchar(60) DEFAULT NULL,
  `subscribe` tinyint(1) NOT NULL DEFAULT '0',
  `nickname` varchar(60) NOT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `language` varchar(60) DEFAULT NULL,
  `city` varchar(60) DEFAULT NULL,
  `province` varchar(60) DEFAULT NULL,
  `country` varchar(60) DEFAULT NULL,
  `head_img_url` varchar(255) DEFAULT NULL,
  `subscribe_time` datetime DEFAULT NULL,
  `remark` varchar(60) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `precision` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id_UNIQUE` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信账号';


DROP TABLE IF EXISTS `xcx_account`;
CREATE TABLE `xcx_account` (
  `id` varchar(32) NOT NULL,
  `app_id` varchar(60) NOT NULL,
  `union_id` varchar(60) DEFAULT NULL,
  `open_id` varchar(60) NOT NULL,
  `nickname` varchar(60) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `language` varchar(60) DEFAULT NULL,
  `country` varchar(60) DEFAULT NULL,
  `province` varchar(60) DEFAULT NULL,
  `city` varchar(60) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `pure_phone_number` varchar(20) DEFAULT NULL,
  `country_code` varchar(10) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id_UNIQUE` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小程序账户';





DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` varchar(32) NOT NULL,
  `sim` varchar(45) DEFAULT NULL,
  `out_going_num` varchar(45) DEFAULT NULL,
  `num` varchar(45) DEFAULT NULL,
  `time` varchar(60) DEFAULT NULL,
  `user_name` varchar(60) DEFAULT NULL,
  `path` varchar(256) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='录音表';


DROP TABLE IF EXISTS `sim_num_user`;
CREATE TABLE `sim_num_user` (
  `sim` varchar(32) NOT NULL,
  `num` varchar(45) DEFAULT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`sim`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='sim序号手机号码使用者对照表';

CREATE TABLE `web_resource` (
  `id` varchar(32) NOT NULL,
  `description` varchar(60) DEFAULT NULL,
  `domain` varchar(100) NOT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `css_url` varchar(255) DEFAULT NULL,
  `js_url` varchar(255) DEFAULT NULL,
  `mobile_img_url` varchar(255) DEFAULT NULL,
  `mobile_css_url` varchar(255) DEFAULT NULL,
  `mobile_js_url` varchar(255) DEFAULT NULL,
  `organization_id` varchar(32) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `domain_UNIQUE` (`domain`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `account_group` (
  `account_id` varchar(32) NOT NULL,
  `group_id` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户 - 分组关联表';
