### git 형상관리 전체 flow

![](C:\Users\82105\AppData\Roaming\marktext\images\2025-11-08-18-36-53-image.png)

### git 명령어

- clone

- status

- add

- commit

- push

- pull

###### clone

###### : github에서 최초 가져오기

작업할 컴퓨터에서 최초로 github 정보를 가져올 때 사용하는 명령어git clone 원격저장소 주소ex) 

```git
git clone {git 원격 저장소 주소}


ex) git clone https://github.com/sangwooYi/ITMaster.git
```

참고 ) 원격 저장소 주소는 하기 위치에서 확인

![](C:\Users\82105\AppData\Roaming\marktext\images\2025-11-08-18-39-54-image.png)

###### status

: 현재 로컬 작업디렉토리 형상 상태 확인

> git status

###### add

: 스테이징 영역에 임시 저장-

> git add .                // 변경사항 전체 add
> 
> git add 파일명    // 특정 파일만 add ( ex) git add test.txt ) commit 

###### commit

: 로컬 영역에서 최종 형상 저장

> git commit -m 커밋메시지
> 
> ex ) git commit -m 'first commit'

###### push

: 로컬 영역의 형상을 github 원격 저장소에 적용

> git push origin 브랜치명
> 
> ex ) git push origin main 

현재 작업중인 브랜치명은 아래처럼 git bash 창에서 확인 가능

![](C:\Users\82105\AppData\Roaming\marktext\images\2025-11-08-19-00-28-image.png)

###### pull

: github 원격저장소의 브랜치 정보를 로컬에서 끌어오는 것

**반드시 push 전에 pull 을 먼저 생활하 하자**

> git pull origin 브랜치명

**checkout**  

: 브랜치를 이동하는 명령

> git checkout 브랜치명  // 해당 브랜치로 이동

**branch** 

: branch 확인 혹은 브랜치 생성

> git branch -a     // 현재 존재하는 모든 브랜치 확인
> 
> git branch 브랜치명 // 해당 브랜치로 브랜치 생성
