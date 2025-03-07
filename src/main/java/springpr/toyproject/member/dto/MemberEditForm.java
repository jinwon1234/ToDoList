package springpr.toyproject.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Setter;
import springpr.toyproject.domain.UserImage;

@Data
@Setter
public class MemberEditForm {

    private Long id;

    private String userId;

    @NotBlank(message = "유저 이름은 필수 값입니다.")
    @Size(min = 3, max=10, message = "이름은 3이상 10이하입니다.")
    private String name;

    private String imageURL;

    public MemberEditForm(Long id, String userId, String name, String imageURL) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.imageURL = imageURL;
    }
}
