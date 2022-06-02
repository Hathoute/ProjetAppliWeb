<%@ page import="java.util.Collection" %>
<%@ page import="pack.entities.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	ListeAttente l = (ListeAttente) request.getAttribute("listeAttente");
%>

<h2>Vous avez <%=l.getCommandes().size()%> commande(s) en attente:</h2>

<% for(Commande c : l.getCommandes()) {
	request.setAttribute("comp_Commande", c);%>
	<jsp:include page="/WEB-INF/components/commande.jsp"/>
<%}%>