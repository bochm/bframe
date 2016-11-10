package cn.bx.bframe.entity;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
public abstract class TreeBean<T extends BaseBean> extends BaseBean{
	private static final long serialVersionUID = 1L;
	protected T parent;
	protected String parentId;//父对象id
	protected String parentTree;//树形排序.所有祖先节点的sort-id用,相连
	public TreeBean(){
		this.sort = 10;
	}
	@JsonBackReference
	public abstract T getParent();
	public abstract void setParent(T parent);
	//树形排序使用
	@JsonProperty("treeSort")
	public String getTreeSort(){
		return (StringUtils.isNotEmpty(this.parentTree) ? (this.parentTree + ",") :  "") + getSort() + "-" + getId();
	}
	
	
	public String getParentTree() {
		return parentTree;
	}
	public void setParentTree(String parentTree) {
		this.parentTree = parentTree;
	}
	//去除treeSort的连接符和sort,用于sql
	public String getParentIds() {
		if(parentTree == null) return null;
		String[] idSorts = parentTree.split(",");
		StringBuffer ids = new StringBuffer();
		for(String idSort : idSorts){
			if(idSort.indexOf("-") > 0) ids.append("'" + idSort.split("-")[1] + "',");
			else ids.append("'" + idSort + "',");
		}
		return ids.substring(0, ids.length() - 1);
	}
	public String getParentId() {
		if (parent != null && parentId == null){
			parentId = parent.getId();
		}
		return StringUtils.isNotEmpty(parentId) ? parentId : "";
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
		if (parent != null){
			parent.setId(parentId);
		}
	}
}
