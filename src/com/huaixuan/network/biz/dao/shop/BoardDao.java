package com.huaixuan.network.biz.dao.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.Board;

public interface BoardDao {
	void addBoard(Board board) throws Exception;

	void editBoard(Board board) throws Exception;

	void removeBoard(Long boardId) throws Exception;

	Board getBoard(Long boardId) throws Exception;

	List<Board> getBoards() throws Exception;

	/**
	 * ���ݰ�����ȡ�øð���Ϣ
	 *
	 * @param boardName
	 * @return
	 */
	Board getBoardByName(String boardName);
}
