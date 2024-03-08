package myBlog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myBlog.controller.dto.CommentRequestDto;
import myBlog.entity.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="COMMENTNUM",nullable = false)
    private long commentNum;

    @Column(name="COMMENTCONTENT",nullable = false)
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid", columnDefinition = "VARCHAR(255)", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "postNum", referencedColumnName = "NUM", columnDefinition = "BIGINT", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    public Comment(CommentRequestDto requestDto){
        this.commentContent=requestDto.getCommentContent();
        this.user=requestDto.getUser();
        this.board=requestDto.getBoard();
    }
}
