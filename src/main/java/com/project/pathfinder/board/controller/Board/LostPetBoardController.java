package com.project.pathfinder.board.controller.Board;

import com.project.pathfinder.board.entity.Board.LostPetBoardEntity;
import com.project.pathfinder.board.entity.Board.LostPropertyBoardEntity;
import com.project.pathfinder.board.service.Board.LostPetBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boards/lost-pet-board")
public class LostPetBoardController {

    @Autowired
    private LostPetBoardService lostPetBoardService;

    @GetMapping
    public ResponseEntity<?> getAllLostPetBoards() {
        try {
            List<LostPetBoardEntity> boards = lostPetBoardService.getLostPetBoards();
            return new ResponseEntity<>(boards, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("반려동물 게시판 조회 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createLostPetBoard(@RequestBody LostPetBoardEntity board) {
        try {
            lostPetBoardService.saveLostPetBoard(board);
            return new ResponseEntity<>("반려동물 게시글 작성 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 작성 중 오류 발생", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLostPetBoardById(@PathVariable Long id) {
        try {
            Optional<LostPetBoardEntity> board = lostPetBoardService.getLostPetBoardById(id);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("해당 게시글을 찾을 수 없습니다", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLostPetBoard(@PathVariable Long id, @RequestBody LostPetBoardEntity board) {
        try {
            lostPetBoardService.saveLostPetBoard(board);
            return new ResponseEntity<>("게시글 수정 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 수정 중 오류 발생", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLostPetBoard(@PathVariable Long id) {
        try {
            lostPetBoardService.deleteLostPetBoard(id);
            return new ResponseEntity<>("게시글 삭제 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 삭제 중 오류 발생", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<LostPetBoardEntity>> searchBoards(
            @RequestParam(required = false) String classifiName,
            @RequestParam(required = false) String findArea,
            @RequestParam(required = false) String findPlace,
            @RequestParam(required = false) LocalDate lostDate,
            @RequestParam(required = false) String petName) {
        try {
            List<LostPetBoardEntity> boards = lostPetBoardService.searchBoards(classifiName, findArea, findPlace, lostDate, petName);
            return new ResponseEntity<>(boards, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}