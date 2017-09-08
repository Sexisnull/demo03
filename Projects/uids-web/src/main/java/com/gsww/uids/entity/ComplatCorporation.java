package com.gsww.ischool.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ComplatCorporation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "complat_corporation", catalog = "uidsdx")
public class ComplatCorporation implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String uuid;
	private Integer type;
	private Integer enable;
	private String loginname;
	private String pwd;
	private String mobile;
	private String phone;
	private String name;
	private String regnumber;
	private String realname;
	private String sex;
	private String post;
	private String address;
	private String scope;
	private String regorgname;
	private String serialnumber;
	private String headship;
	private String fax;
	private String email;
	private String cardnumber;
	private String orgnumber;
	private Timestamp createtime;
	private String nation;
	private String pinyin;
	private Integer isauth;
	private Integer authstate;
	private String cardpic;
	private String orgpic;
	private String licencepic;
	private Integer isupload;
	private String rejectreason;
	private String cardrenamepic;
	private String licencerenamepic;
	private String orgrenamepic;

	// Constructors

	/** default constructor */
	public ComplatCorporation() {
	}

	/** full constructor */
	public ComplatCorporation(String uuid, Integer type, Integer enable,
			String loginname, String pwd, String mobile, String phone,
			String name, String regnumber, String realname, String sex,
			String post, String address, String scope, String regorgname,
			String serialnumber, String headship, String fax, String email,
			String cardnumber, String orgnumber, Timestamp createtime,
			String nation, String pinyin, Integer isauth, Integer authstate,
			String cardpic, String orgpic, String licencepic, Integer isupload,
			String rejectreason, String cardrenamepic, String licencerenamepic,
			String orgrenamepic) {
		this.uuid = uuid;
		this.type = type;
		this.enable = enable;
		this.loginname = loginname;
		this.pwd = pwd;
		this.mobile = mobile;
		this.phone = phone;
		this.name = name;
		this.regnumber = regnumber;
		this.realname = realname;
		this.sex = sex;
		this.post = post;
		this.address = address;
		this.scope = scope;
		this.regorgname = regorgname;
		this.serialnumber = serialnumber;
		this.headship = headship;
		this.fax = fax;
		this.email = email;
		this.cardnumber = cardnumber;
		this.orgnumber = orgnumber;
		this.createtime = createtime;
		this.nation = nation;
		this.pinyin = pinyin;
		this.isauth = isauth;
		this.authstate = authstate;
		this.cardpic = cardpic;
		this.orgpic = orgpic;
		this.licencepic = licencepic;
		this.isupload = isupload;
		this.rejectreason = rejectreason;
		this.cardrenamepic = cardrenamepic;
		this.licencerenamepic = licencerenamepic;
		this.orgrenamepic = orgrenamepic;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "iid", unique = true, nullable = false)
	public Integer getIid() {
		return this.iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	@Column(name = "uuid", length = 32)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "enable")
	public Integer getEnable() {
		return this.enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Column(name = "loginname")
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "pwd")
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "mobile")
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "phone")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "regnumber")
	public String getRegnumber() {
		return this.regnumber;
	}

	public void setRegnumber(String regnumber) {
		this.regnumber = regnumber;
	}

	@Column(name = "realname")
	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Column(name = "sex")
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "post")
	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "scope")
	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Column(name = "regorgname")
	public String getRegorgname() {
		return this.regorgname;
	}

	public void setRegorgname(String regorgname) {
		this.regorgname = regorgname;
	}

	@Column(name = "serialnumber")
	public String getSerialnumber() {
		return this.serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	@Column(name = "headship")
	public String getHeadship() {
		return this.headship;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	@Column(name = "fax")
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "cardnumber")
	public String getCardnumber() {
		return this.cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	@Column(name = "orgnumber")
	public String getOrgnumber() {
		return this.orgnumber;
	}

	public void setOrgnumber(String orgnumber) {
		this.orgnumber = orgnumber;
	}

	@Column(name = "createtime", length = 0)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "nation")
	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "pinyin")
	public String getPinyin() {
		return this.pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Column(name = "isauth")
	public Integer getIsauth() {
		return this.isauth;
	}

	public void setIsauth(Integer isauth) {
		this.isauth = isauth;
	}

	@Column(name = "authstate")
	public Integer getAuthstate() {
		return this.authstate;
	}

	public void setAuthstate(Integer authstate) {
		this.authstate = authstate;
	}

	@Column(name = "cardpic", length = 65535)
	public String getCardpic() {
		return this.cardpic;
	}

	public void setCardpic(String cardpic) {
		this.cardpic = cardpic;
	}

	@Column(name = "orgpic", length = 65535)
	public String getOrgpic() {
		return this.orgpic;
	}

	public void setOrgpic(String orgpic) {
		this.orgpic = orgpic;
	}

	@Column(name = "licencepic", length = 65535)
	public String getLicencepic() {
		return this.licencepic;
	}

	public void setLicencepic(String licencepic) {
		this.licencepic = licencepic;
	}

	@Column(name = "isupload")
	public Integer getIsupload() {
		return this.isupload;
	}

	public void setIsupload(Integer isupload) {
		this.isupload = isupload;
	}

	@Column(name = "rejectreason")
	public String getRejectreason() {
		return this.rejectreason;
	}

	public void setRejectreason(String rejectreason) {
		this.rejectreason = rejectreason;
	}

	@Column(name = "cardrenamepic")
	public String getCardrenamepic() {
		return this.cardrenamepic;
	}

	public void setCardrenamepic(String cardrenamepic) {
		this.cardrenamepic = cardrenamepic;
	}

	@Column(name = "licencerenamepic")
	public String getLicencerenamepic() {
		return this.licencerenamepic;
	}

	public void setLicencerenamepic(String licencerenamepic) {
		this.licencerenamepic = licencerenamepic;
	}

	@Column(name = "orgrenamepic")
	public String getOrgrenamepic() {
		return this.orgrenamepic;
	}

	public void setOrgrenamepic(String orgrenamepic) {
		this.orgrenamepic = orgrenamepic;
	}

}