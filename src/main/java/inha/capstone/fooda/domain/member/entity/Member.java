package inha.capstone.fooda.domain.member.entity;

import inha.capstone.fooda.domain.common.entity.BaseEntity;
import inha.capstone.fooda.domain.member.dto.MemberDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Integer weight;

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private Integer targetWeight;

    @Column(nullable = false)
    private Integer targetKcal;

    @Column(nullable = true)
    @Setter // 애플리케이션 회원가입 후 카카오 로그인 연동을 위해 필요
    private String kakaoEmail; // 카카오 로그인 연동 여부 판별로 사용됨.

    @Builder
    public Member(Long id, String name, String username, String password, Gender gender, Integer weight, Integer height,
                  Integer age, Role role, Integer targetWeight, Integer targetKcal, String kakaoEmail) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.role = role;
        this.targetWeight = targetWeight;
        this.targetKcal = targetKcal;
        this.kakaoEmail = kakaoEmail;
    }

    /**
     * memberDto에 따라 엔티티의 정보를 수정한다.
     *
     * @param memberDto 수정하려는 정보가 담긴 memberDto
     */
    public void update(MemberDto memberDto) {
        this.height = memberDto.getHeight();
        this.weight = memberDto.getWeight();
        this.age = memberDto.getAge();
        this.targetWeight = memberDto.getTargetWeight();
        this.targetKcal = memberDto.getTargetKcal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        Member that = (Member) o;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
