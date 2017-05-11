package com.tarena.action.login;

import org.omg.CORBA.Request;

import com.tarena.action.BaseAction;
import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.login.ILoginDAO;
import com.tarena.entity.Admin;

public class LoginAction extends BaseAction {
	
	//input
	private String adminCode;
	private String password;
	private String imageCode;
	//output
	private String errorMsg;

	public String execute() {
		//1.校验验证码
		String code = (String) session.get("imageCode");
		if(code == null ||
				!code.equalsIgnoreCase(imageCode)) {
			//验证码不匹配，校验失败
			errorMsg = "验证码错误！";
			return "fail";
		}
		
		//2.根据账号查询管理员
		ILoginDAO dao = DAOFactory.getLoginDAO();
		Admin admin = null;
		try {
			admin = dao.findByCode(adminCode);
		} catch (DAOException e) {
			e.printStackTrace();
			errorMsg = "查询管理员报错，请联系系统管理员！";
			return "fail"; //异常，返回登录页面
		}
		
		if(admin == null) {
			//3.如果没有对应的管理员，登录失败，账号不存在
			errorMsg = "账号不存在！";
			return "fail"; //验证失败，回登录页面
		} else {
			//4.如果有对应管理员，看密码，若密码错误，登录失败
			String pwd = admin.getPassword();
			if(!pwd.equals(password)) {
				errorMsg = "密码错误！";
				return "fail"; //验证失败，回登录页面
			}
		}
		//4.登录成功后，将登录信息记录到Session
		session.put("admin", adminCode);
		return "success"; //成功，去index.jsp
	}
	
	public String getAdminCode() {
		return adminCode;
	}

	public void setAdminCode(String adminCode) {
		this.adminCode = adminCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
	
}