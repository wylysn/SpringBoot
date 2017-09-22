package com.purang.SpringBoot.domain;

import org.springframework.util.Assert;

public class User {
	private Long id;//主键.  
    private String name; 
    
    public User() {
    	
    }
    
    public User(Long id, String name) {
    		Assert.notNull(id, "id must not be null");
    		Assert.hasLength(name, "name must not be empty");
    		this.id = id;
    		this.name = name;
    }
    
    public Long getId() {  
       return id;  
    }  
    public void setId(Long id) {  
       this.id = id;  
    }  
    public String getName() {  
       return name;  
    }  
    public void setName(String name) {  
       this.name = name;  
    } 
}
