package com.pangff.wjw.model;

import com.pangff.wjw.model.MyAccountRequest.Body;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("root")
public class RewardDetailRequest {

	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		
	}
}
