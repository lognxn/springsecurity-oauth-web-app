package com.imooc.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.imooc.exception.UserNotExistException;

/**
 * controller异常处理类, controller抛出的异常会在这个类匹配进行处理, 需要类上加上@ControllerAdvice
 * @author longxn
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {
	/**
	 * controller抛出的UserNotExistException异常都会在这个方法进行处理
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(UserNotExistException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleUserNotExistException(UserNotExistException ex){
		Map<String, Object> result = new HashMap<>();
		result.put("id", ex.getId());
		result.put("message", ex.getMessage());	
		return result;
	}
}
