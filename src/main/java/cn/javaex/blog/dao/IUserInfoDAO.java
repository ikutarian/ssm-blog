package cn.javaex.blog.dao;

import cn.javaex.blog.view.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface IUserInfoDAO {

    UserInfo selectUser(@Param("loginName") String loginName,
                        @Param("passWord") String passWord);
}
