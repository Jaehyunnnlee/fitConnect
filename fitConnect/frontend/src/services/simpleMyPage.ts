import AuthApi from "../config/authConfig/axiosInstance";

export const simpleMyPage = async () => {
  try {
    const response = await AuthApi.get(`/rest-api/users/myPage`);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
