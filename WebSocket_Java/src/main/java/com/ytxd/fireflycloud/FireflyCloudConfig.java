package com.ytxd.fireflycloud;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Classname FireflyCloudConfig
 * @Author TY
 * @Date 2024/2/26 10:00
 * @Description TODO
 */
@Data
@Getter
@Configurable
@Component("FireflyCloudConfig")
@ConfigurationProperties(prefix = "ysy")
public class FireflyCloudConfig {
    public String appKey;
    public String appSecret;
}
