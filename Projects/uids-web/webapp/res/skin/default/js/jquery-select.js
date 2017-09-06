(function($){
	var input = '<div id="pre"><input id="itest" type="text" placeholder="--请选择--">';
	var strDiv = '<div id="dtest">';
	var strSpan = '';
	var L = $('#test option').size();
	for(var i = 1; i < L; i ++){
		strSpan += '<span class="soption" data-val="'+ $('#test option').eq(i).attr('value') +'">' + $('#test option').eq(i).html().trim() + '</span>';
	}
	strDiv += strSpan + '</div></div>';
	$('#test').after(input + strDiv);
	//初始化结束
	$(document).on('focus','#itest',function(){//获得焦点，模仿select，显示模拟下拉框
		var st = $(this).val().trim();
		if(st == ''){
			$('#dtest').html(strSpan);
		}
		else{
			var strDiv2 = '';
			for(var i = 0; i < L; i ++){
				var html = $('#test option').eq(i).html();
				if(html.match(st)){
					strDiv2 += '<span class="soption" data-val="'+ $('#test option').eq(i).attr('value') +'">' + $('#test option').eq(i).html().trim() + '</span>';
				}
			}
			$('#dtest').html(strDiv2);
		}
		$('#dtest').slideDown(250);
	});

	$(document).on('blur','#itest',function(){//失去焦点，隐藏模拟下拉框
		$('#dtest').slideUp(50);
	});

	$(document).on('keyup','#itest',function(){//输入信息，自动匹配，暂时不支持鼠标右键
		var st = $(this).val().trim();
		if(st == ''){
			$('#dtest').html(strSpan);
			return false;
		}
		var strDiv2 = '';
		for(var i = 0; i < L; i ++){
			var html = $('#test option').eq(i).html();
			if(html.match(st)){
				strDiv2 += '<span class="soption" data-val="'+ $('#test option').eq(i).attr('value') +'">' + $('#test option').eq(i).html().trim() + '</span>';
			}
		}
		$('#dtest').html(strDiv2);
	});

	$(document).on('mousedown','.soption',function(){//选择下拉选项
		var html = $(this).html();
		var val = $(this).data('val');
		$('#itest').val(html).data('val',val).blur();
		$('#test').val(val);
	});

})(jQuery);