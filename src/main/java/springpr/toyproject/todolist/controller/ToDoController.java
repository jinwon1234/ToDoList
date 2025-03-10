package springpr.toyproject.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springpr.toyproject.domain.Member;
import springpr.toyproject.member.dto.MemberDto;
import springpr.toyproject.member.service.MemberService;
import springpr.toyproject.member.session.SessionConst;
import springpr.toyproject.todolist.dto.SearchToDoCond;
import springpr.toyproject.todolist.dto.ToDoDto;
import springpr.toyproject.todolist.service.ToDoService;

import java.util.List;

@Controller
@RequestMapping("/todo")
@RequiredArgsConstructor
@Slf4j
public class ToDoController {

    private final ToDoService toDoService;
    private final MemberService memberService;

    @GetMapping("/new")
    public String getForm(@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model) {
        ToDoDto toDoForm = new ToDoDto();

        model.addAttribute("todoForm", toDoForm);
        return "writeform";
    }

    @PostMapping("/new")
    public String writeForm(@SessionAttribute(SessionConst.LOGIN_MEMBER) Member member,
            @Validated @ModelAttribute("todoForm") ToDoDto form,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "writeform";
        }

        toDoService.save(form,member);

        return "redirect:/";
    }

    @PostMapping("/{id}/deleteform")
    public String deleteForm(@PathVariable Long id) {
        toDoService.delete(id);

        return "redirect:/";
    }

    @GetMapping("/{id}/updateform")
    public String updateForm(@PathVariable Long id, Model model) {
        ToDoDto form = toDoService.findForm(id);
        model.addAttribute("todoForm", form);

        return "updateform";
    }

    @PostMapping("/{id}/updateform")
    public String updateForm(@Validated @ModelAttribute("todoForm") ToDoDto todoForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("{}" ,todoForm.getId());
            return "updateform";
        }

        toDoService.updateForm(todoForm);

        return "redirect:/";
    }

    @PostMapping("/{id}/status")
    public String changeStatus(@PathVariable Long id, Model model) {
        toDoService.changeStatus(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/search")
    public String search(@PathVariable Long id, @ModelAttribute SearchToDoCond searchToDoCond,
                         Model model) {
        log.info("check {} {} {}", searchToDoCond.getTitleCond(), searchToDoCond.getContentCond(), searchToDoCond.getContentCond());
        List<ToDoDto> form = toDoService.searchToDoForm(id, searchToDoCond);
        MemberDto member = memberService.findMember(id);
        model.addAttribute("member", member);
        model.addAttribute("formList", form);
        return "home";
    }

}
