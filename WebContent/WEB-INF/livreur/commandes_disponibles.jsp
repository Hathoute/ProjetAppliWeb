<%@ page import="java.util.Collection" %>
<%@ page import="pack.util.Utilities" %>
<%@ page import="pack.entities.*" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    Utilisateur user = (Utilisateur) request.getAttribute("user");
    Collection<Commande> commandes = (Collection<Commande>) request.getAttribute("commandes");
%>

<% if (user == null || user.getType() != TypeUtilisateur.LIVREUR) {
    request.setAttribute("message", "Vous devez être un livreur pour accéder à cette page"); %>
    <jsp:forward page="/" />
<%}%>

<% if(commandes.size() == 0) { %>
    <h2>Aucune commande en attente.</h2>
<%} else {%>
<h2>Commandes en attente:</h2>
<% for(Commande c : commandes) { %>
<% request.setAttribute("comp_Commande", c); %>
<jsp:include page="/WEB-INF/components/commande.jsp"/>

<form action="livreurServlet" method="post">
    <button type="submit">Livrer cette commande</button>
    <input type="hidden" name="op" value="livrer">
    <input type="hidden" name="cid" value="<%=c.getId()%>">
</form>

<%}%>
<%}%>
