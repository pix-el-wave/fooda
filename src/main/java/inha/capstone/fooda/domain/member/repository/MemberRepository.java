package inha.capstone.fooda.domain.member.repository;

import inha.capstone.fooda.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);

    Optional<Member> findByUsername(String username);
}
