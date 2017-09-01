package com.gsww.uids.system.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gsww.common.util.HttpUtil;
import com.gsww.common.util.MessageInfo;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.common.util.WebConstants;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.service.AccountService;
import com.gsww.uids.manager.app.entity.AppResource;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.app.service.ResourceService;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationGroup;
import com.gsww.uids.manager.org.service.OrganizationGroupService;
import com.gsww.uids.manager.org.service.OrganizationService;
import com.gsww.uids.manager.role.entity.Role;
import com.gsww.uids.manager.role.service.RoleService;
import com.gsww.uids.manager.sys.entity.Area;
import com.gsww.uids.manager.sys.entity.OperationLog;
import com.gsww.uids.manager.sys.service.AreaService;
import com.gsww.uids.manager.sys.service.LogService;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.service.UserService;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import net.sf.json.JSONObject;

/**
 * 日志记录类  
 * @author jinwei
 *
 */
@Aspect
@Component
public class SystemLogAspect {
	
	private static final Logger logger = Logger.getLogger(SystemLogAspect.class);
    
	@Autowired
    private LogService logService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private OrganizationGroupService organizationGroupService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private AreaService areaService;
	  
	//Controller层切点  
    @Pointcut("@annotation(com.gsww.uids.system.controller.SystemLog)")
    public void logControllerAspect() {
    }

    /** 
     * 前置通知 用于拦截Controller层记录用户的操作 
     * 
     * @param joinPoint 切点 
     */  
    @AfterReturning(value = "logControllerAspect()",argNames = "retVal",returning = "retVal")
    public void loginDoBefore(JoinPoint joinPoint, Object retVal) {
    	
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
        HttpSession session = request.getSession();
        
        //读取session中的账号id
        String accountid = (String) session.getAttribute(WebConstants.ONLINE_ACCOUNT_ID);
        
        try {  
        	 
        	Map<String, String> info = getControllerMethodDescription(joinPoint);
        	if( info !=null){
        		
        		if(StringUtil.isBlank(accountid)){
        			accountid = info.get(OperationLog.ACCOUNT_REGISTER);
                }
                Account account = accountService.findById(accountid);
                
                //请求的IP  
                String ip = HttpUtil.getIpAddress(request);
        		
        		String description = info.get("description");
        		        		
        		String module = info.get("module");
        		String actionType = info.get("actionType");
	            
        		//操作未成功不记录日志
            	JSONObject json = JSONObject.fromObject(retVal);
            	String str = json.get("state")+"";
            	if(str.indexOf("1") < 0){
            		return;
            	}
            	int state = (int)json.get("state");
        		if(state != MessageInfo.STATE_SUCCESS){
        			return;
        		}
	            //创建日志对象
	            OperationLog log = new OperationLog();
	            log.setLogDesc(account.getName() + description);//操作描述
	            log.setIp(ip);//操作IP
	            log.setModule(module);//模块名称
	            log.setTime(TimeHelper.getCurrentTime());//操作时间
	            if( account.getUser() != null ){
	            	log.setUserIdentity(account.getUser().getIdentity());//操作人身份证号
	            }
	            log.setAccountName(account.getName());//操作账号
	            log.setAppName(account.getApp().getName());//账号来源应用
	            if(account.getUser() != null){
	            	log.setUserName(account.getUser().getName());//操作人
	            }else{
	            	log.setUserName("无");
	            }
	            log.setType(actionType);//'操作类型'
	      
	            //保存
	            logService.saveOrUpdate(log);
        	}
        } catch (Exception e) {  
            //记录本地异常日志  
            logger.info("==前置通知异常==");
            logger.info("异常信息:"+e.getMessage());
        }  
    }
    
    /**
     * 获取注解中对方法的描述信息 用于Controller层注解 
     * @param joinPoint		切点
     * @return				描述，模块，操作
     * @throws Exception
     */
    public Map<String, String> getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
        Class targetClass = Class.forName(targetName);  
        Method[] methods = targetClass.getMethods();
        
        Map<String, String> result = null;
        
        String[] paramNames = getFieldsName(targetClass, targetName, methodName);
        
        
        for (Method method : methods) {  
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();  
                if (clazzs.length == arguments.length) {
                	result = new HashMap<String, String>();
                	String description = method.getAnnotation(SystemLog.class).description();
                	String module = method.getAnnotation(SystemLog.class).module();
                	String actionType = method.getAnnotation(SystemLog.class).actionType();
                	
                	if(OperationLog.ACCOUNT_REGISTER.equals(module)){
                		Account account = (Account)arguments[1];
                		result.put(OperationLog.ACCOUNT_REGISTER, account.getUuid());
                	}
                	
                	Map<String, String>map = getDescription(module, paramNames, arguments, actionType, description);
                	
                	result.put("description", map.get("description"));
                	result.put("module", module);
                	result.put("actionType", map.get("actionType"));
                    break;  
                }  
            }  
        }  
        return result;
    }

    /** 
     * 得到方法参数的名称 
     * @param cls 
     * @param clazzName 
     * @param methodName 
     * @return 
     * @throws NotFoundException 
     */  
    private static String[] getFieldsName(Class cls, String clazzName, String methodName) throws NotFoundException{  
        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());  
        ClassClassPath classPath = new ClassClassPath(cls);  
        pool.insertClassPath(classPath);  
          
        CtClass cc = pool.get(clazzName);  
        CtMethod cm = cc.getDeclaredMethod(methodName);  
        MethodInfo methodInfo = cm.getMethodInfo();  
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
        if (attr == null) {  
            // exception  
        }  
        String[] paramNames = new String[cm.getParameterTypes().length];  
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
        for (int i = 0; i < paramNames.length; i++){  
            paramNames[i] = attr.variableName(i + pos); //paramNames即参数名  
        }  
        return paramNames;  
    }
    
    /**
     * 根据参数名获取参数value
     * @param paramName		参数名
     * @param paramNames	所有参数名
     * @param arguments		参数值
     * @return
     */
    private static Object getValueFromMethod(String paramName, String[] paramNames, Object[] arguments){
    	Object obj = null;
    	for( int i = 0; i<paramNames.length; i++){
			if(paramName.equals(paramNames[i])){
				obj = arguments[i];
			}
		}
    	
    	return obj;
    }
    
    /**
     * 获取操作描述
     * @param module 		模块
     * @param paramNames	参数名
     * @param arguments		参数
     * @param description	描述
     * @return
     */
    private Map<String, String> getDescription(String module, String[] paramNames, Object[] arguments, String actionType, String description){
    	
    	Map<String, String> result = new HashMap<String, String>();
    	String detail = " ";
    	//机构描述
    	if(OperationLog.ORGANIZATION_MODULE.equals(module)){
    		Map<String, String> desc = getOrganizationDesc(paramNames, arguments, actionType, WebConstants.ORGANIZATION_PARAM, description);
    		detail += desc.get("detail");
    		actionType = desc.get("actionType");
    	}
    	//机构分组描述
    	else if(OperationLog.ORGANIZATION_GROUP_MODULE.equals(module)){
    		Map<String, String> desc = getOrganizationGroupDesc(paramNames, arguments, actionType, WebConstants.ORGANIZATION_GROUP_PARAM, description);
    		detail += desc.get("detail");
    		actionType = desc.get("actionType");
        }
    	//用户描述
    	else if(OperationLog.USER_MODULE.equals(module)){
    		Map<String, String> desc = getUserDesc(paramNames, arguments, actionType, WebConstants.USER_PARAM, description);
    		detail += desc.get("detail");
    		actionType = desc.get("actionType");
        }
    	//账号描述
    	else if(OperationLog.ACCOUNT_MODULE.equals(module)){
    		Map<String, String> desc = getAccountDesc(paramNames, arguments, actionType, WebConstants.ACCOUNT_PARAM, description);
    		detail += desc.get("detail");
    		actionType = desc.get("actionType");
        }
    	//角色描述
    	else if(OperationLog.ROLE_MODULE.equals(module)){
    		Map<String, String> desc = getRoleDesc(paramNames, arguments, actionType, WebConstants.ROLE_PARAM, description);
    		detail += desc.get("detail");
    		actionType = desc.get("actionType");
        }
    	//应用描述
    	else if(OperationLog.APP_MODULE.equals(module)){
    		Map<String, String> desc = getAppDesc(paramNames, arguments, actionType, WebConstants.APP_PARAM, description);
    		detail += desc.get("detail");
    		actionType = desc.get("actionType");
        }
    	//资源描述
    	else if(OperationLog.SOURCE_MODULE.equals(module)){
    		Map<String, String> desc = getAppSourceDesc(paramNames, arguments, actionType, WebConstants.APP_SOURCE_PARAM, description);
    		detail += desc.get("detail");
    		actionType = desc.get("actionType");
        }
    	//区域描述
    	else if(OperationLog.SYS_AREA_MODULE.equals(module)){
    		Map<String, String> desc = getAreaDesc(paramNames, arguments, actionType, WebConstants.AREA_PARAM, description);
    		detail += desc.get("detail");
    		actionType = desc.get("actionType");
        }
    	else{
    		// TODO 
    		detail += description;
    	}
    	
    	//其他
    	
    	result.put("description", detail);
    	result.put("actionType", actionType);
    	
    	return result;
    }
    
    /**
     * 获取机构操作描述
     * @param paramNames		参数名
     * @param arguments			参数
     * @param actionType		操作
     * @param actionParamName 	方法参数名称
     * @return
     */
    private Map<String, String> getOrganizationDesc(String[] paramNames, Object[] arguments, String actionType, String actionParamName, String moduleName){
    	
    	Map<String, String> result = new HashMap<String, String>();
    	
    	String detail = "";
    	
		List<Organization> infoList = new ArrayList<Organization>();
		//获取机构
		Organization info = (Organization) getValueFromMethod(actionParamName, paramNames, arguments);
		
		if(info != null){
			infoList.add(info);
		}
		//操作为删除是获取机构
		if(OperationLog.DELETE_TYPE.equals(actionType)){
			
			String ids = (String) getValueFromMethod("ids", paramNames, arguments);
			infoList = new ArrayList<Organization>();
			for (String id : ids.split(",")) {
				info = organizationService.findById(id);
				infoList.add(info);
			}
		}
		for(int i = 0; i<infoList.size(); i++){
			if(i>0){
				detail += ",";
			}
			
    		String action = "";
			if(OperationLog.INSERT_UPDATE.endsWith(actionType)){
				String operate = (String) getValueFromMethod(WebConstants.OPERATE_NAME, paramNames, arguments);
				if( OperationLog.INSERT_TYPE.equals(operate) ){
					action = "新增";
					actionType = OperationLog.INSERT_TYPE;
				}else{
					action = "更新";
					actionType = OperationLog.UPDATE_TYPE;
				}
			}
			if( OperationLog.DELETE_TYPE.equals(actionType) ){
				action = "删除";
			}
			detail += action + moduleName +"【"+infoList.get(i).getShortName()+"】";
		}
		result.put("detail", detail);
		result.put("actionType", actionType);
		return result;
    }
   /**
    * 获取机构分组操作描述
    * @param paramNames
    * @param arguments
    * @param actionType
    * @param actionParamName
    * @return
    */
   private Map<String, String> getOrganizationGroupDesc(String[] paramNames, Object[] arguments, String actionType, String actionParamName, String moduleName){
	   Map<String, String> result = new HashMap<String, String>();
	   
    	String detail = "";
    	
		List<OrganizationGroup> infoList = new ArrayList<OrganizationGroup>();
		//获取机构分组
		OrganizationGroup info = (OrganizationGroup) getValueFromMethod(actionParamName, paramNames, arguments);
		
		if(info != null){
			infoList.add(info);
		}
		//操作为删除时获取机构
		if(OperationLog.DELETE_TYPE.equals(actionType)){
			
			String ids = (String) getValueFromMethod("ids", paramNames, arguments);
			infoList = new ArrayList<OrganizationGroup>();
			for (String id : ids.split(",")) {
				info = organizationGroupService.findById(id);
				infoList.add(info);
			}
		}
		for(int i = 0; i<infoList.size(); i++){
			if(i>0){
				detail += ",";
			}
			
    		String action = "";
			if(OperationLog.INSERT_UPDATE.endsWith(actionType)){
				String operate = (String) getValueFromMethod(WebConstants.OPERATE_NAME, paramNames, arguments);
				if( OperationLog.INSERT_TYPE.equals(operate) ){
					action = "新增";
					actionType = OperationLog.INSERT_TYPE;
				}else{
					action = "更新";
					actionType = OperationLog.UPDATE_TYPE;
				}
			}
			if( OperationLog.DELETE_TYPE.equals(actionType) ){
				action = "删除";
			}
			detail += action + moduleName +"【"+infoList.get(i).getName()+"】";
		}
		result.put("detail", detail);
		result.put("actionType", actionType);
		return result;
    }
   
   /**
    * 获取用户操作描述
    * @param paramNames
    * @param arguments
    * @param actionType
    * @param actionParamName
    * @return
    */
   private Map<String, String> getUserDesc(String[] paramNames, Object[] arguments, String actionType, String actionParamName, String moduleName){
    	
	   	Map<String, String> result = new HashMap<String, String>();
	   
    	String detail = "";
    	
		List<User> infoList = new ArrayList<User>();
		//获取机构分组
		User info = (User) getValueFromMethod(actionParamName, paramNames, arguments);
		
		if(info != null){
			infoList.add(info);
		}
		//操作为删除时获取机构
		if(OperationLog.DELETE_TYPE.equals(actionType)){
			
			String ids = (String) getValueFromMethod("ids", paramNames, arguments);
			infoList = new ArrayList<User>();
			for (String id : ids.split(",")) {
				info = userService.findById(id);
				infoList.add(info);
			}
		}
		for(int i = 0; i<infoList.size(); i++){
			if(i>0){
				detail += ",";
			}
    		String action = "";
			if(OperationLog.INSERT_UPDATE.endsWith(actionType)){
				String operate = (String) getValueFromMethod(WebConstants.OPERATE_NAME, paramNames, arguments);
				if( OperationLog.INSERT_TYPE.equals(operate) ){
					action = "新增";
					actionType = OperationLog.INSERT_TYPE;
				}else{
					action = "更新";
					actionType = OperationLog.UPDATE_TYPE;
				}
			}
			if( OperationLog.DELETE_TYPE.equals(actionType) ){
				action = "删除";
			}
			detail += action + moduleName +"【"+infoList.get(i).getName()+"】";
		}
		result.put("detail", detail);
		result.put("actionType", actionType);
		return result;
    }
   
   /**
    * 获取账号操作描述
    * @param paramNames		参数名
    * @param arguments			参数
    * @param actionType		操作
    * @param actionParamName 	方法参数名称
    * @return
    */
   private Map<String, String> getAccountDesc(String[] paramNames, Object[] arguments, String actionType, String actionParamName, String moduleName){
   	
   	Map<String, String> result = new HashMap<String, String>();
   	
   	String detail = "";
   	
		List<Account> infoList = new ArrayList<Account>();
		//获取机构
		Account info = (Account) getValueFromMethod(actionParamName, paramNames, arguments);
		
		if(info != null){
			infoList.add(info);
		}
		//操作为删除是获取机构
		if(OperationLog.DELETE_TYPE.equals(actionType)){
			
			String ids = (String) getValueFromMethod("ids", paramNames, arguments);
			infoList = new ArrayList<Account>();
			for (String id : ids.split(",")) {
				info = accountService.findById(id);
				infoList.add(info);
			}
		}
		for(int i = 0; i<infoList.size(); i++){
			if(i>0){
				detail += ",";
			}
			
   		String action = "";
			if(OperationLog.INSERT_UPDATE.endsWith(actionType)){
				String operate = (String) getValueFromMethod(WebConstants.OPERATE_NAME, paramNames, arguments);
				if( OperationLog.INSERT_TYPE.equals(operate) ){
					action = "新增";
					actionType = OperationLog.INSERT_TYPE;
				}else{
					action = "更新";
					actionType = OperationLog.UPDATE_TYPE;
				}
			}
			if( OperationLog.DELETE_TYPE.equals(actionType) ){
				action = "删除";
			}
			detail += action + moduleName +"【"+infoList.get(i).getName()+"】";
		}
		result.put("detail", detail);
		result.put("actionType", actionType);
		return result;
   }
   
   /**
    * 获取角色操作描述
    * @param paramNames		参数名
    * @param arguments			参数
    * @param actionType		操作
    * @param actionParamName 	方法参数名称
    * @return
    */
   private Map<String, String> getRoleDesc(String[] paramNames, Object[] arguments, String actionType, String actionParamName, String moduleName){
   	
   	Map<String, String> result = new HashMap<String, String>();
   	
   	String detail = "";
   	
		List<Role> infoList = new ArrayList<Role>();
		//获取机构
		Role info = (Role) getValueFromMethod(actionParamName, paramNames, arguments);
		
		if(info != null){
			infoList.add(info);
		}
		//操作为删除是获取机构
		if(OperationLog.DELETE_TYPE.equals(actionType)){
			
			String ids = (String) getValueFromMethod("ids", paramNames, arguments);
			infoList = new ArrayList<Role>();
			for (String id : ids.split(",")) {
				info = roleService.findById(id);
				infoList.add(info);
			}
		}
		for(int i = 0; i<infoList.size(); i++){
			if(i>0){
				detail += ",";
			}
			
   		String action = "";
			if(OperationLog.INSERT_UPDATE.endsWith(actionType)){
				String operate = (String) getValueFromMethod(WebConstants.OPERATE_NAME, paramNames, arguments);
				if( OperationLog.INSERT_TYPE.equals(operate) ){
					action = "新增";
					actionType = OperationLog.INSERT_TYPE;
				}else{
					action = "更新";
					actionType = OperationLog.UPDATE_TYPE;
				}
			}
			if( OperationLog.DELETE_TYPE.equals(actionType) ){
				action = "删除";
			}
			detail += action + moduleName +"【"+infoList.get(i).getName()+"】";
		}
		result.put("detail", detail);
		result.put("actionType", actionType);
		return result;
   }
   /**
    * 获取资源操作描述
    * @param paramNames		参数名
    * @param arguments			参数
    * @param actionType		操作
    * @param actionParamName 	方法参数名称
    * @return
    */
   private Map<String, String> getAppSourceDesc(String[] paramNames, Object[] arguments, String actionType, String actionParamName, String moduleName){
   	
   	Map<String, String> result = new HashMap<String, String>();
   	
   	String detail = "";
   	
		List<AppResource> infoList = new ArrayList<AppResource>();
		//获取机构
		AppResource info = (AppResource) getValueFromMethod(actionParamName, paramNames, arguments);
		
		if(info != null){
			infoList.add(info);
		}
		//操作为删除是获取机构
		if(OperationLog.DELETE_TYPE.equals(actionType)){
			
			String ids = (String) getValueFromMethod("ids", paramNames, arguments);
			infoList = new ArrayList<AppResource>();
			for (String id : ids.split(",")) {
				info = resourceService.findById(id);
				infoList.add(info);
			}
		}
		for(int i = 0; i<infoList.size(); i++){
			if(i>0){
				detail += ",";
			}
			
   		String action = "";
			if(OperationLog.INSERT_UPDATE.endsWith(actionType)){
				String operate = (String) getValueFromMethod(WebConstants.OPERATE_NAME, paramNames, arguments);
				if( OperationLog.INSERT_TYPE.equals(operate) ){
					action = "新增";
					actionType = OperationLog.INSERT_TYPE;
				}else{
					action = "更新";
					actionType = OperationLog.UPDATE_TYPE;
				}
			}
			if( OperationLog.DELETE_TYPE.equals(actionType) ){
				action = "删除";
			}
			detail += action + moduleName +"【"+infoList.get(i).getName()+"】";
		}
		result.put("detail", detail);
		result.put("actionType", actionType);
		return result;
   }
   /**
    * 获取应用操作描述
    * @param paramNames		参数名
    * @param arguments			参数
    * @param actionType		操作
    * @param actionParamName 	方法参数名称
    * @return
    */
   private Map<String, String> getAppDesc(String[] paramNames, Object[] arguments, String actionType, String actionParamName, String moduleName){
   	
   	Map<String, String> result = new HashMap<String, String>();
   	
   	String detail = "";
   	
		List<Application> infoList = new ArrayList<Application>();
		//获取机构
		Application info = (Application) getValueFromMethod(actionParamName, paramNames, arguments);
		
		if(info != null){
			infoList.add(info);
		}
		//操作为删除是获取机构
		if(OperationLog.DELETE_TYPE.equals(actionType)){
			
			String ids = (String) getValueFromMethod("ids", paramNames, arguments);
			infoList = new ArrayList<Application>();
			for (String id : ids.split(",")) {
				info = applicationService.findById(id);
				infoList.add(info);
			}
		}
		for(int i = 0; i<infoList.size(); i++){
			if(i>0){
				detail += ",";
			}
			
   		String action = "";
			if(OperationLog.INSERT_UPDATE.endsWith(actionType)){
				String operate = (String) getValueFromMethod(WebConstants.OPERATE_NAME, paramNames, arguments);
				if( OperationLog.INSERT_TYPE.equals(operate) ){
					action = "新增";
					actionType = OperationLog.INSERT_TYPE;
				}else{
					action = "更新";
					actionType = OperationLog.UPDATE_TYPE;
				}
			}
			if( OperationLog.DELETE_TYPE.equals(actionType) ){
				action = "删除";
			}
			detail += action + moduleName +"【"+infoList.get(i).getName()+"】";
		}
		result.put("detail", detail);
		result.put("actionType", actionType);
		return result;
   }
   /**
    * 获取区域操作描述
    * @param paramNames			参数名
    * @param arguments			参数
    * @param actionType			操作
    * @param actionParamName 	方法参数名称
    * @return
    */
   private Map<String, String> getAreaDesc(String[] paramNames, Object[] arguments, String actionType, String actionParamName, String moduleName){
   	
   	Map<String, String> result = new HashMap<String, String>();
   	
   	String detail = "";
   	
		List<Area> infoList = new ArrayList<Area>();
		//获取机构
		Area info = (Area) getValueFromMethod(actionParamName, paramNames, arguments);
		
		if(info != null){
			infoList.add(info);
		}
		//操作为删除是获取机构
		if(OperationLog.DELETE_TYPE.equals(actionType)){
			
			String ids = (String) getValueFromMethod("ids", paramNames, arguments);
			infoList = new ArrayList<Area>();
			for (String id : ids.split(",")) {
				info = areaService.findById(id);
				infoList.add(info);
			}
		}
		for(int i = 0; i<infoList.size(); i++){
			if(i>0){
				detail += ",";
			}
			
   		String action = "";
			if(OperationLog.INSERT_UPDATE.endsWith(actionType)){
				String operate = (String) getValueFromMethod(WebConstants.OPERATE_NAME, paramNames, arguments);
				if( OperationLog.INSERT_TYPE.equals(operate) ){
					action = "新增";
					actionType = OperationLog.INSERT_TYPE;
				}else{
					action = "更新";
					actionType = OperationLog.UPDATE_TYPE;
				}
			}
			if( OperationLog.DELETE_TYPE.equals(actionType) ){
				action = "删除";
			}
			detail += action + moduleName +"【"+infoList.get(i).getName()+"】";
		}
		result.put("detail", detail);
		result.put("actionType", actionType);
		return result;
   }
}  
