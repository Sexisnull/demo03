package wsAuthTest.wsAuthDaoTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.gateway.dao.application.ApplicationDao;
import com.gsww.uids.gateway.entity.Application;
import com.gsww.uids.gateway.util.SpringContextHolder;

import net.sf.json.JSONArray;

/**
 * ApplicationDao-Test
 * 
 * @author zcc
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ApplicationDaoTest {
	private static ApplicationDao applicationDAO;
	static {
		applicationDAO = SpringContextHolder.getBean("applicationDao");
	}

	@Test
	public void getApplicatioonByMark() {
		Application application = new Application();
		try {
			String mark = "xzsp";
			application = applicationDAO.getApplicatioonByMark(mark);
			System.out.println("ApplicationDAOTest-通过mark获取Applicaton" + JSONArray.fromObject(application).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
