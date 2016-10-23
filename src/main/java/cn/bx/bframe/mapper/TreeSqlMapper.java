package cn.bx.bframe.mapper;

import java.io.Serializable;
import java.util.Map;

import cn.bx.bframe.entity.TreeBean;

public interface TreeSqlMapper<T extends TreeBean<?>>  extends SqlMapper<T>{
	public int updateChildren(Map<String,Object> param);
	public int removeChildren(Serializable id);
	public int updateParents(T tree);
}
