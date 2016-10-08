package cn.hurry.po.inventory;

import cn.hurry.po.goods.Goods;

/**
 * 盘点商品
 * 
 * @author ZhouHao
 * 
 */
public class InventoryGoods {
	/**
	 * 盘点商品绑定对象
	 */
	private int id;

	/**
	 * 盘点编号
	 */
	private int inventoryId;

	/**
	 * 盘点对象
	 */
	private Inventory inventory;
	/**
	 * 商品编号
	 */
	private int goodsId;

	/**
	 * 库存数量
	 */
	private double storeNumber;

	/**
	 * 盘点数量
	 */
	private double realNumber;

	/**
	 * 商品对象
	 */
	private Goods goods;

	/**
	 * 库存成本
	 */
	private double money;

	/**
	 * 总成本
	 */
	private double sumMoney;

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public double getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(double storeNumber) {
		this.storeNumber = storeNumber;
	}

	public double getRealNumber() {
		return realNumber;
	}

	public void setRealNumber(double realNumber) {
		this.realNumber = realNumber;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(double sumMoney) {
		this.sumMoney = sumMoney;
	}

}
