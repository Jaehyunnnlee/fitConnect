package myBlog.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;
import myBlog.controller.dto.UserRequestDto;
import myBlog.entity.Board;
import myBlog.entity.Comment;
import myBlog.entity.Timestamped;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor()
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long userNum;

    @Column(name="name",nullable = false)
    private String userName;

    @Column(name="userid",nullable = false)
    private String userId;

    @Column(name="password",nullable = false)
    private String userPwd;

    @Enumerated(EnumType.STRING)
    @Column(name="role",nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    public User(UserRequestDto requestDto){
        this.userId=requestDto.getUserId();
        this.userPwd=requestDto.getUserPwd();
        this.userName=requestDto.getUserName();
        this.role=Role.USER;
    }
}

