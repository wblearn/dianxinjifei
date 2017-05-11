<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>达内－NetCTOSS</title>
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global_color.css" /> 
        <script language="javascript" type="text/javascript">
            function deleteRole() {
                var r = window.confirm("确定要删除此角色吗？");
                document.getElementById("operate_result_info").style.display = "block";
            }
            function toPage(page){
            	document.getElementById("current_page").value=page;
            	document.forms[0].submit();
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
                <li><a href="../role/role_list.html" class="role_on"></a></li>
                <li><a href="../admin/findAdmin" class="admin_off"></a></li>
                <li><a href="../cost/findCost" class="fee_off"></a></li>
                <li><a href="../account/findAccount" class="account_off"></a></li>
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
            <form action="findRole" method="post">
            <s:hidden name="page" id="current_page" ></s:hidden>
            </form>
                <!--查询-->
                <div class="search_add">
                    <input type="button" value="增加" class="btn_add" onclick="location.href='toAddRole';" />
                </div>  
                <!--删除的操作提示-->
                <div id="operate_result_info" class="operate_success">
                    <img src="../images/close.png" onclick="this.parentNode.style.display='none';" />
                    删除成功！
                </div> <!--删除错误！该角色被使用，不能删除。-->
                <!--数据区域：用表格展示数据-->     
                <div id="data">                      
                    <table id="datalist">
                        <tr>                            
                            <th>角色 ID</th>
                            <th>角色名称</th>
                            <th class="width600">拥有的权限</th>
                            <th class="td_modi"></th>
                        </tr>
                        <s:iterator value="roles">
                        <tr>
                        	<td><s:property value="id"/></td>
                        	<td><s:property value="name"/></td>
                        	<td><s:property value="modulesName"/></td>
                        	<td>
                        		<input type="button" value="修改" class="btn_modify" onclick="location.href='toUpdateRole?id=<s:property value="id"/>';"/>
                            	<input type="button" value="删除" class="btn_delete" onclick="deleteRole();" />
                        	</td>
                        </tr>    
                        </s:iterator>
                    </table>
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