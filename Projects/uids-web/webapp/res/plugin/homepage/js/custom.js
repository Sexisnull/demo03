$(function() {
// 统计报表页页面时间选择
// 欢迎页页面时间选择
	$('#index-report-range').daterangepicker({
        ranges: {
            '最近七天': [Date.today().add({
                days: -6
            }), 'today'],
            '最近三十天': [Date.today().add({
                days: -29
            }), 'today'],
            '本月': [Date.today().moveToFirstDayOfMonth(), Date.today().moveToLastDayOfMonth()],
            '上月': [Date.today().moveToFirstDayOfMonth().add({
                months: -1
            }), Date.today().moveToFirstDayOfMonth().add({
                days: -1
            })],
            '全部':[Date.today().moveToFirstDayOfMonth().add({
                months: -120
            }), Date.today().moveToLastDayOfMonth()]
        },
        opens: 'left',
        format: 'yyyy-MM-dd',
        separator: ' to ',
        startDate: Date.today().add({
            days: -29
        }),
        endDate: Date.today(),
        minDate: '01/01/2000',
        maxDate: '12/31/3000',
        locale: {
            applyLabel: '提交',
            fromLabel: '从',
            toLabel: '至',
            customRangeLabel: '自定义时间',
            daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            firstDay: 1
        },
        showWeekNumbers: true,
        buttonClasses: ['btn-danger']
    },
    function (start, end) {
//        	start = start.toString("yyyy-MM-dd HH:mm:ss");
//        	end = end.toString("yyyy-MM-dd HH:mm:ss");
    	
    	var myChart3 = echarts.init(document.getElementById('main3')); 
        var caseType = [];  
        var caseValue = [];  
        var url = '/aais/workflow/statisticalAnalysis!homeStatistical.do?startTime='+start.toString("yyyy-MM-dd HH:mm:ss")+'&endTime='+(end.toString("yyyy-MM-dd HH:mm:ss")).substring(0,10)+' 23:59:59';
        $.ajax({
    		type:'POST',
    		url: url,
    		dataType:'json',
    		success:function(json){
    			if (json){
    				caseType = json.caseTyple; 
    		        caseValue = json.caseValue;
    				 var option3 = {
    				     tooltip : {
    						        trigger: 'axis',
    						        formatter: "{a} <br/>{b} : {c} 件"
    						    },
    				    toolbox: {
    				        show : false
    				    },
    				    calculable : true,
    				    grid : {
    						x : 45,
    						y : 50,
    						y2 : 40,
    						x2 : 5,
    						borderWidth:0
    					},
    				    xAxis : [{
				        	name : '办件类型',
				            type : 'category',
				            splitLine:{ 
			                     show:false
			    			},
				            textStyle : {
								color : '#28c6de',
								align : 'center'
								},
				            data : caseType
				        }],
    				    yAxis : [{
				        	name : '办件量（件）',
				            type : 'value',
				           	splitLine:{ 
			                     show:false
			    			}
				        }],
    				    series : [{
				            name:'办件量',
				            type:'bar',
				            barWidth:'35',
				            itemStyle: {normal: {color:'rgba(0,135,206,250)', label:{show:true}}},
				            data:caseValue
				        }]
    				};
    				} else {
						var div = document.getElementById("main3");
						div.innerHTML = "<div style=\"position: relative; overflow: hidden; width: 533px;\"><img  src=\"/aais/res/skin/default/images/main/nodata.png\" style=\"margin-top: 15%;margin-left: 13%;width:66%;\"/></div>";
					}
    			myChart3.setOption(option3); 
    		}
    	});
        $('#index-report-range span').html(start.toString('yyyy年MM月dd日') + ' - ' + end.toString('yyyy年MM月dd日'));
//            var doBatchForm = $('<form id="doBatchForm" name="doBatchForm" action="'+url+'" method="post"></form>');
//       		$(document.body).append(doBatchForm);
//       		doBatchForm.append($('<input type="hidden" name="beginTime" value="'+start.toString('yyyy-MM-dd HH:mm:ss')+'" /> <input type="hidden" name="endTime" value="'+end.toString('yyyy-MM-dd HH:mm:ss')+'" />'));
//       		doBatchForm.submit();
    });
       // $('#index-report-range span').html($('#beginTime').val()+"-"+$('#endTime').val());
});