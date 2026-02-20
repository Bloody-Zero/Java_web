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
    
    <title>Удаление товара</title>
    
    <style>
        .warning-message {
            background-color: #fff3cd;
            border: 1px solid #ffeeba;
            color: #856404;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="/jspf/header.jsp" />
    
    <div class="container-fluid mt-3">
        <!-- Отображение сообщений из сессии (после редиректа) -->
        <c:if test="${not empty sessionScope.error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-triangle"></i> ${sessionScope.error}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <c:remove var="error" scope="session" />
        </c:if>
        
        <c:if test="${not empty sessionScope.success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle"></i> ${sessionScope.success}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <c:remove var="success" scope="session" />
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
            
            <!-- Правая часть: подтверждение удаления -->
            <div class="col-md-5">
                <div class="card">
                    <div class="card-header bg-danger text-white">
                        <h4><i class="fas fa-trash-alt"></i> Удаление товара</h4>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty productDelete and productDelete.size() > 0}">
                                <div class="warning-message">
                                    <i class="fas fa-exclamation-triangle fa-2x float-left mr-3"></i>
                                    <h5>Внимание!</h5>
                                    <p>Вы собираетесь удалить товар. Это действие необратимо.</p>
                                </div>
                                
                                <form method="POST" action="${pageContext.request.contextPath}/deleteproduct">
                                    <input type="hidden" name="id" value="${productDelete[0].getId()}" />
                                    
                                    <table class="table table-bordered">
                                        <tr>
                                            <th style="width: 40%;">Код товара:</th>
                                            <td><strong>${productDelete[0].getId()}</strong></td>
                                        </tr>
                                        <tr>
                                            <th>Наименование:</th>
                                            <td>${productDelete[0].getName()}</td>
                                        </tr>
                                        <tr>
                                            <th>Размер:</th>
                                            <td>${productDelete[0].getSize()}</td>
                                        </tr>
                                        <tr>
                                            <th>Вес (кг):</th>
                                            <td>${productDelete[0].getWeight()}</td>
                                        </tr>
                                        <tr>
                                            <th>Производитель:</th>
                                            <td>${productDelete[0].getManufacturer().getName()} 
                                                (${productDelete[0].getManufacturer().getCountry()})</td>
                                        </tr>
                                    </table>
                                    
                                    <div class="form-group row mt-4">
                                        <div class="col-sm-12 text-center">
                                            <button type="submit" class="btn btn-danger btn-lg"
                                                    onclick="return confirm('Вы точно уверены? Это действие нельзя отменить!')">
                                                <i class="fas fa-trash-alt"></i> Подтвердить удаление
                                            </button>
                                            <a href="${pageContext.request.contextPath}/products" 
                                               class="btn btn-secondary btn-lg">
                                                <i class="fas fa-times"></i> Отмена
                                            </a>
                                        </div>
                                    </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-info">
                                    <i class="fas fa-info-circle"></i> Товар не найден или не выбран для удаления.
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
    
    <!-- Автоматическое скрытие алертов через 5 секунд -->
    <script>
        $(document).ready(function() {
            setTimeout(function() {
                $(".alert").fadeOut("slow");
            }, 5000);
        });
    </script>
</body>
</html>