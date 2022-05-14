<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<form action="Servlet" method="get">
	Nom: <input type="text" name="nom_menu"><br>
	Prix: <input type="text" name="prix"><br>
	Descriptif: <input type="text" name="descr"><br>
	Restaurant: <input type="text" name="restaurant"><br>
	<input type="submit" value="Ajouter">
	<input type="hidden" name="op" value="ajoutMenu"> 
</form>