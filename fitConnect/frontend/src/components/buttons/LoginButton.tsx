import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import { login } from "../../services/login";

type LoginButtonProps = {
  email: string;
  password: string;
};
const LoginButton = ({ email, password }: LoginButtonProps) => {
  const navigate = useNavigate();
  const { setIsLogin } = useAuth();
  const handleLogin = async () => {
    try {
      const { data } = await login(email, password);
      const { accessToken } = data;
      setIsLogin(true);

      if (accessToken) {
        localStorage.setItem("accessToken", accessToken);
      }
      navigate("/");
    } catch (error) {
      console.error(error);
    }
  };
  return (
    <Button
      variant="contained"
      fullWidth
      onClick={handleLogin}
      sx={{ marginTop: 2 }}
    >
      로그인
    </Button>
  );
};

export default LoginButton;
