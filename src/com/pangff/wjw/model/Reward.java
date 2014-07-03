package com.pangff.wjw.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("root")
public class Reward extends BaseBean{
	
	Body body;
	
	public static class Body{
		List<RewardBean> list;
		public static class RewardBean{
			String jin;
			String leixing;
			String riqi;
		}
	}

}
