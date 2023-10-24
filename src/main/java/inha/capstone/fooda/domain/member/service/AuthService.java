package inha.capstone.fooda.domain.member.service;

import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.entity.Member;
import inha.capstone.fooda.domain.member.entity.Role;
import inha.capstone.fooda.domain.member.exception.JwtUnauthorizedException;
import inha.capstone.fooda.domain.member.exception.UsernameDuplicateException;
import inha.capstone.fooda.domain.member.exception.UsernameNotFoundExcpetion;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import inha.capstone.fooda.security.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long signUp(MemberDto memberDto) {
        // 아이디(닉네임) 중복 여부 확인
        if (memberRepository.existsByUsername(memberDto.getUsername())) {
            throw new UsernameDuplicateException(memberDto.getUsername() + " 는 이미 존재하는 username 입니다.");
        }
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword())); // 비밀번호 암호화
        memberDto.setRole(Role.USER); // USER 권한 부여
        Member savedMember = memberRepository.save(memberDto.toEntity()); // 회원 저장

        return savedMember.getId();
    }

    public String signIn(String username, String password) {
        // 해당 아이디를 가진 멤버 조회
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundExcpetion(username));

        // 멤버의 비밀번호가 일치할 경우 JWT 토큰 생성
        if (passwordEncoder.matches(password, member.getPassword())) {
            return createToken(username);
        } else {
            throw new JwtUnauthorizedException();
        }
    }

    public String createToken(String username) {
        return jwtTokenProvider.createToken(username);
    }

    /**
     * username에 해당하는 유저의 카카오 계정을 연동한다.
     *
     * @param username   유저의 닉네임(아이디)
     * @param kakaoEmail 유저의 카카오 이메일
     */
    @Transactional
    public void connectWithKakao(String username, String kakaoEmail) {
        // 해당 아이디를 가진 멤버 조회
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundExcpetion(username));

        // 해당 멤버의 카카오 이메일 설정
        member.setKakaoEmail(kakaoEmail);
    }
}
