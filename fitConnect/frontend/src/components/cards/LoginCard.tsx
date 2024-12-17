import React, { useState } from "react";
import {
  Card,
  CardContent,
  TextField,
  Typography,
  CardMedia,
} from "@mui/material";
import LoginButton from "../buttons/LoginButton";

const LoginCard = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

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
        image="/unknown.png"
        alt="Logo"
        sx={{
          width: "30%",
        }}
      />
      <CardContent>
        <Typography variant="h5" component="div" gutterBottom align="center">
          로그인
        </Typography>

        <TextField
          label="이메일"
          variant="outlined"
          fullWidth
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          sx={{ marginBottom: 2 }}
        />
        <TextField
          label="비밀번호"
          variant="outlined"
          type="password"
          fullWidth
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <LoginButton email={email} password={password} />
      </CardContent>
    </Card>
  );
};

export default LoginCard;
