package cn.bx.bframe.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.bx.bframe.entity.TreeBean;

public class SimpleTreeService<T extends TreeBean<?>> extends SimpleService<T>{
	public int saveTree(String stmID,T tree,String stmParentID,String stmChildrenID){
		if(this.update(stmID,tree) == 1){
			if(StringUtils.isNotEmpty(stmParentID)) this.update(stmParentID,tree);
			if(StringUtils.isNotEmpty(stmChildrenID)) this.update(stmChildrenID,tree);
			return 1;
		}
		return 0;
	}
	public int removeTree(String stmID,Object parameter,String stmListChildrenID,String stmChildrenID){
		if(StringUtils.isNotEmpty(stmListChildrenID)){
			List<T> list = this.selectList(stmListChildrenID, parameter);
			for(T tree : list){
				if(StringUtils.isNotEmpty(stmChildrenID)) this.delete(stmChildrenID,tree);
			}
		}
		return this.delete(stmID, parameter);
	}
}
