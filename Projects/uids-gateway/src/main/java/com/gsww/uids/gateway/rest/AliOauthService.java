package com.gsww.uids.gateway.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.request.ZhimaCreditAntifraudVerifyRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.alipay.api.response.ZhimaCreditAntifraudVerifyResponse;
import com.gsww.uids.gateway.dao.outsideuser.OutsideUserDao;
import com.gsww.uids.gateway.entity.OutsideUser;
import com.gsww.uids.gateway.util.SpringContextHolder;

@Path("/uids-web")
public class AliOauthService {

	private OutsideUserDao outsideUserDao = SpringContextHolder.getBean("outsideUserDao");;

	protected Logger logger = Logger.getLogger(AliOauthService.class);

	private static String ALI_URL = "https://openapi.alipaydev.com/gateway.do";
	private static String APP_ID = "2016080500171059";
	private static String CHARSET = "GBK";
	private static String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDWaS71UfDyuXsKbjn5jFr6lm9ja7r7U0Ae1j3xU5+RyAS9gHnsxDVUIOiNGHuBhNJ78H9p2MVB5AEKsRhTAkCKzcvocVvYJaNljkISndNM1BoZD2M9NFSndI6FduiYrB3RXbnM6XThTezpWHCOG6tsgWb5C7YXzWsSPbr7bwUv+uHwu2zvc2AvjoIEnOS1ZDpy945i7rsfsmLU+NCitAu0O2XzW6D/FgMOEZ/gUPbl2kUhCqxsetorKikaIuO3ojOE+zP8oXEPAnlj48zEvXka30drX6xp+KurPYLkE3yXGkkUZUohuFHUP6urTKfantchp4vG8QevId16dRz5Iso5AgMBAAECggEACjhxWJhTV/6nctPWR6L9IzzQini0LQ7G27FyunI2BQj30OCy7ypbMGtxKmikWoQuVGIecLk4je+EbTIL6skMspEkyyu8KQ2CQHELjT+gtuTVaaRmIqC/+EuCD7KfW8e4lCZXmQD35VWFmYnxs5R2E3IHqo94WqIcHH58z0d3g9XnW8YdDcBM0PjgY9Upi7CZ7L2j1+FIeFoZRndkaa2ngJ2Ok2lZmNB1OBHCYL4EOgJXHXDpt3PXc9amL55tcIUocm4bawoB8sGK8d/9qbydOONBHxaKnyj+qbN3FlwsBaSIjH/mDydGrjkW0CNUIdR6BWWCbEQ/iswzd4+wU4hfkQKBgQD0yQB2P5o49475zgBsnGNM7neKb1Q7uiO2EFb8jirfqL39t1BZ67/DhX+uCVTlPNTDD7Bl9a+vISi5sWw7sx9Jm6B1yIrbO1UBUg96ntIyLSiuNu9NTPwo7XflxoOUCEdCEsphths5BMS9FO4V255rSbNvgGMbp3ZPUDNofP67BwKBgQDgO+6vkZTDd8cKnRSnOpbrxMgUi0aOV0CHWrPyzeSG+6tdArASH/O+/6PFtiyAcw3wOK89lXpLhBjeq4gGLZlznW1Gd9KnIwsTfMZDlYHQzY0iBueLjXKxENra6tDxQmepgL+85xRCpOMVKlpJdfALUkF7g/WEhzMSnBEzurDAvwKBgQCz0UBMnVZeiMT4DvNS1eNAbWFVSYkYQxnescwkxQ8Ls/q1ecdF9x+sstHjeClsKK6nCExt6fh/7xzqpEI87M2MFg3e2E3g1IoSaUTDsA37HB9pMyPBpk8Khb9xBM49nYMzL3iKJOuEjFM2Dz0Cw41xhPeSbj7f3rnTc7gABupdWwKBgQDR+g1nRxJhgHZZEANZHdpZ6ana4xktDbOVjHBZ/Ef1xxIPRQcP0e/0eXspF5DQr+zreIlRR/p/YLHRQhtcfbLmuxKrHGWcsYobs4oNm6E2oGV66bBF1C0Edl4bBiym36Im7jOed11XkwQ6u7BUfiZM07gSK93rPpeq446QPFBsDQKBgQCuBscwn/VJk0GQqLT280yvM0ejuST5nUMNZiKNFKX8Xpd5dbNZbAy20YYCEVRguPjxisXx07p4tbQkC3Bt+KBmK3CAI5Q1nsTkvhwcBKlvS+RvEbbXTio6t9Vbe4YtQLCrN/5Vzhak4OGv+zErQKjQ13ZNY3kOvFlVQoPQ/YMqdQ==";
	private static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvQ3PULEX5K0qs/dKBZGYgbf84vx9HM8ZHP/jtUqC5QybkZT8LtcjVBCiZt1v8RBq3z891EPU9ral0OUSIn8VD5DWk5NXyqMh+nVqYvb7Mv2peT8IBtNT1wBEIpXnwXSC29AVkNAD3mVeLBECYrdEt1fY4ePJgI2Kv6ovTj1poOp52etwahpW+Qv2Eb0ZnPDFpj+ktvKzjr5T2VH6IOy5fNDY9KdCIiEFtMM7GJ4wHKUBBz59ERHQfe2N+Lua7Dc72hdKal9PY06idrRQOHrRyug/nc4/lvSlsusx2UkwEq5gH43YZ9MUvDlz1P1DKYks3E6JOHvve07+1LFrwLDbcwIDAQAB";
	/**用于生成TransactionId的自增器*/
    private static AtomicLong transAutoIncIdx   = new AtomicLong(1000000000000l);
    
    /**
     * AliOauth接口（只用于登陆）
     * @param appId
     * @param authCode
     * @return
     */
	@GET
	@Path("/ali/login")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public boolean findUserId(@QueryParam("app_id") String appId, @QueryParam("auth_code") String authCode) {
		OutsideUser outsideUser = new OutsideUser();
		logger.info("<AliOauth接口>接收到请求内容:" + appId);
		// 标志
		boolean flag = false;
		if (!appId.isEmpty()) {
			AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL, appId,
					APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
			AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
			request.setCode(authCode);
			request.setGrantType("authorization_code");
			try {
				// 获取userId
				AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
				System.out.println(oauthTokenResponse.getAccessToken());
				if (oauthTokenResponse != null) {
					String accessToken = oauthTokenResponse.getAccessToken();
					String aliUserId = oauthTokenResponse.getUserId();
					outsideUser = outsideUserDao.findByUserId(accessToken);
					if(outsideUser != null){
						// 登陆成功
						flag = true;
						// TODO(返回个人、基本信息)
					}
				}
				// 获取用户信息
				AlipayUserInfoShareRequest userInfoShareRequest = new AlipayUserInfoShareRequest();
				AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(userInfoShareRequest,
						oauthTokenResponse.getAccessToken());
				System.out.println(userinfoShareResponse.getBody());
			} catch (AlipayApiException e) {
				// 处理异常
				logger.error("<AliOauth接口>异常", e);
			}
		}
		return flag;
	}

	/**
	 * 绑定接口
	 * @param aliUserId
	 * @param userId
	 * @return
	 */
	@GET
	@Path("/ali/merge")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public boolean mergeUserId(@QueryParam("aliUserId") String aliUserId, @QueryParam("userId") String userId) {
		logger.info("<merge接口>接收到请求内容:" + aliUserId);
		// 标志
		boolean flag = false;
		if (!aliUserId.isEmpty() && !userId.isEmpty()) {
			// 绑定个人用户与aliUserId
			int a = outsideUserDao.saveAliUserId(aliUserId, userId);
			return a>0?true:false;
		} else {
			return flag;
		}
	}
	
	/**
	 * 实名认证接口
	 * @param userName
	 * @param certNo
	 * @return
	 */
	@GET
	@Path("/ali/verify")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public boolean verifyCert(@QueryParam("userName") String userName, @QueryParam("certNo") String certNo) {
		logger.info("<merge接口>接收到请求内容:" + certNo);
		// 标志
		boolean flag = false;
        String transactionId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + transAutoIncIdx.getAndDecrement();
        AlipayClient alipayClient = new DefaultAlipayClient(ALI_URL,APP_ID,APP_PRIVATE_KEY,"json",CHARSET,ALIPAY_PUBLIC_KEY,"RSA2");
        ZhimaCreditAntifraudVerifyRequest request = new ZhimaCreditAntifraudVerifyRequest();
        request.setBizContent("{" +
                "\"product_code\":\"w1010100000000002859\"," +
                "\"transaction_id\":\"" + transactionId +"\"," +
                "\"cert_no\":\"" + certNo +"\"," +
                "\"cert_type\":\"IDENTITY_CARD\"," +
                "\"name\":\"" + userName +"\"," +
                "  }");
        ZhimaCreditAntifraudVerifyResponse response;
		try {
			response = alipayClient.execute(request);
			if (response.isSuccess()) {
	            logger.info("调用成功");
	            // 建议记录bizNo用于核对
	            logger.info("欺诈信息验证验证码列表为=" + response.getVerifyCode() + ",bizNo="
	                    + response.getBizNo());
	        } else {
	        	logger.info("调用失败");
	            // 处理各种异常 异常时请记录transactionId排查问题
	        	logger.info("查询芝麻信用-欺诈信息验证的错误 code=" + response.getCode() + ",msg="
	                    + response.getMsg() + ",transactionId=" + transactionId);
	        }
		} catch (AlipayApiException e) {
			// 处理异常
			logger.error("<verify接口>异常", e);
		}
        return flag;
	}

}
