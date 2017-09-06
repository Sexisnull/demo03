
package com.gsww.jup.webtag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.gsww.jup.SpringContextHolder;
import com.gsww.jup.entity.sys.SysPara;
import com.gsww.jup.service.sys.SysParaService;


/**
 * 类名: CheckboxListTag <br/>
 * 功能: 选择框列表. <br/>
 * 创建日期: 2015-6-16 下午05:07:48 <br/>
 * 
 * @author HT
 * @version V1.0
 * @see
 * 
 */
public class CheckboxListTag extends TagSupport {

	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = -6105428696220651863L;

	private String name;
	private String value;
	private String type;
	private String inputType;
	private String cssClass;
	private String cssStyle;
	private String defaultValue;
	private String only;

	private SysParaService sysParaService = (SysParaService) SpringContextHolder.getApplicationContext().getBean("sysParaService");

	@Override
	public int doStartTag() throws JspException {
		try {
			
			List<SysPara> ls = sysParaService.findByParaType(type);

			String typeClass = "check_btn";
			StringBuffer html = new StringBuffer();

			if (!StringUtils.isNotBlank(value)) {
				value = defaultValue;
			}
			if (!StringUtils.isNotBlank(inputType)) {
				inputType = "checkbox";
			}
			if (inputType.equals("radio")) {
				typeClass = "radio_btn";
			}

			for (SysPara dict : ls) {
				html.append("<label class=\"check-label\">");
				html.append("<i id=\"\" class=\"" + typeClass + "");
				html.append(!StringUtils.isBlank(value) && value.indexOf(dict.getParaCode()) > -1 ? " active\"" : "\"");
				html.append("\"></i>");
				html.append("<span>" + dict.getParaName() + "</span>");
				html.append("<input type=\"" + inputType + "\" name=\"" + name + "\" class=\"check_btn\" value=\"" + dict.getParaCode() + "\" style=\"filter: alpha(opacity = 0); width: 0px; opacity: 0;\"");
				if (StringUtils.isNotEmpty(only) && !(dict.getParaCode().indexOf(only) > -1))
					html.append(" disabled=\"disabled\"");
				html.append(!StringUtils.isBlank(value) && value.indexOf(dict.getParaCode()) > -1 ? " checked=\"checked\"" : "");
				html.append(" /></label>");
			}

			this.pageContext.getOut().print(html.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setOnly(String only) {
		this.only = only;
	}

	public String getOnly() {
		return only;
	}

}
