<%@ page import="java.util.Collection" %>
<%@ page import="pack.entities.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	Collection<TypeRestaurant> types = (Collection<TypeRestaurant>) request.getAttribute("types");
	Restaurant r = (Restaurant) request.getAttribute("restaurant");
%>

<% if (r != null) { %>
<h2>Modifier le restaurant <b><%=r.getNom()%></b>:</h2>
<% } else { %>
<h2>Ajouter un restaurant:</h2>
<%}%>


<form action="managerServlet" method="post">
	Nom: <input type="text" name="nom" value="<%=r == null ? "" : r.getNom()%>"><br>
	Description: <input type="text" name="desc" value="<%=r == null ? "" : r.getDescription()%>"><br>
	<select name="type">
		<% for(TypeRestaurant tr : types) {%>
		<option value="<%=tr.getId()%>" <%=r == null ? "" : "selected"%> ><%=tr.getName()%></option>
		<%}%>
	</select>
	<br/>
	<br/>

	<% if (r != null) { %>
	<input type="hidden" name="op" value="modifierRestau">
	<input type="hidden" name="editId" value="<%=r.getId()%>">
	<input type="submit" name="modifier" value="Modifier">
	<input type="submit" name="supprimer" value="Supprimer">
	<% } else { %>
	<input type="submit" value="Ajouter">
	<input type="hidden" name="op" value="ajoutRestau">
	<% } %>
</form>


<% if (r != null) { %>
	<%for (Menu m : r.getMenus()) {
		request.setAttribute("comp_Menu", m);
	%>
	<jsp:include page="/WEB-INF/components/menu.jsp"/>
	<%}%>

	<form action="managerServlet" method="get">
		<input type="submit" value="Ajouter un menu">
		<input type="hidden" name="op" value="ajoutMenu">
		<input type="hidden" name="rid" value="<%=r.getId()%>">
	</form>

<%}%>