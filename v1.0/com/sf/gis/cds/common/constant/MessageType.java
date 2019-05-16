package com.sf.gis.cds.common.constant;
/**
 * 消息推送类型
 * @author 01379875
 *
 */
public class MessageType {

    public final static String SEND_SUCCEFF = "发送成功";
    public final static String SEND_FAILED = "发送失败，请重试";
    public final static String MOBILE_NUMBER_ERROR = "手机号格式错误";
    public final static String MOBILE_IS_NULL = "手机号不能为空";
    public final static String MSG_IS_TOO_LONG = "请求的短信内容太长";
    public final static String MSG_SEND_MANY_TIMES = "短信短时间内发送太频繁";
    public final static String SINGLE_MOBLIE_SEND_TOO_MANYS1 = "单个手机号30秒内下发短信条数超过设定的上限";
    public final static String SINGLE_MOBLIE_SEND_TOO_MANYS2 = "单个手机号1小时内下发短信条数超过设定的上限";
    public final static String SINGLE_MOBLIE_SEND_TOO_MANYS3 = "单个手机号下发相同内容超过设定的上限";
    public final static String MSG_CONTENT_ERROR = "短信内容中含有敏感词";

}
