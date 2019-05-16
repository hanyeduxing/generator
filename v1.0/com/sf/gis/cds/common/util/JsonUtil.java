package com.sf.gis.cds.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class JsonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	/**
	 * 单态实例
	 */
	private static JsonUtil instance;
	
	private static final ObjectMapper objectMapper;

	/** 格式化时间的string */
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	static {
		objectMapper = new ObjectMapper();
		// 去掉默认的时间戳格式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// 设置为中国北京时区
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		// 空值不序列化
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		// 反序列化时，属性不存在的兼容处理
		objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		// 序列化时，日期的统一格式
		objectMapper.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));

		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 单引号处理
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}
	
	
	/**
	 * 获得单态实例
	 * 
	 * @return 单态实例
	 */
	public static JsonUtil getInstance() {
		if (instance == null) {
			instance = new JsonUtil();
		}
		return instance;
	}
	
	private JsonUtil(){
		
	}



	
	/**
	 * json 转换成 Object
	 */
	public static <T> T json2Object(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			logger.warn("", e);
			throw new RuntimeException("解析json错误");
		}
	}

	/**
	 * obj 转换成json
	 */
	public static <T> String object2Json(T entity) {
		try {
			return objectMapper.writeValueAsString(entity);
		} catch (IOException e) {
			logger.warn("", e);
			throw new RuntimeException("转换json错误");
		}
	}

	/**
	 * obj对象转换成树型JSON
	 */
	public static JsonNode object2TreeJson(Object obj) {
		try {
			return objectMapper.valueToTree(obj);
		} catch (Exception e) {
			logger.warn("", e);
			throw new RuntimeException("转换json错误");
		}
	}

	/**
	 * 解码json串成对象
	 */
	public static <T> T decode(String json, Class<?> valueType) {
		try {
			return (T) objectMapper.readValue(json, valueType);
		} catch (Exception e) {
			logger.warn("", e);
			throw new RuntimeException("解码json串成对象错误");
		}
	}

	/**
	 * 解码对象成url参数
	 */
	public static String bean2UrlParams(Object model) {
		try {
			StringBuffer sb = new StringBuffer();
			Class _class = model.getClass();
			while (_class != null) {
				Field[] field = _class.getDeclaredFields();
				for (int j = 0; j < field.length; j++) {
					String name = field[j].getName();
					String getName = name.substring(0, 1).toUpperCase() + name.substring(1);
					Method m = model.getClass().getMethod("get" + getName);
					if (null != m.invoke(model)) {
						sb.append(name + "=" + m.invoke(model));
						sb.append("&");
					}
				}
                _class = _class.getSuperclass();
			}
			String s = sb.toString();
			if (s.endsWith("&")) {
				s = StringUtils.substringBeforeLast(s, "&");
			}
			return s;

		} catch (Exception e) {
			logger.warn("", e);
			throw new RuntimeException("解码对象成url参数错误");
		}
	}

	// test
	public static void main(String[] args) {
		// String url = "http://...";
		// List<Map<String,Object>> list = getListByUrl(url);
		// System.out.println(list);
//		String str = "{\"ACTION_NAME\":\"COMORDER_TRANS\",\"CITY_CODE\":\"420100\",\"ACTION_INFO\":{\"TRANS_TYPE\":\"04\",\"PHONE\":\"18611159401\",\"ORDER_ID\":\"12380147\",\"USER_ID\":null},\"ACTION_INVOKER\":{\"OSVER\":\"2.3.3\",\"PHONE\":\"\",\"IMSI\":\"89860047175006838074\",\"VER\":\"18\",\"OSDESCRIPT\":\"HTC Vision\",\"IMEI\":\"352212045773335\",\"OSNAME\":\"Android\"}}";
//		String str = "{ \"people\": [  { \"firstName\": \"Brett\", \"lastName\":\"McLaughlin\", \"email\": \"brett@newInstance.com\" },  { \"firstName\": \"Jason\", \"lastName\":\"Hunter\", \"email\": \"jason@servlets.com\" },  { \"firstName\": \"Elliotte\", \"lastN1ame\":\"Harold\", \"email\": \"elharo@macfaq.com\" }  ]}";
//		String str = "{\"ACTION_NAME\":\"FIND_ORDER\",\"CITY_CODE\":\"420100\",\"ACTION_INFO\":{\"CSN\":\"CB62EF2CDAF7BF920100\",\"ORDER_ID\":\"12382096\"},\"ACTION_INVOKER\":{\"PHONE\":\"\",\"IMEI\":\"863020015565892\",\"OSNAME\":\"Android\",\"OSVER\":\"4.0.4\",\"IMSI\":\"89860065171807211732\",\"VER\":\"18\",\"CSN\":\"CB62EF2CDAF7BF920100\",\"OSDESCRIPT\":\"MI-ONE C1\"}}";
//		String str = "{\"ACTION_NAME\":\"FIND_ORDER\",\"CITY_CODE\":\"420100\",\"ACTION_INFO\":{\"ORDER_ID\":\"12382102\"},\"ACTION_INVOKER\":{\"OSVER\":\"4.0.4\",\"PHONE\":\"\",\"IMSI\":\"89860065171807211732\",\"VER\":\"18\",\"OSDESCRIPT\":\"MI-ONE C1\",\"IMEI\":\"863020015565892\",\"OSNAME\":\"Android\"}}";
//		Map map = JsonUtil.getInstance().parseJSON2Map(str);
//		System.out.println(map);
	}
	
	/**驼峰转下划线*/
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    public static String humpToLine(String str){
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
