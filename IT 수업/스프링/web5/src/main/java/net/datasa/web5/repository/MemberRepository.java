package net.datasa.web5.repository;

import net.datasa.web5.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.List;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    // %검색값%  이건 Containing
    // ( 주의! 그냥 Like 는 %% 를 안붙여준다 조심 !!! 그냥 Like 검색값 이걸 돌려주는 친구 (안써도 됨))
    // 검색값% 이건 StartingWith
    // %검색값 이건 EndingWith
    List<MemberEntity> findAllByUserNameContainingOrderByUserName(String name);
    List<MemberEntity> findAllByUserIdContaining(String userId);

    // 라이크 검색 ( 이따구로 할바에야 그냥 쿼리 쓰자..)
    List<MemberEntity> findAllByUserNameContainingAndMailAddressContainingAndPhoneNumberContaining(String userName, String mailAddress, String phoneNumber);

    // 정확히 나와야만 오케이
    List<MemberEntity> findAllByUserNameAndMailAddress(String userName, String mailAddress);
}
