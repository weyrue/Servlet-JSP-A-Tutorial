<%--
  Created by IntelliJ IDEA.
  User: Yi
  Date: 2/21/2019
  Time: 12:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.Enumeration" %>
<html>
<head>
    <title>JSP Implicit Objects</title>
</head>
<body>
<b>Http headers:</b><br/>
<%
    for (Enumeration<String> e = request.getHeaderNames();
         e.hasMoreElements(); ) {
        String header = e.nextElement();
        out.println(header + ": " + request.getHeader(header) + "<br/>");
    }

%>
<hr/>
<%
    out.println("Buffer size: " + response.getBufferSize() + "<br/>");
    out.println("Session  id: " + session.getId() + "<br/>");
    out.println("Servlet name: " + config.getServletName() + "<br/>");
    out.println("Server info: " + application.getServerInfo());
%>

</body>
</html>
