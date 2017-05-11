package com.tarena.action.role;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.role.IRoleDAO;
import com.tarena.entity.Role;

public class AddRoleAction {
	private Role role;
	public String execute(){
		IRoleDAO dao=DAOFactory.getRoleDAO();
		try {
			dao.insert(role);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}
