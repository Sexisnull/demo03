package wsAuthTest.wsAuthDaoTest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.gateway.dao.corporation.CorporationDao;
import com.gsww.uids.gateway.entity.Corporation;
import com.gsww.uids.gateway.util.SpringContextHolder;

import net.sf.json.JSONArray;
/**
 * CorporationDao-Test
 * 
 * @author zcc
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CorporationDaoTest {
	private static CorporationDao corporationDAO;
	static {
		corporationDAO = SpringContextHolder.getBean("corporationDAO");
	}

	@Test
	public void findByLoginName() {
		Corporation corporation = new Corporation();
		try {
			String loginName = "hanweb";
			corporation = corporationDAO.findByLoginName(loginName);
			System.out.println(
					"CorporationDaoTest-通过loginName获取Corporation" + JSONArray.fromObject(corporation).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void findByRegNumber() {
		Corporation corporation = new Corporation();
		try {
			String regNumber = "54545451";
			corporation = corporationDAO.findByRegNumber(regNumber);
			System.out.println(
					"CorporationDaoTest-通过regNumber获取Corporation" + JSONArray.fromObject(corporation).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void findByOrgNumber() {
		List<Corporation> corporation = new ArrayList<Corporation>();
		try {
			String orgnumber = "23434233";
			corporation = corporationDAO.findByOrgNumber(orgnumber);
			System.out.println(
					"AuthLogDaoTest-通过orgnumber获取List<Corporation>" + JSONArray.fromObject(corporation).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
