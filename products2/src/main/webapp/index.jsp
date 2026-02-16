<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Главная страница</title>
</head>
<body>
    <jsp:include page="/jspf/header.jsp" />
    
    <div id="main">
        <h2>Функции системы</h2>
        <nav>
            <ul>
                <li><a href="${pageContext.request.contextPath}/products">Управление товарами</a></li>
                <li><a href="${pageContext.request.contextPath}/manufacturers">Управление производителями</a></li>
            </ul>
        </nav>
    </div>
    
    <jsp:include page="/jspf/footer.jsp" />
</body>
</html>