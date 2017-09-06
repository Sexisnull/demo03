package com.gsww.jup;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称�?数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 * 
 * @author calvin
 */
//JPA Entity基类的标�?
@MappedSuperclass
public abstract class IdEntity implements java.io.Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3483465248088937901L;
	/** 对象标识�?*/
	private String key;
	protected Map vo=new HashMap();
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name="KEY", length=32)
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Transient
	public Map getVo() {
		return vo;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((vo == null) ? 0 : vo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final IdEntity other = (IdEntity) obj;
		if (vo == null) {
			if (other.vo != null)
				return false;
		} else if (!vo.equals(other.vo))
			return false;
		return true;
	}

	public void setVo(Map vo) {
		this.vo = vo;
	}
	
	/**
	 * 给VO里添加一个属性
	 * @param key
	 * @param value
	 */
	public void addProperty(String key, Object value) {
		if (vo == null) vo = new HashMap();
		vo.put(key, value);
	}
	
	/**
	 * 从VO里取到指定key的对象值
	 * @param key
	 * @return
	 */
	public Object getProperty(String key) {
		return vo.get(key);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
