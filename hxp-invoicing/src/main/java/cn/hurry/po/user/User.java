package cn.hurry.po.user;

import java.io.Serializable;
import java.util.Date;

import cn.hurry.util.DateTimeUtils;


/**
 * 用户实体类
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Integer id;

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

	/**
	 * 是否是超级管理员
	 */
	private boolean isAdmin;

	/**
	 * 用户所在管理组id
	 */
	private Integer managerGroupId;

	/**
	 * 用户添加时间
	 */
	private Date createDatetime;

	/**
	 * 是否有效
	 */
	private boolean isValid;

	/**
	 * 用户数据
	 */
	private UserData userData;


	public Integer getId() {
		return id;
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

	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Integer getManagerGroupId() {
		return managerGroupId;
	}

	public void setManagerGroupId(Integer managerGroupId) {
		this.managerGroupId = managerGroupId;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=" + this.getId());
		sb.append(",username=" + this.getUsername());
		sb.append(",remark=" + this.getRemark());
		sb.append(",createDatetime=" + DateTimeUtils.format(this.getCreateDatetime(), DateTimeUtils.YEAR_MONTH_DAY));
		sb.append(",isValid=" + (this.isValid() ? "有效" : "无效"));
		return sb.toString();
	}

}
