package com.example.study.Service;

import java.util.ArrayList;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.study.Dto.CommentDto;
import com.example.study.Dto.CommentListDto;
import com.example.study.Dto.CommentUpdateDto;
import com.example.study.Entity.Board;
import com.example.study.Entity.Comment;
import com.example.study.Entity.User;
import com.example.study.Repository.BoardRepository;
import com.example.study.Repository.CommentRepository;
import com.example.study.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;

	// 댓글 등록
	public void postComment(CommentDto commentDto) {
		User user= userRepository.findById(commentDto.getUserId()).orElseThrow(NullPointerException::new);
		Board board= boardRepository.findById(commentDto.getBoardId()).orElseThrow(NullPointerException::new);

		Comment newComment= new Comment();
		newComment.setContent(commentDto.getContent());
		newComment.setUser(user);
		newComment.setBoard(board);
		newComment.setDepth(0L); //Long type
		newComment.setParent(0L);
		newComment.setIsDeleted(false);
		commentRepository.save(newComment);
	}

	//대댓글 등록
	public void postReplyComment(CommentDto commentDto) {
		User user= userRepository.findById(commentDto.getUserId()).orElseThrow(NullPointerException::new);
		Board board= boardRepository.findById(commentDto.getBoardId()).orElseThrow(NullPointerException::new);

		Comment newReplyComment= new Comment();
		newReplyComment.setContent(commentDto.getContent());
		newReplyComment.setUser(user);
		newReplyComment.setBoard(board);
		newReplyComment.setDepth(1L);	//대댓글이니 1로 설정
		newReplyComment.setParent(commentDto.getParent());

		commentRepository.save(newReplyComment);
	}

	//게시판 댓글&대댓글 목록
	public List<CommentListDto> getCommentHierarchy(Long boardId) {
		// 루트 댓글(부모 댓글, 즉 parent가 0인 댓글)을 가져오기
		List<Comment> rootComments = commentRepository.findRootComments(boardId);

		// 각 루트 댓글에 대해 하위 댓글들을 재귀적으로 가져와 계층 구조를 형성
		List<CommentListDto> rootCommentsDTO = new ArrayList<>();
		for (Comment rootComment : rootComments) {
			CommentListDto rootCommentDTO = convertToDTO(rootComment);
			rootCommentDTO.setChildren(getChildrenCommentsDTO(rootComment.getId()));
			rootCommentsDTO.add(rootCommentDTO);
		}

		return rootCommentsDTO;
	}

	private List<CommentListDto> getChildrenCommentsDTO(Long parentId) {
		// 특정 부모 댓글에 속한 자식 댓글을 가져오기
		List<Comment> children = commentRepository.findByParent(parentId);

		List<CommentListDto> childrenDTO = new ArrayList<>();
		for (Comment child : children) {
			CommentListDto childDTO = convertToDTO(child);
			childDTO.setChildren(getChildrenCommentsDTO(child.getId()));
			childrenDTO.add(childDTO);
		}
		return childrenDTO;
	}

	private CommentListDto convertToDTO(Comment comment) {
		CommentListDto commentDTO = new CommentListDto();
		commentDTO.setContent(comment.getContent());
		commentDTO.setDepth(comment.getDepth());
		commentDTO.setParent(comment.getParent());
		commentDTO.setIsDeleted(comment.getIsDeleted());
		commentDTO.setUserId(comment.getUser().getUserId());

		return commentDTO;
	}

	//댓글&대댓글 수정 -> 내용만 변경
	public void updateComment(Long comment_id, CommentUpdateDto commentDto) {

		// 댓글 ID에 해당하는 댓글 정보 찾기
		Optional<Comment> optionalComment = commentRepository.findById(comment_id);
		if (optionalComment.isPresent()) {
			Comment existingComment= optionalComment.get();
			existingComment.setContent(commentDto.getContent());

			commentRepository.save(existingComment);
		}
	}

	//댓글&대댓글 삭제
	public void deleteComment(Long comment_id) {
		Optional<Comment> optionalComment = commentRepository.findById(comment_id);
		Long childCount= commentRepository.countByParent(comment_id);

		if (optionalComment.isPresent()) {
			//자식 댓글이면 바로 삭제
			if (optionalComment.get().getDepth()==1) {
				commentRepository.deleteById(comment_id);
			}
			else //부모 댓글
				{
				if (childCount==0L){ //자식없는 부모댓글이면 바로 삭제
					commentRepository.deleteById(comment_id);
				}else{ //자식있는 부모댓글이면 -> 삭제여부 1로 변경
					Comment existingComment= optionalComment.get();
					existingComment.setIsDeleted(true);
					commentRepository.save(existingComment);
				}
			}

		}
	}


}
