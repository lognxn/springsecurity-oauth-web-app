package com.imooc.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.service.HelloService;

/**
 * 自定义的constraint, 改类实现了ConstraintValidator, 会自动被spring创建bean!!!, 且可以通过@Autowired注入任何的spring创建的bean
 * @author longxn
 *
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

	@Autowired
	HelloService helloService;
	
	@Override
	public void initialize(MyConstraint arg0) {
		System.out.println("initialize");
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext arg1) {
		helloService.greeting("tom");
		System.out.println(value);
		return false;
	}
	
}
