import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

const LogoutButton = () => {
  const navigate = useNavigate();
  const { setIsLogin } = useAuth();
  const handleLogout = () => {
    try {
      setIsLogin(false);
      localStorage.removeItem("accessToken");
      navigate("/");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Button
      variant="contained"
      fullWidth
      onClick={handleLogout}
      sx={{ marginTop: 2 }}
    >
      로그아웃
    </Button>
  );
};

export default LogoutButton;
