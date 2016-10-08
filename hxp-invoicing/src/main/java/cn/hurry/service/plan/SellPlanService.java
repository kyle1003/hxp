package cn.hurry.service.plan;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hurry.data.SessionFactory;
import cn.hurry.data.mapper.plan.SellPlanMapper;

@Service
public class SellPlanService {

	public byte selectSellPlan() throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SellPlanMapper sellPlanMapper = session.getMapper(SellPlanMapper.class);
			return sellPlanMapper.selectSellPlan();
		} finally {
			session.close();
		}
	}

	public void updateSellPlan(byte type) throws Exception {
		SqlSession session = SessionFactory.openSession();
		try {
			SellPlanMapper sellPlanMapper = session.getMapper(SellPlanMapper.class);
			sellPlanMapper.updateSellPlan(type);
			session.commit();
		} finally {
			session.close();
		}
	}

}
