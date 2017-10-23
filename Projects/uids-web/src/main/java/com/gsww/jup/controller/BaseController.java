package com.gsww.jup.controller;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.util.Assert;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.gsww.jup.criteria.Criteria;
import com.gsww.jup.criteria.Criterion;
import com.gsww.jup.criteria.Criterion.Operator;
import com.gsww.jup.criteria.Restrictions;
import com.gsww.jup.entity.BaseResult;
import com.gsww.jup.util.PageUtils;

/**
 * 
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-web</p>
 * <p>创建时间 : 2014-7-23 下午03:35:23</p>
 * <p>类描述 : 基础控制类        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">zhangxj</a>
 */
@Controller
public class BaseController {
	//默认显示条数
	 protected static final String PAGE_SIZE = "10";

	 /**
	  * 
	  * 方法描述 : 初始化PageRequest
	  * @param hrequest 
	  * @param pageUtils 分页实体
	  * @param className 具体实体类
	  * @return
	  */
	protected PageRequest buildPageRequest(HttpServletRequest hrequest,PageUtils pageUtils,Class className,String findNowPage) {
		
		pageUtils=this.getNowPageNo(hrequest,className, pageUtils,findNowPage);	
			
		Sort sort = null;
		//判断排序字段是否为空
		if(pageUtils.getOrderField()!=null){
			//排序字段按逗号分开
			String[] field=pageUtils.getOrderField().split(",");
			//继续判断排序顺序是否为空
			String[] px=null;
			if(pageUtils.getOrderSort()!=null){
				px=pageUtils.getOrderSort().split(",");
			}
			for(int i=0;i<field.length;i++){
				//第一个排序字段无需sort.and
				if(i==0){
					if(px!=null && px[i]!=null && px[i].equals(PageUtils.DESC)){
						sort = new Sort(Direction.DESC, field[i]);
					}else{
						sort = new Sort(field[i]);
					}
				}else{
					//后续排序字段需要加sort.and
					if(px!=null && px[i]!=null && px[i].equals(PageUtils.DESC)){
						sort = sort.and(new Sort(Direction.DESC, field[i]));
					}else{
						sort = sort.and(new Sort(field[i]));
					}
				}
			}
		}
		return new PageRequest(pageUtils.getPageNo()-1, pageUtils.getPageSize(), sort);
	}
	

	 /**
	  * 
	  * 方法描述 : 组装Specification<T>
	  * 支撑EQ, LIKE, GT, LT, GTE, LTE
	  * @param searchParams 
	  * @param clazz 具体实体类
	  * @return
	  */
	public static <T> Specification<T> toSpecification(Map<String, Object> searchParams, final Class<T> clazz){
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), clazz);
		return spec;
	}
	
	protected <T> Specification<T> toNewSpecification(Map<String, Object> searchParams, final Class<T> clazz) throws Exception {
		Criteria<T> spec = new Criteria<T>();
		for (Entry<String, Object> entry : searchParams.entrySet()) {
			String[] key = entry.getKey().split("_");
			Operator op = Enum.valueOf(Operator.class, key[0]);
			spec.add(buildPropertyFilterCriterion(op,key[1],entry.getValue()));
		}
		return spec;
	}

	protected Criterion buildPropertyFilterCriterion(final Operator operator, final String propertyName, final Object propertyValue) throws Exception {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;
		try {
			// 根据Operator构造criterion
			if (Operator.EQ.equals(operator)) {
				criterion = Restrictions.eq(propertyName, propertyValue, true);
			} else if (Operator.LIKE.equals(operator)) {
				criterion = Restrictions.like(propertyName, (String) propertyValue, true);
			} else if (Operator.LT.equals(operator)) {
				criterion = Restrictions.lt(propertyName, propertyValue, true);
			} else if (Operator.LTE.equals(operator)) {
				criterion = Restrictions.lte(propertyName, propertyValue, true);
			} else if (Operator.GT.equals(operator)) {
				criterion = Restrictions.gt(propertyName, propertyValue, true);
			} else if (Operator.GTE.equals(operator)) {
				criterion = Restrictions.gte(propertyName, propertyValue, true);
			} else if (Operator.NE.equals(operator)) {
				criterion = Restrictions.ne(propertyName, propertyValue, true);
			}else if (Operator.IN.equals(operator)) {
				criterion = Restrictions.in(propertyName, (Collection) propertyValue, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return criterion;
	}
	
	/**
	 * 
	 * 方法描述 : 解决返回当前页问题
	 * @param hrequest
	 * @param className 具体类实体
	 * @param pageUtils 分页实体
	 * @return
	 */
	PageUtils getNowPageNo(HttpServletRequest hrequest,Class className,PageUtils pageUtils,String findNowPage){
		HttpSession session =  hrequest.getSession();
		if("true".equals(findNowPage)){
			//判断session，没有则创建
			if (session.getAttribute(className.getName()+"_pageNo") == null || pageUtils.getPageNo()==0){
				if(pageUtils.getPageNo()==0){
					pageUtils.setPageNo(1);
				}
				session.setAttribute(className.getName()+"_pageNo", pageUtils.getPageNo());
			}else{
				pageUtils.setPageNo(Integer.parseInt(session.getAttribute(className.getName()+"_pageNo").toString()));
			}
		}else{
			//重新获取对象
			session.setAttribute(className.getName()+"_pageNo", pageUtils.getPageNo());
		}
		return pageUtils;
	}

	protected void returnMsg(String type, String msg, HttpServletRequest request) {
		Map<String,String> msgMap=new HashMap<String,String>();
		msgMap.put("type", type);
		msgMap.put("msg", msg);
		request.getSession().setAttribute("msgMap",msgMap);
	}
	
	/**
	 * 修改密码
	 * @param ret
	 * @param msg 
	 * @return json
	 */
	protected String renderJson(String ret,String msg){
		BaseResult	result = new BaseResult();
		result.setError(ret, msg);
		return JSON.toString(result);
	}
	
	/**
	 * 传递参数错误 
	 * @return json
	 */
	protected String renderParameterErrorJson(){
		BaseResult	result = new BaseResult();
		result.setParameterError();
		return JSON.toString(result);
	}
	/**
	 * 异常
	 * @return json
	 */
	protected String renderExceptionJson(){
		BaseResult	result = new BaseResult();
		result.setDefaultError();
		return JSON.toString(result);
	}
	
	
}
