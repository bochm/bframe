package cn.bx.bframe.common.config;

import java.util.HashMap;
import java.util.Map;

import cn.bx.bframe.common.util.PropertiesLoader;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppConstants {
	@JsonProperty("USER")
	public static final String AUTHENTICATION_KEY = "_USER_SESSION_AUTHENTICATION";
	@JsonProperty("OK")
	public static final String RET_SECCUSS = "seccuss";
	@JsonProperty("FAIL")
	public static final String RET_FAIL = "fail";
	@JsonProperty("WORN")
	public static final String RET_WARNING = "warning";
	@JsonProperty("STATUS")
	public static final String RET_STATUS = "status";
	@JsonProperty("MSG")
	public static final String RET_MESSAGE = "message";
	@JsonProperty("ERROR")
	public static final String RET_ERROR = "error";
	@JsonProperty("DATA")
	public static final String RET_DATA = "data";
	@JsonProperty("EXCEPTION")
	public static final String RET_EXCEPTION = "exception";
	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";
	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	@JsonIgnore
	private static AppConstants _instance = new AppConstants();
	@JsonIgnore
	private static Map<String, String> map = new HashMap<String,String>();
	@JsonIgnore
	private static PropertiesLoader loader = new PropertiesLoader("config/app-default.properties");
	private AppConstants(){}
	public static AppConstants getInstance(){return _instance;}
	/**
	 * 获取配置
	 * @param key app.properties中的键值
	 */
	public String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key,"");
			map.put(key, value);
		}
		return value;
	}
	public String getConfig(String key,String defaultVal) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key,defaultVal);
			map.put(key, value);
		}
		return value;
	}
}
