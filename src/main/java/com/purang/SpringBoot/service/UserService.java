package com.purang.SpringBoot.service;

import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.purang.SpringBoot.annotation.ReadDataSource;
import com.purang.SpringBoot.annotation.WriteDataSource;
import com.purang.SpringBoot.dao.UserDao;
import com.purang.SpringBoot.domain.UserEntity;
import com.purang.SpringBoot.utils.MD5Util;
import com.purang.SpringBoot.utils.MyLogger;
import com.purang.SpringBoot.utils.SpringContextUtil;

/** 
 * 如果需要事务，自行在方法上添加@Transactional 
 * 如果方法有内部有数据库操作，则必须指定@WriteDataSource还是@ReadDataSource 
 *  
 * 注：AOP ，内部方法之间互相调用时，如果是this.xxx()这形式，不会触发AOP拦截，可能会 
 * 导致无法决定数据库是走写库还是读库 
 * 方法： 
 * 为了触发AOP的拦截，调用内部方法时，需要特殊处理下，看方法getService() 
 *  
 * @author imac 
 * 
 */
@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private RedisTemplate redisTemplate;
	
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
		String key = MD5Util.textToMD5L32("user_"+id);
		ValueOperations<String, UserEntity> operations = redisTemplate.opsForValue();
		
		//缓存存在
		boolean haskey = redisTemplate.hasKey(key);
		if (haskey) {
			UserEntity userEntity = operations.get(key);
			MyLogger.info("UserService.findById():从redis缓存中获取了用户 >> "+userEntity.toString());
			return userEntity;
		}
		
		UserEntity u = this.userDao.getOne(id);
		
		//插入缓存
		operations.set(key, u, 10, TimeUnit.MINUTES);
		
		return u;
	}
	
	@ReadDataSource
	public UserEntity findByUserName(String userName){
		
		UserEntity u = this.userDao.getUserByName(userName);
		
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
