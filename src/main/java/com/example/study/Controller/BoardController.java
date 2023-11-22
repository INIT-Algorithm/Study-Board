package com.example.study.Controller;

import com.example.study.Dto.BoardDto;
import com.example.study.Entity.Board;
import com.example.study.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 모든 게시글 조회
    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    // 특정 ID의 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long id) {
        Optional<Board> board = boardService.getBoardById(id);
        return board.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 새로운 게시글 생성
    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody BoardDto boardDto) {
        Board newBoard = boardService.createBoard(boardDto);
        return ResponseEntity.ok(newBoard);
    }

    // 특정 ID의 게시글 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long id, @RequestBody BoardDto boardDto) {
        Board updatedBoard = boardService.updateBoard(id, boardDto);
        return updatedBoard != null ? ResponseEntity.ok(updatedBoard) : ResponseEntity.notFound().build();
    }

    // 특정 ID의 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}