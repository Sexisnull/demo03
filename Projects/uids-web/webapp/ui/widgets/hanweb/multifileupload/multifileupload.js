var uploadFiles;

jQuery.fn.multifileupload = function(options) {
	if (options == 'getFiles') {
		var files = {};
		$(this).next('.upload_filelist').children('li').each(function() {
			var uuid = $(this).attr('uuid');
			var name = $(this).text();
			files[uuid] = name;
		});
		return files;
	}
	
	$(this).click(function() {
		uploadFiles = null;
		var fileListContainer = $(this).next('.upload_filelist');

		var dialogOptions = {};

		dialogOptions.onClose = function() {
			if (uploadFiles != null) {
				var uuids = new Array();
				var fileNames = new Array();
				var newNames = new Array();
				$.each(uploadFiles, function(index, uploadFile){
					var uuid = uploadFile.uuid;
					var fileName = uploadFile.fileName;
					var newName = uploadFile.newName;
					uuids.push(uuid);
					fileNames.push(fileName);
					newNames.push(newName);
					var li = '<li uuid="' + uuid + '"><i class="icon-minus-sign" title="移除"></i>' + fileName + '</li>';
					fileListContainer.append(li);
				});
				if(options.onClose){
					options.onClose(uuids.join(','), fileNames.join(','), newNames.join(','));
				}
			}
		};

		dialogOptions.upload = options;

		openDialog(options.dialogUrl, 500, 500, dialogOptions);
	});
};

$(function() {
	$('.upload_filelist').on('click', '.icon-minus-sign', function() {
		$(this).parent().animate({
			'margin-left' : '+=100',
			opacity : '0.1'
		}, 'fast', function() {
			$(this).css('visibility', 'hidden').slideUp('fast', function() {
				$(this).remove();
				var uuid = $(this).attr('uuid');
				delete uploadFiles[uuid];
			});
		});
	});
});
