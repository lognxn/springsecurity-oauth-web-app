package com.imooc.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.UserQueryCondition;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.redis.client.RedisClient;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author longxn
 *
 */
@RestController
@RequestMapping("/user")
@Api(value = "共享汽车App", tags = { "" })
public class UserController {
	
	@Autowired
	RedisClient redisClientCluster;
	
	@Autowired
	private RedisConnectionFactory connectionFactory;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	/** 去掉BindingResult errors, 程序才能进入到方法里面执行 */
	@PostMapping
	// public User create(@Valid @RequestBody User user, BindingResult errors){
	public User create(@Valid @RequestBody User user) {
		// if(errors.hasErrors()){
		// errors.getAllErrors().forEach(n ->
		// System.out.println(n.getDefaultMessage()));
		// }
		System.out.println(user.getId());
		System.out.println(user.getPassword());
		System.out.println(user.getUsername());
		System.out.println(user.getBirthday());
		user.setId("1");
		return user;
	}

	@JsonView(User.UserSimpleView.class)
	@GetMapping
	// @GetMapping(value="/user")//等同@RequestMapping(value="/user",method =
	// RequestMethod.GET)
	// @PostMapping
	// @DeleteMapping
	@ApiOperation(value = "获取指定网点可用的且在线的车辆", notes = "<b>返回的参数如下:<b/><br/>" + "modelName : 品牌名称<br/>" + "soc : 电量<br/>"
			+ "minuteCharged : 分钟计费费率<br/>" + "mileageCharged : 小时计费费率<br/>" + "vehicleId : 车辆id<br/>"
			+ "plateNumber : 车牌号<br/>", response = List.class)
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "blogArticleBeen", value = "文档对象", required = true, paramType = "body", dataType = "BlogArticleBeen"),
//			@ApiImplicitParam(name = "path", value = "url上的数据", required = true, paramType = "path", dataType = "Long"),
//			@ApiImplicitParam(name = "query", value = "query类型参数", required = true, paramType = "query", dataType = "String"),
//			@ApiImplicitParam(name = "apiKey", value = "header中的数据", required = true, paramType = "header", dataType = "String") })
	public List<User> query(UserQueryCondition condition,
			@PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable) {
		System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getSort());
		List<User> users = new ArrayList<>();
		users.add(new User("longxian", new Date()));
		users.add(new User());
		users.add(new User());
		return users;
	}

	@GetMapping(value = "/{id:\\d+}") // 等同@RequestMapping(value="/user/{id:\\d+}",method=RequestMethod.GET)
	@JsonView(User.UserDetialView.class)
	public User getInfo(@PathVariable String id) {
		// throw new UserNotExistException(id);
		User user = new User();
		System.out.println("执行controller的getInfo方法");
		user.setUsername("tom");
		return user;
	}

	@PutMapping(value = "/{id:\\d+}")
	public User update(@Valid @RequestBody User user, BindingResult errors) {
		if (errors.hasErrors()) {
			errors.getAllErrors().forEach(n -> System.out.println(n.getDefaultMessage()));
		}
		System.out.println(user.getId());
		System.out.println(user.getPassword());
		System.out.println(user.getUsername());
		System.out.println(user.getBirthday());
		user.setId("1");
		return user;
	}

	@DeleteMapping(value = "/{id:\\d+}")
	public void delete(@PathVariable String id) {
		System.out.println(id);
	}
	
	/** 获取jwt中的额外信息, 并且打印
	 * @return 返回的用户信息, springscurity会去context中查找  */
	@GetMapping("me")
//	public UserDetails getCurrentUser(@AuthenticationPrincipal UserDetails user) {//获取到的是authentication里面的principal对象
	public Authentication getCurrentUser(Authentication user, @RequestHeader String Authorization) throws Exception {
		String token =  StringUtils.substringAfter(Authorization, "bearer ");
		Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
			.parseClaimsJws(token).getBody();
		
		String company = (String)claims.get("company");
		System.out.println("-->" + company);
		
		return user;
	}
	
	@GetMapping("test")
	public void redisTest() {
		//通过自定义的redis工具类
//		redisClientCluster.set("aa","aaa111");
//		System.out.println(redisClientCluster.get("aa"));
		//通过springdata工具类
		connectionFactory.getClusterConnection().hSet("aa123".getBytes(), "bb123".getBytes(), "aabb".getBytes());
		byte[] hGet = connectionFactory.getClusterConnection().hGet("aa123".getBytes(), "bb123".getBytes());
		System.out.println(new String(hGet));
	}
}
