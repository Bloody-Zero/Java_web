<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="domain.Manufacturer"%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Производители</title>
</head>
<body>
    <jsp:include page="/jspf/header.jsp" />
    
    <div id="main">
        <aside class="leftAside">
            <h3>Список производителей</h3>
            <table>
                <thead>
                    <tr>
                        <th>Код</th>
                        <th>Название</th>
                        <th>Страна</th>
                        <th>Контактное лицо</th>
                        <th>Телефон</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="manufacturer" items="${manufacturers}">
                        <tr>
                            <td>${manufacturer.getId()}</td>
                            <td>${manufacturer.getName()}</td>
                            <td>${manufacturer.getCountry()}</td>
                            <td>${manufacturer.getContactPerson()}</td>
                            <td>${manufacturer.getPhone()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </aside>
        
        <section>
            <article>
                <h3>Добавление нового производителя</h3>
                <div class="text-article">
                    <form method="POST" action="${pageContext.request.contextPath}/manufacturers">
                        <p>
                            <label for="name">Название компании:</label>
                            <input type="text" name="name" id="name" required />
                        </p>
                        <p>
                            <label for="country">Страна:</label>
                            <input type="text" name="country" id="country" required />
                        </p>
                        <p>
                            <label for="contactPerson">Контактное лицо:</label>
                            <input type="text" name="contactPerson" id="contactPerson" required />
                        </p>
                        <p>
                            <label for="phone">Телефон:</label>
                            <input type="text" name="phone" id="phone" required />
                        </p>
                        <p>
                            <button type="submit">Добавить производителя</button>
                        </p>
                    </form>
                </div>
            </article>
        </section>
    </div>
    
    <jsp:include page="/jspf/footer.jsp" />
</body>
</html>