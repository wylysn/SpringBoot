package com.purang.SpringBoot.dao;

import java.util.List;

import com.purang.SpringBoot.domain.UserEntity;

public interface UserMapper {
	List<UserEntity> getAll();
	
	UserEntity getOne(Long id);

	void insert(UserEntity user);

	void update(UserEntity user);

	void delete(Long id);
}
