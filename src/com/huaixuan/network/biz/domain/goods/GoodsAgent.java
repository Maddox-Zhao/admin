package com.huaixuan.network.biz.domain.goods;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huaixuan.network.biz.domain.BaseObject;

public class GoodsAgent extends BaseObject {
    private static final long serialVersionUID = 3832626162173359411L;

    private Date              gmtCreate;                                            //创建时间
    private Date              gmtModify;                                            //修改时间
    private Long              userId;                                               //用户ID
    private Long              goodsId;                                              //商品ID
    private String            status;                                               //代销状态
    private String            title;                                                //商品名称
    private String            imgSmall;                                             //商品图片路径
    private String            goodsStatus;                                          //商品状态
    private String            isAgent;                                              //商品代销状态
    private String            goodsSn;                                              //商品编码
    private double            agentPrice;                                           //代销价格
    private double            marketPrice;                                          //市场价格
    private int               goodsNumber;                                          //可用库存
    private String            ruleName;                                             //返点规则名称
    private Long              ruleId;                                               //返点规则ID
    private double            goodsPrice;                                           //商城价
    protected Log             log              = LogFactory.getLog(this.getClass());

    public GoodsAgent clone() {
        GoodsAgent o = null;
        try {
            o = (GoodsAgent) super.clone();
        } catch (Exception e) {
            this.log.equals(e.getMessage());
        }
        return o;
    }

    /**
     * @return the goodsSn
     */
    public String getGoodsSn() {
        return goodsSn;
    }

    /**
     * @param goodsSn the goodsSn to set
     */
    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the imgSmall
     */
    public String getImgSmall() {
        return imgSmall;
    }

    /**
     * @param imgSmall the imgSmall to set
     */
    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    /**
     * @return the goodsStatus
     */
    public String getGoodsStatus() {
        return goodsStatus;
    }

    /**
     * @param goodsStatus the goodsStatus to set
     */
    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    /**
     * @return the isAgent
     */
    public String getIsAgent() {
        return isAgent;
    }

    /**
     * @param isAgent the isAgent to set
     */
    public void setIsAgent(String isAgent) {
        this.isAgent = isAgent;
    }

    /**
     * @return the goodsId
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId the goodsId to set
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate the gmtCreate to set
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return the gmtModify
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * @param gmtModify the gmtModify to set
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getAgentPrice() {
        return agentPrice;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public void setAgentPrice(double agentPrice) {
        this.agentPrice = agentPrice;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

}
