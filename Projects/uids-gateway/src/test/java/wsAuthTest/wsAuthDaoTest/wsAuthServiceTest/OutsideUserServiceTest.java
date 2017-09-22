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
import com.gsww.uids.gateway.entity.OutsideUser;
import com.gsww.uids.gateway.service.CorporationService;
import com.gsww.uids.gateway.service.OutsideUserService;

import net.sf.json.JSONArray;
/**
 * OutsideUserService-Test
 * 
 * @author zcc
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class OutsideUserServiceTest {
	private static OutsideUserService outsideUserService = new OutsideUserService();

	@Test
	public void findByLoginName() {
		OutsideUser outsideUser = new OutsideUser();
		try {
			String loginName = "hanweb";
			outsideUser = outsideUserService.findByLoginName(loginName);
			System.out.println(
					"OutsideUserServiceTest-通过loginName获取OutsideUser" + JSONArray.fromObject(outsideUser).toString());
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
			outsideUser = outsideUserService.findByMobile(cellPhoneNum);
			System.out.println("OutsideUserServiceTest-通过cellPhoneNum获取OutsideUser"
					+ JSONArray.fromObject(outsideUser).toString());
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
			outsideUser = outsideUserService.findByIdCard(IdCard);
			System.out.println(
					"OutsideUserServiceTest-通过IdCard获取OutsideUser" + JSONArray.fromObject(outsideUser).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void checkUserLogin() {
		OutsideUser outsideUser = new OutsideUser();
		try {
			String loginName = "justy_0010";
			String pwd = "Bxp0AwMBenQIBXdBBjIFNQU3";
			String ip = "10.10.100.212";
			outsideUser = outsideUserService.checkUserLogin(loginName, pwd, ip);
			System.out.println("OutsideUserServiceTest-checkUserLogin" + JSONArray.fromObject(outsideUser).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
