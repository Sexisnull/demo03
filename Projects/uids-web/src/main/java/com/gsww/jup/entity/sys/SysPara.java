package com.gsww.jup.entity.sys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 参数
 * @author zhangjy
 */
@Entity
@Table(name = "SYS_PARAMETER")
public class SysPara implements java.io.Serializable{

	private static final long serialVersionUID = -4883107463733534239L;

	// Fields

	private String paraId;
	private SysParaType sysParaType;
	private String paraCode;
	private String paraName;
	private Integer paraSeq;
	private String paraState;

	// Constructors

	/** default constructor */
	public SysPara() {
	}

	/** minimal constructor */
	public SysPara(SysParaType sysParaType, String paraName,
			String paraState) {
//		this.sysParameterType = sysParameterType;
		this.paraName = paraName;
		this.paraState = paraState;
	}

	/** full constructor */
	public SysPara(SysParaType sysParaType, String paraCode,
			String paraName, Integer paraSeq, String paraState) {
//		this.sysParameterType = sysParameterType;
		this.paraCode = paraCode;
		this.paraName = paraName;
		this.paraSeq = paraSeq;
		this.paraState = paraState;
	}

	// Property accessors

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "PARA_ID")
	public String getParaId() {
		return this.paraId;
	}

	public void setParaId(String paraId) {
		this.paraId = paraId;
	}

	@Column(name = "PARA_CODE")
	public String getParaCode() {
		return this.paraCode;
	}

	public void setParaCode(String paraCode) {
		this.paraCode = paraCode;
	}

	@Column(name = "PARA_NAME")
	public String getParaName() {
		return this.paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	@Column(name = "PARA_SEQ")
	public Integer getParaSeq() {
		return this.paraSeq;
	}

	public void setParaSeq(Integer paraSeq) {
		this.paraSeq = paraSeq;
	}

	@Column(name = "PARA_STATE")
	public String getParaState() {
		return this.paraState;
	}

	public void setParaState(String paraState) {
		this.paraState = paraState;
	}

/*	@ManyToOne
	    @JoinColumn(name = "PARA_TYPE_ID")
		public SysParaType getSysParaType() {
			return this.sysParaType;
		}

		public void setSysParameterType(SysParaType sysParaType) {
			this.sysParaType = sysParaType;
		}*/
	@ManyToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name = "paraTypeId")
	public SysParaType getSysParaType() {
		return this.sysParaType;
	}

	public void setSysParaType(SysParaType sysParaType) {
		this.sysParaType = sysParaType;
	}
	
}
