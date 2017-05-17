

INSERT INTO PUBLISHERS (  NAME, PHONE   )
values( 'Manning' ,'(425) 555-1212');

INSERT INTO PUBLISHERS (  NAME, PHONE   )
values( 'Apress' ,'(206) 555-1234');


INSERT INTO BOOKS (ISBN,          TITLE,       AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME,            GENRE,  PRICE, DESCRIPTION )
values('1430241071', 'Pro Spring 3'  ,        'Clarence',         'Ho', 'Java',  19.99,
       'Pro Spring 3 updates the bestselling Pro Spring with the latest that the Spring Framework has to offer');

INSERT INTO BOOKS (ISBN,          TITLE,       AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME,            GENRE,  PRICE, DESCRIPTION )
values('161729120X', 'Spring In Action'  , 'Craig','Walls', 'Java',  29.99,
       'Spring in Action, Fourth Edition is a hands-on guide to the Spring Framework, updated for version 4. ');

INSERT INTO BOOKS (ISBN,          TITLE,       AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME,            GENRE,  PRICE, DESCRIPTION )
values('193239415X', 'Hibernate In Action'  , 'Christian','Bauer', 'Java',  9.99,
       'Hibernate in Action carefully explains the concepts you need, then gets you going.');

INSERT INTO BOOKS (ISBN,          TITLE,       AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME,            GENRE,  PRICE, DESCRIPTION )
values('1931520720', 'Stories of Your Life and Others'  , 'Ted ',' Chiang', 'Science Fiction',  12.25,
       ' includes his first eight published stories plus the author''s story notes ');


INSERT INTO USERS (USER_NAME,   FIRST_NAME, LAST_NAME,  ACTIVE_SINCE )
            values('credmond', 'Conor'  , 'Redmond', '2014-12-31' );

INSERT INTO USERS (USER_NAME,   FIRST_NAME, LAST_NAME,  ACTIVE_SINCE )
            values('jhackett', 'Jack'  ,    'Hackett',   '2014-02-28' );


INSERT INTO USERS (USER_NAME,   FIRST_NAME, LAST_NAME,  ACTIVE_SINCE )
             values('dmcguire', 'Dougal'  ,    'McGuire',   '2014-07-04' );

INSERT INTO USERS (USER_NAME,   FIRST_NAME, LAST_NAME,  ACTIVE_SINCE )
            values('tcrilly', 'Ted'  , 'Crilly', '2011-12-31');

