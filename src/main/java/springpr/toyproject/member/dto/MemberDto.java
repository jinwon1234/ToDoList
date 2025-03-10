package springpr.toyproject.member.dto;

import lombok.Data;
import springpr.toyproject.domain.UserImage;

@Data
public class MemberDto {
    private Long id;
    private String userId;
    private String name;
    private String userImage;

    public MemberDto(Long id,String userId, String name, String userImage) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.userImage = userImage;
    }
}
