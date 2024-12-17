import { Card, CardContent, CardMedia, Typography } from "@mui/material";

export const LogoCard = () => {
  return (
    <Card
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100%",
        width: "100%",
        flexDirection: "column",
        boxShadow: "none",
        border: "1px solid",
      }}
    >
      <CardContent>
        <Typography
          variant="body2"
          color="text.secondary"
          sx={{ fontSize: "20px" }}
        >
          fitConnect에 오신걸 환영합니다.
        </Typography>
      </CardContent>
      <CardMedia
        component="img"
        image="/home.png" // 로고 이미지 경로
        alt="Logo"
        sx={{ width: "60%", height: "70%" }}
      />
      <Typography
        variant="body2"
        color="text.secondary"
        sx={{ fontSize: "12px" }}
      >
        fitness&connect
      </Typography>
    </Card>
  );
};
