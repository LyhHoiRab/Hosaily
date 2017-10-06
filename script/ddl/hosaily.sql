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
