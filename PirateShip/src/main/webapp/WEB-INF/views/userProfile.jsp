<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User profile for</title>
</head>


<body>
	<h1>This is the profile for ${user.firstName} ${user.lastName}</h1>
	<h1>Active Challenges</h1>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Deadline</th>
				<th>Go to challenge</th>
			</tr>
		</thead>

		<c:forEach var="c" items="${active}">
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

	<h1>Pending Challenges</h1>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
			</tr>
		</thead>

		<c:forEach var="c" items="${pending}">
			<tr>
				<td>${c.name}</td>
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

	<h1>Challenges ${user.firstName} ${user.lastName} has completed</h1>
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

	<form method="POST" action="http://localhost:8080/pirate/logout"
		enctype="multipart/form-data">
		<input type="submit" value="LOGOUT">
	</form>
</body>
</html>