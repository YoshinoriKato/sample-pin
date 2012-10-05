
<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="float-card" style="width: 360px; height: 40px;">
	<div id="select-small-card">
		<div class="form-horizontal">
			<div class="control-group">
				<div class="controls">
					<div id="folder-name"
						style="position: absolute; top: 20px; left: 20px;">
						<span id="folder-name-value">あたらしいフォルダ</span>
						<input type="button" value="Make" onclick="makeFolder()">
					</div>
					<div id="folder-form"
						style="position: absolute; top: 10px; left: 20px;">
						<input type="text" name="folder-name" id="form-folder-name"
							class="text"><input type="submit" id="folder-rename"
							value="Rename" class="btn  btn-submit"
							style="position: relative; top: -3px;">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<form style="display: none;" id="make-folder" action="make-folder.do" method="post">
	<input type="hidden" id="folderName" name="folderName" value="">
	<input type="hidden" id="cards" name="cards" value="">
</form>

<script type="text/javascript" charset="utf-8">
	attach(window, 'load', function() {
		$('#folder-name').click(function() {
			$('#form-folder-name').attr('value', $('#folder-name-value').text());
			pushPull('#folder-form', '#folder-name');
		});
	
		$('#folder-rename').click(function() {
			$('#folder-name-value').text($('#form-folder-name').val());
			pushPull('#folder-name', '#folder-form');
		});
	
		$('#folder-form').focusout(pushPull('#folder-name', '#folder-form'));
	});
	
	function makeFolder() {
		$name = $('#folder-name-value').text();
		$array = new Array();
		$('.selected-card').each(function (index, domEle){
			$array.push(domEle.id);
		});
		$('#folderName').val($name);
		$('#cards').val($array);
		alert($name + $array);
		$('#make-folder').submit();
	}
</script>
