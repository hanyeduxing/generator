package com.sf.gis.cds.common.log;


import static com.sf.gis.cds.common.log.AuditLogFormat.DEFAULT_FORMAT;
import static com.sf.gis.cds.common.log.AuditLogFormat.LOGIN_LOG_FMT;
import static com.sf.gis.cds.common.log.AuditLogFormat.ROLE_LOG_FMT;
import static com.sf.gis.cds.common.log.AuditLogFormat.SYS_CONF_LOG_FMT;
import static com.sf.gis.cds.common.log.AuditLogFormat.USER_LOG_FMT;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sf.gis.cds.common.util.DateUtil;
import com.sf.gis.cds.common.util.IPUtil;

@Component
public class AuditLoggerUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(AuditLoggerUtils.class);

	private AuditLoggerUtils(){
		// you don't need it
	}
	private static  String systemCode;
	
	/**
     * ################# 操作编码常量 #################
     */
    /**
     * 新增/添加001
     */
    public static final String ADD = "001";
    /**
     * 更新/变更/修改002
     */
    public static final String UPDATE = "002";
    /**
     * 删除003
     */
    public static final String DELETE = "003";
    /**
     * 授予角色004
     */
    public static final String ACCREDIT_ROLE = "004";

	/**
	 * 授予权限005
	 */
	public static final String ACCREDIT_AUTHORITY = "005";
    /**
     * 登录013
     */
    public static final String LOGIN = "013";
    /**
     * 登出014
     */
    public static final String LOGINOUT = "014";
    /**
     * 查询010
     */
    public static final String QUERY = "010";
    
    public static final String LOG_RESULT_FOR_SUCCESS = "1";
   
    public static final String LOG_RESULT_FOR_FAIL = "0";
    
    public static final String LOGIN_FAIL_REASON = "认证失败";
    
    public static void loginFailLog(){
    	loginLog(LOG_RESULT_FOR_FAIL,LOGIN_FAIL_REASON);
    }
    
    public static void loginSuccLog(){
    	loginLog(LOG_RESULT_FOR_SUCCESS,null);
    }
	
	public static void loginLog(String result,String failReason){
		AuditLoggerUtils.doLoginLog(IPUtil.getRequestIp(),
                AuditLoggerUtils.getSystemCode(),
                IPUtil.getRequestHostName(),
                null,
                "login",
                result,
                failReason);
	}
	
	public static void logoutLog(String msg){
		AuditLoggerUtils.doLoginLog(IPUtil.getRequestIp(),
                AuditLoggerUtils.getSystemCode(),
                IPUtil.getRequestHostName(),
                null,
                "logout",
                LOG_RESULT_FOR_SUCCESS,
                msg);
	}
   
    @Value("${sys.sysCode}")
	public void setSystemCode(String sysCode){
		AuditLoggerUtils.systemCode = sysCode;
	}
	
	/**调用参数
	 * src_ip, Source_App, Computer_name, src_mac, Oper_name,Oper_result, fail_reason
	 * @param args
	 */
	public static void doLoginLog(Object... args){
		Object[] arr = ArrayUtils.addAll(getSystemLogInfo(), args);
		logger.info(String.format(LOGIN_LOG_FMT, arr));
	}
	
	
	/**调用参数
	 * User_name,Source_App,Oper_type,Oper_content_old ,Oper_content_new,Oper_result
	 * @param args
	 */
	public static  void doAuthorityLog(Object... args){
		Object[] arr = ArrayUtils.addAll(getSystemLogInfo(), args);
		logger.info(String.format(ROLE_LOG_FMT, arr));
	}
	
	
	/**调用参数
	 * src_ip,Source_App,Config_name,Oper_type,Oper_content_old,Oper_content_new,Oper_result
	 * @param args
	 */
	public static void doSystemConfLog(Object... args){
		Object[] arr = ArrayUtils.addAll(getSystemLogInfo(), args);
		logger.info(String.format(SYS_CONF_LOG_FMT, arr));
	}
	
	public static void userMangerSuccLog(String userId,String type){
	    doUserMangerLog(userId,getSystemCode(),type,LOG_RESULT_FOR_SUCCESS);
    }
    
    public static void userMangerFailLog(String userId,String type){
        doUserMangerLog(userId,getSystemCode(),type,LOG_RESULT_FOR_FAIL);
    }

	/**
	 * 角色/授权管理成功日志
	 */
	public static void authoritySuccLog(String id, String type, String oldContent, String newContent) {
		doAuthorityLog(id, getSystemCode(), type,  oldContent, newContent, LOG_RESULT_FOR_SUCCESS);
	}

	/**
	 * 角色/授权管理失败日志
	 */
	public static void authorityFailLog(String id, String type, String oldContent, String newContent){
		doAuthorityLog(id, getSystemCode(), type,  oldContent, newContent, LOG_RESULT_FOR_FAIL);
	}
	
	
	/**调用参数
	 * User_name,Source_App,Oper_type,Oper_result
	 * @param args
	 */
	public static void doUserMangerLog(Object... args){
		Object[] arr = ArrayUtils.addAll(getSystemLogInfo(), args);
		logger.info(String.format(USER_LOG_FMT, arr));
	}
	
	private static Object[] getSystemLogInfo(){
		String opTime = DateUtil.date2Str(new Date(),DEFAULT_FORMAT);
		String opUser =  null;
		return new String[]{opTime,opUser,systemCode,IPUtil.getLocalIP()};
	}

	public static String getSystemCode() {
		return systemCode;
	}
	
	
	
}
