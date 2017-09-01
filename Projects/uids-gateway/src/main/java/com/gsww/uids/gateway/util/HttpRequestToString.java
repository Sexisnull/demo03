package com.gsww.uids.gateway.util;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

/**
 * 
 * @author niubt
 *
 */
public class HttpRequestToString extends AbstractTransformer {

	public HttpRequestToString() {
		super();
		this.registerSourceType(String.class);     
		this.setReturnClass(String.class);  
	}
	
	@Override
	protected Object doTransform(Object src, String encoding) throws TransformerException {
		String requestQuery = src.toString();     
		if (requestQuery != null && requestQuery.length() > 0 && requestQuery.indexOf('?') != -1) {     
			requestQuery = requestQuery.substring(requestQuery.indexOf('?') + 1).trim(); 
		} 
		return requestQuery.substring(requestQuery.indexOf("=") + 1);    
	}
}
