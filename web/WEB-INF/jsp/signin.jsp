<%--
  Created by IntelliJ IDEA.
  User: nishantbhardwaj2002
  Date: 3/2/17
  Time: 11:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
    <head>
        <title>Signin</title>
    </head>
    <body>
        <form:form action="signin" method="post">
            <form:label path="username">Username</form:label>
            <form:input path="username" />
            <br />
            <form:label path="password">Password</form:label>
            <form:password path="password" />
            <br />
            <input type="submit" value="Submit" />
        </form:form>
        <br />
        <a href="signup">Signup</a>
    </body>
</html>
