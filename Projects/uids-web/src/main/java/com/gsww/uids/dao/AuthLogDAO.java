package com.gsww.uids.dao;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.gsww.uids.entity.JisAuthlog;
import java.util.List;

public class AuthLogDAO extends BaseJdbcDAO<Integer, JisAuthlog>
{
  public List<JisAuthlog> findAllPerAndAuthLog(int userType)
  {
    String sql = getEntitySql() + " WHERE userType = :userType";
    Query query = createQuery(sql);
    query.addParameter("userType", Integer.valueOf(userType));
    List<JisAuthlog> autoLogList = super.queryForEntities(query);
    return autoLogList;
  }

  public JisAuthlog findByTicket(String ticket,int usertype)
  {
    String sql = getEntitySql() + " WHERE ticket = :ticket And usertype = :usertype";
    Query query = createQuery(sql);
    query.addParameter("ticket", ticket);
    query.addParameter("usertype", usertype);
    JisAuthlog autoLog = (JisAuthlog)queryForEntity(query);
    return autoLog;
  }

  public boolean updateAuthLog(JisAuthlog authLog)
  {
    UpdateSql sql = new UpdateSql("jis_authlog");
    sql.addString("token", authLog.getToken());
    sql.addString("spec", authLog.getSpec());
    sql.addInt("state", authLog.getState());
    sql.setWhere("iid = :iid");
    sql.addWhereParamInt("iid", authLog.getIid());

    return super.update(sql);
  }

  public JisAuthlog findByToken(String token,int usertype)
  {
    String sql = getEntitySql() + " WHERE token = :token And usertype = :usertype";
    Query query = createQuery(sql);
    query.addParameter("token", token);
    query.addParameter("usertype", usertype);
    JisAuthlog autoLog = (JisAuthlog)queryForEntity(query);
    return autoLog;
  }
}