package cn.hurry.po.goods;

import java.text.Collator;

import cn.hurry.manage.goods.GoodsTypeManage;
import cn.hurry.manage.unit.NormsManage;
import cn.hurry.manage.unit.UnitManage;
import cn.hurry.po.unit.Norms;
import cn.hurry.po.unit.Unit;
import cn.hurry.util.NumberUtil;

/**
 * 商品
 * 
 * @author ZhouHao
 * 
 */
public class Goods implements Comparable<Goods> {
	/**
	 * 商品编号
	 */
	private int id;

	/**
	 * 商品条码
	 */
	private String code;

	/**
	 * 监管码
	 */
	private String jgCode;
	
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 剂量
	 */
	private String dose;
	
	/**
	 * 剂型
	 */
	private String doseType;
	
	/**
	 * 生产厂家
	 */
	private String manufacturers;
	
	/**
	 * 药名
	 */
	private String medicineName;
	
	/**
	 * 规格编号
	 */
	private int normsId;

	/**
	 * 单位编号
	 */
	private int unitId;

	/**
	 * 商品类别编号
	 */
	private int goodsTypeId;

	/**
	 * 商品类别实例
	 */
	private GoodsType goodsType;

	/**
	 * 规格对象
	 */
	private Norms norms;

	/**
	 * 单位对象
	 */
	private Unit unit;

	/**
	 * 预计售价
	 */
	private double sellPrice;

	/**
	 * 预计进价
	 */
	private double buyPrice;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 库存
	 */
	private double number;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public int getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(int goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public GoodsType getGoodsType() {
		if(goodsType==null){
			goodsType=GoodsTypeManage.getGoodsTypeById(goodsTypeId);
		}
		return goodsType;
	}

	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNormsId() {
		return normsId;
	}

	public void setNormsId(int normsId) {
		this.normsId = normsId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public Norms getNorms() {
		if(norms==null){
			norms=NormsManage.getNormsById(getNormsId());
		}
		return norms;
	}

	public void setNorms(Norms norms) {
		this.norms = norms;
	}

	public Unit getUnit() {
		if(unit==null){
			unit=UnitManage.getUnitById(getUnitId());
		}
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	/**
	 * 重写toString方法,便于JSON的灵活支持
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(1024);
		builder.append("id=" + id);
		builder.append(",name=" + name);
		builder.append(",sellPrice=" + NumberUtil.convert(sellPrice));
		builder.append(",code=" + code);
		builder.append(",buyPrice=" + NumberUtil.convert(buyPrice));
		builder.append(",remark=" + remark);
		builder.append(",number=" + number);
		builder.append(",dose=" + getDose());
		builder.append(",manufacturers=" + getManufacturers());
		builder.append(",medicineName=" + getMedicineName());
		// 使用内存中的数据
		builder.append(",unit=" + (getUnit()==null?"-":getUnit().getName()));
		builder.append(",goodsType=" +  (getGoodsType()==null?"-":getGoodsType().getName()));
		builder.append(",norms=" + (getNorms()==null?"-":getNorms().getName()));
		return builder.toString();
	}

	/**
	 * 排序字段
	 */
	private String sortField;

	/**
	 * 排序方式 DESC 反序 ASC 正序
	 */
	private String sortOrder;

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(String manufacturers) {
		this.manufacturers = manufacturers;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	
	public String getDoseType() {
		return doseType;
	}

	public void setDoseType(String doseType) {
		this.doseType = doseType;
	}

	public String getJgCode() {
		return jgCode;
	}

	public void setJgCode(String jgCode) {
		this.jgCode = jgCode;
	}

	private static Collator cmp = Collator.getInstance(java.util.Locale.CHINA);

	public int compareTo(Goods o) {
		if ("sellPrice".equals(getSortField())) {
			return getSellPrice() < o.getSellPrice() ? 1 : 0;
		}
		if ("buyPrice".equals(getSortField())) {
			return getBuyPrice() < o.getBuyPrice() ? 1 : 0;
		}
		if ("number".equals(getSortField())) {
			return getNumber() < o.getNumber() ? 1 : 0;
		}
		if ("name".equals(getSortField())) {
			return cmp.compare(getName(), o.getName());
		}
		return cmp.compare(getName(), o.getName());
	}
}
