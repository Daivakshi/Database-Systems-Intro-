# Project Description (continuation of A1)

This is the second step of assignment 1. In this one, you will do the following:

create the relational schema (the tables) based upon an ER design shown below.
populate the tables with some example data, and then
execute an SQL query that demonstrates the viability of your schema.

![diagram](https://github.com/Daivakshi/Database-Systems-Intro-/assets/84505662/746d9d33-5b40-4da1-a586-b4248ddbe4b0)

Note: in the ER diagram above Section is a weak entity and Section_ID is its key. There is a sharp arrow from Admin to Team. Other than that, everything else should be straightforward.

Relational Schema
Translate the E/R diagram into an “equivalent” relational schema in SQL (the data definition language, DDL). Do not create any tables that are not needed. Declare primary keys and foreign keys per table appropriately to capture the logic of the E/R diagram correctly. Choose appropriate domain types — integer, date, varchar(…), etc. — as is appropriate. Key attributes in the E/R diagram should be declared as not nullable. For attribute and table names, carry over those from the E/R diagram where you can, and make sensible choices where you cannot.

The Data
Fill your tables with data. You are free to choose any data you like. However, you must make sure that each table contains at least one tuple and that the query you run on your database (see below) returns at least one tuple.

The Query
List all classes (by course number and department) taken by John Malkovich. Since you make up your own data your answer can be completely arbitrary but it cannot be empty!

We will be using PostreSQL for this course. You can use the following resources:

PostgreSQL on PRISM
psql: PostgreSQL's shell client (a guide to using psql with PRISM's DB)
Tutorial Video for using PostgreSQL on PRISM
From PostgreSQL
The SQL Language
PostgreSQL Documentation V11 Online Manuals for PostgreSQL Users
Deliverables:

Submit ONE .zip file containing the following three files:

1. The schema.sql file that includes all SQL statements to create tables and constraints.

2. The data.sql file that includes all SQL statements to insert the sample data to the created tables.

3. The query.sql that includes the query specified in the assignment which must return valid data.
