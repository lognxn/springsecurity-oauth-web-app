package com.imooc.security.core.support;
/**
 * 基本类型的包装类, 包装基本类型数据
 * @author longxn
 *
 */
public class SimpleResponse {

	public SimpleResponse(Object content) {
		this.content = content;
	}

	private Object content;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	
}
