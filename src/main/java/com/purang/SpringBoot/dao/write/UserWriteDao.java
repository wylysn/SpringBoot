package com.purang.SpringBoot.dao.write;

import com.purang.SpringBoot.domain.UserEntity;

public interface UserWriteDao {
	void insert(UserEntity user);

	void update(UserEntity user);

	void delete(Long id);
}
