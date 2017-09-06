/**
 * AjaxModel
 */
function AjaxModel(options) {
	this.loadUrl = null;    // 请求路径
	this.data = null;       // 请求参数对象
	this.dataType = 'json'; // 返回值类型，默认json
	this.timeout = 10000;   // 请求超时时间，默认10秒
	
	$.extend(this, options);
}
/**
 * load()
 */
AjaxModel.prototype.load = function() {
	var _this = this;
	
	$.ajax({
		url: this.loadUrl,
		data: this.data,
		cache: false,
		async: true,
		type: 'POST',
		dataType: this.dataType,
		timeout: this.timeout,
		beforeSend: function() {
			_this.beforeSend();
		},
		error: function() {
			alert('jqTree Ajax load error');
		},
		success: function(data) {
			data = _this.xmlParse(data);
			_this.success(data);
		}
	});
}
/**
 * success(data)
 */
AjaxModel.prototype.success = function(data) {}
/**
 * xmlParse(data)
 */
AjaxModel.prototype.xmlParse = function(data) {}
/**
 * beforeSend()
 */
AjaxModel.prototype.beforeSend = function() {}