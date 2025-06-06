package springpr.toyproject.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class UserImage extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateURL(String url) {
        this.url = url;
    }

    public void changeMember(Member member) {
        this.member = member;
    }
}
