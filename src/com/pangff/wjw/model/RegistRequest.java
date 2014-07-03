package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class RegistRequest extends BaseBean{
	
	@XStreamAlias("body")
	Body body;
	
	public static class Body{
		@XStreamAlias("zsname")
		String zsname;
		
		@XStreamAlias("memname")
		String memname;
		
		@XStreamAlias("tel")
		String tel;
		
		@XStreamAlias("jibie")
		String jibie;
		
		@XStreamAlias("tuijian")
		String tuijian;
		
		@XStreamAlias("qu")
		String qu;
		
		@XStreamAlias("dlpass")
		String dlpass;
		
		@XStreamAlias("pass2")
		String pass2;
		
		@XStreamAlias("paypass")
		String paypass;
	}

}
