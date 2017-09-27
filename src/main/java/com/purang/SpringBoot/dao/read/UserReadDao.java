package com.purang.SpringBoot.dao.read;

import java.util.List;

import com.purang.SpringBoot.domain.UserEntity;

public interface UserReadDao {
	List<UserEntity> getAll();
	
	UserEntity getOne(Long id);
}
