package com.example.study.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.study.Entity.Board;
import com.example.study.Entity.Likes;
import com.example.study.Entity.User;
import com.example.study.Repository.LikesRepository;
import com.example.study.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageService {
	private final LikesRepository likeRepository;
	private final UserRepository userRepository;

	//좋아요 한 게시판 글 목록 보기
	public List<Board> getLikedBoards(Long userId) {
		User user= userRepository.findById(userId).orElseThrow(NullPointerException::new);
		List<Likes> likedList=likeRepository.findByUser(user);
		return likedList.stream().map(Likes::getBoard).collect(Collectors.toList());
	}

	//닉네임 변경
	public boolean updateNickname(Long userId, String newNickname) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.setUsername(newNickname);
			userRepository.save(user);
			return true;
		} else {
			return false;
		}
	}


}
