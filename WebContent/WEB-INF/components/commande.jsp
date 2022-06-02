<%@ page import="pack.util.Utilities" %>
<%@ page import="pack.entities.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    TypeUtilisateur tu = (TypeUtilisateur) request.getAttribute("userType");
    Commande c = (Commande) request.getAttribute("comp_Commande");
    CommandeEtat e = c.getEtat();
%>

<div>
    <span>Commande #<%=c.getId()%></span>
    <span>Restaurant: <b><%=c.getRestaurant().getNom()%></b></span>
    <%
        for (Menu m : c.getMenus()) { %>
    <div>
        <span>Menu: <b><%=m.getName()%></b></span> <br/>
        <span><%=Utilities.formatPrice(m.getPrix())%></span> <br/>

        <%if (e == CommandeEtat.NONE) {%>
        <form action="userServlet" method="post">
            <button type="submit">Retirer</button>
            <input type="hidden" name="op" value="retierMenuPanier">
            <input type="hidden" name="menuId" value="<%=m.getId()%>">
            <input type="hidden" name="commandeId" value="<%=c.getId()%>">
        </form>
        <%}%>
    </div>
    <%}%>
    <span>Total: <b><%=Utilities.formatPrice(c.getTotalPrice())%></b></span> <br/>
    <%if (e == CommandeEtat.NONE) {%>
    <form action="userServlet" method="post">
        <button type="submit">Retirer la commande</button>
        <input type="hidden" name="op" value="retierCommandePanier">
        <input type="hidden" name="commandeId" value="<%=c.getId()%>">
    </form>
    <%} else {%>
    <span>Depuis le <%=c.getLastTime().toString()%></span> <br/>
    <% if (tu == TypeUtilisateur.MANAGER && e == CommandeEtat.EN_ATTENTE) { %>
    <form action="managerServlet" method="post">
        <button type="submit">Valider la commande</button>
        <input type="hidden" name="op" value="validerCommande">
        <input type="hidden" name="commandeId" value="<%=c.getId()%>">
    </form>
    <%}%>
    <%}%>
    <br/>
</div>
