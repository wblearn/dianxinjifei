package com.tarena.dao.role;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Role;

public interface IRoleDAO {
	public List<Role> findByPage(int page,int pageSize) throws DAOException;
	public int findTotalPage(int pageSize) throws DAOException;
	public void insert(Role role) throws DAOException;
	public Role findById(int id) throws DAOException;
	public void update(Role role) throws DAOException;
}
