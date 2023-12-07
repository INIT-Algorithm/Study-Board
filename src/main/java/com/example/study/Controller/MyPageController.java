package com.example.study.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.study.Entity.Board;
import com.example.study.Entity.Comment;
import com.example.study.Service.MyPageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
	private final MyPageService myPageService;

	//내가 한 좋아요 게시글 목록
	@GetMapping("/likes/{userId}")
	public ResponseEntity<List<Board>> getLikedBoards(@PathVariable Long userId) {
		List<Board> myLikes = myPageService.getLikedBoards(userId);
		return new ResponseEntity<>(myLikes, HttpStatus.OK);
	}

	//닉네임 변경 (Post, put, patch 모두 가능)
	@PostMapping("/update-nickname")
	public ResponseEntity<String> updateNickname(@RequestParam Long userId,
		@RequestParam String newNickname) {
		boolean updated = myPageService.updateNickname(userId, newNickname);

		if (updated) {
			return new ResponseEntity<>("닉네임 변경 성공", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
	}





}
