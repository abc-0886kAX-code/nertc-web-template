package com.ytxd.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component("AstrictConfig")
@ConfigurationProperties(prefix = "astrict")
public class AstrictConfig {
    public Integer astrictman;
    public Integer timeout;
}
