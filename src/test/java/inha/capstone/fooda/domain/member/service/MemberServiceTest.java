package inha.capstone.fooda.domain.member.service;

import inha.capstone.fooda.domain.member.dto.MemberDto;
import inha.capstone.fooda.domain.member.entity.Gender;
import inha.capstone.fooda.domain.member.entity.Member;
import inha.capstone.fooda.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    public void 유저아이디가_주어지면_멤버정보를_조회한다() {
        //given
        String username = "member1";
        MemberDto expected = MemberDto.from(createMember());
        given(memberRepository.findByUsername(username)).willReturn(Optional.of(createMember())); // MemberDto가 아닌 실제 Member 객체 생성

        //when
        MemberDto result = memberService.findMemberByUsername(username);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void 존재하지_않는_아이디가_주어지면_예외를_던진다() {
        String username = "member1";
        assertThrows(UsernameNotFoundException.class,
                () -> memberService.findMemberByUsername(username));
    }

    @Test
    public void 유저아이디와_변경하려는_정보가_주어지면_유저정보를_변경한다() {
        //given
        String username = "member1";
        MemberDto expected = MemberDto.from(createMemberToModify());
        Member existingMember = createMember();
        given(memberRepository.findByUsername(username)).willReturn(Optional.of(existingMember)); // MemberDto가 아닌 실제 Member 객체 생성

        //when
        MemberDto result = memberService.modifyMemberByUsername(username, expected);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private Member createMember() {
        Member member = createMemberDto().toEntity();
        ReflectionTestUtils.setField(member, "id", 1L);
        return member;
    }

    private MemberDto createMemberDto() {
        return MemberDto.of("멤버1", "member1", "pwd1234!", Gender.MALE, 70, 180, 25, 60, 2000);
    }

    private Member createMemberToModify() {
        Member member = createMemberDtoToModify().toEntity();
        ReflectionTestUtils.setField(member, "id", 1L);
        return member;
    }

    private MemberDto createMemberDtoToModify() {
        return MemberDto.of("멤버1", "member1", "pwd1234!", Gender.MALE, 75, 185, 26, 70, 2500);
    }

    @Test
    public void 아이디가_주어졌을때_중복_또는_존재_여부를_판별한다() {
        // given
        String username_exist = "member1";
        String username_not_exist = "member2";
        given(memberRepository.existsByUsername(username_exist)).willReturn(true);

        //when
        boolean result_exist = memberService.existsMemberByUsername(username_exist);
        boolean result_not_exist = memberService.existsMemberByUsername(username_not_exist);

        //then
        assertThat(result_exist).isEqualTo(true);
        assertThat(result_not_exist).isEqualTo(false);
    }
}