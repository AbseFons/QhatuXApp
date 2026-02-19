<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <c:set var="pageTitle" value="QhatuXApp — Mis compras" />
        <%@ include file="/WEB-INF/views/jspf/head.jspf" %>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/RESOURCES/css/mis-compras.css"
              type="text/css"/>
    </head>
    <body>
        <%@ include file="/WEB-INF/views/jspf/header.jspf" %>

        <div class="container purchases-page">

            <!-- Header -->
            <div class="purchases-header">
                <div>
                    <h1 class="purchases-title">Mis compras</h1>
                    <p class="purchases-subtitle mb-0">
                        Revisa tus entradas adquiridas, fechas de eventos y estados de pago.
                    </p>
                </div>
                <a href="${pageContext.request.contextPath}/eventos" class="btn btn-outline-primary btn-sm">
                    <i class="bi bi-arrow-left"></i> Ver eventos
                </a>
            </div>

            <!-- Sin compras -->
            <c:if test="${empty compras}">
                <div class="purchases-empty">
                    <i class="bi bi-ticket-perforated fs-3 mb-2 d-block"></i>
                    <p class="mb-1">Aún no has realizado compras.</p>
                    <p class="mb-0">
                        Explora los eventos disponibles y adquiere tus primeras entradas para empezar a ganar puntos.
                    </p>
                </div>
            </c:if>

            <!-- Lista de compras -->
            <c:if test="${not empty compras}">
                <div class="purchases-list">
                    <c:forEach var="c" items="${compras}">

                        <div class="purchase-card">
                            <!-- Header: evento + estado + código -->
                            <div class="purchase-header">
                                <div>
                                    <div class="purchase-event-name">
                                        <a href="${pageContext.request.contextPath}/eventos/detalle?id=${c.idEvento}"
                                           class="text-decoration-none text-dark">
                                            <c:out value="${c.nombreEvento}" />
                                        </a>
                                    </div>

                                    <div class="purchase-status mt-1">
                                        <c:choose>
                                            <c:when test="${c.estado == 'PAGADO'}">
                                                <span class="badge bg-success">Pagado</span>
                                            </c:when>
                                            <c:when test="${c.estado == 'CANCELADO'}">
                                                <span class="badge bg-secondary">Cancelado</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-warning text-dark">
                                                    ${c.estado}
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>

                                <div class="text-end">
                                    <div class="purchase-ticket-code">
                                        Código de ticket
                                    </div>
                                    <div class="fw-semibold">
                                        ${c.codigoTicket}
                                    </div>
                                </div>
                            </div>

                            <!-- Metadatos: fechas -->
                            <div class="purchase-meta-row">
                                <div class="purchase-meta-item">
                                    <i class="bi bi-calendar-event"></i>
                                    <strong>Fecha del evento:</strong>
                                    <c:choose>
                                        <c:when test="${not empty c.fechaEventoDate}">
                                            <fmt:formatDate value="${c.fechaEventoDate}" pattern="dd/MM/yyyy HH:mm" />
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted">Sin fecha</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="purchase-meta-item">
                                    <i class="bi bi-receipt"></i>
                                    <strong>Fecha de compra:</strong>
                                    <c:choose>
                                        <c:when test="${not empty c.fechaCompraDate}">
                                            <fmt:formatDate value="${c.fechaCompraDate}" pattern="dd/MM/yyyy HH:mm" />
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted">No disponible</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <!-- Footer: cantidades, montos y acción -->
                            <div class="purchase-footer">
                                <div class="purchase-amounts">
                                    <div>
                                        <strong>Cantidad:</strong> ${c.cantidad}
                                    </div>
                                    <div>
                                        <strong>P. unitario:</strong>
                                        S/ <fmt:formatNumber value="${c.precioUnitario}" minFractionDigits="2" />
                                    </div>
                                </div>

                                <div class="d-flex align-items-center gap-3">
                                    <div class="purchase-total">
                                        Total:&nbsp;
                                        S/ <fmt:formatNumber value="${c.precioTotal}" minFractionDigits="2" />
                                    </div>
                                    <div class="purchase-actions">
                                        <a href="${pageContext.request.contextPath}/eventos/detalle?id=${c.idEvento}"
                                           class="btn btn-outline-primary btn-sm">
                                            Ver evento
                                        </a>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </c:forEach>
                </div>
            </c:if>

        </div>

        <%@ include file="/WEB-INF/views/jspf/footer.jspf" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
