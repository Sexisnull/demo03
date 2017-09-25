package wsAuthTest.wsAuthDaoTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.gateway.dao.authlog.AuthLogDao;
import com.gsww.uids.gateway.entity.Application;
import com.gsww.uids.gateway.entity.JisAuthLog;
import com.gsww.uids.gateway.util.SpringContextHolder;

import net.sf.json.JSONArray;
/**
 * AuthLogDao-Test
 * 
 * @author zcc
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class AuthLogDaoTest {
	private static AuthLogDao authLogDao;
	static {
		authLogDao = SpringContextHolder.getBean("authLogDao");
	}

	@Test
	public void getJisAuthLogByTicket() {
		JisAuthLog jisAuthLog = new JisAuthLog();
		try {
			String ticket = "ba889182b1789ed8cbf261fec2106c0e";
			Integer userType = 1;
			jisAuthLog = authLogDao.getJisAuthLogByTicket(ticket, userType);
			System.out.println("AuthLogDaoTest-通过ticket获取JisAuthLog" + JSONArray.fromObject(jisAuthLog).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void getJisAuthLogByToken() {
		JisAuthLog jisAuthLog = new JisAuthLog();
		try {
			String token = "dB1/GnVuA3cGbA1jdDcBawd0eQMHAg==";
			Integer userType = 2;
			jisAuthLog = authLogDao.getJisAuthLogByToken(token, userType);
			System.out.println("AuthLogDaoTest-通过token获取JisAuthLog" + JSONArray.fromObject(jisAuthLog).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
