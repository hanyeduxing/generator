package org.mybatis.generator.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.api.FreemarkerTest;
import org.mybatis.generator.api.IntrospectedTable;
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
	 * @param configuration 配置
	 * @param templates	模板集合
	 * @param projectDir 输出文件目录（maven项目目录）
	 * @param table
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public static void generatorByTable(Configuration configuration, List<Template> templates, File projectDir, 
			IntrospectedTable table, boolean override) throws IOException, TemplateException {
		TableConfiguration tableConfiguration = table.getTableConfiguration();
		Map<String, Object> data = new HashMap<>();
		data.put("tableName", tableConfiguration.getTableName());
		data.put("modelName", tableConfiguration.getDomainObjectName());
		data.put("pkCol", table.getPrimaryKeyColumns());
		data.put("columns", table.getBaseColumns());
		data.put("remarks", table.getRemarks());
		
		
		for(Template template : templates) {
			String typeName = template.getSourceName().split("\\.")[0];
			String suffix = "java";
			if("Model".equals(typeName)) {
				typeName = "";
			}else if("Mapping".equals(typeName)) {
				suffix = "xml";
			}
			String fileName = String.format("%s%s%s%s.%s", projectDir.getPath(), File.separator, 
					tableConfiguration.getDomainObjectName(), typeName, suffix);
			if(!projectDir.exists()) {
				projectDir.mkdirs();
			}
			try(Writer out = new FileWriter(fileName, !override)) {
				generatorFile(template, out, data);
			}
		}
	}
	
	public static void generatorByTables(List<IntrospectedTable> tables, File projectDir) throws IOException, TemplateException {
		//2、创建一个Configuration对象
        //注意这个Configuration来自  freemarker.template
        Configuration configuration = new Configuration(Configuration.getVersion());
 
        //3、设置模板文件保存的目录
        configuration.setClassForTemplateLoading(FreemarkerTest.class, "/freemarker");
 
        //4、模板文件的编码格式，一般就是utf-8
        configuration.setDefaultEncoding("utf-8");
        
        List<Template> templates = new ArrayList<>();
        templates.add(configuration.getTemplate("/Model.ftl"));
        templates.add(configuration.getTemplate("/Req.ftl"));
        templates.add(configuration.getTemplate("/Mapper.ftl"));
//        templates.add(configuration.getTemplate("/Mapping.ftl"));
        templates.add(configuration.getTemplate("/Service.ftl"));
        templates.add(configuration.getTemplate("/ServiceImpl.ftl"));
        templates.add(configuration.getTemplate("/Controller.ftl"));
        
		for(IntrospectedTable table : tables) {
			generatorByTable(configuration, templates, projectDir, table, true);
		}
	}

}
