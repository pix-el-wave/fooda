package inha.capstone.fooda.domain.member.service;

import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.entity.Member;
import inha.capstone.fooda.domain.member.exception.UsernameDuplicateException;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(MemberDto memberDto) {
        if (memberRepository.existsByUsername(memberDto.getUsername())) {
            throw new UsernameDuplicateException();
        }
        Member savedMember = memberRepository.save(memberDto.toEntity());
        return savedMember.getId();
    }
}
