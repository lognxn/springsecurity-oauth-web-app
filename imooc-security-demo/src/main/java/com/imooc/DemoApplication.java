package com.imooc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 */

/** @SpringBootApplication:告知程序是spring-boot项目
 * @RestController:将DemoApplication变成是提供rest服务的类
 * @author longxn
 *
 */
@SpringBootApplication
@RestController
public class DemoApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//spring-boot程序启动的标准方式
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@GetMapping("/hello")
	public String hello(){
		return "hello spring security";
	}

}
