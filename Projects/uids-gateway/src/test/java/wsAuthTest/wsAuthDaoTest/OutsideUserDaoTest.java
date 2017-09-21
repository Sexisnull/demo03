package wsAuthTest.wsAuthDaoTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.gateway.dao.outsideuser.OutsideUserDAO;
import com.gsww.uids.gateway.entity.OutsideUser;
import com.gsww.uids.gateway.util.SpringContextHolder;

import net.sf.json.JSONArray;
/**
 * OutsideUserDAO-Test
 * 
 * @author zcc
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class OutsideUserDaoTest {
	private static OutsideUserDAO outsideUserDAO;
	static {
		outsideUserDAO = SpringContextHolder.getBean("outsideUserDAO");
	}

	@Test
	public void findByLoginName() {
		OutsideUser outsideUser = new OutsideUser();
		try {
			String loginName = "hanweb";
			outsideUser = outsideUserDAO.findByLoginName(loginName);
			System.out.println(
					"OutsideUserDaoTest-通过loginName获取OutsideUser" + JSONArray.fromObject(outsideUser).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void findByMobile() {
		OutsideUser outsideUser = new OutsideUser();
		try {
			String cellPhoneNum = "18900000000";
			outsideUser = outsideUserDAO.findByMobile(cellPhoneNum);
			System.out.println(
					"OutsideUserDaoTest-通过cellPhoneNum获取outsideUser" + JSONArray.fromObject(outsideUser).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void findByIdCard() {
		OutsideUser outsideUser = new OutsideUser();
		try {
			String IdCard = "320323198903061034";
			outsideUser = outsideUserDAO.findByIdCard(IdCard);
			System.out.println("AuthLogDaoTest-通过IdCard获取OutsideUser" + JSONArray.fromObject(outsideUser).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
