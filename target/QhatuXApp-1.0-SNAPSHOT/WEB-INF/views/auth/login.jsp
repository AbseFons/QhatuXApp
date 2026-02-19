<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <title>QhatuX · Iniciar sesión</title>

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>

        <!-- Estilos de auth -->
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/RESOURCES/css/auth.css"
              type="text/css"/>
    </head>
    <body class="auth-bg">

        <div class="auth-wrapper">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-12 col-md-10 col-lg-9">

                        <div class="auth-card">
                            <!-- Lado visual -->
                            <div class="auth-visual d-none d-md-block flex-fill"
                                 style="background-image: url('${pageContext.request.contextPath}/RESOURCES/images/hero-auth.jpg'); background-size: cover; background-position: center;">
                                <div class="auth-visual-overlay"></div>
                                <div class="auth-visual-content">
                                    <div class="auth-visual-title">Inicia sesión</div>
                                    <div class="auth-visual-text">
                                        Accede a tus compras, revisa tus próximos eventos y sigue sumando puntos
                                        para mejorar tu posición en el ranking.
                                    </div>
                                </div>
                            </div>

                            <!-- Lado formulario -->
                            <div class="auth-form flex-fill">
                                <!-- Logo -->
                                <a href="${pageContext.request.contextPath}/home">
                                    <div class="mb-3 text-center text-md-start">
                                        <img src="${pageContext.request.contextPath}/RESOURCES/images/qhatux-logo.png"
                                             alt="QhatuX"
                                             class="auth-logo"/>
                                    </div>
                                </a>

                                <h1 class="h4 auth-title">Iniciar sesión</h1>
                                <p class="auth-subtitle">
                                    Ingresa con tu correo y contraseña para continuar.
                                </p>

                                <!-- Mensaje de error -->
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger auth-alert" role="alert">
                                        ${error}
                                    </div>
                                </c:if>

                                <form method="post" action="${pageContext.request.contextPath}/login" novalidate>
                                    <div class="mb-3">
                                        <label for="email" class="form-label">Correo electrónico</label>
                                        <input type="email"
                                               class="form-control"
                                               id="email"
                                               name="email"
                                               value="${param.email}"
                                               required/>
                                    </div>

                                    <div class="mb-2">
                                        <label for="password" class="form-label">Contraseña</label>
                                        <input type="password"
                                               class="form-control"
                                               id="password"
                                               name="password"
                                               required/>
                                    </div>

                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <small class="text-muted">

                                        </small>
                                    </div>

                                    <div class="d-grid">
                                        <button type="submit" class="btn btn-primary">
                                            Iniciar sesión
                                        </button>
                                    </div>
                                </form>

                                <hr class="my-4"/>

                                <p class="auth-footer-text text-center text-md-start mb-0">
                                    ¿Aún no tienes cuenta?
                                    <a href="${pageContext.request.contextPath}/registro">Crea una cuenta</a>
                                </p>
                            </div>

                        </div><!-- auth-card -->

                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
