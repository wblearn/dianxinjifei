<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>达内－NetCTOSS</title>
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global_color.css" />
        <script language="javascript" type="text/javascript" src="../js/jquery-1.4.3.js"></script>
        <script language="javascript" type="text/javascript">
        
            //仅保存，不验证
            function save(){
            	document.forms[0].submit();
            }
            //保存成功的提示信息
            function showResult() {
                showResultDiv(true);
                window.setTimeout("showResultDiv(false);", 3000);
            }
            function showResultDiv(flag) {
                var divResult = document.getElementById("save_result_info");
                if (flag)
                    divResult.style.display = "block";
                else
                    divResult.style.display = "none";
            }

            //自动查询账务账号
            function searchAccount() {
            	var idcardNo = $("#idcardNo").val();
            	//1.校验身份证是否为空
            	if(idcardNo==""){
            		$("#idcardNoMsg").text("身份证号码不能为空！");
            		return;
            	}
            	//2.校验身份证格式
            	var reg = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
            	if(!reg.test(idcardNo)) {
            		//不符合规定的身份证格式，给予提示
            		$("#idcardNoMsg").text("身份证号格式错误！");
            		$("#birthday").val("");
            		return;
            	}
            	
            	/*
            	* 3.异步查询账务账号，复用账务账号中的Action，
            	* 由于是跨namespace的引用，这里写URL需要注意，
            	* 写成绝对路径。
            	*/
            	$.post(
            		"/stt/account/searchRecommender",
            		{"idcardNo":idcardNo},
            		function(data) {
            			var account = data;
            			if(account == null) {
            				//如果找不到账务账号，给予提示
            				$("#idcardNoMsg").text("找不到对应的账务账号！");;
            			} else {
            				//找到了，设置隐藏ID值，及账务账号的值
            				$("#accountId").val(account.id);
            				$("#loginName").val(account.loginName);
            				//给予正确的提示
            				$("#idcardNoMsg").text("身份证输入有效！");
            			}
            		}
            	);
            }
            function checkPwd(){
            	var pwd1=$("#pwd1").val();
            	var pwd2=$("#pwd2").val();
            	if(pwd1==""){
            		$("#pwd_msg2").text("");
            		$("#pwd_msg1").text("密码不能为空！");
            		return;
            	}else if(pwd1!=pwd2){
            		$("#pwd_msg1").text("");
            		$("#pwd_msg2").text("两次密码必须相同！");
            	}else{
            		$("#pwd_msg2").text("密码格式正确！");
            	}
            	
            }
        </script>
    </head>
    <body>
        <!--Logo区域开始-->
        <div id="header">
            <img src="../images/logo.png" alt="logo" class="left"/>
            <a href="#">[退出]</a>            
        </div>
        <!--Logo区域结束-->
        <!--导航区域开始-->
        <div id="navi">
            <ul id="menu">
                <li><a href="../index.html" class="index_off"></a></li>
                <li><a href="../role/role_list.html" class="role_off"></a></li>
                <li><a href="../admin/admin_list.html" class="admin_off"></a></li>
                <li><a href="../fee/fee_list.html" class="fee_off"></a></li>
                <li><a href="../account/account_list.html" class="account_off"></a></li>
                <li><a href="../service/service_list.html" class="service_on"></a></li>
                <li><a href="../bill/bill_list.html" class="bill_off"></a></li>
                <li><a href="../report/report_list.html" class="report_off"></a></li>
                <li><a href="../user/user_info.html" class="information_off"></a></li>
                <li><a href="../user/user_modi_pwd.html" class="password_off"></a></li>
            </ul>
        </div>
        <!--导航区域结束-->
        <!--主要区域开始-->
        <div id="main">
            <!--保存操作的提示信息-->
            <div id="save_result_info" class="save_fail">保存失败！192.168.0.23服务器上已经开通过 OS 账号 “mary”。</div>
            <form action="addService" method="post" class="main_form">
                <!--内容项-->
                <div class="text_info clearfix"><span>身份证：</span></div>
                <div class="input_info">
                	<input type="hidden" name="service.accountId" id="accountId"/>
                    <input type="text" name="service.idcardNo"class="width180" id="idcardNo"/>
                    <input type="button" value="查询账务账号" class="btn_search_large" onclick="searchAccount();"/>
                    <span class="required">*</span>
                    <div class="validate_msg_short" id="idcardNoMsg">请输入身份证</div>
                </div>
                <div class="text_info clearfix"><span>账务账号：</span></div>
                <div class="input_info">
                    <input type="text"id="loginName" class="readonly" readonly="true" />
                    <span class="required">*</span>
                    <div class="validate_msg_long"></div>
                </div>
                <div class="text_info clearfix"><span>资费类型：</span></div>
                <div class="input_info">
                   <s:select name="service.costId" list="costs" listKey="id" listValue="name"></s:select>                   
                </div> 
                <div class="text_info clearfix"><span>服务器 IP：</span></div>
                <div class="input_info">
                    <input type="text" name="service.ip" />
                    <span class="required">*</span>
                    <div class="validate_msg_medium">15 长度，符合IP的地址规范</div>
                </div>                   
                <div class="text_info clearfix"><span>登录 OS 账号：</span></div>
                <div class="input_info">
                    <input type="text" name="service.os_username" />
                    <span class="required">*</span>
                    <div class="validate_msg_medium">8长度以内的字母、数字和下划线的组合</div>
                </div>
                <div class="text_info clearfix"><span>密码：</span></div>
                <div class="input_info">
                    <input type="password" name="service.password" id="pwd1" onblur="checkPwd()" value="" />
                    <span class="required">*</span>
                    <div class="validate_msg_medium" id="pwd_msg1">30长度以内的字母、数字和下划线的组合</div>
                </div>
                <div class="text_info clearfix"><span>重复密码：</span></div>
                <div class="input_info">
                    <input type="password" id="pwd2" onblur="checkPwd()" />
                    <span class="required">*</span>
                    <div class="validate_msg_medium" id="pwd_msg2">两次密码必须相同</div>
                </div>     
                <!--操作按钮-->
                <div class="button_info clearfix">
                    <input type="button" value="保存" class="btn_save" onclick="save();" />
                    <input type="button" value="取消" class="btn_save" />
                </div>
            </form>
        </div>
        <!--主要区域结束-->
        <div id="footer">
            <span>[源自北美的技术，最优秀的师资，最真实的企业环境，最适用的实战项目]</span>
            <br />
            <span>版权所有(C)加拿大达内IT培训集团公司 </span>
        </div>
    </body>
</html>