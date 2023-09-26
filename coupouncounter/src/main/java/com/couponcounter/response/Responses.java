package com.couponcounter.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Responses {
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("data")
	private Object data;
	
	@JsonProperty("req_id")
	private String reqId;
	
	@JsonProperty("servers_ts")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")	
	private LocalDateTime serverTs;

	public Responses() {

	}

	public Responses(String status, Object data, String reqId, LocalDateTime server_ts) {
		this.status = status;
		this.data = data;
		this.reqId = reqId;
		this.serverTs = server_ts;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	@Override
	public String toString () {
		return "SuccessResponse [status=" + status + ", data=" + data + ", reqId=" + reqId + ", server_ts=" + serverTs
				+ "]";
	}
}
