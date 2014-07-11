package com.pangff.wjw.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("root")
public class WithdrawalsDetailResponse extends BaseBean {

	@XStreamAlias("body")
	public Body body;

	public static class Body {

		@XStreamImplicit(itemFieldName = "list")
		public List<WithdrawalsDetail> list;

		public static class WithdrawalsDetail {
			@XStreamAlias("jin")
			public String jin;

			@XStreamAlias("sxf")
			public String sxf;

			@XStreamAlias("riqi")
			public String riqi;

			@XStreamAlias("bank")
			public String bank;
		}
	}
}
