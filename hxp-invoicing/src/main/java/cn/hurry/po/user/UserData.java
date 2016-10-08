package cn.hurry.po.user;

import java.util.Date;

/**
 * 用户数据类
 * 
 * @author zh.sqy@qq.com
 * 
 */
public class UserData {

	/**
	 * 用户编号
	 */
	private int id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 助记码
	 */
	private String memoryCode;

	/**
	 * 性别
	 */
	private byte sex;

	/**
	 * 籍贯
	 */
	private String hometown;

	/**
	 * 政治面貌
	 */
	private String zzmm;

	/**
	 * 学历
	 */
	private String degree;

	/**
	 * 仓库编号
	 */
	private int storageId;

	/**
	 * 分组编号
	 */
	private int teamId;

	/**
	 * 联系电话
	 */
	private String tel;

	/**
	 * 创建时间
	 */
	private Date createDatetime;

	/**
	 * 最后修改时间
	 */
	private Date lastChangeDatetime;

	/**
	 * 修改人编号
	 */
	private int changeId;

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

	public String getMemoryCode() {
		return memoryCode;
	}

	public void setMemoryCode(String memoryCode) {
		this.memoryCode = memoryCode;
	}

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getZzmm() {
		return zzmm;
	}

	public void setZzmm(String zzmm) {
		this.zzmm = zzmm;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public int getStorageId() {
		return storageId;
	}

	public void setStorageId(int storageId) {
		this.storageId = storageId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Date getLastChangeDatetime() {
		return lastChangeDatetime;
	}

	public void setLastChangeDatetime(Date lastChangeDatetime) {
		this.lastChangeDatetime = lastChangeDatetime;
	}

	public int getChangeId() {
		return changeId;
	}

	public void setChangeId(int changeId) {
		this.changeId = changeId;
	}

}
