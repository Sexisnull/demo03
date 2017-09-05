<#-- 定义变量且赋值 -->
<#assign className = table.className>
<#-- 小写类名 -->
<#assign classNameLower = className?uncap_first>
<#-- 取包名 -->
package ${basepackage}.action;

import com.gsww.jup.action.CrudActionSupport;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import com.gsww.jup.util.*;
import ${basepackage}.entity.${className};
import ${basepackage}.service.${className}Service;

/**
 * <p><strong>公司&#9;:&#9;</strong>中国电信甘肃万维公司</p>
 * <p><strong>项目&#9;:&#9;</strong>JUP</p>
 * <p><strong>类描述&#9;:&#9;</strong>
 * @Namespace("xxx"): jsp目录=WEB-INFO/content/xxx,若xxx="/"则表示路径为content目录下
 * @Action("xxx"): action名字,xxx可自由指定
 * </p>
 * @version 1.0.0
 * @author 2369
 */
@Namespace("/")
@Action("${classNameLower}")
@Results({
	 @Result(name="view",location="${classNameLower}/view.jsp"),
	 @Result(name="input",location="${classNameLower}/edit.jsp"),
	 @Result(name="list",location="${classNameLower}/list.jsp"),
	 @Result(name="success",location="${classNameLower}.do",type="redirect")})
public class ${className}Action extends CrudActionSupport<${className}> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ${className}Service ${classNameLower}Service;
	private ${className} entity;
	private ${table.idColumn.javaType}[] ids;
	
	/**
	 * 分页action
	 */
	@Override
	public String list() {
		try{
			//绑定PropertyFilter查询条件,规则请参考PropertyFilter类实现
			List<PropertyFilter> filters = HibernateUtils.buildPropertyFilters(getRequest());
			//filters.add(new PropertyFilter("EQS_setId",user.getSetId()));
			//设置默认排序
			Page<${className}> page = this.getPage();
			page.setOrder(Page.ASC);
			//查询分页并临时保存到response的Attribute,供jsp页面的el表达式取用
			savePage(${classNameLower}Service.findPage(page, filters));
		}catch(Exception e){
			logger.error("${className} list failer, 原因:{}", e.getMessage());
			saveMessage(e.getMessage());
		}
		return LIST;
	}
	
	/**
	 * 新增和编辑action
	 */
	@Override
	public String input() {
		try{
			prepareModel();
		}catch(Exception e){
			logger.error("${className} input action failer, 原因:{}", e.getMessage());
			saveMessage(e.getMessage());
		}
		return INPUT;
	}
	
	/**
	 * 查看action
	 */
	public String view() {
		try{
			prepareModel();
		}catch(Exception e){
			logger.error("${className} view action failer, 原因:{}", e.getMessage());
			saveMessage(e.getMessage());
		}
		return "view";
	}
	
	/**
	 * 保存action
	 */
	@Override
	public String save() {
		try{
			${classNameLower}Service.save(entity);
		}catch(Exception e){
			logger.error("${className} save action failer, 原因:{}", e.getMessage());
			saveMessage(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 删除action
	 */
	@Override
	public String delete() {
		try{
			String ids[] = getRequest().getParameterValues("ids");
			for (String id : ids) {
				${classNameLower}Service.delete(id);
			}
		}catch(Exception e){
			logger.error("${className} delete action failer, 原因:{}", e.getMessage());
			saveMessage(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 初始化entity
	 */
	@Override
	protected void prepareModel() throws Exception {
		if (id == null) {
			entity = new ${className}();
		} else {
			entity = ${classNameLower}Service.findByKey(id);
		}
	}
	
	@Override
	public ${className} getModel() {
		return entity;
	}
	
	public ${className} getEntity(){
		return this.entity;
	}
	
	public void setEntity(${className} entity){
		this.entity = entity;
	}
	
	public ${table.idColumn.javaType}[] getIds(){
		return this.ids;
	}
	
	public void setIds(${table.idColumn.javaType}[] ids){
		this.ids = ids;
	}
}
