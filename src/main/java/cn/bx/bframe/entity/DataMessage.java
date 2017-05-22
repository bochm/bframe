package cn.bx.bframe.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.bx.bframe.common.config.AppConstants;

public class DataMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	private String message;
	private Object data;
	private String status;
	public DataMessage(){}
	public DataMessage(String message,Object data,String status){
		this.message = message;
		this.data = data;
		this.status = status;
	}
	public DataMessage(String message,Object data,int status){
		this.message = message;
		this.data = data;
		this.status = String.valueOf(status);
	}
	@JsonProperty(AppConstants.RET_MESSAGE)
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@JsonProperty(AppConstants.RET_DATA)
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@JsonProperty(value=AppConstants.RET_STATUS)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/*@JsonProperty("OK")
	public boolean isSeccuss(){
		return AppConstants.RET_SECCUSS.equals(status);
	}
	@JsonProperty("ERROR")
	public boolean isErr(){
		return !AppConstants.RET_SECCUSS.equals(status);
	}*/
	public static DataMessage success(String message,Object data){
		return new DataMessage(message,data,AppConstants.RET_SECCUSS); 
	}
	public static DataMessage data(Object data){
		return new DataMessage(null,data,AppConstants.RET_SECCUSS); 
	}
	public static DataMessage error(String message,Object data){
		return new DataMessage(message,data,AppConstants.RET_ERROR); 
	}
}
