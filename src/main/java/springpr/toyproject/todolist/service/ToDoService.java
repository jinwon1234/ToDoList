package springpr.toyproject.todolist.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springpr.toyproject.domain.Member;
import springpr.toyproject.domain.ToDoForm;
import springpr.toyproject.member.repository.MemberRepository;
import springpr.toyproject.todolist.dto.ToDoDto;
import springpr.toyproject.todolist.repository.ToDoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public void save(ToDoDto dto, Member member) {
        ToDoForm toDoForm = new ToDoForm(dto.getTitle(), dto.getContent(), member);
        toDoRepository.save(toDoForm);
    }

    public List<ToDoDto> findForms(Member member) {
        return toDoRepository.findByMember(member).stream()
                .map(form -> new ToDoDto(form.getId(), form.getTitle(), form.getContent()))
                .collect(Collectors.toList());
    }

    public ToDoDto findForm(Long id) {
        ToDoForm toDoForm = toDoRepository.findById(id).orElseThrow();
        return new ToDoDto(toDoForm.getId(), toDoForm.getTitle(), toDoForm.getContent());
    }

    public void updateForm(ToDoDto toDoDto) {
        /**
         * toDoForm은 Member의 List<ToDoForm>의 객체와 같은 객체를 참조하고 있기 때문에 연관관계 편의 메소드가 필요없다.
         * -> 당연한 소리인데 한 번에 떠오르진 않았다.
         */
        ToDoForm toDoForm = toDoRepository.findById(toDoDto.getId()).orElseThrow();
        toDoForm.changeTitle(toDoDto.getTitle());
        toDoForm.changeContent(toDoDto.getContent());
    }

    public void delete(Long id) {
        toDoRepository.deleteById(id);
    }
}
