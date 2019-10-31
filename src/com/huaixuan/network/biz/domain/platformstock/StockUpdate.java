package com.huaixuan.network.biz.domain.platformstock;



/**
 * @author Mr_Yang   2016-5-10 下午03:48:11
 * 同步库存到各个平台
 **/

public class StockUpdate
{
	private Long id;
	private String sku; 
	private int nowStockNum; //当前库存
	private int lastUpdateStockNum;//上次更新库存
	private int orderStockNum; //平台已下单库存 -已下单本地未开单
	private String type;//sh-上海  hk-香港
	private String shangpinSku; //尚品sku
	private String kaolaSku;    // 考拉sku
	private String kaolaKey;    //  考拉key
	private String kaolahtSku;  //考拉海淘Sku
	private String kaolahtKey; //考拉海淘Key
	private String zhenpinSku;  // 珍品sku
	private String sikuSku;     // 市庫sku
	private String sikunewSku;    //新市庫sku
	private String yhdSku;      // 一號店sku
	private String yunshangSku;  // 云尚店sku
	private String higoSku;     //  海購sku
	private String tmallSku;   //   天貓sku
	private String fqlSku;       // 分期樂sku
	private String jdSku;     //    京東sku
	private String pddSku;      //  拼多多sku
	private String pddnewSku;      //  新拼多多sku
	private String MlhSku;      //  魅力會sku
	private String MlhnewSku;      //  魅力會sku,5000以上的
	private String MlhnewsecSku;      //  魅力會sku,5000以下的
	private String xhsSku;		//小红书SKU
	private String xhsitemId;  	//小红书itemid
	private String ofashionsku; //OFashionsku
	private String weimobsku;   //微盟(weimob)sku
	private String product_id; //OFashionsku productid
    private String yinTaiSku;      //银泰sku
    private String shepinSku;      //奢品sku
    private String suningSku;      //苏宁sku
    private String xiyouProdId;  //供应商银泰西有的唯一标识
    private String yshangSkuId;  //云尚的唯一标识
    
    private String kaola_on_sale_status;//考拉商品状态
    private String kaolaht_on_sale_status;//考拉海淘商品状态
    private String siku_on_sale_status;//寺库商品状态
    private String tmall_on_sale_status;//天猫商品状态
    private String jd_on_sale_status;//京东商品状态
    private String xhs_on_sale_status;//小红书商品状态
    private String mlh_on_sale_status; //魅力惠商品状态
    private String shepin_on_sale_status;//奢品商品状态
    private String zhenpin_on_sale_status;//珍品商品状态
    private String yunshang_on_sale_status;//云尚商品状态
    
    
	private int xhsOrderStock; //小红书订单数
	private int ofashionOrderStock;//OFashion订单数
	private int shangpinOrderStock;// 尚品訂單數
	private int kaolaOrderStock;   // 考拉訂單數
	private int kaolahtOrderStock; //考拉海淘订单数
	private int zhenpinOrderStock;  //珍品訂單數
	private int sikuOrderStock;      //市庫訂單數
	private int sikunewOrderStock;      //新市庫訂單數
	private int yhdOrderStock;      //一號店訂單數
	private int yunshangOrderStock;      //云尚店訂單數
	private int higoOrderStock;     //海購訂單數
	private int tmallOrderStock;    //天貓訂單數
	private int fqlOrderStock;      //分期樂訂單數
	private int jdOrderStock;      // 京東訂單數
	private int pddOrderStock;     // 拼多多訂單數
	private int pddnewOrderStock;     // 新拼多多訂單數
	private int MlhOrderStock;     //  魅力會訂單數
	private int weiMobOrderStock;  //微盟订单数
	private int yinTaiOrderStock;   //银泰订单数
	private int shepinOrderStock;   //奢品订单数

	private int suningOrderStock;   //苏宁订单数

	private int ofashionMcOrderStock;//Ofashion迷橙B2B

	private String generate_time;//数据生成时间
	private String lastOrderTime;  //最後的訂單時間
	private String updateTime;     //更新時間
	
	private String typename;     //数据库新增的字段,型号 
	private String materialname; //数据库新增的字段,材质
	private String colorname;    //数据库新增的字段,颜色
	private String sizename;    //数据库新增的字段,尺寸
	private Long idBrand;      //数据库新增的字段,品牌ID
	private Long idSeries;     //数据库新增的字段,品类ID
	private String brandname;
    private String seriesname;
	
	
	
	private String color;
	private String pType;
	private String material;
	
	
	
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getpType() {
		return pType;
	}
	public void setpType(String pType) {
		this.pType = pType;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getSku()
	{
		return sku;
	}
	public void setSku(String sku)
	{
		this.sku = sku;
	}
	public int getNowStockNum()
	{
		return nowStockNum;
	}
	public void setNowStockNum(int nowStockNum)
	{
		this.nowStockNum = nowStockNum;
	}
	public int getLastUpdateStockNum()
	{
		return lastUpdateStockNum;
	}
	public void setLastUpdateStockNum(int lastUpdateStockNum)
	{
		this.lastUpdateStockNum = lastUpdateStockNum;
	}
	public int getOrderStockNum()
	{
		return orderStockNum;
	}
	public void setOrderStockNum(int orderStockNum)
	{
		this.orderStockNum = orderStockNum;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getShangpinSku()
	{
		return shangpinSku;
	}
	public void setShangpinSku(String shangpinSku)
	{
		this.shangpinSku = shangpinSku;
	}
	public String getKaolaSku()
	{
		return kaolaSku;
	}
	public void setKaolaSku(String kaolaSku)
	{
		this.kaolaSku = kaolaSku;
	}
	public String getKaolaKey()
	{
		return kaolaKey;
	}
	public void setKaolaKey(String kaolaKey)
	{
		this.kaolaKey = kaolaKey;
	}
	public String getZhenpinSku()
	{
		return zhenpinSku;
	}
	public void setZhenpinSku(String zhenpinSku)
	{
		this.zhenpinSku = zhenpinSku;
	}
	public String getSikuSku()
	{
		return sikuSku;
	}
	public void setSikuSku(String sikuSku)
	{
		this.sikuSku = sikuSku;
	}
	public String getYhdSku()
	{
		return yhdSku;
	}
	public void setYhdSku(String yhdSku)
	{
		this.yhdSku = yhdSku;
	}
	public int getShangpinOrderStock()
	{
		return shangpinOrderStock;
	}
	public void setShangpinOrderStock(int shangpinOrderStock)
	{
		this.shangpinOrderStock = shangpinOrderStock;
	}
	public int getKaolaOrderStock()
	{
		return kaolaOrderStock;
	}
	public void setKaolaOrderStock(int kaolaOrderStock)
	{
		this.kaolaOrderStock = kaolaOrderStock;
	}
	public int getZhenpinOrderStock()
	{
		return zhenpinOrderStock;
	}
	public void setZhenpinOrderStock(int zhenpinOrderStock)
	{
		this.zhenpinOrderStock = zhenpinOrderStock;
	}
	public int getSikuOrderStock()
	{
		return sikuOrderStock;
	}
	public void setSikuOrderStock(int sikuOrderStock)
	{
		this.sikuOrderStock = sikuOrderStock;
	}
	

	public String getSikunewSku() {
		return sikunewSku;
	}
	public void setSikunewSku(String sikunewSku) {
		this.sikunewSku = sikunewSku;
	}
	public int getSikunewOrderStock() {
		return sikunewOrderStock;
	}
	public void setSikunewOrderStock(int sikunewOrderStock) {
		this.sikunewOrderStock = sikunewOrderStock;
	}
	
	public int getYhdOrderStock()
	{
		return yhdOrderStock;
	}
	public void setYhdOrderStock(int yhdOrderStock)
	{
		this.yhdOrderStock = yhdOrderStock;
	}
	public String getLastOrderTime()
	{
		return lastOrderTime;
	}
	public void setLastOrderTime(String lastOrderTime)
	{
		this.lastOrderTime = lastOrderTime;
	}
	public String getHigoSku()
	{
		return higoSku;
	}
	public void setHigoSku(String higoSku)
	{
		this.higoSku = higoSku;
	}
	public String getTmallSku()
	{
		return tmallSku;
	}
	public void setTmallSku(String tmallSku)
	{
		this.tmallSku = tmallSku;
	}
	public int getHigoOrderStock()
	{
		return higoOrderStock;
	}
	public void setHigoOrderStock(int higoOrderStock)
	{
		this.higoOrderStock = higoOrderStock;
	}
	public int getTmallOrderStock()
	{
		return tmallOrderStock;
	}
	public void setTmallOrderStock(int tmallOrderStock)
	{
		this.tmallOrderStock = tmallOrderStock;
	}
	public String getFqlSku()
	{
		return fqlSku;
	}
	public void setFqlSku(String fqlSku)
	{
		this.fqlSku = fqlSku;
	}
	public String getJdSku()
	{
		return jdSku;
	}
	public void setJdSku(String jdSku)
	{
		this.jdSku = jdSku;
	}
	public int getFqlOrderStock()
	{
		return fqlOrderStock;
	}
	public void setFqlOrderStock(int fqlOrderStock)
	{
		this.fqlOrderStock = fqlOrderStock;
	}
	public int getJdOrderStock()
	{
		return jdOrderStock;
	}
	public void setJdOrderStock(int jdOrderStock)
	{
		this.jdOrderStock = jdOrderStock;
	}
	public String getPddSku() {
		return pddSku;
	}
	public void setPddSku(String pddSku) {
		this.pddSku = pddSku;
	}
	
	
	public int getPddOrderStock() {
		return pddOrderStock;
	}
	
	public void setPddOrderStock(int pddOrderStock) {
		this.pddOrderStock = pddOrderStock;
	}
	
	
	
	
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getXhsSku() {
		return xhsSku;
	}
	public void setXhsSku(String xhsSku) {
		this.xhsSku = xhsSku;
	}
	public String getXhsitemId() {
		return xhsitemId;
	}
	public void setXhsitemId(String xhsitemId) {
		this.xhsitemId = xhsitemId;
	}
	public String getYinTaiSku() {
		return yinTaiSku;
	}
	public void setYinTaiSku(String yinTaiSku) {
		this.yinTaiSku = yinTaiSku;
	}
	public int getXhsOrderStock() {
		return xhsOrderStock;
	}
	public void setXhsOrderStock(int xhsOrderStock) {
		this.xhsOrderStock = xhsOrderStock;
	}
	public String getMlhSku() {
		return MlhSku;
	}
	public void setMlhSku(String mlhSku) {
		MlhSku = mlhSku;
	}
	public int getMlhOrderStock() {
		return MlhOrderStock;
	}
	public void setMlhOrderStock(int mlhOrderStock) {
		MlhOrderStock = mlhOrderStock;
	}
	public String getOfashionsku() {
		return ofashionsku;
	}
	public void setOfashionsku(String ofashionsku) {
		this.ofashionsku = ofashionsku;
	}
	public int getOfashionOrderStock() {
		return ofashionOrderStock;
	}
	public void setOfashionOrderStock(int ofashionOrderStock) {
		this.ofashionOrderStock = ofashionOrderStock;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getWeimobsku() {
		return weimobsku;
	}
	public void setWeimobsku(String weimobsku) {
		this.weimobsku = weimobsku;
	}
	
	public int getWeiMobOrderStock() {
		return weiMobOrderStock;
	}
	public void setWeiMobOrderStock(int weiMobOrderStock) {
		this.weiMobOrderStock = weiMobOrderStock;
	}
	public int getYinTaiOrderStock() {
		return yinTaiOrderStock;
	}
	public void setYinTaiOrderStock(int yinTaiOrderStock) {
		this.yinTaiOrderStock = yinTaiOrderStock;
	}
	public int getShepinOrderStock() {
		return shepinOrderStock;
	}
	public void setShepinOrderStock(int shepinOrderStock) {
		this.shepinOrderStock = shepinOrderStock;
	}
	public String getShepinSku() {
		return shepinSku;
	}
	public void setShepinSku(String shepinSku) {
		this.shepinSku = shepinSku;
	}

	public String getSuningSku() {
		return suningSku;
	}
	public void setSuningSku(String suningSku) {
		this.suningSku = suningSku;
	}
	
	public String getXiyouProdId() {
		return xiyouProdId;
	}
	public void setXiyouProdId(String xiyouProdId) {
		this.xiyouProdId = xiyouProdId;
	}
	public int getSuningOrderStock() {
		return suningOrderStock;
	}
	public void setSuningOrderStock(int suningOrderStock) {
		this.suningOrderStock = suningOrderStock;
	}

	public int getOfashionMcOrderStock() {
		return ofashionMcOrderStock;
	}
	public void setOfashionMcOrderStock(int ofashionMcOrderStock) {
		this.ofashionMcOrderStock = ofashionMcOrderStock;
	}
	public String getPddnewSku() {
		return pddnewSku;
	}
	public void setPddnewSku(String pddnewSku) {
		this.pddnewSku = pddnewSku;
	}
	public int getPddnewOrderStock() {
		return pddnewOrderStock;
	}
	public void setPddnewOrderStock(int pddnewOrderStock) {
		this.pddnewOrderStock = pddnewOrderStock;
	}
	public String getKaola_on_sale_status() {
		return kaola_on_sale_status;
	}
	public void setKaola_on_sale_status(String kaola_on_sale_status) {
		this.kaola_on_sale_status = kaola_on_sale_status;
	}
	public String getTmall_on_sale_status() {
		return tmall_on_sale_status;
	}
	public void setTmall_on_sale_status(String tmall_on_sale_status) {
		this.tmall_on_sale_status = tmall_on_sale_status;
	}
	public String getJd_on_sale_status() {
		return jd_on_sale_status;
	}
	public void setJd_on_sale_status(String jd_on_sale_status) {
		this.jd_on_sale_status = jd_on_sale_status;
	}
	public String getXhs_on_sale_status() {
		return xhs_on_sale_status;
	}
	public void setXhs_on_sale_status(String xhs_on_sale_status) {
		this.xhs_on_sale_status = xhs_on_sale_status;
	}
	
	public String getMlh_on_sale_status() {
		return mlh_on_sale_status;
	}
	public void setMlh_on_sale_status(String mlh_on_sale_status) {
		this.mlh_on_sale_status = mlh_on_sale_status;
	}
	public String getShepin_on_sale_status() {
		return shepin_on_sale_status;
	}
	public void setShepin_on_sale_status(String shepin_on_sale_status) {
		this.shepin_on_sale_status = shepin_on_sale_status;
	}
	public String getGenerate_time() {
		return generate_time;
	}
	public void setGenerate_time(String generate_time) {
		this.generate_time = generate_time;
	}
	public String getZhenpin_on_sale_status() {
		return zhenpin_on_sale_status;
	}
	public void setZhenpin_on_sale_status(String zhenpin_on_sale_status) {
		this.zhenpin_on_sale_status = zhenpin_on_sale_status;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getMaterialname() {
		return materialname;
	}
	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}
	public String getColorname() {
		return colorname;
	}
	public void setColorname(String colorname) {
		this.colorname = colorname;
	}
	public String getSizename() {
		return sizename;
	}
	public void setSizename(String sizename) {
		this.sizename = sizename;
	}
	public Long getIdBrand() {
		return idBrand;
	}
	public void setIdBrand(Long idBrand) {
		this.idBrand = idBrand;
	}
	public Long getIdSeries() {
		return idSeries;
	}
	public void setIdSeries(Long idSeries) {
		this.idSeries = idSeries;
	}
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	public String getSeriesname() {
		return seriesname;
	}
	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}
	public String getSiku_on_sale_status() {
		return siku_on_sale_status;
	}
	public void setSiku_on_sale_status(String siku_on_sale_status) {
		this.siku_on_sale_status = siku_on_sale_status;
	}
	public String getMlhnewSku() {
		return MlhnewSku;
	}
	public void setMlhnewSku(String mlhnewSku) {
		MlhnewSku = mlhnewSku;
	}
	public String getYunshangSku() {
		return yunshangSku;
	}
	public void setYunshangSku(String yunshangSku) {
		this.yunshangSku = yunshangSku;
	}
	public String getYunshang_on_sale_status() {
		return yunshang_on_sale_status;
	}
	public void setYunshang_on_sale_status(String yunshang_on_sale_status) {
		this.yunshang_on_sale_status = yunshang_on_sale_status;
	}
	public int getYunshangOrderStock() {
		return yunshangOrderStock;
	}
	public void setYunshangOrderStock(int yunshangOrderStock) {
		this.yunshangOrderStock = yunshangOrderStock;
	}
	public String getYshangSkuId() {
		return yshangSkuId;
	}
	public void setYshangSkuId(String yshangSkuId) {
		this.yshangSkuId = yshangSkuId;
	}
	public String getMlhnewsecSku() {
		return MlhnewsecSku;
	}
	public void setMlhnewsecSku(String mlhnewsecSku) {
		MlhnewsecSku = mlhnewsecSku;
	}
	public String getKaolahtSku() {
		return kaolahtSku;
	}
	public void setKaolahtSku(String kaolahtSku) {
		this.kaolahtSku = kaolahtSku;
	}
	public String getKaolahtKey() {
		return kaolahtKey;
	}
	public void setKaolahtKey(String kaolahtKey) {
		this.kaolahtKey = kaolahtKey;
	}
	public String getKaolaht_on_sale_status() {
		return kaolaht_on_sale_status;
	}
	public void setKaolaht_on_sale_status(String kaolaht_on_sale_status) {
		this.kaolaht_on_sale_status = kaolaht_on_sale_status;
	}
	public int getKaolahtOrderStock() {
		return kaolahtOrderStock;
	}
	public void setKaolahtOrderStock(int kaolahtOrderStock) {
		this.kaolahtOrderStock = kaolahtOrderStock;
	}
	
	
	
}
 
