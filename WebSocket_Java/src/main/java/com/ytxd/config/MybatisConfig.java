package com.ytxd.config;

import java.util.Map;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;


@Configuration("MybatisConfig")
@EnableTransactionManagement
@MapperScan(value = "com.ytxd.dao.*")
public class MybatisConfig {
//	/** * mybatis resultType为map时下划线键值转小写驼峰形式插 */
	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return configuration -> configuration.setObjectWrapperFactory(new MapWrapperFactory());
	}
	
//	@Bean
//	public ConfigurationCustomizer mybatisConfigurationCustomizer(){
//		return new ConfigurationCustomizer() {
//			@Override
//			public void customize(org.apache.ibatis.session.Configuration configuration) {
//				configuration.setObjectWrapperFactory(new MapWrapperFactory());
//			}
//		};
//	}

	public static class MapWrapperFactory implements ObjectWrapperFactory {
		@Override
		public boolean hasWrapperFor(Object object) {
			return object != null && object instanceof Map;
		}

		@Override
		public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
			return new MyMapWrapper(metaObject, (Map) object);
		}
	}

	public static class MyMapWrapper extends MapWrapper {
		MyMapWrapper(MetaObject metaObject, Map<String, Object> map) {
			super(metaObject, map);
		}

		@Override
		public String findProperty(String name, boolean useCamelCaseMapping) {
			if (useCamelCaseMapping) {
				return name==null?"":name.toLowerCase();
			}
			return name;
//			if (useCamelCaseMapping && ((name.charAt(0) >= 'A' && name.charAt(0) <= 'Z') || name.contains("_"))) {
//				return underlineToCamelhump(name);
//			}
//			return name;
		}

		/** * 将下划线风格替换为驼峰风格 * * @param inputString * @return */
		private String underlineToCamelhump(String inputString) {
			StringBuilder sb = new StringBuilder();
			boolean nextUpperCase = false;
			for (int i = 0; i < inputString.length(); i++) {
				char c = inputString.charAt(i);
				if (c == '_') {
					if (sb.length() > 0) {
						nextUpperCase = true;
					}
				} else {
					if (nextUpperCase) {
						sb.append(Character.toUpperCase(c));
						nextUpperCase = false;
					} else {
						sb.append(Character.toLowerCase(c));
					}
				}
			}
			return sb.toString();
		}
	}
}
