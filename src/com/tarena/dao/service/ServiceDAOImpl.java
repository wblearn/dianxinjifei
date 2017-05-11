package com.tarena.dao.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tarena.dao.DAOException;
import com.tarena.entity.Service;
import com.tarena.util.DBUtil;

public class ServiceDAOImpl implements IServiceDAO{

	public List<Service> findByCondition(String id, String ip,
			String idcardNo, String status, int page, int pageSize)
			throws DAOException {
		// TODO Auto-generated method stub
		
		List<Service> services=new ArrayList<Service>();
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		sql.append("select * from (");
		sql.append("select s.id id,a.id acc_id,a.idcard_no,a.real_name,s.os_username,s.status,s.unix_host,c.name cost_name,rownum r");
		sql.append(" from service2 s inner join account2 a on s.account_id=a.id inner join cost c on s.cost_id=c.id");
		sql.append(" where 1=1 ");
		if(id!=null&&id.length()>0){
			sql.append(" and os_username=?");
			params.add(id);
		}
		if(ip!=null&&ip.length()>0){
			sql.append(" and unix_host=?");
			params.add(ip);
		}
		if(idcardNo!=null&&idcardNo.length()>0){
			sql.append(" and idcard_no=?");
			params.add(idcardNo);
		}
		if(status!=null&&!status.equals("-1")){
			sql.append(" and s.status=?");
			params.add(status);
		}
		sql.append(") where r<? and r>? ");
		int nextMin = page*pageSize+1;
		params.add(nextMin);
		int lastMax = (page-1)*pageSize;
		params.add(lastMax);
		Connection con=DBUtil.getConnection();
		try {
			PreparedStatement ps=con.prepareStatement(sql.toString());
			for(int i=0;i<params.size();i++){
				ps.setObject(i+1, params.get(i));
			}
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Service service=createService(rs);
				services.add(service);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("按条件查询业务帐号失败！",e);
		}	
		
		return services;
	}

	private Service createService(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		Service s=new Service();
		s.setId(rs.getObject("id")==null?null:rs.getInt("id"));
		s.setAccountId(rs.getObject("acc_id")==null?null:rs.getInt("acc_id"));
		s.setIdcardNo(rs.getString("idcard_no"));
		s.setRealName(rs.getString("real_name"));
		s.setOs_username(rs.getString("os_username"));
		s.setStatus(rs.getString("status"));
		s.setIp(rs.getString("unix_host"));
		s.setCostName(rs.getString("cost_name"));
		return s;
	}

	public List<Service> findAll() throws DAOException {
		// TODO Auto-generated method stub
		List<Service> services=new ArrayList<Service>();
		Connection con=DBUtil.getConnection();
		StringBuffer sql=new StringBuffer();
		sql.append("select * ");
		sql.append("from (select s.id id,a.id acc_id,a.idcard_no,a.real_name,");
		sql.append("s.os_username,s.status,s.unix_host,");
		sql.append("c.name cost_name");
		sql.append(" from service2 s inner join account2 a on s.account_id=a.id inner join cost c on s.cost_id=c.id where 1=1)");
		try {
			PreparedStatement ps=con.prepareStatement(sql.toString());
			
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Service service=new Service();
				service=createService(rs);
				services.add(service);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return services;
	}

	public int findTotalPage(String id, String ip, String idcardNo,
			String status, int pageSize) throws DAOException {
		// TODO Auto-generated method stub
		//1.利用sql语句查询总行数rows
		Connection con=DBUtil.getConnection();
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		sql.append("select count(*) from (        ");
		sql.append("select s.id id,a.id acc_id,a.idcard_no,a.real_name,s.os_username,s.status,s.unix_host,c.name cost_name ");
		sql.append(" from service2 s inner join account2 a on s.account_id=a.id inner join cost c on s.cost_id=c.id )");
		sql.append(" where 1=1 ");
		if(id!=null&&!id.equals("")){
			sql.append(" and os_username=?");
			params.add(id);
		}
		if(ip!=null&&ip.length()>0){
			sql.append(" and unix_host=?");
			params.add(ip);
		}
		if(idcardNo!=null&&idcardNo.length()>0){
			sql.append(" and idcard_no=?");
			params.add(idcardNo);
		}
		if(status!=null&&!status.equals("-1")){
			sql.append(" and status ? ");
			params.add(status);
		}
		
		try {
			PreparedStatement ps=con.prepareStatement(sql.toString());
			for(int i=0;i<params.size();i++){
				ps.setObject(i+1, params.get(i));
			}
			
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
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
		//2.利用算法算出总页数
		return 0;
	}

	public void insert(Service service) throws DAOException{
		// TODO Auto-generated method stub
		Connection con=DBUtil.getConnection();
		StringBuffer sql=new StringBuffer();
		//insert into service2 values(service2_seq.nextval,1011,'192.168.0.26','rr','123','0',sysdate,null,null,6)
		sql.append("insert into service2 values(service2_seq.nextval,?,?,?,?,'0',sysdate,null,null,?)");
		try {
			con.setAutoCommit(false);
			PreparedStatement ps=con.prepareStatement(sql.toString());
			ps.setInt(1, service.getAccountId());
			ps.setString(2, service.getIp());
			ps.setString(3, service.getOs_username());
			ps.setString(4, service.getPassword());
			ps.setInt(5, service.getCostId());
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("插入数据到service2表失败&_&",e);
		}
	}

	public void startService(int id) throws DAOException {
		// TODO Auto-generated method stub
		String sql = "update service2 set status='0'," +
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
					"开通业务账号失败！",e);
		} finally {
			DBUtil.closeConnection();
		}
		
	}
	public void pauseService(int id) throws DAOException {
		// TODO Auto-generated method stub
		Connection con=DBUtil.getConnection();
		System.out.println(id);
		String sql="update service2 set status='1',pause_date=sysdate where id=?";
		try {
			con.setAutoCommit(false);
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("暂停业务帐号失败！",e);
		}
	}

	public void deleteService(int id) throws DAOException {
		// TODO Auto-generated method stub
		Connection con=DBUtil.getConnection();
		String sql="update service2 set status='2',close_date=sysdate where id=?";
		try {
			con.setAutoCommit(false);
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeQuery();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("删除业务帐号失败！",e);
		}
	}

	public Service findById(Integer id) throws DAOException {
		// TODO Auto-generated method stub
		Service service=new Service();
		Connection con=DBUtil.getConnection();
		StringBuffer sql=new StringBuffer();
		sql.append("select * ");
		sql.append("from (select s.id id,a.id acc_id,a.idcard_no,a.real_name,");
		sql.append("s.os_username,s.status,s.unix_host,");
		sql.append("c.name cost_name");
		sql.append(" from service2 s inner join account2 a on s.account_id=a.id inner join cost c on s.cost_id=c.id where 1=1)");
		sql.append(" where id=?");
		try {
			PreparedStatement ps=con.prepareStatement(sql.toString());
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				service=createService(rs);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return service;
		
	}



}
