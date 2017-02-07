package com.clj.csmanagement.interceptor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.NoParameters;
import com.opensymphony.xwork2.interceptor.ParametersInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.reflection.ReflectionContextState;

public class CSMParametersInterceptor extends ParametersInterceptor {

	private static final long serialVersionUID = 5952734347734993969L;

	/**
	 * 日志处理
	 */
	private static final Logger LOG = Logger.getLogger(CSMParametersInterceptor.class);
	
	/**
	 * 
	 * <p>[增加处理传递的JSON类型的数据]</p>
	 * 
	 * @see com.opensymphony.xwork2.interceptor.ParametersInterceptor#doIntercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	public String doIntercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		HttpServletRequest request = ServletActionContext.getRequest();
		if (!(action instanceof NoParameters)) {
			ActionContext ac = invocation.getInvocationContext();
			final Map parameters = ac.getParameters();
			if (LOG.isDebugEnabled()) {
				LOG.debug("Setting params " + getParameterLogMap(parameters));
			}

			if (parameters != null) {
				Map contextMap = ac.getContextMap();
				try {
					ReflectionContextState.setCreatingNullObjects(contextMap, true);
					ReflectionContextState.setDenyMethodExecution(contextMap, true);
					ReflectionContextState.setReportingConversionErrors(contextMap, true);

					ValueStack stack = ac.getValueStack();
					// 判断HTTP HEADER是否为JSON数据
					if (isAjaxRequest(request)) {
						this.setParameters(action, stack, parameters);
					} else {
						super.setParameters(action, stack, parameters);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					ReflectionContextState.setCreatingNullObjects(contextMap, false);
					ReflectionContextState.setDenyMethodExecution(contextMap, false);
					ReflectionContextState.setReportingConversionErrors(contextMap, false);
				}
			}
		}
		return invocation.invoke();
	}
	
	/**
	 * 
	 * <p>[判断是否是Ajax请求]</p>
	 * 
	 * @param request
	 * @return
	 * @return: boolean
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String reHesder = request.getHeader("x-requested-with");
		if (reHesder != null && reHesder.equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * <p>[将参数添加到valueStack中]</p>
	 * 
	 * @see com.opensymphony.xwork2.interceptor.ParametersInterceptor#setParameters(java.lang.Object, com.opensymphony.xwork2.util.ValueStack, java.util.Map)
	 */
	protected void setParameters(Object action, ValueStack stack, final Map parameters) {
		Map target = new HashMap();
		target.putAll(parameters);
		Set s = target.entrySet();
		Iterator it = s.iterator();
		// 循环取得所有的参数
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String[] tempstr = null;
			if (!(entry.getValue() instanceof String[]))
				continue;
			tempstr = (String[]) entry.getValue();
			JSONObject value = null;
			JSONArray arrValue = null;
			// 如果前台传递值不为空
			if (tempstr != null) {
				// 如果传递的值个数为一个元素
				if (tempstr.length == 1) {
					// 如果该数据类型为JSON数据，则解析到parameters中
					if (isJSONObject(tempstr[0])) {
						// 实例化一个JSONObject
						value = JSONObject.fromObject(tempstr[0]);
						// 并将JSON中的值遍历取出来，放到Map中，由super.setParameters统一处理
						parseJSONParameters(String.valueOf(entry.getKey()), value, parameters);
//						parameters.remove(entry.getKey());
					}
					// 如果该数据类型为数组形式的字符串，则解析到parameters中，如：'[xxxxx,xxxx]'类型的值
					else if (isJSONArray(tempstr[0])) {
						arrValue = JSONArray.fromObject(tempstr[0]);
						parseJSONArrayParameters(String.valueOf(entry.getKey()), arrValue, parameters);
						if (arrValue.size() > 0 && arrValue.get(0) instanceof String) {
							try {
								Type type = action.getClass().getDeclaredField(entry.getKey().toString()).getGenericType();
								if (type.getClass().isArray()) {
									parameters.put(entry.getKey(), arrValue.toArray(new String[0]));
								} else if (type.getClass().isAssignableFrom(List.class)) {
									parameters.put(entry.getKey(), new ArrayList(arrValue));
								}
								continue;
							}catch (Exception e) {
							}
						}
//						parameters.remove(entry.getKey());
					}
				}
				// 如果传递的元素为多个元素
				else if (tempstr.length > 1) {
					for (int i = 0; i < tempstr.length; i++) {
						// 如果该数据类型为JSON数据，则解析到parameters中
						if (isJSONObject(tempstr[i])) {
							value = JSONObject.fromObject(tempstr[i]);
							parseJSONParameters(String.valueOf(entry.getKey()) + "[" + i + "]", value, parameters);
//							parameters.remove(entry.getKey());
						}
						// 如果该数据类型为数组形式的字符串，则解析到parameters中，如：'[xxxxx,xxxx]'类型的值
						else if (isJSONArray(tempstr[i])) {
							arrValue = JSONArray.fromObject(tempstr[i]);
							parseJSONArrayParameters(String.valueOf(entry.getKey()) + "[" + i + "]", arrValue,
									parameters);
//							parameters.remove(entry.getKey());
						}
					}
				}
			}
		}
		super.setParameters(action, stack, parameters);
	}
	/**
	 * 
	 * <p>[解析JSON数据，并将解析出来的JSON中的值放到parameters中，由super.setParameters统一处理]</p>
	 * 
	 * @param objKey
	 * @param obj
	 * @param parameters
	 * @return: void
	 */
	private void parseJSONArrayParameters(String objKey, JSONArray obj, Map parameters) {

		for (int j = 0; j < obj.size(); j++) {
			// 如果数组形式的字符串里的元素类型为JSON类型的数据
			if (isJSONObject(String.valueOf(obj.get(j)))) {
				JSONObject joElement = obj.getJSONObject(j);
				parseJSONParameters(objKey + "[" + j + "]", joElement, parameters);
			}
			// 如果数组形式的字段串里的元素类型为数组类型的数据
			else if (isJSONArray(String.valueOf(obj.get(j)))) {
				JSONArray joElement = obj.getJSONArray(j);
				parseJSONArrayParameters(objKey + "[" + j + "]", joElement, parameters);
			}
			// 如果数组形式的字符串里的元素为简单类型，则将数组中的值全部放入parameters中
			else {
				parameters.put(objKey, obj.toArray());
			}
		}
	}
	
	/**
	 * 
	 * <p>[解释JSON类型的参数值]</p>
	 * 
	 * @param objKey
	 * @param obj
	 * @param parameters
	 * @return: void
	 */
	private void parseJSONParameters(String objKey, JSONObject obj, Map parameters) {
		Iterator it = obj.keys();
		while (it.hasNext()) {
			// 将Key值进行拼装
			// 拼装规则：如果在parameters下有变量为person,对应值为一个JSON数据格式的String串‘{name:jack,
			// sex:male }’
			// 则拼装成person.name=jack 和 person.sex=male的键值对放到parameters中
			String jsonKey = String.valueOf(it.next());
			String newkey = new StringBuffer(objKey).append(".").append(jsonKey).toString();
			String value = String.valueOf(obj.get(jsonKey));
			// 如果JSON的Value为数组的处理方式如下：
			if (isJSONArray(value)) {
				JSONArray ja = JSONArray.fromObject(value);
				parseJSONArrayParameters(newkey, ja, parameters);
			}
			// 如果JSON元素的value仍然为JSON，则递归调用parseJSONParameters方法
			else if (isJSONObject(value)) {
				JSONObject jo = JSONObject.fromObject(value);
				parseJSONParameters(newkey, jo, parameters);
			}
			// 如果JSON元素的value已经为简单类型，则将值放入parameters中
			else {
				parameters.put(newkey, new String[] { value });
			}
		}
	}
	
	/**
	 * 
	 * <p>[字符串是否为JSON数据格式]</p>
	 * 
	 * @param obj
	 * @return
	 * @return: boolean
	 */
	private boolean isJSONObject(String obj) {
		return obj != null && obj.trim().startsWith("{") && obj.trim().endsWith("}") && (obj.contains(":"));
	}
	
	/**
	 * 
	 * <p>[字符串是否为JSONArray数据格式]</p>
	 * 
	 * @param obj
	 * @return
	 * @return: boolean
	 */
	private boolean isJSONArray(String obj) {
		return obj != null && obj.trim().startsWith("[") && obj.trim().endsWith("]");
	}
}
