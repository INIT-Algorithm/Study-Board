package com.example.study.Service;

import com.example.study.Dto.BoardDto;
import com.example.study.Entity.Board;
import com.example.study.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 모든 게시글 조회
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    // 특정 ID의 게시글 조회
    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    // 새로운 게시글 생성
    public Board createBoard(BoardDto boardDto) {
        // BoardDto로부터 새로운 Board Entity 생성
        Board newBoard = new Board();
        newBoard.setTitle(boardDto.getTitle());
        newBoard.setContent(boardDto.getContent());

        // 생성된 Board Entity를 저장하고 반환
        return boardRepository.save(newBoard);
    }

    // 게시글 업데이트
    public Board updateBoard(Long id, BoardDto boardDto) {
        // 주어진 ID에 해당하는 게시글 찾기
        Optional<Board> optionalBoard = boardRepository.findById(id);

        // 게시글이 존재하면 업데이트 수행
        if (optionalBoard.isPresent()) {
            Board existingBoard = optionalBoard.get();
            existingBoard.setTitle(boardDto.getTitle());
            existingBoard.setContent(boardDto.getContent());

            // 업데이트된 게시글 저장하고 반환
            return boardRepository.save(existingBoard);
        }

        // 게시글이 존재하지 않으면 null 또는 예외 처리
        return null;
    }

    // 게시글 삭제
    public void deleteBoard(Long id) {
        // 주어진 ID에 해당하는 게시글 삭제
        boardRepository.deleteById(id);
    }
}