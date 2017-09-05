/**
 * XMLParser
 */
function XMLParser() {}
/**
 * parse(_$_XMLDocument)
 */
XMLParser.prototype.parse = function(_$_XMLDocument) {
	var _this = this;
	
	var json = null; // nodes属性的默认值null
	
	if(_$_XMLDocument.length != 0) {
		json = new Array();
	}
	
	_$_XMLDocument.each(function(i, o) {
		json[i] = new Object();
		
		var attrs = o.attributes;
		
		for(var j = 0; j < attrs.length; j++) {
			var name = attrs[j].name;
			json[i][name] = attrs[j].value;
			
			// 对布尔值的处理，从xml获得为string类型
			if(json[i][name] == 'true') {
				json[i][name] = true;
			}
			else if(json[i][name] == 'false') {
				json[i][name] = false;
			}
		}
		
		var nodes = _this.parse($(o).children()); // 递归转换下级节点xml对象
		
		json[i].nodes = nodes; // nodes属性赋值
	});
	
	return json;
}