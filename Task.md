# Basic
![sql-training-1](https://github.com/user-attachments/assets/9fa0f08d-714b-462d-ab92-b546fb3102f3)

## Retrieve everything from a table

### DDL
```sql
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```

###  Answer
```sql
SELECT * FROM cd.facilities;
```

## Retrieve specific columns from a table

### DDL
```sql
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```
###  Answer
```sql
SELECT name, membercost FROM cd.facilities;
```
## Control which rows are retrieved

### DDL
```sql
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```
###  Answer
```sql
SELECT * FROM cd.facilities
WHERE membercost > 0
```
# Control which rows are retrieved - part 2

### DDL
```sql
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```
###  Answer
```sql
SELECT facid, name, membercost, monthlymaintenance FROM cd.facilities
WHERE membercost  >= 35
```
# Basic string searches

### DDL
```sql
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```
###  Answer
```sql
SELECT * FROM cd.facilities
WHERE name LIKE '%Tennis%'
```
# Matching against multiple possible values

### DDL
```sql
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```
###  Answer
```sql
SELECT * from cd.facilities
WHERE cd.facilities.facid IN (1,5)
```

# Classify results into buckets

### DDL
```sql
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```
###  Answer
```sql
SELECT name, 
    CASE 
        WHEN monthlymaintenance > 100 THEN 'expensive'
        ELSE 'cheap'
    END AS cost
FROM 
    cd.facilities;
```
# Working with dates

### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```
###  Answer
```sql
SELECT  memid, surname, firstname, joindate FROM cd.members
WHERE joindate > '2012-09-01'
```
# Removing duplicates, and ordering results

### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```
###  Answer
```sql
SELECT DISTINCT surname FROM cd.members
ORDER BY surname 
LIMIT 10
```

# Combining results from multiple queries

### DDL
```sql
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);

CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);

INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```
###  Answer
```sql
SELECT surname FROM cd.members
UNION
SELECT name FROM cd.facilities
```
# Simple aggregation

### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```
###  Answer
```sql
SELECT max(joindate) as latest FROM cd.members
```
# More aggregation

### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```
# Answer
```sql
SELECT firstname, surname, joindate from cd.members
WHERE joindate = (SELECT MAX(joindate) FROM cd.members);  
```

# Joins
![sql-training-2](https://github.com/user-attachments/assets/aee863da-a106-4dc5-a712-96c8a7b4e4f7)

# Retrieve the start times of members' bookings

### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);

CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);

```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);

```
# Answer
```sql
SELECT b.starttime
FROM cd.members m
JOIN cd.bookings b ON m.memid = b.memid
WHERE m.firstname = 'David' AND m.surname = 'Farrell';
```

# Work out the start times of bookings for tennis courts

### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);

CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```

### DML
```sql
INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);

INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```
# Answer
```sql
SELECT starttime AS start, name
FROM cd.facilities as f
JOIN cd.bookings b ON b.facid = f.facid
WHERE f.name IN ('Tennis Court 2','Tennis Court 1') AND
	  b.starttime >= '2012-09-21' and
	  b.starttime < '2012-09-22'
ORDER BY b.starttime;
```
# Produce a list of all members who have recommended another member

### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```
# Answer
```sql
SELECT DISTINCT m1.firstname, m1.surname FROM cd.members as m1
INNER JOIN cd.members as m2 ON m2.recommendedBy = m1.memid
ORDER BY surname,firstname;
```
# Produce a list of all members, along with their recommender

### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```
# Answer
```sql
SELECT m1.firstname as memfname, m1.surname as memsname,
	   m2.firstname  as recfname, m2.surname  as recsname
FROM cd.members m1
LEFT JOIN cd.members m2 ON m2.memid = m1.recommendedby
ORDER BY memsname, memfname;
```
# Produce a list of all members who have used a tennis court

### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);

CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);

INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```
# Answer
```sql
SELECT DISTINCT m.firstname || ' ' || m.surname as member, f.name as facility
FROM cd.members as m
INNER JOIN cd.bookings b ON m.memid = b.memid
INNER JOIN cd.facilities f ON b.facid = f.facid
WHERE f.name in ('Tennis Court 2','Tennis Court 1')
ORDER BY member,facility
```
# Produce a list of costly bookings
### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);

CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);

INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```
# Answer
```sql
SELECT m.firstname || ' ' || m.surname AS member, f.name AS facility,
	CASE 
        WHEN m.memid = 0 THEN
            b.slots * f.guestcost
        ELSE
            b.slots * f.membercost
    END AS cost
FROM cd.members AS m
INNER JOIN cd.bookings AS b ON m.memid = b.memid
INNER JOIN cd.facilities AS f ON b.facid = f.facid
WHERE b.starttime >= '2012-09-14' AND b.starttime < '2012-09-15'
    AND ((m.memid = 0 AND b.slots * f.guestcost > 30) OR (m.memid != 0 AND b.slots * f.membercost > 30))
ORDER BY cost DESC;
```

# Produce a list of all members, along with their recommender, using no joins.
### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```
# Answer
```sql
SELECT DISTINCT m.firstname || ' ' || m.surname AS member,
( SELECT  r.firstname || ' ' || r.surname AS recommender  FROM cd.members AS r
WHERE  r.memid = m.recommendedby ) AS recommender
FROM cd.members AS m
ORDER BY member;
```

# Produce a list of costly bookings, using a subquery
### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);

CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);

INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```
# Answer
```sql
SELECT member,facility,cost 
FROM (SELECT m.firstname || ' ' || m.surname AS member, f.name AS facility,
        CASE
            WHEN m.memid = 0 THEN b.slots * f.guestcost
            ELSE b.slots * f.membercost
        END AS cost
FROM cd.members m
INNER JOIN cd.bookings b ON m.memid = b.memid
INNER JOIN cd.facilities f ON b.facid = f.facid
WHERE b.starttime >= '2012-09-14' AND b.starttime < '2012-09-15') AS bookings
WHERE cost > 30
ORDER BY cost DESC;
```
# Aggregates
![sql-training-4png](https://github.com/user-attachments/assets/f8efea46-3e35-4b6b-82f6-1f572531f53d)
# Count the number of facilities
### DDL
```sql
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```
# Answer
```sql
SELECT COUNT(*) FROM cd.facilities
```

# Count the number of expensive facilities
### DDL
```sql
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);
```
# Answer
```sql
SELECT count(*) FROM cd.facilities
WHERE guestcost > 10
```
# Count the number of recommendations each member makes.
### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```
# Answer
```sql
SELECT recommendedby, COUNT(*) as count FROM cd.members
WHERE recommendedby IS NOT NULL
GROUP BY recommendedby
ORDER BY recommendedby;
```
# List the total slots booked per facility
### DDL
```sql
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
SELECT f.facid, SUM(b.slots) as "Total Slots" FROM cd.facilities as  f
JOIN cd.bookings as b ON f.facid = b.facid
GROUP BY f.facid
ORDER BY f.facid
```
# List the total slots booked per facility in a given month
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
```
### DML
```sql
INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
SELECT facid, SUM(slots) as "Total Slots" 
FROM cd.bookings
WHERE starttime >= '2012-09-01' AND starttime < '2012-10-01'
GROUP BY facid
ORDER BY "Total Slots";
```
# List the total slots booked per facility per month
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
```
### DML
```sql
INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
SELECT facid,  EXTRACT(month from starttime) as month, sum(slots) as "Total Slots" 
FROM  cd.bookings
WHERE EXTRACT(year from starttime) = 2012
GROUP BY facid,month
ORDER BY facid, month;
```
# Find the count of members who have made at least one booking
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
```
### DML
```sql
INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
SELECT COUNT(DISTINCT memid) FROM cd.bookings
```
# List facilities with more than 1000 slots booked
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
```
### DML
```sql
INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
SELECT facid, SUM(slots) as "Total Slots"
FROM cd.bookings
GROUP BY facid
HAVING SUM(slots) > 1000
ORDEr BY facid
```

# Find the total revenue of each facility
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
SELECT f.name, SUM(slots * CASE WHEN memid = 0 THEN f.guestcost ELSE f.membercost END) AS revenue
FROM cd.bookings b
INNER JOIN cd.facilities f ON b.facid = f.facid
GROUP BY f.name
ORDER BY revenue;
```

# Find facilities with a total revenue less than 1000
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
select name, revenuefrom(select f.name as name,sum(b.slots*case
when b.memid = 0 then f.guestcost
else f.membercost end) as revenue
from cd.facilities as f
inner join cd.bookings as b
on f.facid = b.facid
group by f.name) as revenues
where revenue < 1000
order by revenue;
```
# Output the facility id that has the highest number of slots booked
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
```
### DML
```sql
INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
select facid, sum(slots) as "Total Slots" from cd.bookings
group by facid
order by sum(slots) desc
LIMIT 1; 
```
# List the total slots booked per facility per month, part 2
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
```
### DML
```sql
INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
select facid, extract(month from starttime) as month, sum(slots) as slots
from cd.bookings
where starttime >= '2012-01-01'and starttime < '2013-01-01'
group by rollup(facid, month)
order by facid, month;      
```
# List the total hours booked per named facility
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
select facs.facid, facs.name,trim(to_char(sum(bks.slots)/2.0, '9999999999999999D99')) as "Total Hours"
from cd.bookings bks
inner join cd.facilities facs on facs.facid = bks.facid
group by facs.facid, facs.name
order by facs.facid;     
```
# List each member's first booking after September 1st 2012
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
```
### DML
```sql
INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
SELECT m.surname,m.firstname,m.memid,MIN(b.starttime) as starttime
FROM cd.bookings b
INNER JOIN cd.members m ON m.memid = b.memid
WHERE starttime >= '2012-09-01'
GROUP BY m.surname,m.firstname,m.memid
ORDER BY m.memid;
```
# Produce a list of member names, with each row containing the total member count
### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```
# Answer
```sql
SELECT COUNT(*) OVER() ,firstname,surname FROM cd.members
ORDER BY joindate
```
# Produce a numbered list of members
### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```
# Answer
```sql
SELECT ROW_NUMBER() OVER (ORDER BY joindate) AS row_num,firstname, surname
FROM cd.members
ORDER BY joindate;
```
# Output the facility id that has the highest number of slots booked, again
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
```
### DML
```sql
INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
SELECT facid,total
FROM (SELECT facid,SUM(slots) AS total,RANK() OVER (ORDER BY SUM(slots) DESC) AS rank
FROM cd.bookings
GROUP BY  facid) AS ranked
WHERE rank = 1;
```
# Rank members by (rounded) hours used
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```
### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
SELECT mems.firstname, mems.surname,((SUM(bks.slots) + 10) / 20) * 10 AS hours,
RANK() OVER (ORDER BY ((SUM(bks.slots) + 10) / 20) * 10 DESC) AS rank
FROM cd.bookings AS bks
INNER JOIN cd.members AS mems ON bks.memid = mems.memid
GROUP BY mems.memid, mems.firstname, mems.surname
ORDER BY rank, mems.surname,  mems.firstname;
```
# Find the top three revenue generating facilities
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
SELECT name, rank
FROM (SELECT facs.name AS name, RANK() OVER (ORDER BY SUM(
            CASE
                WHEN bks.memid = 0 THEN bks.slots * facs.guestcost
                ELSE bks.slots * facs.membercost
            END) DESC) AS rank
FROM  cd.bookings AS bks
INNER JOIN cd.facilities AS facs ON bks.facid = facs.facid 
GROUP BY facs.name) AS subq
WHERE rank <= 3
ORDER BY rank;
```
# Classify facilities by value
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
SELECT name, 
    CASE 
        WHEN class = 1 THEN 'high'
        WHEN class = 2 THEN 'average'
        ELSE 'low'
    END AS revenue
FROM (SELECT facs.name, NTILE(3) OVER (ORDER BY SUM(
     CASE
         WHEN memid = 0 THEN slots * facs.guestcost
         ELSE slots * facs.membercost
     END) DESC) AS class
FROM  cd.bookings bks
INNER JOIN cd.facilities facs ON bks.facid = facs.facid
GROUP BY facs.name) AS subq
ORDER BY class, name;
```
# Calculate the payback time for each facility
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
SELECT f.name AS name,f.initialoutlay / (
        (SUM(CASE
             WHEN b.memid = 0 THEN b.slots * f.guestcost
             ELSE b.slots * f.membercost
            END) / 3) - f.monthlymaintenance) AS months
FROM  cd.bookings b
INNER JOIN  cd.facilities f ON b.facid = f.facid
GROUP BY f.facid
ORDER BY name;
```
# Calculate the payback time for each facility
### DDL
```sql
CREATE TABLE bookings (
    bookid integer NOT NULL,
    facid integer NOT NULL,
    memid integer NOT NULL,
    starttime timestamp without time zone NOT NULL,
    slots integer NOT NULL
);
CREATE TABLE facilities (
    facid integer NOT NULL,
    name character varying(100) NOT NULL,
    membercost numeric NOT NULL,
    guestcost numeric NOT NULL,
    initialoutlay numeric NOT NULL,
    monthlymaintenance numeric NOT NULL
);
```
### DML
```sql
INSERT INTO facilities (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) VALUES
(0, 'Tennis Court 1', 5, 25, 10000, 200),
(1, 'Tennis Court 2', 5, 25, 8000, 200),
(2, 'Badminton Court', 0, 15.5, 4000, 50),
(3, 'Table Tennis', 0, 5, 320, 10),
(4, 'Massage Room 1', 35, 80, 4000, 3000),
(5, 'Massage Room 2', 35, 80, 4000, 3000),
(6, 'Squash Court', 3.5, 17.5, 5000, 80),
(7, 'Snooker Table', 0, 5, 450, 15),
(8, 'Pool Table', 0, 5, 400, 15);

INSERT INTO bookings (bookid, facid, memid, starttime, slots) VALUES
(0, 3, 1, '2012-07-03 11:00:00', 2),
(1, 4, 1, '2012-07-03 08:00:00', 2),
(2, 6, 0, '2012-07-03 18:00:00', 2),
(3, 7, 1, '2012-07-03 19:00:00', 2),
(4, 8, 1, '2012-07-03 10:00:00', 1),
(5, 8, 1, '2012-07-03 15:00:00', 1),
(6, 0, 2, '2012-07-04 09:00:00', 3),
(7, 0, 2, '2012-07-04 15:00:00', 3),
(8, 4, 3, '2012-07-04 13:30:00', 2),
(9, 4, 0, '2012-07-04 15:00:00', 2),
(10, 4, 0, '2012-07-04 17:30:00', 2),
(11, 6, 0, '2012-07-04 12:30:00', 2),
(12, 6, 0, '2012-07-04 14:00:00', 2),
(13, 6, 1, '2012-07-04 15:30:00', 2),
(14, 7, 2, '2012-07-04 14:00:00', 2),
(15, 8, 2, '2012-07-04 12:00:00', 1),
(16, 8, 3, '2012-07-04 18:00:00', 1),
(17, 1, 0, '2012-07-05 17:30:00', 3),
(18, 2, 1, '2012-07-05 09:30:00', 3),
(19, 3, 3, '2012-07-05 09:00:00', 2),
(20, 3, 1, '2012-07-05 19:00:00', 2),
(21, 4, 3, '2012-07-05 18:30:00', 2),
(22, 6, 0, '2012-07-05 13:00:00', 2),
(23, 6, 1, '2012-07-05 14:30:00', 2),
(24, 7, 2, '2012-07-05 18:30:00', 2),
(25, 8, 3, '2012-07-05 12:30:00', 1),
(26, 0, 0, '2012-07-06 08:00:00', 3),
(27, 0, 0, '2012-07-06 14:00:00', 3),
(28, 0, 2, '2012-07-06 15:30:00', 3),
(29, 2, 1, '2012-07-06 17:00:00', 3),
(30, 3, 1, '2012-07-06 11:00:00', 2),
(31, 4, 3, '2012-07-06 12:00:00', 2),
(32, 6, 1, '2012-07-06 14:00:00', 2),
(33, 7, 2, '2012-07-06 08:30:00', 2),
(34, 7, 2, '2012-07-06 13:30:00', 2),
(35, 8, 3, '2012-07-06 15:30:00', 1),
(36, 0, 2, '2012-07-07 08:30:00', 3),
(37, 0, 0, '2012-07-07 12:30:00', 3);
```
# Answer
```sql
WITH revdaily AS (
  SELECT
    days.day, rev.revenue 
  FROM (
    SELECT
      generate_series('2012-07-15'::date, '2012-08-31'::date, '1 day')::date AS day
    ) AS days
  LEFT JOIN (
    SELECT
      date(b.starttime) AS day,
      sum(b.slots * 
	      CASE
	        WHEN b.memid=0 THEN a.guestcost
	        ELSE a.membercost
	      END) AS revenue
    FROM
      cd.facilities AS a
    INNER JOIN
      cd.bookings AS b
    ON
      a.facid = b.facid
    GROUP BY
      date(b.starttime)
  ) AS rev
  ON days.day = rev.day
)

SELECT * FROM (
  SELECT
    day,
    avg(revenue) over(
	  ORDER BY day ROWS 
	  BETWEEN 14 PRECEDING
	  AND CURRENT ROW) AS revenue
  FROM
    revdaily
  ) AS revroll
WHERE
  revroll.day >= '2012-08-01'
;
```
# Recursive
![sql-training-3](https://github.com/user-attachments/assets/57dc4442-2251-43bf-9f42-973086c40bd9)
# Find the upward recommendation chain for member ID 27

### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```

### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```

# Answer
```sql
WITH RECURSIVE recommenders AS (
SELECT memid AS recommender,firstname,surname,recommendedby
FROM cd.members
WHERE  memid = 27 UNION ALL
SELECT  m.memid,m.firstname, m.surname, m.recommendedby FROM cd.members AS m
JOIN recommenders AS r ON m.memid = r.recommendedby)

SELECT recommender,firstname,surname
FROM recommenders
WHERE recommender != 27
ORDER BY recommender DESC;
```

# Find the downward recommendation chain for member ID 1

### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```

### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```

# Answer
```sql
WITH RECURSIVE recommenders AS (
SELECT memid,  firstname, surname, recommendedby
FROM  cd.members
WHERE memid = 1
UNION ALL
SELECT m.memid,m.firstname, m.surname, m.recommendedby
FROM cd.members AS m
JOIN recommenders AS r ON m.recommendedby = r.memid)
		
SELECT memid, firstname, surname
FROM recommenders
WHERE memid != 1
ORDER BY memid;
```
# Produce a CTE that can return the upward recommendation chain for any member

### DDL
```sql
CREATE TABLE members (
    memid integer NOT NULL,
    surname character varying(200) NOT NULL,
    firstname character varying(200) NOT NULL,
    address character varying(300) NOT NULL,
    zipcode integer NOT NULL,
    telephone character varying(20) NOT NULL,
    recommendedby integer,
    joindate timestamp without time zone NOT NULL
);
```

### DML
```sql
INSERT INTO members (memid, surname, firstname, address, zipcode, telephone, recommendedby, joindate) VALUES
(0, 'GUEST', 'GUEST', 'GUEST', 0, '(000) 000-0000', NULL, '2012-07-01 00:00:00'),
(1, 'Smith', 'Darren', '8 Bloomsbury Close, Boston', 4321, '555-555-5555', NULL, '2012-07-02 12:02:05'),
(2, 'Smith', 'Tracy', '8 Bloomsbury Close, New York', 4321, '555-555-5555', NULL, '2012-07-02 12:08:23'),
(3, 'Rownam', 'Tim', '23 Highway Way, Boston', 23423, '(844) 693-0723', NULL, '2012-07-03 09:32:15'),
(4, 'Joplette', 'Janice', '20 Crossing Road, New York', 234, '(833) 942-4710', 1, '2012-07-03 10:25:05'),
(5, 'Butters', 'Gerald', '1065 Huntingdon Avenue, Boston', 56754, '(844) 078-4130', 1, '2012-07-09 10:44:09'),
(6, 'Tracy', 'Burton', '3 Tunisia Drive, Boston', 45678, '(822) 354-9973', NULL, '2012-07-15 08:52:55'),
(7, 'Dare', 'Nancy', '6 Hunting Lodge Way, Boston', 10383, '(833) 776-4001', 4, '2012-07-25 08:59:12'),
(8, 'Boothe', 'Tim', '3 Bloomsbury Close, Reading, 00234', 234, '(811) 433-2547', 3, '2012-07-25 16:02:35'),
(9, 'Stibbons', 'Ponder', '5 Dragons Way, Winchester', 87630, '(833) 160-3900', 6, '2012-07-25 17:09:05'),
(10, 'Owen', 'Charles', '52 Cheshire Grove, Winchester, 28563', 28563, '(855) 542-5251', 1, '2012-08-03 19:42:37'),
(11, 'Jones', 'David', '976 Gnats Close, Reading', 33862, '(844) 536-8036', 4, '2012-08-06 16:32:55'),
(12, 'Baker', 'Anne', '55 Powdery Street, Boston', 80743, '844-076-5141', 9, '2012-08-10 14:23:22'),
(13, 'Farrell', 'Jemima', '103 Firth Avenue, North Reading', 57392, '(855) 016-0163', NULL, '2012-08-10 14:28:01'),
(14, 'Smith', 'Jack', '252 Binkington Way, Boston', 69302, '(822) 163-3254', 1, '2012-08-10 16:22:05'),
(15, 'Bader', 'Florence', '264 Ursula Drive, Westford', 84923, '(833) 499-3527', 9, '2012-08-10 17:52:03'),
(16, 'Baker', 'Timothy', '329 James Street, Reading', 58393, '833-941-0824', 13, '2012-08-15 10:34:25'),
(17, 'Pinker', 'David', '5 Impreza Road, Boston', 65332, '811 409-6734', 13, '2012-08-16 11:32:47'),
(20, 'Genting', 'Matthew', '4 Nunnington Place, Wingfield, Boston', 52365, '(811) 972-1377', 5, '2012-08-19 14:55:55'),
(21, 'Mackenzie', 'Anna', '64 Perkington Lane, Reading', 64577, '(822) 661-2898', 1, '2012-08-26 09:32:05'),
(22, 'Coplin', 'Joan', '85 Bard Street, Bloomington, Boston', 43533, '(822) 499-2232', 16, '2012-08-29 08:32:41'),
(24, 'Sarwin', 'Ramnaresh', '12 Bullington Lane, Boston', 65464, '(822) 413-1470', 15, '2012-09-01 08:44:42'),
(26, 'Jones', 'Douglas', '976 Gnats Close, Reading', 11986, '844 536-8036', 11, '2012-09-02 18:43:05'),
(27, 'Rumney', 'Henrietta', '3 Burkington Plaza, Boston', 78533, '(822) 989-8876', 20, '2012-09-05 08:42:35'),
(28, 'Farrell', 'David', '437 Granite Farm Road, Westford', 43532, '(855) 755-9876', NULL, '2012-09-15 08:22:05'),
(29, 'Worthington-Smyth', 'Henry', '55 Jagbi Way, North Reading', 97676, '(855) 894-3758', 2, '2012-09-17 12:27:15'),
(30, 'Purview', 'Millicent', '641 Drudgery Close, Burnington, Boston', 34232, '(855) 941-9786', 2, '2012-09-18 19:04:01'),
(33, 'Tupperware', 'Hyacinth', '33 Cheerful Plaza, Drake Road, Westford', 68666, '(822) 665-5327', NULL, '2012-09-18 19:32:05'),
(35, 'Hunt', 'John', '5 Bullington Lane, Boston', 54333, '(899) 720-6978', 30, '2012-09-19 11:32:45'),
(36, 'Crumpet', 'Erica', 'Crimson Road, North Reading', 75655, '(811) 732-4816', 2, '2012-09-22 08:36:38'),
(37, 'Smith', 'Darren', '3 Funktown, Denzington, Boston', 66796, '(822) 577-3541', NULL, '2012-09-26 18:08:45');
```

# Answer
```sql
with recursive recommenders(recommender, member) as (
	select recommendedby, memid
		from cd.members
	union all
	select mems.recommendedby, recs.member
		from recommenders recs
		inner join cd.members mems
			on mems.memid = recs.recommender
)
select recs.member member, recs.recommender, mems.firstname, mems.surname
	from recommenders recs
	inner join cd.members mems		
		on recs.recommender = mems.memid
	where recs.member = 22 or recs.member = 12
order by recs.member asc, recs.recommender desc 
```

