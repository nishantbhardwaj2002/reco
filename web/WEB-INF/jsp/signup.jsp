<%--
  Created by IntelliJ IDEA.
  User: nishantbhardwaj2002
  Date: 3/3/17
  Time: 1:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
    <Head>
        <title>Signup</title>
    </Head>
    <Body>
        <form:form action="signup" method="post">
            <form:label path="username">Username</form:label>
            <form:input path="username" />
            <br />
            <form:label path="password">Password</form:label>
            <form:password path="password" />
            <br />
            <input type="submit" value="Submit" />
        </form:form>
        <br />
        <a href="signin">Signin</a>
    </Body>
</html>