<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h1>Регистрация</h1>
    <form action="auth?action=registration" method="POST">
        <p>Имя пользователя:</p>
        <input type="text" name="login"></p>
        <c:if test="${isLoginAlreadyExist == true}">
            <p>Такое имя пользователя занято</p>
        </c:if>
        <p>Электронный адрес:</p>
        <p><input type="email" name="email"></p>
        <c:if test="${isEmailAlreadyExist == true}">
            <p>Такая почта уже использовалась при регистрации</p>
        </c:if>
        <p>Пароль:</p>
        <p><input type="password" name="password"></p>
        <p><input type="submit" value="Зарегистрироваться"></p>
    </form>
    <button onclick="location.href = 'auth?action=login';">Уже есть аккаунт</button>
</body>
</html>