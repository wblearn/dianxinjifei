package com.tarena.dao.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tarena.dao.DAOException;
import com.tarena.entity.Admin;
import com.tarena.util.DBUtil;

public class LoginDAOImpl implements ILoginDAO {

	public Admin findByCode(String adminCode) 
		throws DAOException {
		if(adminCode == null)
			return null;
		Admin admin = null;
		Connection con = DBUtil.getConnection();
		String sql = "select * from admin_info2 " +
				"where admin_code=?";
		try {
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setString(1, adminCode);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				admin = createAdmin(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"根据帐号查询管理员失败！", e);
		} finally {
			DBUtil.closeConnection();
		}
		return admin;
	}

	private Admin createAdmin(ResultSet rs) 
		throws SQLException {
		Admin admin = new Admin();
		admin.setId(rs.getInt("id"));
		admin.setAdminCode(rs.getString("admin_code"));
		admin.setPassword(rs.getString("password"));
		admin.setName(rs.getString("name"));
		admin.setTelephone(rs.getString("telephone"));
		admin.setEmail(rs.getString("email"));
		admin.setEnrollDate(rs.getDate("enrolldate"));
		return admin;
	}
	
	public static void main(String[] args) 
		throws Exception {
		ILoginDAO dao = new LoginDAOImpl();
		Admin a = dao.findByCode("lhh");
		System.out.println(a.getName());
	}

}
