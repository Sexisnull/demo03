package com.gsww.uids.gateway.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体类-OutsideUser-complat_outsideuser
 * 
 * @author zcc
 *
 */
public class OutsideUser implements Serializable {
	private static final long serialVersionUID = 2506351625961775795L;

	private Integer iid;
	private String uuid;
	private String loginname;
	private String pwd;
	private String name;
	private Integer age;
	private String sex;
	private Integer enable = Integer.valueOf(1);
	private String degree;
	private String pinyin;
	private Integer paperstype = Integer.valueOf(1);
	private String papersnumber;
	private String description;
	private String mobile;
	private String phone;
	private String comptel;
	private String fax;
	private String email;
	private String qq;
	private String msn;
	private String address;
	private String post;
	private String workunit;
	private String headship;
	private Date birthdate;
	private Date logintime;
	private String loginip;
	private String regip;
	private Date createtime;
	private String regsite;
	private String rejectreason;
	private Integer isauth = Integer.valueOf(0);
	private Integer authstate = Integer.valueOf(0);
	private String headpic;
	private String headrenamepic;
	private String bodypic;
	private String bodyrenamepic;
	private Integer isupload = Integer.valueOf(0);
	private Date modifytime;
	private List<Role> rolelist;
	private Integer opersign;
	private Integer synstate = Integer.valueOf(0);
	private String residenceid = "";
	private String presidenceid = "";
	private String gpresidenceid = "";
	private String residencedetail = "";
	private String livingareaid = "";
	private String plivingareaid = "";
	private String gplivingareaid = "";
	private String livingareadetail = "";
	private Integer iscellphoneverified = Integer.valueOf(0);

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public Integer getPaperstype() {
		return paperstype;
	}

	public void setPaperstype(Integer paperstype) {
		this.paperstype = paperstype;
	}

	public String getPapersnumber() {
		return papersnumber;
	}

	public void setPapersnumber(String papersnumber) {
		this.papersnumber = papersnumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getComptel() {
		return comptel;
	}

	public void setComptel(String comptel) {
		this.comptel = comptel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getWorkunit() {
		return workunit;
	}

	public void setWorkunit(String workunit) {
		this.workunit = workunit;
	}

	public String getHeadship() {
		return headship;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Date getLogintime() {
		return logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public String getRegip() {
		return regip;
	}

	public void setRegip(String regip) {
		this.regip = regip;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getRegsite() {
		return regsite;
	}

	public void setRegsite(String regsite) {
		this.regsite = regsite;
	}

	public String getRejectreason() {
		return rejectreason;
	}

	public void setRejectreason(String rejectreason) {
		this.rejectreason = rejectreason;
	}

	public Integer getIsauth() {
		return isauth;
	}

	public void setIsauth(Integer isauth) {
		this.isauth = isauth;
	}

	public Integer getAuthstate() {
		return authstate;
	}

	public void setAuthstate(Integer authstate) {
		this.authstate = authstate;
	}

	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	public String getHeadrenamepic() {
		return headrenamepic;
	}

	public void setHeadrenamepic(String headrenamepic) {
		this.headrenamepic = headrenamepic;
	}

	public String getBodypic() {
		return bodypic;
	}

	public void setBodypic(String bodypic) {
		this.bodypic = bodypic;
	}

	public String getBodyrenamepic() {
		return bodyrenamepic;
	}

	public void setBodyrenamepic(String bodyrenamepic) {
		this.bodyrenamepic = bodyrenamepic;
	}

	public Integer getIsupload() {
		return isupload;
	}

	public void setIsupload(Integer isupload) {
		this.isupload = isupload;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public List<Role> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<Role> rolelist) {
		this.rolelist = rolelist;
	}

	public Integer getOpersign() {
		return opersign;
	}

	public void setOpersign(Integer opersign) {
		this.opersign = opersign;
	}

	public Integer getSynstate() {
		return synstate;
	}

	public void setSynstate(Integer synstate) {
		this.synstate = synstate;
	}

	public String getResidenceid() {
		return residenceid;
	}

	public void setResidenceid(String residenceid) {
		this.residenceid = residenceid;
	}

	public String getPresidenceid() {
		return presidenceid;
	}

	public void setPresidenceid(String presidenceid) {
		this.presidenceid = presidenceid;
	}

	public String getGpresidenceid() {
		return gpresidenceid;
	}

	public void setGpresidenceid(String gpresidenceid) {
		this.gpresidenceid = gpresidenceid;
	}

	public String getResidencedetail() {
		return residencedetail;
	}

	public void setResidencedetail(String residencedetail) {
		this.residencedetail = residencedetail;
	}

	public String getLivingareaid() {
		return livingareaid;
	}

	public void setLivingareaid(String livingareaid) {
		this.livingareaid = livingareaid;
	}

	public String getPlivingareaid() {
		return plivingareaid;
	}

	public void setPlivingareaid(String plivingareaid) {
		this.plivingareaid = plivingareaid;
	}

	public String getGplivingareaid() {
		return gplivingareaid;
	}

	public void setGplivingareaid(String gplivingareaid) {
		this.gplivingareaid = gplivingareaid;
	}

	public String getLivingareadetail() {
		return livingareadetail;
	}

	public void setLivingareadetail(String livingareadetail) {
		this.livingareadetail = livingareadetail;
	}

	public Integer getIscellphoneverified() {
		return iscellphoneverified;
	}

	public void setIscellphoneverified(Integer iscellphoneverified) {
		this.iscellphoneverified = iscellphoneverified;
	}

}