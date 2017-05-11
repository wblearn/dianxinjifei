package com.tarena.action.service;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.service.IServiceDAO;
import com.tarena.entity.Service;

public class AddServiceAction {
	private Service service;
	public String execute(){
		IServiceDAO dao=DAOFactory.getServiceDAO();
		try {
			dao.insert(service);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	
}
