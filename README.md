# mybatis-spring-boot
Describes how we built up the skeleton of this simple mybatis demo app  

If you have the Spring CLI installed 
```
https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-installing-spring-boot.html#getting-started-installing-the-cli
```
then the following will scaffold a simple skeleton application.
```
spring init -dweb,actuator,hateoas,h2,jdbc    mybatis-spring-boot
```

# MyBatis Spring Boot Starter
Mybatis provides a spring boot starter to simplify mybatis setup and configuration.
For more info see
http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/

```
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.0</version>
</dependency>
```

# Setup H2 in-memory datasource

Also in the same application.properties file 
define a embedded in memory H2 datasource

```
# in memory embedded database. resets after app closes
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
```
##  Enable H2 Web Console
Enable the H2 database web console in src/main/resources/application.properties

```
# enable H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2
```

To Verify H2 setup Start the App
```
mvn spring-boot:run 
```

Open up the H2 admin console
```
http://localhost:8080/h2
```
login using 
JDBC url =jdbc:h2:mem:testdb
username=sa  
password should be empty

There should just be a INFORMATION_SCHEMA direatory and a Users section
You can close the 

## Create Tables
Back in the code we can Initialize  our datasource with a schema that will be executed at application startup
Just add your DDL to a special file called  schema.sql into the src/main/resources directory
(obviously you would not normally do this for production apps)

```
CREATE TABLE IF NOT EXISTS PUBLISHERS  (
  ID               INT          NOT NULL AUTO_INCREMENT  PRIMARY KEY
  ,NAME            VARCHAR(255) NOT NULL CONSTRAINT PUBLISHERS_NAME_UC UNIQUE
  ,PHONE           VARCHAR(30)
);

```


http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#howto-initialize-a-database-using-spring-jdbc
## Populate tables
Populate the PUBLISHERS table by including a data.sql file in src/main/resources

```
INSERT INTO PUBLISHERS (  NAME, PHONE   )
values( 'Manning' ,'(425) 555-1212');

INSERT INTO PUBLISHERS (  NAME, PHONE   )
values( 'Apress' ,'(206) 555-1234');
```

After restarting the springboot app and logging into the H2 console
you should see a new PUBLISHERS table with data populated.




## Add a Publisher Java Bean
```
package com.example.model;
public class Publisher {
  private Integer id ;
  private String  name;
  private String  phoneNumber;
  // TODO getters, setters and toString
}
```
## Add a Publisher Mapper (aka Dao) Interface
Mybatis based DAOs are often called Mappers. 
so feel free to use the term interchangeably in this context
```
 package com.example.dao;
 //imports 

 public interface PublisherMapper {

   List<Publisher> findAll();
 }
``` 

## Add @Mapper  and @Select MyBatis annotations
this is where we can use MyBatis to connect between the Java and Database worlds 
```
@Mapper
 public interface PublisherMapper {
   @Select("SELECT ID as id,  NAME, PHONE as phoneNumber from PUBLISHERS") //SQL
   List<Publisher> findAll();
 }
```    


## Verify your Mybatis Mapper implementation works
``` 
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
 
  @Autowired
  PublisherMapper publisherMapper;

  @Test
  public void findPublishers() {
    List<Publisher> publishers = publisherMapper.findAll();
    System.out.println(publishers);
    assertThat(publishers.size(),is(greaterThan(0)));
  }
}
```
