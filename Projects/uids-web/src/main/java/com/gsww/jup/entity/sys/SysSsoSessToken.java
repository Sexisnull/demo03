package com.gsww.jup.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * SysSsoSessToken entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_SSO_SESS_TOKEN")
public class SysSsoSessToken  implements java.io.Serializable {


    // Fields    

     private String ssoId;
     private String userAcctId;
     private String loginTime;
     private String loginIp;
     private String sessionToken;


    // Constructors

    /** default constructor */
    public SysSsoSessToken() {
    }

    
    /** full constructor */
    public SysSsoSessToken(String userAcctId, String loginTime, String loginIp, String sessionToken) {
        this.userAcctId = userAcctId;
        this.loginTime = loginTime;
        this.loginIp = loginIp;
        this.sessionToken = sessionToken;
    }

   
    // Property accessors
    @Id
    @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "SSO_ID", unique = true, nullable = false, length = 32)
    public String getSsoId() {
        return this.ssoId;
    }
    
    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getUserAcctId() {
        return this.userAcctId;
    }
    
    public void setUserAcctId(String userAcctId) {
        this.userAcctId = userAcctId;
    }

    public String getLoginTime() {
        return this.loginTime;
    }
    
    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return this.loginIp;
    }
    
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getSessionToken() {
        return this.sessionToken;
    }
    
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
   








}