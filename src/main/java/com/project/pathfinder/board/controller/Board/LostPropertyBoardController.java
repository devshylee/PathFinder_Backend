package com.project.pathfinder.board.controller.Board;

import com.project.pathfinder.board.entity.Board.LostPropertyBoardEntity;
import com.project.pathfinder.board.service.Board.LostPropertyBoardService;
import com.project.pathfinder.member.entity.MemberEntity;
import com.project.pathfinder.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boards/lost-property-board")
public class LostPropertyBoardController {

    @Autowired
    private LostPropertyBoardService lostPropertyBoardService;

    @Autowired
    private MemberRepository memberRepository;

    // 모든 게시물 가져오기
    @GetMapping
    public ResponseEntity<?> getAllLostPropertyBoards() {
        try {
            List<LostPropertyBoardEntity> boards = lostPropertyBoardService.getLostPropertyBoards();
            return new ResponseEntity<>(boards, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("분실물 게시판 조회 실패 /n [ERROR] : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 특정 게시물 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getLostPropertyBoardById(@PathVariable Long id) {
        try {
            Optional<LostPropertyBoardEntity> board = lostPropertyBoardService.getLostPetBoardById(id);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("해당 게시글을 찾을 수 없습니다. /n [ERROR] : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 게시물 작성
    @PostMapping
    public ResponseEntity<?> createLostPropertyBoard(@RequestBody LostPropertyBoardEntity lostPropertyBoardEntity) {
        try {
            // memberNickName으로 MemberEntity 조회
            String memberNickName = lostPropertyBoardEntity.getMember().getMemberNickName();
            MemberEntity member = memberRepository.findByMemberNickName(memberNickName)
                    .orElseThrow(() -> new IllegalArgumentException("해당 닉네임을 가진 회원을 찾을 수 없습니다."));

            // 조회된 MemberEntity를 LostPropertyBoardEntity에 설정
            lostPropertyBoardEntity.setMember(member);

            // 분실물 게시글 저장
            lostPropertyBoardService.saveLostPropertyBoard(lostPropertyBoardEntity);

            return new ResponseEntity<>("분실물 게시글 작성 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 작성 중 오류 발생 /n [ERROR] : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 게시물 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLostPropertyBoard(@PathVariable Long id, @RequestBody LostPropertyBoardEntity board) {
        try {
            lostPropertyBoardService.saveLostPropertyBoard(board);
            return new ResponseEntity<>("게시글 수정 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 수정 중 오류 발생 /n [ERROR] : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLostPropertyBoard(@PathVariable Long id) {
        try {
            lostPropertyBoardService.deleteLostPropertyBoard(id);
            return new ResponseEntity<>("게시글 삭제 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 삭제 중 오류 발생 /n [ERROR] : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentLostPropertyBoards() {
        try{
            List<LostPropertyBoardEntity> lostPropertyBoards = lostPropertyBoardService.getRecentPosts();
            return new ResponseEntity<>(lostPropertyBoards, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("최근 게시물을 불러오는 중 에러 발생! /n [ERROR] : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<LostPropertyBoardEntity>> searchBoards(
            @RequestParam(required = false) String classifiName,
            @RequestParam(required = false) String lostArea,
            @RequestParam(required = false) String lostPlace,
            @RequestParam(required = false) LocalDate lostDate,
            @RequestParam(required = false) String lostPropertyName) {
        try {
            List<LostPropertyBoardEntity> boards = lostPropertyBoardService.searchBoards(classifiName, lostArea, lostPlace, lostDate, lostPropertyName);
            return new ResponseEntity<>(boards, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}