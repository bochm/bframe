package cn.bx.bframe.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class SqlMapperTemplet<T>  extends SqlSessionDaoSupport{
	
	
	public T selectOne(String statement){
		return getSqlSession().selectOne(statement);
	}
	public T selectOne(String statement,Object parameter){
		return getSqlSession().selectOne(statement,parameter);
	}
	public Map<String,Object> selectMap(String statement){
		return getSqlSession().selectOne(statement);
	}
	public Map<String,Object> selectMap(String statement,Object parameter){
		return getSqlSession().selectOne(statement, parameter);
	}
	public List<T> selectList(String statement){
		return getSqlSession().selectList(statement);
	}
	public List<T> selectList(String statement,Object parameter){
		return getSqlSession().selectList(statement, parameter);
	}
	public Map<String,Object> selectMapList(String statement,String mapKey){
		return getSqlSession().selectMap(statement, mapKey);
	}
	public Map<String,Object> selectMapList(String statement,Object parameter,String mapKey){
		return getSqlSession().selectMap(statement, parameter,mapKey);
	}
	public int insert(String statement){
		return getSqlSession().insert(statement);
	}
	public int insert(String statement,Object parameter){
		return getSqlSession().insert(statement,parameter);
	}
	public int executeBatch(String statement,List<Object> parameter){
		return executeBatch(statement,parameter,1000);
	}
	public int executeBatch(String statement,List<Object> parameter,int batchCount){
		int dataSize = parameter.size();
		int batchStep = dataSize/batchCount;
		for(int i = 0; i <= batchStep;i++){
			if(i==batchStep){
				if(dataSize%batchCount == 0) break;
				getSqlSession().update(statement, parameter.subList(i*batchCount, i*batchCount+dataSize%batchCount));
			}else{
				getSqlSession().update(statement, parameter.subList(i*batchCount, i*batchCount+batchCount));
			}
		}
		return dataSize;
	}
	public int update(String statement){
		return getSqlSession().update(statement);
	}
	public int update(String statement,Object parameter){
		return getSqlSession().update(statement, parameter);
	}
	public int delete(String statement){
		return getSqlSession().delete(statement);
	}
	public int delete(String statement,Object parameter){
		return getSqlSession().delete(statement, parameter);
	}
}
