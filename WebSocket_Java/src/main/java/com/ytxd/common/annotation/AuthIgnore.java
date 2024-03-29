package com.ytxd.common.annotation;

import java.lang.annotation.*;

/**
 * api接口，忽略Token验证
 * @author ytxd
 * @email 2625100545@qq.com
 * @date 2019-03-23 15:44
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore {

}
