package cn.javaex.blog.service;

import cn.javaex.blog.view.UserInfo;

public interface UserInfoService {

    UserInfo selectUser(String loginName, String passWord);
}
