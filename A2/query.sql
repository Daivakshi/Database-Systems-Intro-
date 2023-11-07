--select * from Students
--    where stu_name = 'John Malkovich';
--select * from Taken
--    where stu_ssn = '102';
select t.stu_ssn, s.stu_name, t.department, t.course_number from Taken t, Students s
    where t.stu_ssn = s.stu_ssn
    and t.stu_ssn = '102';