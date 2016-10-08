package cn.hurry.po.store;

import cn.hurry.po.user.User;

/**
 * 仓库
 * 
 * @author ZhouHao
 * 
 */
public class Store {
	/**
	 * 仓库编号
	 */
	private int id;

	/**
	 * 仓库名称
	 */
	private String name;

	/**
	 * 负责人编号
	 */
	private int userId;

	/**
	 * 负责人
	 */
	private User user;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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
		StringBuilder builder = new StringBuilder();
		builder.append("id="+id);
		builder.append(",name="+name);
		builder.append(",user="+(user==null?"":user.getUsername()));
		builder.append(",userId="+(user==null?"":user.getId()));
		return builder.toString();
	}
}
