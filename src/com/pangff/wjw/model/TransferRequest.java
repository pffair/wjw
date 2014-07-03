package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("root")
public class TransferRequest extends BaseBean{

	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		@XStreamAlias("leixing")
		String leixing;
		
		@XStreamAlias("tomem")
		String tomem;
		
		@XStreamAlias("jin")
		String jin;
		
		@XStreamAlias("password")
		String password;
	}
}
