package com.pangff.wjw.model;

import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.util.XStreamTranslator;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class WithdrawalsCommitRequest extends BaseBean{
	
	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		
		@XStreamAlias("zxname")
		public String zxname;
		
		@XStreamAlias("bank")
		public String bank;
		
		@XStreamAlias("zhihang")
		public String zhihang;
		
		@XStreamAlias("zhanghao")
		public String zhanghao;
		
		@XStreamAlias("jin")
		public String jin;
		
		@XStreamAlias("sxf")
		public String sxf;
		
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
