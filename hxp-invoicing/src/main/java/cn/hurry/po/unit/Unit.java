package cn.hurry.po.unit;

/**
 * 单位
 * 
 * @author ZhouHao
 * 
 */
public class Unit {
	/**
	 * 单位编号
	 */
	private int id;

	/**
	 * 单位名称
	 */
	private String name;

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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("id=").append(id);
		builder.append(",name=").append(name);
		return builder.toString();
	}

}
