//错误提示
function showError(id,msg) {
	$("#"+id+"Ok").hide();
	$("#"+id+"Err").html("<i></i><p>"+msg+"</p>");
	$("#"+id+"Err").show();
	$("#"+id).addClass("input-red");
}
//错误隐藏
function hideError(id) {
	$("#"+id+"Err").hide();
	$("#"+id+"Err").html("");
	$("#"+id).removeClass("input-red");
}
//显示成功
function showSuccess(id) {
	$("#"+id+"Err").hide();
	$("#"+id+"Err").html("");
	$("#"+id+"Ok").show();
	$("#"+id).removeClass("input-red");
}


//打开注册协议弹层
function alertBox(maskid,bosid){
	$("#"+maskid).show();
	$("#"+bosid).show();
}
//关闭注册协议弹层
function closeBox(maskid,bosid){
	$("#"+maskid).hide();
	$("#"+bosid).hide();
}

//追踪变量
var phone_tag=0;

//注册协议确认
$(function() {
	$("#agree").click(function(){
		var ischeck = document.getElementById("agree").checked;
		if (ischeck) {
			$("#btnRegist").attr("disabled", false);
			$("#btnRegist").removeClass("fail");
		} else {
			$("#btnRegist").attr("disabled","disabled");
			$("#btnRegist").addClass("fail");
		}
	});


	$("#phone").blur(function () {
		phone_tag=0;
		var phone=$.trim($("#phone").val());
		//不能为空
		if(phone==null||phone==""){
			showError("phone","请输入手机号码");
			return ;
		}

		//长度
		if(phone.length!=11){
			showError("phone","手机号码长度不正确");
			return ;
		}

		//格式:正则表达式
		if(!/^1[1-9]\d{9}$/.test(phone)){
			showError("phone","手机号码格式不正确");
			return ;
		}

		$.get("/005-money-web/loan/page/checkPhone", { phone: phone },
			function(data){
				if(data.code==0){
					//alert("0000");
					showError("phone",data.message);
					phone_tag=0;
					return ;
				}

				if(data.code==1){
					//alert("1111");
					showSuccess("phone");
					phone_tag=1;
					//彻底解决问题：课后完成！
					// if(phone_tag==1&&loginPassword_tag==1&&btnRegist_tag==1){
					// 	alert("提交数据");
					// 	btnRegist_tag=0;
					// }

					return ;
				}
		});
		showSuccess("phone");
		phone_tag=1;
		return true;
	});

	var loginPassword_tag=0;
	$("#loginPassword").blur(function () {
		loginPassword_tag=0;
		var loginPassword=$.trim($("#loginPassword").val());
		//不能为空
		if(loginPassword==null||loginPassword==""){
			showError("loginPassword","请输入密码..");
			return ;
		}

		//长度
		if(loginPassword.length<6||loginPassword.length>20){
			showError("loginPassword","请输入6-20位密码");
			return ;
		}

		//密码字符只可使用数字和大小写英文字母
		if(!/^[0-9a-zA-Z]+$/.test(loginPassword)){
			showError("loginPassword","密码只可使用数字和大小写英文字母");
			return ;
		}

		//密码应同时包含英文和数字
		if(!/^(([a-zA-Z]+[0-9]+)|([0-9]+[a-zA-Z]+))[a-zA-Z0-9]*/.test(loginPassword)){
			showError("loginPassword","密码应同时包含英文和数字");
			return ;
		}

		showSuccess("loginPassword");
		loginPassword_tag=1;
		return true;
	});

	//发短信
	$("#messageCodeBtn").click(function () {
		//验证：
		$("#phone").blur();
		$("#loginPassword").blur();
		if(phone_tag==1&&loginPassword_tag==1){
			var phone=$.trim($("#phone").val());
			var _this=$(this);
			if(!$(this).hasClass("on")){
				$.get("/005-money-web/loan/page/messageCode", { phone: phone },
					function(data){
						alert( data.message);
						if(data.code==1){
							$.leftTime(60,function(d){
								if(d.status){
									_this.addClass("on");
									_this.html((d.s=="00"?"60":d.s)+"秒后重新获取");
								}else{
									_this.removeClass("on");
									_this.html("获取验证码");
								}
							});
						}
					});
			}

		}
	});


	//课后：完成
	var messageCode_tag=0;
	$("#messageCode").blur(function () {
		messageCode_tag=0;
		//不能为空
		//长度
		//必须是数字

		messageCode_tag=1;

	});

	var btnRegist_tag=0;
	$("#btnRegist").click(function () {
		btnRegist_tag=1;
		//验证：
		/**
		 * 模拟事件提交：
		 *  form.submit();   but.click();
		 *
		 *  f1(){
		 *      var ret=f2();
		 *		return "ok";
		 *  }
		 *
		 * f2(){
		 *     return true;
		 * }
		 *
		 */
		var ret_phone=$("#phone").blur();
		var ret_loginPassword=$("#loginPassword").blur();
		//alert(ret_phone);
		//alert(ret_loginPassword);
		//console.log(ret_phone);
		//console.log(ret_loginPassword);
		//alert("phone_tag:"+phone_tag);
		//alert("loginPassword_tag:"+loginPassword_tag);

		if(phone_tag==1&&loginPassword_tag==1&&btnRegist_tag==1&&messageCode_tag==1){
			var phone=$.trim($("#phone").val());
			var loginPassword=$.trim($("#loginPassword").val());
			var messageCode=$.trim($("#messageCode").val());

			var url = "/005-money-web/loan/page/registerSubmit";
			var data = { phone: phone, loginPassword: $.md5(loginPassword), messageCode: messageCode };

			$.ajax({
				url:url,
				type:'POST',
				contentType:'application/json',
				data:JSON.stringify(data),
				success: function (result) {
					alert(result.message);
				},
				error: function (result) {
					window.location.href="/005-money-web/loan/page/realName";
				}
			})

			// $.post(url,data ,
			// 	function(data){
			// 		if(result.code==1){
			// 			// window.location.href="/005-money-web/index";
			// 			window.location.href="/005-money-web/loan/page/realName";
			// 		}
			// 		if(result.code==0){
			// 			alert(data.message);
			// 		}
			// },"json");

			btnRegist_tag=0;
		}
	});
	

});
