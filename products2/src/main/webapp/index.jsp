<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <meta charset="UTF-8">
    <title>Система управления товарами</title>
</head>
<body>
    <jsp:include page="jspf/header.jsp" />
    
    <div id="main">
        <h2>Функции системы</h2>
        <ul>
            <li><a href="products">Управление товарами</a></li>
            <li><a href="manufacturers">Управление производителями</a></li>
        </ul>
    </div>
    
    <jsp:include page="jspf/footer.jsp" />
</body>
</html>