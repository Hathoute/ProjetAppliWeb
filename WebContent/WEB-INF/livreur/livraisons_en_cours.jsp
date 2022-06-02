<%@ page import="java.util.Collection" %>
<%@ page import="pack.util.Utilities" %>
<%@ page import="pack.entities.*" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    Utilisateur user = (Utilisateur) request.getAttribute("user");
    Collection<Livraison> livraisons = (Collection<Livraison>) request.getAttribute("livraisons");
%>

<% if (user == null || user.getType() != TypeUtilisateur.LIVREUR) {
    request.setAttribute("message", "Vous devez être un livreur pour accéder à cette page"); %>
    <jsp:forward page="/" />
<%}%>

<% if(livraisons.size() == 0) { %>
    <h2>Vous n'avez aucune livraison à livrer.</h2>
<%} else {%>
<h2>Livraisons en cours:</h2>
<% for(Livraison l : livraisons) { %>
<% request.setAttribute("comp_Commande", l.getCommande()); %>
<jsp:include page="/WEB-INF/components/commande.jsp"/>

<form action="livreurServlet" method="post">
    <input type="submit" value="Valider la livraison">
    <input type="hidden" name="lid" value="<%=l.getId()%>">
    <input type="hidden" name="op" value="validerLivraison">
</form>
<%}%>
<%}%>
