csm.login = function() {
	
	function init() {
	}
	function login(){
		var userName = $("#LoginuserName").val();
		var password = $("#LoginPassword").val();
		$("#user_login_form").submit();
	}
	function enterKeydown(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if(e && e.keyCode == 13){ 				// 按Enter键要做的事情
			login();
		}
	}
	return {
		init : init, //初始化方法
		login : login,
		enterKeydown : enterKeydown
	}


}();

$(function() {
	csm.login.init();
}); 