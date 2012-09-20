<%@page import="com.samplepin.common.Helper"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">

</script>

<div class="form-horizontal">
	<form class="form-search" action="javascript:searchGoogle()">
		<fieldset>
			<div class="control-group">
				<label for="search-box" class="control-label">Image Search</label>
				<div class="controls">
					<input type="search" id="search-box" name="search-box" value=""
						placeholder="Please search images. At last, press enter key."
						class="input-medium input-text text"> <input type="submit"
						class="btn  btn-submit" value="Search">
					<div id="float-box" class="input-text text"></div>
				</div>
			</div>
		</fieldset>
	</form>
</div>
<ul id="search-result"></ul>
