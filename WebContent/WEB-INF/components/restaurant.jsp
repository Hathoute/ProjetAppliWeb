<%@ page import="pack.entities.Restaurant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
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
</div>
