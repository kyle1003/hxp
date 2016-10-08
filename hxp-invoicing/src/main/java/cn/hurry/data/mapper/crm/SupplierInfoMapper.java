package cn.hurry.data.mapper.crm;

import java.util.List;
import java.util.Map;

import cn.hurry.po.crm.SupplierInfo;

/**
 * 客户信息数据映射接口
 * 
 * @author
 * 
 */
public interface SupplierInfoMapper {
	public int insertSupplierInfo(SupplierInfo info) throws Exception;

	public int deleteSupplierInfo(SupplierInfo info) throws Exception;

	public int updateSupplierInfo(SupplierInfo info) throws Exception;

	public SupplierInfo selectSupplierById(int id) throws Exception;

	public List<SupplierInfo> selectSupplierByMap(Map<String, Object> obj) throws Exception;

	public int countSupplierByMap(Map<String, Object> obj) throws Exception;

}
