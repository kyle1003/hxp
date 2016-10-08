package cn.hurry.po.operate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hurry.po.user.User;

/**
 * 管理组实体类
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class ManagerGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private Integer id;

	/**
	 * 管理组组名
	 */
	private String name;

	/**
	 * 管理组简介
	 */
	private String discription;


	/**
	 * 创建时间
	 */
	private Date addTime;

	/**
	 * 管理组人员
	 */
	private List<User> users = new ArrayList<User>();

	/**
	 * 管理组的权限组列表
	 */
	private List<OperateGroup> operateGroups = new ArrayList<OperateGroup>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<OperateGroup> getOperateGroups() {
		return operateGroups;
	}

	public void setOperateGroups(List<OperateGroup> operateGroups) {
		this.operateGroups = operateGroups;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

}
