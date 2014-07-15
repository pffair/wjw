package com.pangff.wjw.model;

import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.util.XStreamTranslator;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class TransferDetailRequest extends BaseBean {

	@XStreamAlias("body")
	public Body body;

	public static class Body {

	}

	public String getParams(String method) {
		this.method = method;
		this.body = new Body();
		this.userid = UserInfoUtil.getInstanse().getUserId();
		return XStreamTranslator.getInstance().toXMLString(this);
	}
}
