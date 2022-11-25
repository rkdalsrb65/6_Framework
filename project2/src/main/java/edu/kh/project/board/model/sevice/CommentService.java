package edu.kh.project.board.model.sevice;

import java.util.List;

import edu.kh.project.board.model.vo.Comment;

public interface CommentService {

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return rList
	 */
	List<Comment> selectCommentList(int boardNo);

	/** 댓글 등록
	 * @param comment
	 * @return result
	 */
	int insertComment(Comment comment);

}
