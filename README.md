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


Enable the H2 database web console in src/main/resources/application.properties

```
# enable H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2
```



Also in the same application.properties file 
define a embedded in memory H2 datasource

```
# in memory embedded database. resets after app closes
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
```

Start the App
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

## Initialize Datasource with Data
Back in the code we can Initialize  our  datasource with a schema
by adding a file called schema.sql into the src/main/resources directory

```
CREATE TABLE if not EXISTS BOOKS (
  ID            INT          NOT NULL  AUTO_INCREMENT PRIMARY KEY
  ,ISBN         VARCHAR(13)  NOT NULL  CONSTRAINT books_isbn_uc UNIQUE
  ,AUTHOR       VARCHAR(255) NOT NULL
  ,TITLE        VARCHAR(255) NOT NULL
  ,DESCRIPTION  VARCHAR(750) NOT NULL
  ,GENRE	    VARCHAR(255) NOT NULL
  ,PRICE        DOUBLE       NOT NULL
  ,PUBLISHER    VARCHAR(255)
  ,PUBLISHED_ON DATE
);
```


http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#howto-initialize-a-database-using-spring-jdbc

Populate the BOOKS table by including a data.sql file in src/main/resources

```
INSERT INTO BOOKS (ISBN,          TITLE,            AUTHOR,        GENRE,  PRICE, DESCRIPTION )
values('1430241071', 'Pro Spring 3'  , 'Clarence Ho', 'Java',  19.99, 'Pro Spring 3 updates the bestselling Pro Spring with the latest that the Spring Framework has to offer');

INSERT INTO BOOKS (ISBN,          TITLE,    AUTHOR,        GENRE,  PRICE, DESCRIPTION )
values('161729120X', 'Spring In Action'  , 'Craig Walls', 'Java',  29.99, 'Spring in Action, Fourth Edition is a hands-on guide to the Spring Framework, updated for version 4. ');

INSERT INTO BOOKS (ISBN,          TITLE,       AUTHOR,            GENRE,  PRICE, DESCRIPTION )
values('193239415X', 'Hibernate In Action'  , 'Christian Bauer ', 'Java',  9.99, 'Hibernate in Action carefully explains the concepts you need, then gets you going.');

INSERT INTO BOOKS (ISBN,          TITLE,       AUTHOR,            GENRE,  PRICE, DESCRIPTION )
values('1931520720', 'Stories of Your Life and Others'  , 'Ted Chiang', 'Science Fiction',  12.25, ' includes his first eight published stories plus the authorFirstName''s story notes ');
```

After restarting the springboot app and loging into the H2 console
you should see a new BOOK table with 4 entries

# MyBatis Spring Boot Starter
Mybatis provider a spring boot starter to simplify mybatis setup 

http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/

```
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>
```