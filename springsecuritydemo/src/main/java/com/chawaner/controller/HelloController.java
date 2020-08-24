package com.chawaner.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * @PreAuthorize("hasAuthority('add')")
     * 调用此方法要求当前用户必须拥有add权限才能访问
     */
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('add')")
    public String add(){
        System.out.println("add……");
        return "success";
    }

    /**
     * @PreAuthorize("hasRole(ROLE_ADMIN)")
     * 调用此方法要求当前用户必须拥有ADMIN角色才能访问
     * @return
     */
    @RequestMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(){
        System.out.println("delete……");
        return "success";
    }

}
