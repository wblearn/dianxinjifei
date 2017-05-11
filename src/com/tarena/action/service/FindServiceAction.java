package com.tarena.action.service;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.service.IServiceDAO;
import com.tarena.entity.Service;

public class FindServiceAction {
	private List<Service> services;
//	private Integer id;
//	private String ip;
//	private String idcardNo;
//	private String status;
//	private Integer page=1;
//	private Integer pageSize;
	private String id;
	private String ip;
	private String idcardNo;
	private String status;
	private int page=1;
	private int pageSize;
	private int totalPage;
	public String execute(){
		IServiceDAO dao=DAOFactory.getServiceDAO();
		
		try {
			services=dao.findByCondition(id, ip, idcardNo, status, page, pageSize);
			totalPage=dao.findTotalPage(id,ip,idcardNo,status,pageSize);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	public List<Service> getServices() {
		return services;
	}
	public void setServices(List<Service> services) {
		this.services = services;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIdcardNo() {
		return idcardNo;
	}
	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
