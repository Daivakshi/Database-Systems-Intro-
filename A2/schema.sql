-- Tables from entity sets:-
create table Professors(
    prof_ssn varchar(20) not null,
    prof_name varchar(20),
    prof_email varchar(50),
    constraint prof_pk primary key (prof_ssn)
);

create table Students(
    stu_ssn varchar(20) not null,
    stu_name varchar(20),
    stu_email varchar(50),
    constraint stu_pk primary key (stu_ssn)
);

create table GTA(
    stu_ssn varchar(20) not null,
    salary numeric,
    constraint gta_pk primary key (stu_ssn),
    constraint gta_fk foreign key (stu_ssn) references Students
);

create table Team(
    team_id varchar(20) not null,
    constraint team_pk primary key (team_id)
);

create table Class(
    department varchar(20) not null,
    course_number numeric not null,
    credit numeric,
    constraint class_pk primary key (department, course_number)
);

create table Section(
    sec_id varchar(20) not null,
    department varchar(20) not null,
    course_number numeric not null,
    constraint sec_pk primary key (sec_id, department, course_number),
    constraint sec_fk foreign key (department, course_number) references Class(department, course_number)
);

-- Tables from relations :-
create table Can_teach(
    prof_ssn varchar(20) not null,
    department varchar(20) not null,
    course_number numeric not null,
    constraint ct_pk primary key (prof_ssn, department, course_number),
    constraint ct_fk_class foreign key (department, course_number) references Class(department, course_number),
    constraint ct_fk_prof foreign key (prof_ssn) references Professors
);

create table On_team_prof(
    prof_ssn varchar(20) not null,
    team_id varchar(20) not null,
    constraint otp_pk primary key (prof_ssn, team_id),
    constraint otp_fk_team foreign key (team_id) references Team,
    constraint otp_fk_prof foreign key (prof_ssn) references Professors
);

create table On_team_gta(
    stu_ssn varchar(20) not null,
    team_id varchar(20) not null,
    constraint otg_pk primary key (stu_ssn, team_id),
    constraint otg_fk_team foreign key (team_id) references Team,
    constraint otg_fk_prof foreign key (stu_ssn) references GTA
);

create table Admin_sec(
    team_id varchar(20) not null,
    sec_id varchar(20) not null,
    department varchar(20) not null,
    course_number numeric not null,
    rating numeric,
    constraint admin_pk primary key (sec_id, department, course_number),
    constraint admin_fk_team foreign key (team_id) references Team,
    constraint admin_fk_sec foreign key (sec_id, department, course_number) references Section(sec_id, department, course_number)
);

create table Waitlist(
    stu_ssn varchar(20) not null,
    sec_id varchar(20) not null,
    department varchar(20) not null,
    course_number numeric not null,
    waitlist_rank integer,
    constraint waitlist_pk primary key (stu_ssn, sec_id, department, course_number),
    constraint waitlist_fk_stu foreign key (stu_ssn) references Students,
    constraint waitlist_fk_sec foreign key (sec_id, department, course_number) references Section(sec_id, department, course_number)
);

create table Taken(
    stu_ssn varchar(20) not null,
    sec_id varchar(20) not null,
    department varchar(20) not null,
    course_number numeric not null,
    grade varchar(20),
    constraint taken_pk primary key (stu_ssn, sec_id, department, course_number),
    constraint taken_fk_stu foreign key (stu_ssn) references Students,
    constraint taken_fk_sec foreign key (sec_id, department, course_number) references Section(sec_id, department, course_number)
);