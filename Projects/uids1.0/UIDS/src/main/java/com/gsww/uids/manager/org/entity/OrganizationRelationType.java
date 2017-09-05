package com.gsww.uids.manager.org.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;
/**
 * 机构分类实体类
 * @author jinwei
 *
 */
@Entity
@Table(name = "UIDS_ORGANIZATION_RELATION_TYPE")
public class OrganizationRelationType extends PO {

	private static final long serialVersionUID = -6898632106703845333L;
	
	/**
	 * 机构分类名称
	 */
	@Column(name = "CLASSIFY_NAME", nullable = false, length = 32)
	private String name;
	
	/**
	 * 机构分类编码
	 */
	@Column(name = "CLASSIFY_CODE", length = 32)
	private String code;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME", length = 23)
	private String createTime;
	
	/**
	 * 逻辑删除
	 */
	@Column(name="IS_DELETE")
	private int del = 0;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}
}
