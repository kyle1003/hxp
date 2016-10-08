package cn.hurry.data.mapper.operate;

import java.util.ArrayList;
import java.util.List;

import cn.hurry.po.operate.Operate;

public interface OperateMapper {
	public List<Operate> selectOperateByManagerGroupeId(Integer managerGroupId) throws Exception;

	public ArrayList<Operate> selectAllOperaMenuData() throws Exception;

}
