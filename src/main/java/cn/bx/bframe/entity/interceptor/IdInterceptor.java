package cn.bx.bframe.entity.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.util.StringUtils;

import cn.bx.bframe.common.util.IdGen;
import cn.bx.bframe.entity.BaseBean;
@Intercepts({  
    @Signature(type = Executor.class,  
    		method = "update",  
            args = {MappedStatement.class,Object.class})}) 
public class IdInterceptor implements Interceptor{

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
		//只有insert标签会自动设置id
		System.out.println("##########"+statement.getKeyGenerator().getClass().getSimpleName());
		if(statement.getSqlCommandType().compareTo(SqlCommandType.INSERT) == 0 &&
				"NoKeyGenerator".equals(statement.getKeyGenerator().getClass().getSimpleName())){
			Object parameter = invocation.getArgs()[1];
			if(parameter instanceof BaseBean){
				BaseBean bean = (BaseBean)parameter;
				if(StringUtils.isEmpty(bean.getId()))
					bean.setId(IdGen.uuid());
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
		// TODO Auto-generated method stub
		
	}

}
