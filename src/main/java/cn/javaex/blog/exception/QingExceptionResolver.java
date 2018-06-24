package cn.javaex.blog.exception;

import cn.javaex.blog.view.Result;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QingExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 向控制台打印错误信息
        ex.printStackTrace();

        // 定义一个错误信息
        String message = "系统繁忙，请稍后重试";
        if (ex instanceof QingException) {
            message = ex.getMessage();
        }

        // 判断请求类型
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        ResponseBody responseBody = handlerMethod.getMethod().getAnnotation(ResponseBody.class);
        if (responseBody != null) {
            // 如果是 json 请求，返回 json 数据
            String json = new Gson().toJson(Result.error(message));
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            try {
                response.getWriter().write(json);
                response.getWriter().flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // 返回一个空的ModelAndView表示已经手动生成响应
            return new ModelAndView();
        }

        // 如果是跳转页面请求，则跳转至错误页面
        ModelAndView modelAndView = new ModelAndView();
        //将错误信息传到页面
        modelAndView.addObject("message", message);
        //指向错误页面
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
