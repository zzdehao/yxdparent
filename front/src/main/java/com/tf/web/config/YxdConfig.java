package com.tf.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "yxd")
public class YxdConfig {
	public static final long EXPIRATION_TIME = 3153600000000L;     // 100年
	public static final String TOKEN_SECRET = "!@#$sg3%g23*&a0sg$&@^$&!*%9a!@2#";            // JWT密码
	public static final String TOKEN_PREFIX = "exp";        // Token前缀
	public static final String TOKEN_HEADER_STRING = "Authorization";// 存放Token的Header Key
	//上传路径
	private String uploadPath;

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

}
