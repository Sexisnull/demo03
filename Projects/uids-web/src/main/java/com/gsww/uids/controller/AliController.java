package com.gsww.uids.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.gsww.jup.controller.BaseController;
import com.gsww.jup.util.StringHelper;
import net.sf.json.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * AliController
 * com.gsww.uids.controller
 *
 * @author xiaoyy
 * 支付宝登陆controller
 * @Date 2017-09-13 上午9:13
 * The word 'impossible' is not in my dictionary.
 */
@Controller
@RequestMapping(value = "/ali")
public class AliController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AliController.class);
    private static String APP_ID = "2016080500171059";
    private static String CHARSET = "GBK";
    private static String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDWaS71UfDyuXsKbjn5jFr6lm9ja7r7U0Ae1j3xU5+RyAS9gHnsxDVUIOiNGHuBhNJ78H9p2MVB5AEKsRhTAkCKzcvocVvYJaNljkISndNM1BoZD2M9NFSndI6FduiYrB3RXbnM6XThTezpWHCOG6tsgWb5C7YXzWsSPbr7bwUv+uHwu2zvc2AvjoIEnOS1ZDpy945i7rsfsmLU+NCitAu0O2XzW6D/FgMOEZ/gUPbl2kUhCqxsetorKikaIuO3ojOE+zP8oXEPAnlj48zEvXka30drX6xp+KurPYLkE3yXGkkUZUohuFHUP6urTKfantchp4vG8QevId16dRz5Iso5AgMBAAECggEACjhxWJhTV/6nctPWR6L9IzzQini0LQ7G27FyunI2BQj30OCy7ypbMGtxKmikWoQuVGIecLk4je+EbTIL6skMspEkyyu8KQ2CQHELjT+gtuTVaaRmIqC/+EuCD7KfW8e4lCZXmQD35VWFmYnxs5R2E3IHqo94WqIcHH58z0d3g9XnW8YdDcBM0PjgY9Upi7CZ7L2j1+FIeFoZRndkaa2ngJ2Ok2lZmNB1OBHCYL4EOgJXHXDpt3PXc9amL55tcIUocm4bawoB8sGK8d/9qbydOONBHxaKnyj+qbN3FlwsBaSIjH/mDydGrjkW0CNUIdR6BWWCbEQ/iswzd4+wU4hfkQKBgQD0yQB2P5o49475zgBsnGNM7neKb1Q7uiO2EFb8jirfqL39t1BZ67/DhX+uCVTlPNTDD7Bl9a+vISi5sWw7sx9Jm6B1yIrbO1UBUg96ntIyLSiuNu9NTPwo7XflxoOUCEdCEsphths5BMS9FO4V255rSbNvgGMbp3ZPUDNofP67BwKBgQDgO+6vkZTDd8cKnRSnOpbrxMgUi0aOV0CHWrPyzeSG+6tdArASH/O+/6PFtiyAcw3wOK89lXpLhBjeq4gGLZlznW1Gd9KnIwsTfMZDlYHQzY0iBueLjXKxENra6tDxQmepgL+85xRCpOMVKlpJdfALUkF7g/WEhzMSnBEzurDAvwKBgQCz0UBMnVZeiMT4DvNS1eNAbWFVSYkYQxnescwkxQ8Ls/q1ecdF9x+sstHjeClsKK6nCExt6fh/7xzqpEI87M2MFg3e2E3g1IoSaUTDsA37HB9pMyPBpk8Khb9xBM49nYMzL3iKJOuEjFM2Dz0Cw41xhPeSbj7f3rnTc7gABupdWwKBgQDR+g1nRxJhgHZZEANZHdpZ6ana4xktDbOVjHBZ/Ef1xxIPRQcP0e/0eXspF5DQr+zreIlRR/p/YLHRQhtcfbLmuxKrHGWcsYobs4oNm6E2oGV66bBF1C0Edl4bBiym36Im7jOed11XkwQ6u7BUfiZM07gSK93rPpeq446QPFBsDQKBgQCuBscwn/VJk0GQqLT280yvM0ejuST5nUMNZiKNFKX8Xpd5dbNZbAy20YYCEVRguPjxisXx07p4tbQkC3Bt+KBmK3CAI5Q1nsTkvhwcBKlvS+RvEbbXTio6t9Vbe4YtQLCrN/5Vzhak4OGv+zErQKjQ13ZNY3kOvFlVQoPQ/YMqdQ==";
    private static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvQ3PULEX5K0qs/dKBZGYgbf84vx9HM8ZHP/jtUqC5QybkZT8LtcjVBCiZt1v8RBq3z891EPU9ral0OUSIn8VD5DWk5NXyqMh+nVqYvb7Mv2peT8IBtNT1wBEIpXnwXSC29AVkNAD3mVeLBECYrdEt1fY4ePJgI2Kv6ovTj1poOp52etwahpW+Qv2Eb0ZnPDFpj+ktvKzjr5T2VH6IOy5fNDY9KdCIiEFtMM7GJ4wHKUBBz59ERHQfe2N+Lua7Dc72hdKal9PY06idrRQOHrRyug/nc4/lvSlsusx2UkwEq5gH43YZ9MUvDlz1P1DKYks3E6JOHvve07+1LFrwLDbcwIDAQAB";


    /**
     * 第三方授权
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gateway", method = RequestMethod.GET)
    public ModelAndView gateway(String app_id, String app_auth_code, Model model)  throws Exception {
        ModelAndView mav=new ModelAndView("system/ali/login");
        try {
            System.out.println("app_id=" + app_id);
            System.out.println("app_auth_code=" + app_auth_code);
            if(StringHelper.isNotBlack(app_id)){
                AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", app_id, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
                AlipayOpenAuthTokenAppRequest aliRequest = new AlipayOpenAuthTokenAppRequest();
                aliRequest.setBizContent("{" +
                        "    \"grant_type\":\"authorization_code\"," +
                        "    \"code\":\""+app_auth_code+"\"" +
                        "  }");
                AlipayOpenAuthTokenAppResponse aliResponse = alipayClient.execute(aliRequest);
                // appAuthToken time 365days
                //"appAuthToken": "201709BBa4d3d61996ad4a87b185f27774108X89"
                String appAuthToken = JSONSerializer.toJSON(aliResponse).toString();

                System.out.println(JSONSerializer.toJSON(aliResponse).toString());
            }else{

            }
            model.addAttribute("corporation","a");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return mav;
    }


    /**
     * 新版获取用户信息授权
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/appAuthorize", method = RequestMethod.GET)
    public ModelAndView appAuthorize(String app_id, String auth_code, Model model)  throws Exception {
        ModelAndView mav=new ModelAndView("system/ali/login");
        try {
            System.out.println("app_id=" + app_id);
            System.out.println("auth_code=" + auth_code);
            AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
            AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
            request.setCode(auth_code);
            request.setGrantType("authorization_code");
                AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
                System.out.println(oauthTokenResponse.getAccessToken());
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        return mav;
    }

//    //
//    public void main(String[] args) {
//    }
//
//    // 通过appAuthToken获取用户信息
//    public AlipayOpenAuthTokenAppQueryResponse searchUserInfoByToken(String appAuthToken){
//        AlipayOpenAuthTokenAppQueryResponse response = new AlipayOpenAuthTokenAppQueryResponse();
//        try {
//            AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", "2016080500171059", APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
//            AlipayOpenAuthTokenAppQueryRequest request = new AlipayOpenAuthTokenAppQueryRequest();
//            request.setBizContent("{" +
//                    "    \"app_auth_token\":\"201709BBa4d3d61996ad4a87b185f27774108X89\"" +
//                    "  }");
//            response = alipayClient.execute(request);
//        }catch (AlipayApiException e){
//            e.printStackTrace();
//        }
//        return response;
//    }

}
