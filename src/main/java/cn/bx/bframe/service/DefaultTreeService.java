package cn.bx.bframe.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bx.bframe.entity.TreeBean;
import cn.bx.bframe.mapper.TreeSqlMapper;

public abstract class DefaultTreeService<M extends TreeSqlMapper<T>,T extends TreeBean<?>> extends DefaultService<M, T>{
	public TreeSqlMapper<T> getMapper() {
		return sqlMapper;
	}
	
	public int save(T tree) {
		String id = tree.getId();
		T before = this.get(id);
		int updated = getMapper().save(tree);
		if(updated == 1 && before != null && (!before.getParentId().equals(tree.getParentId())||before.getSort() != tree.getSort())){ //修改父节点
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("old", before);
			param.put("new", tree);
			getMapper().updateChildren(param);
			tree.setId(tree.getNewId());
			tree.setOldId(id);
		}
		return updated;
	}

	public int removeWithChildren(Serializable id) {
		T tree = get(id);
		if(tree != null && tree.getId() != null){
			getMapper().removeChildren(tree);
			return getMapper().remove(id);
		}
		return 0;
	}
	public int removeListWithChildren(Collection<Serializable> ids) {
		List<T> list = this.list(ids);
		for(T tree : list){
			if(tree != null && tree.getId() != null){
				getMapper().removeChildren(tree);
			}
		}
		return getMapper().removeList(ids);
	}
}
