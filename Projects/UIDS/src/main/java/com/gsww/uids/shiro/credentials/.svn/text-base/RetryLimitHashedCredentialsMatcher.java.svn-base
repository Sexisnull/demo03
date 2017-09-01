package com.gsww.uids.shiro.credentials;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import com.gsww.common.util.StringUtil;
import com.gsww.common.util.TimeHelper;
import com.gsww.uids.manager.sys.entity.SystemBasicParam;
import com.gsww.uids.manager.sys.service.SysConfigService;
import net.sf.json.JSONObject;

import java.text.ParseException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在身份验证之前，对密码输入错误次数的限制
 * 
 * @author simplife
 *
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 输错密码次数记录缓存
	 */
    private Cache<String, AtomicInteger> passwordRetryCache;
    
    /**
	 * 输错密码次数到达上限后的时间
	 */
    private Cache<String, String> loginErrorTimeRetryCache;

    /**
     * 构造函数：初始化缓存
     * @param cacheManager
     */
    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
        loginErrorTimeRetryCache = cacheManager.getCache("loginErrorTimeRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    	
    	// 获取输错密码的次数
    	// TODO 这里应当还有一个间隔时间问题
        String username = (String)token.getPrincipal();
        
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        
        SystemBasicParam con = sysConfigService.getSystemBasicParam();
        String currentTime = TimeHelper.getCurrentTime();

        if(retryCount.get() == con.getLoginError()){
        	loginErrorTimeRetryCache.put(username, currentTime);
        }
        retryCount.incrementAndGet();
        
        String loginErrorTime = loginErrorTimeRetryCache.get(username);
        
        int time = -1;
        int loginTime = -1;
        if(StringUtil.isNotBlank(loginErrorTime)){
        	try {
        		time = (int)TimeHelper.secondsBetween(loginErrorTime, currentTime)/60;
        	} catch (ParseException e1) {
        		System.out.println("计算时间差出错！！");
        	}
        
			loginTime = con.getBanTimes()-(int)time;
			
			if( loginTime >0){
				JSONObject json = new JSONObject();
				json.put("count", con.getLoginError());
				json.put("time", loginTime);
				throw new ExcessiveAttemptsException(json.toString());
			}else{
				loginErrorTimeRetryCache.remove(username);
				passwordRetryCache.remove(username);
			}
        }
		// 验证，如果通过，则清除记录
		 boolean matches = super.doCredentialsMatch(token, info);
	        if ( matches ) {
	            passwordRetryCache.remove(username);
	            loginErrorTimeRetryCache.remove(username);
	        }
        // 返回结果
        return matches;
    }
}
