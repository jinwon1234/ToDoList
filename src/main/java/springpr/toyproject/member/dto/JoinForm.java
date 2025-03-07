package springpr.toyproject.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JoinForm {

    @NotBlank(message = "유저 이름은 필수 값입니다.")
    @Size(min = 3, max=10, message = "이름은 3이상 10이하입니다.")
    private String username;

    @NotBlank(message = "아이디는 필수 값입니다.")
    @Size(min = 6, max=14, message = "아이디는 6이상 14이하입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 값입니다.")
    @Size(min = 6, max=14, message = "비밀번호는 6이상 14이하입니다.")
    private String password;

}