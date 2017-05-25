package com.example.dao

import com.example.model.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
public interface UserDao {

    // an eaxmple of a multi-line sql using groovy triple -quote
    @Select(""" SELECT ID, 
                       FIRST_NAME   as firstName, 
                       LAST_NAME    as lastName, 
                       USER_NAME    as userName, 
                       ACTIVE_SINCE as activeOn 
                FROM USERS
                ORDER BY FIRST_NAME ASC, LAST_NAME ASC """)
    List<User> findOrderedUsers();

    @Select("SELECT * FROM USERS")
    List<User> findAll();
}