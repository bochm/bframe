package cn.bx.bframe.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.bx.bframe.common.config.AppConstants;
import cn.bx.system.utils.UserUtils;

public class BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String id;//唯一标识
	protected String remarks;	// 备注
	protected String createBy;	// 创建者
	protected Date createDate;	// 创建日期
	protected String updateBy;	// 更新者
	protected Date updateDate;	// 更新日期
	protected int sort; //排序编号
	protected String status;//状态  1启用  0停用
	//protected String form_action;//表单提交操作 add save delete 改为mybatis插件自动生成

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@JsonIgnore
	public String getCreateBy() {
		if(createBy == null) setCreateBy(UserUtils.getUserId());
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	@JsonIgnore
	public Date getCreateDate() {
		if(createDate == null) setCreateDate(Calendar.getInstance().getTime());
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@JsonIgnore
	public String getUpdateBy() {
		if(updateBy == null) setUpdateBy(UserUtils.getUserId());
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	@JsonIgnore
	public Date getUpdateDate() {
		if(updateDate == null) setUpdateDate(Calendar.getInstance().getTime());
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setId(String id) {
		this.id = id;
	}
	//默认为1
	public String getStatus() {
		return status == null ? AppConstants.YES : status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public String getId(){
		return id;
	}

	
	
}
