package com.gsww.uids.service.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.entity.ComplatOutsideuser;
import com.gsww.uids.service.ComplatOutsideuserService;
import com.gsww.uids.service.JisLogService;
import com.gsww.uids.service.impl.AuthLogServiceImpl;

@Transactional
@Service("JisOutSideUserService")
public class JisOutSideUserService
{

  @Autowired
  private ComplatOutsideuserService outsideUserService;

  @Autowired
  private JisLogService logService;
  
  public boolean addOutUserForReg(ComplatOutsideuser outsideUser, String appmark)
    throws Exception
  {
    if (outsideUser == null) {
      return false;
    }
    boolean isSuccess = false;
    String loginName = outsideUser.getLoginName();

    Integer userId = null;

    isSuccess = this.outsideUserService.insert(outsideUser);
    if (!isSuccess) {
      throw new Exception("插入平台表失败！");
    }
    //userId = this.outsideUserService.findByLoginName(loginName).getIid();
    ComplatOutsideuser complatOutsideuser = this.outsideUserService.findByLoginName(loginName);

    this.logService.add(Integer.valueOf(1), Integer.valueOf(10), loginName);
    return isSuccess;
  }

 
}
