package cn.bx.bframe.mapper;

import cn.bx.bframe.entity.TreeBean;

public interface TreeSqlMapper<T extends TreeBean<?>>  extends SqlMapper<T>{
	public int updateChildren(T tree);
	public int removeChildren(T tree);
	public int updateParents(T tree);
}
