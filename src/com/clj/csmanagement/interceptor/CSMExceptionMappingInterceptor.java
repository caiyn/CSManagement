package com.clj.csmanagement.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.clj.csmanagement.exception.CSMException;
import com.clj.csmanagement.exception.SystemException;
import com.clj.csmanagement.util.ResourceUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;

public class CSMExceptionMappingInterceptor extends
		ExceptionMappingInterceptor implements LocaleProvider {

	private static final long serialVersionUID = -7082765606275230801L;

	/**
	 * 异常错误信息字符串
	 */
	private static final String ERROR_MSG_KEY = "error_msg";
	private static final String ERROR_MSG_STACK = "error_stack";
	private static final String BIZ_ERROR_CODE_DEFAULT = "业务操作失败。";
	private static final String SYSTEM_ERROR_CODE_DEFAULT = "业务操作失败。";
	private static final String ERROR_MSG_BUNDLE_KEY = "errorcodes-resource";
	
	/**
	 * 异常日志
	 */
	private static final String ESS_EXCEPTION_LOGGER = "EPPException";
	private final transient ResourceUtils resourceUtils = ResourceUtils.getInstance();
	
	/**
	 * 
	 * <p>[重写拦截方法，增加处理机制]</p>
	 * 
	 * @see com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	public String intercept(ActionInvocation invocation)throws Exception{
		String result = null;
		try{
			result = invocation.invoke();
		}catch(Exception e){
			if(this.logEnabled){
				this.handleLogging(e);
			}
			Exception ex = null;
			// 如果为ajax提交，则将错误信息写到response中, 并设置状态值
			if(this.isAjaxSubmit()){
				handleAjaxException(e);
				result = "ajax_error";
			}else if(e instanceof CSMException){
				ex = handleEPPException((CSMException)e);
				String[] callFunction = (String[])invocation.getInvocationContext().getParameters().get("callFunction");
				if(callFunction != null && callFunction.length == 1){
					ServletActionContext.getRequest().setAttribute("callFunction", callFunction[0]);
					CSMException bizE = (CSMException)e;
					ServletActionContext.getRequest().setAttribute("errorMessage", getBizExceptionStackBottomMessage(bizE));
					ServletActionContext.getRequest().setAttribute("IsBizException", "true");
				}
				result = "global_error";
			}else if(e instanceof SystemException){
				ex = handleSystemException((SystemException)e);
				String [] callFunction = (String[])invocation.getInvocationContext().getParameters().get("callFunction");
				if(callFunction != null && callFunction.length == 1){
					ServletActionContext.getRequest().setAttribute("callFunction", callFunction[0]);
					ServletActionContext.getRequest().setAttribute("errorMessage", ex.getMessage());
					ServletActionContext.getRequest().setAttribute("IsBizException", "false");
				}
				result = "global_error";
			}else{
				ex = handleOtherException(e);
				String [] callFunction = (String[])invocation.getInvocationContext().getParameters().get("callFunction");
				if(callFunction != null && callFunction.length == 1){
					ServletActionContext.getRequest().setAttribute("callFunction", callFunction[0]);
					ServletActionContext.getRequest().setAttribute("errorMessage", ex.getMessage());
					ServletActionContext.getRequest().setAttribute("IsBizException", "false");
				}
				result = "global_error";
			}
			publishException(invocation, new ExceptionHolder(ex == null ? e : ex));
			markLog(e);
		}
		return result;
	}
	/**
	 * 
	 * <p>[判断是否为ajax提交]</p>
	 * 
	 * @return
	 * @return: boolean
	 */
	private boolean isAjaxSubmit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String reHesder = request.getHeader("x-requested-with");
		if (reHesder != null && reHesder.equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * <p>[处理Ajax异常]</p>
	 * 
	 * @param e
	 * @throws Exception
	 * @return: void
	 */
	private void handleAjaxException(Exception e) throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
		String errorMsg = null;
		String errorCode = e.getMessage();
		if(e instanceof CSMException){
			errorMsg = this.getBizExceptionMessageStack((CSMException)e);
		}else if(e instanceof SystemException){
			errorMsg = new JSONObject().element(ERROR_MSG_KEY, getText(errorCode,SYSTEM_ERROR_CODE_DEFAULT)).toString();
		}else{
			errorMsg = new JSONObject().element(ERROR_MSG_KEY, getText(SYSTEM_ERROR_CODE_DEFAULT)).toString();
		}
		PrintWriter pw = response.getWriter();
		pw.write(errorMsg);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 
	 * <p>处理业务异常 EPPException</p>
	 * 
	 * @param e
	 * @return
	 * @throws Exception
	 * @return: Exception
	 */
	private Exception handleEPPException(CSMException e) throws Exception {
		String errorMsg = getText(e.getMessage(), e.getMessageParameters(), BIZ_ERROR_CODE_DEFAULT);
		return new CSMException(errorMsg, e);
	}
	
	/**
	 * 
	 * <p>[处理系统异常SystemException]</p>
	 * 
	 * @param e
	 * @return
	 * @throws Exception
	 * @return: Exception
	 */
	private Exception handleSystemException(SystemException e) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
		return new SystemException(getText(e.getMessage(), SYSTEM_ERROR_CODE_DEFAULT), e);
	}
	
	/**
	 * 
	 * <p>[处理其他类型异常]</p>
	 * 
	 * @param e
	 * @return
	 * @throws Exception
	 * @return: Exception
	 */
	private Exception handleOtherException(Exception e) throws Exception {
		return new SystemException(getText(SYSTEM_ERROR_CODE_DEFAULT), e);
	}
	
	/**
	 * 
	 * <p>[获得业务异常提示信息栈]</p>
	 * 
	 * @param ex
	 * @return
	 * @return: String
	 */
	private String getBizExceptionMessageStack(CSMException ex){
		String msg = ex.getMessage();
		String [] params = ex.getMessageParameters();
		JSONObject retValue = new JSONObject();
		retValue.element(ERROR_MSG_KEY, getText(msg, params, BIZ_ERROR_CODE_DEFAULT));
		if(ex.getCause() != null && ex.getCause() instanceof CSMException){
			retValue.element(ERROR_MSG_STACK, getBizExceptionMessageStack((CSMException)ex.getCause()));
		}
		return retValue.toString();
	}
	
	/**
	 * 
	 * <p>获得业务异常栈中最底层提示信息</p>
	 * 
	 * @param ex
	 * @return
	 * @return: String
	 */
	private String getBizExceptionStackBottomMessage(CSMException ex){
		String retValue = null;
		CSMException e = ex;
		while(true){
			String msg = e.getMessage();
			String[] params = e.getMessageParameters();
			retValue = getText(msg,params,BIZ_ERROR_CODE_DEFAULT);
			if(e.getCause() != null && e instanceof CSMException){
				e = (CSMException)e.getCause();
			}else{
				break;
			}
		}
		return retValue;
	}
	
	/**
	 * 
	 * <p>获得业务异常提示信息栈</p>
	 * 
	 * @param textKey
	 * @return
	 * @return: String
	 */
	private String getText(String textKey) {
		return getText(null, null, textKey);
	}
	
	/**
	 * 
	 * <p>根据错误代码获得错误信息</p>
	 * 
	 * @param textKey
	 * @param defalutMsg
	 * @return
	 * @return: String
	 */
	private String getText(String textKey, String defalutMsg) {
		return getText(textKey, null, defalutMsg);
	}
	
	/**
	 * 
	 * <p>根据错误代码获得错误信息</p>
	 * 
	 * @param textkey
	 * @param params
	 * @param defalutMsg
	 * @return
	 * @return: String
	 */
	private String getText(String textkey,String[]params,String defalutMsg){
		String msg = null;
		try{
			if(!"".equals(StringUtils.defaultString(textkey,"")) && params != null){
				msg = resourceUtils.getMessage(ERROR_MSG_BUNDLE_KEY,textkey, params);
			}else if(!"".equals(StringUtils.defaultString(textkey, "")) && params == null){
				msg = resourceUtils.getMessage(ERROR_MSG_BUNDLE_KEY, textkey);
			}
			if(msg == null || "".equals(msg)){
				msg =  defalutMsg;
			}
			return msg;
		}catch(Exception ex){
			markLog(ex);
			Logger.getLogger(ESS_EXCEPTION_LOGGER).error("系统查询错误信息失败！");
			return defalutMsg;
		}
	}
	
	/**
	 * 
	 * <p>记录日志</p>
	 * 
	 * @param ex
	 * @return: void
	 */
	private void markLog(Exception ex){
		// log4j日志记录操作
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		Logger.getLogger(ESS_EXCEPTION_LOGGER).error(sw.getBuffer().toString());
	}
	
	public Locale getLocale() {
		return ActionContext.getContext().getLocale();
	}


}
