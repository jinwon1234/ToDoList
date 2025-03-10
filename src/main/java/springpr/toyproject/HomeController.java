package springpr.toyproject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import springpr.toyproject.domain.Member;
import springpr.toyproject.member.dto.MemberDto;
import springpr.toyproject.member.service.MemberService;
import springpr.toyproject.member.session.SessionConst;
import springpr.toyproject.todolist.dto.ToDoDto;
import springpr.toyproject.todolist.service.ToDoService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ToDoService toDoService;
    private final MemberService memberService;

    @GetMapping("/")
    public String home(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false)
                       Member member, Model model) {

        if (member == null) {
            return "member/loginHome";
        }

        /**
         * 최신 업데이트가 된 Member 객체를 보내야한다.
         * 세션(@SessionAttribute)을 통해 받은 Member를 모델로 전달하면 수정내용이 반영되지 않은채로 넘겨질 수 있다.
         */
        MemberDto findMember = memberService.findMember(member.getId());

        List<ToDoDto> formList = toDoService.findForms(findMember);
        model.addAttribute("formList", formList);
        model.addAttribute("member", findMember);

        return "home";
    }
}
