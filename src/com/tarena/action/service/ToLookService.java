package com.tarena.action.service;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.service.IServiceDAO;
import com.tarena.entity.Service;

public class ToLookService {
	private Service service;
	private Integer id;
	public String execute(){
		IServiceDAO dao=DAOFactory.getServiceDAO();
		try {
			service=dao.findById(id);
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
