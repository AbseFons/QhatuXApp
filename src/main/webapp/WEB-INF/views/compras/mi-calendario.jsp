<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <c:set var="pageTitle" value="QhatuXApp — Mi calendario" />
        <%@ include file="/WEB-INF/views/jspf/head.jspf" %>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/RESOURCES/css/calendario.css"
              type="text/css"/>
    </head>
    <body>
        <%@ include file="/WEB-INF/views/jspf/header.jspf" %>

        <div class="container calendar-page">

            <!-- Header -->
            <div class="calendar-header">
                <div>
                    <h1 class="calendar-title">Mi calendario de eventos</h1>
                    <p class="calendar-subtitle mb-0">
                        Revisa tus próximos eventos y los que ya asististe, en formato de agenda.
                    </p>
                </div>
                <div>
                    <a href="${pageContext.request.contextPath}/mis-compras"
                       class="btn btn-outline-primary btn-sm">
                        <i class="bi bi-receipt"></i> Ver mis compras
                    </a>
                </div>
            </div>

            <!-- Próximos eventos -->
            <div class="mb-4">
                <div class="calendar-section-card">
                    <div class="d-flex justify-content-between align-items-baseline mb-2">
                        <div>
                            <p class="calendar-section-title mb-0">
                                <i class="bi bi-calendar-event me-1"></i> Próximos eventos
                            </p>
                            <p class="calendar-section-subtitle mb-0">
                                Eventos que aún no han ocurrido, ordenados por fecha.
                            </p>
                        </div>
                    </div>

                    <c:if test="${empty comprasFuturas}">
                        <div class="calendar-empty">
                            <i class="bi bi-calendar-x fs-4 mb-2 d-block"></i>
                            <p class="mb-1">Aún no tienes eventos próximos.</p>
                            <p class="mb-0">
                                Explora nuevos eventos y adquiere tus entradas para empezar a llenar tu calendario.
                            </p>
                        </div>
                    </c:if>

                    <c:if test="${not empty comprasFuturas}">
                        <c:set var="lastFecha" value="" />
                        <c:forEach var="c" items="${comprasFuturas}">
                            <!-- clave de agrupación por día -->
                            <fmt:formatDate value="${c.fechaEventoDate}" pattern="yyyy-MM-dd" var="fechaClave" />

                            <c:if test="${fechaClave != lastFecha}">
                                <!-- Nuevo bloque de día -->
                                <c:if test="${lastFecha != ''}">
                                    <!-- pequeño espacio entre días -->
                                    <div style="height: .2rem;"></div>
                                </c:if>

                                <div class="calendar-day-block">
                                    <div class="calendar-day-header">
                                        <i class="bi bi-calendar3"></i>
                                        <fmt:formatDate value="${c.fechaEventoDate}" pattern="EEEE d 'de' MMMM" />
                                    </div>
                                </div>

                                <c:set var="lastFecha" value="${fechaClave}" />
                            </c:if>

                            <!-- Evento dentro del día -->
                            <div class="calendar-event-card">
                                <div class="calendar-event-main">
                                    <div class="calendar-event-title">
                                        <a href="${pageContext.request.contextPath}/eventos/detalle?id=${c.idEvento}"
                                           class="text-decoration-none text-dark">
                                            <c:out value="${c.nombreEvento}" />
                                        </a>
                                    </div>
                                    <div class="calendar-event-meta">
                                        <span>
                                            <i class="bi bi-clock"></i>
                                            <fmt:formatDate value="${c.fechaEventoDate}" pattern="HH:mm" />
                                        </span>
                                        <span>
                                            Entradas: ${c.cantidad}
                                        </span>
                                        <span>
                                            Código: ${c.codigoTicket}
                                        </span>
                                    </div>
                                </div>
                                <div class="calendar-event-side">
                                    <a href="${pageContext.request.contextPath}/eventos/detalle?id=${c.idEvento}"
                                       class="btn btn-outline-primary btn-sm">
                                        Ver evento
                                        <i class="bi bi-chevron-right calendar-event-chevron"></i>
                                    </a>
                                </div>
                            </div>

                        </c:forEach>
                    </c:if>
                </div>
            </div>

            <!-- Eventos pasados -->
            <div>
                <div class="calendar-section-card">
                    <div class="d-flex justify-content-between align-items-baseline mb-2">
                        <div>
                            <p class="calendar-section-title mb-0">
                                <i class="bi bi-clock-history me-1"></i> Eventos pasados
                            </p>
                            <p class="calendar-section-subtitle mb-0">
                                Historial de eventos a los que ya asististe.
                            </p>
                        </div>
                    </div>

                    <c:if test="${empty comprasPasadas}">
                        <div class="calendar-empty">
                            <i class="bi bi-archive fs-4 mb-2 d-block"></i>
                            <p class="mb-1">Todavía no tienes eventos registrados en tu historial.</p>
                            <p class="mb-0">
                                Cuando asistas a eventos, aparecerán aquí automáticamente.
                            </p>
                        </div>
                    </c:if>

                    <c:if test="${not empty comprasPasadas}">
                        <c:set var="lastFechaPasada" value="" />
                        <c:forEach var="c" items="${comprasPasadas}">
                            <!-- clave de agrupación por día -->
                            <fmt:formatDate value="${c.fechaEventoDate}" pattern="yyyy-MM-dd" var="fechaClavePasada" />

                            <c:if test="${fechaClavePasada != lastFechaPasada}">
                                <!-- Nuevo bloque de día -->
                                <c:if test="${lastFechaPasada != ''}">
                                    <div style="height: .2rem;"></div>
                                </c:if>

                                <div class="calendar-day-block">
                                    <div class="calendar-day-header">
                                        <i class="bi bi-calendar-check"></i>
                                        <fmt:formatDate value="${c.fechaEventoDate}" pattern="EEEE d 'de' MMMM" />
                                    </div>
                                </div>

                                <c:set var="lastFechaPasada" value="${fechaClavePasada}" />
                            </c:if>

                            <!-- Evento dentro del día -->
                            <div class="calendar-event-card">
                                <div class="calendar-event-main">
                                    <div class="calendar-event-title">
                                        <a href="${pageContext.request.contextPath}/eventos/detalle?id=${c.idEvento}"
                                           class="text-decoration-none text-dark">
                                            <c:out value="${c.nombreEvento}" />
                                        </a>
                                    </div>
                                    <div class="calendar-event-meta">
                                        <span>
                                            <i class="bi bi-clock-history"></i>
                                            <fmt:formatDate value="${c.fechaEventoDate}" pattern="dd/MM/yyyy HH:mm" />
                                        </span>
                                        <span>
                                            Entradas: ${c.cantidad}
                                        </span>
                                        <span>
                                            Código: ${c.codigoTicket}
                                        </span>
                                    </div>
                                </div>
                                <div class="calendar-event-side">
                                    <span class="calendar-event-badge calendar-badge-past">
                                        <i class="bi bi-check-circle"></i> Asistido
                                    </span>
                                </div>
                            </div>

                        </c:forEach>
                    </c:if>
                </div>
            </div>

        </div>

        <%@ include file="/WEB-INF/views/jspf/footer.jspf" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
