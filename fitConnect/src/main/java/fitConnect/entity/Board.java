package fitConnect.entity;

import fitConnect.controller.dto.BoardRequestDto;
import fitConnect.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="post_num",nullable = false)
    private Long postNum;

    @Column(name="post_title",nullable = false,length=30)
    private String postTitle;

    @Column(name="post_content",nullable = false,length = 300)
    private String postContent;

    @ManyToOne
    @JoinColumn(name = "post_user_id", referencedColumnName = "user_id", columnDefinition = "VARCHAR(255)", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "board", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


    public Board(BoardRequestDto requestDto) {
        this.postTitle=requestDto.getPostTitle();
        this.postContent=requestDto.getPostContent();
        this.user=requestDto.getUser();
    }
}
