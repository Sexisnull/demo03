package com.gsww.ischool.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ComplatGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "complat_group", catalog = "uidsdx")
public class ComplatGroup implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String name;
	private Integer nodetype;
	private String codeid;
	private String orgcode;
	private String orgtype;
	private Integer areatype;
	private String areacode;
	private String suffix;
	private String spec;
	private Integer pid;
	private Integer orderid;
	private String pinyin;
	private Integer iscombine;
	private String groupallname;
	private Integer opersign;
	private Timestamp createtime;
	private Timestamp modifytime;
	private Integer synState;
	private String syncSign;
	private String syncDate;

	// Constructors

	/** default constructor */
	public ComplatGroup() {
	}

	/** full constructor */
	public ComplatGroup(String name, Integer nodetype, String codeid,
			String orgcode, String orgtype, Integer areatype, String areacode,
			String suffix, String spec, Integer pid, Integer orderid,
			String pinyin, Integer iscombine, String groupallname,
			Integer opersign, Timestamp createtime, Timestamp modifytime,
			Integer synState, String syncSign, String syncDate) {
		this.name = name;
		this.nodetype = nodetype;
		this.codeid = codeid;
		this.orgcode = orgcode;
		this.orgtype = orgtype;
		this.areatype = areatype;
		this.areacode = areacode;
		this.suffix = suffix;
		this.spec = spec;
		this.pid = pid;
		this.orderid = orderid;
		this.pinyin = pinyin;
		this.iscombine = iscombine;
		this.groupallname = groupallname;
		this.opersign = opersign;
		this.createtime = createtime;
		this.modifytime = modifytime;
		this.synState = synState;
		this.syncSign = syncSign;
		this.syncDate = syncDate;
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

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "nodetype")
	public Integer getNodetype() {
		return this.nodetype;
	}

	public void setNodetype(Integer nodetype) {
		this.nodetype = nodetype;
	}

	@Column(name = "codeid")
	public String getCodeid() {
		return this.codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

	@Column(name = "orgcode", length = 50)
	public String getOrgcode() {
		return this.orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	@Column(name = "orgtype")
	public String getOrgtype() {
		return this.orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	@Column(name = "areatype")
	public Integer getAreatype() {
		return this.areatype;
	}

	public void setAreatype(Integer areatype) {
		this.areatype = areatype;
	}

	@Column(name = "areacode", length = 12)
	public String getAreacode() {
		return this.areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	@Column(name = "suffix")
	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Column(name = "spec")
	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	@Column(name = "pid")
	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "orderid")
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "pinyin")
	public String getPinyin() {
		return this.pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Column(name = "iscombine")
	public Integer getIscombine() {
		return this.iscombine;
	}

	public void setIscombine(Integer iscombine) {
		this.iscombine = iscombine;
	}

	@Column(name = "groupallname")
	public String getGroupallname() {
		return this.groupallname;
	}

	public void setGroupallname(String groupallname) {
		this.groupallname = groupallname;
	}

	@Column(name = "opersign")
	public Integer getOpersign() {
		return this.opersign;
	}

	public void setOpersign(Integer opersign) {
		this.opersign = opersign;
	}

	@Column(name = "createtime", length = 0)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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

	@Column(name = "SYNC_SIGN", length = 1)
	public String getSyncSign() {
		return this.syncSign;
	}

	public void setSyncSign(String syncSign) {
		this.syncSign = syncSign;
	}

	@Column(name = "SYNC_DATE", length = 19)
	public String getSyncDate() {
		return this.syncDate;
	}

	public void setSyncDate(String syncDate) {
		this.syncDate = syncDate;
	}

}