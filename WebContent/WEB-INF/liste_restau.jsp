<%@ page import="pack.entities.Restaurant" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<% List<Restaurant> restaurants = (List<Restaurant>)request.getAttribute("restaurants"); %>

<%for (Restaurant r : restaurants) {
    request.setAttribute("comp_Restaurant", r);
%>
    <jsp:include page="components/restaurant.jsp"/>
<%}%>