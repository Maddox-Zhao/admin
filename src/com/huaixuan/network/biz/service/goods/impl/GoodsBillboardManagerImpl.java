package com.huaixuan.network.biz.service.goods.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsBillboardDao;
import com.huaixuan.network.biz.dao.goods.GoodsPointDao;
import com.huaixuan.network.biz.dao.shop.BoardDao;
import com.huaixuan.network.biz.domain.goods.GoodsBillboard;
import com.huaixuan.network.biz.domain.goods.GoodsPoint;
import com.huaixuan.network.biz.domain.shop.Board;
import com.huaixuan.network.biz.enums.EnumBoardName;
import com.huaixuan.network.biz.service.goods.GoodsBillboardManager;

/**
 * �����Զ����(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
@Service("goodsBillboardManager")
public class GoodsBillboardManagerImpl implements GoodsBillboardManager {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	public GoodsBillboardDao goodsBillboardDao;
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private GoodsPointDao goodsPointDao;

	@Override
	public void addGoodsBillboard(GoodsBillboard goodsBillboardDao) {
		log.info("GoodsBillboardManagerImpl.addGoodsBillboard method");
		try {
			this.goodsBillboardDao.addGoodsBillboard(goodsBillboardDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void editGoodsBillboard(GoodsBillboard goodsBillboard) {
		log.info("GoodsBillboardManagerImpl.editGoodsBillboard method");
		try {
			this.goodsBillboardDao.editGoodsBillboard(goodsBillboard);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void removeGoodsBillboard(Long goodsBillboardId) {
		log.info("GoodsBillboardManagerImpl.removeGoodsBillboard method");
		try {
			this.goodsBillboardDao.removeGoodsBillboard(goodsBillboardId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public GoodsBillboard getGoodsBillboard(Long goodsBillboardId) {
		log.info("GoodsBillboardManagerImpl.getGoodsBillboard method");
		try {
			return this.goodsBillboardDao.getGoodsBillboard(goodsBillboardId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<GoodsBillboard> getGoodsBillboards() {
		log.info("GoodsBillboardManagerImpl.getGoodsBillboards method");
		try {
			return this.goodsBillboardDao.getGoodsBillboards();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public void buildHotSalePoints() {
		Board board = boardDao
				.getBoardByName(EnumBoardName.HOT_SALE.getValue());
		if (board == null) {
			throw new IllegalStateException("board with name:"
					+ EnumBoardName.HOT_SALE.getValue() + " not find.");
		}
		int period = board.getPeriod();// 天为单位
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, (0 - period));// 得到period天前的日�
		String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date
				.getTime());
		List<GoodsPoint> hotSalePoints = goodsPointDao
				.getHotSalePoints(dateString);
		for (GoodsPoint gp : hotSalePoints) {
			if (gp.getHotSalePoint() != null) {
				if (!goodsBillboardDao.updateBilllboard(gp,
						(long) gp.getHotSalePoint(), 1)) {
					goodsBillboardDao.createBillboard(gp,
							(long) gp.getHotSalePoint(), 1);
				}
			}
		}
	}

	@Override
	public void buildHighPopularPoints() {
		Board board = boardDao.getBoardByName(EnumBoardName.HIGH_POPULAR
				.getValue());
		if (board == null) {
			throw new IllegalStateException("board with name:"
					+ EnumBoardName.HOT_SALE.getValue() + " not find.");
		}
		int period = board.getPeriod();// 天为单位
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, (0 - period));// 得到period天前的日�
		String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date
				.getTime());
		List<GoodsPoint> hotSalePoints = goodsPointDao
				.getHighPopularPoint(dateString);
		for (GoodsPoint gp : hotSalePoints) {
			if (gp.getHighPopularPoint() != null) {
				if (!goodsBillboardDao.updateBilllboard(gp,
						(long) gp.getHighPopularPoint(), 2)) {
					goodsBillboardDao.createBillboard(gp,
							(long) gp.getHighPopularPoint(), 2);
				}
			}
		}
	}
}
