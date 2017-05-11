package com.tarena.action.admin;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.admin.IAdminDAO;
import com.tarena.entity.Admin;

public class ToFindAdminAction {
	private List<Admin> admins;
//	private int page=1;
//	private int pageSize;
	
	public String execute(){
		IAdminDAO dao=DAOFactory.getAdminDAO();
		try {
			//admins=dao.findByPage(page,pageSize);
			admins=dao.findAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public List<Admin> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Admin> admins) {
		this.admins = admins;
	}

	
	
}
