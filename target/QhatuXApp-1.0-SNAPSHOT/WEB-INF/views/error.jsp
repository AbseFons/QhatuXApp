<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Error</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    </head>
    <body class="p-4">
        <h3 class="text-danger">Ocurrió un error</h3>
        <c:if test="${not empty error}">
            <div class="alert alert-danger"><pre>${error}</pre></div>
                </c:if>
                <c:if test="${empty error && not empty pageContext.exception}">
    <div class="alert alert-danger"><pre>${pageContext.exception}</pre></div>
                </c:if>
  <a class="btn btn-secondary" href="${pageContext.request.contextPath}/home">Volver al inicio</a>
</body>
</html>
