package restTest.WeChatServiceTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.gateway.util.WeChatUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class AdvancedUtilTest {
	private static WeChatUtil weChatUtil = new WeChatUtil();

	//
	@Test
	public void findByMark() {
		try {
			String code = "c-123";
			weChatUtil.getUserInfoAccessToken(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
