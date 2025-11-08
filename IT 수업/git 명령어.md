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

작업할 컴퓨터에서 최초로 github 정보를 가져올 때 사용하는 명령어

```git
git clone {git 원격 저장소 주소}


ex) git clone 
```

참고 ) 원격 저장소 주소는 하기 위치에서 확인

![](C:\Users\82105\AppData\Roaming\marktext\images\2025-11-08-18-39-54-image.png)

###### status

: 현재 로컬 작업디렉토리 형상 상태 확인





###### add

: 스테이징 영역에 임시 저장

```git
git add .          # 변경사항 전체 add
git add 파일명      # 확장자까지 써줘야하며 특정 파일만 add
                   # ex) git add test.txt
```



###### commit
