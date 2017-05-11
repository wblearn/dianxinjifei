package com.tarena.dao.admin;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Admin;

public interface IAdminDAO {

	List<Admin> findByPage(int page, int pageSize) throws DAOException;

	List<Admin> findAll() throws DAOException;

	void update(Admin admin) throws DAOException;

	void addAdmin(Admin admin) throws DAOException;
	
}
