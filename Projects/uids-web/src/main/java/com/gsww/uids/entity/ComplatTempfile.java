package com.gsww.uids.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ComplatTempfile entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "complat_tempfile", catalog = "uidsdx")
public class ComplatTempfile implements java.io.Serializable {

	// Fields

	private String uuid;
	private String oldname;
	private String newname;
	private String filetype;
	private String loginname;
	private Date uploaddate;
	private Integer filesize;
	private String tmppath;

	// Constructors

	/** default constructor */
	public ComplatTempfile() {
	}

	/** minimal constructor */
	public ComplatTempfile(String uuid) {
		this.uuid = uuid;
	}

	/** full constructor */
	public ComplatTempfile(String uuid, String oldname, String newname,
			String filetype, String loginname, Date uploaddate,
			Integer filesize, String tmppath) {
		this.uuid = uuid;
		this.oldname = oldname;
		this.newname = newname;
		this.filetype = filetype;
		this.loginname = loginname;
		this.uploaddate = uploaddate;
		this.filesize = filesize;
		this.tmppath = tmppath;
	}

	// Property accessors
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 32)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "oldname")
	public String getOldname() {
		return this.oldname;
	}

	public void setOldname(String oldname) {
		this.oldname = oldname;
	}

	@Column(name = "newname")
	public String getNewname() {
		return this.newname;
	}

	public void setNewname(String newname) {
		this.newname = newname;
	}

	@Column(name = "filetype")
	public String getFiletype() {
		return this.filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	@Column(name = "loginname")
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "uploaddate", length = 0)
	public Date getUploaddate() {
		return this.uploaddate;
	}

	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}

	@Column(name = "filesize")
	public Integer getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

	@Column(name = "tmppath")
	public String getTmppath() {
		return this.tmppath;
	}

	public void setTmppath(String tmppath) {
		this.tmppath = tmppath;
	}

}