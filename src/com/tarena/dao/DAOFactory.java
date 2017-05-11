package com.tarena.dao;

import com.tarena.dao.account.AccountDAOImpl;
import com.tarena.dao.account.IAccountDAO;
import com.tarena.dao.admin.AdminDAOImpl;
import com.tarena.dao.admin.IAdminDAO;
import com.tarena.dao.cost.CostDAOImpl;
import com.tarena.dao.cost.ICostDAO;
import com.tarena.dao.login.ILoginDAO;
import com.tarena.dao.login.LoginDAOImpl;
import com.tarena.dao.role.IRoleDAO;
import com.tarena.dao.role.RoleDAOImpl;
import com.tarena.dao.service.IServiceDAO;
import com.tarena.dao.service.ServiceDAOImpl;

/**
 *	DAO工厂类
 */
public class DAOFactory {
	
	private static ICostDAO costdao = 
		new CostDAOImpl();
	
	private static ILoginDAO logindao = 
		new LoginDAOImpl();
	private static IAccountDAO accdao=
		new AccountDAOImpl();
	private static IServiceDAO serdao=
		new ServiceDAOImpl();
	private static IRoleDAO roledao=
		new RoleDAOImpl();
	private static IAdminDAO admindao
		=new AdminDAOImpl();
	/**
	 * @return ICostDAO实例
	 */
	public static ICostDAO getCostDAO() {
		return costdao;
	}
	
	/**
	 * @return ILoginDAO实例
	 */
	public static ILoginDAO getLoginDAO() {
		return logindao;
	}


	public static IAccountDAO getAccountDAO() {
		// TODO Auto-generated method stub
		return accdao;
	}

	public static IServiceDAO getServiceDAO() {
		// TODO Auto-generated method stub
		return serdao;
	}
	public static IRoleDAO getRoleDAO(){
		return roledao;
	}
	public static IAdminDAO getAdminDAO(){
		return admindao;
	}

}