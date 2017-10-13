package com.purang.SpringBoot.domain;

import java.io.Serializable;

public class SysPermissionInit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String url;
	private String permissionInit;
	private Integer sort;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPermissionInit() {
		return permissionInit;
	}
	public void setPermissionInit(String permissionInit) {
		this.permissionInit = permissionInit;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
