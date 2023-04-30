<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>


<div class="workingArea">
    <h1 class="label label-info" >管理</h1>
    <br>
    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>图片</th>
                <th>分类名称</th>
                <th>属性管理</th>
                <th>产品管理</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items=" " var="c">

                <tr>
                    <td> </td>
                    <td><img height="40px" src="img/category/73.jpg"></td>
                    <td> </td>

                    <td><a href="admin_property_list?cid= "><span class="glyphicon glyphicon-th-list"></span></a></td>
                    <td><a href="admin_product_list?cid= "><span class="glyphicon glyphicon-shopping-cart"></span></a></td>
                    <td><a href="admin_category_edit?id= "><span class="glyphicon glyphicon-edit"></span></a></td>
                    <td><a deleteLink="true" href="admin_category_delete?id= "><span class=" 	glyphicon glyphicon-trash"></span></a></td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp" %>
    </div>

    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增分类</div>
        <div class="panel-body">
            <form method="post" id="addForm" action="admin_category_add" enctype="multipart/form-data">
                <table class="addTable">
                    <tr>
                        <td>分类名称</td>
                        <td><input  id="name" name="name" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>分类圖片</td>
                        <td>
                            <input id="categoryPic" accept="image/*" type="file" name="filepath" />
                        </td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

</div>