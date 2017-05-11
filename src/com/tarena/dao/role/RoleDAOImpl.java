package com.tarena.dao.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.tarena.dao.DAOException;
import com.tarena.entity.Role;
import com.tarena.util.DBUtil;
import com.tarena.util.PrivilegeReader;


public class RoleDAOImpl implements IRoleDAO {

	public List<Role> findByPage(
			int page, int pageSize) 
			throws DAOException {
		List<Role> list = new ArrayList<Role>();
		Connection con = DBUtil.getConnection();
		//1.先查询出某一页的角色
		String sql = "select * from (" +
				"select r.*,rownum n from role_info2 r " +
				") where n<? and n>?";
		try {
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setInt(1, page*pageSize+1);
			ps.setInt(2, (page-1)*pageSize);
			ResultSet rs = ps.executeQuery();
			//2.遍历角色
			while(rs.next()) {
				Role vo = new Role();
				vo.setId(rs.getInt("id"));
				vo.setName(rs.getString("name"));
				//3.查出某个角色对应的权限ID
				String sql2 = "select privilege_id " +
						"from role_privilege2 where role_id=?";
				PreparedStatement ps2 = 
					con.prepareStatement(sql2);
				ps2.setInt(1, vo.getId());
				ResultSet rs2 = ps2.executeQuery();
				String nameStr = "";
				while(rs2.next()) {
					String privilegeId = 
						rs2.getString("privilege_id");
					//4.根据权限ID查询模块名
					String pname = PrivilegeReader
						.getPrivilegeNameById(privilegeId);
					//5.将一组模块名拼成字符串，给对应的角色
					nameStr += "、" + pname;
				}
				if(nameStr.length() > 0) {
					nameStr = nameStr.replaceFirst("、", "");
				}
				
				vo.setModulesName(nameStr);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"查询角色信息失败！",e);
		} finally {
			DBUtil.closeConnection();
		}
		
		return list;
	}
	
	public static void main(String[] args) 
		throws Exception {
		IRoleDAO dao = new RoleDAOImpl();
		List<Role> vos = dao.findByPage(2, 2);
		for(Role vo : vos) {
			System.out.println(
				vo.getId() + " " +
				vo.getName() + " " +
				vo.getModulesName()
			);
		}
	}

	public int findTotalPage(int pageSize) 
		throws DAOException {
		// TODO 同学们自行完成，参考资费管理代码
		String sql="select distinct count(*) from role_info2";
		Connection con=DBUtil.getConnection();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				int rows=rs.getInt(1);
				if(rows%pageSize==0){
					return rows/pageSize;
				}else{
					return rows/pageSize+1;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public void insert(Role role) throws DAOException {
		if(role == null)
			return;
		//新增角色数据
		String sql = "insert into role_info2 " +
				"values(role_seq2.nextval,?)";
		Connection con = DBUtil.getConnection();
		try {
			con.setAutoCommit(false);
			String[] columns = {"id"};//指定返回的字段
			PreparedStatement ps = 
				con.prepareStatement(sql, columns);
			//mysql中的写法
//			PreparedStatement ps = 
//				con.prepareStatement(sql, 
//						PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, role.getName());
			ps.executeUpdate();
			
			//获取新增的角色ID，结果集中返回的列就是前面通过数组指定的列
			ResultSet rs = ps.getGeneratedKeys();
			Integer roleId = null;
			if(rs.next()) {
				roleId = rs.getInt(1);
			}
			
			//插入到中间表
			List<String> privilegeIds = role.getPrivilegeIds();
			if(privilegeIds != null 
					&& privilegeIds.size() > 0) {
				String sql2 = "insert into role_privilege2 " 
					+ "values(?,?)";
				PreparedStatement ps2 = 
					con.prepareStatement(sql2);
				for(String privilegeId : privilegeIds) {
					ps2.setInt(1, roleId);
					ps2.setInt(2, Integer.valueOf(privilegeId));
					ps2.addBatch();
				}
				ps2.executeBatch();
			}
			
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DAOException(
					"新增角色失败！",e);
		} finally {
			DBUtil.closeConnection();
		}
	}

	public Role findById(int id) throws DAOException {
		//查询角色
		String sql = "select * from role_info2 where id=?";
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));
				//查询权限数据
				String sql2 = "select privilege_id from " +
						"role_privilege2 where role_id=?";
				PreparedStatement ps2 = 
					con.prepareStatement(sql2);
				ps2.setInt(1, id);
				ResultSet rs2 = ps2.executeQuery();
				List<String> list = new ArrayList<String>();
				while(rs2.next()) {
					list.add(rs2.getString(1));
				}
				role.setPrivilegeIds(list);
				return role;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void update(Role role) 
	throws DAOException {
	if(role == null)
		return;
	//更新角色
	String sql = "update role_info2 set name=? " +
			"where id=?";
	Connection con = DBUtil.getConnection();
	try {
		con.setAutoCommit(false);
		PreparedStatement ps = 
			con.prepareStatement(sql);
		ps.setString(1, role.getName());
		ps.setInt(2, role.getId());
		ps.executeUpdate();
		
		//删除当前角色对应的中间表数据
		String sql2 = "delete from role_privilege2 " +
				"where role_id=?";
		PreparedStatement ps2 = 
			con.prepareStatement(sql2);
		ps2.setInt(1, role.getId());
		ps2.executeUpdate();
		
		//插入中间表数据
		List<String> privilegeIds = role.getPrivilegeIds();
		if(privilegeIds != null 
				&& privilegeIds.size() > 0) {
			String sql3 = "insert into role_privilege2 " 
				+ "values(?,?)";
			PreparedStatement ps3 = 
				con.prepareStatement(sql3);
			for(String privilegeId : privilegeIds) {
				ps3.setInt(1, role.getId());
				ps3.setInt(2, Integer.valueOf(privilegeId));
				ps3.addBatch();
			}
			ps3.executeBatch();
		}
		
		con.commit();
	} catch (SQLException e) {
		e.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		throw new DAOException("更新角色失败！",e);
	} finally {
		DBUtil.closeConnection();
	}
}


}