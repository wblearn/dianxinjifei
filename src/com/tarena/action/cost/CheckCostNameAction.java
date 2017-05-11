package com.tarena.action.cost;

import com.tarena.dao.DAOException;
import com.tarena.dao.DAOFactory;
import com.tarena.dao.cost.ICostDAO;
import com.tarena.entity.Cost;

public class CheckCostNameAction {
	
	//input
	private String name; //资费名
	//output
	private boolean repeat; //是否重复
	
	public String execute() {
		ICostDAO dao = DAOFactory.getCostDAO();
		try {
			Cost cost = dao.findByName(name);
			if(cost == null) {
				//查询不到结果，说明名称没重复
				repeat = false;
			} else {
				//查询到结果，说明名称重复
				repeat = true;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isRepeat() {
		return repeat;
	}
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

}