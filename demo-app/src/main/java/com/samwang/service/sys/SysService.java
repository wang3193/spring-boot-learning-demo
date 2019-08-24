package com.samwang.service.sys;

import com.samwang.sys.User;
import com.samwang.sys.rep.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysService {

    @Autowired
    private UserRepository userRepository;


    public User save(User user){
        return userRepository.save(user);
    }

    public List<User> list(){
        return userRepository.findAll();
    }


}
