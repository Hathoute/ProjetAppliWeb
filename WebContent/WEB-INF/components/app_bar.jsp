<%@ page import="pack.entities.Utilisateur" %>
<%@ page import="pack.managers.NavbarManager" %>
<%@ page import="pack.managers.NavbarEntry" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Random" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utilisateur user = (Utilisateur) request.getAttribute("user");
    List<NavbarEntry> entries = NavbarManager.generateEntries(user);
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Food</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <% int uniqueId = 1;
                for (NavbarEntry e : entries) {
                if(e.children.size() > 1) {
            %>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="dropdown_<%=uniqueId%>" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <%=e.title%>
                </a>
                <div class="dropdown-menu" aria-labelledby="dropdown_<%=uniqueId%>">
                    <% for (String key : e.children.keySet()) {%>
                    <a class="dropdown-item" href="<%=request.getContextPath() + e.children.get(key)%>"><%=key%></a>
                    <%}%>
                </div>
            </li>
                <%}
                else {
                    String title = e.children.keySet().stream().findFirst().get();
                %>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath() + e.children.get(title)%>">
                    <%=title%>
                </a>
            </li>
                <%}%>
            <%}%>
        </ul>
        <% if(user == null) { %>
        <form action="inscription">
            <button type="submit">Inscription</button>
        </form>
        <form action="connexion">
            <button type="submit">Connexion</button>
        </form>
        <%} else {%>
        <span>Salut, <b><%=user.getFullname()%></b></span>
        <%}%>
    </div>
</nav>