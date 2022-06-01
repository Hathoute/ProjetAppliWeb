<%@ page import="java.util.Collection" %>
<%@ page import="pack.util.Utilities" %>
<%@ page import="pack.entities.*" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    Utilisateur user = (Utilisateur) request.getAttribute("user");
%>

<% if (user == null || user.getType() != TypeUtilisateur.CLIENT) {
    request.setAttribute("message", "Vous devez être connecté pour accéder au panier"); %>
    <jsp:forward page="/" />
<%} else if(user.getPanier().getCommandesByEtat(CommandeEtat.NONE).size() > 0) {%>

<h2>Votre Panier:</h2>
<% for(Commande c : user.getPanier().getCommandesByEtat(CommandeEtat.NONE)) { %>
    <% request.setAttribute("comp_Commande", c); %>
    <jsp:include page="components/commande_user.jsp"/>
<%}%>
<span>Total des commandes: <%=Utilities.formatPrice(user.getPanier().getTotalPrice())%></span><br/>
<form method="post" action="userServlet">
    <button type="submit">Procéder au paiement</button>
    <input type="hidden" name="op" value="validerPanier">
</form>
<%} else {%>
<h2>Votre panier est vide.</h2>
<%}%>