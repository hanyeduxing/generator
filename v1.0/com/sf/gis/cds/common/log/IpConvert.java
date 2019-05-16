package com.sf.gis.cds.common.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpConvert extends ClassicConverter {

	private static final Logger logger = LoggerFactory
			.getLogger(ClassicConverter.class);
	
	private static final String  DEFAULTIP="0.0.0.0";
	
	private static InetAddress address;
	
	@Override
	public String convert(ILoggingEvent event) {
		try {
		    if(address == null) {
                address = InetAddress.getLocalHost();
		    }
		} catch (UnknownHostException e) {
			logger.info("ip convert error",e);
		}
		if(null!=address){
			return  address.getHostAddress();
		}
		return DEFAULTIP;
	}
}
