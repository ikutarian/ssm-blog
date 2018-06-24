package cn.javaex.blog.service.impl;

import cn.javaex.blog.dao.IUserInfoDAO;
import cn.javaex.blog.service.UserInfoService;
import cn.javaex.blog.view.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private IUserInfoDAO iUserInfoDAO;

    @Override
    public UserInfo selectUser(String loginName, String passWord) {
        return iUserInfoDAO.selectUser(loginName, passWord);
    }
}
