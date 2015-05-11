<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Page</title>
</head>
<body>
  <h1>Account Information</h1>
  <table>
    <tr><td>Username</td><td>${currentUser.username}</td></tr>
    <tr><td>Display Name</td><td>${currentUser.displayName}</td></tr>
    <tr><td>Emailaddress</td><td>${currentUser.emailAddress}</td></tr>
  </table>

  <a href="/app/logout">Logout</a>

</body>
</html>
