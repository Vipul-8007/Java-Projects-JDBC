create database library_management;

use library_management;

create table books (
    book_id int primary key auto_increment,
    title varchar(100) not null,
    author varchar(100) not null,
    category varchar(50),
    total_copies int not null,
    available_copies int not null
);

create table members (
    member_id int primary key auto_increment,
    member_name varchar(100) not null,
    email varchar(100) unique,
    phone varchar(15),
    join_date date not null
);

create table book_issues (
    issue_id int primary key auto_increment,
    book_id int not null,
    member_id int not null,
    issue_date date not null,
    due_date date not null,
    return_date date,
    fine_amount decimal(8,2) default 0.00,
    foreign key (book_id) references books(book_id),
    foreign key (member_id) references members(member_id)
);

insert into books
    (title, author, category, total_copies, available_copies)
values
    ("Java Programming", "James Gosling", "Programming", 5, 5),
    ("Database Management", "Raghu Ramakrishnan", "Database", 3, 3),
    ("Python Basics", "Guido van Rossum", "Programming", 4, 4);
    
select * from books;

insert into members
    (member_name, email, phone, join_date)
values
    ("Vipul Maske", "vipul@gmail.com", "9876543210", "2026-10-19"),
    ("Abhi Patil", "abhi@gmail.com", "9876543211", "2026-10-19"),
    ("Rohan Jadhav", "rohan@gmail.com", "9876543212", "2026-10-19");

select * from members;

-- Display book issue records
SELECT * FROM book_issues;

select
    bi.issue_id,
    b.title,
    m.member_name,
    bi.issue_date,
    bi.due_date,
    bi.return_date,
    bi.fine_amount
from book_issues bi
join books b on bi.book_id = b.book_id
join members m on bi.member_id = m.member_id;