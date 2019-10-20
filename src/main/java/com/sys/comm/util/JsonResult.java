package com.sys.comm.util;

import java.io.Serializable;

/**
 * @ClassName: JsonResult
 * @Description: JSON格式返回类
 * @author: zhongqian
 */
public class JsonResult implements Serializable {

	/**
	 * @Fields: serialVersionUID
	 * @Todo: TODO
	 */
	private static final long serialVersionUID = 7032059601795801324L;

	// 执行操作是否成功
	private boolean success;

	// 返回消息实体
	private String msg;

	// 承载数据
	private Object data;

	// 消息类型：info、warning、error
	private String msgType="info";

	// 是否是全局的消息
	private boolean global=false;

	//  消息对应编码（新增,可不传）
	private String msgCode;
	//签名
	private String sign;
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public static JsonResult builder(boolean success,String msg) {
		return new JsonResult(success,msg);
	}
	public static JsonResult builder(boolean success,Object msg) {
		return new JsonResult(success,msg);
	}

	public JsonResult() {
		this.success = true;
	}

	public JsonResult(boolean isSuccess) {
		this.success = isSuccess;
	}

	public JsonResult(boolean isSuccess, String msg) {
		this.success = isSuccess;
		this.msg = msg;
	}

	public JsonResult(boolean isSuccess, Object data) {
		this.success = isSuccess;
		this.data = data;
	}

	/**
	 * 设置返回数据,默认返回标志为true
	 *
	 * @param data
	 */
	public JsonResult(Object data) {
		this.success = true;
		this.data = data;

	}

	public JsonResult(boolean isSuccess, String msgCode, String msg, Object data) {
	    this.success = isSuccess;
	    this.msgCode = msgCode;
	    this.msg = msg;
	    this.data = data;
	}

	public JsonResult(boolean isSuccess, String msg, Object data) {
		this.success = isSuccess;
		this.msg = msg;
		this.data = data;
	}

	public JsonResult(boolean isSuccess, boolean global, String msg, String msgType, Object data) {
		this.success = isSuccess;
		this.global = global;
		this.msg = msg;
		this.msgType = msgType;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }


}
