package com.couponcounter.response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
	
	@JsonProperty("status")
	private String status;

	@JsonProperty("status_code")
	private HttpStatus statusCode;

	@JsonProperty("status_msg")
	private String statusMsg;

	@JsonProperty("data")
	private Map<String,String> data = new HashMap<String,String>();

	@JsonProperty("req_id")
	private String reqId;

	@JsonProperty("server_ts")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private LocalDateTime serverTs;

	public ErrorResponse() {

	}
	
	public ErrorResponse(String status, HttpStatus statusCode, String statusMsg, String reqId, LocalDateTime server_ts) {
		this.status = status;
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
		this.reqId = reqId;
		this.serverTs = server_ts;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public Map<String,String> getData() {
		return data;
	}

	public void setData(Map<String,String> data) {
		this.data = data;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public LocalDateTime getServerTs() {
		return serverTs;
	}

	public void setServerTs(LocalDateTime serverTs) {
		this.serverTs = serverTs;
	}

	@Override
	public String toString() {
		return "SuccessResponse [status=" + status + ", statusCode=" + statusCode + ", statusMsg=" + statusMsg
				+ ", data=" + data + ", reqId=" + reqId + ", server_ts=" + serverTs + "]";
	}
}
