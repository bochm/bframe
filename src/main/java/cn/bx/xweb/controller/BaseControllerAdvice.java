package cn.bx.xweb.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cn.bx.bframe.entity.DataMessage;
import cn.bx.system.entity.User;
@EnableWebMvc
@ControllerAdvice
public class BaseControllerAdvice {
	 @ModelAttribute  
	 public User newUser() {  
		 System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");  
		 return new User();  
	 }  
	 @ExceptionHandler(Exception.class)  
	 public @ResponseBody DataMessage processUnauthenticatedException(Exception e) {  
		 System.out.println("===========" + e.getClass().getName());  
		 return new DataMessage("授权异常",null,HttpServletResponse.SC_UNAUTHORIZED); //返回一个逻辑视图名  
	}  
}
