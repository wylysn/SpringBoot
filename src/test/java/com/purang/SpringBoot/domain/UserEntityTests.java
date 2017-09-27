package com.purang.SpringBoot.domain;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.purang.SpringBoot.dao.read.UserReadDao;
import com.purang.SpringBoot.dao.write.UserWriteDao;
import com.purang.SpringBoot.enums.UserSexEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEntityTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
//	@Test
//	public void createWhenUserNameIsNullShouldThrowException() throws Exception {
//		this.thrown.expect(IllegalArgumentException.class);
//		this.thrown.expectMessage("name must not be empty");
//		new User(new Long(1), null);
//	}
	
//	@Autowired
//	private UserMapper userMapper;
//	@Test
//	@Rollback
//	public void findByName() throws Exception {
//		UserEntity u1 = new User();
//		u1.setName("AAA");
//		u1.se
//		userMapper.insert("AAA", 20);
//		User u = userMapper.findByName("AAA");
//		Assert.assertEquals(20, u.getAge().intValue());
//	}
	
//	@Autowired
//	private UserDao UserMapper;
	
	@Autowired
	private UserWriteDao userWriteDao;
	
	@Autowired
	private UserReadDao userReadDao;

	@Test
	public void testInsert() throws Exception {
		userWriteDao.insert(new UserEntity("aa", "a123456", UserSexEnum.MAN));
		userWriteDao.insert(new UserEntity("bb", "b123456", UserSexEnum.WOMAN));
		userWriteDao.insert(new UserEntity("cc", "b123456", UserSexEnum.WOMAN));

		Assert.assertEquals(3, userReadDao.getAll().size());
	}

	@Test
	public void testQuery() throws Exception {
		List<UserEntity> users = userReadDao.getAll();
		if(users==null || users.size()==0){
			System.out.println("is null");
		}else{
			System.out.println(users.toString());
		}
	}
	
	
	@Test
	public void testUpdate() throws Exception {
		UserEntity user = userReadDao.getOne(6l);
		System.out.println(user.toString());
		user.setNickName("neo");
		userWriteDao.update(user);
		Assert.assertTrue(("neo".equals(userReadDao.getOne(6l).getNickName())));
	}
}
