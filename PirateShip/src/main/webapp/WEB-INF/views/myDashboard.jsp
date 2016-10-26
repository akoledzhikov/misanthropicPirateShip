<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Dashboard</title>
</head>


<body>
	<h1>My Name is ${user.firstName} ${user.lastName}</h1>
	<h1>My Points ${user.points}</h1>
	<h1>My Credits ${user.credits}</h1>
	<h1>My Challenges</h1>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Deadline</th>
				<th>Go to challenge</th>
			</tr>
		</thead>

		<c:forEach var="c" items="${myChallenges}">
			<tr>
				<td>${c.name}</td>
				<td>${c.deadline}</td>
				<td>
					<form method="GET"
						action="http://localhost:8080/pirate/challengeInstances/${c.id}"
						enctype="multipart/form-data">
						<input type="submit" value="GO">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

	<h1>Challenges for the week</h1>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>View</th>
			</tr>
		</thead>

		<c:forEach var="t" items="${activeTemplates}">
			<tr>
				<td>${t.name}</td>
				<td>
					<form method="GET"
						action="http://localhost:8080/pirate/challengeTemplates/${t.id}"
						enctype="multipart/form-data">
						<input type="submit" value="GO">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

	<h1>My friends (or enemies that still breathe if I don't have any
		friends:)</h1>
	<table class="table table-striped">

		<c:forEach var="f" items="${friends}">
			<tr>
				<td>
					<form method="GET"
						action="http://localhost:8080/pirate/userProfile/${f.id}"
						enctype="multipart/form-data">
						<input type="submit" value="${f.firstName}">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

	<h1>Challenges I have to vote on</h1>
	<table class="table table-striped">

		<c:forEach var="c" items="${toBeVoted}">
			<tr>
				<td>
					<form method="GET"
						action="http://localhost:8080/pirate/challengeInstances/${c.id}"
						enctype="multipart/form-data">
						<input type="submit" value="${c.name}">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

	<h1>Challenges I have completed</h1>
	<table class="table table-striped">

		<c:forEach var="c" items="${completed}">
			<tr>
				<td>${c.name}
					<form method="GET"
						action="http://localhost:8080/pirate/challengeInstances/${c.id}"
						enctype="multipart/form-data">
						<input type="submit" value="SEE">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>


	<h1>Public Challenges</h1>
	<table class="table table-striped">

		<c:forEach var="c" items="${publicc}">
			<tr>
				<td>${c.name}
					<form method="GET"
						action="http://localhost:8080/pirate/challengeInstances/${c.id}"
						enctype="multipart/form-data">
						<input type="submit" value="SEE">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>


	<h1>Hanging Challenges</h1>
	<table class="table table-striped">

		<c:forEach var="c" items="${hanging}">
			<tr>
				<td>${c.name}
					<form method="GET"
						action="http://localhost:8080/pirate/challengeInstances/${c.id}"
						enctype="multipart/form-data">
						<input type="submit" value="SEE">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>


	<h1>Mutual Challenges</h1>
	<table class="table table-striped">

		<c:forEach var="c" items="${mutual}">
			<tr>
				<td>${c.name}
					<form method="GET"
						action="http://localhost:8080/pirate/challengeInstances/mutualChallenge/${c.id}"
						enctype="multipart/form-data">
						<input type="submit" value="SEE">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

	<form method="POST" action="http://localhost:8080/pirate/logout"
		enctype="multipart/form-data">
		<input type="submit" value="LOGOUT">
	</form>
</body>
</html>