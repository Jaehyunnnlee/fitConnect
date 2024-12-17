import { Card, CardContent, IconButton, Typography } from "@mui/material";
import { Comment } from "./CommentCard";

interface ReplyCardProps {
  comment: Comment;
}

export const ReplyCard = ({ comment }: ReplyCardProps) => {
  return (
    <Card sx={{ marginLeft: 2, marginTop: 1, boxShadow: "none" }}>
      <CardContent>
        <Typography sx={{ fontSize: "12px" }}>
          {comment.userName} | {new Date(comment.modifiedAt).toLocaleString()}
        </Typography>
        {comment.author && (
          <>
            <IconButton size="small" sx={{ fontSize: "10px" }}>
              수정
            </IconButton>
            <IconButton size="small" sx={{ fontSize: "10px" }}>
              삭제
            </IconButton>
          </>
        )}
        <Typography variant="body2" sx={{ marginTop: 1 }}>
          {comment.commentContent}
        </Typography>
      </CardContent>
    </Card>
  );
};
