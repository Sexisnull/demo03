window.Workflow = window.Workflow || {};

Workflow.width = 1000;
Workflow.height = 600;
Workflow.left = (window.screen.availWidth - Workflow.width)/2;
Workflow.top = (window.screen.availHeight - Workflow.height)/2,

Workflow.DOING = "doing";
Workflow.EDIT = "edit";
Workflow.END = "end";
 
Workflow.dialog = {
	url : 'workflowTemplate!add.do?random=' + Math.random(),
	features : 'dialogLeft:' + Workflow.left + 
			'px;dialogTop:' + Workflow.top + 'px;dialogHeight:' + 
			Workflow.height + 'px;dialogWidth:' + Workflow.width + 'px;help:no;status:yes;scroll:no;resizable:yes' 
}

/**
* @description 打开流程查看窗口
*/
Workflow.openWfviewDialog = function(ObjectId,type){
	var obj = new Object();
	var isSubmit = true;
	//判断用户是否点击保存按钮
	var isSave = false;
	//发送催办消息
	obj.sendUrgeMessage = function(win,wfId) {
		var url = "workflowTemplate!workflowUrge.do?workflowId="+wfId+"&random=" + Math.random();
		var obj = new Object();
		win.showModalDialog(url ,obj,"dialogWidth=600px;dialogHeight=450px;help:no;status:yes;scroll:no");
	}
	if (Workflow.EDIT == type) {
		this.dialog.url = 'workflow/workflowTemplate!editDoing.do?wfInstanceId=' + ObjectId + '&random=' + Math.random();
	} else if(Workflow.DOING == type) {
		this.dialog.url = 'workflow/workflowTemplate!viewDoing.do?wfInstanceId=' + ObjectId + '&random=' + Math.random();
	} else if(Workflow.END == type) {
		this.dialog.url = 'workflow/workflowTemplate!viewDoing.do?wfInstanceId=' + ObjectId + '&isEnd=1&random=' + Math.random();
	}
	var sReturn = window.showModalDialog (this.dialog.url, obj, this.dialog.features) ;
	return isSave;
}
 