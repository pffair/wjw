package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class AdvDetailResponse extends BaseBean{
	
	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		
		@XStreamAlias("img")
		AdvDetail img;
		
		public static class AdvDetail{
			@XStreamAlias("imgbig")
			String imgbig;
			
			@XStreamAlias("title")
			String title;
			
			@XStreamAlias("content")
			String content;
		}
	}
	
}
