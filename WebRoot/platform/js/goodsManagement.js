csm.goodsManagement = function() {
	
	function init() {
		welcome();
	}
	function welcome(){
		$(".content_type").each(function(index,obj){
			if($(this).attr("val") == "true"){
				$(this).css("background","#2b2727");
			}
		})
		$.ajax({
			url: WEB_ROOT + '/goodsManagement/goodsManage!queryGoodsInit.do',
			type : 'post',
	        dataType : 'json',
			success : function(response){
				var goodList = response.goodsDTO.goodsList;
				var html = '';
				for(var i=0;i<goodList.length;i++){
					html += '<div class="goods_items"><div class="goods_items_img"><img src="'+WEB_ROOT+'/platform/image/'+goodList[i].goods_code+'.jpg" width="200px" height="200px"/></div>' 
					+'<div  class="goods_items_item" title="'+goodList[i].goods_name+'">商品名称： '+goodList[i].goods_name+'</div>'
					+'<div  class="goods_items_item">商品价格： ￥'+goodList[i].goods_price+'</div>'
					if(goodList[i].goods_num>0){
						html+='<div  class="goods_items_item">商品库存： '+goodList[i].goods_num+'</div>'
					}else{
						html+='<div  class="goods_items_item">商品库存： <font color="red">'+goodList[i].goods_num+' (请补充库存！)</font></div>'
					}
					html+='<div  class="goods_items_item">商品销量： '+goodList[i].goods_sales_num+'</div>'
					if(goodList[i].goods_num==0){
						html+='<div class="goods_items_item"><input type="text" style="width:100px;background-color:#e6e6fa;"id="sellNum'+i+'"disabled="true"/><button style="width:70px;height:36px;" disabled="true" onclick="csm.goodsManagement.sellOut(\''+i+'\',\''+goodList[i].goods_num+'\',\''+goodList[i].goods_sales_num+'\',\''+goodList[i].goods_price+'\',\''+goodList[i].goods_id+'\');">出售</button></div></div>'
					}else{
						html+='<div class="goods_items_item"><input type="text" style="width:100px;background-color:#e6e6fa;"id="sellNum'+i+'"/><button style="width:70px;height:36px;" onclick="csm.goodsManagement.sellOut(\''+i+'\',\''+goodList[i].goods_num+'\',\''+goodList[i].goods_sales_num+'\',\''+goodList[i].goods_price+'\',\''+goodList[i].goods_id+'\');">出售</button></div></div>'
					}
				}
				$("#content_right").html(html)
			},
			error : function(){
				alert("error")
			}
		});
	}
	function searchByType(obj,type){
		var checkedType = $(obj).text();
		$(".content_type").each(function(index,obj){
			if($(this).text() == checkedType){
				$(this).css("background","#2b2727");
				$(this).prop("val","true");
			}else{
				$(this).css("background","#27ae60");
				$(this).prop("val","false");
			}
		})
		if(type=="all"){
			welcome();
		}else{
			$.ajax({
			url: WEB_ROOT + '/goodsManagement/goodsManage!queryByType.do',
			type : 'post',
	        dataType : 'json',
	        data : {
				goodsType : type
	        },
			success : function(response){
				var goodList = response.goodsDTO.goodsList;
				var html = '';
				if(goodList.length>0){
					for(var i=0;i<goodList.length;i++){
					html += '<div class="goods_items"><div class="goods_items_img"><img src="'+WEB_ROOT+'/platform/image/'+goodList[i].goods_code+'.jpg" width="200px" height="200px"/></div>' 
					+'<div  class="goods_items_item" title="'+goodList[i].goods_name+'">商品名称： '+goodList[i].goods_name+'</div>'
					+'<div  class="goods_items_item">商品价格： ￥'+goodList[i].goods_price+'</div>'
					if(goodList[i].goods_num>0){
						html+='<div  class="goods_items_item">商品库存： '+goodList[i].goods_num+'</div>'
					}else{
						html+='<div  class="goods_items_item">商品库存： <font color="red">'+goodList[i].goods_num+' (请补充库存！)</font></div>'
					}
					html+='<div  class="goods_items_item">商品销量： '+goodList[i].goods_sales_num+'</div>'
					+'<div class="goods_items_item"><input type="text" style="width:100px;background-color:#e6e6fa;"id="sellNum"/><button style="width:70px;height:36px;" onclick="csm.goodsManagement.sellOut(\''+i+'\',\''+goodList[i].goods_num+'\',\''+goodList[i].goods_sales_num+'\',\''+goodList[i].goods_price+'\',\''+goodList[i].goods_id+'\');">出售</button></div></div>'
				}
				$("#content_right").html(html)
				}else{
					$("#content_right").html("<div style='width:900px;height:32px;color:red;line-height:32px;text-align:center;margin-top:30px;'>当前分类暂无商品信息！<div>")
				}
			},
			error : function(){
				alert("error")
			}
		});
		}
	}
	function queryByName(){
		var searchName = $("#search2").val();
		$(".content_type").each(function(index,obj){
			if($(this).text() == "全部"){
				$(this).css("background","#2b2727");
				$(this).prop("val","true");
			}else{
				$(this).css("background","#27ae60");
				$(this).prop("val","false");
			}
		})
		$.ajax({
			url: WEB_ROOT + '/goodsManagement/goodsManage!queryByName.do',
			type : 'post',
	        dataType : 'json',
	        data : {
				goodsName : searchName
	        },
			success : function(response){
				var goodList = response.goodsDTO.goodsList;
				var html = '';
				if(goodList.length>0){
					for(var i=0;i<goodList.length;i++){
						html += '<div class="goods_items"><div class="goods_items_img"><img src="'+WEB_ROOT+'/platform/image/'+goodList[i].goods_code+'.jpg" width="200px" height="200px"/></div>' 
						+'<div  class="goods_items_item" title="'+goodList[i].goods_name+'">商品名称： '+goodList[i].goods_name+'</div>'
						+'<div  class="goods_items_item">商品价格： ￥'+goodList[i].goods_price+'</div>'
						if(goodList[i].goods_num>0){
							html+='<div  class="goods_items_item">商品库存： '+goodList[i].goods_num+'</div>'
						}else{
							html+='<div  class="goods_items_item">商品库存： <font color="red">'+goodList[i].goods_num+' (请补充库存！)</font></div>'
						}
						html+='<div  class="goods_items_item">商品销量： '+goodList[i].goods_sales_num+'</div>'
						+'<div class="goods_items_item"><input type="text" style="width:100px;background-color:#e6e6fa;"id="sellNum"/><button style="width:70px;height:36px;" onclick="csm.goodsManagement.sellOut(\''+i+'\',\''+goodList[i].goods_num+'\',\''+goodList[i].goods_sales_num+'\',\''+goodList[i].goods_price+'\',\''+goodList[i].goods_id+'\');">出售</button></div></div>'
					}
				$("#content_right").html(html)
				}else{
					$("#content_right").html("<div style='width:900px;height:32px;color:red;line-height:32px;text-align:center;margin-top:30px;'>未检索到相关商品信息！<div>")
				}
			},
			error : function(){
				alert("error")
			}
		});
	}
	function sellOut(index,goodNum,saleNum,goodPrice,goodId){
		var sellNum = $("#sellNum"+index+"").val();
		var reg = /^[0-9]*$/;
		if(sellNum == null||sellNum == ""){
			alert("请输入售出数量！");
		}else{
			if(reg.test(sellNum)){
				if(parseInt(sellNum)>parseInt(goodNum)){
					alert("当前库存不足！")
				}else{
					var userRecord = (parseInt(sellNum) * parseInt(goodPrice));
					var goodssaleNum = (parseInt(saleNum) + parseInt(sellNum));
					var goodsSurplus = (parseInt(goodNum) - parseInt(sellNum));
					updateGoods(goodsSurplus,goodssaleNum,goodId);
					updateUsers(userRecord);
				}
			}else{
				alert("请输入数字！");
				$("#sellNum"+index+"").val("");
			}
		}
	}
	function updateGoods(goodsSurplus,goodssaleNum,goodId){
		$.ajax({
			url: WEB_ROOT + '/goodsManagement/goodsManage!goodsSelled.do',
			type : 'post',
	        dataType : 'json',
	        data : {
				"goods.goods_num" : goodsSurplus,
				"goods.goods_sales_num" : goodssaleNum,
				"goods.goods_id" : goodId
	        },
			success : function(response){
	        	welcome();
	        	alert("售出成功！");
			},
			error : function(){
				alert("售出失败！")
			}
		});
	}
	function updateUsers(userRecord){
		$.ajax({
			url: WEB_ROOT + '/adminManagement/adminManage!updateUserSelled.do',
			type : 'post',
	        dataType : 'json',
	        data : {
				"user.user_record" : userRecord
	        },
			success : function(response){
			},
			error : function(){
			}
		});
	}
	return {
		init : init, //初始化方法
		welcome : welcome,
		searchByType : searchByType,
		queryByName : queryByName,
		sellOut : sellOut,
		updateGoods : updateGoods,
		updateUsers : updateUsers
	}

}();


$(function() {
	csm.goodsManagement.init();
}); 