<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<script>
$(function(){
    // 页面加载事件：解除不可点击的按键状态
    $(".star").click(function(){
        var page = "forecheckLogin";
        $.get(
            page,
            function(result){
                if("success"==result){
                    var starpage = "forestaradd";
                    $.get(
                        starpage,
                        function(result){
                            if("success"==result){
                                alert("关注成功！！")
                            }
                            else{
                                alert("您已经关注过了，请不要重复关注！！")
                            }
                        }
                    );
                }
                else{
                    $("#loginModal").modal('show');
                }
            }
        );
        return false;
    });
    $(".starD").click(function(){
        var page = "forecheckLogin";
        $.get(
            page,
            function(result){
                if("success"==result){
                    var starpage = "forestardelete";
                    $.get(
                        starpage,
                        function(result){
                            if("success"==result){
                                alert("取消关注成功！！")
                            }
                            else{
                                alert("您已经取消关注过了，请不要重复取消关注！！")
                            }
                        }
                    );
                }
                else{
                    $("#loginModal").modal('show');
                }
            }
        );
        return false;
    });
    $("button.loginSubmitButton").click(function(){
        var name = $("#name").val();
        var password = $("#password").val();

        if(0==name.length||0==password.length){
            $("span.errorMessage").html("请输入账号密码");
            $("div.loginErrorMessageDiv").show();
            return false;
        }

        var page = "foreloginAjax";
        $.get(
            page,
            {"name":name,"password":password},
            function(result){
                if("success"==result){
                    location.reload();
                }
                else{
                    $("span.errorMessage").html("账号密码错误");
                    $("div.loginErrorMessageDiv").show();
                }
            }
        );

        return true;
    });

});



</script>

<div>
    <%--            添加店铺的信息，加上一个关注按钮，并点击店铺头像可以跳转到查看店铺商品--%>
    <div>
        <button><img src="img/user_small/${s.uid}.jpg"/></button>
        <div>
            <p class="site-author-name" itemprop="name">店铺名：${s.name}</p>
            <p class="site-author-name" itemprop="name">店铺描述：${s.description}</p>
            <p class="site-author-name" itemprop="name">粉丝数：${s.fans}</p>
        </div>
        <a href="forestar" class="star"><button class="starButton"><span class="glyphicon glyphicon-shopping-cart"></span>关注店铺</button></a>
        <a href="forestar" class="starD"><button class="starButton"><span class="glyphicon glyphicon-shopping-cart"></span>取消关注店铺</button></a>
        <a href="chat.jsp" class="chatwindow"><button class="starButton"><span class="glyphicon glyphicon-shopping-cart"></span>店铺客服</button></a>
    </div>
</div>