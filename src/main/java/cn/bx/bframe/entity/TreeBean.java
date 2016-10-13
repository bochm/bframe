package cn.bx.bframe.entity;
import org.apache.commons.lang3.StringUtils;

import cn.bx.bframe.common.util.Reflections;

import com.fasterxml.jackson.annotation.JsonBackReference;
public abstract class TreeBean<T> extends BaseBean{
	private static final long serialVersionUID = 1L;
	protected T parent;
	protected String parentIds;//所有父对象id,以-连接
	@JsonBackReference
	public abstract T getParent();
	public abstract void setParent(T parent);
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getParentId() {
		String id = null;
		if (parent != null){
			id = (String)Reflections.getFieldValue(parent, "id");
		}
		return StringUtils.isNotEmpty(id) ? id : "0";
	}
}
