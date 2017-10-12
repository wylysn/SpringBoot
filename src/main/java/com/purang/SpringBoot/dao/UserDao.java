package com.purang.SpringBoot.dao;

import java.util.List;

import org.assertj.core.error.ShouldBeEqualToIgnoringFields;

import com.purang.SpringBoot.domain.UserEntity;

/**
 * 单数据源使用
 * @author imac
 *
 */
public interface UserDao {
	List<UserEntity> getAll();
	
	UserEntity getOne(Long id);
	
	UserEntity getUserByName(String username);

	void insert(UserEntity user);

	void update(UserEntity user);

	void delete(Long id);
}
