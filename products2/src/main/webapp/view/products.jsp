<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="domain.Product"%>
<%@ page import="domain.Manufacturer"%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Товары</title>
</head>
<body>
    <jsp:include page="/jspf/header.jsp" />
    
    <div id="main">
        <aside class="leftAside">
            <h3>Список товаров</h3>
            <table>
                <thead>
                    <tr>
                        <th>Код</th>
                        <th>Наименование</th>
                        <th>Размер</th>
                        <th>Вес (кг)</th>
                        <th>Производитель</th>
                        <th>Страна</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td>${product.getId()}</td>
                            <td>${product.getName()}</td>
                            <td>${product.getSize()}</td>
                            <td>${product.getWeight()}</td>
                            <td>${product.getManufacturer().getName()}</td>
                            <td>${product.getManufacturer().getCountry()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </aside>
        
        <section>
            <article>
                <h3>Добавление нового товара</h3>
                <div class="text-article">
                    <form method="POST" action="${pageContext.request.contextPath}/products">
                        <p>
                            <label for="name">Наименование:</label>
                            <input type="text" name="name" id="name" required />
                        </p>
                        <p>
                            <label for="size">Размер:</label>
                            <input type="text" name="size" id="size" required />
                        </p>
                        <p>
                            <label for="weight">Вес (кг):</label>
                            <input type="number" step="0.01" name="weight" id="weight" required />
                        </p>
                        <p>
                            <label for="manufacturerId">Производитель:</label>
                            <select name="manufacturerId" id="manufacturerId" required>
                                <option disabled selected>Выберите производителя</option>
                                <c:forEach var="man" items="${manufacturers}">
                                    <option value="${man.getId()}">
                                        ${man.getName()} (${man.getCountry()})
                                    </option>
                                </c:forEach>
                            </select>
                        </p>
                        <p>
                            <button type="submit">Добавить товар</button>
                        </p>
                    </form>
                </div>
            </article>
        </section>
    </div>
    
    <jsp:include page="/jspf/footer.jsp" />
</body>
</html>