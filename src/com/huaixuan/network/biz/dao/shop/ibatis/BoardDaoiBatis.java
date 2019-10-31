package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.BoardDao;
import com.huaixuan.network.biz.domain.shop.Board;

@Service("boardDao")
public class BoardDaoiBatis implements BoardDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addBoard(Board board) throws Exception {
		this.sqlMapClient.insert("addBoard", board);
	}

	@Override
	public void editBoard(Board board) throws Exception {
		this.sqlMapClient.update("editBoard", board);
	}

	@Override
	public void removeBoard(Long boardId) throws Exception {
		this.sqlMapClient.delete("removeBoard", boardId);
	}

	@Override
	public Board getBoard(Long boardId) throws Exception {
		return (Board) this.sqlMapClient.queryForObject("getBoard", boardId);
	}

	@Override
	public List<Board> getBoards() throws Exception {
		return this.sqlMapClient.queryForList("getBoards", null);
	}

	/**
	 * 根据榜单名称取得该榜单信息
	 *
	 * @param boardName
	 * @return
	 * @see com.hundsun.bible.dao.BoardDao#getBoardByName(java.lang.String)
	 */
	@Override
	public Board getBoardByName(String boardName) {
		return (Board) this.sqlMapClient.queryForObject("getBoardByName",
				boardName);
	}
}
