package com.gsww.uids.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 组织机构实体
 * @author zhanglei
 *
 */
@Entity
@Table(name = "COMPLAT_GROUP")
public class ComplatGroup implements java.io.Serializable{
	
	// Fields    

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer iid;          //机构Id
    private String name;          //机构名称
    private Integer nodetype;     //节点类型(1-区域2-单位3-部门或处室4-下属单位)
    private String codeid;        //机构编码顶级目录为”001” (如:甘肃省卫生厅编码为 001002，每3位数字代表一个组织.001代表甘肃省，002代表卫生厅)
    private String orgcode;       //组织机构代码(如甘肃省发展和改革委员会为013896483)
    private String orgtype;       //组织机构类型参考《GB/T 20091-2006 组织机构类型》
    private Integer areatype;     //区域类型(1-省级2-市（州）级3-区县4-乡镇街道5-其他)
    private String areacode;      //区域编码12位(如兰州市为620200000000)
    private String suffix;        //机构后缀 (如: 甘肃公安厅gat.gs)
    private String spec;          //机构描述
    private Integer pid;          //父机构id
    private Integer orderid;      //排序号
    private String pinyin;        //拼音首字母
    private Integer iscombine;    //是否为合并机构(0-否 1-是)
    private String groupallname;  //机构全名
    private Integer opersign;     //操作状态位(1-插入2-修改3-删除)
    private Timestamp createtime; //创建时间(yyyy-MM-dd HH:mm:ss)
    private Timestamp modifytime; //修改时间(yyyy-MM-dd HH:mm:ss)
    private Integer synState;     //0-未备份1-备份失败2-备份成功


   // Constructors

   /** default constructor */
   public ComplatGroup() {
   }

   
   /** full constructor */
   public ComplatGroup(String name, Integer nodetype, String codeid, String orgcode, String orgtype, Integer areatype, String areacode, String suffix, String spec, Integer pid, Integer orderid, String pinyin, Integer iscombine, String groupallname, Integer opersign, Timestamp createtime, Timestamp modifytime, Integer synState) {
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
   }

  
   
   @GeneratedValue(strategy = GenerationType.IDENTITY)  
   @Id 
   @Column(name="iid", unique=true, nullable=false)
   public Integer getIid() {
       return this.iid;
   }
   
   public void setIid(Integer iid) {
       this.iid = iid;
   }
   
   @Column(name="name", length=100)

   public String getName() {
       return this.name;
   }
   
   public void setName(String name) {
       this.name = name;
   }
   
   @Column(name="nodetype")

   public Integer getNodetype() {
       return this.nodetype;
   }
   
   public void setNodetype(Integer nodetype) {
       this.nodetype = nodetype;
   }
   
   @Column(name="codeid")

   public String getCodeid() {
       return this.codeid;
   }
   
   public void setCodeid(String codeid) {
       this.codeid = codeid;
   }
   
   @Column(name="orgcode", length=50)

   public String getOrgcode() {
       return this.orgcode;
   }
   
   public void setOrgcode(String orgcode) {
       this.orgcode = orgcode;
   }
   
   @Column(name="orgtype")

   public String getOrgtype() {
       return this.orgtype;
   }
   
   public void setOrgtype(String orgtype) {
       this.orgtype = orgtype;
   }
   
   @Column(name="areatype")

   public Integer getAreatype() {
       return this.areatype;
   }
   
   public void setAreatype(Integer areatype) {
       this.areatype = areatype;
   }
   
   @Column(name="areacode", length=12)

   public String getAreacode() {
       return this.areacode;
   }
   
   public void setAreacode(String areacode) {
       this.areacode = areacode;
   }
   
   @Column(name="suffix")

   public String getSuffix() {
       return this.suffix;
   }
   
   public void setSuffix(String suffix) {
       this.suffix = suffix;
   }
   
   @Column(name="spec")

   public String getSpec() {
       return this.spec;
   }
   
   public void setSpec(String spec) {
       this.spec = spec;
   }
   
   @Column(name="pid")

   public Integer getPid() {
       return this.pid;
   }
   
   public void setPid(Integer pid) {
       this.pid = pid;
   }
   
   @Column(name="orderid")

   public Integer getOrderid() {
       return this.orderid;
   }
   
   public void setOrderid(Integer orderid) {
       this.orderid = orderid;
   }
   
   @Column(name="pinyin")

   public String getPinyin() {
       return this.pinyin;
   }
   
   public void setPinyin(String pinyin) {
       this.pinyin = pinyin;
   }
   
   @Column(name="iscombine")

   public Integer getIscombine() {
       return this.iscombine;
   }
   
   public void setIscombine(Integer iscombine) {
       this.iscombine = iscombine;
   }
   
   @Column(name="groupallname")

   public String getGroupallname() {
       return this.groupallname;
   }
   
   public void setGroupallname(String groupallname) {
       this.groupallname = groupallname;
   }
   
   @Column(name="opersign")

   public Integer getOpersign() {
       return this.opersign;
   }
   
   public void setOpersign(Integer opersign) {
       this.opersign = opersign;
   }
   
   @Column(name="createtime", length=0)

   public Timestamp getCreatetime() {
       return this.createtime;
   }
   
   public void setCreatetime(Timestamp createtime) {
       this.createtime = createtime;
   }
   
   @Column(name="modifytime", length=0)

   public Timestamp getModifytime() {
       return this.modifytime;
   }
   
   public void setModifytime(Timestamp modifytime) {
       this.modifytime = modifytime;
   }
   
   @Column(name="synstate")

   public Integer getSynState() {
       return this.synState;
   }
   
   public void setSynState(Integer synState) {
       this.synState = synState;
   }
   
}
