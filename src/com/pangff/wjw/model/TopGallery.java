package com.pangff.wjw.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class TopGallery extends BaseBean{
	
	public TopGallery(String url){
		this.url = url;
	}
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	Body body;
	
	public static class Body{
		List<String> imgList;
	}
}
