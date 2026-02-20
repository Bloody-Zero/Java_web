<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="domain.Product"%>
<%@ page import="domain.Manufacturer"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Font Awesome для иконок -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
    <!-- Ваши собственные стили -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    
    <title>Товары</title>
</head>
<body>
    <jsp:include page="/jspf/header.jsp" />
    
    <div class="container-fluid mt-3">
        <div class="row">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4>Список товаров</h4>
                    </div>
                    <div class="card-body">
                        <table class="table table-sm table-striped table-hover">
                            <thead class="thead-dark">
                                <tr>
                                    <th>Код</th>
                                    <th>Наименование</th>
                                    <th>Размер</th>
                                    <th>Вес (кг)</th>
                                    <th>Производитель</th>
                                    <th>Страна</th>
                                    <th>Действия</th>
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
                                        <td>
                                            <a href="${pageContext.request.contextPath}/editproduct?id=${product.getId()}" 
                                               class="btn btn-sm btn-outline-primary" title="Редактировать">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/deleteproduct?id=${product.getId()}" 
                                               class="btn btn-sm btn-outline-danger" title="Удалить"
                                               onclick="return confirm('Вы уверены, что хотите удалить товар ${product.getName()}?')">
                                                <i class="fas fa-trash-alt"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h4>Добавить товар</h4>
                    </div>
                    <div class="card-body">
                        <form method="POST" action="${pageContext.request.contextPath}/products">
                            <div class="form-group">
                                <label for="name">Наименование:</label>
                                <input type="text" class="form-control" name="name" id="name" required />
                            </div>
                            <div class="form-group">
                                <label for="size">Размер:</label>
                                <input type="text" class="form-control" name="size" id="size" required />
                            </div>
                            <div class="form-group">
                                <label for="weight">Вес (кг):</label>
                                <input type="number" step="0.01" class="form-control" name="weight" id="weight" required />
                            </div>
                            <div class="form-group">
                                <label for="manufacturerId">Производитель:</label>
                                <select class="form-control" name="manufacturerId" id="manufacturerId" required>
                                    <option disabled selected>Выберите производителя</option>
                                    <c:forEach var="man" items="${manufacturers}">
                                        <option value="${man.getId()}">
                                            ${man.getName()} (${man.getCountry()})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-success">
                                <i class="fas fa-plus-circle"></i> Добавить
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/jspf/footer.jsp" />
    
    <!-- Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
