package com.tarena.action.role;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.role.IRoleDAO;
import com.tarena.entity.Privilege;
import com.tarena.entity.Role;
import com.tarena.util.PrivilegeReader;

public class ToUpdateRoleAction {
	//input
	private int id;
	//output
	private Role role;
	private List<Privilege> privileges;
	public String execute(){
		IRoleDAO dao=DAOFactory.getRoleDAO();
		try {
			role=dao.findById(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		privileges=PrivilegeReader.getPrivileges();
		return "success";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}
	
}
