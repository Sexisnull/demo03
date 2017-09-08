package com.gsww.ischool.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ComplatOutsideuser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "complat_outsideuser", catalog = "uidsdx")
public class ComplatOutsideuser implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String uuid;
	private String loginname;
	private String pwd;
	private String name;
	private Integer age;
	private String sex;
	private Integer enable;
	private String degree;
	private String pinyin;
	private Integer paperstype;
	private String papersnumber;
	private String description;
	private String mobile;
	private String phone;
	private String fax;
	private String email;
	private String qq;
	private String msn;
	private String address;
	private String post;
	private String workunit;
	private String headship;
	private Timestamp birthdate;
	private Timestamp logintime;
	private String loginip;
	private String regip;
	private Timestamp createtime;
	private String regsite;
	private Integer isauth;
	private Integer authstate;
	private String headpic;
	private String bodypic;
	private Integer isupload;
	private String comptel;
	private String rejectreason;
	private String headrenamepic;
	private String bodyrenamepic;

	// Constructors

	/** default constructor */
	public ComplatOutsideuser() {
	}

	/** full constructor */
	public ComplatOutsideuser(String uuid, String loginname, String pwd,
			String name, Integer age, String sex, Integer enable,
			String degree, String pinyin, Integer paperstype,
			String papersnumber, String description, String mobile,
			String phone, String fax, String email, String qq, String msn,
			String address, String post, String workunit, String headship,
			Timestamp birthdate, Timestamp logintime, String loginip,
			String regip, Timestamp createtime, String regsite, Integer isauth,
			Integer authstate, String headpic, String bodypic,
			Integer isupload, String comptel, String rejectreason,
			String headrenamepic, String bodyrenamepic) {
		this.uuid = uuid;
		this.loginname = loginname;
		this.pwd = pwd;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.enable = enable;
		this.degree = degree;
		this.pinyin = pinyin;
		this.paperstype = paperstype;
		this.papersnumber = papersnumber;
		this.description = description;
		this.mobile = mobile;
		this.phone = phone;
		this.fax = fax;
		this.email = email;
		this.qq = qq;
		this.msn = msn;
		this.address = address;
		this.post = post;
		this.workunit = workunit;
		this.headship = headship;
		this.birthdate = birthdate;
		this.logintime = logintime;
		this.loginip = loginip;
		this.regip = regip;
		this.createtime = createtime;
		this.regsite = regsite;
		this.isauth = isauth;
		this.authstate = authstate;
		this.headpic = headpic;
		this.bodypic = bodypic;
		this.isupload = isupload;
		this.comptel = comptel;
		this.rejectreason = rejectreason;
		this.headrenamepic = headrenamepic;
		this.bodyrenamepic = bodyrenamepic;
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

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "sex", length = 100)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "enable")
	public Integer getEnable() {
		return this.enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Column(name = "degree")
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Column(name = "pinyin")
	public String getPinyin() {
		return this.pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Column(name = "paperstype")
	public Integer getPaperstype() {
		return this.paperstype;
	}

	public void setPaperstype(Integer paperstype) {
		this.paperstype = paperstype;
	}

	@Column(name = "papersnumber")
	public String getPapersnumber() {
		return this.papersnumber;
	}

	public void setPapersnumber(String papersnumber) {
		this.papersnumber = papersnumber;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Column(name = "workunit")
	public String getWorkunit() {
		return this.workunit;
	}

	public void setWorkunit(String workunit) {
		this.workunit = workunit;
	}

	@Column(name = "headship")
	public String getHeadship() {
		return this.headship;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	@Column(name = "birthdate", length = 0)
	public Timestamp getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Timestamp birthdate) {
		this.birthdate = birthdate;
	}

	@Column(name = "logintime", length = 0)
	public Timestamp getLogintime() {
		return this.logintime;
	}

	public void setLogintime(Timestamp logintime) {
		this.logintime = logintime;
	}

	@Column(name = "loginip")
	public String getLoginip() {
		return this.loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	@Column(name = "regip")
	public String getRegip() {
		return this.regip;
	}

	public void setRegip(String regip) {
		this.regip = regip;
	}

	@Column(name = "createtime", length = 0)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "regsite")
	public String getRegsite() {
		return this.regsite;
	}

	public void setRegsite(String regsite) {
		this.regsite = regsite;
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

	@Column(name = "headpic", length = 65535)
	public String getHeadpic() {
		return this.headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	@Column(name = "bodypic", length = 65535)
	public String getBodypic() {
		return this.bodypic;
	}

	public void setBodypic(String bodypic) {
		this.bodypic = bodypic;
	}

	@Column(name = "isupload")
	public Integer getIsupload() {
		return this.isupload;
	}

	public void setIsupload(Integer isupload) {
		this.isupload = isupload;
	}

	@Column(name = "comptel")
	public String getComptel() {
		return this.comptel;
	}

	public void setComptel(String comptel) {
		this.comptel = comptel;
	}

	@Column(name = "rejectreason")
	public String getRejectreason() {
		return this.rejectreason;
	}

	public void setRejectreason(String rejectreason) {
		this.rejectreason = rejectreason;
	}

	@Column(name = "headrenamepic")
	public String getHeadrenamepic() {
		return this.headrenamepic;
	}

	public void setHeadrenamepic(String headrenamepic) {
		this.headrenamepic = headrenamepic;
	}

	@Column(name = "bodyrenamepic")
	public String getBodyrenamepic() {
		return this.bodyrenamepic;
	}

	public void setBodyrenamepic(String bodyrenamepic) {
		this.bodyrenamepic = bodyrenamepic;
	}

}