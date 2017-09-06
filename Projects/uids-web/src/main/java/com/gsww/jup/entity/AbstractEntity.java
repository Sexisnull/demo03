package com.gsww.jup.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = -535568459886606682L;

    protected Map vo;

    protected AbstractEntity() {
    	vo = new HashMap();
    }

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
		final AbstractEntity other = (AbstractEntity) obj;
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
     * 
     * @param key
     * @param value
     */
    public void addProperty(String key, Object value) {
	if (vo == null)
	    vo = new HashMap();
		vo.put(key, value);
    }

    /**
     * 从VO里取到指定key的对象值
     * 
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
