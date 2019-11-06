<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;utf-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!--引入BootStrap的css样式-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/boot/css/bootstrap.css">
    <!--BootStrap与JQGRID整合后的css样式-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <!--引入jquery的js文件-->
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-3.3.1.min.js"></script>
    <!--引入BootStrap的js文件-->
    <script src="${pageContext.request.contextPath}/statics/boot/js/bootstrap.js"></script>
    <!--jqgrid与bootstrap整合的国际化的js文件-->
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <!--jqgrid与bootstrap整合的js文件-->
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <!--jqgrid文件上传的js文件-->
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/ajaxfileupload.js"></script>
    <!--错误信息的js文件-->
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <!--KindEditor的js文件-->
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <!--echarts的js文件-->
    <script charset="utf-8" src="${pageContext.request.contextPath}/statics/user/js/echarts.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/statics/user/js/china.js"></script>
    <!--goeasy的js文件-->
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.0.js"></script>
</head>
<body>
<%--顶部导航条--%>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持明法洲后台管理系统</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a >欢迎：${sessionScope.loginAdmin.name}</a></li>
                <li><a href="${pageContext.request.contextPath}/Admin/exit">安全退出</a></li>
            </ul>
        </div>
    </div>
</nav>
<%--左侧栅格--%>
<div class="col-xs-2">
    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingOne">
                <h4 class="panel-title text-center">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        <h4>轮播图管理</h4>
                    </a>
                </h4>
            </div>
            <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                <div class="panel-body text-center">
                    <a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/statics/chart/chart-show.jsp')" class="btn btn-default">轮播图详情</a>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwo">
                <h4 class="panel-title text-center">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                        <h4>专辑管理</h4>
                    </a>
                </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                <div class="panel-body text-center">
                    <a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/statics/album/album-show.jsp')" class="btn btn-default">专辑详情</a>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingThree">
                <h4 class="panel-title text-center">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                        <h4>文章管理</h4>
                    </a>
                </h4>
            </div>
            <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                <div class="panel-body text-center">
                    <a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/statics/article/article-show.jsp')" class="btn btn-default" >文章详情</a>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingFour">
                <h4 class="panel-title text-center">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                        <h4>用户管理</h4>
                    </a>
                </h4>
            </div>
            <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                <div class="panel-body text-center">
                    <a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/statics/user/user-show.jsp')" class="btn btn-default">用户详情</a>
                </div>
                <div class="panel-body text-center">
                    <a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/statics/user/user-province.jsp')" class="btn btn-default">用户分布详情</a>
                </div>
                <div class="panel-body text-center">
                    <a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/statics/user/user-createDate.jsp')" class="btn btn-default">创建时间详情</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="col-xs-10">
    <div id="centerLayout">
        <div class="jumbotron">
            <h2 style="padding-left: 100px">持明法洲后台管理系统!</h2>
        </div>
        <img src="${pageContext.request.contextPath}/statics/image/shouye.png" alt="">
    </div>
</div>
<%--底部页脚--%>
<div class="panel panel-footer text-center">
    <h4>持明法洲后台管理系统@百知教育2019-10-25</h4>
</div>
<c:if test="${sessionScope.loginAdmin!=null}">
    <script language="javascript">
        var i = true;
        if(i) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('登录成功');
            });
            i=false;
        }
    </script>
</c:if>
</body>
</html>
