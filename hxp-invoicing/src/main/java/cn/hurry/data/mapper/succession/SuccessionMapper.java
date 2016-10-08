package cn.hurry.data.mapper.succession;

import java.util.List;
import java.util.Map;

import cn.hurry.po.succession.Succession;

public interface SuccessionMapper {
	public int insertSuccession(Succession succession) throws Exception;

	public int updateSuccession(Succession succession) throws Exception;

	public int deleteSuccession(Succession succession) throws Exception;

	public Succession selectSuccessionById(int id) throws Exception;
	
	public List<Succession> selectSuccessionByMap(Map<String, Object> map) throws Exception;

	public int selectLastInsertId() throws Exception;

	public int countSuccessionByMap(Map<String, Object> map)throws Exception;
}
