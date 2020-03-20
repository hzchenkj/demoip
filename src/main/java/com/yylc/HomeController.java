package com.yylc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;



/**
 * Created by hzchenkj on 2019/6/24.
 */

@RestController
public class HomeController {

    @GetMapping("/hello")
    public String sayHello(HttpServletRequest request) {
        return "Hello World!" + getIpAddr(request);
    }

    private String getIpAddr(HttpServletRequest request) {
        String realIp = request.getHeader("X-Real-IP");
        System.out.println("X-Real-IP:" + realIp);

        String forwardedIp = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for:" + forwardedIp);

        String proxyClientIp = request.getHeader("Proxy-Client-IP");
        System.out.println("Proxy-Client-IP:" + proxyClientIp);

        String httpClientIp = request.getHeader("HTTP_CLIENT_IP");
        System.out.println("HTTP_CLIENT_IP:" + httpClientIp);
        String xForwardIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        System.out.println("HTTP_X_FORWARDED_FOR:" + xForwardIp);
        String ip = request.getRemoteAddr();
        System.out.println("getRemoteAddr" + ip);

        System.out.println("ServerIp:" + getServerIp());

        System.out.println("{'firstName': 'Json','testName': 'Json122'}");

        List<String> texts = new ArrayList<>();
        texts.add("{'firstName': 'Json','testName': 'Json122'}");

        Path dest = Paths.get("/var/log/test.txt");
        Charset cs = Charset.forName("US-ASCII");
        try {
            Path p = Files.write(dest, texts, cs, WRITE, CREATE);
            System.out.println("Text was written to " + p.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }


        String serverIp = getIpByEthNum("ens3");

        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        System.out.println("现在时间：" + sdf.format(date));


        return ">>>>>X-Real-IP:" + realIp + " x-forwarded-for:" + forwardedIp + " getRemoteAddr:" + ip + " serverIp: " + serverIp + " time: " + sdf.format(date);
    }


    public String getServerIp() {
        // 获取操作系统类型
        String sysType = System.getProperties().getProperty("os.name");
        System.out.println(sysType);
        String ip = null;
        String hostAddress = null;
        try {
            InetAddress address = InetAddress.getLocalHost(); //获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
            hostAddress = address.getHostAddress(); //192.168.0.121     
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostAddress;
    }




    private String getIpByEthNum(String ethNum) {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (ethNum.equals(netInterface.getName())) {
                    Enumeration addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = (InetAddress) addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "获取服务器IP错误";
    }

}
