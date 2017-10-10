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
  `introduction` varchar(255) DEFAULT NULL,
  `head_img_url` varchar(255) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
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
  `price` double NOT NULL DEFAULT '0',
  `likes` int(1) NOT NULL DEFAULT '0',
  `view` int(1) NOT NULL DEFAULT '0',
  `weight` int(1) NOT NULL DEFAULT '0',
  `comments` int(1) NOT NULL DEFAULT '0',
  `advisor_id` varchar(32) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

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




