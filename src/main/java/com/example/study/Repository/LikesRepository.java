package com.example.study.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.study.Entity.Board;
import com.example.study.Entity.Likes;
import com.example.study.Entity.User;

@Transactional
public interface LikesRepository extends JpaRepository<Likes, Long> {
	//좋아요 여부 확인
	Optional<Object> findByUserAndBoard(User user, Board board);
	//좋아요 삭제
	void deleteByUserAndBoard(User user, Board board);
}
