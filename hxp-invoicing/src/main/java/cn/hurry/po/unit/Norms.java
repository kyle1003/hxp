package cn.hurry.po.unit;

/**
 * 规格
 * 
 * @author ZhouHao
 * 
 */
public class Norms {
	/**
	 * 规格编号
	 */
	private int id;

	/**
	 * 规格名称
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
		StringBuilder builder = new StringBuilder("id="+id);
		builder.append(",name="+name);
		return builder.toString();
	}
}
