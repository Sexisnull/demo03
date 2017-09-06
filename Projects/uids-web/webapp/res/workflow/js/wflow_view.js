var flowView=function(a,b,c,e,f,g){
		this.centerLeft=null;
		this.centerTop=null;
		//容器div样式或者ID
		this.container=a;
		//中心呈现图样式或者ID
		this.dot=b;
		//周边呈现图样式或者ID
		this.box=c;
		this.out_radius=e;
		
		//上行线
		this.upLine=null;
		//下行线
		this.downLine=null;
		
		this.markerId=null;
		
		this.upArr=f;
		this.downArr=g;
		
		
		this.createSvgDoc=function(a){
			var obj=document.getElementById(a);
			if(obj==null || obj==undefined){
				var _s=document.createElementNS("http://www.w3.org/2000/svg","svg");
				_s.setAttributeNS(null,'id',a);
				_s.setAttributeNS(null,'style',"width:100%;height:100%;");
				obj=_s;
			}
			return obj;
		};
		//生成SVG标签元素
		this.createSVG=function(tag,attrs){
			var el= document.createElementNS('http://www.w3.org/2000/svg', tag);
		    for (var k in attrs)
		        el.setAttribute(k, attrs[k]);
		    return el;
		};
		
		this.createIEsvg=function(tag,attrs,svg){
			var el= document.createElementNS('http://www.w3.org/2000/svg', tag);
			var img= document.createElement('image');
		    for (var k in attrs){
		    	el.setAttribute(k, attrs[k]);
		    	img.setAttribute(k, attrs[k]);
		    }
		    img.setAttribute("xlink:href",svg);
		    img.setAttribute("src",svg);
		    el.appendChild(img);
		    return el;
		}
		
		this.createMaker=function(a,b){
			var marker_up=this.createSVG("marker",{
		    	id :a,
		    	viewBox:'0 0 20 20',
		    	refX:'0',
		    	refY:'10',
		    	markerUnits:'strokeWidth',
		    	markerWidth:'3',
		    	markerHeight:'10',
		    	orient:'auto'
			});
			var marker_path=this.createSVG("path",{
		    	d : "M 0 0 L 20 10 L0 20 z",
		    	fill:b,
		    	stroke:b
			});
			marker_up.appendChild(marker_path);
			var obj=document.getElementById("svg_obj");
			obj.appendChild(marker_up);
		};
		
		this.createCurveLine=function(a,b,c){
			if(c!=undefined){
				var pois=c.split(" ");
				var xy=pois[0].split(",");
				var mn=pois[1].split(",");
				var mid=parseInt(mn[0])+parseInt((xy[0]-mn[0])/2);
				var line=this.createSVG("path",{
					style : "stroke:"+b+";stroke-width:3;marker-end:url(#"+a+");fill:none;",
					d:"M"+(parseInt(mn[0])+40)+" "+(parseInt(mn[1])-25)+" Q"+mid+" 0"+","+(parseInt(xy[0])-30)+" "+(parseInt(xy[1])-30)
				});
				var obj=document.getElementById("svg_obj");
				obj.appendChild(line);
			}
		}
		
		this.createLine=function(a,b,c){
			if(c!=undefined){
				var line=this.createSVG("polyline",{
					style : "stroke:"+b+";stroke-width:3;marker-end:url(#"+a+");fill:none;",
					points:c
				});
				var obj=document.getElementById("svg_obj");
				obj.appendChild(line);
			}
		}
		
		this.createText=function(a,b,c,d,e,f,g,h,i){
			var _span=document.createElement("span");
			_span.setAttribute("_id",a);
			if(h!=undefined && h.length>0){
				_span.setAttribute("style","position:absolute;left:"+(b-2)+"px;top:"+(c)+"px;width:"+d+"px;height:"+e+"px;line-height:"+e+"px;text-align:center;font-family:Microsoft YaHei Light;color:"+g+";border-top:1px solid #93E0FF;border-bottom:1px solid #93E0FF;border-left:"+h+";border-right:"+h+";border-radius:3px;");
			}else{
				_span.setAttribute("style","position:absolute;left:"+(b-2)+"px;top:"+(c)+"px;width:"+d+"px;height:"+e+"px;line-height:"+e+"px;text-align:center;font-family:Microsoft YaHei Light;color:"+g+";border:"+h+";");
			}
			_span.innerHTML=f;
			return _span;
		}
		
		this.createHtml=function(a,b,c,d,e,f,g,h,i){
			var _div=document.createElement("ul");
			_div.setAttribute("id",a);
			_div.setAttribute("class","cui-bubble-layer");
			_div.setAttribute("style","position:absolute;display:none;left:"+(b-2)+"px;top:"+(c)+"px;border:#ccc 1px solid;background:#f2f2f2;border-radius:3px;");
			for(var i=0;i<f.length;i++){
				var _li=document.createElement("li");
				_li.innerHTML=f[i];
				_div.appendChild(_li);
			}
			return _div;
		}
		
		this.createRect=function(a,b,c,d,e,f,g){
			var rect=this.createSVG("rect",{
				ry:a,
				x:b,
		    	y:c,
		    	width:d,
		    	height:e,
		    	style : "stroke:"+f+";stroke-width:1;fill:"+g+";"
	    	});
			var obj=document.getElementById("svg_obj");
			obj.appendChild(rect);
		}
		
		this.createCircle=function(a,b,c,d,e,f,g){
			var circle=this.createSVG("circle",{
				r:a,
				cx:b,
		    	cy:c,
		    	width:d,
		    	height:e,
		    	style : "stroke:"+f+";stroke-width:1;fill:"+g+";"
	    	});
			var obj=document.getElementById("svg_obj");
			obj.appendChild(circle);
		}
		
		this.layout=function(){
			//中心点横坐标
			var svgObj=this.createSvgDoc("svg_obj");
			var _ieSvg=this.createIEsvg("svg",{
				width:"100%",
				height:"100%"
			},svgObj);
			$(this.container).append(svgObj);
			var dotLeft = ($(this.container).width())/9;
			var doHeight=($(this.container).height())/2;
			var points=new Array();
			var _this=this;
			var _work=$("#workflowId").val();
			$.ajax({
				url:_root+'/workflow/workflowTaskAais!workFlowInfo.do?flowId='+_work,
				dataType:'json',
				type:'post',
				success:function(data){
					_this.createMaker("px001","#55B1F9");
					_this.createMaker("px002","#BDBDBD");
					var pointArray=[];
					var maxReal="";
					for(var i=0;i<data.allWorkFlow.length;i++){
						var _r=false,_state="";
						var _span,_div;
						var _code=data.allWorkFlow[i].activityCode;
						var _text=data.allWorkFlow[i].activityName;
						var _realTime=data.allWorkFlow[i].createTime;
						var _limit=data.allWorkFlow[i].limit;
						_limit=(_limit.length>0?_limit+"小时":"无");
						var _realMan=[];
						if(data.allWorkFlow[i].participantVo!=null)
							_realMan=data.allWorkFlow[i].participantVo.userVo;
						var _realName=[],lineArray=[];
						for(var k=0;k<_realMan.length;k++){
							_realName.push("办理人："+_realMan[k].name);
							_realName.push("办理时限："+_limit);
							_realName.push("时间：无");
						}
						if(null != data.realWorkFlow && data.realWorkFlow != undefined){
							for(var j=0;j<data.realWorkFlow.length;j++){
								var _realCode=data.realWorkFlow[j].activityCode;
								if(maxReal==''){
									maxReal=_realCode;
								}else {
									maxReal=(parseInt(maxReal)>parseInt(_realCode)?maxReal:_realCode)
								}
							}
							for(var j=0;j<data.realWorkFlow.length;j++){
								var _realCode=data.realWorkFlow[j].activityCode;
								if(_realCode==_code){
									_r=true;
									_doBack=data.realWorkFlow[j].backTaskId;
									_state=data.realWorkFlow[j].status;
									_realName=[];
									/*if( _state=='00D' || _state=='00E'){
										lineArray.push("00F");
									}else{
										lineArray.push(_state);
									}*/
									lineArray.push(_state);
									_realName.push((_code==0?"受理人：":"办理人：")+data.realWorkFlow[j].userNames);
									_realName.push("办理时限："+_limit);
									_realName.push("时间："+data.realWorkFlow[j].createTime);
								}
							}
						}
						if(i==0){
							_this.createCircle("23",dotLeft*(i+1)+32,doHeight,(dotLeft-60),"40","#1293F5","#D3F1FD");
							_span=_this.createText("div"+(i+1),dotLeft*(i+1),parseInt(doHeight)-20,(dotLeft-60),"40",_text,"","",_realName.join(""));
							pointArray.push((dotLeft*(i+1)+(dotLeft-56))+","+doHeight+" "+(dotLeft*(i+2)-5)+","+parseInt(doHeight));
//							if(null != data.realWorkFlow && data.realWorkFlow != undefined){
//								if(data.realWorkFlow.length>0)
//									_div=_this.createHtml("div"+(i+1),dotLeft*(i+1)-55,parseInt(doHeight)+40,(dotLeft),"60",_realName);
//							}
						}else if(i==(data.allWorkFlow.length-1)){
							_this.createCircle("23",dotLeft*(i+1)+55,doHeight,(dotLeft-60),"40","#1293F5","#D3F1FD");
							_span=_this.createText("div"+(i+1),dotLeft*(i+1)+25,parseInt(doHeight)-20,(dotLeft-60),"40",_text,"","",_realName.join(""));
						}else{
							if(_r){
								if(_state=='00A'){
									_this.createRect("40",dotLeft*(i+1)+10,parseInt(doHeight)-20,(dotLeft-60),"40","#BDBDBD","#F2F2F2");
									_span=_this.createText("div"+(i+1),dotLeft*(i+1)+3,parseInt(doHeight)-25,(dotLeft-50),"50",_text,"","3px solid #93E0FF","",_realName.join(""));
								}else if(_state=='00C'){
									_this.createRect("40",dotLeft*(i+1)+10,parseInt(doHeight)-20,(dotLeft-60),"40","#54C866","#C3F2CB");
									_span=_this.createText("div"+(i+1),dotLeft*(i+1)+5,parseInt(doHeight)-25,(dotLeft-50),"50",_text,"#54C866","",_realName.join(""))
								}else if(_state=='00D'){
									_this.createRect("40",dotLeft*(i+1)+10,parseInt(doHeight)-20,(dotLeft-60),"40","#54C866","#C3F2CB");
									_span=_this.createText("div"+(i+1),dotLeft*(i+1)+3,parseInt(doHeight)-25,(dotLeft-50),"50",_text,"#54C866","",_realName.join(""));
								}else if(_state=='00E'){
									_this.createRect("40",dotLeft*(i+1)+10,parseInt(doHeight)-20,(dotLeft-60),"40","#54C866","#C3F2CB");
									_span=_this.createText("div"+(i+1),dotLeft*(i+1)+5,parseInt(doHeight)-25,(dotLeft-50),"50",_text,"#54C866","",_realName.join(""))
								}else{
									_this.createRect("40",dotLeft*(i+1)+10,parseInt(doHeight)-20,(dotLeft-60),"40","#54C866","#C3F2CB");
									_span=_this.createText("div"+(i+1),dotLeft*(i+1)+5,parseInt(doHeight)-25,(dotLeft-50),"50",_text,"#54C866","",_realName.join(""))
								}
								_div=_this.createHtml("div"+(i+1),dotLeft*(i+1)-45,parseInt(doHeight)+40,(dotLeft),"60",_realName);
							}else{
								_this.createRect("40",dotLeft*(i+1)+10,parseInt(doHeight)-20,(dotLeft-60),"40","#BDBDBD","#F2F2F2");
								_span=_this.createText("div"+(i+1),dotLeft*(i+1)+5,parseInt(doHeight)-25,(dotLeft-50),"50",_text,"","",_realName.join(""));
								_div=_this.createHtml("div"+(i+1),dotLeft*(i+1)-10,parseInt(doHeight)+40,(dotLeft),"60",_realName);
							}
							if(i==(data.allWorkFlow.length-2)){
								pointArray.push((dotLeft*(i+1)+15+(dotLeft-60))+","+parseInt(doHeight)+" "+(dotLeft*(i+2)+10)+","+parseInt(doHeight));
							}else{
								pointArray.push((dotLeft*(i+1)+15+(dotLeft-60))+","+parseInt(doHeight)+" "+(dotLeft*(i+2)-5)+","+parseInt(doHeight));
							}
						}
						$(_span).bind("mouseover",function(){
							$(this).css("cursor","pointer");
							var _id=$(this).attr("_id");
							$("#"+_id).toggle();
						});
						$(_span).bind("mouseout",function(){
							var _id=$(this).attr("_id");
							$("#"+_id).toggle();
						});
						
						$(_this.container).append(_span);
						$(_this.container).append(_div);
						
						if(pointArray!=undefined && pointArray.length>0 && _r){
							for(var m=0;m<lineArray.length;m++){
								if(lineArray[m]=='00C'){
									_this.createLine("px001","#4DAEF8",pointArray[i]);
								}else if( lineArray[m]=='00A'){
									_this.createLine("px001","#4DAEF8",pointArray[i]);
								}else if((lineArray[m]=='00D')){
									_this.createLine("px002","#BDBDBD",pointArray[i]);
									_this.createCurveLine("px001","#4DAEF8",pointArray[i-1]);
								}/*else if((lineArray[m]=='00E')){
									if(_code==(maxReal)){
										_this.createLine("px002","#BDBDBD",pointArray[i]);
										_this.createCurveLine("px001","#4DAEF8",pointArray[i-1]);
									}else{
										_this.createCurveLine("px001","#4DAEF8",pointArray[i]);
									}
								}else if(lineArray[m]=='00F'){
									_this.createLine("px001","#4DAEF8",pointArray[i]);
									_this.createCurveLine("px001","#4DAEF8",pointArray[i]);
								}else if(lineArray[m]=='00B'){
									_this.createLine("px002","#BDBDBD",pointArray[i]);
								}*/
							}
						}else if(pointArray!=undefined && pointArray.length>0 && !_r){
							_this.createLine("px002","#BDBDBD",pointArray[i]);
						}
					}
				}
			})
		}
		
	}
