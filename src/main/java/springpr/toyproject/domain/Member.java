package springpr.toyproject.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name ="member_id")
    private Long id;

    private String name;

    private String userId;
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ToDoForm> forms = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private UserImage userImage;

    public Member(String name, String userId, String password, UserImage userImage) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.userImage = userImage;
        userImage.changeMember(this);
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePassword(String password) {
        this.password = password;
    }


    protected Member() {};
}
