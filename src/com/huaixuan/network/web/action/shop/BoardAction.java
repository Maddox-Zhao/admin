package com.huaixuan.network.web.action.shop;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huaixuan.network.biz.domain.shop.Board;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumBoardName;
import com.huaixuan.network.biz.service.goods.GoodsBillboardManager;
import com.huaixuan.network.biz.service.shop.BoardService;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
public class BoardAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	// private String pageFlag;
	// private Board board;
	@Autowired
	private BoardService boardService;
	@Autowired
	private GoodsBillboardManager goodsBillboardManager;

	// private int period;

	/**
	 * @author chenhang 2011-3-16
	 * @description 榜单页面初始化
	 */
	@AdminAccess({EnumAdminPermission.A_BARDL_MODIFY_USER})
	@RequestMapping(value = "/shop/bardl")
	public String getBoardList(Model model, HttpServletRequest request) {
		String pageFlag = request.getParameter("pageFlag");
		String boardName = EnumBoardName.toMap().get(pageFlag);
		Board board = boardService.getBoardByName(boardName);
		model.addAttribute("board", board);
		model.addAttribute("pageFlag", pageFlag);
		return "/shop/boardlist";
	}

	@RequestMapping(value = "/shop/sbard")
	public String updateBoard(@ModelAttribute("board") Board board,
			Model model, HttpServletRequest request) {
		String pageFlag = request.getParameter("pageFlag");
		String temp = request.getParameter("board.period");
		String idstr = request.getParameter("board.id");
		String amount = request.getParameter("board.amount");
		String periodOther = request.getParameter("period");
		if (StringUtil.isNotBlank(amount)) {
			board.setAmount(Integer.valueOf(amount));
		}
		if (StringUtil.isNotBlank(idstr)) {
			board.setId(Long.valueOf(idstr));
		}
		int period = Integer.valueOf(temp.trim());
		board.setPeriod(period);
		if (null != board) {
			try {
				if (board.getPeriod() == -1) {
					board.setPeriod(Integer.valueOf(periodOther));
				}
				board.setTitle(board.getTitle());
				boardService.editBoard(board);
				goodsBillboardManager.buildHotSalePoints();
				goodsBillboardManager.buildHighPopularPoints();
			} catch (Exception e) {
				return "/shop/shop_error";
			}
		}
		return "redirect:/shop/bardl.html?pageFlag=" + pageFlag;
	}
}
