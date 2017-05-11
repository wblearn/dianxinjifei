package com.tarena.action.service;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.account.IAccountDAO;

public class CheckAccStatusAction {
	private int id;
	private boolean ok;
	public String execute(){
		IAccountDAO dao=DAOFactory.getAccountDAO();
		try {
			ok=dao.checkStatusById(id);
			
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
