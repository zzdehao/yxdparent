package com.tf.web.config;
public class YxdConfig {
	public static final long EXPIRATION_TIME = 3153600000000L;     // 100年
	public static final String TOKEN_SECRET = "!@#$sg3%g23*&a0sg$&@^$&!*%9a!@2#";            // JWT密码
	public static final String TOKEN_PREFIX = "exp";        // Token前缀
	public static final String TOKEN_HEADER_STRING = "Authorization";// 存放Token的Header Key


}
