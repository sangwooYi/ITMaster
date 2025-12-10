package net.datasa.web5.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.dto.MemberDto;
import net.datasa.web5.entity.MemberEntity;
import net.datasa.web5.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    // 암호화 인코더
    private final BCryptPasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    public MemberDto saveMember(MemberDto memberDto) {

        // 암호화
        String encPassword = passwordEncoder.encode(memberDto.getPassword());

        MemberEntity memberEntity = MemberEntity.builder()
                .userId(memberDto.getUserId())
                .password(encPassword)
                .userName(memberDto.getUserName())
                .mailAddress(memberDto.getMailAddress())
                .phoneNumber(memberDto.getPhoneNumber())
                .address(memberDto.getAddress())
                .isActive((byte)1)
                .roleName("role_normal")
                .build();

        MemberEntity result = memberRepository.save(memberEntity);

        return this.convertToDto(result);
    }

    public MemberDto findMemberById(String userId) {

        MemberEntity entity = memberRepository.findById(userId).orElse(null);

        MemberDto result = null;

        if (!ObjectUtils.isEmpty(entity)) {
            result = this.convertToDto(entity);
        }
        return result;
    }

    public boolean chkPassword(String userId, String chkPassword) {

        MemberEntity entity = memberRepository.findById(userId).orElse(null);

        if (ObjectUtils.isEmpty(entity)) {
            return false;
        }

        if (passwordEncoder.matches(chkPassword, entity.getPassword())) {
            return true;
        }
        return false;
    }

    public MemberDto updateMember(MemberDto memberDto) {

        MemberEntity entity = memberRepository.findById(memberDto.getUserId()).orElse(null);
        if (!ObjectUtils.isEmpty(entity)) {

            if (StringUtils.hasText(memberDto.getUpdatePassword())) {
                // 인코딩해서 넘겨야 함!
                entity.setPassword(passwordEncoder.encode(memberDto.getUpdatePassword()));
            }
            entity.setUserName(memberDto.getUserName());
            entity.setMailAddress(memberDto.getMailAddress());
            entity.setAddress(memberDto.getAddress());
            entity.setPhoneNumber(memberDto.getPhoneNumber());
        }
        log.info("entity 변경 후 = {}" ,entity);

        memberRepository.save(entity);

        return memberDto;
    }

    public MemberDto convertToDto(MemberEntity memberEntity) {

        MemberDto memberDto = new MemberDto();

        if (!ObjectUtils.isEmpty(memberEntity.getUserId())) {
            memberDto.setUserId(memberEntity.getUserId());
        }
        if (!ObjectUtils.isEmpty(memberEntity.getPassword())) {
            memberDto.setPassword(memberEntity.getPassword());
        }
        if (!ObjectUtils.isEmpty(memberEntity.getUserName())) {
            memberDto.setUserName(memberEntity.getUserName());
        }
        if (!ObjectUtils.isEmpty(memberEntity.getMailAddress())) {
            memberDto.setMailAddress(memberEntity.getMailAddress());
        }
        if (!ObjectUtils.isEmpty(memberEntity.getPhoneNumber())) {
            memberDto.setPhoneNumber(memberEntity.getPhoneNumber());
        }
        if (!ObjectUtils.isEmpty(memberEntity.getAddress())) {
            memberDto.setAddress(memberEntity.getAddress());
        }
        if (!ObjectUtils.isEmpty(memberEntity.getRoleName())) {
            memberDto.setRoleName(memberEntity.getRoleName());
        }
        if (!ObjectUtils.isEmpty(memberEntity.getRegisterDate())) {
            memberDto.setRegisterDate(memberEntity.getRegisterDate());
        }
        if (!ObjectUtils.isEmpty(memberEntity.getIsActive())) {
            memberDto.setIsActive(memberEntity.getIsActive());
        }
        return memberDto;
    }
}
