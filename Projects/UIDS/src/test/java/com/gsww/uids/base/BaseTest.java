package com.gsww.uids.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-spring-application.xml"})
public class BaseTest {
	
	protected MockHttpServletRequest request;
	
	protected MockHttpServletResponse response;    
	
    @Before  
    public void setup() {
    	request = new MockHttpServletRequest();    
        request.setCharacterEncoding("UTF-8");    
        response = new MockHttpServletResponse(); 
    }
}