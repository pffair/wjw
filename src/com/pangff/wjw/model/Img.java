package com.pangff.wjw.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Img {

	@XStreamAlias("id")
	public String id;
	
	@XStreamAlias("imgs")
	public String imgs;
	
	@XStreamAlias("title")
	public String title;
	
	@XStreamAlias("imgbig")
	public String imgbig;
}
