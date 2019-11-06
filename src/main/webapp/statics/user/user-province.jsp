<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>showUsers</title>
    <script type="text/javascript">
        myEcharts();
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io',
            appkey: "BC-df17106e996e4cc6884b52ae567d4389",
        });
        goEasy.subscribe({
            channel: "cmfz_msq",
            onMessage: function (message) {
                myEcharts();
            }

        });

        function myEcharts() {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('statistics_china'));
            function randomData() {
                return Math.round(Math.random() * 1000);
            }
            option = {
                title: {
                    text: '持名法州APP用户分布图',
                    subtext: '2017年6月15日 最新数据',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                // 说明
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['男', '女']
                },
                visualMap: {
                    min: 0,
                    max: 2500,
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                // 工具箱
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        dataView: {readOnly: false},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                series: [
                    {
                        name: '男',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        }
                        //data:[{name:"山西",value:100},{name:"陕西",value:100}]
                    },
                    {
                        name: '女',
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        }
                        //data:[{name:"山西",value:100},{name:"陕西",value:100}]
                    }
                ]
            };
            myChart.setOption(option);

            $.post("${pageContext.request.contextPath}/User/selectProvinceCount", function (data) {
                myChart.setOption({
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '男',
                        data: data.M
                    },{
                        // 根据名字对应到相应的系列
                        name: '女',
                        data: data.F
                    }]
                });
            }, "json");
        }
    </script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header row">
            <div class="col-md-12">
                <a class="navbar-brand" href="#">
                    用户省份分布图
                </a>
            </div>
        </div>
    </div>
</nav>
<div id="statistics_china" style="width: 1000px;height: 550px;margin-top: 30px;margin-left: 100px">

</div>
<br/>
</body>
</html>