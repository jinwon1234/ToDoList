package springpr.toyproject.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springpr.toyproject.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserIdAndPassword(String userId, String password);
    Member findByUserId(String userId);
}
