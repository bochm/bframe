package cn.bx.bframe.entity;
import org.apache.commons.lang3.StringUtils;

import cn.bx.bframe.common.util.IdGen;

import com.fasterxml.jackson.annotation.JsonBackReference;
public abstract class TreeBean<T extends BaseBean> extends BaseBean{
	private static final long serialVersionUID = 1L;
	protected T parent;
	protected String parentId;//父对象id
	protected String parentIds;//所有父对象id,以,连接
	protected String treeSort;//树形排序.所有祖先节点的排序值-id用,相连
	public TreeBean(){
		this.sort = 9;
	}
	@JsonBackReference
	public abstract T getParent();
	public abstract void setParent(T parent);
	public String getTreeSort(){
		return (StringUtils.isEmpty(this.treeSort) ? "" :  (this.treeSort + ",")) + getSort();
	}
	public void setTreeSort(String treeSort) {
		this.treeSort = treeSort;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
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
	public String getId(){
		if(isNewRecord()) setId(IdGen.uuid());
		return this.id;
	}
}
