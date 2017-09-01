package com.gsww.uids.manager.app.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.util.PropertiesUtil;
import com.gsww.common.util.StringUtil;
import com.gsww.uids.manager.app.entity.AppIcon;
import com.gsww.uids.manager.app.entity.AppResource;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.app.service.AppIconService;
import com.gsww.uids.manager.app.service.ApplicationService;
import com.gsww.uids.manager.app.service.ResourceService;

import net.sf.json.JSONArray;

@Service
public class AppIconServiceImpl implements AppIconService
{
  private static final Logger logger = Logger.getLogger(ApplicationServiceImpl.class);
  
  @Autowired
  private HibernateBaseDao hibernateBaseDao;
  
  @Autowired
  private ApplicationService applicationService;
  
  @Autowired
  private ResourceService resourceService;
  
  public AppIcon saveOrUpdate(AppIcon appIcon)
  {
    try
    {
      if (StringUtil.isBlank(appIcon.getUuid()))
      {
        logger.info("应用新增！！！！！！！！！！！");
        this.hibernateBaseDao.save(appIcon);
        return appIcon;
      }
      logger.info("应用编辑！！！！！！！！！！！");
      this.hibernateBaseDao.update(appIcon);
      return appIcon;
    }
    catch (Exception e)
    {
      logger.info("新增或者更新异常：" + e);
    }
    return null;
  }
  
  public String fileUpload(HttpServletRequest request, HttpServletResponse response, ServletFileUpload upload,String iconType)
  {
    Map<String, String> map = new HashMap<String, String>();
    String msgMap = "1";
    String savePath = PropertiesUtil.readValue("/sources.properties", "appIcon");
    File file1 = new File(savePath);
    
    @SuppressWarnings("rawtypes")
	List fileList = null;
    try
    {
      if (file1.exists()) {
        file1.createNewFile();
      } else {
        file1.mkdir();
      }
      fileList = upload.parseRequest(request);
      
      @SuppressWarnings("unchecked")
	Iterator<FileItem> it = fileList.iterator();
      String name = "";
      String nameChange = "";
      String extName = "";
      while (it.hasNext())
      {
        FileItem item = (FileItem)it.next();
        if (item.getSize() > 1048576L)
        {
          msgMap = "2";
          break;
        }
        if (!item.isFormField())
        {
          BufferedImage img = ImageIO.read(item.getInputStream());
          int width = img.getWidth();
          int height = img.getHeight();
          if ((width > 92) || (height > 92))
          {
            msgMap = "3";
            break;
          }
          name = item.getName();
          if ((name != null) && (!name.trim().equals("")))
          {
            if (name.lastIndexOf(".") >= 0) {
              extName = name.substring(name.lastIndexOf("."));
            }
            File file = null;
            do
            {
              nameChange = UUID.randomUUID().toString().replace("-", "");
              file = new File(savePath + "/" + nameChange + extName);
            } while (file.exists());
            File saveFile = new File(savePath + "/" + nameChange + extName);
            AppIcon appIcon = new AppIcon();
            appIcon.setName(name.substring(0, name.lastIndexOf(".")));
            appIcon.setPath(savePath + "/" + nameChange + extName);
            appIcon.setType(iconType);
            AppIcon appIcnSave = saveOrUpdate(appIcon);
            InputStream is = item.getInputStream();
            int buffer = 1024;
            int length = 0;
            byte[] b = new byte[buffer];
            FileOutputStream fos = new FileOutputStream(saveFile);
            while ((length = is.read(b)) != -1) {
              fos.write(b, 0, length);
            }
            fos.flush();
            fos.close();
            map.put("uuid", appIcnSave.getUuid());
            map.put("name", name);
          }
        }
      }
    }
    catch (Exception e)
    {
      logger.info("上传附件错误=====" + e);
    }
    map.put("msgMap", msgMap);
    String jsonChange = JSONArray.fromObject(map).toString();
    return jsonChange.substring(1, jsonChange.length() - 1);
  }
  
  public AppIcon findIconById(String id)
  {
    if (StringUtil.isBlank(id)) {
      return new AppIcon();
    }
    return (AppIcon)this.hibernateBaseDao.getById(AppIcon.class, id);
  }
  
  public void viewIcon(String filePath, HttpServletRequest request, HttpServletResponse response)
  {
    String url = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/") + "static/images/default.jpg";
    try
    {
      FileInputStream is;
      try
      {
        is = new FileInputStream(filePath);
      }
      catch (Exception e)
      {
        filePath = url;
      }
      finally
      {
        is = new FileInputStream(filePath);
      }
      int i = is.available();
      byte[] data = new byte[i];
      is.read(data);
      is.close();
      response.setContentType("image/*");
      response.setContentLength(i);
      String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
      response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
      OutputStream toClient = response.getOutputStream();
      toClient.write(data);
      toClient.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public List<AppIcon> findAll(String iconType)
  {
    StringBuilder query = new StringBuilder(" from ").append(AppIcon.class.getName()).append(" where type = ?");
    return this.hibernateBaseDao.findList(query.toString(),iconType);
  }
  
    @Override
	public boolean checkDelete(String ids, StringBuilder errInfo) {
		for ( String id : ids.split(",") ) {
			AppIcon icon = this.findIconById(id);
			
			// 检查是否有应用引用
			List<Application> apps = applicationService.findListByParams(null, id);
			if ( apps.size() > 0 ) {
				errInfo.append(String.format("系统图标【%s】被应用使用，不能删除！", icon.getName()));
				return false;
			}
			
			// 检查是否有资源引用
			List<AppResource> resources = resourceService.findListByParams(null, id, null);
			if ( resources.size() > 0 ) {
				errInfo.append(String.format("系统图标【%s】被资源使用，不能删除！", icon.getName()));
				return false;
			}
		}
		
		return true;
	}
}
