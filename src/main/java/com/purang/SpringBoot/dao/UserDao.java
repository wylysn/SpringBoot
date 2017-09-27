package com.purang.SpringBoot.dao;

import java.util.List;

import com.purang.SpringBoot.domain.UserEntity;

/**
 * 单数据源使用
 * @author imac
 *
 */
public interface UserDao {
	List<UserEntity> getAll();
	
	UserEntity getOne(Long id);

	void insert(UserEntity user);

	void update(UserEntity user);

	void delete(Long id);
}
