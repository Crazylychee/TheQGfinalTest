

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<nav class="top ">
		<a href="${contextPath}">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
			qg购物网首页
		</a>	
		
		<span>喵，欢迎来qg购物网</span>
		
		<c:if test="${!empty user}">
			<a href="login.jsp">${user.name}</a>
			<a href="forelogout">退出</a>		
		</c:if>
		
		<c:if test="${empty user}">
			<a href="login.jsp">请登录</a>
			<a href="register.jsp">免费注册</a>		
		</c:if>


		<span class="pull-right">
<%--		这里不直接跳转jsp界面是为了让过滤器过滤掉没登陆的请求--%>
			<a href="forebought">我的订单</a>
			<c:if test="${!empty store}">
				<a href="forestorepage">我的店铺</a>
			</c:if>
			<c:if test="${empty store}">
				<a href="foreregisterstore">商家入驻</a>
			</c:if>
			<a href="forepersonpage">个人中心</a>
			<a href="forecart">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
			购物车<strong>${cartTotalItemNumber}</strong>件</a>		
		</span>
		
		
</nav>



