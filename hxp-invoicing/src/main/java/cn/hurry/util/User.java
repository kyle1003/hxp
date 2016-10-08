package cn.hurry.util;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户实体类
 * 
 * 
 */
public class User implements Serializable {

	public static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private int id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 用户备注
	 */
	private String remark;

	private boolean sex;

	private User father;

	private Date date;

	public Integer getId() {
		return id;
	}
	
	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getFather() {
		return father;
	}

	public void setFather(User father) {
		this.father = father;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=" + this.getId());
		sb.append(",username=" + this.getUsername());
		// sb.append(",password=" + this.getPassword());
		sb.append(",remark=" + this.getRemark());
		return sb.toString();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
