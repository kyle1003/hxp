package cn.hurry.data.mapper.crm;

import java.util.List;
import java.util.Map;

import cn.hurry.po.crm.ClientInfo;

/**
 * 客户信息数据映射接口
 * 
 * @author
 * 
 */
public interface ClientInfoMapper {
	public int insertClientInfo(ClientInfo info) throws Exception;

	public int deleteClientInfo(ClientInfo info) throws Exception;

	public int updateClientInfo(ClientInfo info) throws Exception;

	public ClientInfo selectClientById(int id) throws Exception;

	public List<ClientInfo> selectClientByMap(Map<String, Object> obj) throws Exception;

	public int countClientByMap(Map<String, Object> obj) throws Exception;

}
