package cn.bx.bframe.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.bx.bframe.mapper.SqlMapperTemplet;

/**
 * 简单service实现,可以直接通过service调用mapper文件中的statment而不需要定义Mapper接口,MapperName默认为Service变为Mapper
 * @author bcm
 */
public class SimpleService <T extends Serializable> extends BaseService{
	protected SqlMapperTemplet<T> sqlMapperTemplet;
	@Resource(name="sqlMapperTemplet")
	protected void setSqlMapperTemplet(SqlMapperTemplet<T> sqlMapperTemplet){
		this.sqlMapperTemplet = sqlMapperTemplet;
	}
	protected String mapperStatement(String stmID){
		return getMapperName()+"."+stmID;
	}
	public T selectOne(String stmID){
		return sqlMapperTemplet.selectOne(mapperStatement(stmID));
	}
	public T selectOne(String stmID,Object parameter){
		return sqlMapperTemplet.selectOne(mapperStatement(stmID),parameter);
	}
	public Map<String,Object> selectMap(String stmID,Object parameter){
		return sqlMapperTemplet.selectMap(mapperStatement(stmID),parameter);
	}
	public Map<String,Object> selectMap(String stmID){
		return sqlMapperTemplet.selectMap(mapperStatement(stmID));
	}
	public List<T> selectList(String stmID){
		return sqlMapperTemplet.selectList(mapperStatement(stmID));
	}
	public List<T> selectList(String stmID,Object parameter){
		return sqlMapperTemplet.selectList(mapperStatement(stmID),parameter);
	}
	public Map<String,Object> selectMapList(String stmID,String mapKey){
		return sqlMapperTemplet.selectMapList(mapperStatement(stmID),mapKey);
	}
	public Map<String,Object> selectMapList(String stmID,Object parameter,String mapKey){
		return sqlMapperTemplet.selectMapList(mapperStatement(stmID),parameter,mapKey);
	}
	public int insert(String stmID){
		return sqlMapperTemplet.insert(mapperStatement(stmID));
	}
	public int insert(String stmID,Object parameter){
		return sqlMapperTemplet.insert(mapperStatement(stmID),parameter);
	}
	public int update(String stmID){
		return sqlMapperTemplet.update(mapperStatement(stmID));
	}
	public int update(String stmID,Object parameter){
		return sqlMapperTemplet.update(mapperStatement(stmID),parameter);
	}
	public int delete(String stmID){
		return sqlMapperTemplet.delete(mapperStatement(stmID));
	}
	public int delete(String stmID,Object parameter){
		return sqlMapperTemplet.delete(mapperStatement(stmID),parameter);
	}
	public int executeBatch(String stmID,List<Object> parameter){
		return sqlMapperTemplet.executeBatch(mapperStatement(stmID),parameter);
	}
	public int executeBatch(String stmID,List<Object> parameter,int batchCount){
		return sqlMapperTemplet.executeBatch(mapperStatement(stmID),parameter,batchCount);
	}
	protected String getMapperName(){
		String mapperName = getClass().getName().replaceFirst(".service.", ".mapper.");
		return mapperName.substring(0, mapperName.length()-7)+"Mapper";
	}
}
