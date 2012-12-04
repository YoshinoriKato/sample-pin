<%@page import="com.samplepin.common.Helper"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div id="f-make-card" class="modal hide fade">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h3>Add Card</h3>
	</div>
	<div class="modal-body">
		<jsp:include page="_make2.jsp"></jsp:include>
	</div>
<!-- 	<div class="modal-footer">
		<a href="#" class="btn btn-primary">Save</a>
	</div> -->
</div>


