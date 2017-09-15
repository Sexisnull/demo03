package com.gsww.uids.service;

/**
 * JisUserdetailService
 * com.gsww.uids.service
 *
 * @author xiaoyy
 * @Date 2017-09-15 下午2:42
 * You have to believe in yourself. That‘s the secret of success.
 */
public interface JisUserdetailService {

    //动态addUserField
    public void addUserField(String fieldName) throws Exception;

    //动态delete UserField
    public void delUserField(String fieldName) throws Exception;

}
