package com.couponcounter.requests;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;

@Component
public class JsonRequest {

	@JsonAlias("data")
	private Data data;

	@JsonAlias("username")
	private String username;

	@JsonAlias("req_id")
	private String reqId;

	@JsonAlias("clientTs")
	private LocalDateTime client_ts;

	@JsonAlias("client_type")
	private String client_type;

	public JsonRequest() {

	}

	public JsonRequest(Data data, String reqId, LocalDateTime client_ts, String client_type) {
		super();
		this.data = data;
		this.reqId = reqId;
		this.client_ts = client_ts;
		this.client_type = client_type;
	}

	

	public JsonRequest(String username, String reqId, LocalDateTime client_ts, String client_type) {
		this.username = username;
		this.reqId = reqId;
		this.client_ts = client_ts;
		this.client_type = client_type;
	}

	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public LocalDateTime getClient_ts() {
		return client_ts;
	}

	public void setClient_ts(LocalDateTime client_ts) {
		this.client_ts = client_ts;
	}

	public String getClient_type() {
		return client_type;
	}

	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}

	@Override
	public String toString() {
		return "JsonRequest [data=" + data + ", reqId=" + reqId + ", client_ts=" + client_ts + ", client_type="
				+ client_type + "]";
	}

}
