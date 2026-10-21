create database student_management;

use student_management;

create table students (
    student_id int primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(100) unique,
    phone varchar(15),
    admission_date date not null
);

insert into students (first_name, last_name, email, phone, admission_date)
values ("vipul", "Maske", "vipul@gmail.com", "9876543210", "2026-10-17");

-- update phone number of a student
update students
set phone = "9123456780"
where student_id = 1;


select * from students;

-- create courses table
create table courses (
    course_id int primary key auto_increment,
    course_name varchar(100) not null unique,
    duration_months int not null
);

-- insert sample courses
insert into courses (course_name, duration_months)
values
("Java", 6),
("SQL", 2),
("Python", 4);

select * from courses;

-- create enrollments
create table enrollments (
    enrollment_id int primary key auto_increment,
    student_id int not null,
    course_id int not null,
    enrollment_date date not null,
    foreign key (student_id) references students(student_id),
    foreign key (course_id) references courses(course_id)
);

-- enroll student in java
insert into enrollments (student_id, course_id, enrollment_date)
values (2, 1, "2026-10-17");

select * from enrollments;

-- create attendance table
create table attendance (
    attendance_id int primary key auto_increment,
    student_id int not null,
    attendance_date date not null,
    status varchar(10) not null,
    foreign key (student_id) references students(student_id)
);

-- insert student attendance
insert into attendance (student_id, attendance_date, status)
values (2, "2026-10-17", "Present");

select * from attendance;

-- create marks table
create table marks (
    marks_id int primary key auto_increment,
    student_id int not null,
    course_id int not null,
    marks_obtained int not null,
    total_marks int not null,
    foreign key (student_id) references students(student_id),
    foreign key (course_id) references courses(course_id)
);

-- insert marks for a student
insert into marks (student_id, course_id, marks_obtained, total_marks)
values (2, 1, 85, 100);

select * from marks;

-- display student name, course name, and marks
select
    students.first_name,
    students.last_name,
    courses.course_name,
    marks.marks_obtained,
    marks.total_marks
from students
inner join marks
    on students.student_id = marks.student_id
inner join courses
    on marks.course_id = courses.course_id;
    
-- calculate student percentage
select
    students.first_name,
    students.last_name,
    courses.course_name,
    marks.marks_obtained,
    marks.total_marks,
    (marks.marks_obtained / marks.total_marks) * 100 as percentage
from students
inner join marks
    on students.student_id = marks.student_id
inner join courses
    on marks.course_id = courses.course_id;
    
-- display student percentage and result
select
    students.first_name,
    students.last_name,
    courses.course_name,
    marks.marks_obtained,
    marks.total_marks,
    (marks.marks_obtained / marks.total_marks) * 100 as percentage,
    case
        when (marks.marks_obtained / marks.total_marks) * 100 >= 35
        then "Pass"
        else "Fail"
    end as result
from students
inner join marks
    on students.student_id = marks.student_id
inner join courses
    on marks.course_id = courses.course_id;
    
-- update Java marks to 90
update marks
set marks_obtained = 90
where student_id = 2 and course_id = 1;
