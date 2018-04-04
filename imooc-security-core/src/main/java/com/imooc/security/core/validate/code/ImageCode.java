package com.imooc.security.core.validate.code;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ImageCode implements Serializable {
	private static final long serialVersionUID = 6566401556496286460L;
	private BufferedImage image;
	private String code;
	private LocalDateTime expireTime;//过期的时间点
	
	public ImageCode(BufferedImage image, String code, int expireTime) {
		super();
		this.image = image;
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
	}
	/** 判断是否过期 */
	public boolean isExpried(){
		return LocalDateTime.now().isAfter(expireTime);
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public LocalDateTime getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}
	
	
	
}
