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


		<h1>Challenge</h1>
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
			<label class="col-sm-2">Status</label>
			<div class="col-sm-10">${c.status}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Deadline</label>
			<div class="col-sm-10">${c.deadline}</div>
		</div>

		<div class="row">
			<label class="col-sm-2">Issued by</label>
			<div class="col-sm-10">${challenger.firstName}
				${challenger.lastName}</div>
		</div>

		<c:if test="${not c.hanging}">
			<div class="row">
				<label class="col-sm-2">Target</label>
				<div class="col-sm-10">${target.firstName}${target.lastName}</div>
			</div>
		</c:if>


		<c:if test="${c.allowClaim}">
			<form method="POST"
				action="http://localhost:8080/pirate/challengeInstances/claim/${c.id}"
				enctype="multipart/form-data">
				<input type="submit" value="I can do this!Arr!">
			</form>
		</c:if>


		<c:if test="${c.allowBack}">
			<form method="POST"
				action="http://localhost:8080/pirate/challengeInstances/back/${c.id}"
				enctype="multipart/form-data">
				<input type="submit" value="You can do it, ${target.firstName}!">
			</form>
		</c:if>

		<c:if test="${c.allowUploadContent}">

			<form method="POST"
				action="http://localhost:8080/pirate/challengeInstances/postContent/${c.id}"
				enctype="multipart/form-data">
				Upload stuff here <input type="file" name="file">
				Fun<input
					type="text" name="fun">
				Interesting<input
					type="text" name="interesting">
				Difficult<input
					type="text" name="difficult">
				<input
					type="submit" value="Upload">
			</form>

			<c:if test="${c.allowReject}">
				<form method="POST"
					action="http://localhost:8080/pirate/challengeInstances/reject/${c.id}"
					enctype="multipart/form-data">
					<input type="submit" value="Reject challenge">
				</form>
			</c:if>

		</c:if>

		<c:if test="${c.allowMakePublic}">
			Make this challenge public so total strangers can vote on it:
			<form method="POST"
				action="http://localhost:8080/pirate/challengeInstances/makePublic/${c.id}"
				enctype="multipart/form-data">
				<input type="submit" value="Seems like a good idea..">
			</form>
		</c:if>

		<c:if test="${c.allowVote}">
			<form method="GET"
				action="http://localhost:8080/pirate/challengeInstances/getContent/${c.id}"
				enctype="multipart/form-data">
				<input type="submit" value="GET CONTENT">
			</form>

			<form method="POST"
				action="http://localhost:8080/pirate/challengeInstances/vote/${c.id}"
				enctype="multipart/form-data">
				<input type="submit" value="YES"> <input type="hidden"
					value="YES" name="vote" />
			</form>
			<form method="POST"
				action="http://localhost:8080/pirate/challengeInstances/vote/${c.id}"
				enctype="multipart/form-data">
				<input type="submit" value="NO"> <input type="hidden"
					value="NO" name="vote" />
			</form>
		</c:if>

		<c:if test="${not empty myVote}">
    		My Vote: ${myVote.pass}
		</c:if>
	</div>

	<form method="POST" action="http://localhost:8080/pirate/logout"
		enctype="multipart/form-data">
		<input type="submit" value="LOGOUT">
	</form>
</body>
</html>