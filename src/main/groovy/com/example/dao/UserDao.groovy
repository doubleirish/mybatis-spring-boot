package com.example.dao

import com.example.model.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
public interface UserDao {

  @Select("SELECT * FROM USERS") //SQL
  List<User> findAll( );

  @Select(""" SELECT ID, FIRST_NAME, LAST_NAME, USER_NAME, ACTIVE_SINCE as activeOn FROM USERS
              ORDER BY FIRST_NAME asc, LAST_NAME asc """)   //multiline sql
  List<User> findOrderedUsers();

}