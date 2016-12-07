package cn.bx.system.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import cn.bx.bframe.common.spring.SpringContextHolder;

public class UserUtils {
	public static final String FIELD_TOKEN = "rsToken";
    public static final String FIELD_LOGINNAME = "loginname";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_ID = "id";
    public static final String FIELD_STATUS = "status";
    
	public static final String USER_CACHE = "sys-userCache";
	public static final String PWD_RETRY_CACHE = "sys-passwordRetryCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln_";

	public static final String ROLE_CACHE = "sys-roleCache";
	public static final String PERMISSION_CACHE = "sys-permissionCache";
	private static CacheManager cacheManager = ((EhCacheManager)SpringContextHolder.getBean("shiroCacheManager"));
	
	//------user get set方法-----不使用User实体类改为Map实现
	public static String getPassword(Map<String,String> user) {
		if(user == null) return null;
		return user.get(FIELD_PASSWORD);
	}
	public static String getLoginName(Map<String,String> user) {
		if(user == null) return null;
		return user.get(FIELD_LOGINNAME);
	}
	public static String getUserName(Map<String,String> user) {
		if(user == null) return null;
		return user.get(FIELD_USERNAME);
	}
	public static String getUserId(Map<String,String> user) {
		if(user == null) return null;
		return user.get(FIELD_ID);
	}
	public static String getToken(Map<String,String> user) {
		if(user == null) return null;
		return user.get(FIELD_TOKEN);
	}
	public static String getStatus(Map<String,String> user) {
		if(user == null) return null;
		return user.get(FIELD_STATUS);
	}
	public static void setPassword(Map<String,String> user,String password) {
		if(user!=null)user.put(FIELD_PASSWORD, password);
	}
	public static void setLoginName(Map<String,String> user,String loginname) {
		if(user!=null)user.put(FIELD_LOGINNAME, loginname);
	}
	public static void setUserName(Map<String,String> user,String username) {
		if(user!=null)user.put(FIELD_USERNAME, username);
	}
	public static void setUserId(Map<String,String> user,String id) {
		if(user!=null)user.put(FIELD_ID, id);
	}
	public static void setToken(Map<String,String> user,String token) {
		if(user!=null)user.put(FIELD_TOKEN, token);
	}
	public static void setStatus(Map<String,String> user,String status) {
		if(user!=null)user.put(FIELD_STATUS, status);
	}
	//-------获取当前会话中的user---------------------
	public static String getPassword() {
		return getPassword(getUser());
	}
	public static String getLoginName() {
		return getLoginName(getUser());
	}
	public static String getUserName() {
		return getUserName(getUser());
	}
	public static String getUserId() {
		return getUserId(getUser());
	}
	public static String getToken() {
		return getToken(getUser());
	}
	public static String getStatus() {
		return getStatus(getUser());
	}
	public static void setPassword(String password) {
		setPassword(getUser(),password);
	}
	public static void setLoginName(String loginname) {
		setLoginName(getUser(),loginname);
	}
	public static void setUserName(String username) {
		setUserName(getUser(),username);
	}
	public static void setUserId(String id) {
		setUserId(getUser(),id);
	}
	public static void setToken(String token) {
		setToken(getUser(),token);
	}
	public static void setStatus(String status) {
		setStatus(getUser(),status);
	}
	/**
	 * 增加用户缓存
	 * @param username
	 * @return
	 */
	public static void putUserInCache(Map<String,String> user) {
		putInCache(USER_CACHE, USER_CACHE_ID_ + user.get(FIELD_ID), user);
		putInCache(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.get(FIELD_LOGINNAME), user);
	}
	/**
	 * 获取用户缓存
	 * @param loginname
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String,String> getUserByLoginName(String loginname) {
		return (HashMap<String,String>)getCache(USER_CACHE).get(USER_CACHE_LOGIN_NAME_+loginname);
	}
	/**
	 * 获取用户缓存
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String,String> getUserById(String id) {
		return (HashMap<String,String>)getCache(USER_CACHE).get(USER_CACHE_ID_+id);
	}
	
	/**
	 * 增加角色缓存
	 * @param userId
	 * @param rules
	 */
	public static void putRoleInCache(String userId,Set<String> rules) {
		putInCache(ROLE_CACHE, userId, rules);
	}
	/**
	 * 增加权限缓存
	 * @param userId
	 * @param rp
	 */
	public static void putPermissionInCache(String userId,Set<String> permissions) {
		putInCache(PERMISSION_CACHE, userId, permissions);
	}
	/**
	 * 获取角色缓存
	 * @param userId
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getRoleFromCache(String userId) {
		return (Set<String>)getCache(ROLE_CACHE).get(userId);
	}
	/**
	 * 获取授权缓存
	 * @param userId
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getPermissionFromCache(String userId) {
		return (Set<String>)getCache(PERMISSION_CACHE).get(userId);
	}
	/**
	 * 获取角色缓存
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getRoleFromCache() {
		return (Set<String>)getCache(ROLE_CACHE).get(getUserId());
	}
	/**
	 * 获取授权缓存
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getPermissionFromCache() {
		return (Set<String>)getCache(PERMISSION_CACHE).get(getUserId());
	}
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object getFromCache(String cacheName, String key) {
		return getCache(cacheName).get(key);
	}

	/**
	 * 写入缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void putInCache(String cacheName, String key, Object value) {
		getCache(cacheName).put(key,value);
	}

	/**
	 * 清空user缓存
	 */
	public static void clearUserCache() {
		getCache(USER_CACHE).clear();
	}
	public static void removeUserFromCache(Map<String,String> user) {
		if(user!=null){
			getCache(USER_CACHE).remove(USER_CACHE_ID_ + user.get(FIELD_ID));
			getCache(USER_CACHE).remove(USER_CACHE_LOGIN_NAME_ + user.get(FIELD_LOGINNAME));
		}
	}
	public static void removeUserFromCache(String loginname) {
		removeUserFromCache(getUserByLoginName(loginname));
	}
	public static void removeCurrentUserFromCache() {
		removeUserFromCache(getUser());
	}
	
	/**
	 * 清空RULE缓存
	 */
	public static void clearRuleCache() {
		getCache(ROLE_CACHE).clear();
	}
	/**
	 * 清空PERMISSIONS缓存
	 */
	public static void clearPermissionCache() {
		getCache(PERMISSION_CACHE).clear();
	}
	
	/**
	 * 从缓存中移除
	 * @param cacheName
	 * @param key
	 */
	public static void removeFromCache(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}
	/**
	 * 获得一个Cache
	 * @param cacheName
	 * @return
	 */
	private static <K, V> Cache<K,V> getCache(String cacheName){
		return cacheManager.getCache(cacheName);
	}
	
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录用户
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getUser(){
		try{
			Map<String,String> user = (Map<String,String>)getSubject().getPrincipal();
			if (user != null){
				return user;
			}
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	

	
	public static Object get(String key) {
		return get(key, null);
	}
	
	public static Object get(String key, Object defaultValue) {
		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void put(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static void remove(String key) {
		getSession().removeAttribute(key);
	}
}
