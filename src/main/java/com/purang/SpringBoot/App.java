package com.purang.SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(order = 10) //开启事务，并设置order值，默认是Integer的最大值
//@MapperScan("com.purang.SpringBoot.dao") //for unique datasource
public class App 
{
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(App.class, args);
	}
}
