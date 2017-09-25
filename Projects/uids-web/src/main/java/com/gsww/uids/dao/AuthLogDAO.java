//package com.gsww.uids.dao;
//
//import com.gsww.uids.entity.JisAuthlog;
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.PagingAndSortingRepository;
//
//public interface AuthLogDAO extends PagingAndSortingRepository<JisAuthlog, Integer>,
//JpaSpecificationExecutor<JisAuthlog>{
//
//  List<JisAuthlog> findAllPerAndAuthLog(int usertype);
//
//  @Modifying
//  @Query("from JisAuthlog where ticket = ?1 and usertype = ?2")
//  public JisAuthlog findByTicket(String ticket,int usertype);
//
//  @Modifying
//  @Query("from JisAuthlog where token = ?1 and usertype = ?2")
//  public JisAuthlog findByToken(String token,int usertype);
//}