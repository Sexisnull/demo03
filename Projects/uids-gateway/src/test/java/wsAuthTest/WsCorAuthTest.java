package wsAuthTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.gateway.ws.WsCorAuth;

/**
 * WsCorAuth-Test(法人用户)
 * @author zcc
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class WsCorAuthTest {
	private WsCorAuth wsCorAuth = new WsCorAuth();

	@Test
	public void ticketValidate() {
		try {
			String appmark = "gszw";
			String ticket = "5747f682ab4d2404f64039725eaa906a";
			String time = "2015101616354";
			String sign = "e28MG3NoBwsFEgNgAxp2YnRrdXQANXk1BUZ3RwZFczd4NXc3B0NzPgVEDTlyMg==";
			String ticketValidate = wsCorAuth.ticketValidate(appmark, ticket, time, sign);
			System.out.println("法人用户-票据认证:" + ticketValidate);
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
			String time = "2015101616354";
			String sign = "e28MG3NoBwsFEgNgAxp2YnRrdXQANXk1BUZ3RwZFczd4NXc3B0NzPgVEDTlyMg==";
			String findUserByToken = wsCorAuth.findUserByToken(appmark, token, time, sign);
			System.out.println("法人用户-根据令牌获取用户详细信息:" + findUserByToken);
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
			String time = "2015101616354";
			String sign = "e28MG3NoBwsFEgNgAxp2YnRrdXQANXk1BUZ3RwZFczd4NXc3B0NzPgVEDTlyMg==";
			String proxyapp = "gszw";
			String generateTicket = wsCorAuth.generateTicket(appmark, token, time, sign, proxyapp);
			System.out.println("法人用户-根据令牌获取第三方接口资源票据:" + generateTicket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void userValidate() {
		try {
			String appmark = "gszw";
			String time = "2015101616354";
			String sign = "e28MG3NoBwsFEgNgAxp2YnRrdXQANXk1BUZ3RwZFczd4NXc3B0NzPgVEDTlyMg==";
			String loginname = "hanweb";
			String password = "BRpyEQMcCgcFFHJm";
			String userValidate = wsCorAuth.userValidate(appmark, time, sign, loginname, password);
			System.out.println("法人用户-2.4.4	用户认证:" + userValidate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
