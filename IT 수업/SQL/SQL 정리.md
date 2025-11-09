**1. DDL / DML / DCL 관련**

 DDL : 데이터 구조를 정의하는 명령어

→ CREATE TABLE/ ALTER TABLE / TRUNCATE TABLE / DROP TABLE...****

**※ DDL 의 경우는 커밋여부와 관계없이 실행과 동시에 반영된다**



DML : 데이터를 관리하는 명령어

→ INSERT / SELECT / UPDATE / DELETE ... 



DCL : 데이터베이스 컨트롤 관련한 명령어 ( 권한 / 트랜잭션 관련 컨트롤 )

→ 권한 관련 : GRANT ( 권한 설정 ) / REVOKE ( 권한 삭제 )

    트랜잭션 관련 : COMMIT / ROLLBACK ( DCL 굳이 물어보면 이거 물어볼 듯)





**2. CREATE TABLE 관련**

- CREATE TABLE 관련해서 자료형

       → 예상 되는 자료형 char / varchar / int / datetime / timestamp

          ( char vs varchar 차이  /   date vs datetime 차이/ 

            timestamp 에서 default current_timestamp )

- CREATE 에 사용되는 옵션들
  
  →  primary key / auto_increment / not null / unique / check
  
  → 제한조건 추가하는 constraint 관련 ( foreign key 설정 하는 부분 예상 )



**3. SELECT 관련**

- SELECT /  WHERE / GROUP BY / HAVING / ORDER BY 실행 순서
  
  → WHERE -> GROUP BY -> HAVING -> SELECT -> ORDER BY

- 조건 연산자  AND / OR / IN / NOT /  =  /  != (<> 와 동일) 

- LIKE 검색의 와일드카드 검색 % 와 _ 의 차이  

- CASE WHEN 을 사용한 조건 분기 
  
  ( 사실 WHERE 조건이나 다른곳에서도 사용 가능하나 우선 SELECT 에서만 적용)

- 정렬 ( ASC 오름차순 (디폴트) , DESC 내림차순 )

- DISTINCT 사용

- GROUP BY / HAVING 사용
  
  → 기본적으로 GROUP BY에 조건으로 설정한 컬럼들만 SELECT 문에 사용 가능하다 
  
  ( ※ MySql 에서는 Unique 컬럼일 경우 GROUP BY 조건에 없어도 사용 가능하도록 허용해주나,  기본적인 SQL 체계에서는 안 됨)
  
  

**4. MySql 제공 함수**

- 문자열 처리함수 ( UPPER / TRIM / CONCATE / LENGTH / CHAR_LENGTH / SUBSTR )

- 날짜 관련 함수 ( NOW / CURRENT_TIMESTAMP / DATE_ADD / DATE_FORMAT / STR_TO_DATE / YEAR / MONTH / DAY / DATEDIFF / TIMESTAMPDIFF)

- 집계 관련 함수  ( COUNT / SUM / AVG / MIN / MAX )

- 숫자 처리 함수 ( ROUND / CEIL / FLOOR )

- 조건 분기 함수 ( IF ) 



**5. 그 외 UPDATE / DELETE**

```sql
-- UPDATE 
UPDATE student
SET english = 99
WHERE num = 2;

-- 업데이트 컬럼이 여러개인 경우 , 로 연결
UPDATE student
SET phone = '010-1234-56781'
     ,birth = STR_TO_DATE('1998-03-22', '%Y-%m-%d')
WHERE num = 2;


-- DELETE
DELETE FROM student
WHERE name = '김철수';
```
