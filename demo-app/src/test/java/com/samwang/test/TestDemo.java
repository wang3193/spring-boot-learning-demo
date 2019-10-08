package com.samwang.test;

import com.samwang.conf.Configure;
import com.samwang.conf.WebConfigure;
import com.samwang.service.sys.SysService;
import com.samwang.sys.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class) //导入spring测试框架[2]
@SpringBootTest
@DisplayName("Test Demo")
public class TestDemo {

    @Autowired
    private SysService sysService;

    @Autowired
    private Configure configure;

    @Autowired
    private WebConfigure webConfigure;

    @Test
    @DisplayName("Add user")
    public void test(){
//        User user = new User();
//        user.setName("FromJunit");
//        User newOne = sysService.save(user);
//        assertAll("Insert User Success.",
//                () -> assertNotNull(newOne),
//                () -> assertNotNull(newOne.getId()));
    }

    @Test
    public void test1(){
        System.out.println(configure.toString());
    }

    @Test
    public void test2(){
        System.out.println(webConfigure.toString());
    }
}
