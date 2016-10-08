package cn.hurry.po.goods;

/**
 * 商品类型
 * 
 * @author ZhouHao
 * 
 */
public class GoodsType implements Comparable<GoodsType> {
	/**
	 * 商品类型编号
	 */
	private int id;

	/**
	 * 商品类型名称
	 */
	private String name;

	/**
	 * 商品类型父级编号
	 */
	private int pid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int compareTo(GoodsType o) {
		return this.getId() > o.getId() ? 1 : 0;
	}

}
