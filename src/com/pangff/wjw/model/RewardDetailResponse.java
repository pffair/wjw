package com.pangff.wjw.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("root")
public class RewardDetailResponse {

	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		@XStreamImplicit(itemFieldName="list")
		public List<RewardDetail> list;
		
		public static class RewardDetail{
			
			@XStreamAlias("jin")
			public String jin;
			
			@XStreamAlias("leixing")
			public String leixing;
			
			@XStreamAlias("riqi")
			public String riqi;
		}
	}
}
