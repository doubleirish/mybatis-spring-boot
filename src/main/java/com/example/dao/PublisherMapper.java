package com.example.dao;

import com.example.model.Publisher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
 public interface PublisherMapper {
  @Select("SELECT *, PHONE as phoneNumber from PUBLISHERS") //SQL
  List<Publisher> findAll();

//  === DB ===
//  CREATE TABLE IF NOT EXISTS PUBLISHERS  (
//     ID     INT          NOT NULL AUTO_INCREMENT  PRIMARY KEY
//    ,NAME   VARCHAR(255) NOT NULL CONSTRAINT PUBLISHERS_NAME_UC UNIQUE
//    ,PHONE  VARCHAR(30));

//  === Model ===
//  public class Publisher {
//    private Integer id ;
//    private String  name;
//    private String  phoneNumber;
}

