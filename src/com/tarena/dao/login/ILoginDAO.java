package com.tarena.dao.login;

import com.tarena.dao.DAOException;
import com.tarena.entity.Admin;

public interface ILoginDAO {
	
	Admin findByCode(String adminCode)
		throws DAOException;

}
