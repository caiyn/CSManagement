package com.clj.csmanagement.interceptor;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

public class CSMFileUploadInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 17502465790277311L;

protected static final Logger log = Logger.getLogger(CSMFileUploadInterceptor.class);
	
	private static final String DEFAULT_DELIMITER = ",";
	private static final String DEFAULT_MESSAGE = "no.message.found";
	protected Long maximumSize;
	protected String allowedTypes;
	protected Set allowedTypesSet = Collections.EMPTY_SET;
	
	
	public Long getMaximumSize() {
		return maximumSize;
	}

	public void setMaximumSize(Long maximumSize) {
		this.maximumSize = maximumSize;
	}
	public void setAllowedTypes(String allowedTypes) {
		this.allowedTypes = allowedTypes;

		// set the allowedTypes as a collection for easier access later
		allowedTypesSet = getDelimitedValues(allowedTypes);
	}
	
	private static Set getDelimitedValues(String delimitedString) {
		Set<String> delimitedValues = new HashSet<String>();
		if (delimitedString != null) {
			StringTokenizer stringTokenizer = new StringTokenizer(delimitedString, DEFAULT_DELIMITER);
			while (stringTokenizer.hasMoreTokens()) {
				String nextToken = stringTokenizer.nextToken().toLowerCase().trim();
				if (nextToken.length() > 0) {
					delimitedValues.add(nextToken);
				}
			}
		}
		return delimitedValues;
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);

		if (!(request instanceof MultiPartRequestWrapper)) {
			if (log.isDebugEnabled()) {
				ActionProxy proxy = invocation.getProxy();
				log.debug(getTextMessage("struts.messages.bypass.request", new Object[] { proxy.getNamespace(),
						proxy.getActionName() }, ActionContext.getContext().getLocale()));
			}

			return invocation.invoke();
		}

		final Object action = invocation.getAction();
		ValidationAware validation = null;

		if (action instanceof ValidationAware) {
			validation = (ValidationAware) action;
		}

		MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;
		
		Map parameters = ac.getParameters();

		// Bind allowed Files
		Enumeration fileParameterNames = multiWrapper.getFileParameterNames();
		while (fileParameterNames != null && fileParameterNames.hasMoreElements()) {
			// get the value of this input tag
			String inputName = (String) fileParameterNames.nextElement();

			// get the content type
			String[] contentType = multiWrapper.getContentTypes(inputName);

			if (isNonEmpty(contentType)) {
				// get the name of the file from the input tag
				String[] fileName = multiWrapper.getFileNames(inputName);

				if (isNonEmpty(fileName)) {
					// Get a File object for the uploaded File
					File[] files = multiWrapper.getFiles(inputName);
					if (files != null) {
						for (int index = 0; index < files.length; index++) {

							if (acceptFile(files[index], contentType[index], inputName, validation, ac.getLocale())) {
								parameters.put(inputName, files);
								parameters.put(inputName + "ContentType", contentType);
								parameters.put(inputName + "FileName", fileName);
							}
						}
					}
				} else {
					log.error(getTextMessage("struts.messages.invalid.file", new Object[] { inputName }, ActionContext
							.getContext().getLocale()));
				}
			} else {
				log.error(getTextMessage("struts.messages.invalid.content.type", new Object[] { inputName },
						ActionContext.getContext().getLocale()));
			}
		}

		// invoke action
		String result = invocation.invoke();

		// cleanup
		fileParameterNames = multiWrapper.getFileParameterNames();
		while (fileParameterNames != null && fileParameterNames.hasMoreElements()) {
			String inputValue = (String) fileParameterNames.nextElement();
			File[] file = multiWrapper.getFiles(inputValue);
			for (int index = 0; index < file.length; index++) {
				File currentFile = file[index];
				log.info(getTextMessage("struts.messages.removing.file", new Object[] { inputValue, currentFile },
						ActionContext.getContext().getLocale()));

				if ((currentFile != null) && currentFile.isFile()) {
					currentFile.delete();
				}
			}
		}

		return result;
	}
	
	private String getTextMessage(String messageKey, Object[] args, Locale locale) {
		if (args == null || args.length == 0) {
			return LocalizedTextUtil.findText(this.getClass(), messageKey, locale);
		} else {
			return LocalizedTextUtil.findText(this.getClass(), messageKey, locale, DEFAULT_MESSAGE, args);
		}
	}
	
	protected boolean acceptFile(File file, String contentType, String inputName, ValidationAware validation,
			Locale locale) {
		boolean fileIsAcceptable = false;

		// If it's null the upload failed
		if (file == null) {
			String errMsg = getTextMessage("struts.messages.error.uploading", new Object[] { inputName }, locale);
			if (validation != null) {
				validation.addFieldError(inputName, errMsg);
			}

			log.error(errMsg);
		} else if (maximumSize != null && maximumSize.longValue() < file.length()) {
			String errMsg = getTextMessage("struts.messages.error.file.too.large", new Object[] { inputName,
					file.getName(), "" + file.length() }, locale);
			if (validation != null) {
				validation.addFieldError(inputName, errMsg);
			}

			log.error(errMsg);
		} else if ((!allowedTypesSet.isEmpty()) && (!containsItem(allowedTypesSet, contentType))) {
			String errMsg = getTextMessage("struts.messages.error.content.type.not.allowed", new Object[] { inputName,
					file.getName(), contentType }, locale);
			if (validation != null) {
				validation.addFieldError(inputName, errMsg);
			}

			log.error(errMsg);
		} else {
			fileIsAcceptable = true;
		}

		return fileIsAcceptable;
	}
	
	private static boolean containsItem(Collection itemCollection, String key) {
		return itemCollection.contains(key.toLowerCase());
	}
	
	private static boolean isNonEmpty(Object[] objArray) {
		boolean result = false;
		for (int index = 0; index < objArray.length && !result; index++) {
			if (objArray[index] != null) {
				result = true;
			}
		}
		return result;
	}


}
