package wsAuthTest.wsAuthDaoTest.wsAuthServiceTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.gateway.entity.JisAuthLog;
import com.gsww.uids.gateway.service.AuthLogService;

import net.sf.json.JSONArray;

/**
 * AuthLogService-Test
 * 
 * @author zcc
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class AuthLogServiceTest {
	private static AuthLogService authLogService = new AuthLogService();

	@Test
	public void findByTicket() {
		JisAuthLog jisAuthLog = new JisAuthLog();
		try {
			String ticket = "ba889182b1789ed8cbf261fec2106c0e";
			Integer userType = 1;
			jisAuthLog = authLogService.findByTicket(ticket, userType);
			System.out.println(
					"AuthLogServiceTest-通过ticket, userType获取JisAuthLog" + JSONArray.fromObject(jisAuthLog).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void findByToken() {
		JisAuthLog jisAuthLog = new JisAuthLog();
		try {
			String token = "dB1/GnVuA3cGbA1jdDcBawd0eQMHAg==";
			Integer userType = 2;
			jisAuthLog = authLogService.findByToken(token, userType);
			System.out.println(
					"AuthLogServiceTest-通过token, userType获取JisAuthLog" + JSONArray.fromObject(jisAuthLog).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
