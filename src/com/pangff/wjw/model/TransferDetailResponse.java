package com.pangff.wjw.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("root")
public class TransferDetailResponse extends BaseBean{

	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		@XStreamImplicit(itemFieldName = "list")
		public List<TransferDetail> list;
		
		public static class TransferDetail{
			@XStreamAlias("mem")
			public String mem;
			
			@XStreamAlias("tomem")
			public String tomem;
			
			@XStreamAlias("jin")
			public String jin;
			
			@XStreamAlias("riqi")
			public String riqi;
		}
	}
}
