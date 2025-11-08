-- 10월 15일 수업

-- 데이터베이스 보기
show databases;

-- 데이터베이스 선택 
use test;

-- 테이블 보기
show tables;


-- 테이블 생성
create table address (
	num int primary key,
	name varchar(30) not null,
	phone varchar(30),
	age int
);


-- 테이블 삭제
drop table address;

-- 데이터 저장 
insert into address (num, name, phone, age) 
	values (1, '홍길동', '010-1111-1111', 20);

insert into address (num, name, phone, age) 
	values (2, '이상우', '010-1234-1111', 33);


-- 테이블 전체 정보 조회
select * from address;


create table person (
	num
	name
	phone
	address
	birth
	gender
);




