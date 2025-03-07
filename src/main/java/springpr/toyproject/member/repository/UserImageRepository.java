package springpr.toyproject.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springpr.toyproject.domain.Member;
import springpr.toyproject.domain.UserImage;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    Member findByMember(Member member);
}
