package com.sf.gis.cds.common.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommonConstanst {

	public static final String LOGIN_USER_EMP_NO = "_sf.dmds.USER_NAME_";
	public static final String LOGIN_USER_EMP_INFO = "_sf.dmds.USER_INFO_";

	public static final String COOKIE_DOMAIN = ".sf-express.com";

	public static final String JWT_SECERT = "6686sf7fc3b34t26m61c088d5sf8245b";

	public static final String JWT_ERRCODE_EXPIRE = "expire";

	public static final String JWT_ERRCODE_FAIL = "fail";

	public static final String GLOBAL_USER_INFO = "user_info";
	
	public static final String LOGIN_USER_MOBILE = "user_info_mobile";
	
	public static final int TAG_SYS_TYPE = 1;
	
	public static final int TAG_COMMON_TYPE = 2;
	
	public static final int TAG_USER_TYPE = 3;
	
	public final static long MAX_FILE_SIZE = 10 * 1024 * 1024;

	// 系统识别参数
	public static final String SYS_MK = "2";

	public static final String SYS_GD = "0";

	public static final String SYS_BD = "1";

	public static final String LOGIN_PAGE = "login";

	public static final Long CODE_EXPIRED_TIME = 600000L;

	public static final Byte SEX_UNKNOWN = 0;

	public static final Byte SEX_MALE = 1;

	public static final Byte SEX_FEMALE = 2;

	/**
	 * 手机号正则
	 */
	public static final String MOBILE_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

	public final static List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            // 图片
            "jpeg",
            "jpg",
            "gif",
            "png",
            "webp",
            "bmp",
            "tif",
            // 文件
            "pdf",
            "txt",

            // 文档
            "doc",
            "docx",
            "xls",
            "xlsx",
            "ppt",
            "pptx",
            "csv",
            "key",

            //声音
            "mp3",

            //压缩包
            "rar",
            "zip",
            "7z"
    );
	
	public final static List<String> PIC_EXTENSIONS = Arrays.asList(
            // 图片
            "jpeg",
            "jpg",
            "gif",
            "png",
            "webp",
            "bmp",
            "tif"
    );
	
	public static final List<String> AUDIO_EXTENSIONS = Collections.singletonList("mp3");

}
