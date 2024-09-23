package com.project.pathfinder.board.controller;

import com.project.pathfinder.board.entity.LostPropertyBoardEntity;
import com.project.pathfinder.board.entity.WantedBoardEntity;
import com.project.pathfinder.board.service.WantedBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boards/wanted-board")
public class WantedBoardController {


    @Autowired
    private WantedBoardService wantedBoardService;

    // 모든 게시물 가져오기
    @GetMapping
    public ResponseEntity<?> getAllWantedBoards() {
        try {
            List<WantedBoardEntity> boards = wantedBoardService.getWantedBoards();
            return new ResponseEntity<>(boards, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("분실물 게시판 조회 실패", HttpStatus.BAD_REQUEST);
        }
    }

    // 특정 게시물 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getWantedBoardById(@PathVariable Long id) {
        try {
            Optional<WantedBoardEntity> board = wantedBoardService.getLostPetBoardById(id);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("해당 게시글을 찾을 수 없습니다", HttpStatus.BAD_REQUEST);
        }
    }

    // 게시물 작성
    @PostMapping
    public ResponseEntity<?> createWantedBoard(@RequestBody WantedBoardEntity wantedBoardEntity) {
        try {
            wantedBoardService.saveWantedBoard(wantedBoardEntity);
            return new ResponseEntity<>("분실물 게시글 작성 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 작성 중 오류 발생", HttpStatus.BAD_REQUEST);
        }
    }

    // 게시물 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWantedBoard(@PathVariable Long id, @RequestBody WantedBoardEntity board) {
        try {
            wantedBoardService.saveWantedBoard(board);
            return new ResponseEntity<>("게시글 수정 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 수정 중 오류 발생", HttpStatus.BAD_REQUEST);
        }
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWantedBoard(@PathVariable Long id) {
        try {
            wantedBoardService.deleteWantedBoard(id);
            return new ResponseEntity<>("게시글 삭제 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 삭제 중 오류 발생", HttpStatus.BAD_REQUEST);
        }
    }
}
