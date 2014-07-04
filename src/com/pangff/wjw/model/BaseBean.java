package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class BaseBean {
	
	@XStreamAlias("userid")
	public String userid;
	
	@XStreamAlias("method")
	public String method;

}
