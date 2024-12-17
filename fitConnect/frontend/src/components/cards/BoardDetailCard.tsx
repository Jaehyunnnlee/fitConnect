import { useEffect, useState } from "react";
import {
  Box,
  Card,
  CardContent,
  CardHeader,
  CircularProgress,
  Typography,
} from "@mui/material";
import { getBoardDetail } from "../../services/board";

interface Post {
  postNum: number;
  postTitle: string;
  postContent: string;
  userName: string;
  modifiedAt: Date;
}

interface BoardDetailCardProps {
  postNum: string;
}
const BoardDetailCard = ({ postNum }: BoardDetailCardProps) => {
  const [post, setPost] = useState<Post | null>(null);

  const getPostDetail = async () => {
    if (postNum) {
      try {
        const response = await getBoardDetail(postNum);
        setPost(response.data);
      } catch (error) {
        console.error("boardDetailCard 오류 발생: ", error);
      }
    }
  };
  useEffect(() => {
    getPostDetail();
  }, []);

  return (
    <div>
      {post ? (
        <Card>
          <CardHeader
            title={post.postTitle}
            subheader={`작성자: ${post.userName} | 수정일: ${new Date(post.modifiedAt).toLocaleString()}`}
            titleTypographyProps={{
              fontWeight: "bold",
            }}
          />
          <CardContent>
            <Typography variant="body1">{post.postContent}</Typography>
          </CardContent>
        </Card>
      ) : (
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <CircularProgress />
        </Box>
      )}
    </div>
  );
};

export default BoardDetailCard;
