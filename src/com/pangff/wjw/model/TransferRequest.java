package com.pangff.wjw.model;

import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.util.XStreamTranslator;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("root")
public class TransferRequest extends BaseBean{

	@XStreamAlias("body")
	public Body body;
	
	public static class Body{
		@XStreamAlias("leixing")
		public String leixing;
		
		@XStreamAlias("tomem")
		public String tomem;
		
		@XStreamAlias("jin")
		public String jin;
		
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
