package com.huaixuan.network.biz.service.shop.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.dao.shop.ActivityDao;
import com.huaixuan.network.biz.dao.shop.ActivityGoodsDao;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.shop.Activity;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.huaixuan.network.biz.service.shop.ActivityService;
import com.huaixuan.network.common.util.DateUtil;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private UploadUtil uploadUtil;
	@Autowired
	private ActivityGoodsDao activityGoodsDao;

	@Override
	public long addActivity(Activity activity, List<MultipartFile> files) {
		// log.info("ActivityManagerImpl.addActivity method");

		try {
			String goodsPicPath = "activity" + Constants.FILE_SEP
					+ DateUtil.getDateTime("yyyyMM", new Date());

			if (files != null && files.size() > 0) {
                
//				String fileName = uploadUtil.newUpload(files.get(0),
//						goodsPicPath);
//				activity.setImg(goodsPicPath + Constants.FILE_SEP + fileName);

				// String[] fileNamePrefix=fileName.split("\\.");
				// uploadUtil.uploadWithSpecificName(images[1],
				// imagesFileName[1],fileNamePrefix[0]+"_small", goodsPicPath);
//				String fileNamePrefix = uploadUtil.newUpload(files.get(1),
//						goodsPicPath);
//				activity.setImg_small(goodsPicPath + Constants.FILE_SEP
//						+ fileNamePrefix);

				// String[] fileNamePrefix2=fileName.split("\\.");
				// uploadUtil.uploadWithSpecificName(images[2],
				// imagesFileName[2],fileNamePrefix2[0]+"_homeSmall",
				// goodsPicPath);
				if (files.get(0) != null && files.get(0).getSize() > 0) {
					String fileNamePrefix2 = uploadUtil.newUpload(files.get(0),
							goodsPicPath);
					activity.setImg(goodsPicPath + Constants.FILE_SEP + fileNamePrefix2);
					activity.setImg_small(goodsPicPath + Constants.FILE_SEP
							+ fileNamePrefix2);
					activity.setImg_home_small(goodsPicPath
							+ Constants.FILE_SEP + fileNamePrefix2);
				}

			}
			return this.activityDao.addActivity(activity);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public void editActivity(Activity activity, List<MultipartFile> files) {
		log.info("ActivityManagerImpl.editActivity method");
		try {
			/*
			 * String goodsPicPath = "activity" + Constants.FILE_SEP +
			 * DateUtil.getDateTime("yyyyMM", new Date()); if(images!=null &&
			 * images.length>0){ for (int i = 0; i < images.length; i++) {
			 * String fileName = uploadUtil.upload(images[i], imagesFileName[i],
			 * goodsPicPath); if (i == 0) { activity.setImg(goodsPicPath +
			 * Constants.FILE_SEP + fileName); } } }
			 */

			String goodsPicPath = "activity" + Constants.FILE_SEP
					+ DateUtil.getDateTime("yyyyMM", new Date());
			String activityImag = activity.getImg();
			int index = activityImag.lastIndexOf("/");
			String imgPath = activityImag.substring(0, index);
			String imgName = activityImag.substring(index + 1);
			String[] imagNameSplit = imgName.split("\\.");
			String imagNameLarge = imagNameSplit[0];
			// String iamgNameSmall=imagNameSplit[0]+"_small";
			// String iamgNameHomeSmall = imagNameSplit[0]+"_homeSmall";

			String activitySmallImag = activity.getImg_small();
			int index2 = activitySmallImag.lastIndexOf("/");
			String imgPath2 = activitySmallImag.substring(0, index2);
			String imgName2 = activitySmallImag.substring(index2 + 1);
			String[] imagNameSplit2 = imgName2.split("\\.");
			String imagNameSamll = imagNameSplit2[0];

			if (files.get(0) != null && files.get(0).getSize() > 0) {
				String fileNamePrefix2 = uploadUtil.newUpload(files.get(0),
						goodsPicPath);
				
				activity.setImg(goodsPicPath + Constants.FILE_SEP + fileNamePrefix2);
				activity.setImg_small(goodsPicPath + Constants.FILE_SEP
						+ fileNamePrefix2);
				activity.setImg_home_small(goodsPicPath
						+ Constants.FILE_SEP + fileNamePrefix2);
			}

//			if (files.get(0) != null && files.get(0).getSize() > 0) {
//				uploadUtil.uploadWithSpecificName(files.get(0), imagNameLarge,
//						imgPath);
//			}
//			if (files.get(1) != null && files.get(1).getSize() > 0) {
//				uploadUtil.uploadWithSpecificName(files.get(1), imagNameSamll,
//						imgPath);
//			}

			this.activityDao.editActivity(activity);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void editActivity(Activity activity) {
		log.info("ActivityManagerImpl.editActivity method");
		try {
			this.activityDao.editActivity(activity);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void removeActivity(Long activityId) {
		log.info("ActivityManagerImpl.removeActivity method");
		try {
			this.activityDao.removeActivity(activityId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public Activity getActivity(Long activityId) {
		log.info("ActivityManagerImpl.getActivity method");
		try {
			return this.activityDao.getActivity(activityId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Activity> getActivitys() {
		log.info("ActivityManagerImpl.getActivitys method");
		try {
			return this.activityDao.getActivitys();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getActivitysPage(int currentPage, int pageSize) {
		log.info("ActivityManagerImpl.getActivitysPage method");
		try {
			QueryPage queryPage = new QueryPage("par");
			Map pramas = queryPage.getParameters();

			Integer count = this.activityDao.getActivitysCount();

			if (count > 0) {

				/* ��ǰҳ */
				queryPage.setCurrentPage(currentPage);
				/* ÿҳ���� */
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);

				pramas.put("startRow",
						queryPage.getPageFristItem());
				pramas.put("endRow",
						queryPage.getPageLastItem());

				/* ��ҳ��ѯ����Ա��¼ */
				List<Activity> activityList = this.activityDao
						.getActivitysPage(pramas);
				if (activityList != null && activityList.size() > 0) {
					queryPage.setItems(activityList);
				}
			}
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	@Override
	public List<Activity> allActivityDisplay() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("curTime", new Date());
			// params.put("curTime",
			// DateUtil.convertDateToString("yyyy-MM-dd HH", new Date()));
			return this.activityDao.allActivityDisplay(params);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	@Override
	public List<Activity> allActivityDisplay2() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("curTime", new Date());
			// params.put("curTime",
			// DateUtil.convertDateToString("yyyy-MM-dd HH", new Date()));
			return this.activityDao.allActivityDisplay2(params);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}

	}

	/*date:2018/5/3
	 * author:Hu hongfei
	 * content:发布逻辑
	 * 
	 *根据位置(activity_position)和状态(status)获取已发布数量：
	 *1.若位置为 首页轮播 ，则当已发布数量小于4时允许发布
	 *2.若位置为 其他位置，则当不存在已发布时允许发布
	 * 
	 * */
	@Override
	public boolean validatePublish(String activityPosition) {
		int count = activityDao.validatePublish(activityPosition);
		if (activityPosition.equals("indexSwiper")) { //如果是首页轮播，可以发布4个
			if (count < 4) {
				return true;
			}
		}else if (activityPosition.equals("indexNew")) {//如果是最新上线可以发布很多
			
				return true;
			
		}else if (activityPosition.equals("discountLeftMiddle")) {//如果是特惠的可以发布很多
			
			return true;
		
	    }else if (activityPosition.equals("discountTop")) {//如果是特惠的顶部可以发布很多
			
			return true;
		
	    }else{
			if(count==0){
			return true;
		}
		}
		
		return false;
	}

	@Override
	public String getActivityPosition(long id) {
		return activityDao.getActivityPosition(id);
	}

	@Override
	public int validateGoodsCount(long id) {
		return activityDao.validateGoodsCount(id);
	}

}
