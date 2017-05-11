package com.tarena.action.service;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.service.IServiceDAO;

public class DeleteServiceAction {
	private boolean ok;
	private int id;
	public String execute(){
		IServiceDAO dao=DAOFactory.getServiceDAO();
		try {
			dao.deleteService(id);
			ok=true;
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ok=false;
		}
		return "success";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
}
