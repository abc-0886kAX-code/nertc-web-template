package com.ytxd.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Data
@Configuration
@ConfigurationProperties(prefix = "aesword")
public class AesWord {
    public String loginUrl;
    public Map<String,Object> params;
}
