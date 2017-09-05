package com.gsww.uids.system.service;

import java.io.IOException;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.gsww.common.util.StringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * freemark的自定义权限验证标签处理
 * 
 * @author simplife
 *
 */
public class PermissionDirective implements TemplateDirectiveModel {

	private final static String HASPERM_KEY = "perms";
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String perms = params.containsKey(HASPERM_KEY) ? params.get(HASPERM_KEY).toString() : "";
		boolean result = false;
		if (StringUtil.isNotBlank(perms)) {
			Subject subject = SecurityUtils.getSubject();
			for (String perm : perms.split(",")) {
				if (subject.isPermitted(perm)) {
					result = true;
					break;
				}
			}
		}
		if (result) {
			body.render(env.getOut());
		}
		
	}

}
