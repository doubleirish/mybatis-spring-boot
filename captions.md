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



###  What is MyBatis
- The purpose of Mybatis is to reduce the complexity of storing and retrieving data from a relational database .
- MyBatis is a persistence framework that is built  on top of the JDBC API.
- It hides away most of the low level complexity you would normally encounter when working with JDBC directly
- MyBatis is similar to Hibernate/JPA in that they both help simplify the interaction with the database
- But where JPA takes an Object Centric approach and generates  SQL behind the scenes ,
  MyBatis instead works with SQL directly and relies on the features of the SQL language , 
  specifically aliases to simplify mapping between c.
 
 
to see an example how SQL aliases could be used 


### @Select Annotation 
 Mybatis piggybacks off of the  built-in "alias" feature in SQL 
 to define the mapping between COLUMN_NAMES in UPPERCASE and Java Bean properties in CamelCase

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
- Thats the theory lets look at some code examples. 

  
There's essentially three main Areas in the Demo :-
- On one side we have a database and tables containing data
- On the Application side ( a springboot App in this case)  we have Jave Beans that we'd like to load that data into.
- and we're going to use Mybatis to bridge between the database and out application model 

Because of time limitations, I'm going to focus mainly on the MyBatis interactions
 and  skim over Springboot application setup and Database setup. 

- The code for the example shown next is available for download from github 
- The link is here

if you want a more detailed walkthrough on how to setup from scratch
I recommend stepping through the  README.md   file in the github demo link .


## DEMO Begins on intellij in presentation mode


#### MyBatis Spring Boot Starter 
```
pom.xml
```
The simplest way to get started using Mybatis In a Spring boot application 
is to include the mybatis starter dependency you see here. 
This package includes an auto-configuration component which we'll discuss later

we'll also add a dependency on the H2 database so we can setup an embedded  in-memory db.
 

#### Setup H2 in-memory datasource 
```
src/main/resources/application.properties
```
This is where can can setup our connection to a database.
 
In this case we are defining an embedded in-memory database for ease of setup 

<!-- 

####  Enable H2 Web Console
For debug purposes we can also enable a H2 database web console that gets bundled with our springboot webapp H2 database web  .
we'll see how this works later 
--> 

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

When we read rows in the PUBLLISHERS table we'd like data to go into instances of this class.
 
#### the  Publisher DAO Interface
```
src\main\java\com\example\dao\PublisherMapper.java
```
This Publisher interface defines all the method signatures that we will implement.

right now there, there's no implementation. this is just the interface .

so lets add one

#### @Mapper
Part of the mybatis starter package for spring boot is an auto-configuration setup.
This contains an automatic scan for classes marked with th Mybatis @mapper annotation
The @Mapper annotation lets us register this class with Mybatis.

This is similar to the @repository or @Component tags used by spring to register beans 
using component scanning rather than 

#### @Select
The Second step is build a @Select annotation that defines a mapping from DB cols to 
Mybatis will use the SQL we provide to retrieve rows from a database.
And it uses the Alias mapping to help map from DB cols to Bean properties
 
prefix findAll() with
```
 @Select("SELECT ID as id,  NAME as name, PHONE as phoneNumber from PUBLISHERS") //SQL
```
drop unneeded aliases 
```
@Select("SELECT ID,  NAME, PHONE as phoneNumber from PUBLISHERS")
```
use wild card
``` 
@Select("SELECT *, PHONE as phoneNumber from PUBLISHERS")
``` 
 

#### Run Test
let's see if this works with a test

#### @Select with underscore conversion
```
src\main\java\com\example\dao\BookMapper.java
```
here's another  bean we'd like to load data into 
We were lucky with the last model class as most of it's property_names were exactly identical to the table column names 
and we didn't need to write many  explict alias mappings 

in this model, we're not so lucky, as the properties and columns are similar  but unfortunately not exactly the same 
as the column names contain underscores and the model property names don't
by default AUTHOR_FIRST_NAME column is not equivalent to CamelCase authorFirstName


wildcards work when the column name and the java property name are exactly the same

```
@Select("SELECT TITLE as title , AUTHOR_FIRST_NAME as authorfirstName  FROM BOOKS")
```

- but if we enable  enable the mapUnderscoreToCamelCase property in applications.properties, 
mybatis WILL perform the undercore to camle case mapping you see here

- Then you'll be able to map all the columns with the following wonderflully concise statement       
```
  @Select("SELECT * FROM BOOKS") //SQL
  List<Book> findAll( );
```
#### @Select with @Param parameters
in the same file you'see see how we can pass in parameter values 
```
  @Select("SELECT * FROM BOOKS WHERE GENRE = #{genre}")
  List<Book> findByGenre(@Param("genre") String genre);
``` 

#### @Select with Groovy Triple quote 
``` 
 src\main\groovy\com\example\dao\UserDao.groovy
```
- If you enable Groovy compilation in your project,
- then you can use @Select annotation with the handy triple-quote feature
- This allows you to paste in large SQL queries right into your code.
#### demo wrap up

- We only covered the @Select annotation here.
- but there equivalent annotations for INSERT, UPDATES and DELETES
- there's also a powerful  dynamic sql feature whe=ich we'' cover in the next set of slides'
#Demo End - Slides Begin
 



### MyBatis Mapping Choices 
MyBatis provides two main approaches for mapping between Java objects and Database tables
- For mapping relatively simple tables and queries, I typically use the @ Annotation option.
like the @Select annotation shown here.
- For dealing with more complex tables
or if we want to use advanced features like Dynamic SQL
then I'd suggest the XML based mapping
- We'll take a more in-depth look at both of these options next.
 

### XML Mapping
- XML based mapping is a little trickier
- since the SQL statements are no longer co-located with our interface
but placed in a separate XML file.
- There are three main parts to this
- firstly a Book class is used to store the results of the findAllBooks() query
we had an interface 
-  just like before used to define the DAO (Data Access Object) operations
- whats differerent,  is that we have a new XML file which defines the mapping from database query  results to Java fields.

### Dynamic SQL
- One reason for choosing the XML based mapping
is if you need to dynamically change the SQL at runtime.
- An example of a situation where Dynamic SQL could be used
is for a Web Search form with a number of optional fields.
- We can use the MyBatis <if> tags
to optionally include SQL predicates
when certain optional fields in the Java search form object are populated
- In this case, depending on what fields were populated, we could have
three different SQL SELECT statements generated.
- when a "first name" was included
- or a last name
- or both names

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

### Advantages

### Learning Curve
If comparing Mybatis to alternatives like JPA
one advantage over JPA is that
most developers already have some basic SQL skills
so it's very easy to jump into.
The 'alias' based mapping used by MyBatis is already built-in to SQL
Dynamic SQL is strightforward
mapping complex relationships between tables can be built on top of SQL's existing 'JOIN' feature.

### Complex Reports
MyBatis is especially handy to use if you have a lot of predefined SQL reports that you need to get into your Java application
Its' very easy to just paste your SQL in to an XML or annotation based mapper

### Debugging
When using JPA, the generated SQL it builds for you
can be hard to find
127	<UNT>
and even harder to understand.
But With mybatis you always have full visibility to the SQL used.
This can help avoid issues like the dreaded "N+1 selects" issue

###Performance
Sometimes there are specific features of your underlying database
that you need to access in order to maximize performance
For example you may need to use database specifc optimizer hints
or database specifc features for performance reasons

### Review 
- Intro 
- Demo
- 

### Conclusion 

We hope you've enjoyed this brief introduction to MyBatis

# deprecated slides
   
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