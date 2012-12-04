<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<form class="form-search" action="javascript:searchGoogle()">
	<fieldset>
		<div class="form-horizontal">
			<div class="control-group">
				<label for="search-box" class="control-label">Image Search</label>
				<div class="controls">
					<!-- <div class="input-append"> -->
						<input type="search" id="search-box" name="search-box" value=""
							placeholder="At last, press enter key."
							class="text input-text"><!-- <input type="submit"
							class="btn" value="Search"> -->
					<!-- </div> -->
				</div>
			</div>
		</div>
	</fieldset>
</form>

<ul id="search-result"></ul>
