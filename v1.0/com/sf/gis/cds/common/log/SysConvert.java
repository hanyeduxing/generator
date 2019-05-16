package com.sf.gis.cds.common.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class SysConvert extends ClassicConverter {
    @Value("${sys.sysCode}")
    private String sysCode;
    @Value("${sys.sysName}")
    private String sysName;
    
    @Override
    public String convert(ILoggingEvent event) {
        return sysCode + "(" + sysName + ")";
    }
}