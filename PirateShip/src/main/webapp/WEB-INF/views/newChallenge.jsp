<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
</head>
<body>
	<div class="container">


		<h1>New Challenge</h1>
		<br />

		<div class="row">
			<label class="col-sm-2">Name</label>
			<div class="col-sm-10">${template.name}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Category</label>
			<div class="col-sm-10">${template.category}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Description</label>
			<div class="col-sm-10">${template.description}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Credits cost</label>
			<div class="col-sm-10">${template.creditsCost}</div>
		</div>

		<c:if test="${allowChallenge}">
			<form method="POST"
				action="http://localhost:8080/pirate/challengeInstances/challenge/${template.id}"
				enctype="multipart/form-data">
				target <select id="ID" name="ID">
					<option value="-1">Open Challenge</option>
					<c:forEach var="f" items="${friends}">
						<option value="${f.id}">${f.firstName}</option>
					</c:forEach>
				</select> <input type="submit" value="Challenge!">
			</form>

			<form method="POST"
				action="http://localhost:8080/pirate/challengeInstances/mutualChallenge/${template.id}"
				enctype="multipart/form-data">
				target <select id="ID" name="targetId">
					<c:forEach var="f" items="${friends}">
						<option value="${f.id}">${f.firstName}</option>
					</c:forEach>
				</select> <input type="submit" value="Mutual Challenge">
			</form>
		</c:if>

	</div>

	<form method="POST" action="http://localhost:8080/pirate/logout"
		enctype="multipart/form-data">
		<input type="submit" value="LOGOUT">
	</form>
</body>
</html>