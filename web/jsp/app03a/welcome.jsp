<%--
  Created by IntelliJ IDEA.
  User: Yi
  Date: 2/21/2019
  Time: 12:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.text.DateFormat" %>
<%@page import="java.util.Date" %>
<html>
<head>
    <title>Todat's date</title>
</head>
<body>
<%
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
    String s = dateFormat.format(new Date());
    out.println("Today is " + s);
%>

</body>
</html>
