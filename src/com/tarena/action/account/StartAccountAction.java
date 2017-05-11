package com.tarena.action.account;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.account.IAccountDAO;

public class StartAccountAction {
	
	//input
	private int id;
	//output
	private boolean ok;
	
	public String execute() {
		IAccountDAO dao = 
			DAOFactory.getAccountDAO();
		try {
			dao.start(id);
			ok = true;
		} catch (DAOException e) {
			e.printStackTrace();
			ok = false;
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