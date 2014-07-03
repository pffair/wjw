package com.pangff.wjw.util;

import com.thoughtworks.xstream.XStream;

public class XStreamTranslator<T> {
	private XStream xstream = null;
	
	private XStreamTranslator() {
		xstream = new XStream();
		xstream.ignoreUnknownElements();
	}

	/**
	 * Convert a any given Object to a XML String
	 * 
	 * @param object
	 * @return
	 */
	public String toXMLString(Object object) {
		return xstream.toXML(object);
	}

	/**
	 * Convert given XML to an Object
	 * @param xml
	 * @return
	 */
	public Object toObject(String xml) {
		return (Object) xstream.fromXML(xml);
	}
	
	public XStream getXsteam(){
		return xstream;
	}

	/**
	 * return this class instance
	 * @return
	 */
	public static XStreamTranslator getInstance() {
		return new XStreamTranslator();
	}
}
