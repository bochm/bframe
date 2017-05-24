package cn.bx.xweb.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.bx.bframe.common.config.AppConstants;
import cn.bx.bframe.entity.DataMessage;
import cn.bx.bframe.mapper.SqlMapperTemplet;

/**
 * 通用请求处理
 * @author bcm
 *
 */
@Controller
@RequestMapping(value="/app/common")
public class CommonController {
	@Resource(name="sqlMapperTemplet")
	private SqlMapperTemplet<?> sqlMapperTemplet;
	/*
	 * 常量 js获取系统常量,使用json注解为js初始化调用
	 */
	@RequestMapping("/constrants")
	public @ResponseBody DataMessage getConstrants() {
		Map<String,Object> ret = new HashMap<String,Object>();
		Field[] fields = AppConstants.class.getDeclaredFields();
		for(Field f : fields){
			try {
				if(f.getAnnotation(JsonProperty.class) != null){
					ret.put(f.getAnnotation(JsonProperty.class).value(), f.get(f.getName()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return DataMessage.data(ret);
	}
	/*
	 * 根据sqlMapper中的stmID获取数据数据
	 */
	@RequestMapping("/selectArrayByStmID/{stmid}")
	public @ResponseBody DataMessage queryArrayByStmID(@PathVariable("stmid") String stmid,@RequestBody Map<String,Object> parameter) {
		return DataMessage.data(sqlMapperTemplet.selectList(stmid, getParam(parameter)));
	}
	
	/*
	 * 根据sqlMapper中的stmID获取数据数据
	 */
	@RequestMapping("/selectMapByStmID/{stmid}")
	public @ResponseBody DataMessage queryMapByStmID(@PathVariable("stmid") String stmid,@RequestBody Map<String,Object> parameter) {
		return DataMessage.data(sqlMapperTemplet.selectMap(stmid, getParam(parameter)));
	}
	
	/*
	 * 根据sqlMapper中的stmID获取数据数据
	 */
	@RequestMapping("/selectMapListByStmID/{stmid}")
	public @ResponseBody DataMessage queryMapListByStmID(@PathVariable("stmid") String stmid,@RequestBody Map<String,Object> parameter) {
		return DataMessage.data(sqlMapperTemplet.selectMapList(stmid,  getParam(parameter),
				String.valueOf(parameter.get("key"))));
	}
	
	@SuppressWarnings({ "unchecked"})
	private Map<String,String> getParam(Map<String,Object> parameter){
		if(parameter.containsKey("param")) return (Map<String,String>)parameter.get("param");
		else if(parameter.containsKey("para")) return (Map<String,String>)parameter.get("para");
		else if(parameter.containsKey("params")) return (Map<String,String>)parameter.get("params");
		else if(parameter.containsKey("parameter")) return (Map<String,String>)parameter.get("parameter");
		else return null;
	}
}
