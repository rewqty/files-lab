<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>FileViewer</title>
</head>
<body>
    <div>
        <form action="auth?action=logout" method="POST">
            <input class="button" type="submit" value="Выйти" name="logout"/>
        </form>
    </div>
    <div>
        ${date}
    </div>

    <h1>${folderPath}</h1>
    <c:if test="${isParentFolderPathVisible == true}">
        <p><a href="${URL}${parentFolderPath}">Вверх</a></p>
    </c:if>
    <table>
        <tr><th>Файл</th><th>Размер</th><th>Дата изменения</th></tr>
        <c:forEach var="file" items="${files}">
            <tr>
                <td><a href="${URL}<c:out value="${file.getPath()}" />"><c:out value="${file.getName()}" /></a></td>
                <td><c:out value="${file.getSize() == -1 ? '' : file.getSize().toString() += ' B'}" /></td>
                <td><fmt:formatDate pattern = "dd-MM-yyyy HH:mm:ss" value = "${file.getDateLastModified()}" /></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>