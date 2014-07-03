package com.pangff.wjw.model;


public class Adv extends BaseBean{
	
	public Adv(String picUrl,String display){
		this.picUrl = picUrl;
		this.display = display;
	}

	private String picUrl;

	private String display;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
	Body body;
	
	public static class Body{
		String id;
		String returns;
		String message;
		Img img;
	}
	
}
