package cn.bx.bframe.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.bx.bframe.entity.BaseBean;
import cn.bx.bframe.mapper.SqlMapper;

public abstract class DefaultService<M extends SqlMapper<T>, T extends BaseBean> extends BaseService{

	// spring4 泛型限定依赖注入
	protected M sqlMapper;

	@Autowired
	public void setSqlMapper(M mapper) {
		this.sqlMapper = mapper;
	}
	public SqlMapper<T> getMapper() {
		return sqlMapper;
	}
	
	public List<T> list(Object parameter) {
		return getMapper().list(parameter);
	}

	public T get(Serializable id) {
		return getMapper().get(id);
	}

	public int add(T obj) {
		return getMapper().add(obj);
	}

	public int save(T obj) {
		return getMapper().save(obj);
	}

	public int remove(Serializable id) {
		return getMapper().remove(id);
	}
	
	public int removeList(Collection<Serializable> ids) {
		return getMapper().removeList(ids);
	}
}