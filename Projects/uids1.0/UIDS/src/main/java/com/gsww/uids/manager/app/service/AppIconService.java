package com.gsww.uids.manager.app.service;

import com.gsww.uids.manager.app.entity.AppIcon;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public abstract interface AppIconService
{
  /**
   * 保存图标信息
   * @param paramAppIcon
   * @return
   */
  public abstract AppIcon saveOrUpdate(AppIcon paramAppIcon);
  
  /**
   * 图标上传
   * @param request
   * @param response
   * @param upload
   * @param iconType
   * @return
   */
  public  String fileUpload(HttpServletRequest request, HttpServletResponse response, ServletFileUpload upload,String iconType);
  
  /**
   * 根据图标id查询图标信息
   * @param id
   * @return
   */
  public  AppIcon findIconById(String id);
  
  /**
   * 浏览图标
   * @param filePath
   * @param request
   * @param response
   */
  public  void viewIcon(String filePath, HttpServletRequest request, HttpServletResponse response);
  
  /**
   * 根据图标类型查询图标
   * @param iconType
   * @return
   */
  public  List<AppIcon> findAll(String iconType);
  
  /**
   * 检查能否批量删除：如果有一个不能删除，则返回false
   * 
   * @param ids
   * @param errInfo
   * @return
   */
	boolean checkDelete(String ids, StringBuilder errInfo);
	
	
}
