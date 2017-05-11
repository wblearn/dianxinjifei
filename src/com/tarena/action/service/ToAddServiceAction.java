package com.tarena.action.service;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.cost.ICostDAO;
import com.tarena.entity.Cost;
import com.tarena.entity.Service;

public class ToAddServiceAction {
	List<Cost> costs;
	Service service;
	public String execute(){
		ICostDAO dao=DAOFactory.getCostDAO();
		try {
			costs=dao.findAll();
			System.out.println(costs.get(1).getId());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	public List<Cost> getCosts() {
		return costs;
	}
	public void setCosts(List<Cost> costs) {
		this.costs = costs;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	
}
