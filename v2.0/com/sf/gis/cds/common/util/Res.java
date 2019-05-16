package com.sf.gis.cds.common.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import com.sf.gis.cds.common.constant.CommonConstanst;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Data
@EqualsAndHashCode(callSuper=false)
public class Res<T> extends SwaggerJsonSerializer {
    private static final Logger logger = LoggerFactory.getLogger(Res.class);
    private String status;
    private T result;
    private String message;
    private String detail;
//    private CasProperties casProperties;
    public Res(){

    }
    public static final String OK = "ok";
    public static final String FAIL = "fail";

    public static Res<?> buildOk() {
        Res<?> res = new Res();
        res.setStatus(OK);
        res.setMessage("ok");
        return res;
    }
    public static void responseOutWithHtml(HttpServletResponse response, String url) {
        // 将实体对象转换为JSON Object转换
        String result = "<html><head><script language='javascript'>(function(){window.location.href='"+url+"'})()</script></head> </html>";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(result);
            logger.debug("返回是\n");
        } catch (IOException e) {
            logger.error("写入错误", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static void responseOutWithJson(HttpServletResponse response, Object responseObject) {
        // 将实体对象转换为JSON Object转换
        String responseJSONObject = JSON.toJSONString(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject);
            logger.debug("返回是\n");
            logger.debug(responseJSONObject);
        } catch (IOException e) {
            logger.error("写入错误", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    public static String getCookie(String key) {
        String cookie = null;
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        Cookie[] cookies = req.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            Cookie c = cookies[i];
            if (key.equals(c.getName())) {
                cookie = c.getValue();
            }
        }
        return cookie;
    }
    public static Cookie setCookie(String id){
        if(id != null) {
            Cookie cookie = new Cookie("JSESSIONID", id);
            cookie.setDomain(".sf-express.com");
            cookie.setPath("/");
            return cookie;
        } else {
            return null;
        }
    }
    public static void setUserName(Object userName){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        if(userName != null) {
            Cookie cookie = new Cookie("_sf.bdp.TOKEN_", userName.toString());
            cookie.setDomain(".sf-express.com");
        }

        req.getSession().setAttribute(CommonConstanst.LOGIN_USER_EMP_NO, userName);
    }


    public static String getUserName(){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return (String) req.getSession().getAttribute(CommonConstanst.LOGIN_USER_EMP_NO);
    }
    
    public static void setUserMobile(String userMobile){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        req.getSession().setAttribute(CommonConstanst.LOGIN_USER_MOBILE, userMobile);
    }
    
    public static String getUserMobile(){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return (String) req.getSession().getAttribute(CommonConstanst.LOGIN_USER_MOBILE);
    }

    public static Res<?> buildOk(String msg) {
        return build("ok", new Object(), msg);
    }

    public static Res<?> buildOk(Object result) {
        return build("ok", result, (String) null);
    }

    public static Res<?> buildOk(Object result, String msg) {
        return build("ok", result, msg);
    }

    public static Res<?> buildOkOrMsg(Object data, String errMsg) {
        Res<?> res;
        if (data != null) {
            res = build(OK, data, "");
        } else {
            res = buildFail(errMsg);
        }
        return res;
    }

    public static Res<?> build(String succ, Object result, String msg) {
        Res res = new Res();
        res.setStatus(succ);
        res.setMessage(msg);
        res.setResult(result);
        return res;
    }

    public static Res<?> buildFail(String msg) {
        return build("fail", (Object) null, msg);
    }

    public static Res<?> buildFail(Object result, String msg) {
        return build("fail", result, msg);
    }
    
    public static Res<?> buildWxLogin(Object result) {
        return build("wxLogin", result, (String) null);
    }
}
