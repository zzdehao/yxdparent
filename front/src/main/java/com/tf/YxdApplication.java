package com.tf;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mvn clean package -Dmaven.test.skip=true -P prod
 */
@MapperScan("com.tf.mapper")
@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
@Profile(value = "dev")
public class YxdApplication {
    public static void main(String[] args) {
        SpringApplication.run(YxdApplication.class, args);
        System.out.println("==============started is finsish=============");
    }



}