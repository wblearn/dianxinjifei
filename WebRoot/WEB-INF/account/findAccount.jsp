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
            //开通
            function startAccount(id) {
            	//先提示用户是否要开通
                var r = window.confirm("确定要开通此账务账号吗？");
                //如果用户选择了确定开通
                if(r) {
                	//发异步请求，来做开通
                	$.post(
                		"startAccount",
                		{"id":id},
                		function(data){
                			//data用于封装返回值，当前为Action中的ok
                			var ok = data;
                			if(ok) {
                				$("#hintMsg").text("开通成功！");
                				document.getElementById("operate_result_info").style.display = "block";
                				//如果开通成功，应该刷新当前页面
                				var currPage = $("#currentPage").val();
                				setTimeout(function(){
                					//延迟2秒刷新页面，刷新页面要提交表单，带上查询条件和分页信息
                					toPage(currPage);
                				},2000);
                			} else {
                				$("#hintMsg").text("开通失败！");
	                			//操作完成后，打开提示信息
	                			document.getElementById("operate_result_info").style.display = "block";
	                			setTimeout(function(){
	                				//延迟2秒，关闭提示信息，造成提示信息显示2秒后自动关闭的效果
	                				document.getElementById("operate_result_info").style.display = "none";
	                			},2000);
                			}
                		}
                	);
                }
            }
             //暂停
            function pauseAccount(id) {
            	//先提示用户是否要暂停
                var r = window.confirm("确定要暂停此账务账号吗？");
                //如果用户选择了确定暂停
                if(r) {
                	//发异步请求，来做暂停
                	$.post(
                		"pauseAccount",
                		{"id":id},
                		function(data){
                			//data用于封装返回值，当前为Action中的ok
                			var ok = data;
                			if(ok) {
                				$("#hintMsg").text("暂停成功！");
                				document.getElementById("operate_result_info").style.display = "block";
                				//如果暂停成功，应该刷新当前页面
                				var currPage = $("#currentPage").val();
                				setTimeout(function(){
                					//延迟2秒刷新页面，刷新页面要提交表单，带上查询条件和分页信息
                					toPage(currPage);
                				},2000);
                			} else {
                				$("#hintMsg").text("暂停失败！");
	                			//操作完成后，打开提示信息
	                			document.getElementById("operate_result_info").style.display = "block";
	                			setTimeout(function(){
	                				//延迟2秒，关闭提示信息，造成提示信息显示2秒后自动关闭的效果
	                				document.getElementById("operate_result_info").style.display = "none";
	                			},2000);
                			}
                		}
                	);
                }
            }
            //删除
            function deleteAccount(id) {
            	//先提示用户是否要删除
                var r = window.confirm("确定要删除此账务账号吗？");
                //如果用户选择了确定删除
                if(r) {
                	//发异步请求，来做删除
                	$.post(
                		"deleteAccount",
                		{"id":id},
                		function(data){
                			//data用于封装返回值，当前为Action中的ok
                			var ok = data;
                			if(ok) {
                				$("#hintMsg").text("删除成功！");
                				document.getElementById("operate_result_info").style.display = "block";
                				//如果删除成功，应该刷新当前页面
                				var currPage = $("#currentPage").val();
                				setTimeout(function(){
                					//延迟2秒刷新页面，刷新页面要提交表单，带上查询条件和分页信息
                					toPage(currPage);
                				},2000);
                			} else {
                				$("#hintMsg").text("删除失败！");
	                			//操作完成后，打开提示信息
	                			document.getElementById("operate_result_info").style.display = "block";
	                			setTimeout(function(){
	                				//延迟2秒，关闭提示信息，造成提示信息显示2秒后自动关闭的效果
	                				document.getElementById("operate_result_info").style.display = "none";
	                			},2000);
                			}
                		}
                	);
                }
            }
            
            //分页跳转
            function toPage(currentPage) {
            	document.getElementById("currentPage").value = currentPage;
            	//setTimeout(function(){
            	//	document.forms[0].submit();
            	//},3000);
            	document.forms[0].submit();
            }
        </script>
    </head>
    <body>
        <!--Logo区域开始-->
        <div id="header">
            <img src="../images/logo.png" alt="logo" class="left"/>
            <%=session.getAttribute("admin") %><a href="#">[退出]</a>            
        </div>
        <!--Logo区域结束-->
        <!--导航区域开始-->
        <div id="navi">                        
            <ul id="menu">
                <li><a href="../index.html" class="index_off"></a></li>
                <li><a href="../role/findRole" class="role_off"></a></li>
                <li><a href="../admin/findAdmin" class="admin_off"></a></li>
                <li><a href="../cost/findCost" class="fee_off"></a></li>
                <li><a href="../account/findAccount" class="account_on"></a></li>
                <li><a href="../service/findService" class="service_off"></a></li>
                <li><a href="../bill/bill_list.html" class="bill_off"></a></li>
                <li><a href="../report/report_list.html" class="report_off"></a></li>
                <li><a href="../user/user_info.html" class="information_off"></a></li>
                <li><a href="../user/user_modi_pwd.html" class="password_off"></a></li>
            </ul>            
        </div>
        <!--导航区域结束-->
        <!--主要区域开始-->
        <div id="main">
            <form action="findAccount" method="post">
            	<!-- 隐藏文本框，用于传递页码 -->
            	<%--
            	<input type="hidden" name="page" id="currentPage"/>
                --%>
                <!-- 
                	用struts2标签来写hidden，这样可以让page的值在
                	页面刷新后回显过来。
                 -->
                <s:hidden name="page" id="currentPage"></s:hidden>
                <!--查询-->
                <div class="search_add">                        
                    <div>
                    	身份证：
                    	<s:textfield name="idcardNo" cssClass="text_search"/>
                    </div>                            
                    <div>
                    	姓名：
                    	<s:textfield name="realName" cssClass="width70 text_search"></s:textfield>
                    </div>
                    <div>
                    	登录名：
                    	<s:textfield name="loginName" cssClass="text_search"></s:textfield>
                    </div>
                    <div>
                    状态：
                    	<s:select name="status" list="#{'-1':'全部','0':'开通','1':'暂停','2':'删除'}" cssClass="select_search"></s:select>
                    </div>
                    <div><input type="button" value="搜索" class="btn_search" onclick="toPage(1);"/></div>
                    <input type="button" value="增加" class="btn_add" onclick="location.href='toAddAccount';" />
                </div>  
               </form>
                <!--删除等的操作提示-->
                <div id="operate_result_info" class="operate_success">
                    <img src="../images/close.png" onclick="this.parentNode.style.display='none';" />
                    <span id="hintMsg">删除成功，且已删除其下属的业务账号！</span>
                </div>   
                <!--数据区域：用表格展示数据-->     
                <div id="data">            
                    <table id="datalist">
                    <tr>
                        <th>账号ID</th>
                        <th>姓名</th>
                        <th class="width150">身份证</th>
                        <th>登录名</th>
                        <th>状态</th>
                        <th class="width100">创建日期</th>
                        <th class="width150">上次登录时间</th>                                                        
                        <th class="width200"></th>
                    </tr>
                    <s:iterator value="accounts">
	                    <tr>
	                        <td><s:property value="id"/></td>
	                        <td><a href="account_detail.html"><s:property value="realName"/></a></td>
	                        <td><s:property value="idcardNo"/></td>
	                        <td><s:property value="loginName"/></td>
	                        <td>
	                        	<s:if test="status==0">
	                        		开通
	                        	</s:if>
	                        	<s:elseif test="status==1">
	                        		暂停
	                        	</s:elseif>
	                        	<s:else>删除</s:else>
	                        </td>
	                        <td><s:date name="createDate" format="yyyy-MM-dd"/></td>
	                        <td><s:property value="lastLoginTime"/></td>                            
	                        <td class="td_modi">
	                        	<!-- 
	                        		开通时显示暂停、修改、删除按钮；
	                        		暂停时显示开通、修改、删除按钮；
	                        		删除时不显示任何按钮；
	                        	 -->
	                        	<s:if test="status==0">
	                        		<input type="button" value="暂停" class="btn_pause" onclick="pauseAccount(<s:property value="id"/>);" />
	                            	<input type="button" value="修改" class="btn_modify" onclick="location.href='toUpdateAccount?id=<s:property value="id"/>" />
	                            	<input type="button" value="删除" class="btn_delete" onclick="deleteAccount(<s:property value="id"/>);" />
	                        	</s:if>
	                        	<s:elseif test="status==1">
	                        		<input type="button" value="开通" class="btn_start" onclick="startAccount(<s:property value="id"/>);" />
	                            	<input type="button" value="修改" class="btn_modify" onclick="location.href='toUpdateAccount?id=<s:property value="id"/>';" />
	                            	<input type="button" value="删除" class="btn_delete" onclick="deleteAccount(<s:property value="id"/>);" />
	                        	</s:elseif>
	                        	<s:else>
	                        		
	                        	</s:else>
	                        </td>
	                    </tr>
                    </s:iterator>       
                </table>
                <p>业务说明：<br />
                1、创建则开通，记载创建时间；<br />
                2、暂停后，记载暂停时间；<br />
                3、重新开通后，删除暂停时间；<br />
                4、删除后，记载删除时间，标示为删除，不能再开通、修改、删除；<br />
                5、暂停账务账号，同时暂停下属的所有业务账号；<br />                
                6、暂停后重新开通账务账号，并不同时开启下属的所有业务账号，需要在业务账号管理中单独开启；<br />
                7、删除账务账号，同时删除下属的所有业务账号。</p>
                </div>                   
                <!--分页-->
                <div id="pages">
                    <a href="javascript:toPage(1);">首页</a>
                    
                    <s:if test="page==1">
        	        	<a href="#">上一页</a>
                    </s:if>
                    <s:else>
                    	<a href="javascript:toPage(<s:property value="page-1"/>);">上一页</a>
                    </s:else>
                    
                    <s:iterator begin="1" end="totalPage" var="i">
                    	<s:if test="#i==page">
	                    	<a href="javascript:toPage(<s:property value="#i"/>);" class="current_page"><s:property value="#i"/></a>
                   		</s:if>
                   		<s:else>
                   			<a href="javascript:toPage(<s:property value="#i"/>);"><s:property value="#i"/></a>
                   		</s:else>
                    </s:iterator>
                    
                    <s:if test="page==totalPage">
                    	<a href="#">下一页</a>
                    </s:if>
                    <s:else>
                    	<a href="javascript:toPage(<s:property value="page+1"/>);">下一页</a>
                    </s:else>
                    <a href="javascript:toPage(<s:property value="totalPage"/>)">末页</a>
                </div>                    
        </div>
        <!--主要区域结束-->
        <div id="footer">
            <p>[源自北美的技术，最优秀的师资，最真实的企业环境，最适用的实战项目]</p>
            <p>版权所有(C)加拿大达内IT培训集团公司 </p>
        </div>
    </body>
</html>