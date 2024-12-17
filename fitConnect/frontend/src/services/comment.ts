import AuthApi from "../config/authConfig/axiosInstance";

export const getComment = async (postNum: string) => {
  try {
    const response = await AuthApi.get(`/rest-api/posts/${postNum}/comments`);
    console.log(response);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const writeComment = async (postNum: string, message: string) => {
  const data = { commentContent: message };
  try {
    const response = await AuthApi.post(
      `rest-api/posts/${postNum}/comments`,
      data,
    );
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const writeReply = async (
  postNum: string,
  commentNum: number,
  message: string,
) => {
  const data = { commentContent: message };
  try {
    const response = await AuthApi.post(
      `rest-api/posts/${postNum}/comments/${commentNum}/replies`,
      data,
    );
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const updateComment = async (commentNum: number, message: string) => {
  const data = { commentContent: message };
  try {
    const response = await AuthApi.put(`rest-api/comments/${commentNum}`, data);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const deleteComment = async (commentNum: number) => {
  try {
    const response = await AuthApi.delete(`rest-api/comments/${commentNum}`);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
