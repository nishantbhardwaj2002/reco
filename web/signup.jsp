<%--
  Created by IntelliJ IDEA.
  User: nishantbhardwaj2002
  Date: 3/3/17
  Time: 1:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(session != null && session.getAttribute("user") != null) {
        response.sendRedirect("newsfeed.jsp");
    }
%>
<html>
    <head>
        <title>Signup</title>
    </head>
    <body>
        <form action="signup" method="post">
            Username : <input type="text" name="username"/>
            <br />
            Password : <input type="password" name="password"/>
            <br />
            <input type="submit" value="Signup">
        </form>
        <br />
        <a href="signin.jsp">Signin</a>
    </body>
</html>