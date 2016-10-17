package cn.bx.bframe.entity;
import org.apache.commons.lang3.StringUtils;

import cn.bx.bframe.common.util.IdGen;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
public abstract class TreeBean<T extends BaseBean> extends BaseBean{
	private static final long serialVersionUID = 1L;
	protected T parent;
	protected String parentId;//父对象id
	protected String parentIds;//所有父对象id,以,连接
	protected String oldId;//修改sort之前的id
	public TreeBean(){
		this.sort = 9;
	}
	@JsonBackReference
	public abstract T getParent();
	public abstract void setParent(T parent);
	@JsonProperty("treeSort")
	public String getTreeSort(){
		return (StringUtils.isEmpty(this.parentIds) ? "" :  (this.parentIds + ",")) +  getNewId();
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
	@JsonIgnore
	public String getNewId(){
		String oId = getId();
		if(oId == null) return null;
		return getSort() + oId.substring(oId.indexOf("-"));//如果修改了sort则重新定义id(id由sort-uuid组成)
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
		if (parent != null){
			parent.setId(parentId);
		}
	}
	
	public String getOldId() {
		return oldId == null ? getId() : oldId;
	}
	public void setOldId(String oldId) {
		this.oldId = oldId;
	}
	//树形为了排序将sort加在id之前
	public String getId(){
		if(isNewRecord()) setId(getSort() + "-" + IdGen.uuid());
		return this.id;
	}
}
