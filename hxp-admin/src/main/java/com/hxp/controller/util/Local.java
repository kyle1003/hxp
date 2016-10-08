package com.hxp.controller.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * Created by Administrator on 2016/8/31.
 */
public class Local {

    /*工程部署目录**/
    public static String projectLocal =null;

    static {
        String local = null;
        try {
            local= URLDecoder.decode(Local.class.getProtectionDomain().getCodeSource().getLocation().getFile(), "utf-8");

            if(System.getProperty("os.name").toLowerCase().contains("windows")){
                int index = local.indexOf("WEB-INF");
                if (index>-1){
                    local =local.substring(1,index);//WEB服务器下运行
                }else{
                    if(local.indexOf("target") > -1){//maven服务器下运行
                        local = local.substring(0,local.lastIndexOf("target")) +"src/main/webapp/";
                    }else {
                        local =local.substring(1,local.lastIndexOf("/") + 1);
                    }
                }

                if(local.startsWith("/")){
                    local =local.substring(1);
                }
                System.out.println("Windows采集程序部署路径 >>>>>>>>>>>>>>>>>>>> \" + local");
            }else{//linux
                int index =local.indexOf("WEB-INF");
                local.substring(0,index);//web服务器下运行
                System.out.println("Linux采集程序部署路径 >>>>>>>>>>>>>>>>>>>> \" + local");
            }

            projectLocal =null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
