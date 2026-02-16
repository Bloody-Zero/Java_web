<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="domain.Product" %>
<%@ page import="domain.Manufacturer" %>

<%
    // Тестовые данные для производителей (для выпадающего списка)
    Manufacturer m1 = new Manufacturer(1L, "ООО Технопром", "Россия", "Иванов И.И.", "+7 (495) 123-45-67");
    Manufacturer m2 = new Manufacturer(2L, "Samsung Electronics", "Южная Корея", "Kim D.S.", "+82 (2) 345-67-89");
    Manufacturer m3 = new Manufacturer(3L, "Apple Inc.", "США", "Tim Cook", "+1 (408) 996-10-10");
    Manufacturer m4 = new Manufacturer(4L, "ООО Мегаполис", "Россия", "Петров П.П.", "+7 (812) 234-56-78");
    
    Manufacturer[] manufacturers = new Manufacturer[]{m1, m2, m3, m4};
    pageContext.setAttribute("manufacturers", manufacturers);
    
    // Тестовые данные для товаров
    Product p1 = new Product(1L, "Ноутбук", "15.6 дюймов", 2.5, 1L, m1);
    Product p2 = new Product(2L, "Смартфон Galaxy", "6.5 дюймов", 0.2, 2L, m2);
    Product p3 = new Product(3L, "iPhone 15", "6.1 дюймов", 0.18, 3L, m3);
    Product p4 = new Product(4L, "Холодильник", "180 см", 75.0, 4L, m4);
    Product p5 = new Product(5L, "Телевизор QLED", "55 дюймов", 15.5, 2L, m2);
    
    Product[] products = new Product[]{p1, p2, p3, p4, p5};
    pageContext.setAttribute("products", products);
%>

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
                        <th scope="col">Код</th>
                        <th scope="col">Наименование</th>
                        <th scope="col">Размер</th>
                        <th scope="col">Вес (кг)</th>
                        <th scope="col">Производитель</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td>${product.getId()}</td>
                            <td>${product.getName()}</td>
                            <td>${product.getSize()}</td>
                            <td>${product.getWeight()}</td>
                            <td>${product.getManufacturerName()}</td>
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
                                <c:forEach var="manufacturer" items="${manufacturers}">
                                    <option value="${manufacturer.getId()}">
                                        ${manufacturer.getName()} (${manufacturer.getCountry()})
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