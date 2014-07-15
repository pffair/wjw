package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class TransferResponse extends BaseBean {

	@XStreamAlias("body")
	public Body body;

	public static class Body {

		@XStreamAlias("return")
		public String returns;

		@XStreamAlias("message")
		public String message;
	}
}
