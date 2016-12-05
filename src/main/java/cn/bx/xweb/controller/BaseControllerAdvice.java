package cn.bx.xweb.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bx.bframe.entity.DataMessage;
import cn.bx.system.utils.UserUtils;

@ControllerAdvice
public class BaseControllerAdvice {
	 @ModelAttribute  
	 public Map<String,String> user() {   
		 return UserUtils.getUser();  
	 }  
	 @ExceptionHandler(Exception.class)  
	 public @ResponseBody DataMessage acceptException(Exception e) {  
		 System.out.println("===========系统异常===========" + e.getClass().getName());  
		 e.printStackTrace();
		 return new DataMessage("系统异常",null,HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}  
}
