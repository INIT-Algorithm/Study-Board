package com.example.study.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.study.Dto.LikesDto;
import com.example.study.Service.LikesService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikesController {

	private final LikesService likeService;

	//좋아요 등록
	@PostMapping("/")
	public ResponseEntity<String> insert(@RequestBody LikesDto likeDto)  {
		likeService.createLike(likeDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("좋아요 등록 성공");
	}

	//좋아요 취소
	@DeleteMapping("/")
	public ResponseEntity<Void> delete(@RequestBody LikesDto likeDto)  {
		likeService.deleteLike(likeDto);
		return ResponseEntity.noContent().build();
	}

}
