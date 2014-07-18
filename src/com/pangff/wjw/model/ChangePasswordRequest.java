package com.pangff.wjw.model;

import com.pangff.wjw.util.ParseMD5;
import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.util.XStreamTranslator;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class ChangePasswordRequest extends BaseBean{
	
	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
	
		
		@XStreamAlias("LX")
		public String LX;
		
		@XStreamAlias("oldpass")
		public String oldpass;
		
		@XStreamAlias("newpass")
		public String newpass;
		
	}
	
	public  String getParams(String method,Body body){
		this.method = method;
		this.userid = UserInfoUtil.getInstanse().getUserId();
		this.body=body;
		return XStreamTranslator.getInstance().toXMLString(this);
	}

}
