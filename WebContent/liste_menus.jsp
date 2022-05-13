<%@ page import="pack.entities.Menu" %>
<%@ page import="pack.entities.Restaurant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<% Restaurant r = (Restaurant)request.getAttribute("restaurant"); %>

<p><%=r.getNom()%></p>
<p><%=r.getDescription()%></p>
<p><%=r.getType().getName()%></p>
<p>Liste des menues</p>
<% int i = 1; for(Menu m : r.getMenus()) {%>
<p>Menu <%=i++%>:</p>
<p><%=m.getName()%></p>
<p><%=m.getEuroPrice()%></p>
<br/>
<%} %>