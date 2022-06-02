<%@ page import="pack.entities.Utilisateur" %>
<%@ page import="pack.entities.Restaurant" %>
<%@ page import="java.util.Collection" %>
<%@ page import="pack.entities.Menu" %>
<%@ page import="pack.entities.Produit" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	Restaurant r = (Restaurant) request.getAttribute("restaurant");
	Menu m = (Menu) request.getAttribute("menu");
	Collection<Produit> produits = (Collection<Produit>) request.getAttribute("produits");
%>

<% if (m != null) { %>
<h2>Modifier le menu <b><%=m.getName()%></b>:</h2>
<% } else { %>
<h2>Ajouter un menu:</h2>
<%}%>

<form action="managerServlet" method="post">
	Nom: <input type="text" name="nom" value="<%=m == null ? "" : m.getName()%>"><br>
	Prix (en Cents): <input type="number" name="prix" value="<%=m == null ? "" : m.getPrix()%>"><br>

	<% if (m != null) { %>
	<input type="hidden" name="op" value="modifierMenu">
	<input type="hidden" name="editId" value="<%=m.getId()%>">
	<input type="submit" name="modifier" value="Modifier">
	<input type="submit" name="supprimer" value="Supprimer">
	<% } else { %>
	<input type="submit" value="Ajouter">
	<input type="hidden" name="restau" value="<%=r.getId()%>">
	<input type="hidden" name="op" value="ajoutMenu">
	<% } %>
</form>

<% if (m != null) { %>

<%for (Produit p : produits) {
	request.setAttribute("comp_Produit", p);
%>
	<jsp:include page="/WEB-INF/components/produit.jsp"/>
<%}%>

<form action="managerServlet" method="get">
	<input type="submit" value="Ajouter un produit">
	<input type="hidden" name="op" value="ajoutProduit">
	<input type="hidden" name="mid" value="<%=m.getId()%>">
</form>

<%}%>