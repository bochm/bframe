package cn.bx.bframe.mapper;

import java.io.Serializable;
import java.util.List;

import cn.bx.bframe.entity.BaseBean;


public interface SqlMapper<T extends BaseBean> {
	public List<T> list(Object parameter);
	public T get(Serializable id);
	public int add(T obj);
	public int save(T obj);
	public int remove(Serializable id);
	public int removeList(String[] ids);
}
