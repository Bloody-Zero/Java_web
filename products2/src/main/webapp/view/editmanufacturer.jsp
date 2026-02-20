<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
    
    <title>Редактирование производителя</title>
</head>
<body>
    <jsp:include page="/jspf/header.jsp" />
    
    <div class="container-fluid mt-3">
        <div class="row">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4>Список производителей</h4>
                    </div>
                    <div class="card-body">
                        <table class="table table-sm table-striped table-hover">
                            <thead class="thead-dark">
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
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header bg-warning">
                        <h4>Редактирование производителя</h4>
                    </div>
                    <div class="card-body">
                        <form method="POST" action="${pageContext.request.contextPath}/editmanufacturer">
                            <input type="hidden" name="id" value="${manufacturerEdit[0].getId()}" />
                            
                            <div class="form-group">
                                <label for="id_display">Код производителя:</label>
                                <input type="text" class="form-control" id="id_display" 
                                       value="${manufacturerEdit[0].getId()}" readonly />
                            </div>
                            
                            <div class="form-group">
                                <label for="name">Название компании:</label>
                                <input type="text" class="form-control" name="name" id="name" 
                                       value="${manufacturerEdit[0].getName()}" required />
                            </div>
                            
                            <div class="form-group">
                                <label for="country">Страна:</label>
                                <input type="text" class="form-control" name="country" id="country" 
                                       value="${manufacturerEdit[0].getCountry()}" required />
                            </div>
                            
                            <div class="form-group">
                                <label for="contactPerson">Контактное лицо:</label>
                                <input type="text" class="form-control" name="contactPerson" id="contactPerson" 
                                       value="${manufacturerEdit[0].getContactPerson()}" required />
                            </div>
                            
                            <div class="form-group">
                                <label for="phone">Телефон:</label>
                                <input type="text" class="form-control" name="phone" id="phone" 
                                       value="${manufacturerEdit[0].getPhone()}" required />
                            </div>
                            
                            <button type="submit" class="btn btn-warning">
                                <i class="fas fa-save"></i> Сохранить изменения
                            </button>
                            <a href="${pageContext.request.contextPath}/manufacturers" 
                               class="btn btn-secondary">
                                <i class="fas fa-times"></i> Отмена
                            </a>
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