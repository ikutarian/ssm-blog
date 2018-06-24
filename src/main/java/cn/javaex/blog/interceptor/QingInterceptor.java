package cn.javaex.blog.interceptor;

import cn.javaex.blog.view.UserInfo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class QingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求地址
        String url = request.getRequestURI();
        // 对特殊地址，直接放行
        if (url.indexOf("login") > 0 || url.indexOf("portal") > 0) {
            return true;
        }
        // 判断session，session存在的话，登陆后台
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (userInfo != null) {
            // 身份存在，放行
            return true;
        }

        // 跳转到登陆页面
        request.getRequestDispatcher("/WEB-INF/page/admin/login.jsp").forward(request, response);

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
