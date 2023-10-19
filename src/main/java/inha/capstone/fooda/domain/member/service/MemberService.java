package inha.capstone.fooda.domain.member.service;

import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.entity.Member;
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
                        .orElseThrow(() -> new UsernameNotFoundException(username + " 아이디를 가진 유저가 존재하지 않습니다.")));
    }

    /**
     * 유저네임(아이디)의 유저의 정보를 변경
     *
     * @param username          변경하려는 유저의 유저네임(아이디)
     * @param memberDtoToUpdate 변경하려는 정보가 담긴 MemberDTO
     * @return 변경한 MemberDto
     */
    @Transactional
    public MemberDto modifyMemberByUsername(String username, MemberDto memberDtoToUpdate) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " 아이디를 가진 유저가 존재하지 않습니다."));

        member.update(memberDtoToUpdate); // 회원 정보 변경

        return MemberDto.from(member);
    }

    /**
     * 특정 username을 가지는 회원의 존재 여부를 반환한다.
     *
     * @param username 조회하려는 유저네임(아이디)
     * @return 해당 username을 가지는 회원이 존재하면 true, 아니면 false를 반환
     */
    public boolean existsMemberByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }
}
