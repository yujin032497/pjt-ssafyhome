package com.ssafy.pjt;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.pjt.model.dto.Board;
import com.ssafy.pjt.model.dto.User;
import com.ssafy.pjt.model.mapper.BoardDao;
import com.ssafy.pjt.model.mapper.UserDao;

public class BoardDaoTest extends AbstractTest {

	private static final Logger logger = LoggerFactory.getLogger(BoardDaoTest.class);
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private UserDao userDao;
	
	private Board board;
	private User user1;
	
	private List<Map<String, String>> boards;
	
	@Before
	public void setUp() {
		this.user1 = new User("admin","admin","관리자","역삼역","010-1234-5678",1);
		this.board = new Board(1,"admin","관리자","제목","내용",0,null);
	}
	
	@Test // 게시글 추가
	public void addBoard() {
		boardDao.deleteAll();
		assertEquals(0, boardDao.getTotalArticle());
		
		System.out.println(user1.getId());
		Board board2 = new Board(0,user1.getId(), null, null, null, 0, null);
		int cnt = boardDao.writeArticle(board2);
		assertEquals(1, cnt);
		assertNotNull(board2.getArticleNo());
		
		Board boardget1 = boardDao.getArticle(board2.getArticleNo());
		assertEquals(boardget1.getUserId(), board2.getUserId());
	}
	
	@Test 
	public void getCount() {
		boardDao.getTotalArticle();
		assertEquals(1, boardDao.getTotalArticle());
	}
	/*
	 * @Test public void addAndUpdate() { boardDao.deleteAll(); assertEquals(0,
	 * boardDao.getTotalArticle());
	 * 
	 * assertEquals(1, boardDao.writeArticle(board));
	 * 
	 * board.setSubject("테스트수정중");
	 * 
	 * assertEquals(1, boardDao.updateArticle(board));
	 * 
	 * Board boardget1 = boardDao.getArticle(board.getArticleNo());
	 * assertEquals(boardget1.getSubject(), board.getSubject()); }
	 * 
	 * @Test public void addAndDelete() { boardDao.deleteAll(); assertEquals(0,
	 * boardDao.getTotalArticle());
	 * 
	 * Board board2 = new Board(0, user1.getId(), null, null, null, 0, null); int
	 * cnt = boardDao.writeArticle(board2); assertEquals(1, cnt);
	 * assertNotNull(board2.getArticleNo());
	 * 
	 * assertEquals(1, boardDao.deleteArticle(board2.getArticleNo()));
	 * 
	 * Board boardget1 = boardDao.getArticle(board2.getArticleNo());
	 * assertNull(boardget1); }
	 */
	
}
