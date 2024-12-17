import { Grid } from "@mui/material";
import { LogoCard } from "../components/cards/LogoCard";
import LoginCard from "../components/cards/LoginCard";
import { useAuth } from "../context/AuthContext";
import SimpleMyPageCard from "../components/cards/SimpleMyPageCard";
import BoardCard from "../components/cards/BoardCard";

const MainPage = () => {
  const { isLogin } = useAuth();
  return (
    <Grid container sx={{ height: "100%", flexDirection: "column" }}>
      <Grid container sx={{ height: "100%" }}>
        {isLogin ? (
          <>
            <Grid
              item
              xs={12}
              md={6}
              sx={{
                padding: 2, // 내부 여백
              }}
            >
              <LogoCard />
            </Grid>
            <Grid
              item
              xs={12}
              md={6}
              sx={{
                padding: 2, // 내부 여백
              }}
            >
              <SimpleMyPageCard />
            </Grid>
          </>
        ) : (
          <>
            <Grid
              item
              xs={12}
              md={6}
              sx={{
                padding: 2, // 내부 여백
              }}
            >
              <LogoCard />
            </Grid>
            <Grid
              item
              xs={12}
              md={6}
              sx={{
                padding: 2, // 내부 여백
              }}
            >
              <LoginCard />
            </Grid>
          </>
        )}
      </Grid>
      <Grid item>
        <BoardCard />
      </Grid>
    </Grid>
  );
};

export default MainPage;
