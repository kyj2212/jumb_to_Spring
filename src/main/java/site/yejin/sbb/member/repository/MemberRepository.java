package site.yejin.sbb.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.yejin.sbb.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);

}