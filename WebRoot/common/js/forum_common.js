var Global = function() {
};
var csm={};
Global.WEB_ROOT = window.location.pathname.split("/")[1];
WEB_ROOT = "/" + Global.WEB_ROOT;
	/**
 * 聚焦到光标所在位置 IE版
 * @param {} textEl
 * @param {} position
 */
function positionCursor(textEl,position){
	if($.browser.msie){//IE
		var range = textEl.createTextRange();   
		range.collapse(true);        
		range.moveStart('character', position);   
		range.select();
	}else{//非IE浏览器
		textEl.focus();         
		textEl.setSelectionRange(position,position); 
	}
}

/**
 *加载制定菜单页面
 */
function forwordPageNavigation(obj){
	$.ajax({
		url: obj.url,
		type:"POST",
		data:{
			resourceId:obj.resourceId
		},
		dataType:"html",
		success:function(response){				
			$("#wrap_patent_data").html(response);
			if(obj.resourceId=="PATENT_SEARCH"){
				epp.patentsearch.init();
			}else if(obj.resourceId=="SEARCH_HISTORY"){
				epp.searchhistory.init();
			}else if(obj.resourceId=="IPC_SEARCH"){
				epp.ipcsearch.init();
			}else if(obj.resourceId=="CLASSIFY_NAVIGATION_SEARCH"){
				epp.classifynavigationsearch.init();
			}else if(obj.resourceId=="MY_SUBJECT_MANAGER"){
				epp.subjectmanager.init();
			}
		},
		error:function(response){
			alert("加载导航菜单页面失败！");
		}
	});
	return false;
}

/**
 * 获取跳转菜单的url
 */
function fetchForwordPageUrl(resourceId){
	var url;
	var resId="";
	/**
	 *加载专利检索页面
	 */
	if(resourceId=="patentsearch"){
		url=WEB_ROOT + '/search/dispatcher/dispatcherAC!forwordPatentSearch.do';
		resId = "PATENT_SEARCH";
	}
	/**
	 *加载IPC检索页面
	 */
	else if(resourceId=="IPCsearch"){
		url=WEB_ROOT + '/search/dispatcher/dispatcherAC!forwordIpcSearch.do';
		resId = "IPC_SEARCH";
	}
	/**
	 *加载分类导航检索页面
	 */
	else if(resourceId=="clasnavsearch"){
		url=WEB_ROOT + '/search/dispatcher/dispatcherAC!forwordClassifyNavgaition.do';
		resId = "CLASSIFY_NAVIGATION_SEARCH";
	}
	/**
	 *加载同族检索页面
	 */
	else if(resourceId=="familysearch"){
		url=WEB_ROOT + '/search/dispatcher/dispatcherAC!forwordFamilySearch.do';
		resId = "FAMILY_SEARCH";
	}
	/**
	 *加载检索式历史页面
	 */
	else if(resourceId=="searchhistory"){
		url=WEB_ROOT + '/search/dispatcher/dispatcherAC!forwordSearchHistory.do';
		resId = "SEARCH_HISTORY";
	}
	/**
	 *加载我的专题管理页面
	 */
	else if(resourceId=="mysubject"){
		url=WEB_ROOT + '/search/dispatcher/dispatcherAC!forwordMySubjectManager.do';
		resId = "MY_SUBJECT_MANAGER";
	}else{
	}
	var forwordResult = {
		"url":url,
		"resourceId":resId
	};
	return forwordResult;
}

/**
 * 判断浏览器类型
 * @return {String}
 */
function getOs()
{ 
   var OsObject = ""; 
   if(navigator.userAgent.indexOf("MSIE")!=-1) { 
        OsObject = "MSIE"; 
   } 
   else if(navigator.userAgent.indexOf("Firefox")!=-1){ 
        OsObject = "Firefox"; 
   } 
   else if(navigator.userAgent.indexOf("Chrome")!=-1){ 
        OsObject = "Chrome"; 
   } 
   else if(navigator.userAgent.indexOf("Safari")!=-1) { 
        OsObject = "Safari"; 
   }  
   else if(navigator.userAgent.indexOf("Camino")!=-1){ 
        OsObject = "Camino"; 
   } 
   else if(navigator.userAgent.indexOf("Opera")!=-1){ 
        OsObject = "Opera"; 
   } 

   return OsObject;
} 

/**
 * 当没有获取到某元数的值时，返回“”
 * @param {} value
 * @return {String}
 */
function validateValue(value){
	if(typeof(value)=="undefined" || value=="" || value==null){
		return null;
	}
	return value;
}

/**
 * 验证日期：9999-99-99 9999.99.99 99999999
 * @param {} val
 */
function validatorCalendar(val){
	var result = {
		value : false,//默认验证通过
//		alertText : sipo.search.checkDateFormat
		alertText : "日期格式不正确，请重新输入！"
	}
	if(val){
		///.{0,}[.].{0,}[-].{0,}|.{0,}[-].{0,}[.].{0,}|.{0,}[-.]$|^[-.].{0,}/
		if(/.{0,}[.].{0,}[-].{0,}|.{0,}[-].{0,}[.].{0,}/.test(val)){
			result.value = true;
			return result;
		}
		var vd;
		if(/[-\.]/.test(val)){
			//日期以分隔符形式判断
			var flag = 0;
			if (/^[-.].{0,}/.test(val)){
				val = val.substr(1);
				flag = 1;
			}
			if (/[-.]{2}/.test(val)){
				flag = 2;
			}
			if (/(to)/.test(val)){
				flag = 3;
				var validator = val.split(/(to)/);
				var length = validator.length;
				if (length == 2){
					return validatorCalendar(validator[0].replace(/\s+/g,"")) || validatorCalendar(validator[1].replace(/\s+/g,""));
				} else {
					result.value = true;
					return result;
				}
			}
			var vals = val.split(/[-\.]/);
			var len = vals.length;
			switch(len){
				case 3:
					vd = validatorDate(vals[0],vals[1],vals[2]);
					result.value = !(vd.year && vd.month && vd.day);
					break;
				case 2:
					if (flag == 1){
						vd = validatorDate("1986",vals[0],vals[1]);
						result.value = !(vd.year && vd.month);
						break;
					} else if (flag == 2){
						vd = validatorDate(vals[0],"01",vals[1]);
						result.value = !(vd.year && vd.month);
						break;
					} else {
						vd = validatorDate(vals[0],vals[1]);
						result.value = !(vd.year && vd.month);
						break;
					}
					
				case 1:
					vd = validatorDate(vals[0]);
					result.value = !(vd.year);
					break;
				default:
					result.value = true;
					break;
			}
		}else if(/^(\d{4}|\d{6}|\d{8})$/.test(val) || /^(\d{4}|\d{6}|\d{8}) to (\d{4}|\d{6}|\d{8})$/.test(val)){
			//日期以99999999形式判断
			if (/(to)/.test(val)){
				flag = 3;
				var validator = val.split(/(to)/);
				var length = validator.length;
				if (length == 2){
					return validatorCalendar(validator[0].replace(/\s+/g,"")) || validatorCalendar(validator[1].replace(/\s+/g,""));
				} else {
					result.value = true;
					return result;
				}
			}
			var len = val.length;
			switch(len){
				case 4:
					vd = validatorDate(val);
					result.value = !(vd.year);
					break;
				case 6:
					vd = validatorDate(val.substring(0,4),val.substring(4,6));
					result.value = !(vd.year && vd.month);
					break;
				case 8:
					vd = validatorDate(val.substring(0,4),val.substring(4,6),val.substring(6,8));
					result.value = !(vd.year && vd.month && vd.day);
					break;
				default:
					result.value = true;
					break;
			}
		}else{
			result.value = true;
		}
	}
	return result;
}
/**
 * 判断日期
 * @param {} year
 * @param {} month
 * @param {} day
 */
function validatorDate(year,month,day){
	var result = {
		year : false,
		month :false,
		day : false
	};
	if(year){
		if(/^\d{4}$/.test(year) && parseInt(year,10) > 1000){
			result.year = true;	
		}
	}
	if(month){
		if(/^(([0]{0,1}[1-9]{1})|([1]{1}[0,1,2]{1}))$/.test(month)){
			result.month = true;
		}
	}
	if(day && result.year && result.month){
		if(/^\d{1,2}$/.test(day) && parseInt(day,10) > 0){
			year = parseInt(year,10);
			month = parseInt(month,10);
			day = parseInt(day,10);
			if(/^([1,3,5,7,8]{1}|(1[0,2]{1}))$/.test(month)){
				//大月
				if(day<=31){
					result.day = true;
				}
			}else if(/^([4,6,9]{1}|11)$/.test(month)){
				//小月
				if(day<=30){
					result.day = true;
				}
			}else{
				//2月
				if(validatorLeapYear(year)){
					//闰年
					if(day<=29){
						result.day = true;
					}
				}else{
					if(day<=28){
						result.day = true;
					}
				}
			}	
		}
	}
	return result;
}

/**
 * 判断闰年
 * @param {} year
 */
function validatorLeapYear(year){
	var result = false;
	if(year){
		result = /^\d{4}$/.test(year);
		if(result){
			year = parseInt(year,10);
			if(!(year%400==0 || (year%4==0 && year%100!=0))){
				result = false;
			}
		}
	}
	return result;
}

/**
 * 过滤高亮时带有的FONT标签
 */
function filterFontTaglib(fontTaglibStr){
	if(fontTaglibStr==undefined){
		return fontTaglibStr;
	}
	var afterFilterStr = "";
	afterFilterStr=fontTaglibStr.replace(/\<FONT color\=\'red\'\>/g,"");
	afterFilterStr=afterFilterStr.replace(/\<\/FONT\>/g,"");
	afterFilterStr=fontTaglibStr.replace(/\<font color\=\'red\'\>/g,"");
	afterFilterStr=afterFilterStr.replace(/\<\/font\>/g,"");
	return afterFilterStr;
}

/**
 * 替换数学公式标签中的<>
 */
function replaceMathTaglib(content){
	content = content.replace(/<math>/g,"&lt;math&gt;");
	content = content.replace(/<\/math>/g,"&lt;/math&gt;");
	content = content.replace(/<mrow>/g,"&lt;mrow&gt;");
	content = content.replace(/<\/mrow>/g,"&lt;/mrow&gt;");
	content = content.replace(/<mi>/g,"&lt;mi&gt;");
	content = content.replace(/<\/mi>/g,"&lt;/mi&gt;");
	content = content.replace(/<msub>/g,"&lt;msub&gt;");
	content = content.replace(/<\/msub>/g,"&lt;/msub&gt;");
	content = content.replace(/<mo>/g,"&lt;mo&gt;");
	content = content.replace(/<\/mo>/g,"&lt;/mo&gt;");
	content = content.replace(/<munder>/g,"&lt;munder&gt;");
	content = content.replace(/<\/munder>/g,"&lt;/munder&gt;");
	content = content.replace(/<mfrac>/g,"&lt;mfrac&gt;");
	content = content.replace(/<\/mfrac>/g,"&lt;/mfrac&gt;");
	content = content.replace(/<msqrt>/g,"&lt;msqrt&gt;");
	content = content.replace(/<\/msqrt>/g,"&lt;/msqrt&gt;");
	content = content.replace(/<mn>/g,"&lt;mn&gt;");
	content = content.replace(/<\/mn>/g,"&lt;/mn&gt;");
	return content;
}

/**
 * 替换所有的<>为&lt;&gt;
 */
function replaceAllJJH(content){
	content = content.replace(/</g,"&lt;");
	content = content.replace(/>/g,"&gt;");
	return content;
}

function checkbrowse() { 
	var ua = navigator.userAgent.toLowerCase(); 
	var is = (ua.match(/\b(chrome|opera|safari|msie|firefox)\b/) || ['', 'mozilla'])[1]; 
	var r = '(?:' + is + '|version)[\\/: ]([\\d.]+)'; 
	var v = (ua.match(new RegExp(r)) || [])[1]; 
	jQuery.browser.is = is; 
	jQuery.browser.ver = v; 
	return { 
		'is': jQuery.browser.is, 
		'ver': jQuery.browser.ver 
	} 
}