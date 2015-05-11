<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login or Register</title>
</head>

<body>
<form action="/app/login" method="post">
    <fieldset>
        <legend>Login</legend>
        <table>
            <tr>
                <c:if test="${msg ne null}">
                    <td colspan="2">${msg}</td>
                </c:if>
            </tr>
            <tr>
                <td><label for="username">Username</label></td>
                <td><input type="text" id="username" name="j_username"/></td>
            </tr>
            <tr>
                <td><label for="password">Password</label></td>
                <td><input type="password" id="password" name="j_password"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Login"/></td>
            </tr>
        </table>
    </fieldset>
</form>
<a href="/app/newuser">Create Account</a>
</body>

</html>