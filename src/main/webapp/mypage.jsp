<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>FileViewer</title>
</head>
<body>
    ${date}
    <h1>${folderPath}</h1>
    <p><a href="${URL}${parentFolderPath}">Вверх</a></p>
    <table>
        <tr><th>Файл</th><th>Размер</th><th>Дата</th></tr>
        <c:forEach var="file" items="${files}">
            <tr><td><a href="${URL}${file[0]}">${file[1]}</a></td><td>${file[2]}</td><td>${file[3]}</td></tr>
        </c:forEach>
    </table>
</body>
</html>