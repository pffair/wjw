package com.pangff.wjw.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("root")
public class ExchangeDetailResponse extends BaseBean{
	
	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		
		@XStreamImplicit(itemFieldName="list")
		public List<ExchangeDetail> list;
		
		public static class ExchangeDetail{
			
			@XStreamAlias("jin")
			public String jin;
			
			@XStreamAlias("riqi")
			public String riqi;
		}
	}
	
}

