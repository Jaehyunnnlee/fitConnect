import React, { useEffect, useState } from "react";
import {
  Box,
  Button,
  Card,
  CardContent,
  TextField,
  Typography,
} from "@mui/material";
import { getComment, writeComment } from "../../services/comment";
import CommentDetailCard from "./CommentDetailCard";

export interface Comment {
  commentNum: number;
  commentContent: number;
  userName: string;
  modifiedAt: string;
  children: Comment[];
  author: boolean;
}

interface CommentCardProps {
  postNum: string;
}
const CommentCard = ({ postNum }: CommentCardProps) => {
  const [comments, setComments] = useState<Comment[]>([]);
  const [message, setMessage] = useState("");
  const [isFocus, setIsFocus] = useState(false);

  const handleFocus = () => {
    setIsFocus(true);
  };
  const handleCancel = () => {
    setIsFocus(false);
    setMessage("");
  };

  const getCommentList = async () => {
    if (postNum) {
      try {
        const response = await getComment(postNum);
        setComments(response.data);
      } catch (error) {
        console.error(error);
      }
    }
  };
  const handleWriteComment = async () => {
    try {
      if (!message.trim()) {
        return;
      }
      await writeComment(postNum, message);
      getCommentList();
      setMessage("");
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    getCommentList();
  }, []);
  return (
    <Card
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100%",
        flexDirection: "column",
        boxShadow: "none",
        // border: "1px solid",
      }}
    >
      <CardContent sx={{ width: "98%" }}>
        <Box sx={{ marginBottom: 1 }}>
          <Typography variant="h6" component="div">
            댓글 {comments.length}개
          </Typography>
          <TextField
            label="댓글"
            variant="outlined"
            fullWidth
            sx={{ marginBottom: 1, marginTop: 1 }}
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            onFocus={handleFocus}
          />
          {isFocus && (
            <>
              <Button size="small" onClick={handleWriteComment}>
                입력
              </Button>
              <Button size="small" onClick={handleCancel}>
                취소
              </Button>
            </>
          )}
        </Box>
        <Box>
          {comments.map((comment) => (
            <CommentDetailCard
              key={comment.commentNum}
              comment={comment}
              postNum={postNum}
            />
          ))}
        </Box>
      </CardContent>
    </Card>
  );
};

export default CommentCard;
