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
import com.gsww.uids.gateway.rest.QqAuthService;
import com.gsww.uids.gateway.rest.WeChatAuthService;
import com.gsww.uids.gateway.service.CorporationService;
import com.gsww.uids.gateway.service.OutsideUserService;

import net.sf.json.JSONArray;

/**
 * QQAuthService-Test
 * 
 * @author zcc
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class QqAuthServiceTest {
	private static QqAuthService qqAuthService = new QqAuthService();

	@Test
	public void auth() {
		try {
			String code = "hanweb";
			String outsideUser = qqAuthService.auth(code);
			// String outsideUser = weChatAuthService.auth(code);
			System.out.println("QQ登录三方平台" + outsideUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void mergeUserId() {
		try {
			String openid = "11111";
			String userId = "1";
			boolean mergeState = qqAuthService.mergeUserId(openid, userId);
			// String outsideUser = weChatAuthService.auth(code);
			if (mergeState) {
				System.out.println("QQ账号绑定成功");
			} else {
				System.out.println("QQ账号绑定失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
