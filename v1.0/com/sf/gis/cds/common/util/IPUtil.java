package com.sf.gis.cds.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPUtil {

    public static final String REGEX_IP = "^\\d+\\.\\d+\\.\\d+\\.\\d+$";
    public static final String LOCAL_IP = "127.0.0.1";


    private static final Logger logger= LoggerFactory.getLogger(IPUtil.class);

    public static boolean isIp(String ip) {
        Pattern pattern = Pattern.compile(REGEX_IP);
        if (ip == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }


    /**
     * ip 2 long
     *
     * @param ip
     * @return
     */
    public static long ip2Long(String ip) {
        if (!isIp(ip)) {
            ip = LOCAL_IP;
        }
        String[] ipAdress = ip.split("\\.");
        if (ipAdress==null || ipAdress.length != 4) {
            return 2130706433;//127.0.0.1
        }
        long ipLong = 16777216L * Integer.parseInt(ipAdress[0]) + 65536 * Integer.parseInt(ipAdress[1]) + 256 * Integer.parseInt(ipAdress[2]) + Integer.parseInt(ipAdress[3]);
        return ipLong;
    }


    /**
     * 获得主机IP
     *
     * @return String
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }

    /**
     * @return String
     */
    public static List<String> getLocalIPList() {
        InetAddress ip = null;
        List<String> list = null;
        try {
            if (isWindowsOS()) {//win
                list = new ArrayList<>();
                ip = InetAddress.getLocalHost();
                list.add(ip.getHostAddress());
            } else {//linux
                boolean bFindIP = false;
                Enumeration<NetworkInterface> netInterfaces = NetworkInterface
                        .getNetworkInterfaces();
                while (netInterfaces.hasMoreElements()) {
                    if (bFindIP) {
                        break;
                    }
                    NetworkInterface ni = netInterfaces.nextElement();
                    // ----------特定情况，可以考虑用ni.getName判断
                    // 遍历所有ip
                    Enumeration<InetAddress> ips = ni.getInetAddresses();
                    while (ips.hasMoreElements()) {
                        ip = ips.nextElement();
                        if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
                                && ip.getHostAddress().indexOf(":") == -1) {
                            if (null == list) {
                                list = new ArrayList<>();
                                list.add(ip.getHostAddress());
                            } else {
                                list.add(ip.getHostAddress());
                            }
                            bFindIP = true;
                            break;
                        }
                    }

                }
            }
        } catch (Exception e) {
            logger.error("getLocalIPList error",e);
        }

        return list;
    }

    public static void main(String[] args) throws Exception {
        // System.out.println(InetUtil.getLocalAddress());
        System.out.println(getLocalIPList());
    }

    /**
     * 获取mac地址
     * @param ip
     * @return
     */
    public static String getMac(String ip) {
        String str = "";
        String macAddress = "";
        InputStreamReader ir = null;
        LineNumberReader in = null;
        try {
            // cmd /c C:\\Windows\\sysnative\\nbtstat.exe -a
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
            ir = new InputStreamReader(p.getInputStream());
            in = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = in.readLine();
                if (str != null) {
                    if (str.indexOf("MAC Address") > 1) {
                        macAddress = str.substring(
                                str.indexOf("MAC Address") + 14, str.length());
                        break;
                    }
                }
            }
        } catch (IOException e) {
        	logger.error("get mac IOException",e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (ir != null) {
                    ir.close();
                }
            } catch (IOException e) {
            	logger.error("get mac final IOException",e);
            }
        }
        return macAddress;
    }

    // 得到客户端计算机名
    public static String getComputerName(String ip) {
        String computerName = "";
        String str = "";
        InputStreamReader ir = null;
        LineNumberReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
            ir = new InputStreamReader(p.getInputStream());
            input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                try {
                    str = input.readLine();
                } catch (IOException e) {
                   logger.error("getComputerName error",e);
                }
                if (str != null && str.indexOf("唯一") > 1) {
                    computerName = str.substring(0, str.indexOf("<")).trim();
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("getComputerName  error2",e);
            // e.printStackTrace();
        }finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (ir != null) {
                    ir.close();
                }
            } catch (IOException e) {
                logger.error("getComputerName finally error",e);
            }
        }
        return computerName;
    }

    /**
     * 得到本机Ip
     *
     * @return 本机Ip
     */
    public static String getLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
        	logger.error("get local ip UnknownHostException",e);
        }
        return "";
    }

    public static Long getLocalIPLong() {
        return ip2Long(getLocalIP());
    }


    public static String long2ip(long ip) {
        long mask = 0xFF;
        long ip1 = ip & mask;// 0~7
        long ip2 = (ip >> 8) & mask;// 8~15
        long ip3 = (ip >> 16) & mask;// 16~23
        long ip4 = (ip >> 24) & mask;// 24~31
        String ips = new StringBuffer().append(ip4).append(".").append(ip3).append(".").append(ip2).append(".").append(ip1).toString();
        return ips;
    }
    
    public static String getRequestIp() {
        String ipAddress = "";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (null == request) {
            return ipAddress;
        }
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            // 这里主要是获取本机的ip,可有可无
            if (ipAddress.equals("127.0.0.1")
                    || ipAddress.endsWith("0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                	logger.error("get request ip UnknownHostException", e);
                    return "";
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
            // = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        // 或者这样也行,对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        // return
        // ipAddress!=null&&!"".equals(ipAddress)?ipAddress.split(",")[0]:null;
        return ipAddress;
    }

    public static String getRequestHostName() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String remoteHost = "";
        if (null != request) {
            remoteHost = request.getRemoteHost();
        }
        return remoteHost;
    }
}
