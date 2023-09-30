package inha.capstone.fooda.domain.member.dto;

import inha.capstone.fooda.domain.member.entity.Gender;
import inha.capstone.fooda.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberDto {
    private Long id;
    private String name;
    private String username;
    private String password;
    private Gender gender;
    private Integer weight;
    private Integer height;
    private Integer age;

    public static MemberDto of(String name, String username, String password, Gender gender, Integer weight, Integer height, Integer age) {
        return new MemberDto(null, name, username, password, gender, weight, height, age);
    }

    public Member toEntity() {
        return Member.builder()
                .id(getId())
                .name(getName())
                .username(getUsername())
                .password(getPassword())
                .gender(getGender())
                .weight(getWeight())
                .height(getHeight())
                .age(getAge())
                .build();
    }
}
