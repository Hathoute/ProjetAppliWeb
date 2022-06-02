<%@ page import="pack.entities.Restaurant" %>
<%@ page import="pack.entities.Utilisateur" %>
<%@ page import="pack.entities.TypeUtilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utilisateur u = (Utilisateur) request.getAttribute("user");
    TypeUtilisateur tu = (TypeUtilisateur) request.getAttribute("userType");
    Restaurant r = (Restaurant) request.getAttribute("comp_Restaurant");
%>


<div>
    <span><b><%=r.getNom()%></b></span> <br/>
    <span><%=r.getDescription()%></span> <br/>
    <span><%=r.getType().getName()%></span> <br/>
    <form action="dataServlet">
        <button type="submit">Menus</button>
        <input type="hidden" name="op" value="listMenues">
        <input type="hidden" name="rid" value="<%=r.getId()%>">
    </form>

    <% if(tu == TypeUtilisateur.MANAGER && r.getProprio().getId() == u.getId()) { %>
    <form action="managerServlet">
        <button type="submit">Gérer mon restaurant</button>
        <input type="hidden" name="op" value="gererRestau">
        <input type="hidden" name="rid" value="<%=r.getId()%>">
    </form>
    <form action="managerServlet">
        <button type="submit">Gérer les commandes</button>
        <input type="hidden" name="op" value="listeAttente">
        <input type="hidden" name="rid" value="<%=r.getId()%>">
    </form>
    <%}%>
</div>
