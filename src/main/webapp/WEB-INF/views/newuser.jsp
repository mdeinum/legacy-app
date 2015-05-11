<%@page session="false" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>New User Registration</title>
</head>
<body>

<form:form action="/app/newuser" method="post" commandName="user">
    <div class="errors">
        <form:errors path="*"/>
    </div>
    <fieldset>
        <legend>New User</legend>
        <table>
            <tr>
                <td><form:label path="username">Username</form:label></td>
                <td><form:input path="username"/></td>
            </tr>
            <tr>
                <td><form:label path="password">Password</form:label></td>
                <td><form:password path="password"/></td>
            </tr>
            <tr>
                <td><form:label path="passwordValidation">Password (Validation)</form:label></td>
                <td><form:password path="passwordValidation"/></td>
            </tr>
            <tr>
                <td><form:label path="displayName">Display Name</form:label></td>
                <td><form:input path="displayName"/></td>
            </tr>
            <tr>
                <td><form:label path="emailAddress">Email</form:label></td>
                <td><form:input path="emailAddress"/></td>
            </tr>
            <tr>
                <td><form:label path="emailValidation">Email (Validation)</form:label></td>
                <td><form:input path="emailValidation"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="save"/></td>
            </tr>
        </table>
    </fieldset>
    </fieldset>

</form:form>


</body>
</html>