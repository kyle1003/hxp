package cn.hurry.data.mapper.order.giveback;

import java.util.List;
import java.util.Map;

import cn.hurry.po.order.giveback.GiveBackInfo;

public interface GiveBackInfoMapper {
	public int insertGiveBackInfo(GiveBackInfo giveBackInfo) throws Exception;

	public int updateGiveBackInfo(GiveBackInfo giveBackInfo) throws Exception;

	public int deleteGiveBackInfo(GiveBackInfo giveBackInfo) throws Exception;

	public GiveBackInfo selectGiveBackInfoById(int id) throws Exception;

	public List<GiveBackInfo> selectGiveBackInfoByMap(Map<String, Object> map) throws Exception;

	public int countGiveBackInfoByMap(Map<String, Object> map) throws Exception;

}
