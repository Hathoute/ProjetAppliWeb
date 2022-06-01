<%@ page import="pack.entities.Menu" %>
<%@ page import="pack.util.Utilities" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Menu m = (Menu) request.getAttribute("comp_Menu");
%>
<div>
    <span><b><%=m.getName()%></b></span> <br/>
    <span><%=Utilities.formatPrice(m.getPrix())%></span> <br/>
    <span>Restaurant: <b><%=m.getRestaurant().getNom()%></b></span> <br/>
    <form action="userServlet" method="post">
        <button type="submit">Ajouter au panier</button>
        <input type="hidden" name="op" value="ajoutPanier">
        <input type="hidden" name="menuId" value="<%=m.getId()%>">
    </form>
</div>
