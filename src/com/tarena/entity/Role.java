package com.tarena.entity;

import java.util.List;

public class Role {
	
	private Integer id;
	private String name;
	private String modulesName;//一组模块的名称
	//追加属性，封装选中的模块id
	private List<String> privilegeIds;
	
	public List<String> getPrivilegeIds() {
		return privilegeIds;
	}
	
	public void setPrivilegeIds(List<String> privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	public String getModulesName() {
		return modulesName;
	}

	public void setModulesName(String modulesName) {
		this.modulesName = modulesName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}