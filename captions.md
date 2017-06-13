# Presentation Text
### Intro
Hi, My name is Conor Redmond and this presentation
is a high level introduction to the MyBatis Persistence Framework.

### Is this for you  ?
This presentation will be of interest to you :- 
- if you are a Java Developer  
- writing applications that  access a relational database
- and think that the current approaches using JPA and JDBC are too complicated.

### Contents 
In this presentation we will :-
- Provide a brief introduction to MyBatis 
- And Demonstrate how to build and use a simple  (DAO)  using Mybatis

###  What is MyBatis
MyBatis is a Persistence Framework For Java.
It works on top of JDBC 
but eliminates most of the complexity and boilerplate of using  JDBC  directly.

It works by mapping Java methods to SQL statements

If you provide a correctly annotated DAO/Mapper Interface
Mybatis will use that tobuild an implementation that can execute SQL


we'll see an example of how this works in the next slide
 
  

### &Mapper Annotation 
The mybatis-starter maven dependency for springboot  
includes an auto configuration component . 
 At applicaton startup the auto configuration will 
- scan for a Datasource to work with
- it will also scan for Java Interfaces that include the Mybatis  @Mapper annotation 
   and register those interfaces with MyBatis

- for each  elligible Interface it finds,
 it will   build an implementation of that interface 
   and register it as a spring bean in the spring context.
- In your application code you can inject these MyBatis Mapper beans and call their methods 
- calling the findAll() method on the generated bookMapper bean 
   will cause the "SELECT * FROM BOOKS" SQL that was inside  the @Select annotation
   to be   executed against the database represented by the datasource
- finally the bookMapper bean will attempt to map the results from the SQL query
   into Book objects that are returned to the callern - inject bean name in your code, -> cand call 
  

### @Select Annotation result mapping



in the SQL snippet shown here, 
   the database column "UNAME" has an alias "userName"
   
- MyBatis reuses this   alias feature
- to perform the mapping  of results 
- from database columns
- to fields in  Java objects

- so when mapping the results of a SQL query into a Java Object, 
- the "ID" Column maps to the "id" field
- the "UNAME" column maps the the "userName" field
- and the "LNAME" column maps to the "lastName" field

### MyBatis Demo Intro TODO NEW SLIDE
in the following demo we will use  Mybatis to build  a DAO.

We will create our DAO inside  a Spring boot application that connects to a H2 embedded in-memory database            

there is a link here if you want download the code from github and try it yourself

## DEMO Begins on intellij in presentation mode


#### MyBatis Spring Boot Starter
 
```
pom.xml
```
 This Demo is based on a Springboot application 
 with a Maven build file 
 and so the first thing we'll need to do
is  add the mybatis-spring-boot-starter dependency 
for  MyBatis  .
 

#### Create Tables
```
src/main/resources/schema.sql
```
- next we'll define some database  tables we'll use in this demo.
 
 
#### Populate tables
```
src/main/resources/data.sql
```
and in this file we'll then populate those tables with some data
 

#### The Publisher PPOJO and other model pbjects
```
src\main\java\com\example\model\Publisher.java
```
All the classes in the model package will be used to to store data loaded from the database.
These are plain old java objects 

#### Datasource
In the application.properties file we've setup the properties needed to build  a  datasource that defines our connection to a database server.
In this case we're using an embedded H2 database. 
By Embedded I mean that the database server is embedded in the springboot application 
and the database server starts when the application starts.  
It's also an in-memory database so there is no persistent storage of information across restarts of the application.
This is not something you'd use in production but perfect for a small demonstration like this one
 
 
#### the  Publisher DAO Interface
```
src\main\java\com\example\dao\PublisherMapper.java
```
finally the the  Publisher  Mapper interface ,   defines the signatures of all  the DAO methods  we want to create.

Right now,  this is just an empty interface .

 lets annotate it up so mybatis can   build us an  implementation
                 This
####  AutoConfiguration and  the @Mapper
The first thing to do is to add The @Mapper annotation to register this class with Mybatis.

#### @Select
The Next step is add an @Select annotation that contains the SQL query we want to run on the database
 
 
**TYPE** --> prefix findAll() method  fully qualified alias mapping
```
 @Select("SELECT ID as id,  NAME as name, PHONE as phoneNumber from PUBLISHERS") //SQL
```
This is using an explicit column name to java property name mapping but since the ID and NAME DB colums have the exact same name as the Java model property  
 
we can drop the explict alias mapping 
**TYPE** --> drop unneeded aliases 
```
@Select("SELECT ID,  NAME, PHONE as phoneNumber from PUBLISHERS")
```           
Since we want all the columns in the PUBLISHER table to be returned we can simplify this further using a wild card

**TYPE** --> use wild card 
``` 
@Select("SELECT *, PHONE as phoneNumber from PUBLISHERS")
``` 
 
 For the PHONE column there isn't an exact match to a java property, which means we need to keep the explict alias mapping here

#### Run Test 
Lets see if this works by calling the findAll() method from a UnitTest
In this test    you can see, we're making a call to our findAll() method  that we just setup.

We're injecting a  publisherMapper bean built  by MyBatis  that does all of the SQL execution for us, behind the scenes.

 
and here, we're just going to print the results  of our query that weere returned in this of Publisher object.

 
 
Lets just go ahead and run this.

 
As you can see,  it's returned the values we stored in the database
 
I'm also printing the Class name of the publisherMapper
 
You'll notice it's a Proxy class  
so it's something generated by MyBatis


 
#### @Select with @Param parameters
```
src\main\java\com\example\dao\BookMapper.java
```
Lets take a look at another mapper interface  
In the BookMapper interface there's an example of how pass to pass in parameter values to a SELECT statement

The MyBatis @param annotation, is used to link parameters from the interface method to matching expressions in the SELECT statement
```
  @Select("SELECT * FROM BOOKS WHERE GENRE = #{genre}")
  List<Book> findByGenre(@Param("genre") String genre);
``` 

#### @Select with Groovy Triple quote 
``` 
 src\main\groovy\com\example\dao\UserDao.groovy
```
- if you enable   Groovy compilation in your project  
 and are Using A Groovy interface  for you Mapper 
 then you can use the  handy triple-quote groovy syntax for supplyin a  multi-line value to the @Select annotation  
- This allows you to paste in large SQL queries right into your code. And vice-aversa extract queries from your code and run them in a sql client

#### demo wrap up

- Because of time constraints We only covered the @Select annotation here.
- but there equivalent annotations for the SQL INSERT, UPDATE and DELETE commands.
- there's also a powerful  dynamic sql feature whe=ich we'' cover in the next set of slides'
#Demo End - Slides Begin
 

This Demo   


#Summary 
- That Demo concludes our presentation 

- To recap what we've learned

- Mybatis links Java interface methods to SQL statements

-MyBatis uses SQL directly , there is no generation of SQL like with some alteratives such as JPA 

- Result mapping is based off of existing SQL language constructs like  aliases and wildcards, so there is no new mapping language to learn

- We saw how we could build a Simple DAO with just a few annotations 

- And finally we saw how MyBatis and Groovy work particularly well for multiline SSQL statements 
### Advantages

### Learning Curve
If comparing Mybatis to alternatives like JPA
one advantage over JPA is that
most developers already have some basic SQL skills
so it's very easy to jump into.
The 'alias' based mapping used by MyBatis is already built-in to SQL
Dynamic SQL is straightforward
mapping complex relationships between tables can be built on top of SQL's existing 'JOIN' feature.

### Complex Reports
MyBatis is especially handy to use if you have a lot of predefined SQL reports 
that you need to get into your Java application
Its' very easy to just paste your SQL in to an XML or annotation based mapper

### Debugging
When using JPA, the generated SQL it builds for you
can be hard to find
and even harder to understand.
But With mybatis you always have full visibility to the SQL used, because you wrote it.


###Performance
Sometimes there are specific features of your underlying database
that you need to access in order to maximize performance.

For example you may need to use database specific optimizer hints
or database specifc features for performance reasons

### Review 
- Intro 
- Demo
- 

### Thank You + Resources 
I hope you've found enjoyed this  brief introduction to MyBatis.

Included here is  a link to the code used in the demonstration 

and a link to some other Useful Resources   

Thank You!
 