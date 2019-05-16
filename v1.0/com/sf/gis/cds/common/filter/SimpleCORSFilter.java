package com.sf.gis.cds.common.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sf.gis.cds.common.constant.CommonConstanst;
import com.sf.gis.cds.common.model.TokenCheckResult;
import com.sf.gis.cds.common.util.TokenMgrUtil;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SimpleCORSFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCORSFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String originHeader = ((HttpServletRequest) servletRequest).getHeader("Origin");
        String authToken = ((HttpServletRequest) servletRequest).getHeader("Authentication-Token");
//        if (authToken != null && !authToken.isEmpty()) {
//            TokenCheckResult tokenCheckResult = TokenMgrUtil.validateJWT(authToken);
//            if (tokenCheckResult.isSuccess()) {
//                Claims claims = tokenCheckResult.getClaims();
//                JSONObject obj = JSON.parseObject(claims.getSubject());
//                ((HttpServletRequest) servletRequest).setAttribute(CommonConstanst.GLOBAL_USER_INFO, obj);
//            } else {
//                ((HttpServletRequest) servletRequest).removeAttribute(CommonConstanst.GLOBAL_USER_INFO);
//            }
//        }
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "x-requested-with,Cache-Control,Pragma,Content-Type,Authorization,Authentication-Token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}