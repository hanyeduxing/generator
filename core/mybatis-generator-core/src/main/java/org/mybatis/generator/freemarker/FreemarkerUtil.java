package org.mybatis.generator.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.mybatis.generator.api.FreemarkerTest;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.TableConfiguration;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {
	
	
	/**
	 * 模板生成文件
	 * @param template
	 * @param out
	 * @param data
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static void generatorFile(Template template, Writer out, Map<String, Object> data) throws TemplateException, IOException {
		//5、加载一个模板文件，创建一个模板对象。
//        Template template = configuration.getTemplate("/Model.ftl");
 
        //6、创建一个数据集。可以是pojo也可以是map。推荐使用map
//        Map<String, Object> data = new HashMap<>();
 
        //7、创建一个Writer对象，指定输出文件的路径及文件名。
//        Writer out = new FileWriter(new File("D:/test.html"));
        
        //8、生成静态页面
        //@Param1:数据   @Param2:输出流  数据data+模板test.ftl封装到test.html
        template.process(data, out);
	}
	
	/**
	 * 根据表生成 文件
	 * @param templates	模板集合
	 * @param files
	 * @param override
	 * @param data
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public static void generatorByTable(List<Template> templates, List<File> files, 
			boolean override, Map<String, Object> data) throws IOException, TemplateException {
		for(int i = 0; i < templates.size(); i ++) {
			Template template = templates.get(i);
			File file = files.get(i);
			
			boolean append = false;
			if(!override) {
				if(file.exists()) {
					if(TemplateEnum.TYPE_SERVICE.getSourceName().contains(template.getSourceName()) ||
							TemplateEnum.TYPE_SERVICE_IMPL.getSourceName().contains(template.getSourceName()) ||
							TemplateEnum.TYPE_CONTROLLER.getSourceName().contains(template.getSourceName()) ||
							TemplateEnum.TYPE_POOL_SERVICE.getSourceName().contains(template.getSourceName()) ||
							TemplateEnum.TYPE_POOL_SERVICE_IMPL.getSourceName().contains(template.getSourceName()) ||
							TemplateEnum.TYPE_POOL_CONTROLLER.getSourceName().contains(template.getSourceName()) ||
							TemplateEnum.TYPE_POOL_MAPPER.getSourceName().contains(template.getSourceName())) {
						continue;
					}else if(TemplateEnum.TYPE_MODEL.getSourceName().contains(template.getSourceName()) ||
							TemplateEnum.TYPE_REQ.getSourceName().contains(template.getSourceName()) ||
							TemplateEnum.TYPE_POOL_MODEL.getSourceName().contains(template.getSourceName()) ||
							TemplateEnum.TYPE_POOL_REQ.getSourceName().contains(template.getSourceName()) ||
							TemplateEnum.TYPE_POOL_MAPPER.getSourceName().contains(template.getSourceName())) {
						append = false;
					}else {
						append = true;
					}
				}
			}
			
			try(Writer out = new FileWriter(file, append)) {
				generatorFile(template, out, data);
			}
		}
		
		
	}
	
	/**
	 * 
	 * @param context
	 * @param projectDir
	 * @param overwrite
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void generatorByContext(Context context, File projectDir, boolean overwrite) throws IOException, TemplateException {
		List<IntrospectedTable> tables = context.getIntrospectedTables();
		//2、创建一个Configuration对象
        //注意这个Configuration来自  freemarker.template
        Configuration configuration = new Configuration(Configuration.getVersion());
 
        //3、设置模板文件保存的目录
        configuration.setClassForTemplateLoading(FreemarkerTest.class, "/freemarker");
 
        //4、模板文件的编码格式，一般就是utf-8
        configuration.setDefaultEncoding(context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING));
        
        //生成资源池代码
        generatorPool(context, projectDir, overwrite, configuration, tables);
        //生成项目代码
//        generatorProject(context, projectDir, overwrite, configuration, tables);
	}
	
	
	public static void generatorProject(Context context, File projectDir, boolean overwrite, 
			Configuration configuration, List<IntrospectedTable> tables) throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put(PropertyRegistry.CONTEXT_BASE_PACKAGE, context.getProperty(PropertyRegistry.CONTEXT_BASE_PACKAGE));
        data.put(PropertyRegistry.CONTEXT_MODEL_DAO, context.getProperty(PropertyRegistry.CONTEXT_MODEL_DAO));
        data.put(PropertyRegistry.CONTEXT_MODEL_SERVICE, context.getProperty(PropertyRegistry.CONTEXT_MODEL_SERVICE));
        data.put(PropertyRegistry.CONTEXT_MODEL_WEB, context.getProperty(PropertyRegistry.CONTEXT_MODEL_WEB));
        String projectDirStr = context.getProperty(PropertyRegistry.CONTEXT_PROJECT_DIR);
        if(projectDirStr != null && projectDirStr.length() > 0) {
        	projectDir = new File(projectDirStr);
        }
        
        List<Template> templates = new ArrayList<>();
        templates.add(configuration.getTemplate(TemplateEnum.TYPE_MODEL.getSourceName()));
        templates.add(configuration.getTemplate(TemplateEnum.TYPE_REQ.getSourceName()));
        templates.add(configuration.getTemplate(TemplateEnum.TYPE_MANAGER.getSourceName()));
        templates.add(configuration.getTemplate(TemplateEnum.TYPE_MANAGER_IMPL.getSourceName()));
        templates.add(configuration.getTemplate(TemplateEnum.TYPE_SERVICE.getSourceName()));
        templates.add(configuration.getTemplate(TemplateEnum.TYPE_SERVICE_IMPL.getSourceName()));
        templates.add(configuration.getTemplate(TemplateEnum.TYPE_CONTROLLER.getSourceName()));
        
		for(IntrospectedTable table : tables) {
			TableConfiguration tableConfiguration = table.getTableConfiguration();
			List<IntrospectedColumn> reqColumns = new ArrayList<>();
			List<File> files = getProjectFilesByTable(context, table, projectDir, reqColumns);
			data.put("tableName", tableConfiguration.getTableName());
			String modelName = tableConfiguration.getDomainObjectName();
			data.put("modelName", modelName);
			String lowerModelName = modelName.substring(0, 1).toLowerCase() + modelName.substring(1);
			data.put("lowerModelName", lowerModelName);
			data.put("pkCol", table.getPrimaryKeyColumns());
			data.put("columns", table.getBaseColumns());
			data.put("remarks", table.getRemarks());
			data.put("reqColumns", reqColumns);
			Set<String> importList = new HashSet<>();
			data.put("importList", importList);
			Set<String> reqImportList = new HashSet<>();
			data.put("reqImportList", reqImportList);
			for(IntrospectedColumn column : table.getBaseColumns()) {
				String packageName = column.getFullyQualifiedJavaType().getPackageName();
				if(!"java.lang".equals(packageName)) {
					importList.add(column.getFullyQualifiedJavaType().getFullyQualifiedName());
				}
			}
			for(IntrospectedColumn column : reqColumns) {
				String packageName = column.getFullyQualifiedJavaType().getPackageName();
				if(!"java.lang".equals(packageName)) {
					reqImportList.add(column.getFullyQualifiedJavaType().getFullyQualifiedName());
				}
			}
			
			generatorByTable(templates, files, overwrite, data);
		}
	}
	public static void generatorPool(Context context, File projectDir, boolean overwrite, 
			Configuration configuration, List<IntrospectedTable> tables) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<>();
		data.put(PropertyRegistry.CONTEXT_POOL_PACKAGE, context.getProperty(PropertyRegistry.CONTEXT_POOL_PACKAGE));
		data.put(PropertyRegistry.CONTEXT_BASE_PACKAGE, context.getProperty(PropertyRegistry.CONTEXT_BASE_POOL_PACKAGE));
		data.put(PropertyRegistry.CONTEXT_MODEL_DAO, context.getProperty(PropertyRegistry.CONTEXT_MODEL_POOL_DAO));
		data.put(PropertyRegistry.CONTEXT_MODEL_SERVICE, context.getProperty(PropertyRegistry.CONTEXT_MODEL_POOL_SERVICE));
		data.put(PropertyRegistry.CONTEXT_MODEL_WEB, context.getProperty(PropertyRegistry.CONTEXT_MODEL_POOL_WEB));
		String projectDirStr = context.getProperty(PropertyRegistry.CONTEXT_PROJECT_POOL_DIR);
		if(projectDirStr != null && projectDirStr.length() > 0) {
			projectDir = new File(projectDirStr);
		}
		
		List<Template> templates = new ArrayList<>();
		templates.add(configuration.getTemplate(TemplateEnum.TYPE_POOL_MODEL.getSourceName()));
		templates.add(configuration.getTemplate(TemplateEnum.TYPE_POOL_REQ.getSourceName()));
		templates.add(configuration.getTemplate(TemplateEnum.TYPE_POOL_MAPPER.getSourceName()));
        templates.add(configuration.getTemplate(TemplateEnum.TYPE_POOL_MAPPING.getSourceName()));
		templates.add(configuration.getTemplate(TemplateEnum.TYPE_POOL_SERVICE.getSourceName()));
		templates.add(configuration.getTemplate(TemplateEnum.TYPE_POOL_SERVICE_IMPL.getSourceName()));
		templates.add(configuration.getTemplate(TemplateEnum.TYPE_POOL_CONTROLLER.getSourceName()));
		
		for(IntrospectedTable table : tables) {
			TableConfiguration tableConfiguration = table.getTableConfiguration();
			List<IntrospectedColumn> reqColumns = new ArrayList<>();
			List<File> files = getPoolFilesByTable(context, table, projectDir, reqColumns);
			data.put("tableName", tableConfiguration.getTableName());
			String modelName = tableConfiguration.getDomainObjectName();
			data.put("modelName", modelName);
			String lowerModelName = modelName.substring(0, 1).toLowerCase() + modelName.substring(1);
			data.put("lowerModelName", lowerModelName);
			data.put("pkCol", table.getPrimaryKeyColumns());
			data.put("columns", table.getBaseColumns());
			data.put("remarks", table.getRemarks());
			data.put("reqColumns", reqColumns);
			Set<String> importList = new HashSet<>();
			data.put("importList", importList);
			Set<String> reqImportList = new HashSet<>();
			data.put("reqImportList", reqImportList);
			for(IntrospectedColumn column : table.getBaseColumns()) {
				String packageName = column.getFullyQualifiedJavaType().getPackageName();
				if(!"java.lang".equals(packageName)) {
					importList.add(column.getFullyQualifiedJavaType().getFullyQualifiedName());
				}
			}
			for(IntrospectedColumn column : reqColumns) {
				String packageName = column.getFullyQualifiedJavaType().getPackageName();
				if(!"java.lang".equals(packageName)) {
					reqImportList.add(column.getFullyQualifiedJavaType().getFullyQualifiedName());
				}
			}
			
			generatorByTable(templates, files, overwrite, data);
		}
	}

	
	/**
	 * 
	 * @param context
	 * @param table
	 * @param projectDir
	 * @param reqColumns
	 * @return
	 */
	private static List<File> getProjectFilesByTable(Context context,IntrospectedTable table, File projectDir,
			List<IntrospectedColumn> reqColumns) {
		List<File> files = new ArrayList<>();
		String dirs = context.getProperty(PropertyRegistry.CONTEXT_BASE_PACKAGE);
		dirs = File.separator + dirs.replaceAll("\\.", File.separator.equals("\\") ? "\\\\" : File.separator);
		String modelDao = File.separator + context.getProperty(PropertyRegistry.CONTEXT_MODEL_DAO);
		String modelService = File.separator + context.getProperty(PropertyRegistry.CONTEXT_MODEL_SERVICE);
		String modelWeb = File.separator + context.getProperty(PropertyRegistry.CONTEXT_MODEL_WEB);
		
		String objName = table.getTableConfiguration().getDomainObjectName();
		for(TemplateEnum template : TemplateEnum.values()) {
			String dirName = null;
			String fileName = "";
			switch (template) {
				case TYPE_MODEL:
					dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelDao, template.getFileDir(), dirs, template.getPackageSuffix());
					fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
					break;
	
				case TYPE_REQ:
					dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelDao, template.getFileDir(), dirs, template.getPackageSuffix());
					fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
					break;
					
				case TYPE_MANAGER:
					dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelDao, template.getFileDir(), dirs, template.getPackageSuffix());
					fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
					break;
					
				case TYPE_MANAGER_IMPL:
					dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelDao, template.getFileDir(), dirs, template.getPackageSuffix());
					fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
					break;
					
				case TYPE_SERVICE:
					dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelService, template.getFileDir(), dirs, template.getPackageSuffix());
					fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
					break;
					
				case TYPE_SERVICE_IMPL:
					dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelService, template.getFileDir(), dirs, template.getPackageSuffix());
					fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
					break;
					
				case TYPE_CONTROLLER:
					dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelWeb, template.getFileDir(), dirs, template.getPackageSuffix());
					fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
					break;
					
				default:
					break;
				}
			if(dirName == null) {
				continue;
			}
			File dir = new File(dirName);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			fileName = dirName + File.separator + fileName;
			File file = new File(fileName);
			files.add(file);
			if(file.exists() && TemplateEnum.TYPE_REQ.equals(template)) {
				for(IntrospectedColumn col : table.getBaseColumns()) {
					try {
						String fileContent = FileUtils.readFileToString(file, "utf-8");
						if(fileContent.contains(String.format(" %s;", col.getJavaProperty()))) {
							reqColumns.add(col);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return files;
	}
	/**
	 * 
	 * @param context
	 * @param table
	 * @param projectDir
	 * @param reqColumns
	 * @return
	 */
	private static List<File> getPoolFilesByTable(Context context,IntrospectedTable table, File projectDir,
			List<IntrospectedColumn> reqColumns) {
		List<File> files = new ArrayList<>();
		String dirs = context.getProperty(PropertyRegistry.CONTEXT_BASE_POOL_PACKAGE);
		dirs = File.separator + dirs.replaceAll("\\.", File.separator.equals("\\") ? "\\\\" : File.separator);
		String packages = File.separator + context.getProperty(PropertyRegistry.CONTEXT_POOL_PACKAGE);
		String modelDao = File.separator + context.getProperty(PropertyRegistry.CONTEXT_MODEL_POOL_DAO);
		String modelService = File.separator + context.getProperty(PropertyRegistry.CONTEXT_MODEL_POOL_SERVICE);
		String modelWeb = File.separator + context.getProperty(PropertyRegistry.CONTEXT_MODEL_POOL_WEB);
		
		String objName = table.getTableConfiguration().getDomainObjectName();
		for(TemplateEnum template : TemplateEnum.values()) {
			String dirName = null;
			String fileName = "";
			switch (template) {
			case TYPE_POOL_MODEL:
				dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelDao, template.getFileDir(), dirs, String.format(template.getPackageSuffix(), packages));
				fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
				break;
				
			case TYPE_POOL_REQ:
				dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelDao, template.getFileDir(), dirs, String.format(template.getPackageSuffix(), packages));
				fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
				break;
				
			case TYPE_POOL_MAPPER:
				dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelDao, template.getFileDir(), dirs, String.format(template.getPackageSuffix(), packages));
				fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
				break;
				
			case TYPE_POOL_MAPPING:
				dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelDao, template.getFileDir(), "", String.format(template.getPackageSuffix(), packages));
				fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
				break;
				
			case TYPE_POOL_SERVICE:
				dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelService, template.getFileDir(), dirs, String.format(template.getPackageSuffix(), packages));
				fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
				break;
				
			case TYPE_POOL_SERVICE_IMPL:
				dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelService, template.getFileDir(), dirs, String.format(template.getPackageSuffix(), packages));
				fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
				break;
				
			case TYPE_POOL_CONTROLLER:
				dirName = String.format("%s%s%s%s%s", projectDir.getPath(), modelWeb, template.getFileDir(), dirs, String.format(template.getPackageSuffix(), packages));
				fileName = String.format("%s%s%s", objName, template.getClassType(), template.getSuffix());
				break;
				
			default:
				break;
			}
			if(dirName == null) {
				continue;
			}
			File dir = new File(dirName);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			fileName = dirName + File.separator + fileName;
			File file = new File(fileName);
			files.add(file);
			if(file.exists() && TemplateEnum.TYPE_POOL_REQ.equals(template)) {
				for(IntrospectedColumn col : table.getBaseColumns()) {
					try {
						String fileContent = FileUtils.readFileToString(file, "utf-8");
						if(fileContent.contains(String.format(" %s;", col.getJavaProperty()))) {
							reqColumns.add(col);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return files;
	}

}
