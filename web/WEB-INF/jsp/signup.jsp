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
        <link rel="stylesheet" href="http://localhost:8080/resources/signinPageStyle.css" />
        <title>Signup</title>
    </Head>
    <Body>
    <div class="form">
    <form:form class="login-form" action="signup" method="post">
            <form:label path="username">Username</form:label>
            <form:input path="username" />
            <br />
            <form:label path="password">Password</form:label>
            <form:password path="password" />
            <br />
            <br />
        <button type="submit">Signup</button>
        </form:form>
        <br />
        <p class="message">Already registered? <a href="signin">Signin</a></p>
    </div>
    </Body>
</html>