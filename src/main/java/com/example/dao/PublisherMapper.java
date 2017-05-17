package com.example.dao;

import com.example.model.Book;
import com.example.model.Publisher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PublisherMapper {

  @Select("SELECT ID as id,  NAME, PHONE as phoneNumber from PUBLISHERS") //SQL
  List<Publisher> findAll();


}

