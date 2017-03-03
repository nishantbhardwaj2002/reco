<%--
  Created by IntelliJ IDEA.
  User: nishantbhardwaj2002
  Date: 3/2/17
  Time: 11:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Signin</title>
    </head>
    <body>
        <form action="signin" method="post">
            Username : <input type="text" name="username" />
            <br />
            Password : <input type="password" name="password" />
            <br />
            <input type="submit" value="Signin" />
        </form>
        <br />
        <a href="signup.jsp">Signup</a>
    </body>
</html>
