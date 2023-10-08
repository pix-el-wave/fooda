package inha.capstone.fooda.domain.member.service;

import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 유저네임(아이디)로 멤버 조회
     *
     * @param username 조회하려는 유저네임(아이디)
     * @return 조회한 Member Entity
     */
    public MemberDto findMemberByUsername(String username) {
        return MemberDto.from(
                memberRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username + "은 이미 존재하는 username 입니다.")));
    }
}
