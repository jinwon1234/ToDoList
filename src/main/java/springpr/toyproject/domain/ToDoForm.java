package springpr.toyproject.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ToDoForm extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Status status;

    protected ToDoForm() {
    }

    public ToDoForm(String title, String content, Member member, Status status) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.status = status;
    }

    public void changeTitle(String newTitle) {
        this.title = newTitle;
    }

    public void changeContent(String newContent) {
        this.content = newContent;
    }

    public void changeStatus(Status newStatus) {
        this.status = newStatus;
    }
}
