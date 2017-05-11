package com.tarena.action.account;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.account.IAccountDAO;
import com.tarena.entity.Account;

/**
 *	查找推荐人数据的Action
 *	根据推荐人的身份证，来查找一个推荐人的Account数据
 */
public class SearchRecommenderAction {

	//input
	private String idcardNo;
	//output
	private Account account;
	
	public String execute() {
		IAccountDAO dao = 
			DAOFactory.getAccountDAO();
		try {
			account = dao.findByIdcardNo(idcardNo);
		} catch (DAOException e) {
			e.printStackTrace();
			/*
			 * 如果调用DAO时发生异常，这里没必要
			 * return "error"，因为当前请求是异步的，
			 * 不希望页面刷新，如果返回error将页面刷新
			 * 则用户体验不好。
			 * 我们可以在发生错误时给account输出属性
			 * 一个空值，页面上如果判断出有空值则给个
			 * 提示就可以了，用户体验会好一些。
			 * */
			account = null;
		}
		return "success";
	}
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getIdcardNo() {
		return idcardNo;
	}
	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}
	
}