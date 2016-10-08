package cn.hurry.service.unit;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.unit.NormsMapper;
import cn.hurry.data.mapper.user.UserMapper;
import cn.hurry.manage.unit.NormsManage;
import cn.hurry.po.unit.Norms;

@Service
public class NormsService {

	/**
	 * 删除规格
	 * 
	 * @param Norms
	 *            规格实例
	 * @throws Exception
	 *             删除失败异常
	 */
	public void deleteNorms(Norms norms) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			NormsMapper normsMapper = session.getMapper(NormsMapper.class);
			normsMapper.deleteNorms(norms);
			session.commit();
			NormsManage.removeNorms(norms);
		} finally {
			session.close();
		}
	}

	/**
	 * 添加规格
	 * 
	 * @param Norms
	 *            规格实例
	 * @throws Exception
	 *             添加失败异常
	 */
	public int insertNorms(Norms norms) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			NormsMapper normsMapper = session.getMapper(NormsMapper.class);
			UserMapper userMapper = session.getMapper(UserMapper.class);
			Norms dbNorms = NormsManage.getNormsByName(norms.getName());
			if(dbNorms!=null){
				throw new Exception("规格名称已存在");
			}
			normsMapper.insertNorms(norms);
			norms.setId(userMapper.selectLastInsertId());
			session.commit();
			NormsManage.addNorms(norms);
		} finally {
			session.close();
		}
		return norms.getId();
	}

	/**
	 * 根据规格编号查询规格
	 * 
	 * @param id
	 *            规格编号
	 * @return 规格实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public Norms selectNormsById(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			NormsMapper normsMapper = session.getMapper(NormsMapper.class);
			return normsMapper.selectNormsById(id);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据map条件集合查询规格
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的规格集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<Norms> selectNormsByMap(HashMap<String, Object> map)
			throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			NormsMapper normsMapper = session.getMapper(NormsMapper.class);
			return normsMapper.selectNormsByMap(map);
		} finally {
			session.close();
		}
	}

	/**
	 * 修改规格
	 * 
	 * @param Norms
	 *            规格实例
	 * @throws Exception
	 *             修改失败异常
	 */
	public void updateNorms(Norms norms) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			NormsMapper normsMapper = session.getMapper(NormsMapper.class);
			Norms dbNorms = NormsManage.getNormsByName(norms.getName());
			if(dbNorms!=null&&dbNorms.getId()!=norms.getId()){
				throw new Exception("规格名称已存在");
			}
			normsMapper.updateNorms(norms);
			session.commit();
			NormsManage.addNorms(norms);
		} finally {
			session.close();
		}
	}

}
