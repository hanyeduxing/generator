package com.sf.gis.cds.common.log;

public class AuditLogFormat {
	
    public static final String LOG_VERSION_ONE = "1";
	
	public static final String LOG_DELIMTER="\u0000";
	
	public static final String LOG_SEGMENT_END="\u0000\r";
	
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**登录日志的格式如下，顺序不能变
	 * Oper_time  操作时间
	 * Oper_user_name  操作人员的账号名
	 * Source_App   源系统编码
	 * Destination_App 目标系统编码
	 * dst_ip 服务端应用ip或者域名
	 * src_ip(*) 源IP
	 * Computer_name(*) 客户端计算机名称
	 * src_mac(*) 源MAC地址
	 * Oper_name 操作名称 见附录操作编码表
	 * Oper_result 操作结果 1 Success, 0 Fail
	 * fail_reason(*) 失败原因 例如timeout，密码不对等
	 * 
	 * 后面的序号表示传入参数的序号： 前4个固定由系统生成（Oper_time Oper_user_name Destination_App dst_ip），后面的是应用系统传入
	 * 应用系统传入参数顺序：src_ip Source_App Computer_name src_mac Oper_name  Oper_result fail_reason
	 */
	public static final String LOGIN_LOG_FMT = "1"+LOG_DELIMTER+LOG_VERSION_ONE
            +  LOG_DELIMTER +"%1$s"
            +  LOG_DELIMTER +"%2$s"
            +  LOG_DELIMTER +"%6$s"
            +  LOG_DELIMTER +"%3$s"
            +  LOG_DELIMTER +"%4$s"
            +  LOG_DELIMTER +"%5$s"
            +  LOG_DELIMTER +"%7$s"
            +  LOG_DELIMTER +"%8$s"
            +  LOG_DELIMTER +"%9$s"
            +  LOG_DELIMTER +"%10$s"
            +  LOG_DELIMTER +"%11$s"
            +  LOG_SEGMENT_END;

	
	
	/**用户管理日志的格式如下，顺序不能变
	 * Oper_time  操作时间
	 * Oper_user_name  操作人员的账号名
	 * User_name 被操作的账号
	 * Source_App   源系统编码
	 * Destination_App 目标系统编码
	 * dst_ip 服务端应用ip或者域名
	 * Oper_type 操作类型 见附录操作编码表
	 * Oper_result 操作结果 1 Success, 0 Fail
	 * 
	 * 后面的序号表示传入参数的序号： 前4个固定由系统生成（Oper_time Oper_user_name Destination_App dst_ip），后面的是应用系统传入
	 * 应用系统传入参数顺序： User_name Source_App Oper_type Oper_result
	 */
	public static final String USER_LOG_FMT = "2"+LOG_DELIMTER+LOG_VERSION_ONE
			    +  LOG_DELIMTER +"%1$s"
			    +  LOG_DELIMTER +"%2$s"
			    +  LOG_DELIMTER +"%5$s"
			    +  LOG_DELIMTER +"%6$s"
			    +  LOG_DELIMTER +"%3$s"
			    +  LOG_DELIMTER +"%4$s"
			    +  LOG_DELIMTER +"%7$s"
			    +  LOG_DELIMTER +"%8$s"
			    +  LOG_SEGMENT_END;
	
	
	/**角色 /权限管理日志 的格式如下，顺序不能变
	 * Oper_time  操作时间
	 * Oper_user_name  操作人员的账号名
	 * User_name 被操作的账号
	 * Source_App   源系统编码
	 * Destination_App 目标系统编码
	 * dst_ip 服务端应用ip或者域名
	 * Oper_type 操作类型 见附录操作编码表
	 * Oper_content_old 旧权限/角色内容
	 * Oper_content_new  新权限/角色内容
	 * Oper_result 操作结果 1 Success, 0 Fail
	 * 
	 * 后面的序号表示传入参数的序号： 前4个固定由系统生成（Oper_time Oper_user_name Destination_App dst_ip），后面的是应用系统传入
	 * 应用系统传入参数顺序： User_name Source_App Oper_type Oper_content_old  Oper_content_new Oper_result
	 */
	public static final String ROLE_LOG_FMT = "3"+LOG_DELIMTER+LOG_VERSION_ONE
			    +  LOG_DELIMTER +"%1$s"
			    +  LOG_DELIMTER +"%2$s"
			    +  LOG_DELIMTER +"%5$s"
			    +  LOG_DELIMTER +"%6$s"
			    +  LOG_DELIMTER +"%3$s"
			    +  LOG_DELIMTER +"%4$s"
			    +  LOG_DELIMTER +"%7$s"
			    +  LOG_DELIMTER +"%8$s"
			    +  LOG_DELIMTER +"%9$s"
			    +  LOG_DELIMTER +"%10$s"
			    +  LOG_SEGMENT_END;
	
	/**系统配置操作 日志的格式如下，顺序不能变
	 * Oper_time  操作时间
	 * Oper_user_name  操作人员的账号名
	 * src_ip 源IP
	 * dst_ip 服务端应用ip或者域名
	 * Source_App   源系统编码
	 * Destination_App 目标系统编码
	 * Config_name 被操作的配置项的名称
	 * Oper_type 操作类型 见附录操作编码表
	 * Oper_content_old 旧权限/角色内容
	 * Oper_content_new  新权限/角色内容
	 * Oper_result 操作结果 1 Success, 0 Fail
	 * 
	 * 后面的序号表示传入参数的序号： 前4个固定由系统生成（Oper_time Oper_user_name Destination_App dst_ip），后面的是应用系统传入
	 * 应用系统传入参数顺序： src_ip Source_App Config_name Oper_type Oper_content_old  Oper_content_new Oper_result
	 */
	public static final String SYS_CONF_LOG_FMT = "4"+LOG_DELIMTER+LOG_VERSION_ONE
			    +  LOG_DELIMTER +"%1$s"
			    +  LOG_DELIMTER +"%2$s"
			    +  LOG_DELIMTER +"%5$s"
			    +  LOG_DELIMTER +"%4$s"
			    +  LOG_DELIMTER +"%6$s"
			    +  LOG_DELIMTER +"%3$s"
			    +  LOG_DELIMTER +"%7$s"
			    +  LOG_DELIMTER +"%8$s"
			    +  LOG_DELIMTER +"%9$s"
			    +  LOG_DELIMTER +"%10$s"
			    +  LOG_DELIMTER +"%11$s"
			    +  LOG_SEGMENT_END;
}
