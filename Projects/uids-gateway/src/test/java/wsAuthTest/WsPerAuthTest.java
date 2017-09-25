package wsAuthTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.gateway.ws.WsPerAuth;
/**
 * WsPerAuth-Test(个人用户)
 * @author zcc
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class WsPerAuthTest {
	private WsPerAuth wsPerAuth = new WsPerAuth();

	@Test
	public void ticketValidate() {
		try {
			String appmark = "gszw";
			String ticket = "ba889182b1789ed8cbf261fec2106c0e";
			String time = "20150923163033";
			String sign = "fWBwAHUKAnEGZgJ3dH0CBHVHc0N9NXVAd0B2M3ExAEUPRAQxDTsCNnVBAzY=";
			String ticketValidate = wsPerAuth.ticketValidate(appmark, ticket, time, sign);
			System.out.println("个人用户-票据认证:" + ticketValidate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void findUserByToken() {
		try {
			String appmark = "gszw";
			String token = "dx4HYnJpBnIOZAxiBUYJY3cED3UDBg==";
			String time = "20150923163033";
			String sign = "fWBwAHUKAnEGZgJ3dH0CBHVHc0N9NXVAd0B2M3ExAEUPRAQxDTsCNnVBAzY=";
			String findUserByToken = wsPerAuth.findUserByToken(appmark, token, time, sign);
			System.out.println("个人用户-根据令牌获取用户详细信息:" + findUserByToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void generateTicket() {
		try {
			String appmark = "gszw";
			String token = "dx4HYnJpBnIOZAxiBUYJY3cED3UDBg==";
			String time = "20150923163033";
			String sign = "fWBwAHUKAnEGZgJ3dH0CBHVHc0N9NXVAd0B2M3ExAEUPRAQxDTsCNnVBAzY=";
			String proxyapp = "gszw";
			String generateTicket = wsPerAuth.generateTicket(appmark, token, time, sign, proxyapp);
			System.out.println("个人用户-根据令牌获取第三方接口资源票据:" + generateTicket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void userValidate() {
		try {
			String appmark = "gszw";
			String time = "20150923163033";
			String sign = "fWBwAHUKAnEGZgJ3dH0CBHVHc0N9NXVAd0B2M3ExAEUPRAQxDTsCNnVBAzY=";
			String loginname = "ls";
			String password = "BRoCYXVqAA11ZH1p";
			String userValidate = wsPerAuth.userValidate(appmark, time, sign, loginname, password);
			System.out.println("个人用户-2.4.4	用户认证:" + userValidate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
