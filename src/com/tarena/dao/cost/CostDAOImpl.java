package com.tarena.dao.cost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Cost;
import com.tarena.util.DBUtil;

public class CostDAOImpl implements ICostDAO {
	
	public List<Cost> findAll() throws DAOException {
		List<Cost> list = new ArrayList<Cost>();
		Connection con = DBUtil.getConnection();
		String sql = "select * from cost";
		try {
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Cost c = createCost(rs);
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"查询全部的资费数据失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
		return list;
	}

	private Cost createCost(ResultSet rs) 
		throws SQLException {
		Cost c = new Cost();
		c.setId(rs.getInt("id"));
		c.setName(rs.getString("name"));
		c.setBaseDuration(rs.getInt("base_duration"));
		c.setBaseCost(rs.getDouble("base_cost"));
		c.setUnitCost(rs.getDouble("unit_cost"));
		c.setStatus(rs.getString("status"));
		c.setDescr(rs.getString("descr"));
		c.setCreateTime(rs.getDate("creatime"));
		c.setStartTime(rs.getDate("startime"));
		c.setCostType(rs.getString("cost_type"));
		return c;
	}
	
	public static void main(String[] args) 
		throws Exception {
		ICostDAO dao = new CostDAOImpl();
		Cost c = dao.findById(102);
		c.setName("mm");
		c.setBaseDuration(900);
		c.setBaseCost(90.00);
		c.setUnitCost(0.9);
		c.setCostType("2");
		c.setDescr("MM");
		dao.update(c);
	}

	public List<Cost> findByPage(int page, int pageSize) 
		throws DAOException {
		List<Cost> list = new ArrayList<Cost>();
		Connection con = DBUtil.getConnection();
		String sql = "select * from ( " +
				"select c.*,rownum r from cost2 c " +
				"where rownum<? " +
				") where r>? ";
		try {
			PreparedStatement ps = 
				con.prepareStatement(sql);
			// 小于下一页最小行
			int nextMin = page*pageSize+1;
			ps.setInt(1, nextMin);
			// 大于上一页最大行
			int lastMax = (page-1)*pageSize;
			ps.setInt(2, lastMax);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Cost c = createCost(rs);
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"分页查询资费数据失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
		return list;
	}

	public int findTotalPage(int pageSize) 
		throws DAOException {
		//1.查询总行数
		int rows = 0;
		Connection con = DBUtil.getConnection();
		String sql = "select count(*) from cost2";
		try {
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				rows = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"查询总行数失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
		
		//2.根据总行数，计算总页数
		if(rows % pageSize == 0) {
			return rows/pageSize;
		} else {
			return rows/pageSize+1;
		}
		
	}

	public void delete(int id) throws DAOException {
		Connection con = DBUtil.getConnection();
		String sql = "delete from cost2 where id=?";
		try {
			con.setAutoCommit(false);
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DAOException(
					"删除资费数据失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
	}

	public void insert(Cost cost) 
		throws DAOException {
		if(cost == null) 
			return;
		Connection con = DBUtil.getConnection();
		String sql = "insert into cost2 " +
				"values(cost_seq.nextval,?,?,?,?,'1',?,SYSDATE,null,?)";
		try {
			con.setAutoCommit(false);
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setString(1, cost.getName());
			ps.setObject(2, cost.getBaseDuration());
			ps.setObject(3, cost.getBaseCost());
			ps.setObject(4, cost.getUnitCost());
			ps.setString(5, cost.getDescr());
			ps.setString(6, cost.getCostType());
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DAOException(
					"新增资费数据失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
	}

	public Cost findByName(String name) 
		throws DAOException {
		if(name == null) {
			return null;
		}
		
		Connection con = DBUtil.getConnection();
		String sql = "select * from cost2 where name=?";
		try {
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Cost cost = createCost(rs);
				return cost;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"根据名称查询资费数据失败！",e);
		} finally {
			DBUtil.closeConnection();
		}
		
		return null;
	}

	public Cost findById(int id) 
		throws DAOException {
		Connection con = DBUtil.getConnection();
		String sql = "select * from cost2 where id=?";
		try {
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Cost cost = createCost(rs);
				return cost;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"根据ID查询资费数据失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
		return null;
	}

	public void update(Cost cost) 
		throws DAOException {
		if(cost == null)
			return;
		Connection con = DBUtil.getConnection();
		String sql = "update cost2 set name=?,base_duration=?," +
				"base_cost=?,unit_cost=?,cost_type=?,descr=? " +
				"where id=?";
		try {
			con.setAutoCommit(false);
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setString(1, cost.getName());
			ps.setObject(2, cost.getBaseDuration());
			ps.setObject(3, cost.getBaseCost());
			ps.setObject(4, cost.getUnitCost());
			ps.setString(5, cost.getCostType());
			ps.setString(6, cost.getDescr());
			ps.setObject(7, cost.getId());
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DAOException(
					"修改资费数据失败！",e);
		} finally {
			DBUtil.closeConnection();
		}
	}

}