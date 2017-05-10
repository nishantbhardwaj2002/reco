<%--
  Created by IntelliJ IDEA.
  User: nishantbhardwaj2002
  Date: 3/2/17
  Time: 11:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
    <Head>
        <link rel="stylesheet" href="http://localhost:8080/resources/colorlib/css/style.css" />
        <title>Signin (Reco)</title>
    </Head>
    <Body>
    <div class="form">
        <form:form class="login-form" action="signin" method="post">
            <form:label path="username">Username</form:label>
            <form:input path="username" />
            <br />
            <form:label path="password">Password</form:label>
            <form:password path="password" />
            <br />
            <br />
            <button type="submit">Signin</button>
        </form:form>
        <br />
        <p class="message">Not registered? <a href="signup">Create an account</a></p>
    </div>
    </Body>
</html>
