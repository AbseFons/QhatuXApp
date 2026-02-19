<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <c:set var="pageTitle" value="QhatuXApp — Evento" />
        <%@ include file="/WEB-INF/views/jspf/head.jspf" %>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/RESOURCES/css/evento-detalle.css"
              type="text/css"/>
    </head>
    <body>
        <%@ include file="/WEB-INF/views/jspf/header.jspf" %>

        <c:choose>
            <c:when test="${empty evento}">
                <div class="container mt-4">
                    <div class="alert alert-danger event-alert">Evento no encontrado.</div>
                </div>
            </c:when>

            <c:otherwise>
                <div class="container event-detail-page">

                    <!-- HERO del evento -->
                    <div class="event-hero">
                        <div class="event-hero-img-wrapper">
                            <img class="event-hero-img"
                                 src="${empty evento.urlFoto ? 'https://via.placeholder.com/1200x500?text=Evento' : evento.urlFoto}"
                                 alt="${evento.nombre}">
                            <div class="event-hero-overlay"></div>

                            <div class="event-hero-content">
                                <div class="event-meta-chip mb-2">
                                    <i class="bi bi-calendar-event me-1"></i>
                                    <fmt:formatDate value="${evento.fechaInicioDate}" pattern="dd/MM/yyyy HH:mm" />
                                    <c:if test="${not empty evento.fechaFinDate}">
                                        &nbsp;–&nbsp;
                                        <fmt:formatDate value="${evento.fechaFinDate}" pattern="dd/MM/yyyy HH:mm" />
                                    </c:if>
                                </div>

                                <h1 class="event-hero-title">
                                    <c:out value="${evento.nombre}" />
                                </h1>
                                <div class="event-hero-meta">
                                    <i class="bi bi-geo-alt me-1"></i>
                                    <c:out value="${evento.direccion}" />,
                                    <c:out value="${evento.ciudad}" />,
                                    <c:out value="${evento.pais}" />
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Contenido principal -->
                    <div class="row g-3">
                        <!-- Columna izquierda: descripción y detalles -->
                        <div class="col-lg-7">
                            <div class="event-info-card mb-3">
                                <h2 class="event-info-section-title">
                                    <i class="bi bi-info-circle me-1"></i> Descripción del evento
                                </h2>
                                <p class="event-info-text mb-0">
                                    <c:out value="${evento.descripcion}" />
                                </p>
                            </div>

                            <div class="event-info-card">
                                <h2 class="event-info-section-title">
                                    <i class="bi bi-geo-alt me-1"></i> Detalles
                                </h2>

                                <div class="event-info-text">
                                    <p class="mb-2">
                                        <strong>Fecha y hora:</strong><br/>
                                        <fmt:formatDate value="${evento.fechaInicioDate}" pattern="EEEE d 'de' MMMM 'a las' HH:mm" />
                                        <c:if test="${not empty evento.fechaFinDate}">
                                            <br/>
                                            <small class="text-muted">
                                                Termina:
                                                <fmt:formatDate value="${evento.fechaFinDate}" pattern="EEEE d 'de' MMMM 'a las' HH:mm" />
                                            </small>
                                        </c:if>
                                    </p>

                                    <p class="mb-2">
                                        <strong>Ubicación:</strong><br/>
                                        <c:out value="${evento.direccion}" /><br/>
                                        <c:out value="${evento.ciudad}" />, <c:out value="${evento.pais}" />
                                    </p>

                                    <p class="mb-0">
                                        <strong>Precio:</strong>
                                        S/ <fmt:formatNumber value="${evento.precio}" minFractionDigits="2"/>
                                    </p>
                                </div>
                            </div>
                        </div>

                        <!-- Columna derecha: compra + comentarios -->
                        <div class="col-lg-5">
                            <!-- Comprar entradas -->
                            <div class="event-buy-card">
                                <h2 class="h6 mb-3">
                                    <i class="bi bi-ticket-perforated me-1"></i> Comprar entradas
                                </h2>

                                <p class="event-buy-price mb-1">
                                    S/ <fmt:formatNumber value="${evento.precio}" minFractionDigits="2"/>
                                </p>
                                <p class="event-buy-meta mb-3">
                                    Compra simulada para el proyecto. No se realizan cobros reales.
                                </p>

                                <c:if test="${not empty errorCompra}">
                                    <div class="alert alert-danger event-alert mb-3">
                                        ${errorCompra}
                                    </div>
                                </c:if>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/eventos/${evento.idEvento}/comprar">
                                    <div class="mb-3">
                                        <label class="form-label">Cantidad de entradas</label>
                                        <div class="input-group">
                                            <input type="number" class="form-control" name="cantidad"
                                                   min="1" value="1" required>
                                            <button class="btn btn-primary">
                                                Comprar
                                            </button>
                                        </div>
                                    </div>
                                </form>

                                <c:if test="${sessionScope.user == null}">
                                    <div class="alert alert-warning event-alert mb-0 mt-3">
                                        Inicia sesión para completar la compra y ganar puntos.
                                    </div>
                                </c:if>

                                <c:if test="${sessionScope.user != null && sessionScope.user.rol eq 'admin'}">
                                    <div class="mt-3 d-flex gap-2">
                                        <a class="btn btn-outline-primary btn-sm"
                                           href="${pageContext.request.contextPath}/eventos/editar?id=${evento.idEvento}">
                                            Editar evento
                                        </a>
                                        <form method="post"
                                              action="${pageContext.request.contextPath}/eventos/eliminar"
                                              onsubmit="return confirm('¿Eliminar evento?');">
                                            <input type="hidden" name="id" value="${evento.idEvento}">
                                            <button class="btn btn-outline-danger btn-sm">Eliminar</button>
                                        </form>
                                    </div>
                                </c:if>
                            </div>

                            <!-- Comentarios -->
                            <div class="comments-card">
                                <div class="d-flex justify-content-between align-items-center mb-2">
                                    <h2 class="h6 mb-0">
                                        <i class="bi bi-chat-dots me-1"></i> Comentarios
                                    </h2>
                                </div>

                                <c:if test="${empty comentarios}">
                                    <div class="alert alert-info event-alert mb-3">
                                        Aún no hay comentarios. Sé el primero en opinar sobre este evento.
                                    </div>
                                </c:if>

                                <c:if test="${not empty comentarios}">
                                    <div class="mb-3">
                                        <c:forEach var="cmt" items="${comentarios}">
                                            <div class="comment-item">
                                                <div class="comment-header">
                                                    <span class="comment-author">
                                                        <c:out value="${cmt.autorNombre}" />
                                                    </span>
                                                    <span class="comment-date">
                                                        <c:out value="${cmt.fecha}" />
                                                    </span>
                                                </div>
                                                <div class="comment-text">
                                                    <c:out value="${cmt.texto}" />
                                                </div>

                                                <c:if test="${sessionScope.user != null}">
                                                    <c:if test="${sessionScope.user.rol eq 'admin' || sessionScope.user.idUsuario == cmt.idUsuario}">
                                                        <form class="comment-delete-btn"
                                                              method="post"
                                                              action="${pageContext.request.contextPath}/eventos/comentario/eliminar"
                                                              onsubmit="return confirm('¿Eliminar comentario?');">
                                                            <input type="hidden" name="idComentario" value="${cmt.idComentario}">
                                                            <input type="hidden" name="idEvento" value="${evento.idEvento}">
                                                            <button class="btn btn-sm btn-outline-danger">
                                                                Eliminar
                                                            </button>
                                                        </form>
                                                    </c:if>
                                                </c:if>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:if>

                                <c:if test="${sessionScope.user != null}">
                                    <form method="post" action="${pageContext.request.contextPath}/eventos/comentar">
                                        <input type="hidden" name="idEvento" value="${evento.idEvento}">
                                        <div class="mb-2">
                                            <label class="comment-form-label">Nuevo comentario</label>
                                            <textarea class="form-control comment-textarea"
                                                      name="texto"
                                                      rows="3"
                                                      maxlength="800"
                                                      required></textarea>
                                        </div>
                                        <button class="btn btn-primary btn-sm">
                                            Publicar
                                        </button>
                                    </form>
                                </c:if>

                                <c:if test="${sessionScope.user == null}">
                                    <div class="alert alert-warning event-alert mb-0">
                                        Inicia sesión para comentar y ganar puntos por tu participación.
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>

                </div>
            </c:otherwise>
        </c:choose>

        <%@ include file="/WEB-INF/views/jspf/footer.jspf" %>
    </body>
</html>
