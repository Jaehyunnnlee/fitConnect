//package fitConnect.controller.dto.response;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import fitConnect.entity.Reply;
//
//import java.time.LocalDateTime;
//
//@Getter
//@NoArgsConstructor
//public class ReplyResonseDto {
//    private Long replyNum;
//    private String replyContent;
//    private String userName;
//    private String userId;
//    private LocalDateTime modifiedAt;
//
//    public ReplyResonseDto(Reply entity){
//        this.replyNum=entity.getReplyNum();
//        this.replyContent=entity.getReplyContent();
//        this.userName=entity.getUser().getUserName();
//        this.modifiedAt = entity.getModifiedAt();
//        this.userId=entity.getUser().getUserId();
//    }
//}
