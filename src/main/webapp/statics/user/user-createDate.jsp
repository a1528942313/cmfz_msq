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
            var myChart = echarts.init(document.getElementById('statistics_main'));
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '持名法州App用户注册'
                },
                tooltip: {},
                legend: {
                    data:['男','女']
                },
                xAxis: {
                    data: ['近一周','近两周','近三周']
                },
                yAxis: {},
                series: [{
                    name: '男',
                    type: 'bar'
                    //data:[]
                },{
                    name: '女',
                    type: 'bar'
                    //data:[]
                }]
            };

            myChart.setOption(option);

            // 异步加载统计信息
            $.post("${pageContext.request.contextPath }/User/selectDateCount",function(data){
                // 使用刚指定的配置项和数据显示图表。
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
            },"json");
        }
    </script>

</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header row">
            <div class="col-md-12">
                <a class="navbar-brand" href="#">
                    用户创建时间分布图
                </a>
            </div>
        </div>
    </div>
</nav>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="statistics_main" style="width: 600px;height: 400px;;margin-top: 30px;margin-left: 30px"></div>
<br/>
</body>
</html>