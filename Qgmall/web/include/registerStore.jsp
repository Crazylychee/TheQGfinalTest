<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<script>
    $(function(){

        <c:if test="${!empty msg}">
        $("span.errorMessage").html("${msg}");
        $("div.registerErrorMessageDiv").css("visibility","visible");
        </c:if>

        $(".registerForm").submit(function(){
            if(0==$("#name").val().length){
                $("span.errorMessage").html("请输入用户名");
                $("div.registerErrorMessageDiv").css("visibility","visible");
                return false;
            }
            if(0==$("#password").val().length){
                $("span.errorMessage").html("请输入密码");
                $("div.registerErrorMessageDiv").css("visibility","visible");
                return false;
            }
            if(0==$("#repeatpassword").val().length){
                $("span.errorMessage").html("请输入重复密码");
                $("div.registerErrorMessageDiv").css("visibility","visible");
                return false;
            }
            if($("#password").val() !=$("#repeatpassword").val()){
                $("span.errorMessage").html("重复密码不一致");
                $("div.registerErrorMessageDiv").css("visibility","visible");
                return false;
            }

            return true;
        });
    })
</script>



<form method="post" action="foreregisterS" class="registerStoreForm">

    <div class="registerDiv">
<%--        <div class="registerErrorMessageDiv">--%>
<%--            <div class="alert alert-danger" role="alert">--%>
<%--                <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>--%>
<%--                <span class="errorMessage"></span>--%>
<%--            </div>--%>
<%--        </div>--%>
        <button class="registerTable" align="center"><img src="img/user_small/${user.id}.jpg"/></button>


        <table class="registerTable" align="center">
            <tr>
                <td  class="registerTip registerTableLeftTD">设置店铺名</td>
                <td></td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">店铺名</td>
                <td  class="registerTableRightTD"><input id="name" name="name" placeholder="店铺名一旦设置成功，无法修改" > </td>
            </tr>
            <tr>
                <td  class="registerTip registerTableLeftTD">设置店铺描述</td>
                <%--                <td  class="registerTableRightTD">登陆时验证，保护账号信息</td>--%>
            </tr>
            <tr>
                <td class="registerTableLeftTD">店铺描述</td>
                <td class="registerTableRightTD"><input id="description" name="description" type="description"  placeholder="设置你的店铺描述" > </td>
            </tr>
            <%--            <tr>--%>
            <%--                <td class="registerTableLeftTD"></td>--%>
            <%--                <td class="registerTableRightTD"><input id="repeatpassword" type="password"   placeholder="请再次输入你的密码" > </td>--%>
            <%--            </tr>--%>


            <%--		是HTML表格元素的一个属性，它表示单元格应该横跨两列--%>

            <tr>
                <td colspan="2" class="registerButtonTD">
                    <a href="registerSuccess.jsp"><button>提交 开店</button></a>
                </td>
            </tr>
        </table>
    </div>
</form>
