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
    
    <title>Редактирование товара</title>
    
    <style>
        .error-message {
            color: red;
            font-weight: bold;
            margin-bottom: 15px;
        }
        .success-message {
            color: green;
            font-weight: bold;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <jsp:include page="/jspf/header.jsp" />
    
    <div class="container-fluid mt-3">
        <!-- Отображение сообщений об ошибках/успехе -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-triangle"></i> ${error}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle"></i> ${success}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        
        <div class="row">
            <!-- Левая часть: список товаров -->
            <div class="col-md-7">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4><i class="fas fa-list"></i> Список товаров</h4>
                    </div>
                    <div class="card-body" style="max-height: 500px; overflow-y: auto;">
                        <table class="table table-sm table-striped table-hover">
                            <thead class="thead-dark">
                                <tr>
                                    <th>Код</th>
                                    <th>Наименование</th>
                                    <th>Размер</th>
                                    <th>Вес (кг)</th>
                                    <th>Производитель</th>
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
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            
            <!-- Правая часть: форма редактирования -->
            <div class="col-md-5">
                <div class="card">
                    <div class="card-header bg-warning">
                        <h4><i class="fas fa-edit"></i> Редактирование товара</h4>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty productEdit and productEdit.size() > 0}">
                                <form method="POST" action="${pageContext.request.contextPath}/editproduct">
                                    <input type="hidden" name="id" value="${productEdit[0].getId()}" />
                                    
                                    <div class="form-group row">
                                        <label for="id_display" class="col-sm-4 col-form-label">Код товара:</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" id="id_display" 
                                                   value="${productEdit[0].getId()}" readonly />
                                        </div>
                                    </div>
                                    
                                    <div class="form-group row">
                                        <label for="name" class="col-sm-4 col-form-label">Наименование:</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="name" id="name" 
                                                   value="${productEdit[0].getName()}" required />
                                        </div>
                                    </div>
                                    
                                    <div class="form-group row">
                                        <label for="size" class="col-sm-4 col-form-label">Размер:</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="form-control" name="size" id="size" 
                                                   value="${productEdit[0].getSize()}" required />
                                        </div>
                                    </div>
                                    
                                    <div class="form-group row">
                                        <label for="weight" class="col-sm-4 col-form-label">Вес (кг):</label>
                                        <div class="col-sm-8">
                                            <input type="number" step="0.01" class="form-control" name="weight" id="weight" 
                                                   value="${productEdit[0].getWeight()}" required />
                                        </div>
                                    </div>
                                    
                                    <div class="form-group row">
                                        <label for="manufacturerId" class="col-sm-4 col-form-label">Производитель:</label>
                                        <div class="col-sm-8">
                                            <select class="form-control" name="manufacturerId" id="manufacturerId" required>
                                                <option value="">Выберите производителя</option>
                                                <c:forEach var="man" items="${manufacturers}">
                                                    <option value="${man.getId()}" 
                                                            <c:if test="${man.getId() == productEdit[0].getManufacturer().getId()}">selected</c:if>>
                                                        ${man.getName()} (${man.getCountry()})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group row">
                                        <div class="col-sm-12 text-center">
                                            <button type="submit" class="btn btn-warning">
                                                <i class="fas fa-save"></i> Сохранить изменения
                                            </button>
                                            <a href="${pageContext.request.contextPath}/products" 
                                               class="btn btn-secondary">
                                                <i class="fas fa-times"></i> Отмена
                                            </a>
                                        </div>
                                    </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-info">
                                    <i class="fas fa-info-circle"></i> Товар не найден или не выбран для редактирования.
                                </div>
                                <div class="text-center">
                                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">
                                        <i class="fas fa-arrow-left"></i> Вернуться к списку товаров
                                    </a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/jspf/footer.jsp" />
    
    <!-- Bootstrap JS и зависимости -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>