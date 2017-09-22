package com.purang.SpringBoot.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserEntityTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void createWhenUserNameIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("name must not be empty");
		new User(new Long(1), null);
	}
}
