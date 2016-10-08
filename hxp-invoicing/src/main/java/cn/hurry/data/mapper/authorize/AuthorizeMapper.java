package cn.hurry.data.mapper.authorize;

import cn.hurry.po.authorize.Authorize;

/**
 * 授权映射接口
 * 
 * @author zh.sqy@qq.com
 * 
 */
public interface AuthorizeMapper {

	/**
	 * 新增授权
	 * 
	 * authorize
	 *            授权
	 * @return 成功新增条数
	 * @throws Exception
	 */
	public int insertAuthorize(Authorize authorize) throws Exception;

	/**
	 * 更新授权
	 * 
	 * @param authorize
	 *            授权
	 * @return 成功更新条数
	 * @throws Exception
	 */
	public int updateAuthorize(Authorize authorize) throws Exception;

	/**
	 * 查询授权
	 * 
	 * @return 授权
	 * @throws Exception
	 */
	public Authorize selectAuthorize() throws Exception;

}
