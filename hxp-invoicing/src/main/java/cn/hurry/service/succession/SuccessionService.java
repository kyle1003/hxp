package cn.hurry.service.succession;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.succession.SuccessionInfoMapper;
import cn.hurry.data.mapper.succession.SuccessionMapper;
import cn.hurry.manage.succession.SuccessionInfoManage;
import cn.hurry.manage.succession.SuccessionManage;
import cn.hurry.po.succession.Succession;
import cn.hurry.po.succession.SuccessionInfo;

@Service
public class SuccessionService {

	public void insertSuccession(Succession succession) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SuccessionMapper successionMapper = session.getMapper(SuccessionMapper.class);
			succession.setSuccessionInfoId(SuccessionInfoManage.getToDaySuccessionInfo().getId());
			successionMapper.insertSuccession(succession);
			int id = successionMapper.selectLastInsertId();
			succession = successionMapper.selectSuccessionById(id);
			session.commit();
			SuccessionManage.put(succession);
		} finally {
			session.close();
		}
	}

	public Succession selectSuccessionById(int id) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SuccessionMapper successionMapper = session.getMapper(SuccessionMapper.class);
			return successionMapper.selectSuccessionById(id);
		} finally {
			session.clearCache();
			session.close();
		}
	}

	public List<Succession> selectSuccessionByMap(Map<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SuccessionMapper successionMapper = session.getMapper(SuccessionMapper.class);
			return successionMapper.selectSuccessionByMap(map);
		} finally {
			session.close();
		}
	}
	
	public int countSuccessionByMap(Map<String, Object> map) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SuccessionMapper successionMapper = session.getMapper(SuccessionMapper.class);
			return successionMapper.countSuccessionByMap(map);
		}catch (Exception e) {
			return 0;
		} finally {
			session.close();
		}
	}

	public void updateSuccession(Succession succession) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SuccessionMapper successionMapper = session.getMapper(SuccessionMapper.class);
			successionMapper.updateSuccession(succession);
			succession = successionMapper.selectSuccessionById(succession.getId());
			session.commit();
			SuccessionManage.put(succession);
		} finally {
			session.close();
		}
	}

	/**
	 * 交班
	 * 
	 * @param succession
	 *            接班信息
	 * @return Succession 返回交班人信息，为null 表示第一班
	 * @throws Exception
	 */
	public Succession doHandOver(Succession succession) throws Exception {
		SqlSession session = SessionFactory.openSession();
		Succession woringIng = SuccessionManage.getSuccessionByStatus(Succession.STATUS_WORKING);
		try {
			SuccessionInfoMapper successionInfoMapper = session.getMapper(SuccessionInfoMapper.class);
			SuccessionMapper successionMapper = session.getMapper(SuccessionMapper.class);
			if (woringIng == null || succession.getTakeOverUserId() != woringIng.getTakeOverUserId()) {
				throw new Exception("获取交班信息错误，交班失败");
			}
			SuccessionInfo toDayInfo = SuccessionInfoManage.getToDaySuccessionInfo();
			if (toDayInfo == null) {
				throw new Exception("获取开班信息失败");
			}
			if (toDayInfo.getStatus() == SuccessionInfo.status_settleing) {
				throw new Exception("正在交班，请勿重复提交");
			}
			toDayInfo.setStatus(SuccessionInfo.status_settleing);
			woringIng.setStatus(Succession.STATUS_WORKOVER_BUT_NOT_HANDOVER);
			woringIng.setHandOverTime(new Date());
			successionMapper.updateSuccession(woringIng);
			successionInfoMapper.updateSuccessionInfo(toDayInfo);
			toDayInfo = successionInfoMapper.selectSuccessionInfoById(toDayInfo.getId());
			woringIng = successionMapper.selectSuccessionById(woringIng.getId());
			session.commit();
			SuccessionInfoManage.put(toDayInfo);
			SuccessionManage.put(woringIng);
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
		return woringIng;
	}

	/**
	 * 接班，传入接班信息，自动获取交班人进行交接班
	 * 
	 * @param succession
	 *            接班信息
	 * @return Succession 返回交班人信息，为null 表示第一班
	 * @throws Exception
	 */
	public Succession doTakOver(Succession succession) throws Exception {
		SqlSession session = SessionFactory.openSession();
		Succession woringIng = SuccessionManage.getSuccessionByStatus(Succession.STATUS_WORKOVER_BUT_NOT_HANDOVER);
		try {
			SuccessionMapper successionMapper = session.getMapper(SuccessionMapper.class);
			SuccessionInfoMapper successionInfoMapper = session.getMapper(SuccessionInfoMapper.class);
			if (woringIng == null) {
				throw new Exception("获取交接班信息错误，接班失败");
			}
			SuccessionInfo toDayInfo = SuccessionInfoManage.getToDaySuccessionInfo();
			if (toDayInfo == null) {
				throw new Exception("获取开班信息失败");
			}
			if (toDayInfo.getStatus() == SuccessionInfo.status_working) {
				throw new Exception("还未交班，无法接班");
			}
			// 有接班人，设置状态为已交班
			woringIng.setStatus(Succession.STATUS_WORKOVER);
			successionMapper.updateSuccession(woringIng);
			succession.setHandOverSuccession(woringIng);
			succession.setHandOverSuccessionId(woringIng.getId());
			succession.setStatus(Succession.STATUS_WORKING);
			succession.setTakeOverTime(new Date());
			succession.setSuccessionInfoId(toDayInfo.getId());
			successionMapper.insertSuccession(succession);
			toDayInfo.setStatus(SuccessionInfo.status_working);
			successionInfoMapper.updateSuccessionInfo(toDayInfo);
			toDayInfo = successionInfoMapper.selectSuccessionInfoById(toDayInfo.getId());
			woringIng = successionMapper.selectSuccessionById(woringIng.getId());
			succession = successionMapper.selectSuccessionById(successionMapper.selectLastInsertId());
			session.commit();
			// 刷新缓存
			SuccessionInfoManage.put(toDayInfo);
			SuccessionManage.put(succession);
			SuccessionManage.put(woringIng);
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
		return woringIng;
	}

	public Succession doStartWork(Succession succession) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SuccessionMapper successionMapper = session.getMapper(SuccessionMapper.class);
			SuccessionInfo toDayInfo = SuccessionInfoManage.getToDaySuccessionInfo();
			SuccessionInfoMapper successionInfoMapper = session.getMapper(SuccessionInfoMapper.class);
			if (toDayInfo == null) {
				throw new Exception("获取开班信息失败");
			}
			if (toDayInfo.getStatus() != SuccessionInfo.status_notStart) {
				throw new Exception("今天已开班，不能重复开班");
			}
			succession.setSuccessionInfoId(toDayInfo.getId());
			succession.setStatus(Succession.STATUS_WORKING);
			succession.setTakeOverTime(new Date());
			successionMapper.insertSuccession(succession);
			succession = successionMapper.selectSuccessionById(successionMapper.selectLastInsertId());
			toDayInfo.setStatus(SuccessionInfo.status_working);
			successionInfoMapper.updateSuccessionInfo(toDayInfo);
			toDayInfo = successionInfoMapper.selectSuccessionInfoById(toDayInfo.getId());
			session.commit();
			SuccessionManage.put(succession);
			SuccessionInfoManage.put(toDayInfo);
			return succession;
		} finally {
			session.close();
		}
	}

	/**
	 * 下班并日结
	 * 
	 * @return 所有交接班信息
	 * @throws Exception
	 */
	public void doOver() throws Exception {
		SqlSession session = SessionFactory.openSession();
		Succession woringIng = SuccessionManage.getSuccessionByStatus(Succession.STATUS_WORKING);
		try {
			SuccessionInfo toDayInfo = SuccessionInfoManage.getToDaySuccessionInfo();
			SuccessionInfoMapper successionInfoMapper = session.getMapper(SuccessionInfoMapper.class);
			SuccessionMapper successionMapper = session.getMapper(SuccessionMapper.class);
			if (toDayInfo == null) {
				throw new Exception("获取开班信息失败");
			}
			if (toDayInfo.getStatus() != SuccessionInfo.status_working) {
				throw new Exception("日结信息错误:status " + toDayInfo.getStatus());
			}
			toDayInfo.setStatus(SuccessionInfo.status_isSettle);
			woringIng.setStatus(Succession.STATUS_WORKOVER);
			successionInfoMapper.updateSuccessionInfo(toDayInfo);
			successionMapper.updateSuccession(woringIng);
			session.commit();
			SuccessionManage.put(woringIng);
			SuccessionInfoManage.init();
		} catch (Exception e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
}
