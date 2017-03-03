<%--
  Created by IntelliJ IDEA.
  User: nishantbhardwaj2002
  Date: 3/3/17
  Time: 1:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Newsfeed</title>
    </head>
    <body>
        Hi <%= request.getSession().getAttribute("username") %>, here's the news recommended for you
        <br />
        <a href="signout">Signout</a>
    </body>
</html>
