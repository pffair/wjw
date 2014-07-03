package com.pangff.wjw.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("root")
public class TransferDetailResponse extends BaseBean{

	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		@XStreamImplicit(itemFieldName = "list")
		List<TransferDetail> list;
		
		public static class TransferDetail{
			@XStreamAlias("mem")
			String mem;
			
			@XStreamAlias("tomem")
			String tomem;
			
			@XStreamAlias("jin")
			String jin;
			
			@XStreamAlias("riqi")
			String riqi;
		}
	}
}
