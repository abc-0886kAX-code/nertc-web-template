package com.ytxd.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration("Swagger3Config")
public class Swagger3Config {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo())
				.ignoredParameterTypes(Map.class, HashMap.class, List.class, ArrayList.class)// map接受参数时，隐藏map
				.select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any()).build().securityContexts(Arrays.asList(securityContexts()))
				.securitySchemes(unifiedAuth());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("四川绵阳芙蓉溪项目接口文档")
				.description("更多请咨询服务开发者：北京银通先达信息技术有限公司 电话：010-62319230")
				.contact(new Contact("北京银通先达信息技术有限公司", "http://www.ytxd.com.cn", "")).version("1.0").build();
	}

	private static List<SecurityScheme> unifiedAuth() {
		List<SecurityScheme> arrayList = new ArrayList<SecurityScheme>();
		arrayList.add(new ApiKey("token", "token", "header"));
		return arrayList;
	}

	private SecurityContext securityContexts() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "描述信息");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("token", authorizationScopes));
	}
}