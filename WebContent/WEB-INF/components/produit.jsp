<%@ page import="pack.entities.Menu" %>
<%@ page import="pack.util.Utilities" %>
<%@ page import="pack.entities.Utilisateur" %>
<%@ page import="pack.entities.TypeUtilisateur" %>
<%@ page import="pack.entities.Produit" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utilisateur u = (Utilisateur) request.getAttribute("user");
    TypeUtilisateur tu = (TypeUtilisateur) request.getAttribute("userType");
    Produit p = (Produit) request.getAttribute("comp_Produit");
%>
<div>
    <span><b><%=p.getNom()%></b></span> <br/>

    <% if(tu == TypeUtilisateur.MANAGER && p.getRestaurant().getProprio().getId() == u.getId()) { %>
    <form action="managerServlet">
        <button type="submit">Gérer ce produit</button>
        <input type="hidden" name="op" value="gererProduit">
        <input type="hidden" name="pid" value="<%=p.getId()%>">
        <input type="hidden" name="mid" value="<%=-1%>">
    </form>
    <%}%>
</div>
