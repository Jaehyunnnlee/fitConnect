package fitConnect.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import fitConnect.controller.dto.ReplyRequestDto;
import fitConnect.entity.user.User;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reply extends Timestamped{
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        @Column(name="REPLYNUM",nullable = false)
        private long replyNum;

        @Column(name="REPLYCONTENT",nullable = false)
        private String replyContent;

        @ManyToOne
        @JoinColumn(name = "userid", referencedColumnName = "userid", columnDefinition = "VARCHAR(255)", nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name="commentNum",referencedColumnName = "COMMENTNUM",columnDefinition = "BIGINT", nullable = false)
        private Comment comment;

        public Reply(ReplyRequestDto requestDto){
            this.replyContent=requestDto.getReplyContent();
            this.user=requestDto.getUser();
            this.comment=requestDto.getComment();
        }

}
