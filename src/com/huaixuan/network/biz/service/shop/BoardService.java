package com.huaixuan.network.biz.service.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.Board;

public interface BoardService {
	public void addBoard(Board board);

	public void editBoard(Board board);

	public void removeBoard(Long boardId);

	public Board getBoard(Long boardId);

	public List<Board> getBoards();

	public Board getBoardByName(String boardName);
}
