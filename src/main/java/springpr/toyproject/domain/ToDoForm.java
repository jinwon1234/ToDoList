package springpr.toyproject.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ToDoForm {

    @Id @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    private String title;
    private String content;

    protected ToDoForm() {
    }

    public ToDoForm(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void changeTitle(String newTitle) {
        this.title = newTitle;
    }

    public void changeContent(String newContent) {
        this.content = newContent;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
