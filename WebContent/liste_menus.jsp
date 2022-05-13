<%@ page import="pack.Restaurant" %>
<%@ page import="pack.Menu" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<% Restaurant r = (Restaurant)request.getAttribute("restaurant"); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des restaurants</title>
</head>
<body>

<p><%=r.getNom()%></p>
<p><%=r.getDescription()%></p>
<p><%=r.getType().getName()%></p>
<p>Liste des menues</p>
<% int i = 1; for(Menu m : r.getMenu()) {%>
<p>Menu <%=i++%>:</p>
<p><%=m.getName()%></p>
<p><%=m.getPrix()%>€</p>
<br/>
<%} %>

</body>
</html>