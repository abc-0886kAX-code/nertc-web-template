package com.ytxd.util;


import com.ytxd.common.DataUtils;
import com.ytxd.service.Subhall.Aquatic_Ecology.CalIndScore;
import org.apache.commons.jexl3.*;
import org.apache.commons.jexl3.internal.Engine;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 强制执行java语句
 */
public class EnforcementUtils {

    private static JexlEngine jexlEngine = new Engine();
    // 初始化反射类中的方法


    /**
     * @param express   表达式
     * @param parameter 参数和值的map
     * @param ignore    是否忽略大小写
     * @return
     */
    public static Object executeString(String express, Map<String, Object> parameter, boolean ignore) {
        if(ignore){
            express = express.toLowerCase(Locale.ROOT);
        }
        JexlExpression expression = jexlEngine.createExpression(express);//将参数中的字符串传进来
        JexlContext jexlContext = new MapContext();
        if (parameter != null) {
            for (String key : parameter.keySet()) {//遍历传过来的参数
                /**
                 * 将传进来的参数替换到表达式中去
                 */
                if (ignore) {
                    jexlContext.set(key.toLowerCase(Locale.ROOT), parameter.get(key));
                } else {
                    jexlContext.set(key, parameter.get(key));
                }

            }
        }
        if (null == expression.evaluate(jexlContext)) {//执行表达式
            return "";//为空就返回空字符串
        }
        return expression.evaluate(jexlContext);//执行表达式，返回结果
    }
    public static void main(String[] args) throws Exception {
//        String express = "(1-AC/AR)*100".toLowerCase(Locale.ROOT);
//        String express = "";
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("Tn",5.2);
//        parameter.put("AR",5.5);
//        parameter.put("AD",5.5);
//        System.out.println(executeString(express,parameter,true));
        String methedname = "lookup(32,AC)";
        String str = "";
        System.out.println(executeString(str,parameter,true));
//        Object obj = CalIndScore.class.newInstance();
//        Class clz = obj.getClass();
//        Map<String, Method> methodMap = Stream.of(clz.getDeclaredMethods()).collect(Collectors.toMap(item -> {
//            return item.getName().toLowerCase(Locale.ROOT);
//        }, v -> v, (v1, v2) -> {
//            return v1;
//        }));
//        Method method = methodMap.get("lookup");
//
////        Method method = clz.getDeclaredMethod("lookup");
//        String methparam = methedname.replaceAll("lookup\\(", "").replaceAll("\\)", "");
//        System.out.println(methedname.contains("look"));
//////        System.out.println(method.getName());
//        System.out.println(method.invoke(obj, methparam.split(",")));
    }
}
