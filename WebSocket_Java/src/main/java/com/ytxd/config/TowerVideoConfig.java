package com.ytxd.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 高塔视频相关配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "tower")
public class TowerVideoConfig implements InitializingBean {

    public static String CLIENT_ID;

    public static String URL_TOKEN;

    public static String URL_REAL;

    public static String URL_CONTROL;

    public static String URL_WARN;

    private String client_id;

    private String url_token;

    private String url_real;

    private String url_control;

    private String url_warn;

    @Override
    public void afterPropertiesSet() throws Exception {
        CLIENT_ID = client_id;
        URL_TOKEN = url_token;
        URL_REAL = url_real;
        URL_CONTROL = url_control;
        URL_WARN = url_warn;
    }
}
