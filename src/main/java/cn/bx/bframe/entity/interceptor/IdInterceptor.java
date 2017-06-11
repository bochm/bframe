package cn.bx.bframe.entity.interceptor;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.util.StringUtils;

import cn.bx.bframe.common.util.IdGen;
import cn.bx.bframe.entity.BaseBean;
@Intercepts({  
    @Signature(type = Executor.class,  
    		method = "update",  
            args = {MappedStatement.class,Object.class})}) 
public class IdInterceptor implements Interceptor{
	private  IdGen idGen;
	@Override
	@SuppressWarnings("unchecked")
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
		//只有insert标签会自动设置id
		if(statement.getSqlCommandType().compareTo(SqlCommandType.INSERT) == 0 &&
				"NoKeyGenerator".equals(statement.getKeyGenerator().getClass().getSimpleName())){
			Object parameter = invocation.getArgs()[1];
			System.out.println("#######"+(parameter.getClass()));
			if(parameter instanceof BaseBean){
				BaseBean bean = (BaseBean)parameter;
				if(StringUtils.isEmpty(bean.getId()))
					bean.setId(idGen.getId());
			}else if(parameter instanceof DefaultSqlSession.StrictMap){
				
				DefaultSqlSession.StrictMap<Object> paraMap = (DefaultSqlSession.StrictMap<Object>)parameter;
				if(paraMap.containsKey("list")){
					List<Object> beans = (List<Object>)paraMap.get("list");
					for(Object bean : beans){
						if(bean instanceof BaseBean){
							BaseBean b = (BaseBean)bean;
							if(StringUtils.isEmpty(b.getId())) b.setId(idGen.getId());
						}
					}
				}
				
				
			}
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		String idGenerator = properties.getProperty("idGenerator");
		if("idworker".equalsIgnoreCase(idGenerator)){
			long workerId = Long.parseLong(StringUtils.isEmpty(properties.getProperty("idGenerator")) ? "0" : properties.getProperty("workerId"));
			long datacenterId = Long.parseLong(StringUtils.isEmpty(properties.getProperty("datacenterId")) ? "0" : properties.getProperty("datacenterId"));
			this.idGen = new IdGen(workerId,datacenterId);
		}else{
			this.idGen = new IdGen();
		}
	}

}
