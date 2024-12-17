import { createContext, ReactNode, useContext, useMemo, useState } from "react";

interface AuthProps {
  children: ReactNode;
}

interface AuthContextType {
  isLogin: boolean;
  // eslint-disable-next-line no-unused-vars
  setIsLogin: (isLogin: boolean) => void;
}

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined,
);

export const AuthProvider = ({ children }: AuthProps) => {
  const [isLogin, setIsLogin] = useState<boolean>(
    !!localStorage.getItem("accessToken"),
  );

  const value = useMemo(() => ({ isLogin, setIsLogin }), [isLogin, setIsLogin]);
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth는 AuthProvider 내에서 사용해야 합니다");
  }
  return context;
};
