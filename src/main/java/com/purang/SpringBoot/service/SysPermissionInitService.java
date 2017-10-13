package com.purang.SpringBoot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purang.SpringBoot.annotation.ReadDataSource;
import com.purang.SpringBoot.dao.SysPermissionInitDao;
import com.purang.SpringBoot.domain.SysPermissionInit;

@Service
public class SysPermissionInitService {
	@Autowired
	private SysPermissionInitDao sysPermissionInitDao;
	
	@ReadDataSource
	public List<SysPermissionInit> getAll(){
		
		List<SysPermissionInit> list = sysPermissionInitDao.getAll();
		
		return list;
	}
}
