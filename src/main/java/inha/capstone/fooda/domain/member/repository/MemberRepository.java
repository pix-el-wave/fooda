package inha.capstone.fooda.domain.member.repository;

import inha.capstone.fooda.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);

    boolean existsByKakaoEmail(String kakaoEmail);

    Optional<Member> findByUsername(String username);
}
