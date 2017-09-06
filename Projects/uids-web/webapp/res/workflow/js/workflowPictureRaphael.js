/*
        生成workfllow
 */

var workflowAutoGener=function(divId,_ajaxUrl,divWidth,divHeight,jianju){
	// 绘制开始和结束
	this.startEndPoint=function(paper,x, y, r, text, fill, stroke, textcolor) {
		var circle = paper.circle(x, y, r);
		circle.attr("fill", fill);
		circle.attr("stroke", stroke);
		paper.text(x, y, text).attr( {
			font : "12px Fontin-Sans, Arial",
			fill : textcolor,
			textstring : "start"
		});
		return circle;
	};
	// 绘制饼
	this.ellipsePoint=function(paper,x, y, xr, yr, text, fill, stroke, textcolor,html) {
		var ellipse = paper.ellipse(x, y, xr, yr);
		ellipse.attr("fill", fill);
		ellipse.attr("stroke", stroke);
		var text = paper.text(x, y, text).attr( {
			font : "12px Fontin-Sans, Arial",
			fill : textcolor
		});
		
		ellipse.mouseover(function(){
            var xx = $(paper.getById(ellipse.id)[0]).offset().left - 50;
            var yy = $(paper.getById(ellipse.id)[0]).offset().top + 50;
            $(".cui-bubble-layer").remove();
            $("body").append($(html).css({
                "left": xx,
                "top": yy
            }));
        });
        text.mouseover(function(){
            var xx = $(paper.getById(ellipse.id)[0]).offset().left - 50;
            var yy = $(paper.getById(ellipse.id)[0]).offset().top + 50;
            $(".cui-bubble-layer").remove();
            $("body").append($(html).css({
                "left": xx,
                "top": yy
            }));
        });
        ellipse.mouseout(function() {
            $(".cui-bubble-layer").remove();
        });
        text.mouseout(function() {
            $(".cui-bubble-layer").remove();
        });
	}
	// 绘制箭头
	this.arrowPoint=function(paper,sx, sy, ex, ey, color, lineWidth) {
		paper.path("M" + sx + " " + sy + "L" + ex + " " + ey).attr( {
			'arrow-end' : 'block-wide-long',
			'stroke' : color,
			"stroke-width" : lineWidth
		});
	}
	// 绘制贝塞尔箭头
	this.arrowBeizerPoint=function(paper,sx, sy, ex, ey, color, lineWidth) {
		paper.path(
				"M" + (sx - 25) + "," + (sy - 20) + " Q"
						+ (sx - (sx - ex) / 2 + 5) + "," + (sy - 70) + " "
						+ (ex + 40) + "," + (sy - 20)).attr({
			'arrow-end' : 'block-wide-long',
			stroke : color,
			"stroke-width" : lineWidth
		});
	}
	this.arrowBeizerLine=function(paper,sx, sy, ex, ey, color, lineWidth) {
		paper.path(
				"M" + (sx - 25) + " " + (sy - 20) + " C"
						+ (sx - (sx - ex) / 4 + 15) + " " + (sy - 82) + ","+ (ex + (sx - ex) / 4 + 15) + " " + (sy - 82) + " "
						+ (ex + 40) + " " + (sy - 20)).attr({
			'arrow-end' : 'block-wide-long',
			stroke : color,
			"stroke-width" : lineWidth
		});
	}
	// 绘制方形
	this.drawRect=function(paper,sx, sy, ex, ey, stroke) {
		var rect = paper.rect(sx, sy, ex, ey, 3);
		rect.attr("stroke", stroke);
		return rect;
	}
	
	this.createHtml=function(f){
		var _div=document.createElement("ul");
		_div.setAttribute("class","cui-bubble-layer");
		for(var i=0;i<f.length;i++){
			var _li=document.createElement("li");
			_li.innerHTML=f[i];
			_div.appendChild(_li);
		}
		return _div;
	}
	
	/**
	 * allFlow：所有环节元素（json.allWorkFlow元素）
	 * realFlow：已办环节元素（json.realWorkFlow元素）
	 * jianju: 图表间距
	 */
	this.viewWork=function(paper,allFlow,realFlow,jianju){
		if(allFlow!=undefined)
		var curCode="";
		for(var m=0;m<allFlow.length;m++){
			var index=m;
			var el=allFlow[m];
			var _code=el.activityCode;
			var arrowColor = "#d1d1d1";
		    var ellipseColor = "#f3f3f3";
		    var ellipseBorderColor = "#d1d1d1";
		    var ellipseTextColor = "#333";
		    var _realName=[];
		    var _limit=el.limit;
			//_limit=(_limit.length>0?_limit+"小时":"无");
		  //  _limit=(_limit.length>0?_limit+"小时":"无");
			var _realMan=[];
			if(el.participantVo!=null)
				for(var k=0;k<el.participantVo.userVo.length;k++){
					_realMan.push(el.participantVo.userVo[k].name);
				}
		    if(realFlow!=undefined)
		    for (var i = realFlow.length - 1; i >= 0; i--){
		        if ((realFlow[i].status == "00C" || realFlow[i].status == "00D" || realFlow[i].status == "00E") && realFlow[i].activityCode == index) {
		        	if(realFlow[i].status == "00C")
		        	arrowColor = "#2fa4f7";
		            var ellipseColor = "#c3f3cb";
		            var ellipseBorderColor = "#82d78f";
		            var ellipseTextColor = "#54c966";
		        }
		        if (realFlow[i].status == "00D" && realFlow[i].activityCode == index) {
		            arrowColor = "#d1d1d1";
		            this.arrowBeizerPoint(paper,50 + index * jianju + 25, 70, 50 + (index - 1) * jianju - 40, 70, "#2fa4f7", 2);
		        }
		        if (realFlow[i].status == "00A" && realFlow[i].activityCode == index) {
//		        	arrowColor = "#2fa4f7";
		        	this.drawRect(paper,50 + index * jianju - 39, 46, 78, 48, "#2fa4f7")
		        	curCode=index;
		        }
		        
		        if (realFlow[i].status == "00E" && realFlow[i].activityCode == index) {
//		        	arrowColor = "#2fa4f7";
		        	this.arrowBeizerLine(paper,50 + index * jianju + 25, 70, 50 + jianju - 40, 70, "#2fa4f7", 2);
		        }
		        
		        var _realCode=realFlow[i].activityCode;
				if(_realCode==_code){
					_realName.push((_code==0?"受理人：":"办理人：")+realFlow[i].userNames);
					_realName.push("办理时限："+_limit);
					_realName.push("时间："+realFlow[i].createTime);
				}
		        
		    }
		    if(_realName.length==0){
		    	_realName.push("办理人："+_realMan.join(","));
				_realName.push("办理时限："+_limit);
				_realName.push("时间：无");
		    }
		    //绘制图饼
		    if (el.activityCode == 0 || el.activityCode == -1) {
		        if (el.activityCode == 0) {
		        	this.arrowPoint(paper,50 + index * jianju + 25, 70, 50 + (index + 1) * jianju - 40, 70, arrowColor, 2);
		        	this.startEndPoint(paper,50 + index * jianju, 70, 20, "开始", "#d3f1fd", "#2fa4f7", "#333");
		        }
		        if (el.activityCode == -1) {
		        	this.startEndPoint(paper,50 + index * jianju - 15, 70, 20, "结束", "#d3f1fd", "#2fa4f7", "#333");
		        }
		    } else {
		    	if(el.activityName.length > 8){
		    		el.activityName = el.activityName.substring(0,4)+"\n"+el.activityName.substring(4,7)+"...";
		    	}else if(el.activityName.length > 4){
		    		el.activityName = el.activityName.substring(0,4)+"\n"+el.activityName.substring(4)
		    	}else {
		    		el.activityName = el.activityName;
		    	}
		        this.arrowPoint(paper,50 + index * jianju + 40, 70, 50 + (index + 1) * jianju - 40, 70, arrowColor, 2);
//		        var html = "<div class='flow_tips'><p>办理人:" + el.participantVo.userVo[0].name + "</p><p>办理时限:" + el.limit + "小时</p><p>时间</div>";
		        var html=this.createHtml(_realName);
		        if(curCode!='' && ellipseColor != "#f3f3f3" && parseInt(curCode)<parseInt(_code)){
		        	this.ellipsePoint(paper,50 + index * jianju, 70, 35, 20, el.activityName, "#FFE698", ellipseBorderColor, ellipseTextColor,html);
		        }else{
		        	this.ellipsePoint(paper,50 + index * jianju, 70, 35, 20, el.activityName, ellipseColor, ellipseBorderColor, ellipseTextColor,html);
		        }
		    }
		}
	};
	this.run=function(){
		var main=this;
		$.ajax({
			url:_ajaxUrl,
			dataType:'json',
			type:'post',
			success:function(json){
				// 创建画布
				var allFlow=json.allWorkFlow;
				var realFlow=json.realWorkFlow;
				var paper = Raphael(divId, allFlow.length*jianju,"100%");
				main.viewWork(paper,allFlow,realFlow,jianju);
			},
			error:function(e){
				alert("查询数据错误！");
			}
		})
	}
}


