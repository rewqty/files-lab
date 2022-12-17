<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Авторизуйтесь</h1>
    <form action="auth?action=login" method="POST">
        <p>Имя пользователя:</p>
        <p><input type="text" name="login"></p>
        <p>Пароль:</p>
        <p><input type="password" name="password"></p>
        <p><input type="submit" value="Авторизироваться"></p>
    </form>
    <button onclick="location.href = 'auth?action=registration';">У меня нет аккаунта</button>
</body>
</html>