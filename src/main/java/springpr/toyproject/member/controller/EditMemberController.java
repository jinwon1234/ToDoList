package springpr.toyproject.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springpr.toyproject.domain.Member;
import springpr.toyproject.member.dto.ChangePasswordForm;
import springpr.toyproject.member.dto.MemberEditForm;
import springpr.toyproject.member.service.MemberService;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EditMemberController {

    private final MemberService memberService;

    @GetMapping("{id}/edit")
    public String editMember(@PathVariable Long id, Model model) {
        Member member = memberService.findMember(id);
        MemberEditForm editForm = new MemberEditForm(member.getId(), member.getUserId(), member.getName(), member.getUserImage().getUrl());
        model.addAttribute("member", editForm);

        log.info("url : {}", member.getUserImage().getUrl());

        return "member/edit";
    }

    @PostMapping("{id}/edit")
    public String editMember(@PathVariable Long id, @Validated @ModelAttribute("member") MemberEditForm editForm,
                             BindingResult bindingResult, @RequestParam MultipartFile profileImage,
                             Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            /**
             * 객체 바인딩에서 오류가 발생했을 때 다른 필드값들이 null로 남아있을 수도 있다.
             * 이를 방지하기 위해서는 매개변수인 editForm에 기존 값들을 모두 넣어서 전달해야 출력이 정상적으로되고 오류메시지도 표시가된다.
             */
            Member member = memberService.findMember(id);
            editForm.setId(member.getId());
            editForm.setUserId(member.getUserId());
            editForm.setImageURL(member.getUserImage().getUrl());
            model.addAttribute("member", editForm);
            return "member/edit";
        }

        memberService.editProfile(editForm, profileImage);

        return "redirect:/";

    }

    @PostMapping("{id}/deleteImage")
    public String deleteImage(@PathVariable Long id) {
        memberService.deleteImage(id);

        return "redirect:/{id}/edit";
    }

    @GetMapping("/{id}/password")
    public String editPassword(@PathVariable Long id, Model model) {
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        changePasswordForm.setId(id);
        model.addAttribute("form", changePasswordForm);
        return "member/editpassword";
    }

    @PostMapping("/{id}/password")
    public String editPassword(@Validated @ModelAttribute("form") ChangePasswordForm form,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/editpassword";
        }

        if (!memberService.checkPassword(form)) {
            bindingResult.reject("curPassword", "비밀번호가 일치하지 않습니다");
            return "member/editpassword";
        }

        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            bindingResult.reject("confirmPassword", "새로운 비밀번호와 일치하지 않습니다.");
            return "member/editpassword";
        }


        memberService.changePassword(form);

        return "redirect:/{id}/edit";
    }
}
