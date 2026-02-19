<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <c:set var="pageTitle" value="QhatuXApp — Home" />
        <%@ include file="/WEB-INF/views/jspf/head.jspf" %>
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/RESOURCES/css/home.css"
              type="text/css" />
    </head>
    <body>
        <%@ include file="/WEB-INF/views/jspf/header.jspf" %>

        <div class="container py-5">

            <!-- HERO -->
            <div class="hero-section mb-5"
                 style="background-image: url('${pageContext.request.contextPath}/RESOURCES/images/hero-events.png');">

                <div class="row align-items-center h-100">
                    <div class="col-12 col-md-7">
                        <div class="hero-content">
                            <!-- Logo -->
                            <img src="${pageContext.request.contextPath}/RESOURCES/images/qhatux-logo.png"
                                 alt="QhatuX"
                                 class="hero-logo" />

                            <!-- Pequeño pill / etiqueta -->
                            <div class="hero-pill">
                                <i class="bi bi-stars"></i>
                                Plataforma social de eventos y gamificación
                            </div>

                            <!-- Texto principal -->
                            <h1 class="display-8 fw-bold mb-3">
                                Descubre eventos, compra tus entradas y gana puntos.
                            </h1>

                            <p class="lead mb-4">
                                Conciertos, teatro y mucho más en un solo lugar. Sube de nivel mientras vives nuevas experiencias.
                            </p>

                            <!-- Botones -->
                            <div class="d-flex flex-wrap gap-2 hero-buttons">
                                <a href="${pageContext.request.contextPath}/eventos" class="btn btn-primary btn-lg">
                                    <i class="bi bi-calendar-event me-1"></i> Ver eventos
                                </a>

                                <c:choose>
                                    <c:when test="${empty sessionScope.user}">
                                        <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-dark btn-lg">
                                            Iniciar sesión
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/mi-perfil" class="btn btn-outline-light btn-lg">
                                            Mi perfil
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>



            <!-- Tarjetas mejoradas -->
            <div class="row g-4 mt-4">

                <!-- Explora eventos -->
                <div class="col-md-3">
                    <div class="feature-card h-100 shadow-sm">
                        <i class="bi bi-search feature-icon"></i>
                        <h5 class="feature-title">Explora eventos</h5>
                        <p class="feature-text">
                            Encuentra conciertos, festivales, obras de teatro, ferias y más.
                            Filtra por categoría, ciudad o fecha y descubre nuevas experiencias.
                        </p>
                        <a href="${pageContext.request.contextPath}/eventos" class="feature-link">
                            Ver más <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>

                <!-- Interacción social -->
                <div class="col-md-3">
                    <div class="feature-card h-100 shadow-sm">
                        <i class="bi bi-people feature-icon"></i>
                        <h5 class="feature-title">Conecta con otros</h5>
                        <p class="feature-text">
                            Comenta tus experiencias, comparte opiniones y descubre qué eventos
                            disfrutan otros usuarios. QhatuX es una plataforma para vivir eventos
                            de forma más social y divertida.
                        </p>
                        <a href="${pageContext.request.contextPath}/eventos" class="feature-link">
                            Ver más <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>

                <!-- Compra tus entradas -->
                <div class="col-md-3">
                    <div class="feature-card h-100 shadow-sm">
                        <i class="bi bi-credit-card feature-icon"></i>
                        <h5 class="feature-title">Compra tus entradas</h5>
                        <p class="feature-text">
                            Realiza compras simuladas de manera sencilla y segura.
                            Gestiona tu aforo, revisa tus compras recientes y accede a los detalles completos.
                        </p>
                        <a href="${pageContext.request.contextPath}/mis-compras" class="feature-link">
                            Ver más <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>

                <!-- Gana puntos -->
                <div class="col-md-3">
                    <div class="feature-card h-100 shadow-sm">
                        <i class="bi bi-stars feature-icon"></i>
                        <h5 class="feature-title">Gana puntos</h5>
                        <p class="feature-text">
                            Sube de nivel participando en eventos y dejando comentarios.
                            Acumula puntos y compite en el ranking global para convertirte en un verdadero fan.
                        </p>
                        <a href="${pageContext.request.contextPath}/mi-perfil" class="feature-link">
                            Ver más <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </div>


            <!-- Sección: Resumen Usuario -->
            <c:if test="${not empty sessionScope.user}">
                <c:set var="uHome" value="${usuarioHome}" />
                <c:set var="pHome" value="${personaHome}" />
                <c:set var="proximosHome" value="${proximosEventosHome}" />

                <div class="row g-3 mb-4 mt-4">

                    <!-- Resumen del usuario -->
                    <div class="col-md-5">
                        <div class="summary-card">

                            <div class="summary-header">
                                <div class="summary-avatar">
                                    <i class="bi bi-person"></i>
                                </div>
                                <div>
                                    <div class="summary-title">Tu resumen</div>
                                    <p class="summary-name mb-0">
                                        <c:out value="${pHome.nombres}" /> <c:out value="${pHome.apellidos}" />
                                    </p>
                                    <span class="badge bg-secondary text-uppercase mt-1">
                                        <c:out value="${uHome.rol}" />
                                    </span>
                                </div>
                            </div>

                            <div class="summary-chip">
                                <i class="bi bi-stars"></i>
                                Nivel actual: <strong>${uHome.nivel}</strong>
                            </div>

                            <p class="summary-meta mb-2">
                                <i class="bi bi-envelope me-1"></i>
                                <c:out value="${pHome.email}" />
                            </p>

                            <div class="summary-stats">
                                <div class="summary-stat">
                                    <div class="summary-stat-label">Puntos acumulados</div>
                                    <div class="summary-stat-value">${uHome.puntos} XP</div>
                                </div>
                                <div class="summary-stat">
                                    <div class="summary-stat-label">Estado</div>
                                    <div class="summary-stat-value">
                                        <i class="bi bi-circle-fill text-success me-1" style="font-size: .55rem;"></i>
                                        Activo
                                    </div>
                                </div>
                            </div>

                            <p class="summary-meta mb-3">
                                Sigue asistiendo a eventos y dejando comentarios para subir de nivel y mejorar tu posición en el ranking.
                            </p>

                            <div class="summary-actions">
                                <a href="${pageContext.request.contextPath}/mi-perfil" class="btn btn-sm btn-outline-primary">
                                    Ver mi perfil
                                </a>
                                <a href="${pageContext.request.contextPath}/mi-calendario" class="btn btn-sm btn-outline-secondary">
                                    Mi calendario
                                </a>
                            </div>
                        </div>
                    </div>

                    <!-- Próximos eventos del usuario -->
                    <div class="col-md-7">
                        <div class="upcoming-card">
                            <div class="upcoming-header">
                                <p class="upcoming-title mb-0">
                                    <i class="bi bi-calendar-week me-1"></i> Tus próximos eventos
                                </p>
                                <c:if test="${not empty proximosHome}">
                                    <span class="badge bg-light text-muted upcoming-badge">
                                        ${fn:length(proximosHome)} evento(s) próximo(s)
                                    </span>
                                </c:if>
                            </div>

                            <c:if test="${empty proximosHome}">
                                <p class="text-muted mb-0">
                                    Aún no tienes eventos próximos. Explora la sección de eventos y compra tus entradas
                                    para empezar a construir tu calendario.
                                </p>
                            </c:if>

                            <c:if test="${not empty proximosHome}">
                                <div class="upcoming-list">
                                    <c:forEach var="c" items="${proximosHome}">
                                        <a href="${pageContext.request.contextPath}/eventos/detalle?id=${c.idEvento}"
                                           class="text-decoration-none text-dark">
                                            <div class="upcoming-item">
                                                <div>
                                                    <div class="upcoming-item-title">
                                                        <c:out value="${c.nombreEvento}" />
                                                    </div>
                                                    <div class="upcoming-item-meta">
                                                        <i class="bi bi-calendar-event me-1"></i>
                                                        <fmt:formatDate value="${c.fechaEventoDate}" pattern="dd/MM/yyyy HH:mm" />
                                                        &nbsp;·&nbsp;
                                                        Entradas: ${c.cantidad}
                                                    </div>
                                                </div>
                                                <i class="bi bi-chevron-right text-muted"></i>
                                            </div>
                                        </a>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:if>


            <!-- Sección: Eventos destacados -->

            <div class="mt-5">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h2 class="h4 mb-0">
                        <i class="bi bi-lightning-charge me-1"></i> Eventos destacados
                    </h2>
                    <a href="${pageContext.request.contextPath}/eventos" class="btn btn-sm btn-outline-primary">
                        Ver todos los eventos
                    </a>
                </div>

                <c:if test="${empty destacados}">
                    <div class="alert alert-info">
                        Aún no hay eventos destacados configurados. Marca algunos eventos como destacados para que aparezcan aquí.
                    </div>
                </c:if>

                <c:if test="${not empty destacados}">
                    <div id="destacadosCarousel" class="carousel slide" data-bs-ride="carousel">
                        <!-- Indicadores -->
                        <div class="carousel-indicators">
                            <c:forEach var="e" items="${destacados}" varStatus="st">
                                <button type="button"
                                        data-bs-target="#destacadosCarousel"
                                        data-bs-slide-to="${st.index}"
                                        class="<c:if test='${st.first}'>active</c:if>"
                                        <c:if test='${st.first}'>aria-current="true"</c:if>
                                        aria-label="Slide ${st.index + 1}">
                                </button>
                            </c:forEach>
                        </div>

                        <!-- Slides -->
                        <div class="carousel-inner">
                            <c:forEach var="e" items="${destacados}" varStatus="st">
                                <div class="carousel-item <c:if test='${st.first}'>active</c:if>">

                                        <!-- Solo imagen, tamaño fijo -->
                                    <c:choose>
                                        <c:when test="${not empty e.urlFoto}">
                                            <img src="${e.urlFoto}"
                                                 class="d-block rounded-4 carousel-img-fixed"
                                                 alt="${e.nombre}">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="d-flex align-items-center justify-content-center bg-light rounded-4 carousel-img-fixed">
                                                <i class="bi bi-image fs-1 text-muted"></i>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <!-- Caption sobre la imagen (título + fecha + ciudad, sin descripción larga) -->
                                    <div class="carousel-caption d-none d-md-block text-start">
                                        <h5 class="mb-1">
                                            <c:out value="${e.nombre}" />
                                        </h5>
                                        <p class="mb-0 small">
                                            <i class="bi bi-calendar-event me-1"></i>
                                        <fmt:formatDate value="${e.fechaInicioDate}" pattern="dd/MM/yyyy HH:mm" />
                                        ·
                                        <i class="bi bi-geo-alt me-1"></i>
                                        <c:out value="${e.ciudad}" />
                                        </p>
                                    </div>

                                </div>
                            </c:forEach>
                        </div>


                        <!-- Controles -->
                        <button class="carousel-control-prev" type="button"
                                data-bs-target="#destacadosCarousel" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Anterior</span>
                        </button>
                        <button class="carousel-control-next" type="button"
                                data-bs-target="#destacadosCarousel" data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Siguiente</span>
                        </button>
                    </div>
                </c:if>
            </div>


            <!-- Sección: Explorar por categoría -->
            <div class="mt-5">
                <h2 class="h4 mb-3">
                    <i class="bi bi-grid-3x3-gap me-1"></i> Explorar por categoría
                </h2>

                <c:if test="${empty categorias}">
                    <p class="text-muted">No hay categorías configuradas todavía.</p>
                </c:if>

                <c:if test="${not empty categorias}">
                    <div class="d-flex flex-wrap gap-2">
                        <c:forEach var="cat" items="${categorias}">
                            <a href="${pageContext.request.contextPath}/eventos?idCategoria=${cat.idCategoria}"
                               class="btn btn-outline-secondary">
                                ${cat.nombre}
                            </a>
                        </c:forEach>
                    </div>
                </c:if>
            </div>

            <!-- Sección: Ranking -->
            <div class="ranking-card">
                <div class="ranking-header">
                    <div>
                        <p class="ranking-title mb-1">
                            <i class="bi bi-trophy me-1"></i> Top fans de QhatuX
                        </p>
                        <p class="ranking-subtitle mb-0">
                            Usuarios con más puntos acumulados por asistir y comentar eventos.
                        </p>
                    </div>
                </div>

                <c:if test="${empty topFans}">
                    <p class="text-muted mb-0">
                        Aún no hay usuarios con puntos registrados. Cuando empiecen a comprar y participar,
                        verás aquí el ranking actualizado.
                    </p>
                </c:if>

                <c:if test="${not empty topFans}">
                    <div class="ranking-list">
                        <c:forEach var="u" items="${topFans}" varStatus="st">
                            <c:set var="p" value="${personasRanking[u.idPersona]}" />

                            <c:set var="positionClass" value="" />
                            <c:if test="${st.index == 0}">
                                <c:set var="positionClass" value="ranking-medal-1" />
                            </c:if>
                            <c:if test="${st.index == 1}">
                                <c:set var="positionClass" value="ranking-medal-2" />
                            </c:if>
                            <c:if test="${st.index == 2}">
                                <c:set var="positionClass" value="ranking-medal-3" />
                            </c:if>

                            <div class="ranking-item">
                                <div class="ranking-left">
                                    <!-- Posición -->
                                    <div class="ranking-position ${positionClass}">
                                        ${st.index + 1}
                                    </div>

                                    <!-- Avatar + datos -->
                                    <div class="ranking-avatar">
                                        <i class="bi bi-person"></i>
                                    </div>

                                    <div>
                                        <div class="ranking-user-name">
                                            <c:choose>
                                                <c:when test="${not empty p}">
                                                    <c:out value="${p.nombres}" /> <c:out value="${p.apellidos}" />
                                                </c:when>
                                                <c:otherwise>
                                                    Usuario #${u.idUsuario}
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="ranking-user-email">
                                            <c:if test="${not empty p}">
                                                <c:out value="${p.email}" />
                                            </c:if>
                                        </div>
                                    </div>
                                </div>

                                <div class="ranking-right">
                                    <div class="ranking-level-badge">
                                        <i class="bi bi-stars"></i>
                                        ${u.nivel}
                                    </div>
                                    <div class="ranking-xp">
                                        <strong>${u.puntos}</strong> XP
                                    </div>
                                </div>
                            </div>

                        </c:forEach>
                    </div>
                </c:if>
            </div>


        </div>

        <!-- Bootstrap JS (necesario para el navbar colapsable) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <%@ include file="/WEB-INF/views/jspf/footer.jspf" %>
    </body>
</html>
