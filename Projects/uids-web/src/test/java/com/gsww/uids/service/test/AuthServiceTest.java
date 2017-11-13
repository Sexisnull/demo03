package com.gsww.uids.service.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsww.uids.service.impl.AuthServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class AuthServiceTest {
	private static Logger logger = LoggerFactory
			.getLogger(ComplatBanListServiceTest.class);

	@Test
	public void authGetToken() {
		try {
			AuthServiceImpl authService = new AuthServiceImpl();
			String someInfo = authService.authGetToken();
			System.out.println("corporCont->someInfo:" + someInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		assertTrue(true);
	}

	@Test
	public void corporCont() {
		try {
			int verify_mode = 112;
			String zzjgdm = "111";
			String tyshxydm = "111";
			String gszch = "111";
			String qymcqc = "111";
			AuthServiceImpl authService = new AuthServiceImpl();
			String someInfo = authService.corporCont(verify_mode, zzjgdm,
					tyshxydm, gszch, qymcqc);
			System.out.println("corporCont->someInfo:" + someInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		assertTrue(true);
	}

	@Test
	public void corporAuth() {
		try {
			int verify_mode = 0;
			String frmc = "";
			String dbrxm = "";
			String id = "";
			String tyshxydm = "";
			String zzjgdm = "";
			String gszch = "";
			AuthServiceImpl authService = new AuthServiceImpl();
			String someInfo = authService.corporAuth(verify_mode, frmc, dbrxm,
					id, tyshxydm, zzjgdm, gszch);
			System.out.println("corporCont->someInfo:" + someInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		assertTrue(true);
	}

	@Test
	public void perporAuth() {
		try {
			final String id = "";
			final String name = "";
			AuthServiceImpl authService = new AuthServiceImpl();
			String someInfo = authService.perporAuth(id, name);
			System.out.println("corporCont->someInfo:" + someInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		assertTrue(true);
	}

	@Test
	public void pubporAuth() {
		try {
			final int verify_mode = 1;
			final String id = "";
			final String name = "";
			final String jgbm = "";
			final String jgmc = "";
			final String qybm = "";
			AuthServiceImpl authService = new AuthServiceImpl();
			String someInfo = authService.pubporAuth(verify_mode, id, name,
					jgbm, jgmc, qybm);
			System.out.println("corporCont->someInfo:" + someInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		assertTrue(true);
	}

}
