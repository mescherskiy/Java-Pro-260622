<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Prog Academy</title>
  </head>
  <body>
    <% String login = (String)session.getAttribute("user_login"); %>
    <% String passStrength = (String)session.getAttribute("pass_strength"); %>
    <% String age = (String)session.getAttribute("user_age"); %>
    <% String input = (String)session.getAttribute("input"); %>
    <% String newAccount = (String) session.getAttribute("new_account"); %>

    <% if ("created".equals(newAccount)) { %>
    New account successfully created!<br>
    <% session.removeAttribute("new_account"); %>
    <% } %>

    <% if (login == null || "".equals(login)) { %>
        <form action="/login" method="POST">
            Login: <br><input type="text" name="login"><br>
            Password: <br><input type="password" name="password"><br>
            <% if ("wrong".equals(input)) { %>
            <span style="color: red; ">Login or password is incorrect</span><br>
            <% } %>
            Age: <br><input type="text" name="age"><br>
            <% if (age != null && (!age.matches("\\d+"))) { %>
            <span style="color: red; ">Type only numbers!</span><br>
            <% } else if (age != null && Integer.parseInt(age) < 18) { %>
            <span style="color: red; ">Sorry, you must be under 18!</span><br>
            <% } %>
            <br><input type="submit" /><br>
            <br><i>Don't have an account?</i> <a href="/registration"><b>Register!</b></a>
        </form>
    <% } else { %>
        <h1>You are logged in as: <%= login %></h1>
        <% if (passStrength.length() > 0) { %>
        <span style="color: red; "><%= passStrength%></span><br>
        <% } %>
        <br>Click this link to <a href="/login?a=exit">logout</a>
    <% } %>
    <% session.removeAttribute("user_login"); %>
    <% session.removeAttribute("pass_strength"); %>
    <% session.removeAttribute("user_age"); %>
    <% session.removeAttribute("input"); %>
    <% session.removeAttribute("new_account"); %>
  </body>
</html>
