package com.pangff.wjw.model;

import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.util.XStreamTranslator;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class RegistRequest extends BaseBean{
	
	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		@XStreamAlias("zsname")
		public String zsname;
		
		@XStreamAlias("memname")
		public String memname;
		
		@XStreamAlias("tel")
		public String tel;
		
		@XStreamAlias("jibie")
		public String jibie;
		
		@XStreamAlias("tuijian")
		public String tuijian;
		
		@XStreamAlias("qu")
		public String qu;
		
		@XStreamAlias("dlpass")
		public String dlpass;
		
		@XStreamAlias("pass2")
		public String pass2;
		
		@XStreamAlias("paypass")
		public String paypass;
		
		@XStreamAlias("password")
		public String password;
	}
	
	public  String getParams(String method,Body body){
		this.method = method;
		this.userid = UserInfoUtil.getInstanse().getUserId();
		this.body = body;
		return XStreamTranslator.getInstance().toXMLString(this);
	}

}
