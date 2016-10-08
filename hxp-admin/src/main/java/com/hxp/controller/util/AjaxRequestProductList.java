package com.hxp.controller.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/31.
 */
public class AjaxRequestProductList {

     public static  String  init(String ftl, Map<Object,Object> map) throws IOException,TemplateException{
         Configuration freemarkerCfg = new Configuration();
         freemarkerCfg.setDirectoryForTemplateLoading(new File(Local.projectLocal+ "/WEB-INF/ftl"));
         freemarkerCfg.setEncoding(Locale.getDefault(),"UTF-8");

         Template template = freemarkerCfg.getTemplate(ftl);
         template.setEncoding("UTF-8");

         StringWriter  result = new StringWriter();
         template.process(map,result);
         return result.toString();
     }

    public  static  String ajaxRequestProductList(Map map, HttpServletResponse response, String urlPath) throws Exception{
        //获取product信息，productService由spring注入
        //   List<ProductInfo> productInfoList = productService.queryProductByProductId();

        response.setContentType("text/html; charset=UTF-8");

        //得到渲染好的模板内容

        String result = init(urlPath, map);
        response.getWriter().write(result);
        map.clear();
        return null;
    }
}
