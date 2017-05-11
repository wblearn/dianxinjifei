package com.tarena.dao.account;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Account;

public interface IAccountDAO {
	
	/**
	 * 根据条件查询账务账号
	 * @param idcardNo 身份证
	 * @param realName 姓名
	 * @param loginName 登录名
	 * @param status 状态
	 * @return
	 * @throws DAOException
	 */
	void pauseAccount(int id) throws DAOException;
	
	void start(int id) throws DAOException;
	void deleteAccount(int id) throws DAOException;
	Account findByIdcardNo(String idcardNo) throws DAOException;
	void insert(Account account) throws DAOException;
	void update(Account account) throws DAOException;
	Account findById(int id) throws DAOException;
	List<Account> findByCondition(String idcardNo, String realName,String loginName, String status,
			int page,int pageSize) throws DAOException;

	int findTotalPage(String idcardNo, String realName, String loginName,
			String status,int page) throws DAOException;

	boolean checkStatusById(int id) throws DAOException;
}