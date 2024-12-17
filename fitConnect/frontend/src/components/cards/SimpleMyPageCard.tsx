import {
  Box,
  Card,
  CardContent,
  CardMedia,
  CircularProgress,
  Typography,
} from "@mui/material";
import { useEffect, useState } from "react";
import { simpleMyPage } from "../../services/simpleMyPage";
import LogoutButton from "../buttons/LogoutButton";

const SimpleMyPageCard = () => {
  const [userInfo, setUserInfo] = useState<{
    userId: string;
    userName: string;
    createdAt: Date;
  } | null>(null);
  const myPageInfo = async () => {
    try {
      const result = await simpleMyPage();
      setUserInfo(result.data);
    } catch (error) {
      console.error(error);
    }
  };
  useEffect(() => {
    myPageInfo();
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
        border: "1px solid",
      }}
    >
      <CardMedia
        component="img"
        image="/user.png"
        alt="Logo"
        sx={{
          width: "30%",
          height: "50%",
        }}
      />
      <CardContent>
        <Typography variant="h5" component="div" gutterBottom align="center">
          내 정보
        </Typography>

        {userInfo ? (
          <>
            <Typography variant="body1" gutterBottom>
              <strong>아이디:</strong> {userInfo.userId}
            </Typography>
            <Typography variant="body1" gutterBottom>
              <strong>닉네임:</strong> {userInfo.userName}
            </Typography>
            <Typography variant="body1" gutterBottom>
              <strong>가입일:</strong>{" "}
              {new Date(userInfo.createdAt).toLocaleDateString()}
            </Typography>
          </>
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
      </CardContent>
      <LogoutButton />
    </Card>
  );
};

export default SimpleMyPageCard;
