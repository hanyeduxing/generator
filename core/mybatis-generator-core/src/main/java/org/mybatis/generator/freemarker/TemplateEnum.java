package org.mybatis.generator.freemarker;

public enum TemplateEnum {
	
	TYPE_MODEL("/Model.ftl", ".java", "", "/src/main/java/", "/model"),
	TYPE_REQ("/Req.ftl", ".java", "Req", "/src/main/java/", "/req"),
	TYPE_MANAGER("/Manager.ftl", ".java", "Manager", "/src/main/java/", "/manager"),
	TYPE_MANAGER_IMPL("/ManagerImpl.ftl", ".java", "ManagerImpl", "/src/main/java/", "/manager/impl"),
	TYPE_SERVICE("/Service.ftl", ".java", "Service", "/src/main/java/", "/service"),
	TYPE_SERVICE_IMPL("/ServiceImpl.ftl", ".java", "ServiceImpl", "/src/main/java/", "/service/impl"),
	TYPE_CONTROLLER("/Controller.ftl", ".java", "Controller", "/src/main/java/", "/web/controller"),
	
	TYPE_POOL_MODEL("/PoolModel.ftl", ".java", "", "/src/main/java/", "/dao/%s/model"),
	TYPE_POOL_REQ("/PoolReq.ftl", ".java", "Req", "/src/main/java/", "/dao/%s/req"),
	TYPE_POOL_MAPPER("/PoolMapper.ftl", ".java", "Mapper", "/src/main/java/", "/dao/%s/mapper"),
	TYPE_POOL_MAPPING("/PoolMapping.ftl", ".xml", "Mapper", "/src/main/resources/", "/mapper/%s"),
	TYPE_POOL_SERVICE("/PoolService.ftl", ".java", "Service", "/src/main/java/", "/service/%s"),
	TYPE_POOL_SERVICE_IMPL("/PoolServiceImpl.ftl", ".java", "ServiceImpl", "/src/main/java/", "/service/%s/impl"),
	TYPE_POOL_CONTROLLER("/PoolController.ftl", ".java", "Controller", "/src/main/java/", "/web/controller/%s");

	private TemplateEnum(String sourceName, String suffix, String classType, String fileDir, String packageSuffix) {
		this.sourceName = sourceName;
		this.suffix = suffix;
		this.classType = classType;
		this.fileDir = fileDir;
		this.packageSuffix = packageSuffix;
	}

	/**
	 * *.ftl
	 */
	private String sourceName;
	
	/**
	 * .java/.xml
	 */
	private String suffix;
	
	/**
	 * Req/Service/Mapper
	 */
	private String classType;
	
	/**
	 * src/main/java   src/main/resource
	 */
	private String fileDir;
	
	/**
	 * model/req/service/impl
	 */
	private String packageSuffix;

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public String getPackageSuffix() {
		return packageSuffix;
	}

	public void setPackageSuffix(String packageSuffix) {
		this.packageSuffix = packageSuffix;
	}
	
}
