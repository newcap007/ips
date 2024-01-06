<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var uploader;//上传对象	
	var submitForm = function($dialog, $grid, $pjq, parseData) {
		if ($('form').form('validate')) {
			if (uploader.files.length > 0) {
				uploader.start();
				uploader.bind('UploadComplete', function(uploader,files) {
					parent.frm.progressBar('close');//关闭上传进度条
					$grid.datagrid('load');
					$dialog.dialog('destroy');
				});
			} else {
				$pjq.messager.alert('提示', result.msg, '请选择文件');
			}
		}
	};
	$(function() {
		uploader = new plupload.Uploader({//上传插件定义
			browse_button : 'pickfiles',//选择文件的按钮
			container : 'container',//文件上传容器
			runtimes : 'html5,flash',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html4
			//flash_swf_url : frm.contextPath + '/jslib/plupload_1_5_7/plupload/js/plupload.flash.swf',// Flash环境路径设置
			url : frm.contextPath + '/collectionsUpload?fileFolder=/collections',//上传文件路径
			max_file_size : '5mb',//100b, 10kb, 10mb, 1gb
			chunk_size : '10mb',//分块大小，小于这个大小的不分块
			unique_names : true,//生成唯一文件名
			// 如果可能的话，压缩图片大小
			/*resize : {
				width : 320,fileFolder
				height : 240,
				quality : 90
			},*/
			// 指定要浏览的文件类型
			/* filters : [ {
				title : '图片文件',
				extensions : 'jpg,gif,png'
			} ] */
		});
		uploader.bind('Init', function(uploader, params) {//初始化时
			//$('#filelist').html("<div>当前运行环境: " + params.runtime + "</div>");
			$('#filelist').html("");
		});
		uploader.bind('BeforeUpload', function(uploader, file) {
			//上传之前
			//$('.ext-icon-cross').hide();
		});
		uploader.bind('FilesAdded', function(uploader, files) {//选择文件后
			$.each(files, function(i, file) {
				$('#filelist').append('<div id="' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ')<strong></strong>' + '<span onclick="uploader.removeFile(uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></div>');
			});
			uploader.refresh();
		});
		uploader.bind('UploadProgress', function(uploader, file) {//上传进度改变
			 var msg;
			if (file.percent == 100) {
				msg = '99';//因为某些大文件上传到服务器需要合并的过程，所以强制客户看到99%，等后台合并完成...
			} else {
				msg = file.percent;
			}
			$('#' + file.id + '>strong').html(msg + '%');

			parent.frm.progressBar({//显示文件上传滚动条
				title : '文件上传中...',
				value : msg
			}); 
		});
		uploader.bind('Error', function(uploader, err) {//出现错误
			$('#filelist').append("<div>错误代码: " + err.code + ", 描述信息: " + err.message + (err.file ? ", 文件名称: " + err.file.name : "") + "</div>");
			uploader.refresh();
		});
		uploader.bind('FileUploaded', function(uploader, file, info) {//上传完毕
			var response = $.parseJSON(info.response);
			if (response.status) {
				$('#' + file.id + '>strong').html("100%");
				console.info(response.fileUrl);
				console.info(file.name);
				//$('#f1').append('<input type="hidden" name="fileUrl" value="'+response.fileUrl+'"/>');
				//$('#f1').append('<input type="hidden" name="fileName" value="'+file.name+'"/><br/>');
				$(':input[name="data.fileUrl"]').val(response.fileUrl);
			}
		});
		uploader.init();		
	});
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>文件上传</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>文件上传</th>
					<td><div id="container">
							<a id="pickfiles" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom'">选择文件</a>
							<div id="filelist">您的浏览器没有安装Flash插件，或不支持HTML5！</div>
						</div>
					</td>
				</tr>
				<tr>
					<th></th>
					<td><input id="fileUrl" name="data.fileUrl" readonly="readonly"/></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>