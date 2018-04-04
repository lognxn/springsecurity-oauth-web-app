package com.imooc.dto;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.validator.MyConstraint;

import io.swagger.annotations.ApiModelProperty;

/**
 * 输入输出的实体
 * @author longxn
 *
 */
public class User {
	
	public User() {
		super();
	}
	public User(String username, Date birthday) {
		super();
		this.username = username;
		this.birthday = birthday;
	}
	/** @JsonView用于显示用户信息的接口, 该接口不显示用户的password */
	public interface UserSimpleView{};
	/** @JsonView用于显示用户信息的接口, 该接口显示用户的password */
	public interface UserDetialView extends UserSimpleView{};
	@ApiModelProperty(value="用户id")
	private String id;
	
	@MyConstraint(message = "这是一个测试")
	private String username;
	
	@NotBlank(message = "密码不能为空")//不为空
	private String password;
	
	@Past(message = "生日必须是过去的时间")//必须是过去的时间
	@ApiModelProperty(value="生日",dataType = "java.util.Date")
	private Date birthday;
	
	@JsonView(UserSimpleView.class)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JsonView(UserSimpleView.class)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@JsonView(UserSimpleView.class)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonView(UserDetialView.class)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
