package cn.hurry.util;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

/**
 * 定时器工具<br>
 * 
 * @author ZhouHao
 * 
 */
public class TimerUtils {

	private static Logger logger = LoggerFactory.getLogger(TimerUtils.class);
	/**
	 * 定时器
	 */
	private static final java.util.Timer TIMER = new java.util.Timer();

	private Scheduler scheduler = null;

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * 任务列表
	 */
	private static final HashMap<String, MyTask> TASK_MAP = new HashMap<String, MyTask>();

	/**
	 * 添加定时任务
	 * 
	 * @param myTask
	 * @throws ParseException
	 * @throws SchedulerException
	 */
	public static void addTask(MyTask myTask) {
		logger.info("添加任务:" + myTask.getName());
		synchronized (TIMER) {
			if (!myTask.isLoop()) {
				TIMER.schedule(myTask, myTask.getExecuteTime());
			} else {
				TIMER.scheduleAtFixedRate(myTask, myTask.getExecuteTime(), myTask.getSpaceTime());
			}
		}
		synchronized (TASK_MAP) {
			TASK_MAP.put(myTask.getName(), myTask);
		}
		// JobDetail jobDetail = new JobDetail("", Scheduler.DEFAULT_GROUP, myTask.getExecClass());
		// CronTrigger trigger;
		// try {
		// trigger = new CronTrigger("Triggername1", Scheduler.DEFAULT_GROUP, "0 0/5 * * * ?");
		// scheduler.scheduleJob(jobDetail,trigger);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		logger.info("添加任务:" + myTask.getName() + "成功");
		
	}

	public static void main(String[] args) {
		//开始执行时间 当前时间1秒后
		java.util.Date d=new java.util.Date(System.currentTimeMillis()+1000);
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				System.out.println(123);
			}
		}, d,1000); //1000为间隔时间 不要此参数为只执行一次
	}
	
	/**
	 * 批量添加定时任务
	 * 
	 * @param myTask
	 */
	public static void addTask(List<MyTask> myTask) {
		for (MyTask task : myTask) {
			addTask(task);
		}
	}

	/**
	 * 获取任务
	 * 
	 * @param name
	 *            任务名称
	 * @return
	 */
	public static MyTask getTask(String name) {
		synchronized (TASK_MAP) {
			return TASK_MAP.get(name);
		}
	}

	/**
	 * 取消任务
	 * 
	 * @param name
	 *            任务名
	 */
	public static void cancelTask(String name) {
		getTask(name).cancel();
	}

	/**
	 * 停止定时器,清空任务
	 */
	public static void stopTimer() {
		logger.info("清空任务");
		TIMER.cancel();
		TASK_MAP.clear();
	}

	/**
	 * 销毁此计划任务
	 * 
	 * @param name
	 *            任务名
	 */
	public static void removeTask(String name) {
		synchronized (TASK_MAP) {
			TASK_MAP.remove(name);
		}
	}

	/**
	 * 修改任务
	 * 
	 * @param name
	 *            任务名
	 */
	public static void updateTask(MyTask myTask) {
		cancelTask(myTask.getName());
		addTask(myTask);
	}

	/**
	 * 修改任务执行时间
	 * 
	 * @param taskName
	 *            任务名称
	 * @param date
	 *            任务时间
	 */
	public static void updateTaskTime(String taskName, Date date) {
		MyTask myTask = getTask(taskName);
		if (myTask == null)
			return;
		myTask.setExecuteTime(date);
		updateTask(myTask);
	}
}
