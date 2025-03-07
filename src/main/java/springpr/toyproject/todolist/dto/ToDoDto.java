package springpr.toyproject.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ToDoDto {

    private Long id;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    public ToDoDto(){
    }

    public ToDoDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
