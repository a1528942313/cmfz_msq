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
                url:"${pageContext.request.contextPath}/User/showAll",
                datatype:"json",
                autowidth:true,
                height:300,
                pager:"#pager",
                rowNum:"3",
                rowList:["3","5","6","10"],
                viewrecords:true,
                editurl:"${pageContext.request.contextPath}/User/edit",
                colNames:["ID","法名","姓名","头像","性别","省份","市区","签名","电话","创建日期","状态","所属上师","操作"],
                colModel:[
                    {name:"id",hidden:true},
                    {name:"dharma",editable:true},
                    {name:"name",editable:true},
                    {name:"upload",editable:true,edittype: 'file',editoptions:{enctype:"multipart/form-data"} ,
                        formatter:function(cellvalue, options, rowObject){
                            return "<a href='${pageContext.request.contextPath}/statics/album/image/"+rowObject.image+"' download='"+rowObject.image+"'><img src='${pageContext.request.contextPath}/statics/user/image/"+rowObject.image+"' width='90px' height='60px'/></a>";
                        }
                    },
                    {name:"sex",width:80,editable:true,edittype:"select",editoptions: {value:"M:男;F:女"},
                        formatter:function(cellvalue, options, rowObject){
                            if("M"==rowObject.sex) return "男";
                            if("F"==rowObject.sex) return "女";
                        }
                    },
                    {name:"province",editable:true},
                    {name:"city",editable:true},
                    {name:"sign",editable:true},
                    {name:"phone",editable:true},
                    {name:"createDate",width:220},
                    {name:"status",width:80,editable:true,edittype:"select",editoptions: {value:"yes:激活;no:冻结"},
                        formatter:function(cellvalue, options, rowObject){
                            if("yes"==rowObject.status) return "激活";
                            if("no"==rowObject.status) return "冻结";
                        }
                    },
                    {name:"guruId",editable:true,edittype: "select", editoptions: {dataUrl: "${pageContext.request.contextPath}/Guru/selectAll"},
                        formatter: function (cellvalue, options, rowObject) {
                            return "<a href='${pageContext.request.contextPath}/statics/album/image/"+rowObject.image+"' download='"+rowObject.image+"'><img src='${pageContext.request.contextPath}/statics/guru/image/"+rowObject.guruImage+"' width='90px' height='60px'/></a>";
                        }
                    },
                    {name:"option",width:100,formatter:function(cellvalue, options, rowObject){
                            return  "<a class='btn btn-danger' onclick=\"update('"+rowObject.id+"')\" >修改</a>";
                        }
                    }
                ]
            }).jqGrid("navGrid","#pager",{edit:false,add:false,del:true});
        });
        function update(id) {
            if (id != null)
            //调用修改的方法
                $("#table").jqGrid('editGridRow', id, {
                    height : 530,
                    closeAfterEdit:true,
                    afterSubmit:function (response) {
                        var code = response.responseJSON.code;
                        var id = response.responseJSON.data.id;
                        if(code=="200"){
                            $.ajaxFileUpload( {
                                url : "${pageContext.request.contextPath}/User/upload",
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
            else
                alert("请选中一行");
        }
        function add() {
            $("#table").jqGrid('editGridRow', "new", {
                height : 530,
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    var code = response.responseJSON.code;
                    var id = response.responseJSON.data.id;
                    if(code=="200"){
                        $.ajaxFileUpload( {
                            url : "${pageContext.request.contextPath}/User/upload",
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
        }
    </script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header row">
            <div class="col-md-12">
                <a class="navbar-brand" href="#">
                    用户信息管理
                </a>
            </div>
        </div>
    </div>
</nav>
<div class="container-fluid">
    <ul class="nav nav-tabs">
        <li class="active"><a href="">用户列表</a></li>
        <li><a href="#userInfo" data-toggle="modal" onclick="add()">用户添加</a></li>
    </ul>
    <table id="table"></table>
    <div id="pager"></div>
</div>
<br/>
</body>
</html>