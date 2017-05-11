package com.tarena.dao.service;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Service;

public interface IServiceDAO {
	//查询全部的。仅仅用来测试
	public List<Service> findAll() throws DAOException;
	//按条件查询，用于显示给用户的
	public List<Service> findByCondition(String id, String ip, String idcardNo,
			String status, int page, int pageSize) throws DAOException;
	//查询出总页数，分页时使用迭代器，1---totalPage
	public int findTotalPage(String id, String ip, String idcardNo,
			String status, int pageSize) throws DAOException;
	public void insert(Service service) throws DAOException;
//	public void pause() throws DAOException;
//	public void start() throws DAOException;
	//开通业务方法
	public void startService(int id) throws DAOException;
	//暂停
	public void pauseService(int id) throws DAOException;
	public void deleteService(int id) throws DAOException;
	public Service findById(Integer id) throws DAOException;
}
