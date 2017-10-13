DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `available` tinyint(4) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(32) DEFAULT NULL,
  `permission` varchar(32) DEFAULT NULL,
  `resource_type` varchar(32) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `available` tinyint(4) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `role` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `permission_id` bigint(20) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `role_id` bigint(20) NOT NULL,
  `uid` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `sys_permission` (`id`,`available`,`name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`) VALUES (1,0,'用户管理',0,'0/','userInfo:view','menu','userInfo/userList');
INSERT INTO `sys_permission` (`id`,`available`,`name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`) VALUES (2,0,'用户增加',1,'0/1','userInfo:add','button','userInfo/userAdd');
INSERT INTO `sys_permission` (`id`,`available`,`name`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`) VALUES (3,0,'用户删除',1,'0/1','userInfo:del','button','userInfo/userDel');
INSERT INTO `sys_role` (`id`,`available`,`description`,`role`) VALUES (1,0,'管理员','admin');
INSERT INTO `sys_role` (`id`,`available`,`description`,`role`) VALUES (2,0,'vip会员','vip');
INSERT INTO `sys_role_permission` (`role_id`,`permission_id`) VALUES (1,1);
INSERT INTO `sys_role_permission` (`role_id`,`permission_id`) VALUES (1,2);
INSERT INTO `sys_user_role` (`role_id`,`uid`) VALUES (1,40);
INSERT INTO `sys_user_role` (`role_id`,`uid`) VALUES (2,40);

-- ----------------------------
-- Table structure for sys_permission_init
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_init`;
CREATE TABLE `sys_permission_init` (
  `id` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `permission_init` varchar(255) DEFAULT NULL COMMENT '需要具备的权限',
  `sort` int(50) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `sys_permission_init` (`id`,`url`,`permission_init`,`sort`) VALUES ('1','/login*','anon',1);
INSERT INTO `sys_permission_init` (`id`,`url`,`permission_init`,`sort`) VALUES ('2','/loginUser','anon',2);
INSERT INTO `sys_permission_init` (`id`,`url`,`permission_init`,`sort`) VALUES ('3','/logout*','anon',3);
INSERT INTO `sys_permission_init` (`id`,`url`,`permission_init`,`sort`) VALUES ('4','/error/*','anon',4);
INSERT INTO `sys_permission_init` (`id`,`url`,`permission_init`,`sort`) VALUES ('5','/index*','authc',5);
INSERT INTO `sys_permission_init` (`id`,`url`,`permission_init`,`sort`) VALUES ('6','/*','authc',6);
INSERT INTO `sys_permission_init` (`id`,`url`,`permission_init`,`sort`) VALUES ('7','/**','authc',7);
INSERT INTO `sys_permission_init` (`id`,`url`,`permission_init`,`sort`) VALUES ('8','/*.*','authc',8);
