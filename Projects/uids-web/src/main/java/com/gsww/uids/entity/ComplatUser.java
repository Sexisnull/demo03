package com.gsww.uids.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2017-09-07 下午14:30:23</p>
 * <p>类描述 :   政府用户模块实体类    </p>
 *
 *
 * @version 3.0.0
 * @author <a href=" ">shenxh</a>
 */


@Entity
@Table(name = "COMPLAT_USER")
public class ComplatUser implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5515771248216846874L;
	private Integer iid;             // 主键id
	private String uuid;             // UUID
	private String loginname;        // 登录名，如张三zhangs
	private String pwd;              // 密码(加密)
	private String name;             // 姓名
	private Integer groupid;         // 组织机构id
	private Integer age;             // 年龄
	private Integer sex;             // 性别1：男0：女
	private Integer enable;          // 是否启用：0：禁用1：启用
	private Integer usertype;        // 用户类型
	private String pwdquestion;      // 密码找回问题
	private String pwdanswer;        // 密码找回答案
	private Date createtime;         // 创建时间
	private String ip;               // ip地址
	private Date accesstime;         // 访问时间    yyyy-MM-dd HH:mm:ss
	private String pinyin;           // 拼音首字母
	private String mobile;           // 移动电话
	private String phone;            // 固定电话
	private String fax;              // 传真
	private String email;            // email
	private String qq;               // QQ号
	private String msn;              // msn
	private String address;          // 地址
	private String post;             // 邮政编码	
	private String headship;         // 用户职务
	private Integer orderid;         // 排序id
	private String loginallname;     // 带有后缀的登录名，如省发展改革委员会的张三zhangs.fgw.gs（唯一）
	private Date modifytime;         // 修改时间
	private Integer synState;        // 0-未备份      1-备份失败         4-备份成功
	private Integer Opersign;        // 1-新增      2-修改       3-假删	
    private Date modifyPassTime;
    private String groupName;             //机构名称
    private String cardid;           //身份证号
	/** default constructor */
	public ComplatUser() {
		super();
	}



	public ComplatUser(Integer iid, String uuid, String loginname, String pwd,
			String name, Integer groupid, Integer age, Integer sex, Integer enable,
			Integer usertype, String pwdquestion, String pwdanswer,
			Date createtime, String ip, Date accesstime, String pinyin,
			String mobile, String phone, String fax, String email, String qq,
			String msn, String address, String post, String headship,
			Integer orderid, String loginallname, Date modifytime,
			Integer synState, Integer opersign) {
		super();
		this.iid = iid;
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
	    Opersign = opersign;
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IID")
	public Integer getIid() {
		return iid;
	}
	
	public void setIid(Integer iid) {
		this.iid = iid;
	}
	
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	@Column(name = "UUID")
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "LOGINNAME")
	public String getLoginname() {
		return loginname;
	}
	
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	
	@Column(name = "PWD")
	public String getPwd() {
		return pwd;
	}
		
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
		
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "GROUPID")
	public Integer getGroupid() {
		return groupid;
	}
	
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	
	@Column(name = "AGE")
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Column(name = "SEX")
	public Integer getSex() {
		return sex;
	}
	
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Column(name = "ENABLE")
	public Integer getEnable() {
		return enable;
	}
	
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	
	@Column(name = "USERTYPE")
	public Integer getUsertype() {
		return usertype;
	}
	
	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}
	
	@Column(name = "PWDQUESTION")
	public String getPwdquestion() {
		return pwdquestion;
	}
	
	public void setPwdquestion(String pwdquestion) {
		this.pwdquestion = pwdquestion;
	}
	
	@Column(name = "PWDANSWER")
	public String getPwdanswer() {
		return pwdanswer;
	}
	
	public void setPwdanswer(String pwdanswer) {
		this.pwdanswer = pwdanswer;
	}
	
	@Column(name = "CREATETIME")
	public Date getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "IP")
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name = "ACCESSTIME")
	public Date getAccesstime() {
		return accesstime;
	}
	
	public void setAccesstime(Date accesstime) {
		this.accesstime = accesstime;
	}
	
	@Column(name = "PINYIN")
	public String getPinyin() {
		return pinyin;
	}
	
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "QQ")
	public String getQq() {
		return qq;
	}
	
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@Column(name = "MSN")
	public String getMsn() {
		return msn;
	}
	
	public void setMsn(String msn) {
		this.msn = msn;
	}
	
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "POST")
	public String getPost() {
		return post;
	}
	
	public void setPost(String post) {
		this.post = post;
	}
	
	@Column(name = "HEADSHIP")
	public String getHeadship() {
		return headship;
	}
	
	public void setHeadship(String headship) {
		this.headship = headship;
	}
	
	@Column(name = "ORDERID")
	public Integer getOrderid() {
		return orderid;
	}
	
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	
	@Column(name = "LOGINALLNAME")
	public String getLoginallname() {
		return loginallname;
	}
	
	public void setLoginallname(String loginallname) {
		this.loginallname = loginallname;
	}
	
	@Column(name = "MODIFYTIME")
	public Date getModifytime() {
		return modifytime;
	}
	
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	
	@Column(name = "SYNSTATE")
	public Integer getSynState() {
		return synState;
	}
	
	public void setSynState(Integer synState) {
		this.synState = synState;
	}
	
	@Column(name = "OPERSIGN")
	public Integer getOpersign() {
		return Opersign;
	}
	public void setOpersign(Integer opersign) {
		Opersign = opersign;
	}

    @Column(name="MODIFYPASSTIME")
    public Date getModifyPassTime()
    {
      return this.modifyPassTime;
    }
    
    public void setModifyPassTime(Date modifyPassTime) {
        this.modifyPassTime = modifyPassTime;
      }

    @Transient
    public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Transient
	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}


}
