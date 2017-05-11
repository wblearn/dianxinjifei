package com.tarena.dao.cost;

import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Cost;

/**
 *	资费模块DAO接口
 */
public interface ICostDAO {
	
	/**
	 * 查询全部的资费数据
	 */
	List<Cost> findAll() throws DAOException;
	
	/**
	 * 分页查询资费数据
	 * @param page 当前页
	 * @param pageSize 页容量
	 * @return
	 * @throws DAOException
	 */
	List<Cost> findByPage(int page, int pageSize) 
		throws DAOException;
	
	/**
	 * 查询一共有多少页：
	 * 计算规则：
	 * 	1.rows/pageSize能整除，则返回rows/pageSize
	 *  2.否则有余数返回rows/pageSize+1
	 *  
	 * @param pageSize 页容量
	 * @return
	 * @throws DAOException
	 */
	int findTotalPage(int pageSize)
		throws DAOException;
	
	/**
	 * 删除一条资费数据
	 * @param id
	 * @throws DAOException
	 */
	void delete(int id) throws DAOException;
	
	/**
	 * 新增资费数据
	 * @param cost
	 * @throws DAOException
	 */
	void insert(Cost cost) throws DAOException;

	/**
	 * 根据名称查询资费数据
	 * @param name
	 * @return
	 * @throws DAOException
	 */
	Cost findByName(String name) throws DAOException;
	
	/**
	 * 根据ID查询资费数据
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	Cost findById(int id) throws DAOException;
	
	/**
	 * 修改资费数据
	 * @param cost
	 * @throws DAOException
	 */
	void update(Cost cost) throws DAOException;
	
}