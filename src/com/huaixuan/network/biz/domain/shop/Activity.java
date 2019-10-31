package com.huaixuan.network.biz.domain.shop;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huaixuan.network.biz.domain.BaseObject;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.hundsun.network.melody.common.util.StringUtil;

public class Activity extends BaseObject implements Serializable {

	/**
     *
     */
	private static final long serialVersionUID = -5962792129477935378L;
	protected Log log = LogFactory.getLog(this.getClass());
	/* @property: */
	private long id;
	/* @property: */
	private String title;
	/* @property: */
	private Date gmtStart;
	/* @property: */
	private Date gmtEnd;
	/* @property: */	
	private String img;
	// �Сͼ
	private String imgSmall;

	// added by fangqing 20101026 ��ҳСͼ
	private String imgHomeSamll;
	
	//添加活动折扣
	private String activityDiscount;

	public String getImgHomeSamll() {
		if (StringUtil.isNotBlank(img)) {
			String[] imgSplit = img.split("\\.");
			return imgSplit[0] + "_homeSmall." + imgSplit[1];
		}
		return null;
	}

	public void setImgHomeSamll(String imgHomeSamll) {
		this.imgHomeSamll = imgHomeSamll;
	}

	// added by cyan 20101102 �����У��Сͼ����ҳСͼ
	/* @property: */
	private String img_small;

	/* @property: */
	private String img_home_small;

	public String getImg_small() {
		return img_small;
	}

	public void setImg_small(String img_small) {
		this.img_small = img_small;
	}

	public String getImg_home_small() {
		return img_home_small;
	}

	public void setImg_home_small(String img_home_small) {
		this.img_home_small = img_home_small;
	}

	/* @property: */
	private String context;
	/* @property: */
	private String status;
	/* @property: */
	private Date gmtCreate;
	/* @property: */
	private Date gmtModify;

	private String gmtStart_str;
	/* @property: */
	private String gmtEnd_str;
	/* redundant fields */
	private Goods firstGoods;
	
	private String activityPosition;

	public String getImgSmall() {
		if (StringUtil.isNotBlank(img)) {
			String[] imgSplit = img.split("\\.");
			return imgSplit[0] + "_small." + imgSplit[1];
		}
		return null;
	}

	public void setImgSmall(String imgSmall) {
		this.imgSmall = imgSmall;
	}

	public Goods getFirstGoods() {
		return firstGoods;
	}

	public void setFirstGoods(Goods firstGoods) {
		this.firstGoods = firstGoods;
	}

	/* Default constructor - creates a new instance with no values set. */
	public Activity() {
	}

	/* @model:�������� */
	public void setId(long obj) {
		this.id = obj;
	}

	/* @model:������ */
	public long getId() {
		return this.id;
	}

	/* @model:�������� */
	public void setTitle(String obj) {
		this.title = obj;
	}

	/* @model:������ */
	public String getTitle() {
		return this.title;
	}

	/* @model:�������� */
	public void setGmtStart(Date obj) {
		this.gmtStart = obj;
	}

	/* @model:������ */
	public Date getGmtStart() {
		return this.gmtStart;
	}

	/* @model:�������� */
	public void setGmtEnd(Date obj) {
		this.gmtEnd = obj;
	}

	/* @model:������ */
	public Date getGmtEnd() {
		return this.gmtEnd;
	}

	/* @model:�������� */
	public void setImg(String obj) {
		this.img = obj;
	}

	/* @model:������ */
	public String getImg() {
		return this.img;
	}

	/* @model:�������� */
	public void setContext(String obj) {
		this.context = obj;
	}

	/* @model:������ */
	public String getContext() {
		return this.context;
	}

	/* @model:�������� */
	public void setStatus(String obj) {
		this.status = obj;
	}

	/* @model:������ */
	public String getStatus() {
		return this.status;
	}

	/* @model:�������� */
	public void setGmtCreate(Date obj) {
		this.gmtCreate = obj;
	}

	/* @model:������ */
	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	/* @model:�������� */
	public void setGmtModify(Date obj) {
		this.gmtModify = obj;
	}

	/* @model:������ */
	public Date getGmtModify() {
		return this.gmtModify;
	}

	/* {@inheritDoc} */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Activity)) {
			return false;
		}
		final Activity activity = (Activity) o;
		return this.hashCode() == activity.hashCode();
	}

	/* {@inheritDoc} */
	public int hashCode() {
		// return this.hashCode();
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result
				+ ((gmtStart == null) ? 0 : gmtStart.hashCode());
		result = prime * result + ((gmtEnd == null) ? 0 : gmtEnd.hashCode());
		result = prime * result + ((img == null) ? 0 : img.hashCode());
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModify == null) ? 0 : gmtModify.hashCode());
		result = prime * result
				+ ((activityPosition == null) ? 0 : activityPosition.hashCode());
		result = prime * result
				+ ((activityDiscount == null) ? 0 : activityDiscount.hashCode());

		return result;
	}

	/* {@inheritDoc} */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("id", this.id)
				.append("title", this.title).append("gmtStart", this.gmtStart)
				.append("gmtEnd", this.gmtEnd).append("img", this.img)
				.append("context", this.context).append("status", this.status)
				.append("gmtCreate", this.gmtCreate)
				.append("gmtModify", this.gmtModify).append("activityPosition", this.activityPosition)
				.append("activityDiscount", this.activityDiscount);
		return sb.toString();
	}

	public String getGmtStart_str() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �����ʽ������ʾ����
		if (getGmtStart() != null) {
			return df.format(getGmtStart());
		} else {
			return null;
		}
	}

	public void setGmtStart_str(String gmtStart_str) {
		this.gmtStart_str = gmtStart_str;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �����ʽ������ʾ����
		Date ts = null;
		if (StringUtils.isNotBlank(gmtStart_str)) {
			try {
				ts = df.parse(gmtStart_str);
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}
		this.gmtStart = ts;
	}

	public String getGmtEnd_str() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �����ʽ������ʾ����
		if (getGmtEnd() != null) {
			return df.format(getGmtEnd());
		} else {
			return null;
		}
	}

	public void setGmtEnd_str(String gmtEnd_str) {
		this.gmtEnd_str = gmtEnd_str;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �����ʽ������ʾ����
		Date ts = null;
		if (StringUtils.isNotBlank(gmtEnd_str)) {
			try {
				ts = df.parse(gmtEnd_str + " 23:59:59");// ����ʱ��ת��Ϊ�������һ��
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}
		this.gmtEnd = ts;
	}

	public String getActivityPosition() {
		return activityPosition;
	}

	public void setActivityPosition(String activityPosition) {
		this.activityPosition = activityPosition;
	}

	public String getActivityDiscount() {
		return activityDiscount;
	}

	public void setActivityDiscount(String activityDiscount) {
		this.activityDiscount = activityDiscount;
	}
	
	

	
}
