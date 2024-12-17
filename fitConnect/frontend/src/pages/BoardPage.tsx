import { useParams } from "react-router-dom";

import BoardDetailCard from "../components/cards/BoardDetailCard";
import CommentCard from "../components/cards/CommentCard";

export const BoardPage = () => {
  const { postNum } = useParams<{ postNum: string }>();

  return (
    <div>
      {postNum && (
        <>
          <BoardDetailCard postNum={postNum} />
          <CommentCard postNum={postNum} />
        </>
      )}
    </div>
  );
};

export default BoardPage;
