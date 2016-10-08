package cn.hurry.data.mapper.succession;

import java.util.List;
import java.util.Map;

import cn.hurry.po.succession.SuccessionInfo;

public interface SuccessionInfoMapper {
	public int insertSuccessionInfo(SuccessionInfo successionInfo) throws Exception;

	public int updateSuccessionInfo(SuccessionInfo successionInfo) throws Exception;

	public int deleteSuccessionInfo(SuccessionInfo successionInfo) throws Exception;

	public SuccessionInfo selectSuccessionInfoById(int id) throws Exception;
	
	public List<SuccessionInfo> selectSuccessionInfoByMap(Map<String, Object> map) throws Exception;

	public int selectLastInsertId() throws Exception;

	public int countSuccessionInfoByMap(Map<String, Object> map)throws Exception;
}
