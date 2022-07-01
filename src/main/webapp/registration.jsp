<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
    <title>Registration</title>
    </head>
    <body>
    <% String log = (String)session.getAttribute("login"); %>
    <% String pass = (String)session.getAttribute("password"); %>

    <form action="/registration" method="POST">
        New login: <br><input type="text" name="newLogin"><br>
        <% if ("short".equals(log)) { %>
        <span style="color: red"><i>Login must contain minimum 5 symbols</i></span><br>
        <% } else if ("already_exists".equals(log)) { %>
        <span style="color: red"><i>An account with such login already exists</i></span><br>
        <% } %>
        New password: <br><input type="password" name="newPassword"><br>
        <% if ("short".equals(pass)) { %>
        <span style="color: red"><i>Password must contain minimum 10 symbols</i></span><br>
        <% } %>
        Confirm password: <br><input type="password" name="confirmPassword"><br>
        <% if ("mismatch".equals(pass)) { %>
        <span style="color: red"><i>Passwords do not match</i></span><br>
        <% } %>
        <% session.removeAttribute("login");%>
        <% session.removeAttribute("password");%>
        <br><input type="submit" /><br>
    </form>
    </body>
</html>
