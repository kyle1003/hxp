package cn.hurry.data.mapper.store;

import java.util.HashMap;
import java.util.List;

import cn.hurry.po.store.Store;

/**
 * 仓库映射接口
 * 
 * @author ZhouHao
 * 
 */
public interface StoreMapper {
	/**
	 * 添加仓库
	 * 
	 * @param Store
	 *            仓库实例
	 * @return 成功添加数量
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertStore(Store store) throws Exception;

	/**
	 * 修改仓库
	 * 
	 * @param Store
	 *            仓库实例
	 * @return 成功修改数量
	 * @throws Exception
	 *             修改失败异常
	 */
	public int updateStore(Store store) throws Exception;

	/**
	 * 删除仓库
	 * 
	 * @param Store
	 *            仓库实例
	 * @return 成功删除数量
	 * @throws Exception
	 *             删除失败异常
	 */
	public int deleteStore(Store store) throws Exception;

	/**
	 * 根据仓库编号查询仓库
	 * 
	 * @param id
	 *            仓库编号
	 * @return 仓库实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Store selectStoreById(int id) throws Exception;

	/**
	 * 根据map条件集合查询仓库
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的仓库集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<Store> selectStoreByMap(HashMap<String, Object> map)
			throws Exception;
}
