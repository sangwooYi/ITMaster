package net.datasa;

import lombok.*;

//@Getter  // 게터 자동생성
//@Setter  // 세터 자동생성
//@ToString  // toString 오버라이딩 자동 생성
//@EqualsAndHashCode // equals() 및 hashcode() 오버라이딩 자동 생성
//@RequiredArgsConstructor // 생성자 자동생성

// @RequiredArgsConstructor 는 final 같이 반드시 초기화해야하는 필드만 생성자에 세팅해준다
// 따라서 NoArgs 나 AllArgs 는 필요시 별도로 추가해줘야함
@AllArgsConstructor
@Data  // 위에 애들 전부 포함해서 한번에 해주는 친구 ( DTO 로 사용되는 친구라는 선언 )
public class Person {
    String userID;
    String password;
    String userName;
    String address;

    // 그냥 위에까만 작성해도 @Getter @Setter 가 아래코드를 자동으로 만들어줌
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
}
