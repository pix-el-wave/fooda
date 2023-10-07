package inha.capstone.fooda.security;

import inha.capstone.fooda.domain.member.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsServiceImpl {
    @Bean
    public UserDetailsService userDetailsService(MemberService memberService) {
        return username -> FoodaPrinciple.of(memberService.findMemberByUsername(username));
    }
}
