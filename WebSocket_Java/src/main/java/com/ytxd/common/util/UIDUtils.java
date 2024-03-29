package com.ytxd.common.util;

import cn.hutool.core.lang.UUID;

import java.util.Locale;

/**
 *
 * @Desription TODO 系统主键工具类
 * @return
 * @date 2023/8/17 13:46
 * @Auther TY
 */
public class UIDUtils {
    /**
     * 获取没有-的UID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().toUpperCase(Locale.ROOT).replaceAll("-","");
    }
}
