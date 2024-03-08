package myBlog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myBlog.controller.dto.BoardRequestDto;
import myBlog.entity.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="NUM",nullable = false)
    private Long postNum;

    @Column(name="TITLE",nullable = false,length=30)
    private String postTitle;

    @Column(name="CONTENT",nullable = false,length = 300)
    private String postContent;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid", columnDefinition = "VARCHAR(255)", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    public Board(BoardRequestDto requestDto) {
        this.postTitle=requestDto.getPostTitle();
        this.postContent=requestDto.getPostContent();
        this.user=requestDto.getUser();
    }
}
