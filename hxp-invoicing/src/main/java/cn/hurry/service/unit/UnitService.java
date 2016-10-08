package cn.hurry.service.unit;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.unit.UnitMapper;
import cn.hurry.data.mapper.user.UserMapper;
import cn.hurry.manage.unit.UnitManage;
import cn.hurry.po.unit.Unit;

@Service
public class UnitService {

	/**
	 * 删除单位
	 * 
	 * @param Unit
	 *            单位实例
	 * @throws Exception
	 *             删除失败异常
	 */
	public void deleteUnit(Unit unit) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UnitMapper unitMapper = session.getMapper(UnitMapper.class);
			unitMapper.deleteUnit(unit);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * 添加单位
	 * 
	 * @param Unit
	 *            单位实例
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertUnit(Unit unit) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UnitMapper unitMapper = session.getMapper(UnitMapper.class);
			UserMapper userMapper = session.getMapper(UserMapper.class);
			Unit dbUnit = UnitManage.getUnitByName(unit.getName());
			if(dbUnit!=null){
				throw new Exception("单位名称已存在！");
			}
			unitMapper.insertUnit(unit);
			unit.setId(userMapper.selectLastInsertId());
			session.commit();
			UnitManage.addUnit(unit);
		} finally {
			session.close();
		}
		return unit.getId();
	}

	/**
	 * 根据单位编号查询单位
	 * 
	 * @param id
	 *            单位编号
	 * @return 单位实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Unit selectUnitById(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UnitMapper unitMapper = session.getMapper(UnitMapper.class);
			return unitMapper.selectUnitById(id);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据map条件集合查询单位
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的单位集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<Unit> selectUnitByMap(HashMap<String, Object> map)
			throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UnitMapper unitMapper = session.getMapper(UnitMapper.class);
			return unitMapper.selectUnitByMap(map);
		} finally {
			session.close();
		}
	}

	/**
	 * 修改单位
	 * 
	 * @param Unit
	 *            单位实例
	 * @throws Exception
	 *             修改失败异常
	 */
	public void updateUnit(Unit unit) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			UnitMapper unitMapper = session.getMapper(UnitMapper.class);
			Unit dbUnit = UnitManage.getUnitByName(unit.getName());
			if(dbUnit!=null&&dbUnit.getId()!=unit.getId()){
				throw new Exception("单位名称已存在！");
			}
			unitMapper.updateUnit(unit);
			session.commit();
			UnitManage.addUnit(unit);
		} finally {
			session.close();
		}
	}
	

}
