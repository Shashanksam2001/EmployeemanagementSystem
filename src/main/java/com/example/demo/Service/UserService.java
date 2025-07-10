package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    public boolean register(User user){
         if (repo.findByName(user.getName())!=null) {
        return false;
    }
    // If user does not exist, hash password and save
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    repo.save(user);
    return true;
    }

    public User login(String name,String rpassword){
        User user=repo.findByName(name);
        if(user!=null && bCryptPasswordEncoder.matches(rpassword, user.getPassword())){
            return user;
        }
        return null;
    }
    
    
}

