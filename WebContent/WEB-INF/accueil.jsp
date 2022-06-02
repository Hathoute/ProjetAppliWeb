<%@ page import="pack.entities.Restaurant" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>
<%@ page import="pack.entities.Menu" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    Collection<Restaurant> restaurants = (Collection<Restaurant>) request.getAttribute("restosVedette");
    Collection<Menu> menus = (Collection<Menu>) request.getAttribute("menusJour");
%>

<div>
    <h5>Restaurants en vedette</h5>
    <%for (Restaurant r : restaurants) {
        request.setAttribute("comp_Restaurant", r);
    %>
        <jsp:include page="/WEB-INF/components/restaurant.jsp"/>
    <%}%>
</div>

<div>
    <h5>Les menus du jour</h5>
    <%for (Menu m : menus) {
        request.setAttribute("comp_Menu", m);
    %>
    <jsp:include page="/WEB-INF/components/menu.jsp"/>
    <%}%>
</div>