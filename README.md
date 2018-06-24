一个以 SSM 作为框架搭建的个人博客。

## 关于项目

* 项目是一个个人博客，主要用来练手
* 使用 Maven 构建
* 后端使用 Spring - SpringMVC -MyBatis，前端使用 JQuery - JavaEx 搭建

## 功能介绍

* 后台管理系统
* 文章分类
* 标签
* 浏览量统计
* 富文本编辑

## 使用步骤

* Java
* MySQL 5.x
* Spring 4.x
* SpringMVC 4.x
* MyBatis 3.x
* 其他依赖看 pom.xml 即可

## 克隆项目到本地

使用如下命令讲项目克隆到本地：

```
git@github.com:ikutarian/ssm-blog.git
```

## 数据库初始化

数据库初始化语句在 `blog.sql` 文件中。只需要执行

```sql
source blog.sql
```

即可导入数据库。

接着，打开 `jdbc.properties`，根据本地环境修改数据库的配置信息

```
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=utf8
jdbc.username=root
jdbc.password=
```

## 运行项目

使用 Tomcat 运行项目

## 打开浏览器查看项目运行效果

在浏览器中输入 localhost:8080/blog/ 即可查看项目的运行效果

## 要进入后台管理系统

在浏览器中输入 localhost:8080/blog/admin/index.action，账号密码分别是：admin 和 111111

## 知识点

关于本项目用到的知识点，可以在 doc 文件夹中找到，目前总结了两份文档

* SSM文档.md
* 知识点.md

可以查看这些文档学习

## 反馈

有任何问题可以提交 issues