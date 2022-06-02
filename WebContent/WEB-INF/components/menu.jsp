<%@ page import="pack.entities.Menu" %>
<%@ page import="pack.util.Utilities" %>
<%@ page import="pack.entities.Utilisateur" %>
<%@ page import="pack.entities.TypeUtilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utilisateur u = (Utilisateur) request.getAttribute("user");
    TypeUtilisateur tu = (TypeUtilisateur) request.getAttribute("userType");
    Menu m = (Menu) request.getAttribute("comp_Menu");
%>
<div>
    <span><b><%=m.getName()%></b></span> <br/>
    <span><%=Utilities.formatPrice(m.getPrix())%></span> <br/>
    <span>Restaurant: <b><%=m.getRestaurant().getNom()%></b></span> <br/>
    <% if(tu == TypeUtilisateur.CLIENT) { %>
    <form action="userServlet" method="post">
        <button type="submit">Ajouter au panier</button>
        <input type="hidden" name="op" value="ajoutPanier">
        <input type="hidden" name="menuId" value="<%=m.getId()%>">
    </form>
    <%}%>

    <% if(tu == TypeUtilisateur.MANAGER && m.getRestaurant().getProprio().getId() == u.getId()) { %>
    <form action="managerServlet">
        <button type="submit">Modifier ce menu</button>
        <input type="hidden" name="op" value="gererMenu">
        <input type="hidden" name="mid" value="<%=m.getId()%>">
        <input type="hidden" name="rid" value="<%=m.getRestaurant().getId()%>">
    </form>
    <%}%>
</div>
