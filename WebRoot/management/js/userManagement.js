var addhtml = '<table>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px"><font color="red">*</font>货品名称：</td><td style="height:28px;"><input type="text" style="height:28px; line-height:28px;" id="gName" placeholder="请输入名称"/></td></tr>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px"><font color="red">*</font>货品数量：</td><td style="height:28px"><input type="text" style="height:28px; line-height:28px;" id="gNum"  placeholder="请输入数字"/></td></tr>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px"><font color="red">*</font>货品价格：</td><td style="height:28px"><input type="text" style="height:28px; line-height:28px;" id="gPrice"  placeholder="请输入数字"/></td></tr>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px"><font color="red">*</font>货品类型：</td><td style="height:28px"><input type="text" style="height:28px; line-height:28px;" id="gType"  placeholder="请输入类型"/></td></tr>'
	+'<tr style="width:290px;height:56px"><td style="width:80px;height:56px line-height:28px;"><font color="red">*</font>货品描述：</td><td style="height:56px"><textarea style="font-size:14px;" id="gDescription"  placeholder="请输入描述"/></td></tr>'
	+'</table>';
var updatehtml = '<table>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px">货品名称：</td><td style="height:28px"><input type="text" style="height:28px; line-height:28px;" id="gName" /></td></tr>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px">货品数量：</td><td style="height:28px"><input type="text" style="height:28px; line-height:28px;" id="gNum" /></td></tr>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px">货品价格：</td><td style="height:28px"><input type="text" style="height:28px; line-height:28px;" id="gPrice" /></td></tr>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px">货品类型：</td><td style="height:28px"><input type="text" style="height:28px; line-height:28px;" id="gType" /></td></tr>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px">货品状态：</td><td style="height:28px"><select name="select" id="select_state" style="width:157px;height:28px;"><option id="option_default" value=""></option><option id="option_other" value=""></option></select></td></tr>'
	+'<tr style="width:290px;height:56px"><td style="width:80px;height:56px line-height:28px;">货品描述：</td><td style="height:56px"><textarea style="font-size:14px;width:157px;" id="gDescription" /></td></tr>'
	+'</table>';
var adduhtml = '<table>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px"><font color="red">*</font>员工名称：</td><td style="height:28px;"><input type="text" style="height:28px; line-height:28px;" id="uName" placeholder="请输入用户名"/></td></tr>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px"><font color="red">*</font>员工年龄：</td><td style="height:28px"><input type="text" style="height:28px; line-height:28px;" id="uAge" placeholder="请输入数字"/></td></tr>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px"><font color="red">*</font>员工职位：</td><td style="height:28px"><select name="selectu" id="select_position" style="width:157px;height:28px;"><option value="店员">店员</option><option value="店长">店长</option></select></td></tr>'
	+'<tr style="width:290px;height:28px"><td style="width:80px;height:28px">员工工资：</td><td style="height:28px"><input type="text" style="height:28px; line-height:28px;" id="uSalary" placeholder="请输入数字"/></td></tr>'
	+'</table>';
var contenetTop = '<div class="content_right_search">'
					+'<div class="content_right_add" id="content_right_add"  onclick="csm.userManagement.showAddDialog()"><img src="'+WEB_ROOT+'/management/image/add.png" width="18px" height="18px"/></div>'
					+'<div class="search-w3ls search-w3ls_clj">'
					+	'<div class="input-group">'
					+		'<input type="text" class="form-control input_clj" name="search" id="search2"'
					+		'placeholder="Search" required />'
					+		'<span class="input-group-btn"  onclick="csm.userManagement.hpglSearch()">'
					+		'<button class="btn btn-default">'
					+			'<i class="fa fa-search"></i>'
					+		'</button> </span>'
					+	'</div>'
					+'</div>'
				+'</div>'
var rycontenetTop = '<div class="content_right_search">'
					+'<div class="content_right_add" id="content_right_add"  onclick="csm.userManagement.showAddUDialog();"><img src="'+WEB_ROOT+'/management/image/add.png" width="18px" height="18px"/></div>'
					+'<div class="search-w3ls search-w3ls_clj">'
					+	'<div class="input-group">'
					+		'<input type="text" class="form-control input_clj" name="search" id="search2"'
					+		'placeholder="Search" required />'
					+		'<span class="input-group-btn"  onclick="csm.userManagement.ryglSearch();">'
					+		'<button class="btn btn-default">'
					+			'<i class="fa fa-search"></i>'
					+		'</button> </span>'
					+	'</div>'
					+'</div>'
				+'</div>'
csm.userManagement = function() {
	
	function init() {
		setTab();
	}
	function setTab(tabName){
		switch(tabName){
			case "hpgl":
				$(".content_type").each(function(index,obj){
					if($(this).text() == "货品管理"){
						$(this).css("background","#2b2727");
						$(this).prop("val","true");
					}else{
						$(this).css("background","#27ae60");
						$(this).prop("val","false");
					}
				})
				hpgl();
				break;
			case "xsry":
				$(".content_type").each(function(index,obj){
					if($(this).text() == "人员管理"){
						$(this).css("background","#2b2727");
						$(this).prop("val","true");
					}else{
						$(this).css("background","#27ae60");
						$(this).prop("val","false");
					}
				})
				rygl();
				break;
//			case "ghs":
//				$(".content_type").each(function(index,obj){
//					if($(this).text() == "供货商管理"){
//						$(this).css("background","#2b2727");
//						$(this).prop("val","true");
//					}else{
//						$(this).css("background","#27ae60");
//						$(this).prop("val","false");
//					}
//				})
//				$("#content_right").html("<div>供货商管理</div>")
//				break;
			case "qhtx":
				$(".content_type").each(function(index,obj){
					if($(this).text() == "缺货提醒"){
						$(this).css("background","#2b2727");
						$(this).prop("val","true");
					}else{
						$(this).css("background","#27ae60");
						$(this).prop("val","false");
					}
				})
				$("#content_right_top").html("");
				qhtx();
				break;
			case "yjfx":
				$(".content_type").each(function(index,obj){
					if($(this).text() == "业绩分析"){
						$(this).css("background","#2b2727");
						$(this).prop("val","true");
					}else{
						$(this).css("background","#27ae60");
						$(this).prop("val","false");
					}
				})
				$("#content_right_top").html("");
				$("#content_right").html('<canvas id="myChart" width="450" height="400" style="background-color:#F2DEDE"></canvas><canvas id="myChart2" width="450" height="400" style="background-color:#F2DEDE"></canvas><div><div style="background:#F2DEDE;width:450px;height:30px;float:left;text-align:center;line-height:30px;font-size:14px;color:#666666;">销售人员业绩分析-柱状图</div><div style="background:#F2DEDE;width:450px;height:30px;float:left;text-align:center;line-height:30px;font-size:14px;color:#666666;">销售人员业绩分析-线状图</div></div>')
				yjfx();
				break;
			default:
				$(".content_type").each(function(index,obj){
					if($(this).attr("val") == "true"){
						$(this).css("background","#2b2727");
					}
				});
				hpgl();
				break;
		}
	}
	/*
	 * 货品管理
	 */
	function hpgl(){
		$.ajax({
			url: WEB_ROOT + '/goodsManagement/goodsManage!queryGoodsInitM.do',
			type : 'post',
	        dataType : 'json',
			success : function(response){
				var goodList = response.goodsDTO.goodsList;
				var hpglhtml = '<table class="hpgl_table"><tr class="hpgl_thead"><td class="hpgl_thead_name">商品名称</td><td>商品库存</td><td>商品价格</td><td>商品销量</td><td>商品分类</td><td>商品状态</td><td>操作</td></tr>';
				if(goodList.length>0){
					for(var i=0;i<goodList.length;i++){
					hpglhtml += '<tr class="hpgl_tbody"><td>'+goodList[i].goods_name+'</td><td>'+goodList[i].goods_num+'</td><td>'+goodList[i].goods_price+'</td><td>'+goodList[i].goods_sales_num+'</td>' 
					switch(goodList[i].goods_type){
						case "mm":
							hpglhtml+='<td>面膜</td>';
							break;
						case "sfs":
							hpglhtml+='<td>爽肤水</td>';
							break;
						case "ms":
							hpglhtml+='<td>面霜</td>';
							break;
						case "xz":
							hpglhtml+='<td>卸妆</td>';
							break;
						case "jy":
							hpglhtml+='<td>精油</td>';
							break;
						case "cg":
							hpglhtml+='<td>唇膏</td>';
							break;
						case "xs":
							hpglhtml+='<td>香水</td>';
							break;
						case "fdy":
							hpglhtml+='<td>粉底液</td>';
							break;
						case "bbs":
							hpglhtml+='<td>BB霜</td>';
							break;
						default:
							hpglhtml+='<td>其他</td>';
							break;
					}
					if(goodList[i].goods_state == "1"){
						hpglhtml+='<td>在售</td>';
					}else{
						hpglhtml+='<td>下架</td>';
					}
					hpglhtml+='<td><img src="'+WEB_ROOT+'/management/image/update.png" width="15px" height="15px" style="margin-right:15px;cursor: pointer;" onclick="csm.userManagement.showUpdateDialog(\''+goodList[i].goods_id+'\',\''+goodList[i].goods_name+'\',\''+goodList[i].goods_num+'\',\''+goodList[i].goods_price+'\',\''+goodList[i].goods_type+'\',\''+goodList[i].goods_state+'\',\''+goodList[i].goods_description+'\')"/><img src="'+WEB_ROOT+'/management/image/cross.png" width="18px" height="18px"style="cursor: pointer;" onclick="csm.userManagement.deleteGoods(\''+goodList[i].goods_id+'\')"/></td></tr>';
				}
				hpglhtml+='</table>';
				$("#content_right_top").html(contenetTop)
				$("#content_right").html(hpglhtml)
				}else{
					$("#content_right").html("<div style='width:900px;height:32px;color:red;line-height:32px;text-align:center;margin-top:30px;'>当前分类暂无商品信息！<div>")
				}
			},
			error : function(){
				alert("error")
			}
		});
	}
	/*
	 * 缺货提醒
	 */
	function qhtx(){
		$.ajax({
			url: WEB_ROOT + '/goodsManagement/goodsManage!queryGoodsqx.do',
			type : 'post',
	        dataType : 'json',
			success : function(response){
				var goodList = response.goodsDTO.goodsList;
				var qhtxhtml = '<table class="hpgl_table"><tr class="hpgl_thead"><td class="hpgl_thead_name">商品名称</td><td>商品库存</td><td>商品价格</td><td>商品销量</td><td>商品分类</td><td>商品状态</td><td>提示</td></tr>';
				if(goodList.length>0){
					for(var i=0;i<goodList.length;i++){
					qhtxhtml += '<tr class="hpgl_tbody"><td>'+goodList[i].goods_name+'</td><td><font color="red">'+goodList[i].goods_num+'</font></td><td>'+goodList[i].goods_price+'</td><td>'+goodList[i].goods_sales_num+'</td>' 
					switch(goodList[i].goods_type){
						case "mm":
							qhtxhtml+='<td>面膜</td>';
							break;
						case "sfs":
							qhtxhtml+='<td>爽肤水</td>';
							break;
						case "ms":
							qhtxhtml+='<td>面霜</td>';
							break;
						case "xz":
							qhtxhtml+='<td>卸妆</td>';
							break;
						case "jy":
							qhtxhtml+='<td>精油</td>';
							break;
						case "cg":
							qhtxhtml+='<td>唇膏</td>';
							break;
						case "xs":
							qhtxhtml+='<td>香水</td>';
							break;
						case "fdy":
							qhtxhtml+='<td>粉底液</td>';
							break;
						case "bbs":
							qhtxhtml+='<td>BB霜</td>';
							break;
						default:
							qhtxhtml+='<td>其他</td>';
							break;
					}
					if(goodList[i].goods_state == "1"){
						qhtxhtml+='<td>在售</td><td><font color="red">请上货！</font></td></tr>';
					}else{
						qhtxhtml+='<td>下架</td><td><font color="red">无</font></td></tr>';
					}
				}
				qhtxhtml+='</table>';
				$("#content_right").html(qhtxhtml)
				}else{
					$("#content_right").html("<div style='width:900px;height:32px;color:red;line-height:32px;text-align:center;margin-top:30px;'>当前分类暂无商品信息！<div>")
				}
			},
			error : function(){
				alert("error")
			}
		});
	}
	function hpglSearch(){
		var searchName = $("#search2").val();
		$.ajax({
			url: WEB_ROOT + '/goodsManagement/goodsManage!queryByName.do',
			type : 'post',
	        dataType : 'json',
	        data : {
				goodsName : searchName
	        },
			success : function(response){
				var goodList = response.goodsDTO.goodsList;
				var hpglhtml = '<table class="hpgl_table"><tr class="hpgl_thead"><td class="hpgl_thead_name">商品名称</td><td>商品库存</td><td>商品价格</td><td>商品销量</td><td>商品分类</td><td>商品状态</td><td>操作</td></tr>';
				if(goodList.length>0){
					for(var i=0;i<goodList.length;i++){
					hpglhtml += '<tr class="hpgl_tbody"><td>'+goodList[i].goods_name+'</td><td>'+goodList[i].goods_num+'</td><td>'+goodList[i].goods_price+'</td><td>'+goodList[i].goods_sales_num+'</td>' 
					switch(goodList[i].goods_type){
						case "mm":
							hpglhtml+='<td>面膜</td>';
							break;
						case "sfs":
							hpglhtml+='<td>爽肤水</td>';
							break;
						case "ms":
							hpglhtml+='<td>面霜</td>';
							break;
						case "xz":
							hpglhtml+='<td>卸妆</td>';
							break;
						case "jy":
							hpglhtml+='<td>精油</td>';
							break;
						case "cg":
							hpglhtml+='<td>唇膏</td>';
							break;
						case "xs":
							hpglhtml+='<td>香水</td>';
							break;
						case "fdy":
							hpglhtml+='<td>粉底液</td>';
							break;
						case "bbs":
							hpglhtml+='<td>BB霜</td>';
							break;
						default:
							hpglhtml+='<td>其他</td>';
							break;
					}
					if(goodList[i].goods_state == "1"){
						hpglhtml+='<td>在售</td>';
					}else{
						hpglhtml+='<td>下架</td>';
					}
					hpglhtml+='<td><img src="'+WEB_ROOT+'/management/image/update.png" width="15px" height="15px" style="margin-right:15px;cursor: pointer;"onclick="csm.userManagement.showUpdateDialog(\''+goodList[i].goods_id+'\',\''+goodList[i].goods_name+'\',\''+goodList[i].goods_num+'\',\''+goodList[i].goods_price+'\',\''+goodList[i].goods_type+'\',\''+goodList[i].goods_state+'\',\''+goodList[i].goods_description+'\')"/><img src="'+WEB_ROOT+'/management/image/cross.png" width="18px" height="18px"style="cursor: pointer;" onclick="csm.userManagement.deleteGoods(\''+goodList[i].goods_id+'\')"/></td></tr>';
				}
				hpglhtml+='</table>';
				$("#content_right").html(hpglhtml)
				}else{
					$("#content_right").html("<div style='width:900px;height:32px;color:red;line-height:32px;text-align:center;margin-top:30px;'>当前分类暂无商品信息！<div>")
				}
			},
			error : function(){
				alert("error")
			}
		});
	}
	function showAddDialog(){
		var addGoodsDialog = dialog({
			 	title: "增加货品",
				content:addhtml,
				width:300,
				height:240,
				button :[{
			    	value: "确定",//检索
		   	    	callback : function(){
					 addGoods();
		   	    	}
		   	    },{
		   	    	value: "取消",//取消
		   	    	callback : function(){
		   	    		this.close();
		   	    	}
		   	    }]
		});
		addGoodsDialog.showModal();
		if(flag == "u"){
			$("#gName").val(goodsName);
			$("#gNum").val(goodsNum);
			$("#gPrice").val(goodsPrice);
			$("#gType").val(goodsType);
			$("#gDescription").val(goodsDescription);
		}
	}
	function showUpdateDialog(goodsId,goodsName,goodsNum,goodsPrice,goodsType,goodsState,goodsDescription){
		var UpdateGoodsDialog = dialog({
			 	title: "修改货品",
				content:updatehtml,
				width:300,
				height:280,
				button :[{
			    	value: "确定",//检索
		   	    	callback : function(){
					 updateGoods(goodsId);
		   	    	}
		   	    },{
		   	    	value: "取消",//取消
		   	    	callback : function(){
		   	    		this.close();
		   	    	}
		   	    }]
		});
		UpdateGoodsDialog.showModal();
		if(goodsState == 1){
			$("#option_default").prop("value","在售");
			$("#option_default").text("在售");
			$("#option_other").prop("value","下架");
			$("#option_other").text("下架");
		}else if(goodsState == 0){
			$("#option_default").prop("value","下架");
			$("#option_default").text("下架");
			$("#option_other").prop("value","在售");
			$("#option_other").text("在售");
		}
		$("#gName").val(goodsName);
		$("#gNum").val(goodsNum);
		$("#gPrice").val(goodsPrice);
		$("#gType").val(goodsType);
		$("#gDescription").val(goodsDescription);
	}
	function addGoods(){
		var reg = /^[0-9]*$/;
		var goodsName = $("#gName").val();
		var goodsNum = $("#gNum").val();
		var goodsPrice = $("#gPrice").val();
		var goodsType = $("#gType").val();
		var goodsDescription = $("#gDescription").val();
		if(reg.test(goodsNum)&&reg.test(goodsPrice)){
			$.ajax({
				url: WEB_ROOT + '/goodsManagement/goodsManage!addGoods.do',
				type : 'post',
		        dataType : 'json',
		        data : {
					"goods.goods_name" : goodsName,
					"goods.goods_num" : goodsNum,
					"goods.goods_price" : goodsPrice,
					"goods.goods_type" : goodsType,
					"goods.goods_description" : goodsDescription
		        },
				success : function(response){
		        	setTab();
		        	alert("添加成功！");
				},
				error : function(){
					alert("添加失败！")
				}
			});
		}else{
			alert("输入有误！");
		}
	}
	function deleteGoods(goodsId){
		if(confirm("删除后将无法恢复，是否继续？")){
			$.ajax({
				url: WEB_ROOT + '/goodsManagement/goodsManage!deleteGoods.do',
				type : 'post',
		        dataType : 'json',
		        data : {
					"goods.goods_id" : goodsId
		        },
				success : function(response){
		        	setTab();
		        	alert("删除成功！")
				},
				error : function(){
					alert("删除失败！")
				}
			});
		}else{
			return;
		}
	}
	function updateGoods(goodsId){
		var goodsName = $("#gName").val();
		var goodsNum = $("#gNum").val();
		var goodsPrice = $("#gPrice").val();
		var goodsType = $("#gType").val();
		var goodsDescription = $("#gDescription").val();
		var goodsState;
		if(document.getElementsByTagName("select")[0].value == "在售"){
			goodsState = 1;
		}else if(document.getElementsByTagName("select")[0].value == "下架"){
			goodsState = 0;
		}
		$.ajax({
			url: WEB_ROOT + '/goodsManagement/goodsManage!updateGoods.do',
			type : 'post',
	        dataType : 'json',
	        data : {
				"goods.goods_name" : goodsName,
				"goods.goods_num" : goodsNum,
				"goods.goods_price" : goodsPrice,
				"goods.goods_type" : goodsType,
				"goods.goods_description" : goodsDescription,
				"goods.goods_state" : goodsState,
				"goods.goods_id" : goodsId
				
	        },
			success : function(response){
	        	setTab();
	        	alert("修改成功！")
			},
			error : function(){
				alert("修改失败！")
			}
		});
	}
	/***************************************人员管理**************************************/
	function rygl(){
		$.ajax({
			url: WEB_ROOT + '/adminManagement/adminManage!allUsers.do',
			type : 'post',
	        dataType : 'json',
			success : function(response){
	        	var userList = response.userDTO.userList;
				var ryglhtml = '<table class="hpgl_table"><tr class="hpgl_thead"><td class="hpgl_thead_name">员工姓名</td><td>员工年龄</td><td>入职时间</td><td>员工职位</td><td>员工业绩</td><td>员工工资</td><td>操作</td></tr>';
				if(userList.length>0){
					for(var i=0;i<userList.length;i++){
						if(userList[i].userName == "admin"){
							continue;
						}else{
							ryglhtml += '<tr class="hpgl_tbody"><td>'+userList[i].userName+'</td><td>'+userList[i].userAge+'</td><td>'+userList[i].userCreateTime+'</td>'
							if(userList[i].user_position == "admin"){
								ryglhtml+='<td>店长</td>'
							}else if(userList[i].user_position == "normal"){
								ryglhtml+='<td>店员</td>'
							}
							ryglhtml+='<td>'+userList[i].user_record+'</td><td>'+userList[i].user_salary+'</td>'
							ryglhtml+='<td><img src="'+WEB_ROOT+'/management/image/update.png" width="15px" height="15px" style="margin-right:15px;cursor: pointer;" onclick="csm.userManagement.showUpdateUDialog(\''+userList[i].userId+'\',\''+userList[i].userName+'\',\''+userList[i].userAge+'\',\''+userList[i].user_salary+'\');"/><img src="'+WEB_ROOT+'/management/image/cross.png" width="18px" height="18px"style="cursor: pointer;" onclick="csm.userManagement.deleteUser(\''+userList[i].userId+'\')"/></td></tr>';
						}
					}
				ryglhtml+='</table>';
				$("#content_right_top").html(rycontenetTop)
				$("#content_right").html(ryglhtml)
				}else{
					$("#content_right").html("<div style='width:900px;height:32px;color:red;line-height:32px;text-align:center;margin-top:30px;'>当前暂无人员信息信息！<div>")
				}
			},
			error : function(){
				alert("加载失败！")
			}
		});
	}
	function showAddUDialog(){
		var addGoodsDialog = dialog({
			 	title: "新增员工",
				content:adduhtml,
				width:300,
				height:200,
				button :[{
			    	value: "确定",//检索
		   	    	callback : function(){
					 addUser();
		   	    	}
		   	    },{
		   	    	value: "取消",//取消
		   	    	callback : function(){
		   	    		this.close();
		   	    	}
		   	    }]
		});
		addGoodsDialog.showModal();
	}
	function showUpdateUDialog(userId,userName,userAge,userSalary){
		var showUpdateUDialog = dialog({
			 	title: "修改员工信息",
				content:adduhtml,
				width:300,
				height:200,
				button :[{
			    	value: "确定",//检索
		   	    	callback : function(){
					 updateUser(userId);
		   	    	}
		   	    },{
		   	    	value: "取消",//取消
		   	    	callback : function(){
		   	    		this.close();
		   	    	}
		   	    }]
		});
		showUpdateUDialog.showModal();
		$("#uName").val(userName);
		$("#uAge").val(userAge);
		$("#uSalary").val(userSalary);
	}
	function addUser(){
		var reg = /^[0-9]*$/;
		var userName = $("#uName").val();
		var userAge = $("#uAge").val();
		var userSalary = $("#uSalary").val();
		var userPosition;
		if(document.getElementsByTagName("select")[0].value == "店员"){
			userPosition = "normal";
		}else if(document.getElementsByTagName("select")[0].value == "店长"){
			userPosition = "admin";
		}
		if(reg.test(userAge)&&reg.test(userSalary)){
			$.ajax({
				url: WEB_ROOT + '/adminManagement/adminManage!addUser.do',
				type : 'post',
		        dataType : 'json',
		        data : {
					"user.userName" : userName,
					"user.userAge" : userAge,
					"user.user_salary" : userSalary,
					"user.user_position" : userPosition
		        },
				success : function(response){
		        	setTab("xsry");
		        	alert("添加成功！")
				},
				error : function(){
					alert("添加失败！")
				}
			});
		}else{
			alert("输入有误！")
		}
	}
	function updateUser(userId){
		var userName = $("#uName").val();
		var userAge = $("#uAge").val();
		var userSalary = $("#uSalary").val();
		var userPosition;
		if(document.getElementsByTagName("select")[0].value == "店员"){
			userPosition = "normal";
		}else if(document.getElementsByTagName("select")[0].value == "店长"){
			userPosition = "admin";
		}
		$.ajax({
			url: WEB_ROOT + '/adminManagement/adminManage!updateUser.do',
			type : 'post',
	        dataType : 'json',
	        data : {
				"user.userName" : userName,
				"user.userAge" : userAge,
				"user.user_salary" : userSalary,
				"user.user_position" : userPosition,
				"user.userId" : userId
				
	        },
			success : function(response){
	        	setTab("xsry");
	        	alert("修改成功！")
			},
			error : function(){
				alert("修改失败！")
			}
		});
	}
	function ryglSearch(){
		var ryCondition = $("#search2").val();
		$.ajax({
			url: WEB_ROOT + '/adminManagement/adminManage!selectedUsers.do',
			type : 'post',
	        dataType : 'json',
	        data : {
				"sc" : ryCondition
	        },
			success : function(response){
	        	var userList = response.userDTO.userList;
				var ryglhtml = '<table class="hpgl_table"><tr class="hpgl_thead"><td class="hpgl_thead_name">员工姓名</td><td>员工年龄</td><td>入职时间</td><td>员工职位</td><td>员工业绩</td><td>员工工资</td><td>操作</td></tr>';
				if(userList.length>0){
					for(var i=0;i<userList.length;i++){
						ryglhtml += '<tr class="hpgl_tbody"><td>'+userList[i].userName+'</td><td>'+userList[i].userAge+'</td><td>'+userList[i].userCreateTime+'</td>'
						if(userList[i].user_position == "admin"){
							ryglhtml+='<td>店长</td>'
						}else if(userList[i].user_position == "normal"){
							ryglhtml+='<td>店员</td>'
						}
						ryglhtml+='<td>'+userList[i].user_record+'</td><td>'+userList[i].user_salary+'</td>'
						if(userList[i].user_position == "admin"){
							ryglhtml+='<td></td></tr>';
						}else if(userList[i].user_position == "normal"){
							ryglhtml+='<td><img src="'+WEB_ROOT+'/management/image/update.png" width="15px" height="15px" style="margin-right:15px;cursor: pointer;" onclick="csm.userManagement.showUpdateUDialog(\''+userList[i].userId+'\',\''+userList[i].userName+'\',\''+userList[i].userAge+'\',\''+userList[i].user_salary+'\');"/><img src="'+WEB_ROOT+'/management/image/cross.png" width="18px" height="18px"style="cursor: pointer;" onclick="csm.userManagement.deleteUser(\''+userList[i].userId+'\')"/></td></tr>';
						}
					}
				ryglhtml+='</table>';
				$("#content_right_top").html(rycontenetTop)
				$("#content_right").html(ryglhtml)
				}else{
					$("#content_right").html("<div style='width:900px;height:32px;color:red;line-height:32px;text-align:center;margin-top:30px;'>当前暂无人员信息信息！<div>")
				}
			},
			error : function(){
				alert("加载失败！")
			}
		});
	}
	function deleteUser(userId){
		if(confirm("删除后将无法恢复，是否继续？")){
			$.ajax({
				url: WEB_ROOT + '/adminManagement/adminManage!deleteUsers.do',
				type : 'post',
		        dataType : 'json',
		        data : {
					"user.userId" : userId
		        },
				success : function(response){
		        	setTab("xsry");
		        	alert("删除成功！")
				},
				error : function(){
					alert("删除失败！")
				}
			});
		}else{
			return;
		}
	}
	/*********************************************业绩分析****************************************/
	function yjfx(){
		$.ajax({
			url: WEB_ROOT + '/adminManagement/adminManage!allUsers.do',
			type : 'post',
	        dataType : 'json',
			success : function(response){
	        	var userList = response.userDTO.userList;
	        	var userNameArray = new Array();
				var userRecordArray = new Array();
	        	for(var i=0;i<userList.length;i++){
					if(userList[i].userName == "admin"){
						continue;
					}else{
						userNameArray.push(userList[i].userName);
						userRecordArray.push(userList[i].user_record);
					}
				}
	        	var data = {  
        			labels : userNameArray,  
        			datasets : [    
		                {    
		                    //曲线的填充颜色  
		                    fillColor : "rgba(151,187,205,0.5)",    
		                    //填充块的边框线的颜色  
		                    strokeColor : "rgba(151,187,205,1)",   
		                    //表示数据的圆圈的边的颜色  
		                    pointStrokeColor : "red",    
		                    data : userRecordArray 
		                }  
		            ]    
        		};    
			    var options = {  
			        scaleOverride :false, //是否显示折线图的线条  
			        scaleShowLabels :true,//是否显示纵轴  
			        scaleShowGridLines :true,//是否显示坐标轴的标尺  
			        bezierCurve :true,//是否显示圆滑的效果  
			        pointDot :true,//是否显示折线图的顶点  
			        pointDotRadius :2,//折线图定点大小  
			        pointDotStrokeWidth:1,//折线图定点外围大小  
			        animation :true,//是否显示动画效果  
			        animationSteps :60,//图形显示的速度  
			    };  
    
		        var ctx = document.getElementById("myChart").getContext("2d");    
		        var myNewChart = new Chart(ctx).Bar(data,options);   
		        var ctx = document.getElementById("myChart2").getContext("2d");    
		        var myNewChart = new Chart(ctx).Line(data,options);   
			},
			error : function(){
			}
		});
		
	}
	return {
		init : init, //初始化方法
		setTab : setTab,//切换tab页
		hpgl : hpgl,//货品管理
		hpglSearch : hpglSearch,//货品管理页面查询
		showAddDialog : showAddDialog,//货品管理页面显示增加弹窗
		showUpdateDialog : showUpdateDialog,//货品管理页面显示修改弹窗
		addGoods : addGoods,//新增货品
		deleteGoods : deleteGoods,//删除货品
		updateGoods : updateGoods,//修改货品
		qhtx : qhtx,//缺货提醒
		rygl : rygl,//人员管理
		showAddUDialog : showAddUDialog,//人员管理页面显示增加弹窗
		addUser : addUser,//新增人员
		showUpdateUDialog : showUpdateUDialog,//人员管理页面显示修改弹窗
		updateUser : updateUser,//修改人员信息
		ryglSearch : ryglSearch,//人员管理页面查询
		deleteUser : deleteUser,//人员管理页面删除
		yjfx : yjfx
	}

}();


$(function() {
	csm.userManagement.init();
	$("#content_right_add").mouseover(function(){
		$(this).css("background","#ccc")
	})
	$("#content_right_add").mouseout(function(){
		$(this).css("background","#fff")
	})
}); 