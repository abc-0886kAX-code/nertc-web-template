package com.ytxd;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.ytxd.properties.SystemProperties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

@Slf4j	//lombox插件的注解： 为类提供一个 属性名为log 的 log4j 日志对像
@EnableOpenApi	//Swagger2插件注解：接口的文档在线自动生成。
//@MapperScan("com.ytxd.dao.**")
@SpringBootApplication	//spring启动注解
@ServletComponentScan
@EnableConfigurationProperties(SystemProperties.class)
@EnableAsync
@EnableScheduling
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("\r"
				+ "=====================================================\r"
				+ "\r"
				+ "               北京银通先达科研管理系统启动成功                                       \r"
				+ "\r"
				+ "=====================================================\r");
	}
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);//Application类
    }

	/*@Bean
	public TomcatServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
			@Override
			public void customize(Connector connector) {
				connector.setProperty("relaxedPathChars", "\"<>[\\]^`{|}");
				connector.setProperty("relaxedQueryChars", "\"<>[\\]^`{|}");
			}
		});
		return factory;
	}*/
}
