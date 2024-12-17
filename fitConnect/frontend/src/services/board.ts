import AuthApi from "../config/authConfig/axiosInstance";

export const getPosts = async (page: number, size: number) => {
  try {
    const response = await AuthApi.get(`/rest-api/boards/posts`, {
      params: {
        page,
        size,
      },
    });
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const getBoardDetail = async (postNum: string) => {
  try {
    const response = await AuthApi.get(`/rest-api/boards/post/${postNum}`);
    console.log(response);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
