<%@ page import="pack.entities.Utilisateur" %>
<%@ page import="pack.entities.Restaurant" %>
<%@ page import="java.util.Collection" %>
<%@ page import="pack.entities.Menu" %>
<%@ page import="pack.entities.Produit" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	Menu menu = (Menu) request.getAttribute("menu");
	Produit p = (Produit) request.getAttribute("produit");
%>

<% if (p != null) { %>
<h2>Modifier le produit <b><%=p.getNom()%></b>:</h2>
<% } else { %>
<h2>Ajouter un produit:</h2>
<%}%>

<form action="managerServlet" method="post">
	Nom: <input type="text" name="nom" value="<%=p == null ? "" : p.getNom()%>"><br>
	Description: <input type="text" name="desc" value="<%=p == null ? "" : p.getDescription()%>"><br>

	<% if (p != null) { %>
	<input type="hidden" name="op" value="modifierProduit">
	<input type="hidden" name="editId" value="<%=p.getId()%>">
	<input type="submit" name="modifier" value="Modifier">
	<input type="submit" name="supprimer" value="Supprimer">
	<% } else { %>
	<input type="submit" value="Ajouter">
	<input type="hidden" name="menu" value="<%=menu.getId()%>">
	<input type="hidden" name="op" value="ajoutProduit">
	<% } %>
</form>