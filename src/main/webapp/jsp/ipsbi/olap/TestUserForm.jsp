<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="zone.framework.util.base.SecurityUtil"%>
<%
	String contextPath = request.getContextPath();
	SecurityUtil securityUtil = new SecurityUtil(session);
%><!doctype html>
<head>
  <meta charset="utf-8" />
<jsp:include page="../../../inc.jsp"></jsp:include>
  <script>
    $(function () {
    $('#chart').highcharts({
        title: {
            text: 'xxx图表'//指定图表标题
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'], //指定x轴标签
            labels : //定义x轴标签的样式
            {
                    rotation : -80 ,
                    fontStyle : 'italic',
                    style: {
                        fontSize:'',
                        fontFamily: '微软雅黑' 
                    }
            }
        },
        yAxis: {
            title: {
                text: 'Temperature (°C)'  //指定y轴标题
            }
        },
        tooltip: {
            valueSuffix: '°C'   //指定鼠标移动到某个点上的提示框单位
        },
        legend: {  //
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '温度',
            type:'column', //指定图表类型 为柱状图，默认为折线图。
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6] //y轴数据
        }]
    });
});

  </script>
  </head>
  <body>
  <div id="chart" style="min-width:700px;height:400px"></div>
  </body></html>