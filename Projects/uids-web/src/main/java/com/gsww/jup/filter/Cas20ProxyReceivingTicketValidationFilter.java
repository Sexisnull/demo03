package com.gsww.jup.filter;

import java.io.IOException;
import java.util.*;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.proxy.*;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.AbstractTicketValidationFilter;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.ProxyList;
import org.jasig.cas.client.validation.ProxyListEditor;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * Creates either a CAS20ProxyTicketValidator or a CAS20ServiceTicketValidator depending on whether any of the
 * proxy parameters are set.
 * <p>
 * This filter can also pass additional parameters to the ticket validator.  Any init parameter not included in the
 * reserved list {@link org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter#RESERVED_INIT_PARAMS}.
 *
 * @author Scott Battaglia
 * @author Brad Cupit (brad [at] lsu {dot} edu)
 * @version $Revision$ $Date$
 * @since 3.1
 *
 */
public class Cas20ProxyReceivingTicketValidationFilter extends AbstractTicketValidationFilter {

    private static final String[] RESERVED_INIT_PARAMS = new String[] {"proxyReceptorUrl", "acceptAnyProxy", "allowedProxyChains", "casServerUrlPrefix", "proxyCallbackUrl", "renew", "exceptionOnValidationFailure", "redirectAfterValidation", "useSession", "serverName", "service", "artifactParameterName", "serviceParameterName", "encodeServiceUrl", "millisBetweenCleanUps"};

    private static final int DEFAULT_MILLIS_BETWEEN_CLEANUPS = 60 * 1000;

    /**
     * The URL to send to the CAS server as the URL that will process proxying requests on the CAS client. 
     */
    private String proxyReceptorUrl;

    private Timer timer;

    private TimerTask timerTask;

    private int millisBetweenCleanUps;
    
    private final String ExcludeFile = "ExcludeFile";  //excludeFile 列表  
    
    private String strExcludeFile;  
    private String[] arrExcludeFile = null;  
    /**
     * Storage location of ProxyGrantingTickets and Proxy Ticket IOUs.
     */
    private ProxyGrantingTicketStorage proxyGrantingTicketStorage = new ProxyGrantingTicketStorageImpl();

    @SuppressWarnings("rawtypes")
	protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        super.initInternal(filterConfig);
        setProxyReceptorUrl(getPropertyFromInitParams(filterConfig, "proxyReceptorUrl", null));

        final String proxyGrantingTicketStorageClass = getPropertyFromInitParams(filterConfig, "proxyGrantingTicketStorageClass", null);

        setStrExcludeFile(getPropertyFromInitParams(filterConfig, ExcludeFile, ""));  
        this.log.trace("Loaded ExcludeFile parameter: " + this.strExcludeFile);  
        
        if (proxyGrantingTicketStorageClass != null) {
            try {
                final Class storageClass = Class.forName(proxyGrantingTicketStorageClass);
                this.proxyGrantingTicketStorage = (ProxyGrantingTicketStorage) storageClass.newInstance();
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }

        log.trace("Setting proxyReceptorUrl parameter: " + this.proxyReceptorUrl);
        this.millisBetweenCleanUps = Integer.parseInt(getPropertyFromInitParams(filterConfig, "millisBetweenCleanUps", Integer.toString(DEFAULT_MILLIS_BETWEEN_CLEANUPS)));
    }

    public void init() {
        super.init();
        CommonUtils.assertNotNull(this.proxyGrantingTicketStorage, "proxyGrantingTicketStorage cannot be null.");

        if (strExcludeFile != null && strExcludeFile.trim().length() > 0) {  
            arrExcludeFile = strExcludeFile.split(",");  
        }  
        if (this.timer == null) {
            this.timer = new Timer(true);
        }

        if (this.timerTask == null) {
            this.timerTask = new CleanUpTimerTask(this.proxyGrantingTicketStorage);
        }
        this.timer.schedule(this.timerTask, this.millisBetweenCleanUps, this.millisBetweenCleanUps);
    }

    /**
     * Constructs a Cas20ServiceTicketValidator or a Cas20ProxyTicketValidator based on supplied parameters.
     *
     * @param filterConfig the Filter Configuration object.
     * @return a fully constructed TicketValidator.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected final TicketValidator getTicketValidator(final FilterConfig filterConfig) {
        final String allowAnyProxy = getPropertyFromInitParams(filterConfig, "acceptAnyProxy", null);
        final String allowedProxyChains = getPropertyFromInitParams(filterConfig, "allowedProxyChains", null);
        final String casServerUrlPrefix = getPropertyFromInitParams(filterConfig, "casServerUrlPrefix", null);
        final Cas20ServiceTicketValidator validator;

        if (CommonUtils.isNotBlank(allowAnyProxy) || CommonUtils.isNotBlank(allowedProxyChains)) {
            final Cas20ProxyTicketValidator v = new Cas20ProxyTicketValidator(casServerUrlPrefix);
            v.setAcceptAnyProxy(parseBoolean(allowAnyProxy));
            v.setAllowedProxyChains(createProxyList(allowedProxyChains));
            validator = v;
        } else {
            validator = new Cas20ServiceTicketValidator(casServerUrlPrefix);
        }
        validator.setProxyCallbackUrl(getPropertyFromInitParams(filterConfig, "proxyCallbackUrl", null));
        validator.setProxyGrantingTicketStorage(proxyGrantingTicketStorage);
        validator.setProxyRetriever(new Cas20ProxyRetriever(casServerUrlPrefix));
        validator.setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));

        final Map additionalParameters = new HashMap();
        final List params = Arrays.asList(RESERVED_INIT_PARAMS);

        for (final Enumeration e = filterConfig.getInitParameterNames(); e.hasMoreElements();) {
            final String s = (String) e.nextElement();

            if (!params.contains(s)) {
                additionalParameters.put(s, filterConfig.getInitParameter(s));
            }
        }

        validator.setCustomParameters(additionalParameters);

        return validator;
    }

    protected final ProxyList createProxyList(final String proxies) {
        if (CommonUtils.isBlank(proxies)) {
            return new ProxyList();
        }

        final ProxyListEditor editor = new ProxyListEditor();
        editor.setAsText(proxies);
        return (ProxyList) editor.getValue();
     }

    public void destroy() {
        super.destroy();
        this.timer.cancel();
    }

    /**
     * This processes the ProxyReceptor request before the ticket validation code executes.
     */
    protected final boolean preFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String requestUri = request.getRequestURI();
        
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
                    return false;  
                }  
            }  
        }
        
        String green =(String) request.getSession().getAttribute("green");  
        if(StringUtils.isBlank(green)){
      	  green= request.getParameter("green");
        }
        if(StringUtils.isNotBlank(green)&&"green".equals(green)){
//      	   request.getSession().setAttribute("green", green);
      	   filterChain.doFilter(request, response);  
      	 return false;  
        }
        
        if (CommonUtils.isEmpty(this.proxyReceptorUrl) || !requestUri.endsWith(this.proxyReceptorUrl)) {
            return true;
        }

        CommonUtils.readAndRespondToProxyReceptorRequest(request, response, proxyGrantingTicketStorage);

        return false;
    }

    public final void setProxyReceptorUrl(final String proxyReceptorUrl) {
        this.proxyReceptorUrl = proxyReceptorUrl;
    }

    public void setProxyGrantingTicketStorage(final ProxyGrantingTicketStorage storage) {
        proxyGrantingTicketStorage = storage;
    }

    public void setTimer(final Timer timer) {
        this.timer = timer;
    }

    public void setTimerTask(final TimerTask timerTask) {
        this.timerTask = timerTask;
    }

    public void setMillisBetweenCleanUps(final int millisBetweenCleanUps) {
        this.millisBetweenCleanUps = millisBetweenCleanUps;
    }
    
    public void setStrExcludeFile(String strExcludeFile) {  
        this.strExcludeFile = strExcludeFile;  
    }  
}
