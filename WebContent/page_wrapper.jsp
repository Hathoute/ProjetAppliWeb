<%--
  Created by IntelliJ IDEA.
  User: Hathoute
  Date: 5/13/2022
  Time: 9:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pageTitle = (String) request.getAttribute("pageTitle");
%>

<html>
<head>
    <title><%=pageTitle%></title>
</head>
<body>
    <jsp:include page="${htmlFile}"></jsp:include>
</body>
</html>
