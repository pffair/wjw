package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;


public class BaseBean {
	
	@XStreamAlias("userid")
	String userid;
	
	@XStreamAlias("method")
	String method;

}
