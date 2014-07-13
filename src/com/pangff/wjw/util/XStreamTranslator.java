package com.pangff.wjw.util;

import com.pangff.wjw.model.BaseBean;
import com.thoughtworks.xstream.XStream;

public class XStreamTranslator {
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
	public synchronized String toXMLString(Object object) {
		//xstream.alias( "root" , TopGalleryRequest.class );
		 xstream.processAnnotations(object.getClass());
		return xstream.toXML(object);
	}

	/**
	 * Convert given XML to an Object
	 * @param xml
	 * @return
	 */
	public synchronized <T> T  toObject(String xml,Class<T> cls) {
		xstream.processAnnotations(cls);
		xstream.addDefaultImplementation(cls,BaseBean.class);
		T obj=(T)xstream.fromXML(xml);
		return obj;
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
