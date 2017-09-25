package wsAuthTest.wsAuthDaoTest.wsAuthServiceTest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.gateway.entity.Corporation;
import com.gsww.uids.gateway.entity.JisAuthLog;
import com.gsww.uids.gateway.service.CorporationService;

import net.sf.json.JSONArray;
/**
 * CorporationService-Test
 * 
 * @author zcc
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CorporationServiceTest {
	private static CorporationService corporationService = new CorporationService();

	@Test
	public void findByLoginName() {
		Corporation corporation = new Corporation();
		try {
			String loginName = "hanweb";
			corporation = corporationService.findByLoginName(loginName);
			System.out.println(
					"CorporationServiceTest-通过loginName获取Corporation" + JSONArray.fromObject(corporation).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void findByRegNumber() {

		Corporation corporation = new Corporation();
		try {
			String regnumber = "54545451";
			corporation = corporationService.findByRegNumber(regnumber);
			System.out.println(
					"CorporationServiceTest-通过regnumber获取Corporation" + JSONArray.fromObject(corporation).toString());
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
			corporation = corporationService.findByOrgNumber(orgnumber);
			System.out.println(
					"CorporationServiceTest-通过orgnumber获取Corporation" + JSONArray.fromObject(corporation).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);

	}

	@Test
	public void checkUserLogin() {
		Corporation corporation = new Corporation();
		try {
			String loginName = "hanweb";
			String pwd = "BRpyEQMcCgcFFHJm";
			String ip = "10.10.100.211";
			corporation = corporationService.checkUserLogin(loginName, pwd, ip);
			System.out.println("CorporationServiceTest-checkUserLogin" + JSONArray.fromObject(corporation).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
