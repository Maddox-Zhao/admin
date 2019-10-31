package com.huaixuan.network.biz.service.shop.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.BoardDao;
import com.huaixuan.network.biz.domain.shop.Board;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.service.shop.BoardService;

@Service("boardService")
public class BoardServiceImpl implements BoardService {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	public BoardDao boardDao;

	@Override
	public void addBoard(Board boardDao) {
		log.info("BoardManagerImpl.addBoard method");
		try {
			this.boardDao.addBoard(boardDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void editBoard(Board board) {
		log.info("BoardManagerImpl.editBoard method");
		try {
			this.boardDao.editBoard(board);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void removeBoard(Long boardId) {
		log.info("BoardManagerImpl.removeBoard method");
		try {
			this.boardDao.removeBoard(boardId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public Board getBoard(Long boardId) {
		log.info("BoardManagerImpl.getBoard method");
		try {
			return this.boardDao.getBoard(boardId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Board> getBoards() {
		log.info("BoardManagerImpl.getBoards method");
		try {
			return this.boardDao.getBoards();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 根据榜单名称取得该榜单信息
	 *
	 * @param boardName
	 * @return
	 * @see com.hundsun.bible.facade.goods.BoardManager#getBoardByName(java.lang.String)
	 */
	@Override
	public Board getBoardByName(String boardName) {
		log.info("BoardManagerImpl.getBoardByName method");
		try {
			return this.boardDao.getBoardByName(boardName);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
}
