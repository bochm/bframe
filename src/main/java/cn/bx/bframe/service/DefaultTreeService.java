package cn.bx.bframe.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.bx.bframe.entity.TreeBean;
import cn.bx.bframe.mapper.TreeSqlMapper;

public abstract class DefaultTreeService<M extends TreeSqlMapper<T>,T extends TreeBean<?>> extends DefaultService<M, T>{
	public TreeSqlMapper<T> getMapper() {
		return sqlMapper;
	}
	
	public int save(T tree) {
		if(getMapper().save(tree) == 1){
			getMapper().updateChildren(tree);
			getMapper().updateParents(tree);
			return 1;
		}
		return 0;
	}

	public int remove(Serializable id) {
		T tree = get(id);
		if(tree != null && tree.getId() != null){
			getMapper().removeChildren(tree);
			return getMapper().remove(id);
		}
		return 0;
	}
	public int removeList(Collection<Serializable> ids) {
		List<T> list = this.list(ids);
		for(T tree : list){
			if(tree != null && tree.getId() != null){
				getMapper().removeChildren(tree);
			}
		}
		return getMapper().removeList(ids);
	}
}
