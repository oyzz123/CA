package com.campus.filter;

import com.alibaba.fastjson.JSONObject;
import com.campus.result.Result;
import com.campus.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/user/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}


    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //执行放行前操作

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //获取请求url
        String url = httpServletRequest.getRequestURL().toString();

        //判断url中是否包含login，如果包含说明是登录请求，直接放行
        if(url.contains("login")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;//登陆后不需要执行外面代码，需要直接返回
        }

        if(url.contains("register")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;//登陆后不需要执行外面代码，需要直接返回
        }

        //获取请求头中的令牌进行校验
        String jwt = httpServletRequest.getHeader("token");

        //未登录
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头为空，返回未登录信息");
            Result error = Result.error("未登录");
            //需要返回json格式的数据返回给浏览器
            String notLogin = JSONObject.toJSONString(error);
            httpServletResponse.getWriter().write(notLogin);//将错误信息返回给浏览器
            return;
        }

        //解析token，如果解析失败，返回错误结果(未登录)
        try {
            JwtUtil.parseToken(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析令牌失败，返回未登录信息");
            Result error = Result.error("未登录");
            //需要返回json格式的数据返回给浏览器
            String notLogin = JSONObject.toJSONString(error);
            httpServletResponse.getWriter().write(notLogin);//将错误信息返回给浏览器
            return;
        }
        //放行
        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
