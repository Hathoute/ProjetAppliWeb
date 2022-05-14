<%@ page import="pack.entities.Restaurant" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<% List<Restaurant> restaurants = (List<Restaurant>)request.getAttribute("restaurants"); %>

<% for(Restaurant resto : restaurants) {%>
<p><%=resto.getNom()%></p>
<p><%=resto.getDescription()%></p>
<p><%=resto.getType().getName()%></p>
<a href="dataServlet?op=listMenues&rid=<%=resto.getId()%>">Voir la liste des menus</a>
<p>-------------</p>
<%} %>