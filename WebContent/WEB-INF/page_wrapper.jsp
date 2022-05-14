<%@ page import="pack.util.RuntimeEnvironment" %><%--
  Created by IntelliJ IDEA.
  User: Hathoute
  Date: 5/13/2022
  Time: 9:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pageTitle = (String) request.getAttribute("pageTitle");
    String file = (String) request.getAttribute("file");
    String message = (String) request.getAttribute("message");
%>

<html>
<head>
    <title><%=pageTitle%></title>
</head>
<body>
<% if(RuntimeEnvironment.isDebugging()) { %>
    <p>Currently loaded file: <%=file%></p>
<%}%>
<% if(message != null) { %>
    <p><%=message%></p>
<%}%>
    <jsp:include page="${file}"></jsp:include>
</body>
</html>
