<%--
  Created by IntelliJ IDEA.
  User: Yi
  Date: 4/18/2019
  Time: 6:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Country List</title>
</head>
<body>
We operate in these countries:
<ul>
    <c:forEach items='${countries}' var='country'>
        <li>${country.value}</li>
    </c:forEach>
    <a href='../../image/Purdue.jpg'>Purdue.jpg</a>
    <img src='../../image/Purdue.jpg'/>
</ul>
</body>
</html>
