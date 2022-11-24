package com.ssafy.pjt.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt.model.dto.Board;
import com.ssafy.pjt.model.dto.User;
import com.ssafy.pjt.model.service.BoardService;

@RestController
@RequestMapping("/api/board")
@CrossOrigin("*")
public class BoardRestController {

	private static final Logger logger = LoggerFactory.getLogger(BoardRestController.class);

	@Autowired
	BoardService boardService;

	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam Map<String, String> map) throws Exception {
		logger.debug("list parameter : {}", map);
		try {
			List<Board> listArticle = boardService.getListArticle(map);
			if(listArticle!=null && listArticle.size()>0) {
				return new ResponseEntity<List<Board>>(listArticle, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@GetMapping("/total")
	public ResponseEntity<?> total(@RequestParam Map<String, String> map) throws Exception {
		logger.debug("total parameter : {}", map);
		try {
			int total = boardService.getTotalArticle(map);
			
			logger.debug(String.valueOf(total));
			if(total>0) {
				return new ResponseEntity<Integer>(total, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping("/write")
	public ResponseEntity<?> write(@RequestBody Board board, HttpSession session) throws Exception {
		try {
			logger.debug("write board : {}", board);
			// fixture
			User user = new User("ssafy1", "1234", "김싸피", "서울시 강남구 역삼동", "010-1111-2222", 0);
//			User user = (User) session.getAttribute("loginUser");
			user.setId(board.getUserId());
			board.setUserId(user.getId());

			boardService.writeArticle(board);

			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/view/{boardId}")
	public ResponseEntity<?>detail(@PathVariable("boardId") String boardId) {
		logger.debug("view parameter : {}", boardId);
		try {
			boardService.updateHit(Integer.parseInt(boardId));
			Board board = boardService.getArticle(Integer.parseInt(boardId));
			logger.debug(board.toString());
			if(board!=null) {
				return new ResponseEntity<Board>(board, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{articleNo}")
	public ResponseEntity<?> delete(@PathVariable("articleNo") String articleNo) throws Exception {
		try {
			//logger.debug("delete articleNo : {}", articleNo);
			boardService.deleteArticle(Integer.parseInt(articleNo));

			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/modify/{articleNo}")
	public ResponseEntity<?> modify(@RequestBody Board article) throws Exception {
		try {
			logger.debug("modify board : {}", article);
			boardService.updateArticle(article);

			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
