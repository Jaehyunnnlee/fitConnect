package fitConnect.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;
import fitConnect.controller.dto.UserRequestDto;
import fitConnect.entity.Board;
import fitConnect.entity.Comment;
import fitConnect.entity.Timestamped;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor()
public class User extends Timestamped{
    @Id
    @Column(name="user_id",nullable = false)
    private String userId;

    @Column(name="user_name",nullable = false)
    private String userName;

    @Column(name="user_password",nullable = false)
    private String userPwd;

    @Enumerated(EnumType.STRING)
    @Column(name="user_role",nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();


    public User(UserRequestDto requestDto){
        this.userId=requestDto.getUserId();
        this.userPwd=requestDto.getUserPwd();
        this.userName=requestDto.getUserName();
        this.role=Role.USER;
    }
}

