package com.gsww.uids.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ComplatUserOk entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "complat_user-ok", catalog = "uidsdx")
public class ComplatUserOk implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String uuid;
	private String loginname;
	private String pwd;
	private String name;
	private Integer groupid;
	private Integer age;
	private Integer sex;
	private Integer enable;
	private Integer usertype;
	private String pwdquestion;
	private String pwdanswer;
	private Timestamp createtime;
	private String ip;
	private Timestamp accesstime;
	private String pinyin;
	private String mobile;
	private String phone;
	private String fax;
	private String email;
	private String qq;
	private String msn;
	private String address;
	private String post;
	private String headship;
	private Integer orderid;
	private String loginallname;
	private Timestamp modifytime;
	private Integer synState;
	private Integer opersign;

	// Constructors

	/** default constructor */
	public ComplatUserOk() {
	}

	/** full constructor */
	public ComplatUserOk(String uuid, String loginname, String pwd,
			String name, Integer groupid, Integer age, Integer sex,
			Integer enable, Integer usertype, String pwdquestion,
			String pwdanswer, Timestamp createtime, String ip,
			Timestamp accesstime, String pinyin, String mobile, String phone,
			String fax, String email, String qq, String msn, String address,
			String post, String headship, Integer orderid, String loginallname,
			Timestamp modifytime, Integer synState, Integer opersign) {
		this.uuid = uuid;
		this.loginname = loginname;
		this.pwd = pwd;
		this.name = name;
		this.groupid = groupid;
		this.age = age;
		this.sex = sex;
		this.enable = enable;
		this.usertype = usertype;
		this.pwdquestion = pwdquestion;
		this.pwdanswer = pwdanswer;
		this.createtime = createtime;
		this.ip = ip;
		this.accesstime = accesstime;
		this.pinyin = pinyin;
		this.mobile = mobile;
		this.phone = phone;
		this.fax = fax;
		this.email = email;
		this.qq = qq;
		this.msn = msn;
		this.address = address;
		this.post = post;
		this.headship = headship;
		this.orderid = orderid;
		this.loginallname = loginallname;
		this.modifytime = modifytime;
		this.synState = synState;
		this.opersign = opersign;
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

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "groupid")
	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "sex")
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "enable")
	public Integer getEnable() {
		return this.enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Column(name = "usertype")
	public Integer getUsertype() {
		return this.usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	@Column(name = "pwdquestion")
	public String getPwdquestion() {
		return this.pwdquestion;
	}

	public void setPwdquestion(String pwdquestion) {
		this.pwdquestion = pwdquestion;
	}

	@Column(name = "pwdanswer")
	public String getPwdanswer() {
		return this.pwdanswer;
	}

	public void setPwdanswer(String pwdanswer) {
		this.pwdanswer = pwdanswer;
	}

	@Column(name = "createtime", length = 0)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "ip")
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "accesstime", length = 0)
	public Timestamp getAccesstime() {
		return this.accesstime;
	}

	public void setAccesstime(Timestamp accesstime) {
		this.accesstime = accesstime;
	}

	@Column(name = "pinyin")
	public String getPinyin() {
		return this.pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
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

	@Column(name = "qq")
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "msn")
	public String getMsn() {
		return this.msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	@Column(name = "address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "post")
	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "headship")
	public String getHeadship() {
		return this.headship;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	@Column(name = "orderid")
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "loginallname")
	public String getLoginallname() {
		return this.loginallname;
	}

	public void setLoginallname(String loginallname) {
		this.loginallname = loginallname;
	}

	@Column(name = "modifytime", length = 0)
	public Timestamp getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "synState")
	public Integer getSynState() {
		return this.synState;
	}

	public void setSynState(Integer synState) {
		this.synState = synState;
	}

	@Column(name = "Opersign")
	public Integer getOpersign() {
		return this.opersign;
	}

	public void setOpersign(Integer opersign) {
		this.opersign = opersign;
	}

}