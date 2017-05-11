package com.tarena.action.admin;

import com.tarena.dao.DAOFactory;
import com.tarena.dao.admin.IAdminDAO;
import com.tarena.entity.Admin;

public class AddAdminAction {
	private Admin admin;
	public String execute(){
		IAdminDAO dao=DAOFactory.getAdminDAO();
		//dao.addAdmin(admin);
		return "success";
	}
}
