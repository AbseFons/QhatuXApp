<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html lang="es">
    <head>
        <c:set var="pageTitle" value="Mi perfil" />
        <%@ include file="/WEB-INF/views/jspf/head.jspf" %>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/RESOURCES/css/perfil.css"
              type="text/css"/>
    </head>
    <body>
        <%@ include file="/WEB-INF/views/jspf/header.jspf" %>

        <div class="container profile-page">

            <!-- Header -->
            <div class="profile-header">
                <div>
                    <h1 class="profile-title">Mi perfil</h1>
                    <p class="profile-subtitle mb-0">
                        Resumen de tu actividad y progreso en QhatuX.
                    </p>
                </div>
                <div>
                    <a href="${pageContext.request.contextPath}/eventos"
                       class="btn btn-outline-primary btn-sm">
                        <i class="bi bi-calendar-event"></i> Ver eventos
                    </a>
                </div>
            </div>

            <c:set var="u" value="${usuarioPerfil}" />
            <c:set var="p" value="${personaPerfil}" />

            <!-- Fila principal: datos + progreso -->
            <div class="row g-3 mb-4">
                <!-- Datos básicos -->
                <div class="col-md-6">
                    <div class="profile-card h-100">
                        <div class="profile-user-card-body">
                            <div class="profile-avatar">
                                <!-- Iniciales simples: primera letra de nombres y apellidos (texto fijo si no están) -->
                                <c:choose>
                                    <c:when test="${not empty p.nombres && not empty p.apellidos}">
                                        <c:out value="${fn:substring(p.nombres,0,1)}" />
                                        <c:out value="${fn:substring(p.apellidos,0,1)}" />
                                    </c:when>
                                    <c:otherwise>
                                        <i class="bi bi-person"></i>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div>
                                <div class="profile-user-info-name">
                                    <c:out value="${p.nombres}" /> <c:out value="${p.apellidos}" />
                                </div>
                                <div class="profile-user-info-email">
                                    <c:out value="${p.email}" />
                                </div>
                                <div class="mt-2">
                                    <span class="badge bg-secondary text-uppercase">
                                        <c:out value="${u.rol}" />
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Puntos y nivel -->
                <div class="col-md-6">
                    <div class="profile-card h-100">
                        <div class="profile-progress-card-body">
                            <h5 class="card-title mb-3">
                                <i class="bi bi-stars me-2"></i> Progreso y nivel
                            </h5>

                            <p class="mb-1">
                                <span class="text-muted">Puntos acumulados:</span>
                                <span class="fw-bold">${u.puntos} XP</span>
                            </p>
                            <p class="mb-3">
                                <span class="text-muted">Nivel actual:</span>
                                <span class="badge bg-primary profile-level-badge">
                                    ${u.nivel}
                                </span>
                            </p>

                            <!-- Cálculo de progreso y puntos restantes -->
                            <c:set var="xp" value="${u.puntos}" />
                            <c:set var="xpBase" value="0" />
                            <c:set var="xpNext" value="0" />

                            <c:choose>
                                <c:when test="${xp lt 50}">
                                    <c:set var="xpBase" value="0" />
                                    <c:set var="xpNext" value="50" />
                                </c:when>
                                <c:when test="${xp lt 150}">
                                    <c:set var="xpBase" value="50" />
                                    <c:set var="xpNext" value="150" />
                                </c:when>
                                <c:when test="${xp lt 300}">
                                    <c:set var="xpBase" value="150" />
                                    <c:set var="xpNext" value="300" />
                                </c:when>
                                <c:otherwise>
                                    <c:set var="xpBase" value="300" />
                                    <c:set var="xpNext" value="300" />
                                </c:otherwise>
                            </c:choose>

                            <c:set var="porcentaje">
                                <c:choose>
                                    <c:when test="${xpNext > xpBase}">
                                        ${ (xp - xpBase) * 100 / (xpNext - xpBase) }
                                    </c:when>
                                    <c:otherwise>100</c:otherwise>
                                </c:choose>
                            </c:set>

                            <c:set var="faltan" value="${xpNext - xp}" />

                            <div class="mb-1 d-flex justify-content-between">
                                <small class="text-muted">Progreso al siguiente nivel</small>
                                <small class="text-muted">
                                    <fmt:formatNumber value="${porcentaje}" maxFractionDigits="0" />%
                                </small>
                            </div>
                            <div class="progress" style="height: 8px;">
                                <div class="progress-bar" role="progressbar"
                                     style="width: ${porcentaje}%;"
                                     aria-valuenow="${porcentaje}" aria-valuemin="0" aria-valuemax="100">
                                </div>
                            </div>

                            <small class="text-muted d-block mt-2">
                                <c:choose>
                                    <c:when test="${xpNext > xp}">
                                        Te faltan <strong>${faltan} XP</strong> para alcanzar el siguiente nivel.
                                    </c:when>
                                    <c:otherwise>
                                        Has alcanzado el nivel máximo definido por ahora. ¡Sigue participando para mantener tu posición en el ranking!
                                    </c:otherwise>
                                </c:choose>
                            </small>
                            <small class="text-muted d-block">
                                Ganas puntos al comprar entradas y comentar en eventos.
                            </small>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Resumen de actividad + accesos rápidos -->
            <div class="row g-3">
                <!-- Eventos comprados -->
                <div class="col-md-4">
                    <div class="profile-card h-100">
                        <div class="profile-activity-card-body">
                            <div class="profile-activity-icon profile-activity-icon-primary">
                                <i class="bi bi-ticket-perforated fs-4"></i>
                            </div>
                            <div>
                                <h6 class="mb-1">Eventos comprados</h6>
                                <p class="display-6 mb-0">${totalCompras}</p>
                                <small class="text-muted">Entradas adquiridas en la plataforma.</small>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Comentarios publicados -->
                <div class="col-md-4">
                    <div class="profile-card h-100">
                        <div class="profile-activity-card-body">
                            <div class="profile-activity-icon profile-activity-icon-success">
                                <i class="bi bi-chat-dots fs-4"></i>
                            </div>
                            <div>
                                <h6 class="mb-1">Comentarios publicados</h6>
                                <p class="display-6 mb-0">${totalComentarios}</p>
                                <small class="text-muted">Opiniones compartidas en los eventos.</small>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Acciones rápidas -->
                <div class="col-md-4">
                    <div class="profile-card h-100">
                        <div class="profile-shortcuts-card-body">
                            <p class="profile-shortcuts-title mb-1">
                                <i class="bi bi-lightning-charge me-1"></i> Accesos rápidos
                            </p>
                            <p class="profile-shortcuts-subtitle">
                                Navega rápidamente a las secciones que más usas.
                            </p>

                            <div class="profile-shortcuts-grid">
                                <a href="${pageContext.request.contextPath}/mis-compras"
                                   class="btn btn-outline-primary btn-sm">
                                    <i class="bi bi-receipt me-1"></i> Mis compras
                                </a>
                                <a href="${pageContext.request.contextPath}/mi-calendario"
                                   class="btn btn-outline-secondary btn-sm">
                                    <i class="bi bi-calendar-week me-1"></i> Mi calendario
                                </a>
                                <a href="${pageContext.request.contextPath}/eventos"
                                   class="btn btn-outline-dark btn-sm">
                                    <i class="bi bi-search me-1"></i> Explorar eventos
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <%@ include file="/WEB-INF/views/jspf/footer.jspf" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
