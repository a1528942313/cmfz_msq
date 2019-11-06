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
                url:"${pageContext.request.contextPath}/Album/showAll",
                datatype:"json",
                autowidth:true,
                height:300,
                caption:"专辑列表",
                pager:"#pager",
                rowNum:3,
                rowList:["3","5","6","10"],
                viewrecords:true,
                editurl:"${pageContext.request.contextPath}/Album/edit",
                colNames:["ID","标题","封面","集数","评分","作者","播音","发布日期","简介","创建日期","状态","大师"],
                colModel:[
                    {name:"id",hidden:true},
                    {name:"title",editable:true},
                    {name:"upload",editable:true,width:110,edittype: 'file',editoptions:{enctype:"multipart/form-data"} ,
                        formatter:function(cellvalue, options, rowObject){
                            return "<a href='${pageContext.request.contextPath}/statics/album/image/"+rowObject.image+"' download='"+rowObject.image+"'><img src='${pageContext.request.contextPath}/statics/album/image/"+rowObject.image+"' width='90px' height='60px'/></a>";
                        }
                    },
                    {name:"chapterCount",width:60},
                    {name:"score",editable:true,width:60},
                    {name:"author",editable:true},
                    {name:"broadcast",editable:true},
                    {name:"publishDate",editable:true,edittype:"date",width:100},
                    {name:"brief",editable:true},
                    {name:"createDate",width:150},
                    {name:"status",width:70,editable:true,edittype:"select",editoptions: {value:"yes:激活;no:冻结"},
                        formatter:function(cellvalue, options, rowObject){
                            if("yes"==rowObject.status) return "激活";
                            if("no"==rowObject.status) return "冻结";
                        }
                    },
                    {name:"guruId",width:70,editable:true,edittype: "select", editoptions: {dataUrl: "${pageContext.request.contextPath}/Guru/selectAll"},
                        formatter: function (cellvalue, options, rowObject) {
                            if (rowObject.guruName != null) {
                                return rowObject.guruName;
                            } else {
                                return "";
                            }
                        }
                    }
                ] ,
                subGrid : true,
                caption : "章节列表",
                subGridRowExpanded : function(subgrid_id, row_id) {
                    var subgrid_table_id = subgrid_table_id = subgrid_id + "_t";
                    var pager_id = "p_" + subgrid_table_id;
                    $("#" + subgrid_id).html(
                        "<table id='" + subgrid_table_id
                        + "' class='scroll'></table><div id='"
                        + pager_id + "' class='scroll'></div>");
                    $("#" + subgrid_table_id).jqGrid(
                        {

                            styleUI:"Bootstrap",
                            url : "${pageContext.request.contextPath}/Chapter/showAll?albumId="+row_id,
                            datatype:"json",
                            autowidth:true,
                            height:240,
                            pager : pager_id,
                            caption:"章节列表",
                            rowNum:3,
                            rowList:["3","5","6","10"],
                            viewrecords:true,
                            editurl:"${pageContext.request.contextPath}/Chapter/edit?albumId="+row_id,
                            colNames : ["ID","标题","大小","时长","播放","创建时间","状态","所属专辑"],
                            colModel : [
                                {name:"id",hidden:true},
                                {name:"title",editable:true},
                                {name:"size",formatter:function(cellvalue, options, rowObject){ return rowObject.size+"MB"; }},
                                {name:"duration"},
                                {name:"upload",editable:true,width:300,edittype: 'file',editoptions:{enctype:"multipart/form-data"} ,
                                    formatter:function(cellvalue, options, rowObject){
                                        return "<audio controls><source src='${pageContext.request.contextPath}/statics/album/audition/"+rowObject.url+"' type='audio/ogg'></audio>";
                                    }
                                },
                                {name:"createDate"},
                                {name:"status",width:50,editable:true,edittype:"select",editoptions: {value:"yes:激活;no:冻结"},
                                    formatter:function(cellvalue, options, rowObject){
                                        if("yes"==rowObject.status) return "激活";
                                        if("no"==rowObject.status) return "冻结";
                                    }
                                },
                                {name:"albumId",hidden:true}
                            ]
                        }).jqGrid('navGrid',"#" + pager_id, {edit:true,add:true,del:true },{
                        closeAfterEdit:true,
                        afterSubmit:function (response) {
                            var code = response.responseJSON.code;
                            var id = response.responseJSON.data.id;
                            if(code=="200"){
                                $.ajaxFileUpload( {
                                    url : "${pageContext.request.contextPath}/Chapter/upload",
                                    fileElementId : 'upload',
                                    type:'POST',
                                    data:{id:id},
                                    success : function() {
                                        $("#table").trigger("reloadGrid");
                                    }
                                });
                            }
                            return "true";
                        }
                    },{
                        closeAfterAdd:true,
                        afterSubmit:function (response) {
                            var code = response.responseJSON.code;
                            var id = response.responseJSON.data.id;
                            if(code=="200"){
                                $.ajaxFileUpload({
                                    url : "${pageContext.request.contextPath}/Chapter/upload",
                                    fileElementId : 'upload',
                                    type:'POST',
                                    data:{id:id},
                                    success : function() {
                                        $("#table").trigger("reloadGrid");
                                    }
                                });
                            }
                            return "true";
                        }
                    },{
                        afterSubmit:function (response) {
                            $("#table").trigger("reloadGrid");
                            return "true";
                        }
                    });
                }
            }).jqGrid("navGrid","#pager",{edit:true,add:true,del:true},{
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    var code = response.responseJSON.code;
                    var id = response.responseJSON.data.id;
                    if(code=="200"){
                        $.ajaxFileUpload( {
                            url : "${pageContext.request.contextPath}/Album/upload",
                            fileElementId : 'upload',
                            type:'POST',
                            data:{id:id},
                            success : function() {
                                $("#table").trigger("reloadGrid");
                            }
                        });
                    }
                    return "true";
                }
            },{
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    var code = response.responseJSON.code;
                    var id = response.responseJSON.data.id;
                    if(code=="200"){
                        $.ajaxFileUpload( {
                            url : "${pageContext.request.contextPath}/Album/upload",
                            fileElementId : 'upload',
                            type:'POST',
                            data:{id:id},
                            success : function() {
                                $("#table").trigger("reloadGrid");
                            }
                        });
                    }
                    return "true";
                }
            });

        });

    </script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header row">
            <div class="col-md-12">
                <a class="navbar-brand" href="#">
                    专辑信息管理
                </a>
            </div>
        </div>
    </div>
</nav>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li class="active"><a href="">文章列表</a></li>
        <li><a href="${pageContext.request.contextPath}/Album/export" >导出专辑</a></li>
    </ul>
    <table id="table"></table>
    <div id="pager"></div>
</div>
<br/>
</body>
</html>