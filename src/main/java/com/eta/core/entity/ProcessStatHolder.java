package com.eta.core.entity;
import java.util.List;


/**
 * Descrip:相应处理部分的处理状态类，该状态类主要涉及到整个处理过程中的相关异常数据的保存以及处理结果
 */
public interface ProcessStatHolder {
	
	/**
	 * 处理状态，成功
	 */
	static final String RESULT_STAT_SUCCESS = "SUCCESS";
	
	/**
	 * 处理结果，警告
	 */
	static final String RESULT_STAT_WARN = "WARN";
	
	
	/**
	 * 处理结果，未登录
	 */
	static final String RESULT_STAT_NOT_LOGIN = "NOT_LOGIN";
	
	
	/**
	 * 处理结果，有关联资源，此时返回数据为查询到的关联资源
	 */
	static final String RESULT_STAT_REL_BUZ = "REL_BUZ";
	
	
	/**
	 * 处理结果，当前资源业务异常，返回当前异常数据自身
	 */
	static final String RESULT_STAT_BUZ_EXCEPTION = "BUZ_EXCEPTION";
	/**
	 * 处理结果，当前资源参数异常，返回异常参数信息
	 */
	static final String RESULT_STAT_PARAMETER_EXCEPTION = "PARAMETER_EXCEPTION";
	
	/**
	 * 处理结果，错误
	 */
	static final String RESULT_STAT_ERROR = "ERROR";
	
	/**
	 * 增加当前处理节点的需要被捕获的数据
	 * @param data
	 */
	void addResultData(Object data);
		
	/**
	 * 为当前处理节点增加多条结果数据
	 * @param data
	 */
	@SuppressWarnings("rawtypes")
	<T extends List> void addResultData(T data);
	
	/**
	 * 设置处理流程中每个处理状态的处理结果
	 * @param flag
	 */
	void setProcessFlag(String flag);
	
	
	/**
	 * 设置当前信息
	 * @param mess
	 */
	void setMess(String mess);
	
	/**
	 * 设置业务处理结果
	 * @param flag
	 * @param mess
	 * @param data
	 */
	void setProcessResult(String flag, String mess, Object data);
	
	/**
	 * 获取当前处理流程中已完成的处理结果状态
	 * @return
	 */
	String getResultFlag();
	
	
	/**
	 * 返回处理过程中所有消息序列
	 * @return
	 */
	List<String> getMess();
	
	/**
	 * 获取当前处理流程中所有处理结果数据
	 * @return
	 */
	List<Object> getResultData();
	
	/**
	 * 清除当前处理流程中的所有处理结果信息，包括状态和相关数据
	 */
	void clearResultStatData();

}