package com.gsww.jup.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class CASAuthenticationFilter extends AbstractCasFilter {  
    private final String ExcludeFile = "ExcludeFile";  //excludeFile 列表  
    private String casServerLoginUrl;  
    private boolean renew;  
    private boolean gateway;  
    private GatewayResolver gatewayStorage;  
    private String strExcludeFile;  
    private String[] arrExcludeFile = null;  
    public CASAuthenticationFilter(){  
        this.renew = false;  
  
        this.gateway = false;  
  
        this.gatewayStorage = new DefaultGatewayResolverImpl();   
        setStrExcludeFile("");  
    }  
      
    protected void initInternal(FilterConfig filterConfig) throws ServletException {  
        if (!(isIgnoreInitConfiguration())) {  
            super.initInternal(filterConfig);  
            setCasServerLoginUrl(getPropertyFromInitParams(filterConfig, "casServerLoginUrl", null));  
            this.log.trace("Loaded CasServerLoginUrl parameter: " + this.casServerLoginUrl);  
            setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));  
            this.log.trace("Loaded renew parameter: " + this.renew);  
            setGateway(parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));  
            this.log.trace("Loaded gateway parameter: " + this.gateway);  
              
            setStrExcludeFile(getPropertyFromInitParams(filterConfig, ExcludeFile, ""));  
            this.log.trace("Loaded ExcludeFile parameter: " + this.strExcludeFile);  
              
            String gatewayStorageClass = getPropertyFromInitParams(filterConfig, "gatewayStorageClass", null);  
  
            if (gatewayStorageClass == null) return;  
            try {  
              this.gatewayStorage = ((GatewayResolver)Class.forName(gatewayStorageClass).newInstance());  
            } catch (Exception e) {  
              this.log.error(e, e);  
              throw new ServletException(e);  
            }  
          }  
    }  
    public void init()  
    {  
      super.init();  
      CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");  
      if (strExcludeFile != null && strExcludeFile.trim().length() > 0) {  
          arrExcludeFile = strExcludeFile.split(",");  
      }  
    }  
  
    @Override
    public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {  
      String modifiedServiceUrl;  
      HttpServletRequest request = (HttpServletRequest)servletRequest;  
      HttpServletResponse response = (HttpServletResponse)servletResponse;  
      HttpSession session = request.getSession(false);  
      Assertion assertion = (session != null) ? (Assertion)session.getAttribute("_const_cas_assertion_") : null;  
      String green =(String) request.getSession().getAttribute("green");  
      if(StringUtils.isBlank(green)){
    	  green= request.getParameter("green");
      }
      if(StringUtils.isNotBlank(green)&&"green".equals(green)){
    	   request.getSession().setAttribute("green", green);
    	   filterChain.doFilter(request, response);  
    	   return;  
      }
      if (assertion != null) {  
        filterChain.doFilter(request, response);  
        return;  
      }  
        
      
      String requestStr = request.getRequestURL().toString();  
      this.log.debug("requestStr-->"+requestStr);  
      PathMatcher matcher = new AntPathMatcher();  
      if(arrExcludeFile != null){  
          for(String excludePath : arrExcludeFile){  
              boolean flag = matcher.match(excludePath, requestStr);  
              if(!flag){  
                  flag = requestStr.indexOf(excludePath) > 0;  
              }  
              if(flag){  
                  this.log.debug("excludePath " + excludePath + " pass sso authentication");  
                  filterChain.doFilter(request, response);  
                  return;  
              }  
          }  
      }
        
  
      String serviceUrl = constructServiceUrl(request, response);  
      String ticket = CommonUtils.safeGetParameter(request, getArtifactParameterName());  
      boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);  
  
      if ((CommonUtils.isNotBlank(ticket)) || (wasGatewayed)) {  
        filterChain.doFilter(request, response);  
        return;  
      }  
  
      this.log.debug("no ticket and no assertion found");  
      if (this.gateway) {  
        this.log.debug("setting gateway attribute in session");  
        modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);  
      } else {  
        modifiedServiceUrl = serviceUrl;  
      }  
  
      if (this.log.isDebugEnabled()) {  
        this.log.debug("Constructed service url: " + modifiedServiceUrl);  
      }  
  
      String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);  
  
      if (this.log.isDebugEnabled()) {  
        this.log.debug("redirecting to \"" + urlToRedirectTo + "\"");  
      }  
  
      response.sendRedirect(urlToRedirectTo);  
    }  
  
    public final void setRenew(boolean renew) {  
      this.renew = renew;  
    }  
  
    public final void setGateway(boolean gateway) {  
      this.gateway = gateway;  
    }  
  
    public final void setCasServerLoginUrl(String casServerLoginUrl) {  
      this.casServerLoginUrl = casServerLoginUrl;  
    }  
  
    public final void setGatewayStorage(GatewayResolver gatewayStorage) {  
      this.gatewayStorage = gatewayStorage;  
    }  
  
    public void setStrExcludeFile(String strExcludeFile) {  
        this.strExcludeFile = strExcludeFile;  
    }  
}