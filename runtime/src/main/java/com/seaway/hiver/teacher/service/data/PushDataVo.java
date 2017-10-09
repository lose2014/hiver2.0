package com.seaway.hiver.teacher.service.data;

/**
 * Created by Leo.Chang on 2016/12/21.
 */
public class PushDataVo {
	private String title;
	private String body;
	private String redirectURL;
	private String broadcast;
	private String broadcastContent;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public String getBroadcast() {
		return broadcast;
	}

	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}

	public String getBroadcastContent() {
		return broadcastContent;
	}

	public void setBroadcastContent(String broadcastContent) {
		this.broadcastContent = broadcastContent;
	}
}
