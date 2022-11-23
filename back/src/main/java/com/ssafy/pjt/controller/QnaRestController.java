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

import com.ssafy.pjt.model.dto.Qna;
import com.ssafy.pjt.model.dto.User;
import com.ssafy.pjt.model.service.QnaService;

@RestController
@RequestMapping("/api/qna")
@CrossOrigin("*")
public class QnaRestController {

	private static final Logger logger = LoggerFactory.getLogger(QnaRestController.class);

	@Autowired
	QnaService QnaService;

	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam Map<String, String> map) throws Exception {
		logger.debug("list parameter : {}", map);
		try {
			List<Qna> listQna = QnaService.getListQna(map);
			if(listQna!=null && listQna.size()>0) {
				return new ResponseEntity<List<Qna>>(listQna, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@GetMapping("/listAll")
	public ResponseEntity<?> list() throws Exception {
		//logger.debug("list parameter : {}", map);
		try {
			List<Qna> listQna = QnaService.getListAllQna();
			if(listQna!=null && listQna.size()>0) {
				return new ResponseEntity<List<Qna>>(listQna, HttpStatus.OK);
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
			int total = QnaService.getTotalQna();
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
	public ResponseEntity<?> write(@RequestBody Qna Qna, HttpSession session) throws Exception {
		try {
			logger.debug("write Qna : {}", Qna);
			// fixture
			
			User user = new User();
			user.setId(Qna.getUserId());
			Qna.setUserId(user.getId());
			logger.debug("write Qna : {}", Qna);
			QnaService.writeQna(Qna);

			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/view/{QnaId}")
	public ResponseEntity<?>detail(@PathVariable("QnaId") String QnaId) {
		logger.debug("view parameter : {}", QnaId);
		try {
			Qna Qna = QnaService.getQna(Integer.parseInt(QnaId));
			logger.debug(Qna.toString());
			if(Qna!=null) {
				return new ResponseEntity<Qna>(Qna, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{QnaNo}")
	public ResponseEntity<?> delete(@PathVariable("QnaNo") String QnaNo) throws Exception {
		try {
			logger.debug("delete QnaNo : {}", QnaNo);
			QnaService.deleteQna(Integer.parseInt(QnaNo));

			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/modify/{QnaNo}")
	public ResponseEntity<?> modify(@RequestBody Qna Qna) throws Exception {
		try {
			logger.debug("modify Qna : {}", Qna);
			QnaService.updateQna(Qna);

			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
