package com.example.dao

import com.example.model.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
public interface UserDao {

  @Select("SELECT * FROM USERS") //SQL
  List<User> findAll( );

  @Select(""" SELECT * FROM USERS
              ORDER BY FIRSTNAME asc, LASTNAME asc """)   //multiline sql
  List<User> findOrderedUsers();

}