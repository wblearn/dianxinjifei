package com.tarena.action.role;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.role.IRoleDAO;
import com.tarena.entity.Role;

public class FindRoleAction {
	List<Role> roles;
	int page=1;
	int pageSize;
	int totalPage;
	public String execute(){
		IRoleDAO dao=DAOFactory.getRoleDAO();
		try {
			roles=dao.findByPage(page, pageSize);
			totalPage=dao.findTotalPage(pageSize);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
}
