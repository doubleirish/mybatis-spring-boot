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
- Demonstrate how to build a simple MyBatis based Data Access Object (DAO)
- Examine some of the other useful features of Mybatis like Dynamic SQL  
- And See the situation where MyBatis really shines.



###  What is MyBatis

- MyBatis is a persistence framework that is built  on top of the Java JDBC API.

- The purpose of Mybatis is to reduce the complexity of storing and retrieving data from a relational database .
- It hides away a lot of the low level complexity you would normally encounter when working with JDBC directly.

- MyBatis is similar to Hibernate/JPA in that they both help simplify the interaction with the database
- But where JPA takes an Object Centric approach and generates  SQL behind the scenes ,
  MyBatis instead works with SQL directly and relies on the features of the SQL language , 
  specifically aliases to simplify mapping between DB tables and Java Beans.
 
  

### @Select Annotation 
 Mybatis cleverly utilizes the  built-in "alias" feature in SQL 
 to help define the mapping between COLUMN_NAMES shown her in UPPERCASE and Java Bean properties shown in CamelCase

- For example in the SQL shown, the database column "UNAME" has an alias "userName"
- MyBatis reuses this same native SQL alias feature
- to perform the mapping
- from database columns
- to fields in  Java objects
- so when mapping the results of a SQL query into a Java Object, ...
- the "ID" Column maps to the "id" field
- the "UNAME" column maps the the "userName" field
- and the "LNAME" column maps to the "lastName" field


### MyBatis Demo Intro TODO NEW SLIDE
- So Thats the general theory,  lets take a look at some code examples. 

  
There's essentially three main Areas in the Demo :-
- On one side we have a database and tables containing data
- On the Application side ( a springboot App in this case)  we have Jave Beans that we'd like to load that data into.
- and we're going to use Mybatis to bridge between the database and our application model objects.

Because of time limitations, I'm going to focus mainly on the MyBatis interactions
 and  skim over Springboot application setup and Database setup. 

- The code for the example shown next is available for download from github 
- The link is here

if you want a more detailed walkthrough on how to setup from scratch
I recommend stepping through the  README markdown   file in the github demo link shown here .


## DEMO Begins on intellij in presentation mode


#### MyBatis Spring Boot Starter 
```
pom.xml
```
We're using a Springboot application and so the first thing you'll need 
is to add a dependency for  MyBatis into your build file. 
in this case we're using maven
I recomment using the  mybatis starter dependency you see here. 

[ctrl-click to internals of dependency]

This package   includes an auto-configuration component which will reduce the amount of mybatis configuration required

we've also added a dependency for a H2 database. 
We'll use this to setup an embedded  in-memory database 
which we'll be using in our demo.
 

#### Setup H2 in-memory datasource 
```
src/main/resources/application.properties
```
This is where can can setup our connection to a database.
 
In this case we are defining an embedded in-memory database for ease of setup for demonstrative purpise



#### Create Tables
```
src/main/resources/schema.sql
```
- This file defines the schema of the tables we're using for this test.
 
<!--
SpringBoot has a feature where we can Initialize our database  at application or test startup time.
- Just add your DDL to a special file called  schema.sql into the src/main/resources directory
(obviously you would not normally do this for production apps)
http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#howto-initialize-a-database-using-spring-jdbc

--> 



#### Populate tables
```
src/main/resources/data.sql
```
And here's some sample data that we will load into the tables.

<!--
After restarting the springboot app and logging into the H2 console
you should see a new PUBLISHERS table with data populated.
-->

#### The Publisher Java Bean
```
src\main\java\com\example\model\Publisher.java
```
All the classes in the model package represent places where we want to store data loaded from the database.

for example, When we read rows in the PUBLISHERS table, we'd like the data to go into instances of this class.
 
#### the  Publisher DAO Interface
```
src\main\java\com\example\dao\PublisherMapper.java
```
This Publisher Data-Access-Object or Dao interface defines signatures of all   the methods
that we will use to interact with the PUBLISHERS table.

right now,  we havent written any  actual  implementation yet . this is just the interface .

so lets add an implementation

#### @Mapper
Part of the mybatis starter package for spring boot is an auto-configuration setup.
This contains an automatic scan for classes marked with the Mybatis @Mapper annotation
The @Mapper annotation lets us register this class with Mybatis.

This is quite  similar to the How the Spring uses the @Repository or @Component tags  to identify  beans 
during the  component scanning stage at apploication startup

#### @Select
The Next step is add a @Select annotation that will run a query on the database and help map the results  to a Java model object

Mybatis will use the SQL we provide to retrieve rows from a database.
And it uses the Alias mapping to help map from DB cols to Bean properties
 
TYPE --> prefix findAll() with
```
 @Select("SELECT ID as id,  NAME as name, PHONE as phoneNumber from PUBLISHERS") //SQL
```
since the ID and NAME DB colums have the exact same name as the Java model property name 
we want to map the results to
we can drop the explict alias mapping 
TYPE --> drop unneeded aliases 
```
@Select("SELECT ID,  NAME, PHONE as phoneNumber from PUBLISHERS")
```
When the DB column names in the result match the java model prperty names exactly 
we can drop the explict listing of column names and use a wildcard opertaer
here we've used the wildcard to map  all columns in the result except for the PHONE column 
which requires an explict mapping as the db sourcename anbd the java traget name are different
``` 
@Select("SELECT *, PHONE as phoneNumber from PUBLISHERS")
``` 
 

#### Run Test
When Mybatis detects classes with the @Mapper interface  at application or test startup 
it will generate an implementation class behind the scenes
Lets test the  @Select annotaion we just wrote by running a test

#### @Select with underscore conversion
```
src\main\java\com\example\dao\BookMapper.java
```
here's another bean we'd like to load data into. 
We were lucky with the last model class as most of it's property names were  identical to the table column names 
and we didn't need to write many  explict alias mappings 

but in this model, we're not so lucky, as the names of the  java properties and database columns are similar,
  but unfortunately not exactly the same.

Here, the column names contain underscores and the model property names don't.

So , AUTHOR_FIRST_NAME column is not equivalent to CamelCase authorFirstName


This would normally require us to explicting map the columns to java prperties like so 
```
@Select("SELECT AUTHOR_FIRST_NAME as authorfirstName  FROM BOOKS")
```
```
src\main\resources\applications.properties 
```
- but, if we     enable the mapUnderscoreToCamelCase property in applications.properties, 
mybatis WILL perform the underscore to camelcase mapping automatically for you.

- Now you'll be able to map all the columns with the following wonderflully concise statement       
```
  @Select("SELECT * FROM BOOKS") //SQL
  List<Book> findAll( );
```
#### @Select with @Param parameters
in the same file you can  see an example of how we can   pass in parameter values to the SELECT statement
The @param annotation, another mybatis specific annotation, 
is used to link parameters from the interface method to predicates in the SELECT statement
```
  @Select("SELECT * FROM BOOKS WHERE GENRE = #{genre}")
  List<Book> findByGenre(@Param("genre") String genre);
``` 

#### @Select with Groovy Triple quote 
``` 
 src\main\groovy\com\example\dao\UserDao.groovy
```
- If you enable Groovy compilation in your project,
- then you can use the @Select annotation in a groovy class like this one.
- Now you can use the handy triple-quote groovy syntax to supplu multi-line contents to the @Select annotation  
- This allows you to paste in large SQL queries right into your code.

#### demo wrap up

- Because of time constraints We only covered the @Select annotation here.
- but there equivalent annotations for the SQL INSERT, UPDATE and DELETE commands.
- there's also a powerful  dynamic sql feature whe=ich we'' cover in the next set of slides'
#Demo End - Slides Begin
 

### MyBatis Mapping Choices 
in the Demo we saw how to use the @select annotation for  mapping between Database tables and Java objects.
-  I typically use the @ Annotation option for mapping relatively simple tables and queries 
-   when dealing with more complex mapping where want want to map to nested classes 
or if we want to   Dynamic SQL
then I'd suggest taking a look at the Mybatis's XML based mapping which we 'll look at next.
 

### XML Mapping
- With XML based mapping the SQL statements are no longer co-located with our interface class
but placed in a separate XML file.
- There are three main parts to this mapping
- firstly a Book model class is used to store the results of the findAllBooks() query
- an interface  is  used to define the DAO (Data Access Object) operations , just like before
- Finally we   have a new XML file which defines the mapping from database query  results to Java fields.

### Dynamic SQL
- One reason for choosing the XML based mapping in MyBatis 
is if you need to dynamically change the SQL at runtime.

- An example of a situation where Dynamic SQL could be used
is for a Web Search form with a number of optional fields.

-in this case we don't know which fields are relevant to the search until the user submits the form 

- We can use the MyBatis <if> tags to optionally include SQL predicates
when certain optional fields in the Java search form object are populated

- Depending on what fields were populated, we could have
three different SQL SELECT statements generated.
- when a "first name" was included in the search form 
- or when a last name was included
- or when both names were included


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

### Conclusion 

here's a link to the code used in this presentation 

and a link to some other Resources   if you want to learn more about MyBatis. 

We hope you've enjoyed this brief introduction to MyBatis.

Thank You

# ============ DEPRECATEd slides=====================
   
 ### ORM Mapping issue
The problems with moving data between Applications and a relational database
typically the field names, types and structures don't quite line up
and a developer often need to map from one domain to the other
some examples of simple mapping issues could be
differences between the database column names and the names of the fields in the java objects you're storing data in.
We also need to account for differences in the TYPE of the fields on the java and Database sides .


### Association issues 
More complex mapping issues can be seen
when comparing the structural differences between Tables and Java Object Graphs
In the java example above, the association between a parent Blog object and a child Comment Object
is a one way reference.
But when representing the same relationship in a Database Table using a foreign key
the association is a bidirectional relationship.

### Granularity 
Another  mapping issue occurs when there is a big difference in the granularity of structures between the Java and database sides
For example
a single "Address" class on the Java side
might be represented as multiple ADDRESS, STATE and COUNTRY tables on the Database side.
The main takeaway here is that mapping between these very different structures is hard
and any framework that helps simplify this  mapping, like MyBatis,  is a very good thing.
So, that's the problem...
Now lets take a look at the solution..




### MyBatis Design pattern
Before we delve into the code, lets take a quick look at the design pattern behind MyBatis.
MyBatis is an implementation of the "Data Mapper" design pattern.
as described by Martin Fowler in his book
"Enterprise Application Architecture"
A DataMapper, or more simply a Mapper
is a class that sits between
your domain objects and the database
and helps transfer data between them
Thats the theory, lets look at some examples next.
###
JPA tries to be database agnostic
which can make it harder to embed database specific features that you need to improve performance
MyBatis has no such barriers.
During this presentation we looked at some of the underlying reasons why Object-Relation mapping is hard.
Anything that makes that mapping easier is a good thing.
And MyBatis's trick of reusing the SQL 'alias'
allows you do do a lot of the mapping for free
while still having perfectly valid SQL
We saw that building Dynamic SQL and delivering pre-existing complex SQL reports
is something the MyBatis Excels at
and significantly easier than the JPA equivalents
we showed how the SQL centric approach used by MyBatis
makes it easier to get started
and can actually help with debugging and performance
We hope you've enjoyed this brief introduction to MyBatis
Thank You!
Pool 1


### Annotations vs XML
Given a choice between XML and Annotation based implementations, which should you choose?
- My advice would be to use annotation based mapping where possible
as these are generally easy to write and understand.
- If the queries you want to use are more complicated,
in that they require dynamic SQL or collections or nested-selects
then you'll need to use XML based mappers
which are more verbose but also more powerful.
- The SQL centric approach adopted by MyBatis
has a number of advantages for the developer
which we'll take a look at in the next slides.