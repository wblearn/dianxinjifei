package com.tarena.dao.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Account;
import com.tarena.util.DBUtil;

public class AccountDAOImpl implements IAccountDAO {

//	public List<Account> findByCondition(
//			String idcardNo, 
//			String realName,
//			String loginName, 
//			String status) 
//			throws DAOException {
//		/*
//		 * 定义存储参数值的集合，在拼条件的同时记录其值，
//		 * 那么当所有的条件if都执行完以后，条件中拼了几个
//		 * ？那么集合中就存在几个值，并且该集合中的数据
//		 * 是和？的顺序一致，并且就是对应？的取值。
//		 * */
//		List<Object> params = new ArrayList<Object>();
//		//拼查询SQL
//		StringBuffer sb = new StringBuffer();
//		sb.append("select * from account2 where 1=1 ");
//		if(idcardNo != null 
//				&& idcardNo.length() > 0) {
//			sb.append("and idcard_no=? ");
//			params.add(idcardNo);
//		}
//		if(realName != null 
//				&& realName.length() > 0) {
//			sb.append("and real_name=? ");
//			params.add(realName);
//		}
//		if(loginName != null
//				&& loginName.length() > 0){
//			sb.append("and login_name=? ");
//			params.add(loginName);
//		}
//		if(status != null
//				&& !status.equals("-1")) {
//			//下拉选，默认空选项其value为-1，空选项是不拼条件的
//			sb.append("and status=? ");
//			params.add(status);
//		}
//		
//		//查询
//		List<Account> list = new ArrayList<Account>();
//		Connection con = DBUtil.getConnection();
//		try {
//			PreparedStatement ps = 
//				con.prepareStatement(sb.toString());
//			for(int i=0;i<params.size();i++) {
//				/*
//				 * 设置参数的下标从1开始，而i是从0开始循环，
//				 * 因此这里传入i+1
//				 * */
//				ps.setObject(i+1, params.get(i));
//			}
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()) {
//				Account a = createAccount(rs);
//				list.add(a);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DAOException(
//					"根据条件查询账务账号失败！",e);
//		} finally {
//			DBUtil.closeConnection();
//		}
//		return list;
//	}
	
	public List<Account> findByCondition(
			String idcardNo, 
			String realName,
			String loginName, 
			String status,
			int page,
			int pageSize) 
			throws DAOException {
		/*
		 * 定义存储参数值的集合，在拼条件的同时记录其值，
		 * 那么当所有的条件if都执行完以后，条件中拼了几个
		 * ？那么集合中就存在几个值，并且该集合中的数据
		 * 是和？的顺序一致，并且就是对应？的取值。
		 * */
		List<Object> params = new ArrayList<Object>();
		//拼查询SQL
		StringBuffer sb = new StringBuffer();
		sb.append("select * from (");
		sb.append("select a.*,rownum r from account2 a where 1=1 ");
		if(idcardNo != null 
				&& idcardNo.length() > 0) {
			sb.append("and idcard_no like ? ");
			params.add(idcardNo);
		}
		if(realName != null 
				&& realName.length() > 0) {
			sb.append("and real_name like ? ");
			params.add(realName);
		}
		if(loginName != null
				&& loginName.length() > 0){
			sb.append("and login_name like ? ");
			params.add(loginName);
		}
		if(status != null
				&& !status.equals("-1")) {
			//下拉选，默认空选项其value为-1，空选项是不拼条件的
			sb.append("and status like ? ");
			params.add(status);
		}
		
		sb.append(") where r<? and r>? ");
		int nextMin = page*pageSize+1;
		params.add(nextMin);
		int lastMax = (page-1)*pageSize;
		params.add(lastMax);
		
		//查询
		List<Account> list = new ArrayList<Account>();
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = 
				con.prepareStatement(sb.toString());
			for(int i=0;i<params.size();i++) {
				/*
				 * 设置参数的下标从1开始，而i是从0开始循环，
				 * 因此这里传入i+1
				 * */
				if(params.get(i) instanceof java.lang.String){
					ps.setObject(i+1,"%"+ params.get(i)+"%");
				}else{
					ps.setObject(i+1, params.get(i));
				}
				
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Account a = createAccount(rs);
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"根据条件查询账务账号失败！",e);
		} finally {
			DBUtil.closeConnection();
		}
		return list;
	}
	
	private Account createAccount(ResultSet rs) 
		throws SQLException {
		Account a = new Account();
		a.setId(rs.getInt("id"));
		a.setRecommenderId(rs.getObject("recommender_id")==null?null:rs.getInt("recommender_id"));
		a.setLoginName(rs.getString("login_name"));
		a.setLoginPassword(rs.getString("login_passwd"));
		a.setStatus(rs.getString("status"));
		a.setCreateDate(rs.getDate("create_date"));
		a.setPauseDate(rs.getDate("pause_date"));
		a.setCloseDate(rs.getDate("close_date"));
		a.setRealName(rs.getString("real_name"));
		a.setIdcardNo(rs.getString("idcard_no"));
		a.setBirthdate(rs.getDate("birthdate"));
		a.setGender(rs.getString("gender"));
		a.setOccupation(rs.getString("occupation"));
		a.setTelephone(rs.getString("telephone"));
		a.setEmail(rs.getString("email"));
		a.setMailaddress(rs.getString("mailaddress"));
		a.setZipcode(rs.getString("zipcode"));
		a.setQq(rs.getString("qq"));
		a.setLastLoginTime(rs.getDate("last_login_time"));
		a.setLastLoginIp(rs.getString("last_login_ip"));
		return a;
	}
	
	

	public void pauseAccount(int id) throws DAOException {
		// TODO Auto-generated method stub
		Connection con=DBUtil.getConnection();
		String sql="update account2 set status='1' where id=?";
		String sql2="update service2 set status='1' where account_id=?";
		try {
			con.setAutoCommit(false);
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			PreparedStatement ps2=con.prepareStatement(sql2);
			ps2.setInt(1, id);
			ps2.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("暂停帐务帐号失败！",e);
		}
	}

	public void start(int id) throws DAOException {
		String sql = "update account2 set status='0'," +
				"pause_date=null where id=?";
		Connection con = DBUtil.getConnection();
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
					"开通账务账号失败！",e);
		} finally {
			DBUtil.closeConnection();
		}
	}

	public void deleteAccount(int id) throws DAOException {
		// TODO Auto-generated method stub
		String sql="update account2 set status='2',pause_date=null where id=?";
		String sql2="update service2 set status='2',pause_date=null where account_id=?";
		Connection con=DBUtil.getConnection();
		try {
			con.setAutoCommit(false);
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			PreparedStatement ps2=con.prepareStatement(sql2);
			ps2.setInt(1, id);
			ps2.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new DAOException("删除帐务帐号失败！",e);
		}
	}

	public Account findByIdcardNo(String idcardNo) 
		throws DAOException {
		if(idcardNo == null)
			return null;
		String sql = "select * from account2 " +
				"where idcard_no=?";
		Connection con = DBUtil.getConnection();
		try {
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setObject(1, idcardNo);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Account acc = createAccount(rs);
				return acc;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"根据身份证查询账务账号失败！",e);
		} finally {
			DBUtil.closeConnection();
		}
		return null;
	}
	public void insert(Account account) 
		throws DAOException {
		if(account == null)
			return;
		String sql = "insert into account2 " +
				"values(account_seq.nextval,?,?,?,'0',sysdate,null,null,?,?,?,?,?,?,?,?,?,?,null,null)";
		Connection con = DBUtil.getConnection();
		try {
			con.setAutoCommit(false);
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setObject(1, account.getRecommenderId());
			ps.setObject(2, account.getLoginName());
			ps.setObject(3, account.getLoginPassword());
			ps.setObject(4, account.getRealName());
			ps.setObject(5, account.getIdcardNo());
			ps.setObject(6, account.getBirthdate());
			ps.setObject(7, account.getGender());
			ps.setObject(8, account.getOccupation());
			ps.setObject(9, account.getTelephone());
			ps.setObject(10, account.getEmail());
			ps.setObject(11, account.getMailaddress());
			ps.setObject(12, account.getZipcode());
			ps.setObject(13, account.getQq());
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
					"新增账务账号失败！",e);
		} finally {
			DBUtil.closeConnection();
		}
	}

	public void update(Account account) 
	throws DAOException {
	if(account == null)
		return;
	String sql = "update account2 set real_name=?," +
			"telephone=?,recommender_id=?,email=?," +
			"occupation=?,gender=?,mailaddress=?," +
			"zipcode=?,qq=? where id=?";
	Connection con = DBUtil.getConnection();
	try {
		con.setAutoCommit(false);
		PreparedStatement ps = 
			con.prepareStatement(sql);
		ps.setObject(1, account.getRealName());
		ps.setObject(2, account.getTelephone());
		System.out.println(account.getTelephone());
		ps.setObject(3, account.getRecommenderId());
		System.out.println(account.getRecommenderId());
		ps.setObject(4, account.getEmail());
		ps.setObject(5, account.getOccupation());
		ps.setObject(6, account.getGender());
		ps.setObject(7, account.getMailaddress());
		ps.setObject(8, account.getZipcode());
		ps.setObject(9, account.getQq());
		ps.setObject(10, account.getId());
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
				"修改账务账号失败！",e);
	} finally {
		DBUtil.closeConnection();
	}
	
}

	public Account findById(int id) throws DAOException {
		// TODO Auto-generated method stub
		String sql="select * from account2 where id=?";
		Connection con=DBUtil.getConnection();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				Account acc=createAccount(rs);
				if(acc.getRecommenderId()!=null){
					String sql2="select * from account2 where id=?";
					PreparedStatement ps2=con.prepareStatement(sql2);
					ps2.setInt(1, acc.getRecommenderId());
					ResultSet rs2=ps2.executeQuery();
					if(rs2.next()){
						String idCardNo=rs2.getString(1);
						acc.setRecommenderIdcardNo(idCardNo);
					}
				}
				return acc;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("根据ID查询失败！",e);
		}
		
		
		return null;
	}

	public int findTotalPage(
				String idcardNo, 
				String realName, 
				String loginName, 
				String status, 
				int pageSize) 
			throws DAOException {
			List<Object> params = new ArrayList<Object>();
			//根据条件查询出应该有多少行数据
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) from account2 where 1=1 ");
			if(idcardNo != null
					&& idcardNo.length() > 0) {
				sb.append("and idcard_no=? ");
				params.add(idcardNo);
			} 
			if(realName != null
					&& realName.length() > 0) {
				sb.append("and real_name=? ");
				params.add(realName);
			}
			if(loginName != null
					&& loginName.length() > 0) {
				sb.append("and login_name=? ");
				params.add(loginName);
			}
			if(status != null
					&& !status.equals("-1")) {
				sb.append("and status=? ");
				params.add(status);
			}
		
			Connection con = DBUtil.getConnection();
			try {
				PreparedStatement ps = 
					con.prepareStatement(sb.toString());
				for(int i=0;i<params.size();i++) {
					ps.setObject(i+1, params.get(i));
				}
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					int rows = rs.getInt(1);
					//计算总页数
					if(rows%pageSize==0) {
						return rows/pageSize;
					} else {
						return rows/pageSize+1;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return 0;
	}

	public boolean checkStatusById(int id) throws DAOException {
		// TODO Auto-generated method stub
		Connection con=DBUtil.getConnection();
		String sql="select a.status from account2 a inner join service2 s on a.id=s.account_id where s.id=?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				if(rs.getInt(1)==0){
					return true;
				}else{
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

}