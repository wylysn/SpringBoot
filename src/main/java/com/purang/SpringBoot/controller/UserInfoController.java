package com.purang.SpringBoot.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userList")
    @RequiresPermissions("perms[userInfo:view]")//权限管理;
    public String userInfo(){
        return "user/userInfo";
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("perms[userInfo:add]")//权限管理;
    public String userInfoAdd(){
        return "user/userInfoAdd";
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("perms[userInfo:del]")//权限管理;
    public String userDel(){
        return "user/userInfoDel";
    }
}