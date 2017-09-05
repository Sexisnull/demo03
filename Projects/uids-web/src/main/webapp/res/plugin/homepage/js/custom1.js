//页面高度控制	
//document.domain = "xxx.com";//指向根域

$(window.parent.document).find("#frame_con").load(function() {// 绑定事件
	var main = $(window.parent.document).find("#frame_con");// 找到iframe对象
	var thisheight = $(document).height() + 30;// 获取页面高度
	main.height(thisheight < 500 ? 500 : thisheight);// 为iframe高度赋值如果高度小于500，则等于500，反之不限高，自适应
});

$(function() {
	// 时间段选择
	$('#dashboard-report-range').daterangepicker(
			{
				ranges : {
					'今天' : [ 'today', 'today' ],
					'昨天' : [ 'yesterday', 'yesterday' ],

					'七天之前' : [ Date.today().add({
						days : -6
					}), 'today' ],
					'三十天之前' : [ Date.today().add({
						days : -29
					}), 'today' ],
					'本月' : [ Date.today().moveToFirstDayOfMonth(),
							Date.today().moveToLastDayOfMonth() ],
					'上月' : [ Date.today().moveToFirstDayOfMonth().add({
						months : -1
					}), Date.today().moveToFirstDayOfMonth().add({
						days : -1
					}) ]
				},
				opens : 'right',
				format : 'yyyy/MM/dd',
				separator : ' to ',
				startDate : Date.today().add({
					days : -29
				}),
				endDate : Date.today(),
				minDate : '01/01/2012',
				maxDate : '12/31/2014',
				locale : {
					applyLabel : '提交',
					fromLabel : '从',
					toLabel : '至',
					customRangeLabel : '自定义时间',
					daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
					monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月',
							'八月', '九月', '十月', '十一月', '十二月' ],
					firstDay : 1
				},
				showWeekNumbers : true,
				buttonClasses : [ 'btn-danger' ]
			},

			function(start, end) {
				$('#dashboard-report-range span').html(
						start.toString('yy年MM月d日') + ' - '
								+ end.toString('yy年MM月d日'));
				datablind(start.toString('yyyy-MM-dd'), end
						.toString('yyyy-MM-dd'));
				datablind1(start.toString('yyyy-MM-dd'), end
						.toString('yyyy-MM-dd'));
			});

	$('#dashboard-report-range span').html(Date.today().add({
		days : -29
	}).toString('yy年MM月d日') + ' - ' + Date.today().toString('yy年MM月d日'));

	// 欢迎页页面时间选择
	$('#index-report-range')
			.daterangepicker(
					{
						ranges : {
							'今天' : [ 'today', 'today' ],
							'昨天' : [ 'yesterday', 'yesterday' ],

							'七天之前' : [ Date.today().add({
								days : -6
							}), 'today' ],
							'三十天之前' : [ Date.today().add({
								days : -29
							}), 'today' ],
							'本月' : [ Date.today().moveToFirstDayOfMonth(),
									Date.today().moveToLastDayOfMonth() ],
							'上月' : [ Date.today().moveToFirstDayOfMonth().add({
								months : -1
							}), Date.today().moveToFirstDayOfMonth().add({
								days : -1
							}) ]
						},
						opens : 'left',
						format : 'yyyy/MM/dd',
						separator : ' to ',
						startDate : Date.today().add({
							days : -29
						}),
						endDate : Date.today(),
						minDate : '01/01/2012',
						maxDate : '12/31/2014',
						locale : {
							applyLabel : '提交',
							fromLabel : '从',
							toLabel : '至',
							customRangeLabel : '自定义时间',
							daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
							monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
									'七月', '八月', '九月', '十月', '十一月', '十二月' ],
							firstDay : 1
						},
						showWeekNumbers : true,
						buttonClasses : [ 'btn-danger' ]
					},

					function(start, end) {
						var url="/egovm/indexdata/home-data.do";
						$('#index-report-range span').html(
								start.toString('yyyy年MM月d日') + ' - '
										+ end.toString('yyyy年MM月d日'));
						// alert("前");
						/*
						 * datablind(start.toString('yyyy-MM-dd'), end
						 * .toString('yyyy-MM-dd'));
						 * datablind1(start.toString('yyyy-MM-dd'), end
						 * .toString('yyyy-MM-dd'));
						 */
						// alert("后");
						var doBatchForm = $('<form id="doBatchForm" name="doBatchForm" action="'
								+ url + '" method="post"></form>');
						$(document.body).append(doBatchForm);
						doBatchForm
								.append($('<input type="hidden" name="beginTime" value="'
										+ start.toString('yyyy-MM-dd')
										+ '" /><input type="hidden" name="endTime" value="'
										+ end.toString('yyyy-MM-dd') + '" />'));
						doBatchForm.submit();
					});
    $('#index-report-range span').html($('#beginTime').val()+"-"+$('#endTime').val());

	$('#index-report-range span').html(Date.today().add({
		days : -29
	}).toString('yyyy年MM月d日') + ' - ' + Date.today().toString('yyyy年MM月d日'));});