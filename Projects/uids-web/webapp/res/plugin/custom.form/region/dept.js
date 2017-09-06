var setting = {
			async: {
				enable: true,
				url:"sys-region!regiontree.do",
				autoParam:["id"],
				otherParam:{"type":"2"},
				dataFilter: filter
			},
			view: {
				drag: {
					autoExpandTrigger: true,
					prev: dropPrev,
					inner: dropInner,
					next: dropNext
				},
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				dblClickExpand: dblClickExpand,
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,
				renameTitle:'编辑部门',
				removeTitle:"删除部门",
				showRenameBtn: showRenameBtn
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeDrop: beforeDrop,
				//beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onClick: onClick
			}
		};

	var zNodes =[];
		var log, className = "dark";
		function dblClickExpand(treeId, treeNode) {
			return treeNode.level > 0;
		}
		function dropPrev(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function dropInner(treeId, nodes, targetNode) {
			if (targetNode && targetNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					if (!targetNode && curDragNodes[i].dropRoot === false) {
						return false;
					} else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function dropNext(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function beforeDrag(treeId, treeNodes) {
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					return false;
				}
			}
			return true;
		}
		
		function changeChildren(childNode){
			childNode.viewtype='2';
			treeObj.updateNode(childNode);
		}
		//nowNode(拖动节点) dragNode(拖动到的目标节点)
		function dragNode(nowNode,dragNode, moveType){
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var oldparentNode=nowNode.getParentNode();
			
			var id=nowNode.id;
			var seq=nowNode.seq;
			var level=parseInt(dragNode.level)+1;	
				if(moveType=="inner"){
					$.ajax({
								   type: "POST",
								   url: "sys-region!updateCode.do",
								   data: "parKey="+dragNode.id+"&nowCode="+nowNode.id+"&level="+level,
								   success: function(data){
								   		if(data=="success"){
								   			alert("拖动成功！");
								   			treeObj.reAsyncChildNodes(dragNode, "refresh");
								   			showRemoveBtn(oldparentNode.id,oldparentNode);	
								   			return true;
								   		}
								   }
					});
						
				}else if(moveType=="prev"){
					seq=parseInt(dragNode.seq)-1;
					level=dragNode.level;		
					$.ajax({
						   type: "POST",
						   url: "sys-region!updateCode.do",
						   data: "parKey="+dragNode.getParentNode().id+"&nowCode="+nowNode.id+"&seq="+seq+"&level="+level,
						   success: function(data){
						   		if(data=="success"){
						   			alert("拖动成功！");
						   			treeObj.reAsyncChildNodes(dragNode.getParentNode(), "refresh");
						   			showRemoveBtn(oldparentNode.id,oldparentNode);	
						   			return true;
						   		}
						   }
					});
				}else if(moveType=="next"){
					level=dragNode.level;		
					if(parseInt(dragNode.seq)>=999){
						seq=999;
					}else{
						seq=parseInt(dragNode.seq)+1;
					}				
					$.ajax({
						   type: "POST",
						   url: "sys-region!updateCode.do",
						   data: "parKey="+dragNode.getParentNode().id+"&nowCode="+nowNode.id+"&seq="+seq+"&level="+level,
						   success: function(data){
						   		if(data=="success"){
						   			alert("拖动成功！");
						   			treeObj.reAsyncChildNodes(dragNode.getParentNode(), "refresh");
						   			showRemoveBtn(oldparentNode.id,oldparentNode);	
						   			return true;
						   		}
						   }
					});
				}	
		}
		function beforeDrop(treeId, treeNodes, targetNode, moveType) {
			if(moveType!="inner" && targetNode.id=="1"){
				return false;
			}
			if(treeNodes[0].isParent==false || treeNodes[0].getParentNode().id!=targetNode.id){
				if(confirm("确定 拖动 ‘" + treeNodes[0].name + "’ 部门吗？")){
					dragNode(treeNodes[0],targetNode, moveType);
				}else{
					return false;
				}
			}			
			return targetNode ? targetNode.drop !== false : true;
		}
		function beforeEditName(treeId, treeNode) {
			alert("beforeEditName");
			className = (className === "dark" ? "":"dark");
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			return confirm("确定 -- " + treeNode.name + " 的编辑状态吗？");
		}
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");	
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);	
			if(confirm("确定要删除‘" + treeNode.name + "’ 部门吗？")){
				//ajax调用后台进行数据库删除
				if(treeNode.tld!=undefined){
					$.ajax({
							   type: "POST",
							   url: "sys-region!delete.do",
							   data: "key="+treeNode.tld,
							   success: function(data){
							   		if(data=="success"){
							   			zTree.removeNode(treeNode);	
							   			zTree.reAsyncChildNodes(treeNode.getParentNode().getParentNode(), "refresh");
							   			return true;
							   		}else{
							   			alert(data);
							   			zTree.cancelSelectedNode();
							   			return false;
							   		}
							   }
					});
				}else{
					return true;
				}
			}
			return false;
			
		}
		function beforeRename(treeId, treeNode, newName) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");	
			var oldName=treeNode.name;
			className = (className === "dark" ? "":"dark");			
			if (newName.length == 0) {
				alert("节点名称不能为空.");							
				setTimeout(function(){zTree.editName(treeNode);}, 10);
				return false;
			}
			if(newName.length>32){
				alert("节点名称过长，请检查！");
				return false;
			}
			if(newName!=treeNode.name){
				if(confirm("确认要将节点重命名为 " + newName + " 吗？")){
					$.ajax({
					   type: "POST",
					   url: "sys-region!updateName.do",
					   data: "key="+treeNode.tld+"&newName="+newName,
					   success: function(data){
						   if(data=="success"){
							   treeNode.viewtype='2';
							   zTree.updateNode(treeNode);
						   	   return true;
						   }else{
							   alert("重命名失败,请联系管理员！");
							   var zTree = $.fn.zTree.getZTreeObj("treeDemo");	
							   zTree.cancelEditName();
							   return false;
						   }
					   }
					});
				}else{
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");	
					zTree.cancelEditName();
					return false;
				}
			}
			
		}
		
		function showRemoveBtn(treeId, treeNode) {
			if(treeNode.level=='0' || treeNode.isParent){
				return false;
			}else{
				return true;
			}
		}
		function showRenameBtn(treeId, treeNode) {
			if(treeNode.level=='0'){
				return false;
			}else{
				return true;
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.id
				+ "' title='添加部门' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.id);
			if (btn) btn.bind("click", function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var seq=1;
				if(treeNode.children && treeNode.children!="" ){
					var nodes=treeNode.children;
					seq=parseInt(nodes[nodes.length-1].seq)+1;
				}
				var name="新部门" + (newCount++);
				zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:name,seq:seq});
				var level=parseInt(treeNode.level)+1;
				$.ajax({
					   type: "POST",
					   url: "sys-region!save.do",
					   data: "seq="+seq+"&short="+name+"&parId="+treeNode.id+"&type=2&level="+level,
					   success: function(data){
					   		if(data!="error"){
					   			alert("部门添加成功！");				   			
					   			zTree.reAsyncChildNodes(treeNode, "refresh");
					   			return true;
					   		}else{
					   			alert("部门添加失败！");
					   			return false;
					   		}
					   }
					});				
				return false;
			});
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.id).unbind().remove();					
		};
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
		function showMenu() {
			var cityObj = $("#parentDept");
			var cityOffset = $("#parentDept").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

			$("html").bind("mousedown", onBodyDown);
			
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("html").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "parentDept" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}
		
		function onClick(e, treeId, treeNode) {
			if(treeNode.regiontype.indexOf("1")==-1){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
				nodes = zTree.getSelectedNodes();
				var name = nodes[0].name;
				var id = nodes[0].id;
				var key=nodes[0].tld;				
				var seq=nodes[0].seq;

				//后期需要用ajax获取数据后填充到表单		
				if(key!=undefined){
					$.ajax({
					   type: "POST",
					   url: "sys-region!getRegion.do",
					   data: "key="+key+"&type=2",
					   success: function(data){
						   var json=eval("("+data+")");
					     $("#regionRistCoe").attr("value", json.regionRistCoe);
					     $("#regionOphardCoe").attr("value", json.regionOphardCoe);
					     if(json.regionName==undefined){
					    	 $("#regionName").attr("value","");
					     }else{
					    	 $("#regionName").attr("value",json.regionName);
					     }
					     if(json.regionRemark==undefined){
					    	 $("#regionRemark").attr("value", "");
					     }else{
					    	 $("#regionRemark").attr("value", json.regionRemark);
					     }		
					     if(json.regionAssessType==undefined){
					    	 $("#regionAssessType").attr("value", "");
					     }else{
					    	 $("#regionAssessType").attr("value", json.regionAssessType);
					     }
					     $("#regionCode").attr("value", json.regionCode);
					     $("#personNum").attr("value", json.personNum);
					     $("#personName").attr("value", json.personName);
					   }
					});
				}else{
					  $("#regionRistCoe").attr("value", "");
					  $("#regionOphardCoe").attr("value", "");
					  $("#regionName").attr("value", "");
					  $("#regionRemark").attr("value", "");
					  $("#regionCode").attr("value", id);
				}		
				$("#seq").attr("value", seq);								
				$("#key").attr("value", key);				
				$("#regionShotName").attr("value", name);
				$("#parentDept").attr("value", nodes[0].getParentNode().name);
				$("#parentRegionCode").attr("value", nodes[0].getParentNode().id);				
			}
		}
		//treeId是treeDemo 
		function filter(treeId, parentNode, childNodes) { 
			if (!childNodes) return null; 
			for (var i=0, l=childNodes.length; i<l; i++) { 
				childNodes[i].name = childNodes[i].name.replace('',''); 
			} 
			return childNodes; 
		} 
		$(document).ready(function(){
			var validateUp = $("#errorUpForm").validate({
				errorPlacement: function(error, element) { 
					//return error;
				},
				showErrors:function(errorMap,errorList) {
					$(".formError").remove();
					for(var s in errorMap){
					}
					for(var i=0;errorList[i];i++){
						var ee=$(errorList[i].element);
						var emsg=errorMap[ee.attr("name")];
						var position = ee.position();
						var style="top:"+position.top+"px;left:"+position.left+"px;";
						var div=$('<div class="formError" style="'+style+'"><div class="formErrorContent">'+emsg+'</div><div class="formErrorArrow"><div class="line10"><!-- --></div><div class="line9"><!-- --></div><div class="line8"><!-- --></div><div class="line7"><!-- --></div><div class="line6"><!-- --></div><div class="line5"><!-- --></div><div class="line4"><!-- --></div><div class="line3"><!-- --></div><div class="line2"><!-- --></div><div class="line1"><!-- --></div></div></div>');
						ee.after(div);
						var top=position.top-div.outerHeight();
						div.attr("style","top:"+top+"px;left:"+position.left+"px;");
					}
			        //this.defaultShowErrors();
			    },
			    success:function(){
			    	//$(".formError").remove();
			    },
			    submitHandler: function(form){
					form.submit(); 
				}
			});
		
			$("#treeDemo").height($("html").height()-100);//-20
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$("#submits").click(function(){
				
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			
				var key = $("#key").val();
				var id = $("#regionCode").val();
				var nowCode=$("#nowkey").val();
				if(nowCode==id){
					alert("不能修改自己部门的信息！");
					return false;
				}
				var check=validateUp.form();
				if(check){	
					var treeNode = treeObj.getNodeByParam("id",id,null);
							
					var name = $("#regionName").val();
					var parId = $("#parentRegionCode").val();
					var ristCoe=$("#regionRistCoe").val();
					var ophardCoe=$("#regionOphardCoe").val();
					var regionShotName=$("#regionShotName").val();
					var seq=$("#seq").val();
					var regionRemark=$("#regionRemark").val();
					var personNum = $("#personNum").val();
					var regionAssessType = $("#regionAssessType").val();
					var level=treeNode.level;
					$.ajax({
					   type: "POST",
					   url: "sys-region!save.do",
					   data: "key="+key+"&seq="+seq+"&code="+id+"&name="+name+"&parId="+parId+"&short="+regionShotName+"&ristCoe="+ristCoe+"&ophardCoe="+ophardCoe+"&remark="+regionRemark+"&level="+level+"&type=2&level="+treeNode.level+"&personNum="+personNum+"&atype="+regionAssessType,
					   success: function(data){
					   		if(data!="error"){
					   			alert("部门数据更新成功！");				   			
								treeNode.icon = "";
								treeNode.tld = data;					
								treeNode.name=regionShotName;
								treeObj.updateNode(treeNode);
					   			return true;
					   		}else{
					   			alert("部门数据更新失败！");
					   			return false;
					   		}
					   }
					});
				}
				
			});
		});