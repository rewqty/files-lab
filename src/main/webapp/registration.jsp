<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h1>Регистрация</h1>
    <form action="auth?action=registration" method="POST">
        <p>Имя пользователя:</p>
        <input type="text" name="login"></p>
        <p>Электронный адрес:</p>
        <p><input type="email" name="email"></p>
        <p>Пароль:</p>
        <p><input type="password" name="password"></p>
        <p><input type="submit" value="Зарегистрироваться"></p>
    </form>
    <button onclick="location.href = 'auth?action=login';">Уже есть аккаунт</button>
</body>
</html>