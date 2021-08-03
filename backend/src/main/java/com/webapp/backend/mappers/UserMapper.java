package com.webapp.backend.mappers;

import java.util.List;

import com.webapp.backend.models.Users;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from Users")
    List<Users> FindAll();
}
