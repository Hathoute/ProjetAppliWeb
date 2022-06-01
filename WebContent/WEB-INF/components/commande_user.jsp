<%@ page import="pack.entities.Restaurant" %>
<%@ page import="pack.entities.Commande" %>
<%@ page import="pack.entities.Menu" %>
<%@ page import="pack.util.Utilities" %>
<%@ page import="pack.entities.CommandeEtat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Commande c = (Commande) request.getAttribute("comp_Commande");
    CommandeEtat e = c.getEtat();
%>

<div>
    <span>Restaurant: <b><%=c.getRestaurant().getNom()%></b></span>
    <%  int index = 0;
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
    <span>Depuis le <%=c.getLastTime().toString()%></span>
    <%}%>
    <br/>
</div>
