package com.tarena.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Admin;
import com.tarena.entity.Role;
import com.tarena.util.DBUtil;
import com.tarena.util.PrivilegeReader;

public class AdminDAOImpl implements IAdminDAO {

	public List<Admin> findByPage(int page, int pageSize) throws DAOException {
		// TODO Auto-generated method stub

		List<Admin> admins = new ArrayList<Admin>();
		Connection con = DBUtil.getConnection();
		//1.先查询出某一页的管理员
		String sql = "select * from (select v.*,rownum r from adminrole_v v) where r<? and r>?";
		try {
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setInt(1, page*pageSize+1);
			ps.setInt(2, (page-1)*pageSize);
			ResultSet rs = ps.executeQuery();
			//2.遍历管理员
			while(rs.next()) {
				if(rs.getObject("id")!=null){
					Admin a = new Admin();
					a.setId(rs.getInt("id"));
					a.setAdminCode(rs.getString("admin_code"));
					a.setName(rs.getString("admin_code"));
					a.setTelephone(rs.getString("telephone"));
					a.setEmail(rs.getString("email"));
					a.setEnrollDate(rs.getDate("enrolldate"));
					a.setRoleName(rs.getString("role_name"));
					System.out.println(rs.getInt("id"));
					System.out.println(rs.getString("role_name"));
					admins.add(a);	
				}		
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"查询管理员信息失败！",e);
		} finally {
			DBUtil.closeConnection();
		}
		
		return admins;
	
	}

	public List<Admin> findAll() throws DAOException {
		// TODO Auto-generated method stub
		List<Admin> admins=new ArrayList<Admin>();
		String sql="select distinct * from adminrole_v";
		Connection con=DBUtil.getConnection();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			//2.遍历管理员
			while(rs.next()) {
				Admin a = new Admin();
				a.setId(rs.getInt("id"));
				a.setAdminCode(rs.getString("admin_code"));
				a.setName(rs.getString("admin_code"));
				a.setTelephone(rs.getString("telephone"));
				a.setEmail(rs.getString("email"));
				a.setEnrollDate(rs.getDate("enrolldate"));
				a.setRoleName(rs.getString("role_name"));
				admins.add(a);	
					
				}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("查询管理员失败！",e);
		}
		return admins;
	}

	public void update(Admin admin) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	public void addAdmin(Admin admin) throws DAOException {
		Connection con = DBUtil.getConnection();
		
			try {
				con.setAutoCommit(false);
				// 新增角色
				String sql = "insert into ADMIN_INFO values(admin_seq2.nextval,?,?,?,?,?,SYSDATE)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, admin.getAdminCode());
				ps.setString(2, admin.getPassword());
				ps.setString(3, admin.getName());
				ps.setString(4, admin.getTelephone());
				ps.setString(5, admin.getEmail());
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new DAOException("添加管理员失败！",e);
			}
			
		
			
	}
	
	

}
