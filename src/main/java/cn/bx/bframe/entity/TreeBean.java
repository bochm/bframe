package cn.bx.bframe.entity;
import org.apache.commons.lang3.StringUtils;

import cn.bx.bframe.common.util.IdGen;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
public abstract class TreeBean<T extends BaseBean> extends BaseBean{
	private static final long serialVersionUID = 1L;
	protected T parent;
	protected String parentId;//父对象id
	protected String parentIds;//所有父对象id,以,连接
	public TreeBean(){
		this.sort = 9;
	}
	@JsonBackReference
	public abstract T getParent();
	public abstract void setParent(T parent);
	@JsonProperty("treeSort")
	public String getTreeSort(){
		return (StringUtils.isEmpty(this.parentIds) ? "" :  (this.parentIds + ",")) +  getId();
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
	//树形为了排序将sort加在id之前
	public String getId(){
		if(isNewRecord()) setId(getSort() + "-" + IdGen.uuid());
		return this.id;
	}
}
