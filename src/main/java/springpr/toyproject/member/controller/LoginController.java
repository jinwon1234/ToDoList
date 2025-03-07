package springpr.toyproject.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springpr.toyproject.domain.Member;
import springpr.toyproject.member.dto.JoinForm;
import springpr.toyproject.member.dto.LoginForm;
import springpr.toyproject.member.service.MemberService;
import springpr.toyproject.member.session.SessionConst;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login(Model model, @RequestParam(defaultValue = "/") String redirectURL) {
        LoginForm member = new LoginForm();
        model.addAttribute("member",member);
        model.addAttribute("redirectURL", redirectURL);
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("member") LoginForm form,
                        BindingResult bindingResult, RedirectAttributes redirectAttributes,
                        HttpServletRequest request,
                        @RequestParam String redirectURL) {
        if (bindingResult.hasErrors()) {
            return "member/login";
        }

        String id = form.getUserId();
        String password = form.getPassword();

        Member loginMember = memberService.login(id, password);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "잘못된 아이디 혹은 비밀번호입니다");
            return "member/login";
        }

        /**
         * true - 기존 세션이 없으면 새로 생성, 기존세선이 있으면 기존 값 사용
         * false - 기존 세션이 없으면 nulL 반환, 기존세선이 있으면 기존 값 사용
         */
        HttpSession session = request.getSession(true);
        // .setAttribute(세션 이름, 저장값)
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:" + redirectURL;

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate(); //세션 삭제
        }

        return "redirect:/";
    }

    @GetMapping("/join")
    public String join(Model model) {
        JoinForm joinForm = new JoinForm();
        model.addAttribute("member",joinForm);
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("member") JoinForm form,
                       BindingResult bindingResult,
                       @RequestParam MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            return "member/join";
        }


        if (!memberService.join(form, file)) {
            bindingResult.reject("joinFail", "이미 존재하는 아이디입니다");
            return "member/join";
        }


        return "redirect:/login";
    }
}
