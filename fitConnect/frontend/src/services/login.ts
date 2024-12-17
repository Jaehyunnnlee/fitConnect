import AuthApi from "../config/authConfig/axiosInstance";

export const login = async (userId: string, userPwd: string) => {
  const data = { userId, userPwd };
  try {
    const response = await AuthApi.post(`/rest-api/users/login`, data);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
