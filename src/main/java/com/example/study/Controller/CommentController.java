package com.example.study.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.study.Dto.CommentDto;
import com.example.study.Dto.CommentListDto;
import com.example.study.Dto.CommentUpdateDto;
import com.example.study.Service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
	private final CommentService commentService;

	//댓글 등록
	@PostMapping("/")
	public ResponseEntity<String> insert(@RequestBody CommentDto commentDto)  {
		commentService.postComment(commentDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("댓글 등록 성공");
	}

	//대댓글 등록
	@PostMapping("/reply/")
	public ResponseEntity<String> replyInsert(@RequestBody CommentDto commentDto)  {
		commentService.postReplyComment(commentDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("대댓글 등록 성공");
	}

	//게시판에 달린 댓글&대댓글 계층 목록 확인
	@GetMapping("/list/{board_id}")
	public ResponseEntity<List<CommentListDto>> getCommentHierarchy(@PathVariable("board_id") Long boardId) {
		List<CommentListDto> comments = commentService.getCommentHierarchy(boardId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	//댓글&대댓글 수정
	@PatchMapping("/update/{comment_id}")
	public ResponseEntity<String> updateComment(@PathVariable Long comment_id, @RequestBody CommentUpdateDto commentDto) {
		commentService.updateComment(comment_id, commentDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("댓글 수정 성공");
	}

	//댓글&대댓글 삭제
	@PatchMapping("/delete/{comment_id}")
	public ResponseEntity<String> deleteComment(@PathVariable Long comment_id) {
		commentService.deleteComment(comment_id);
		return ResponseEntity.noContent().build();
	}

}
