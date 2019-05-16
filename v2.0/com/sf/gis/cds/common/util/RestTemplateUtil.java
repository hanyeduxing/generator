package com.sf.gis.cds.common.util;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Date;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class RestTemplateUtil {
	public static GsonBuilder builder = null;
	
	public static RestTemplate getRestTemplate() {
//        StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
//        FastJsonHttpMessageConverter m1 = new FastJsonHttpMessageConverter();
//        RestTemplate restTemplate = new RestTemplateBuilder().additionalMessageConverters(m,m1).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
	
	
	public static Gson getGson() {
		
		if(builder == null) {
			builder =	new GsonBuilder();
			builder.registerTypeHierarchyAdapter(Date.class, new JsonDeserializer<Date>() {
				@Override
				public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
						throws JsonParseException {
					return new Date(json.getAsJsonPrimitive().getAsLong());
				}
			});
		}
		return builder.create();
	}
}
