package com.gsww.common.util;

/**
 * url生成器
 * 
 * @author taolm
 *
 */
public class UrlBuilder {
	
	/**
	 * 生成的url
	 */
	private StringBuilder url = new StringBuilder(256);
	
	/**
	 * 构造函数
	 * 
	 * @param url
	 */
	public UrlBuilder(String url) {
		this.url.append(url);
	}
	
	/**
     * 给url拼接参数
     * 
     * @param url
     * @param parameter
     * @param value
     * @param connectString
     * @return
     */
    public UrlBuilder appendParameter(String parameter, String value, String connectString) {
    	
    	url.append(connectString).append(parameter).append("=").append(value); 	
    	return this;
    }

	@Override
	public String toString() {
		return url.toString();
	}
}
