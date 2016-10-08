package cn.hurry.po.order;

import java.util.Date;

import cn.hurry.manage.goods.GoodsManage;
import cn.hurry.po.goods.Goods;
import cn.hurry.util.NumberUtil;

/**
 * 单据商品绑定实例
 * 
 * @author ZhouHao
 * 
 */
public class OrderGoods {

	/**
	 * 绑定编号
	 */
	private int id;

	/**
	 * 单据编号
	 */
	private String orderId;

	/**
	 * 单据实例
	 */
	private Order order;

	/**
	 * 商品编号
	 */
	private int goodsId;

	/**
	 * 商品实例
	 */
	private Goods goods;

	/**
	 * 单价
	 */
	private double price;

	/**
	 * 数量
	 */
	private double number;

	/**
	 * 剩余数量
	 */
	private double surplusNumber;

	public static final String SHELFLIFEUNIT_JSON = "[{\"id\":\"1\",\"text\":\"月\"},{\"id\":\"2\",\"text\":\"天\"},{\"id\":\"3\",\"text\":\"年\"}]";

	public static final int SHELFLIFEUNIT_YEAR = 3;

	public static final int SHELFLIFEUNIT_MONTH = 1;

	public static final int SHELFLIFEUNIT_DAY = 2;

	/**
	 * 生产日期
	 */
	private Date productionDate;

	/**
	 * 有效期
	 */
	private Date shelfLife;

	/**
	 * 生产批号
	 */
	private String productionBatchNumber;

	/**
	 * 批准文号
	 */
	private String approvalDocument;

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public String getApprovalDocument() {
		return approvalDocument;
	}

	public void setApprovalDocument(String approvalDocument) {
		this.approvalDocument = approvalDocument;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public Date getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(Date shelfLife) {
		this.shelfLife = shelfLife;
	}

	public double getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(double surplusNumber) {
		this.surplusNumber = surplusNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public Goods getGoods() {
		if (goods == null) {
			goods = GoodsManage.getGoodsById(goodsId);
		}
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	@Override
	protected OrderGoods clone() throws CloneNotSupportedException {
		return (OrderGoods) super.clone();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("oid=" + id);
		builder.append(",orderid=" + orderId);
		if (getGoods() != null) {
			builder.append("," + getGoods().toString());
			builder.append(",countNumber=" + getGoods().getNumber());
		}
		builder.append(",price=" + NumberUtil.convert(price));
		builder.append(",countPrice=" + NumberUtil.convert((price * number)));
		builder.append(",odNumber=" + number);
		builder.append(",surplusNumber=" + surplusNumber);
		return builder.toString();
	}
}
