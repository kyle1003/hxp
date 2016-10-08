package cn.hurry.service.goods;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.goods.GoodsTypeMapper;
import cn.hurry.data.mapper.user.UserMapper;
import cn.hurry.manage.goods.GoodsManage;
import cn.hurry.manage.goods.GoodsTypeManage;
import cn.hurry.po.goods.GoodsType;
@Service
public class GoodsTypeService {

	/**
	 * 删除商品类别
	 * 
	 * @param GoodsType
	 *            商品类别实例
	 * @throws Exception
	 *             删除失败异常
	 */
	public void deleteGoodsType(GoodsType goodsType) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GoodsTypeMapper goodsTypeMapper = session.getMapper(GoodsTypeMapper.class);
			if(GoodsManage.getGoodsByGoodsTypeId(goodsType.getId(),false).size()!=0){
				throw new Exception("该类别存在商品信息,不能删除!");
			}
			if(GoodsTypeManage.getGoodsTypeByPid(goodsType.getId(),false).size()!=0){
				throw new Exception("该类别存在子类别,不能删除!");
			}
			goodsTypeMapper.deleteGoodsType(goodsType);
			session.commit();
			GoodsTypeManage.removeGoodsType(goodsType);
		} finally {
			session.close();
		}
	}
	
	/**
	 * 添加商品类别
	 * 
	 * @param GoodsType
	 *            商品类别实例
	 * @throws Exception
	 *             添加失败异常
	 */
	public void insertGoodsType(GoodsType goodsType) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GoodsTypeMapper goodsTypeMapper = session.getMapper(GoodsTypeMapper.class);
			UserMapper userMapper = session.getMapper(UserMapper.class);
			GoodsType dbGoodsType = GoodsTypeManage.getGoodsTypeByName(goodsType.getName());
			if(dbGoodsType!=null){
				throw new Exception("类别名称已存在");
			}
			goodsTypeMapper.insertGoodsType(goodsType);
			goodsType.setId(userMapper.selectLastInsertId());
			session.commit();
			GoodsTypeManage.addGoodsType(goodsType);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据商品类别编号查询商品类别
	 * 
	 * @param id
	 *            商品类别编号
	 * @return 商品类别实例
	 * @throws Exception
	 *             查询失败异常
	 */
	public GoodsType selectGoodsTypeById(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GoodsTypeMapper goodsTypeMapper = session.getMapper(GoodsTypeMapper.class);
			return goodsTypeMapper.selectGoodsTypeById(id);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据map条件集合查询商品类别
	 * 
	 * @param map
	 *            条件集合
	 * @return 符合条件的商品类别集合
	 * @throws Exception
	 *             查询失败异常
	 */
	public List<GoodsType> selectGoodsTypeByMap(HashMap<String, Object> map)
			throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GoodsTypeMapper goodsTypeMapper = session.getMapper(GoodsTypeMapper.class);
			return goodsTypeMapper.selectGoodsTypeByMap(map);
		} finally {
			session.close();
		}
	}

	/**
	 * 修改商品类别
	 * 
	 * @param GoodsType
	 *            商品类别实例
	 * @throws Exception
	 *             修改失败异常
	 */
	public void updateGoodsType(GoodsType goodsType) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			GoodsTypeMapper goodsTypeMapper = session.getMapper(GoodsTypeMapper.class);
			GoodsType dbGoodsType = GoodsTypeManage.getGoodsTypeByName(goodsType.getName());
			if(dbGoodsType!=null&&dbGoodsType.getId()!=goodsType.getId()){
				throw new Exception("类别名称已存在");
			}
			goodsTypeMapper.updateGoodsType(goodsType);
			session.commit();
			GoodsTypeManage.addGoodsType(goodsType);
		} finally {
			session.close();
		}
	}

}
