<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <c:set var="pageTitle" value="QhatuXApp — Eventos" />
        <%@ include file="/WEB-INF/views/jspf/head.jspf" %>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/RESOURCES/css/eventos.css"
              type="text/css"/>
    </head>
    <body>
        <%@ include file="/WEB-INF/views/jspf/header.jspf" %>

        <div class="container events-page">

            <!-- Header -->
            <div class="events-header">
                <div>
                    <h1 class="events-header-title">Explora eventos</h1>
                    <p class="events-header-subtitle mb-0">
                        Busca por nombre, filtra por categoría y encuentra tu próxima experiencia.
                    </p>
                </div>

                <c:if test="${sessionScope.user != null && sessionScope.user.rol eq 'admin'}">
                    <a class="btn btn-primary"
                       href="${pageContext.request.contextPath}/eventos/nuevo">
                        <i class="bi bi-plus-lg me-1"></i> Nuevo evento
                    </a>
                </c:if>
            </div>

            <!-- Filtros / búsqueda -->
            <div class="events-filters-card">
                <div class="row g-3 align-items-end">
                    <!-- Buscador -->
                    <div class="col-md-8">
                        <form class="d-flex" method="get" action="${pageContext.request.contextPath}/eventos">
                            <c:if test="${not empty idCategoriaSeleccionada}">
                                <input type="hidden" name="idCategoria" value="${idCategoriaSeleccionada}" />
                            </c:if>

                            <div class="input-group">
                                <input class="form-control"
                                       type="search"
                                       name="q"
                                       value="${q}"
                                       placeholder="Buscar eventos por nombre, ciudad..."
                                       aria-label="Buscar">
                                <button class="btn btn-primary" type="submit">
                                    <i class="bi bi-search"></i>
                                </button>

                                <c:if test="${not empty q}">
                                    <a href="${pageContext.request.contextPath}/eventos?idCategoria=${idCategoriaSeleccionada}"
                                       class="btn btn-outline-secondary">
                                        Limpiar
                                    </a>
                                </c:if>
                            </div>
                        </form>
                    </div>

                    <!-- Filtro por categoría -->
                    <div class="col-md-4">
                        <form class="d-flex" method="get" action="${pageContext.request.contextPath}/eventos">
                            <c:if test="${not empty q}">
                                <input type="hidden" name="q" value="${q}" />
                            </c:if>

                            <div class="input-group">
                                <label class="input-group-text" for="fCategoria">Categoría</label>
                                <select class="form-select" id="fCategoria" name="idCategoria" onchange="this.form.submit()">
                                    <option value="">Todas</option>
                                    <c:forEach var="cat" items="${categorias}">
                                        <option value="${cat.idCategoria}"
                                                ${idCategoriaSeleccionada != null && idCategoriaSeleccionada == cat.idCategoria ? 'selected' : ''}>
                                            ${cat.nombre}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Lista de eventos -->
            <c:if test="${empty eventos}">
                <div class="events-empty">
                    <i class="bi bi-calendar-x fs-3 mb-2 d-block"></i>
                    <p class="mb-1">No se encontraron eventos con los filtros actuales.</p>
                    <p class="mb-0">
                        Prueba quitando algunos filtros o revisa más tarde para nuevos eventos.
                    </p>
                </div>
            </c:if>

            <c:if test="${not empty eventos}">
                <div class="row row-cols-1 row-cols-md-3 g-4">
                    <c:forEach var="e" items="${eventos}">
                        <div class="col">
                            <div class="event-card h-100">
                                <div class="event-card-img-wrapper">
                                    <img class="event-card-img"
                                         src="${empty e.urlFoto ? 'https://via.placeholder.com/600x300?text=Evento' : e.urlFoto}"
                                         alt="${e.nombre}">

                                    <!-- Badge opcional de fecha -->
                                    <span class="event-badge-date">
                                        <fmt:formatDate value="${e.fechaInicioDate}" pattern="dd MMM" />
                                    </span>
                                </div>

                                <div class="event-card-body">
                                    <h5 class="event-card-title">
                                        <c:out value="${e.nombre}" />
                                    </h5>

                                    <div class="event-card-meta">
                                        <i class="bi bi-calendar-event me-1"></i>
                                        <fmt:formatDate value="${e.fechaInicioDate}" pattern="dd/MM/yyyy HH:mm" />
                                        <br/>
                                        <i class="bi bi-geo-alt me-1"></i>
                                        <c:out value="${e.ciudad}" />, <c:out value="${e.pais}" />
                                    </div>

                                    <div class="event-card-footer">
                                        <div class="event-card-price">
                                            S/ <fmt:formatNumber value="${e.precio}" minFractionDigits="2" />
                                        </div>
                                        <a class="btn btn-outline-primary btn-sm"
                                           href="${pageContext.request.contextPath}/eventos/detalle?id=${e.idEvento}">
                                            Ver detalle
                                        </a>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <!-- Paginación -->
            <c:set var="totalPages" value="${(total + size - 1) / size}" />
            <c:if test="${totalPages > 1}">
                <nav class="events-pagination">
                    <ul class="pagination justify-content-center pagination-sm">
                        <c:forEach begin="1" end="${totalPages}" var="p">
                            <li class="page-item ${p==page?'active':''}">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/eventos?page=${p}&size=${size}&q=${q}&idCategoria=${idCategoriaSeleccionada}">
                                    ${p}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>

        </div>

        <%@ include file="/WEB-INF/views/jspf/footer.jspf" %>
    </body>
</html>
