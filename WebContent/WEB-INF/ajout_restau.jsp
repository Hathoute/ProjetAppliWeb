<%@ page import="pack.entities.TypeRestaurant" %>
<%@ page import="java.util.Collection" %>
<%@ page import="pack.entities.Utilisateur" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	Collection<TypeRestaurant> types = (Collection<TypeRestaurant>) request.getAttribute("types");
	Collection<Utilisateur> managers = (Collection<Utilisateur>) request.getAttribute("managers");
%>


<form action="adminServlet" method="post">
	Nom: <input type="text" name="nom"><br>
	Description: <input type="text" name="desc"><br>
	<select name="type">
		<% for(TypeRestaurant tr : types) {%>
		<option value="<%=tr.getId()%>"><%=tr.getName()%></option>
		<%}%>
	</select>
	<br/>
	<select name="manager">
		<% for(Utilisateur u : managers) {%>
		<option value="<%=u.getId()%>"><%=u.getFullname()%></option>
		<%}%>
	</select>
	<br/>
	<input type="submit" value="Ajouter">
	<input type="hidden" name="op" value="ajoutRestau">
</form>