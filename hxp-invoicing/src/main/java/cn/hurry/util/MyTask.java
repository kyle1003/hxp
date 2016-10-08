package cn.hurry.util;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 计划任务
 * 
 * @author ZhouHao
 * 
 */
public class MyTask extends TimerTask {
	private static Logger logger = LoggerFactory.getLogger(MyTask.class);

	/**
	 * 任务名称编号
	 */
	private String name;

	/**
	 * 执行的方法名
	 */
	private String methodName;

	/**
	 * 要执行的类
	 */
	private Class<?> execClass;

	/**
	 * 是否带有参数
	 */
	private boolean hasParam;

	/**
	 * 是否已经执行
	 */
	private boolean executed;

	/**
	 * 循环执行
	 */
	private boolean loop = false;

	/**
	 * 参数列表
	 */
	private Object params[];

	/**
	 * 参数类型
	 */
	private Class<?> paramTypes[];

	/**
	 * 执行时间
	 */
	private Date executeTime;

	/**
	 * 间隔时间
	 */
	private long spaceTime;

	public Class<?> getExecClass() {
		return execClass;
	}

	public void setExecClass(Class<?> execClass) {
		this.execClass = execClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHasParam() {
		return hasParam;
	}

	public void setHasParam(boolean hasParam) {
		this.hasParam = hasParam;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public long getSpaceTime() {
		return spaceTime;
	}

	public void setSpaceTime(long spaceTime) {
		this.spaceTime = spaceTime;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public Class<?>[] getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(Class<?>[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	@Override
	public void run() {
		Class<?> class1 = this.getExecClass();
		logger.info("执行任务" + getName() + " \t" + class1.getName() + "." + methodName);
		try {
			Method method = null;
			if (hasParam) {
				method = class1.getMethod(methodName);
				method.invoke(class1.newInstance());
			} else {
				method = class1.getMethod(methodName, paramTypes);
				method.invoke(class1.newInstance(), params);
			}
			if (!loop) {// 如果不循环执行,则销毁该任务(任务池中)
				TimerUtils.cancelTask(name);
			}
			logger.info("执行任务完成");
		} catch (Exception e) {
			logger.error("执行任务失败", e);
		}
	}

	@Override
	public boolean cancel() {
		TimerUtils.removeTask(name);
		return super.cancel();
	}

}
