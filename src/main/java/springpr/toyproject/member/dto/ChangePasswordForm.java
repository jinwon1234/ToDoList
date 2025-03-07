package springpr.toyproject.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ChangePasswordForm {

    private Long id;

    @NotBlank(message = "현재 비밀번호를 입력해주세요")
    private String curPassword;

    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    @Size(min = 6, max=14, message = "비밀번호는 6이상 14이하입니다.")
    private String newPassword;

    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    @Size(min = 6, max=14, message = "비밀번호는 6이상 14이하입니다.")
    private String confirmPassword;
}
