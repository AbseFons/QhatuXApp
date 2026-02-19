<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <title>QhatuX · Registro</title>

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
                            <!-- Lado visual (solo se ve en md+) -->
                            <div class="auth-visual d-none d-md-block flex-fill"
                                 style="background-image: url('${pageContext.request.contextPath}/RESOURCES/images/hero-auth.jpg'); background-size: cover; background-position: center;">
                                <div class="auth-visual-overlay"></div>
                                <div class="auth-visual-content">
                                    <div class="auth-visual-title">Bienvenido a QhatuX</div>
                                    <div class="auth-visual-text">
                                        Crea tu cuenta para descubrir nuevos eventos, ganar puntos por participar
                                        y competir en el ranking de fans.
                                    </div>
                                </div>
                            </div>

                            <!-- Lado formulario -->
                            <div class="auth-form flex-fill">
                                <!-- Logo -->
                                <div class="mb-3 text-center text-md-start">
                                    <img src="${pageContext.request.contextPath}/RESOURCES/images/qhatux-logo.png"
                                         alt="QhatuX"
                                         class="auth-logo"/>
                                </div>

                                <h1 class="h4 auth-title">Crear cuenta</h1>
                                <p class="auth-subtitle">
                                    Completa tus datos para empezar a explorar y comprar entradas.
                                </p>

                                <!-- Mensaje de error -->
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger auth-alert" role="alert">
                                        ${error}
                                    </div>
                                </c:if>

                                <form method="post" action="${pageContext.request.contextPath}/registro" novalidate>
                                    <div class="row g-3">
                                        <div class="col-md-6">
                                            <label for="nombres" class="form-label">Nombres</label>
                                            <input type="text" class="form-control" id="nombres" name="nombres"
                                                   value="${param.nombres}" required/>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="apellidos" class="form-label">Apellidos</label>
                                            <input type="text" class="form-control" id="apellidos" name="apellidos"
                                                   value="${param.apellidos}" required/>
                                        </div>

                                        <div class="col-md-4">
                                            <label for="tipoDocumento" class="form-label">Tipo de documento</label>
                                            <select class="form-select" id="tipoDocumento" name="tipoDocumento" required>
                                                <option value="" ${empty param.tipoDocumento ? "selected" : ""} disabled>Selecciona…</option>
                                                <option value="DNI" ${param.tipoDocumento == 'DNI' ? "selected" : ""}>DNI</option>
                                                <option value="CE"  ${param.tipoDocumento == 'CE'  ? "selected" : ""}>CE</option>
                                                <option value="PASAPORTE" ${param.tipoDocumento == 'PASAPORTE' ? "selected" : ""}>Pasaporte</option>
                                                <option value="OTRO" ${param.tipoDocumento == 'OTRO' ? "selected" : ""}>Otro</option>
                                            </select>
                                        </div>
                                        <div class="col-md-8">
                                            <label for="nroDocumento" class="form-label">N° documento</label>
                                            <input type="text" class="form-control" id="nroDocumento" name="nroDocumento"
                                                   value="${param.nroDocumento}" required/>
                                        </div>

                                        <div class="col-12">
                                            <label for="email" class="form-label">Correo electrónico</label>
                                            <input type="email" class="form-control" id="email" name="email"
                                                   value="${param.email}" required/>
                                        </div>

                                        <div class="col-12">
                                            <label for="password" class="form-label">Contraseña</label>
                                            <input type="password" class="form-control" id="password" name="password" required/>
                                        </div>
                                    </div>

                                    <div class="d-grid mt-4">
                                        <button type="submit" class="btn btn-primary">
                                            Crear cuenta
                                        </button>
                                    </div>
                                </form>

                                <hr class="my-4"/>

                                <p class="auth-footer-text text-center text-md-start mb-0">
                                    ¿Ya tienes cuenta?
                                    <a href="${pageContext.request.contextPath}/login">Inicia sesión</a>
                                </p>
                            </div>

                        </div><!-- auth-card -->

                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
