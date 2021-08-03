package com.webapp.backend.resources;

import java.util.List;

import com.webapp.backend.mappers.UserMapper;
import com.webapp.backend.models.Users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private UserMapper _userMapper;

    public UserResource(UserMapper userMapper) {
        this._userMapper = userMapper;
    }

    @GetMapping("/all")
    public List<Users> getAll(){
        return _userMapper.FindAll();
    }
}
