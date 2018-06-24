package cn.javaex.blog.action;

import cn.javaex.blog.exception.QingException;
import cn.javaex.blog.service.UserInfoService;
import cn.javaex.blog.view.Result;
import cn.javaex.blog.view.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class UserInfoAction {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("index.action")
    public String index() {
        return "admin/index";
    }

    @RequestMapping("login.action")
    public String login() {
        return "admin/login";
    }

    @RequestMapping("login.json")
    @ResponseBody
    public Result login2(HttpServletRequest request) throws QingException {
        // 获取参数
        String loginName = request.getParameter("login_name");
        String passWord = request.getParameter("pass_word");
        // 校验参数
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(passWord)) {
            throw new QingException("用户名或密码不能为空");
        }
        UserInfo userInfo = userInfoService.selectUser(loginName, passWord);
        if (userInfo == null) {
            throw new QingException("用户名或密码不正确");
        }
        // 设置session
        request.getSession().setAttribute("userInfo", userInfo);

        return Result.success();
    }

    @RequestMapping("login_out.action")
    public String logOut(HttpSession session) {
        // 销毁session
        session.invalidate();
        return "admin/login";
    }
}
