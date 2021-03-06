package com.samwang.controller.sys;

import com.samwang.service.sys.SysService;
import com.samwang.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys")
public class SysController {

    @Autowired
    private SysService sysService;

    @GetMapping()
    public User helloWorld(){
        User user = new User();
        user.setName("our name");
        return user;
    }


    @GetMapping("/list")
    public List<User> list(){
        return sysService.list();
    }

    @PostMapping("/add")
    public User add(){
        User user = new User();
        user.setName("test");
        return sysService.save(user);
    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable Long id){
        return sysService.get(id);
    }
}
