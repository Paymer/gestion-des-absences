<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Gestion des Absences</title>
		<link rel="stylesheet"
			href="<c:url value='/res/bootstrap-3.3.7-dist/css/bootstrap.min.css'/>">
		<link rel="stylesheet"
			href="<c:url value='/res/bootstrap-3.3.7-dist/js/bootstrap.min.js'/>">
		
		<style>
			a.btn.btn-night{
				color: #fff;
			    background-color: #191970;
			    border-color: #080860;
			}
			a.btn.btn-night input{
			}
		</style>
	</head>
	<body>
		<form method="post" id="nightForm">
			<input type="hidden" name="traitementNuit" value="true"/>
			<a class="btn btn-night" onClick="document.getElementById('nightForm').submit()">
				<span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
				Passer la nuit
			</a>
		</form>
	</body>
</html>