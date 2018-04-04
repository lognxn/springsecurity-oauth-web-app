package com.imooc.exception;

/**
 * 用于测试对app返回的信息json进行修改: 出异常后添加userId返回给app
 * @author longxn
 *
 */
public class UserNotExistException extends RuntimeException{

	private static final long serialVersionUID = -6112780192479692859L;

	private String id;
	
	public UserNotExistException(String id){
		super("user not exist!");
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
