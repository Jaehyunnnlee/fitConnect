import { Button } from "@mui/material";
import { writeReply } from "../../services/comment";

interface ReplyButtonProps {
  isReplyVisible: boolean; // 답글이 보이는지 여부
  toggleReply: () => void; // 답글 보이기/숨기기 토글 함수
  replyCount: number;
  postNum: string;
  commentNum: number;
  message: string;
}
export const ReplyButton = ({
  isReplyVisible,
  toggleReply,
  replyCount,
  postNum,
  commentNum,
  message,
}: ReplyButtonProps) => {
  const handleWriteReply = async () => {
    try {
      const response = await writeReply(postNum, commentNum, message);
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };
  let buttonText;

  if (replyCount > 0) {
    if (isReplyVisible) {
      buttonText = "취소";
    } else {
      buttonText = `답글 ${replyCount}개`;
    }
  } else if (isReplyVisible) {
    buttonText = "취소";
  } else {
    buttonText = `답글 달기`;
  }
  return (
    <div>
      {isReplyVisible && (
        <Button size="small" onClick={handleWriteReply}>
          답글
        </Button>
      )}
      <Button size="small" onClick={toggleReply}>
        {buttonText}
      </Button>
    </div>
  );
};
