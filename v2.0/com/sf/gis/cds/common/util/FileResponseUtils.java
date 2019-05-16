package com.sf.gis.cds.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileResponseUtils {
	private final static Logger logger = LoggerFactory.getLogger(FileResponseUtils.class);
	public static void writeFile(byte[] data, HttpServletResponse response){
		OutputStream out = null;
		InputStream in = null;
		try {
			out = response.getOutputStream();
			in = new ByteArrayInputStream(data);
			int len ;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf, 0, 1024)) != -1) {
				out.write(buf, 0, len);
				out.flush();
			}
		} catch (IOException e) {
			logger.error("文件读取失败",e);
		}finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
		}
	}
}
