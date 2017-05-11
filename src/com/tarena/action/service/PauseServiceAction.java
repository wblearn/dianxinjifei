package com.tarena.action.service;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.service.IServiceDAO;

public class PauseServiceAction {
	private int id;
	private boolean ok;
	public String execute(){
		IServiceDAO dao=DAOFactory.getServiceDAO();
		try {
			dao.pauseService(id);
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
