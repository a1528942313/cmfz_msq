<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>showUsers</title>
    <script type="application/javascript">
        $(function () {
            $("#table").jqGrid({
                styleUI:"Bootstrap",
                url:"${pageContext.request.contextPath}/Article/showAll",
                datatype:"json",
                autowidth:true,
                height:300,
                pager:"#pager",
                rowNum:"3",
                rowList:["3","5","6","10"],
                viewrecords:true,
                editurl:"${pageContext.request.contextPath}/Article/edit",
                colNames:["ID","标题","内容","作者","发布时间","创建日期","状态","所属上师","上师ID","操作"],
                colModel:[
                    {name:"id",hidden:true},
                    {name:"title",editable:true},
                    {name:"content",editable:true},
                    {name:"author",editable:true},
                    {name:"publishDate",editable:true,edittype:"date",width:120},
                    {name:"createDate"},
                    {name:"status",width:80,editable:true,
                        formatter:function(cellvalue, options, rowObject){
                            if("yes"==rowObject.status) return "激活";
                            if("no"==rowObject.status) return "冻结";
                        }
                    },
                    {name:"guruImage",editable:true,
                        formatter: function (cellvalue, options, rowObject) {
                            return "<a href='${pageContext.request.contextPath}/statics/album/image/"+rowObject.image+"' download='"+rowObject.image+"'><img src='${pageContext.request.contextPath}/statics/guru/image/"+rowObject.guruImage+"' width='90px' height='60px'/></a>";
                        }
                    },
                    {name:"guruId",editable:true,hidden:true,
                        formatter: function (cellvalue, options, rowObject) {
                            return rowObject.guruId;
                        }
                    },
                    {name:"option",width:80,formatter:function(cellvalue, options, rowObject){
                            return  "<a class='btn btn-danger' onclick=\"openModal('edit','"+rowObject.id+"')\" >修改</a>";
                        }
                    }
                ]
            }).jqGrid("navGrid","#pager",{edit:false,add:false,del:true,search:false});

        });

        function openModal(oper,id) {
            $("#modal").modal("show");
            KindEditor.html("#article-content",null);
            var article = $("#table").jqGrid("getRowData",id);
            $.ajax({
                url:"${pageContext.request.contextPath}/Guru/selectAll",
                datatype:"JSON",
                type:"POST",
                success:function (response) {
                    $("#article-guruId").clear;
                    $("#article-guruId").html("<label for='guruId'>所属上师id</label>"+response);
                    if(article.guruId!=null) $("#guruId").val(article.guruId);
                }
            });
            $("#article-oper").val(oper);
            $("#article-id").val(article.id);
            $("#article-title").val(article.title);
            KindEditor.html("#article-content",article.content);
            $("#article-author").val(article.author);
            $("#article-publishDate").val(article.publishDate);
            if(article.status=='冻结') $("#article-status").val("no");
            else $("#article-status").val("yes");
        }

        function saveArticle() {
            $.ajax({
                url:"${pageContext.request.contextPath}/Article/edit",
                datatype:"JSON",
                data:$("#article-form").serialize(),
                type:"POST",
                success:function () {
                    $("#modal").modal("hide");
                    $("#table").trigger("reloadGrid");
                }
            });
        }


        KindEditor.create("#article-content",{
            width:"100%",
            height:300,
            resizeType:1,
            allowFileManager:true,
            fileManagerJson:"${pageContext.request.contextPath}/Article/browser",
            uploadJson:"${pageContext.request.contextPath}/Article/upload",
            afterChange:function () {
                this.sync();
            }
        });


    </script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header row">
            <div class="col-md-12">
                <a class="navbar-brand" href="#">
                    文章信息管理
                </a>
            </div>
        </div>

    </div>
</nav>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li class="active"><a href="">文章列表</a></li>
        <li><a href="#userInfo" data-toggle="modal" onclick="openModal('add')">文章添加</a></li>
    </ul>
    <table id="table"></table>
    <div id="pager"></div>
</div>


<div class="modal fade" role="dialog" id="modal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加文章</h4>
            </div>
            <div class="modal-body">
                <form class="form-inline" id="article-form">
                    <input type="hidden" class="form-control" id="article-id" name="id">
                    <input type="hidden" class="form-control" id="article-oper" name="oper">
                    <div class="form-group">
                        <label for="article-title">标题</label>
                        <input type="text" class="form-control" id="article-title" name="title" placeholder="请输入标题">
                    </div>
                    <div class="form-group">
                        <label for="article-author">作者</label>
                        <input type="text" class="form-control" id="article-author" name="author" placeholder="请输入作者">
                    </div>
                    <br/><br/>
                    <div class="form-group">
                        <label for="article-publishDate">发布时间</label>
                        <input type="date" class="form-control" id="article-publishDate" name="publishDate" placeholder="请选择发布时间">
                    </div>
                    <div class="form-group">
                        <label for="article-status">状态</label>
                        <select class="form-control" id="article-status" name="status">
                            <option value="yes">激活</option>
                            <option value="no">冻结</option>
                        </select>
                    </div>
                    <div class="form-group" id="article-guruId">

                    </div>
                    <br/><br/>
                    <textarea id="article-content" name="content" >
                    </textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveArticle()">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<br/>
</body>
</html>