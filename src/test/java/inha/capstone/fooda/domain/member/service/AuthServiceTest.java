package inha.capstone.fooda.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.entity.Gender;
import inha.capstone.fooda.domain.member.entity.Member;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import inha.capstone.fooda.security.JwtTokenProvider;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void 유저정보가_주어지면_회원가입을_수행한다() {
        //given
        MemberDto memberDto = createMemberDto();
        given(memberRepository.existsByUsername(any(String.class))).willReturn(false);
        given(memberRepository.save(any(Member.class))).willReturn(createMember());

        //when
        authService.signUp(memberDto);

        //then
        then(memberRepository).should().save(any(Member.class));
    }

    @Test
    public void 유저_닉네임과_카카오_이메일이_주어지면_유저의_카카오_이메일_정보를_수정한다() {
        //given
        Member member = createMember();
        String username = "member1";
        String kakaoEmail = "member1@test.com";
        given(memberRepository.findByUsername(username)).willReturn(Optional.ofNullable(member));

        //when
        authService.connectWithKakao(username, kakaoEmail);

        //then
        assert member != null;
        assertThat(member.getKakaoEmail()).isEqualTo(kakaoEmail);
    }

    private Member createMember() {
        Member member = createMemberDto().toEntity();
        ReflectionTestUtils.setField(member, "id", 1L);
        return member;
    }

    private MemberDto createMemberDto() {
        return MemberDto.of("멤버1", "member1", "pwd1234!", Gender.MALE, 70, 180, 25, 60, 2000);
    }

    @Test
    public void 유저네임이_주어지면_jwt토큰_생성한다() {
        //given
        String username = "gildong";
        String expected = "token";
        given(jwtTokenProvider.createToken(username)).willReturn(expected);

        //when
        String result = authService.createToken(username);

        //then
        assertThat(result).isEqualTo(expected);
        then(jwtTokenProvider).should().createToken(username);
    }
}