package com.sf.gis.cds.common.constant;

import com.sf.gis.cds.common.util.SpringContextUtil;

/**
 * 服务地址 配置
 * @author 80003818
 *
 */
public class ServiceConfig {

	/**
	 * 资源区服务地址
	 */
	public static String POOL_SERVICE_URL;
	
	
	static {
		ApiConfigBean apiConfigBean = SpringContextUtil.getBean(ApiConfigBean.class);
		ServiceConfig.POOL_SERVICE_URL = apiConfigBean.getPoolServiceUrl();
	}
	
	
	public static String INSERT_URL = "%s/%s/insert";
	public static String BATCH_INSERT_URL = "%s/%s/batchInsert";
	public static String INSERT_SELECTIVE_URL = "%s/%s/insertSelective";
	public static String DELETE_URL = "%s/%s/delete?id=%s";
	public static String UPDATE_URL = "%s/%s/update";
	public static String UPDATE_SELECTIVE_URL = "%s/%s/updateSelective";
	public static String GET_BY_ID_URL = "%s/%s/getById?id=%s";
	public static String LIST_URL = "%s/%s/list";
	public static String GET_BY_IDS_URL = "%s/%s/getByIds?ids=%s";
	
}
