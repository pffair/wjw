package com.pangff.wjw.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("root")
public class TopGalleryResponse extends BaseBean{
	
	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		
		@XStreamImplicit(itemFieldName="img")
		public List<String> imgList;
		
		@XStreamAlias("gundong")
		public Gundong gundong;
		
		@XStreamAlias("gundong")
		public static class Gundong{
			
			@XStreamAlias("p")
			public String p;
		}
	}
}
