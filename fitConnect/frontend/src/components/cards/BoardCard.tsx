import React, { useEffect, useState } from "react";
import {
  Button,
  Card,
  CardContent,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TablePagination,
  TableRow,
  Typography,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { getPosts } from "../../services/board";

interface Post {
  postNum: number;
  postTitle: number;
  userName: string;
  modifiedAt: string;
}

const BoardCard = () => {
  const [posts, setPosts] = useState<Post[]>([]);
  const [page, setPage] = useState<number>(0);
  const [size, setSize] = useState<number>(10);
  const [totalElements, setTotalElements] = useState<number>(1);
  const navigate = useNavigate();
  useEffect(() => {
    const getResponse = async () => {
      try {
        const result = await getPosts(page, size);
        setPosts(result.data.posts);
        setTotalElements(result.data.totalElements);
      } catch (error) {
        console.error("error: ", error);
      }
    };
    getResponse();
  }, [page, size]);

  const handlePage = (
    event: React.MouseEvent<HTMLButtonElement, MouseEvent> | null,
    newPage: number,
  ) => {
    setPage(newPage);
  };

  const handleSize = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSize(parseInt(event.target.value, 10));
  };

  const getBoardDetail = (postNum: number) => {
    navigate(`/post/${postNum}`);
  };
  return (
    <Card
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100%",
        flexDirection: "column",
        boxShadow: "none",
        border: "1px solid",
      }}
    >
      <CardContent sx={{ width: "100%" }}>
        <Typography variant="h5" component="div" gutterBottom align="center">
          게시글
        </Typography>
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="게시글 목록">
            <TableHead>
              <TableRow>
                <TableCell>번호</TableCell>
                <TableCell align="left">제목</TableCell>
                <TableCell align="left">작성자</TableCell>
                <TableCell align="left">최종 수정일</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {posts.map((post) => (
                <TableRow key={post.postNum}>
                  <TableCell>{post.postNum}</TableCell>
                  <TableCell align="left">
                    <Button
                      variant="text"
                      onClick={() => getBoardDetail(post.postNum)}
                      sx={{
                        color: "black",
                        textTransform: "none",
                        "&:hover": {
                          fontWeight: "bold",
                        },
                      }}
                    >
                      {post.postTitle}
                    </Button>
                  </TableCell>
                  <TableCell align="left">{post.userName}</TableCell>
                  <TableCell align="left">
                    {new Date(post.modifiedAt).toLocaleString()}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>

        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={totalElements} // 전체 데이터 수
          rowsPerPage={size}
          page={page}
          onPageChange={handlePage}
          onRowsPerPageChange={handleSize}
        />
      </CardContent>
    </Card>
  );
};

export default BoardCard;
