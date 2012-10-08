
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
					<!-- <div id="folder-name"
						style="position: absolute; top: 20px; left: 20px;">
						<span id="folder-name-value">あたらしいフォルダ</span>
						<input type="button" value="Make" onclick="makeFolder()">
					</div> -->
					<div style="position: absolute; top: 10px; left: 20px;">
						<form id="make-folder"
							action="make-folder.do" method="post"><!-- 
							 --><input type="hidden" id="cards" name="cards" value=""><!-- 
							 --><input type="text" name="folderName" class="text"
								value="あたらしいフォルダ"><!-- 
							 --><input type="submit" value="Make" class="btn  btn-submit"
								style="position: relative; top: -3px;" onclick="makeFolder()">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" charset="utf-8">
	attach(window, 'load', function() {
/* 		$('#folder-name').click(function() {
			$('#form-folder-name').attr('value', $('#folder-name-value').text());
			pushPull('#folder-form', '#folder-name');
		});
	
		$('#folder-rename').click(function() {
			$('#folder-name-value').text($('#form-folder-name').val());
			pushPull('#folder-name', '#folder-form');
		});
	
		$('#folder-form').focusout(pushPull('#folder-name', '#folder-form')); */
	});
	
	function makeFolder() {
		$array = new Array();
		$('.selected-card').each(function (index, domEle){
			$array.push(domEle.id);
		});
		$('#cards').val($array);
		$('#make-folder').submit();
	}
</script>
