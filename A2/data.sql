insert into Professors (prof_ssn, prof_name, prof_email) VALUES
    (1, 'Ray Wilson', 'ray@yorku.ca'),
    (2, 'Jon Doe', 'jon@yorku.ca'),
    (3, 'Kim Hana', 'kim@yorku.ca');

insert into Students (stu_ssn, stu_name, stu_email) VALUES
    (101, 'Reyna Moreno', 'reyna@yorku.ca'),
    (102, 'John Malkovich', 'johnm@yorku.ca'),
    (103, 'Freya Dolores', 'freya@yorku.ca'),
    (104, 'Sarah Reyes', 'reyes@yorku.ca'),
    (105, 'Jane Cruz', 'jcruz@yorku.ca');

insert into Class (department, course_number, credit) VALUES
    ('EECS', 1012, 3.0),
    ('EECS', 1019, 3.0),
    ('EECS', 2021, 4.0),
    ('MATH', 2030, 3.0),
    ('MODR', 1770, 6.0);

insert into GTA (stu_ssn, salary) VALUES
    (101, 20000),
    (103, 17000),
    (105, 15000);

insert into Team (team_id) VALUES
    ('a1'),
    ('b2'),
    ('c3');

insert into Section(sec_id, department, course_number) VALUES
    ('A', 'EECS', 1012),
    ('B', 'EECS', 1012),
    ('A', 'EECS', 1019),
    ('A', 'EECS', 2021),
    ('A', 'MATH', 2030),
    ('A', 'MODR', 1770);

insert into Can_teach(prof_ssn, department, course_number) VALUES
    (1, 'MATH', 2030),
    (1, 'EECS', 1019),
    (1, 'EECS', 1012),
    (2, 'EECS', 1012),
    (2, 'EECS', 2021),
    (3, 'MODR', 1770);

insert into On_team_prof(prof_ssn, team_id) VALUES
    (1, 'a1'),
    (2, 'b2'),
    (3, 'c3');

insert into On_team_gta(stu_ssn, team_id) VALUES
    (101, 'a1'),
    (103, 'b2'),
    (105, 'c3');

insert into Admin_sec(team_id, sec_id, department, course_number, rating) VALUES
    ('a1', 'A', 'EECS', 1012, 70),
    ('a1', 'A', 'EECS', 1019, 57),
    ('a1', 'A', 'MATH', 2030, 89),
    ('b2', 'B', 'EECS', 1012, 62),
    ('b2', 'A', 'EECS', 2021, 43),
    ('c3', 'A', 'MODR', 1770, 96);

insert into Waitlist(stu_ssn, sec_id, department, course_number, waitlist_rank) VALUES
    (101, 'A', 'EECS', 2021, 1),
    (102, 'B', 'EECS', 1012, 1),
    (103, 'A', 'MATH', 2030, 1),
    (104, 'A', 'MATH', 2030, 2),
    (105, 'A', 'EECS', 2021, 2);

insert into Taken(stu_ssn, sec_id, department, course_number, grade) VALUES
    (101, 'A', 'MODR', 1770, 'B'),
    (102, 'B', 'EECS', 1012, 'A+'),
    (102, 'A', 'MATH', 2030, 'A'),
    (103, 'A', 'MODR', 1770, 'B+'),
    (103, 'A', 'EECS', 1019, 'D'),
    (104, 'A', 'EECS', 1012, 'B+'),
    (104, 'A', 'EECS', 1019, 'C'),
    (104, 'A', 'EECS', 2021, 'C+'),
    (105, 'A', 'EECS', 1012, 'A+'),
    (105, 'A', 'EECS', 1019, 'B');