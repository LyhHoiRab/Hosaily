-- 应用表
CREATE TABLE `application` (
  `id` varchar(32) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `app_id` varchar(60) NOT NULL,
  `secret` varchar(60) NOT NULL,
  `aes_key` varchar(45) DEFAULT NULL,
  `token` varchar(45) NOT NULL,
  `type` int(1) NOT NULL,
  `state` int(1) NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token_UNIQUE` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;