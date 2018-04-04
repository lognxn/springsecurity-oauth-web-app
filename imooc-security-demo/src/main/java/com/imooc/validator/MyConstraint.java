package com.imooc.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 自定义注解,注解指定了运行的类,类中写的是判断的逻辑
 * 必须的三个要素, 可以参考{@NotBlank}
 * @author longxn
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyConstraint {
	
	String message();
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
