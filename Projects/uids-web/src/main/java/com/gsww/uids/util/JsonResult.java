package com.gsww.uids.util;

import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class JsonResult
{
  private boolean a = false;

  private String b = null;

  private String c = null;

  private Map<String, Object> d = new HashMap<String, Object>(3);

  public static JsonResult getInstance()
  {
    return new JsonResult();
  }

  public boolean isSuccess() {
    return this.a;
  }

  public JsonResult setSuccess(boolean paramBoolean) {
    this.a = paramBoolean;
    return this;
  }

  public String getMessage() {
    return this.b;
  }

  public JsonResult setMessage(String paramString) {
    this.b = paramString;
    return this;
  }

  @Deprecated
  public JsonResult successAdd()
  {
    this.b = SpringUtil.getMessage("add.success");
    this.a = true;
    return this;
  }

  @Deprecated
  public JsonResult failAdd()
  {
    this.b = SpringUtil.getMessage("add.fail");
    this.a = false;
    return this;
  }

  @Deprecated
  public JsonResult successModify()
  {
    this.b = SpringUtil.getMessage("modify.success");
    this.a = true;
    return this;
  }

  @Deprecated
  public JsonResult failModify()
  {
    this.b = SpringUtil.getMessage("modify.fail");
    this.a = false;
    return this;
  }

  @Deprecated
  public JsonResult successRemove()
  {
    this.b = SpringUtil.getMessage("remove.success");
    this.a = true;
    return this;
  }

  @Deprecated
  public JsonResult failRemove()
  {
    this.b = SpringUtil.getMessage("remove.fail");
    this.a = false;
    return this;
  }

  @Deprecated
  public JsonResult successOperation()
  {
    this.b = SpringUtil.getMessage("opr.success");
    this.a = true;
    return this;
  }

  @Deprecated
  public JsonResult successOperation(String paramString)
  {
    this.b = SpringUtil.getMessage(paramString);
    this.a = true;
    return this;
  }

  @Deprecated
  public JsonResult failOperation()
  {
    this.b = SpringUtil.getMessage("opr.fail");
    this.a = false;
    return this;
  }

  @Deprecated
  public JsonResult failOperation(String paramString)
  {
    this.b = SpringUtil.getMessage("opr.fail");
    this.a = false;
    return this;
  }

  public JsonResult set(int paramResultState)
  {
    switch (paramResultState) {
    case 1:
      this.a = true;
      this.b = "新增成功";
      break;
    case 2:
      this.a = false;
      this.b = "新增失败";
      break;
    case 3:
      this.a = true;
      this.b = "更新成功";
      break;
    case 4:
      this.a = false;
      this.b = "更新失败";
      break;
    case 5:
      this.a = true;
      this.b = "删除成功";
      break;
    case 6:
      this.a = false;
      this.b = "删除失败";
      break;
    case 7:
      this.a = true;
      this.b = "操作成功";
      break;
    case 8:
      this.a = false;
      this.b = "操作失败";
    }
    return this;
  }

  public String getCode() {
    return this.c;
  }

  public JsonResult setCode(String paramString) {
    this.c = paramString;
    return this;
  }

  public Map<String, Object> getParams() {
    return this.d;
  }

  public JsonResult addParam(String paramString, Object paramObject)
  {
    if (StringUtil.isNotEmpty(paramString)) {
      this.d.put(paramString, paramObject);
    }
    return this;
  }
}