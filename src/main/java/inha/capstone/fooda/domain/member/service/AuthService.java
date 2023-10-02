package inha.capstone.fooda.domain.member.service;

import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.entity.Member;
import inha.capstone.fooda.domain.member.exception.UsernameDuplicateException;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signUp(MemberDto memberDto) {
        // 아이디(닉네임) 중복 여부 확인
        if (memberRepository.existsByUsername(memberDto.getUsername())) {
            throw new UsernameDuplicateException(memberDto.getUsername() + " 는 이미 존재하는 username 입니다.");
        }

        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword())); // 비밀번호 암호화

        Member savedMember = memberRepository.save(memberDto.toEntity());

        return savedMember.getId();
    }
}
