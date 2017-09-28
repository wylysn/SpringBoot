package com.purang.SpringBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.purang.SpringBoot.annotation.ReadDataSource;
import com.purang.SpringBoot.annotation.WriteDataSource;
import com.purang.SpringBoot.dao.UserDao;
import com.purang.SpringBoot.domain.UserEntity;
import com.purang.SpringBoot.utils.SpringContextUtil;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@WriteDataSource
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void insertUser(UserEntity u){
		this.userDao.insert(u);;
	
		//如果类上面没有@Transactional,方法上也没有，哪怕throw new RuntimeException,数据库也会成功插入数据
	//	throw new RuntimeException("测试插入事务");
	}
	/**
	 * 写事务里面调用读
	 * @param u
	 */
	public void wirteAndRead(UserEntity u){
		getService().insertUser(u);//这里走写库，那后面的读也都要走写库
		//这是刚刚插入的
		UserEntity uu = getService().findById(u.getId());
		System.out.println("==读写混合测试中的读(刚刚插入的)====id="+u.getId()+",  user_name=" + uu.getUserName());
		//为了测试,3个库中id=1的user_name是不一样的
		UserEntity uuu = getService().findById(new Long(1));
		System.out.println("==读写混合测试中的读====id=1,  user_name=" + uuu.getUserName());
		
	}
	
	public void readAndWirte(UserEntity u){
		//为了测试,3个库中id=1的user_name是不一样的
		UserEntity uu = getService(). findById(new Long(1));
		System.out.println("==读写混合测试中的读====id=1,user_name=" + uu);
		getService().insertUser(u);
		
	}
	
	@ReadDataSource
	public UserEntity findById(Long id){
		UserEntity u = this.userDao.getOne(id);
		return u;
	}
	
	private UserService getService(){
		// 采取这种方式的话，
		//@EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true)
		//必须设置为true
	/*	if(AopContext.currentProxy() != null){
			return (UserService)AopContext.currentProxy();
		}else{
			return this;
		}
		*/
		return SpringContextUtil.getBean(this.getClass());
	}
}