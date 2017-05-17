package com.example.dao

import com.example.model.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
public interface UserDao {


    @Select(""" SELECT ID, 
                       FIRST_NAME   as firstName, 
                       LAST_NAME    as lastName, 
                       USER_NAME    as userName, 
                       ACTIVE_SINCE as activeOn 
                FROM USERS
                ORDER BY FIRST_NAME ASC, LAST_NAME ASC """)
    //multiline sql
    List<User> findOrderedUsers();


    @Select("SELECT * FROM USERS")
    //SQL.  property mapUnderscoreToCamelCase=true
    List<User> findAll();


}