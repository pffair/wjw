package com.pangff.wjw.model;

import java.util.List;

import com.pangff.wjw.model.ExchangeDetailResponse.Body.ExchangeDetail;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("root")
public class RewardDetailResponse {

	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		@XStreamImplicit(itemFieldName="list")
		List<RewardDetail> list;
		
		public static class RewardDetail{
			
			@XStreamAlias("jin")
			String jin;
			
			@XStreamAlias("leixing")
			String leixing;
			
			@XStreamAlias("riqi")
			String riqi;
		}
	}
}
