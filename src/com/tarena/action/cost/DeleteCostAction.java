package com.tarena.action.cost;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.cost.ICostDAO;

public class DeleteCostAction {
	
	//input
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String execute() {
		ICostDAO dao = DAOFactory.getCostDAO();
		try {
			dao.delete(id);
			System.out.println("已经删除。。");
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

}