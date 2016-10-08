package cn.hurry.po.operate;

import java.io.Serializable;

/**
 * 权限组实体类
 * 
 * @author ZhouHao
 * 
 */
public class OperateGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private int id;

	/**
	 * 权限
	 */
	private int operateId;

	/**
	 * 管理组
	 */
	private int managerGroupId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOperateId() {
		return operateId;
	}

	public void setOperateId(int operateId) {
		this.operateId = operateId;
	}

	public int getManagerGroupId() {
		return managerGroupId;
	}

	public void setManagerGroupId(int managerGroupId) {
		this.managerGroupId = managerGroupId;
	}

}
