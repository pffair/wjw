package com.pangff.wjw.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("root")
public class TopGalleryResponse extends BaseBean{
	
	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		
		@XStreamImplicit(itemFieldName="img")
		List<String> imgList;
		
		@XStreamAlias("gundong")
		String gundong;
	}
}
