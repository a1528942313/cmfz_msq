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
                url:"${pageContext.request.contextPath}/Chart/showAll",
                datatype:"json",
                autowidth:true,
                height:300,
                caption:"轮播图列表",
                pager:"#pager",
                rowNum:"3",
                rowList:["3","5","6","10"],
                viewrecords:true,
                editurl:"${pageContext.request.contextPath}/Chart/edit",
                colNames:["ID","名称","封面","描述","创建日期","状态"],
                colModel:[
                    {name:"id",hidden:true},
                    {name:"name",editable:true},
                    {name:"upload",editable:true,edittype: 'file',editoptions:{enctype:"multipart/form-data"} ,
                        formatter:function(cellvalue, options, rowObject){
                            return "<a href='${pageContext.request.contextPath}/statics/album/image/"+rowObject.image+"' download='"+rowObject.image+"'><img src='${pageContext.request.contextPath}/statics/chart/image/"+rowObject.image+"' width='90px' height='60px'/></a>";
                        }
                    },
                    {name:"title",editable:true},
                    {name:"createDate"},
                    {name:"status",editable:true,edittype:"select",editoptions: {value:"yes:激活;no:冻结"},
                        formatter:function(cellvalue, options, rowObject){
                            if("yes"==rowObject.status) return "激活";
                            if("no"==rowObject.status) return "冻结";
                        }
                    }
                ]
            }).jqGrid("navGrid","#pager",{edit:true,add:true,del:true},{
                    closeAfterEdit:true,
                    afterSubmit:function (response) {
                        var code = response.responseJSON.code;
                        var id = response.responseJSON.data.id;
                        if(code=="200"){
                            $.ajaxFileUpload( {
                                url : "${pageContext.request.contextPath}/Chart/upload",
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
                },
                {
                    closeAfterAdd:true,
                    afterSubmit:function (response) {
                        var code = response.responseJSON.code;
                        var id = response.responseJSON.data.id;
                        if(code=="200"){
                            $.ajaxFileUpload( {
                                url : "${pageContext.request.contextPath}/Chart/upload",
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
                    轮播图信息管理
                </a>
            </div>
        </div>
    </div>
</nav>
<table id="table"></table>
<div id="pager"></div>
<br/>
</body>
</html>