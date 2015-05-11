<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome New User</title>
</head>
<body>

  <h1>Welcome</h1>
    <p>
        Welcome ${user.displayName}, a welcome email has been sent to ${user.emailAddress}.
    </p>
    <p>
        <a href="/app/">Login</a>.
    </p>
</body>
</html>
