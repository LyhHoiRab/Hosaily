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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='导师表';


CREATE TABLE `tag` (
  `id` varchar(32) NOT NULL,
  `name` varchar(60) NOT NULL,
  `description` varchar(60) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

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

CREATE TABLE `course_course` (
  `parent_id` varchar(32) NOT NULL,
  `children_id` varchar(32) NOT NULL,
  PRIMARY KEY (`parent_id`,`children_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程层级表';

CREATE TABLE `course_tag` (
  `course_id` varchar(32) NOT NULL,
  `tag_id` varchar(32) NOT NULL,
  PRIMARY KEY (`course_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程 - 标签关联表';

CREATE TABLE `course_media` (
  `course_id` varchar(32) NOT NULL,
  `media_id` varchar(32) NOT NULL,
  PRIMARY KEY (`course_id`,`media_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程 - 媒体关联表';

CREATE TABLE `course_level` (
  `course_id` varchar(32) NOT NULL,
  `level_id` varchar(32) NOT NULL,
  PRIMARY KEY (`course_id`,`level_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程 -  等级关联表';

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id_UNIQUE` (`open_id`),
  UNIQUE KEY `union_id_UNIQUE` (`union_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信账号';

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id_UNIQUE` (`open_id`),
  UNIQUE KEY `union_id_UNIQUE` (`union_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小程序账户';

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

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

CREATE TABLE `attention` (
  `id` varchar(32) NOT NULL,
  `account_id` varchar(32) NOT NULL,
  `course_id` varchar(32) NOT NULL,
  `type` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程关注表';


