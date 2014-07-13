package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class AdvDetailResponse extends BaseBean{
	
	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		
		@XStreamAlias("img")
		public AdvDetail img;
		
		public static class AdvDetail{
			@XStreamAlias("imgbig")
			public String imgbig;
			
			@XStreamAlias("title")
			public String title;
			
			@XStreamAlias("content")
			public String content;
		}
	}
	
}
