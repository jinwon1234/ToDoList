package springpr.toyproject.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import springpr.toyproject.domain.Status;

@Data
public class ToDoDto {

    private Long id;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private Status status;

    public ToDoDto(){
    }

    public ToDoDto(Long id, String title, String content, Status status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
    }
}
