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
    
    <title>Удаление производителя</title>
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
                    <div class="card-header bg-danger text-white">
                        <h4>Удаление производителя</h4>
                    </div>
                    <div class="card-body">
                        <div class="alert alert-warning">
                            <i class="fas fa-exclamation-triangle"></i>
                            Вы уверены, что хотите удалить этого производителя?
                        </div>
                        
                        <form method="POST" action="${pageContext.request.contextPath}/deletemanufacturer">
                            <input type="hidden" name="id" value="${manufacturerDelete[0].getId()}" />
                            
                            <div class="form-group">
                                <label for="id_display">Код производителя:</label>
                                <input type="text" class="form-control" id="id_display" 
                                       value="${manufacturerDelete[0].getId()}" readonly />
                            </div>
                            
                            <div class="form-group">
                                <label for="name">Название компании:</label>
                                <input type="text" class="form-control" id="name" 
                                       value="${manufacturerDelete[0].getName()}" readonly />
                            </div>
                            
                            <div class="form-group">
                                <label for="country">Страна:</label>
                                <input type="text" class="form-control" id="country" 
                                       value="${manufacturerDelete[0].getCountry()}" readonly />
                            </div>
                            
                            <div class="form-group">
                                <label for="contactPerson">Контактное лицо:</label>
                                <input type="text" class="form-control" id="contactPerson" 
                                       value="${manufacturerDelete[0].getContactPerson()}" readonly />
                            </div>
                            
                            <div class="form-group">
                                <label for="phone">Телефон:</label>
                                <input type="text" class="form-control" id="phone" 
                                       value="${manufacturerDelete[0].getPhone()}" readonly />
                            </div>
                            
                            <button type="submit" class="btn btn-danger">
                                <i class="fas fa-trash-alt"></i> Подтвердить удаление
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