<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>ShopLite • Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg bg-white border-bottom">
    <div class="container">
        <a class="navbar-brand text-primary" href="${pageContext.request.contextPath}/">ShopLite</a>
    </div>
</nav>

<section class="container py-5" style="max-width:720px;">
    <div class="card shadow-sm">
        <div class="card-body p-4">
            <h3 class="mb-3">Iniciar sesión</h3>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/login" class="row g-3">
                <div class="mb-3">
                    <label class="form-label">Usuario</label>
                    <input name="username" value="admin" class="form-control" required/>
                </div>
                <div class="mb-3">
                    <label class="form-label" value="admin123">Contraseña</label>
                    <input name="password" type="password" class="form-control" value="admin123" required/>
                </div>
                <button class="btn btn-primary w-100">Entrar</button>
            </form>
        </div>
    </div>
</section>
</body>
</html>
