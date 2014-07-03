package com.pangff.wjw.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("root")
public class WithdrawalsDetailResponse extends BaseBean {

	@XStreamAlias("body")
	Body body;

	public static class Body {

		@XStreamImplicit(itemFieldName = "list")
		List<WithdrawalsDetail> list;

		public static class WithdrawalsDetail {
			@XStreamAlias("jin")
			String jin;

			@XStreamAlias("sxf")
			String sxf;

			@XStreamAlias("riqi")
			String riqi;

			@XStreamAlias("bank")
			String bank;
		}
	}
}
