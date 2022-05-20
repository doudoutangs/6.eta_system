package com.eta.core.entity;


/**
 * Descrip:处理结果类，主要向前台返回后台相关的处理信息
 */
public class ProcessResult implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public final static String ERROR = ProcessStatHolder.RESULT_STAT_ERROR;
	
	public final static String SUCCESS = ProcessStatHolder.RESULT_STAT_SUCCESS;

	public final static String BUZ_EXCEPTION = ProcessStatHolder.RESULT_STAT_BUZ_EXCEPTION;
	
	public final static String SHOW_SQL = "SHOW_SQL";
	
	public final static String WARN = ProcessStatHolder.RESULT_STAT_WARN;
	
	/**
	 * 处理结果状态
	 */
	private String resultStat;
	
	/**
	 * 处理结果返回信息
	 */
	private String mess;
	
	/**
	 * 处理结果回调函数
	 */
	private String callBack;
	
	/**
	 *处理完成返回数据 
	 */
	private Object data;

	private String redirectURL;

	public ProcessResult() {
		this.resultStat = SUCCESS;
	}
	
	public ProcessResult(String resultStat, String mess){
		this.resultStat = resultStat;
		this.mess = mess;
	}

	
	public ProcessResult(String resultStat){
		this.resultStat = resultStat;
	}
	
	public ProcessResult(String resultStat, String mess, Object data){
		this.resultStat = resultStat;
		this.mess = mess;
		this.data = data;
	}

	public ProcessResult(Object data){
		this.resultStat = SUCCESS;
		this.data = data;
	}

	public String getResultStat() {
		return resultStat;
	}
	
	

	public void setResultStat(String resultStat) {
		this.resultStat = resultStat;
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	@Override
	public String toString() {
		return "ProcessResult{" +
				"resultStat='" + resultStat + '\'' +
				", mess='" + mess + '\'' +
				", callBack='" + callBack + '\'' +
				", data=" + data +
				", redirectURL='" + redirectURL + '\'' +
				'}';
	}
}

