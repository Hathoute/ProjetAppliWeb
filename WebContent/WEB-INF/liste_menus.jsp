<%@ page import="pack.entities.Menu" %>
<%@ page import="pack.entities.Restaurant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<% Restaurant r = (Restaurant)request.getAttribute("restaurant"); %>

<%for (Menu m : r.getMenus()) {
    request.setAttribute("comp_Menu", m);
%>
    <jsp:include page="components/menu.jsp"/>
<%}%>