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
<%} else {
    Collection<Commande> commandesEnAttente = user.getPanier().getCommandes().stream()
            .filter(x -> x.getEtat() == CommandeEtat.EN_ATTENTE)
            .collect(Collectors.toList());
    Collection<Commande> commandesEnLivraison = user.getPanier().getCommandes().stream()
            .filter(x -> x.getEtat() == CommandeEtat.EN_LIVRAISON)
            .collect(Collectors.toList());
    Collection<Commande> commandesRecues = user.getPanier().getCommandes().stream()
            .filter(x -> x.getEtat() == CommandeEtat.LIVRE)
            .collect(Collectors.toList());
    boolean any = commandesEnAttente.size() + commandesEnLivraison.size() + commandesRecues.size() > 0;

%>

<% if(!any) { %>
    <h2>Vous n'avez aucune commande en cours</h2>
<%}%>

<% if(commandesEnAttente.size() > 0) { %>
<h2>Commandes en attente:</h2>
<% for(Commande c : commandesEnAttente) { %>
<% request.setAttribute("comp_Commande", c); %>
<jsp:include page="components/commande_user.jsp"/>
<%}%>
<%}%>


<% if(commandesEnLivraison.size() > 0) { %>
<h2>Commandes en cours de livraison:</h2>
<% for(Commande c : commandesEnLivraison) { %>
<% request.setAttribute("comp_Commande", c); %>
<jsp:include page="components/commande_user.jsp"/>
<%}%>
<%}%>


<% if(commandesRecues.size() > 0) { %>
<h2>Commandes livrées:</h2>
<% for(Commande c : commandesRecues) { %>
<% request.setAttribute("comp_Commande", c); %>
<jsp:include page="components/commande_user.jsp"/>
<%}%>
<%}}%>
