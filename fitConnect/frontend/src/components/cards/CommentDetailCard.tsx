// import { useState } from "react";
import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  Divider,
  IconButton,
  TextField,
  Typography,
} from "@mui/material";
import { memo, useState } from "react";
import { Comment } from "./CommentCard";
import { ReplyCard } from "./ReplyCard";
import { deleteComment, writeReply } from "../../services/comment";

interface CommentDetailCardProps {
  comment: Comment;
  postNum: string;
}
const CommentDetailCard = memo(
  ({ comment, postNum }: CommentDetailCardProps) => {
    const [isReplyVisible, setIsReplyVisible] = useState(false);
    const [message, setMessage] = useState("");
    const toggleReply = () => {
      setIsReplyVisible((prev: boolean) => !prev);
      setMessage("");
    };
    const [isFocus, setIsFocus] = useState(false);

    const handleFocus = () => {
      setIsFocus(true);
    };
    const handleCancel = () => {
      setIsFocus(false);
      setMessage("");
    };

    const handleWriteReply = async () => {
      try {
        if (!message.trim()) {
          return;
        }
        const response = await writeReply(postNum, comment.commentNum, message);
        setMessage("");
        toggleReply();
        console.log(response);
      } catch (error) {
        console.error(error);
      }
    };
    const handleDeleteComment = async () => {
      try {
        await deleteComment(comment.commentNum);
      } catch (error) {
        console.error(error);
      }
    };
    console.log(comment.author);
    return (
      <Card key={comment.commentNum} sx={{ boxShadow: "none" }}>
        <CardHeader
          title={`${comment.userName} | ${new Date(comment.modifiedAt).toLocaleString()}`}
          subheader={
            comment.author && (
              <>
                <IconButton size="small" sx={{ fontSize: "10px" }}>
                  수정
                </IconButton>
                <IconButton
                  size="small"
                  onClick={handleDeleteComment}
                  sx={{ fontSize: "10px" }}
                >
                  삭제
                </IconButton>
              </>
            )
          }
          titleTypographyProps={{
            fontSize: "12px",
          }}
          sx={{
            backgroundColor: "#f5f5f5",
          }}
        />
        <CardContent>
          <Typography variant="body2">{comment.commentContent}</Typography>
        </CardContent>
        <Box>
          {isReplyVisible && (
            <TextField
              label="답글"
              variant="outlined"
              fullWidth
              sx={{ marginBottom: 2 }}
              onChange={(e) => setMessage(e.target.value)}
              onFocus={handleFocus}
            />
          )}
          {isFocus && (
            <>
              <Button size="small" onClick={handleWriteReply}>
                입력
              </Button>
              <Button size="small" onClick={handleCancel}>
                취소
              </Button>
            </>
          )}
        </Box>

        <CardActions>
          {comment.children.length > 0 && (
            <Button size="small" onClick={toggleReply}>
              답글 {comment.children.length}개
            </Button>
          )}
          {comment.children.length <= 0 && (
            <Button size="small" onClick={toggleReply}>
              답글 달기
            </Button>
          )}
        </CardActions>

        {isReplyVisible &&
          comment.children.map((reply: Comment) => (
            <ReplyCard key={reply.commentNum} comment={reply} />
          ))}
        <Divider sx={{ margin: 1 }} />
      </Card>
    );
  },
);

export default CommentDetailCard;
