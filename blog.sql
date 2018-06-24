-- 创建数据库
CREATE DATABASE IF NOT EXISTS blog DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE blog;


-- 用户表
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `login_name` varchar(50) NOT NULL,
    `pass_word` varchar(50) NOT NULL,    
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO user_info VALUES("1", "admin", "111111");


-- 文章分类表
DROP TABLE IF EXISTS `type_info`;
CREATE TABLE `type_info` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `sort` int(11) NOT NULL COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO type_info VALUES(1, "JavaEE", 1), 
                            (2, "Java", 2);


-- 文章
DROP TABLE IF EXISTS `article_info`;
CREATE TABLE `article_info` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `title` varchar(50) NOT NULL,
    `content` text NOT NULL,
    `content_text` varchar(200) NOT NULL COMMENT '文章简介',
    `cover` varchar(100) NOT NULL COMMENT '封面',
    `view_count` int(11) NOT NULL,
    `update_time` datetime NOT NULL COMMENT '文章最后一次更新时间',
    `status` int(11) NOT NULL COMMENT '文章状态 1: 正常 0:回收站',
    `type_id` int(11) NOT NULL COMMENT '文章分类',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
