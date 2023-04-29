

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<title>Welcome to qgmall!! ${p.name}</title>
<div class="categoryPictureInProductPageDiv">
	<img class="categoryPictureInProductPage" src="img/category/${p.category.id}.jpg">
</div>

<div class="productPageDiv">
<%--		产品的图片--%>
	<%@include file="imgAndInfo.jsp" %>
<%--	产品的评价--%>
	<%@include file="productReview.jsp" %>
<%--	产品的细节，详情描述--%>
	<%@include file="productDetail.jsp" %>
</div>