<%@ page import="pack.entities.Menu" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Menu m = (Menu) request.getAttribute("comp_Menu");
%>
<div>
    <span><b><%=m.getName()%></b></span> <br/>
    <span><%=m.getEuroPrice()%></span> <br/>
    <span>Restaurant: <b><%=m.getRestaurant().getNom()%></b></span> <br/>
    <form action="interactionServlet">
        <button type="submit">Ajouter au panier</button>
        <input type="hidden" name="op" value="ajoutPanier">
        <input type="hidden" name="menu" value="<%=m.getId()%>">
    </form>
</div>
