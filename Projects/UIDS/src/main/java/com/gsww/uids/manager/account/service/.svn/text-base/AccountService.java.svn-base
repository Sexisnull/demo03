package com.gsww.uids.manager.account.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.account.entity.AccountErrorTemp;
import com.gsww.uids.manager.account.entity.AccountMergeTemp;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.entity.UserDetail;

/**
 * 账号业务层接口
 * 
 * @author taolm
 *
 */
@Service
public interface AccountService {
	
	/**
	 * 保存错误信息临时表
	 * @param info
	 * @return
	 */
	boolean saveOrUpdate(AccountErrorTemp info);
	
	/**
	 * 保存合并账号临时表
	 * @param info
	 * @return
	 */
	boolean saveOrUpdate(AccountMergeTemp info);
	
	/**
	 * 清空错误信息临时表，逻辑删除
	 * @return
	 */
	void deleteErrorTemp();
	
	/**
	 * 清空错误信息临时表，逻辑删除
	 * @return
	 */
	void deleteMergeTemp();

	/**
	 * 保存或更新
	 * 
	 * @param info
	 * @return
	 */
	boolean saveOrUpdate(Account info);
	
	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	void delete(String ids);
	
	/**
	 * 查找
	 * 
	 * @param id
	 * @return
	 */
	Account findById(String id);

	/**
	 * 获得分页列表
	 * 
	 * @param type 账号类型
	 * @param name 姓名
	 * @param identity 身份证号
	 * @param corporateName 企业/机构名称
	 * @param accountName 账号名
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	PageObject<Account> findPage(int type, String name, String identity, String corporateName, String accountName, Integer currentPage, Integer pageSize);
	
	/**
     * 检查能否批量删除：如果有一个不能删除，则返回false
     * 
     * @param ids
     * @param errInfo
     * @return
     */
	boolean checkDelete(String ids, StringBuilder errInfo);
	
	/**
	 * 根据所属应用和账号名查找
	 * 
	 * @param appId
	 * @param accountName
	 * @return
	 */
	List<Account> findByAppAndAccountName(String appId, String accountName);
	
	/**
	 * 根据所属应用clientId和账号名查找
	 * 
	 * @param clientId
	 * @param accountName
	 * @return
	 */
	Account findByClientIdAndAccountName(String clientId, String accountName);
	
	/**
	 * 检查账号是否唯一
	 * 
	 * @param info
	 * @param errInfo
	 * @return
	 */
	boolean checkUnique(Account info, StringBuilder errInfo);
	
	/**
	 * 寻找用户下的账号
	 * 
	 * @param userId
	 * @return
	 */
	List<Account> findByUser(String userId);
	
	/**
	 * 新增一个用户并绑定
	 * 
	 * @param accountId
	 * @param newUser
	 * @param newDetail
	 * @return
	 */
	boolean bindNewUser(String accountId, User newUser, UserDetail newDetail);
	
	/**
	 * 绑定某个已存在的自然人用户
	 * 
	 * @param accountId 要绑定的账号
	 * @param userIdentity 要绑定用户的身份证号
	 * @return
	 */
	boolean bindExistNaturalUser(String accountId, String userIdentity);
	
	/**
	 * 绑定某个已存在的法人用户
	 * 
	 * @param accountId
	 * @param orgCode
	 * @return
	 */
	boolean bindExistCorporateUser(String accountId, String orgCode);
	
	/**
	 * 根据Account和AccountDetail中的外键查找记录，主要实体间的引用关系，能否删除某个实体
	 * 
	 * @param userId
	 * @param appId
	 * @return
	 */
	List<Account> findListByParams(String userId, String appId);
	
	/**
	 * 合并之后更新
	 * 
	 * @param tableinfo
	 */
	void mergeAfterUpdate(String tableinfo1);
	
	/**
	 * 查找所有的错误账号信息
	 * @return
	 */
	List<AccountErrorTemp>  findErrAll();
	
	/**
	 * 查找所有的合并账号信息
	 * @return
	 */
	List<AccountMergeTemp> findMergeAll();
	
	/**
	 * 关闭窗口或者取消窗口时，清空临时表
	 * @return 
	 */
	boolean closeWindows();
	
	/**
	 * 验证用户名密码
	 * 
	 * @param appId
	 * @param accountName
	 * @param password
	 * @return
	 */
	boolean validate(String appId, String accountName, String password);
	
	/**
	 * 账号类型与用户类型是否一致
	 * 
	 * @param accountType
	 * @param userType
	 * @return
	 */
	boolean isAccountTypeCoincidentWithUserType(int accountType, int userType);
	
	/**
	 * 查找用户的主账号
	 * 
	 * @param user
	 * @param loginAccount
	 * @param loginApp
	 * @return
	 */
	Account findMainAccount(User user, Account loginAccount, Application loginApp);
	
	/**
	 * 给账号和用户中的敏感信息（身份证号、手机号）增加掩码
	 * 
	 * @param pages
	 * @return
	 */
	PageObject<Account> addMaskToSeceretInfo(PageObject<Account> pages);
}
