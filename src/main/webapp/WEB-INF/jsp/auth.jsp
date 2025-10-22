<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8" />
<title>Вход в аккаунт - NewsPortal</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- Bootstrap CDN -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<style>
body {
	background-color: #f8f9fa;
	min-height: 100vh;
	display: flex;
	flex-direction: column;
}

.auth-container {
	max-width: 400px;
	margin: 80px auto;
	padding: 30px;
	background: #fff;
	border-radius: 8px;
	box-shadow: 0 0 10px rgb(0 0 0/ 0.1);
}

footer {
	background-color: #f1f1f1;
	padding: 15px 0;
	text-align: center;
	margin-top: auto;
}
</style>
</head>
<body>

	<header class="header-top py-2" style="background-color: #f1f1f1;">
		<div
			class="container d-flex justify-content-between align-items-center">
			<a href="Controller?command=page_main"
				class="text-dark text-decoration-none fs-4 fw-bold">NewsPortal</a> <a
				href="Controller?command=page_main"
				class="btn btn-outline-primary btn-sm">На главную</a>
		</div>
	</header>

	<div class="auth-container">
		<h2 class="mb-4 text-center">Вход в аккаунт</h2>

		<%-- Блок вывода сообщения об ошибке --%>
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger" role="alert">${errorMessage}</div>
		</c:if>
		<c:if test="${not empty param.message}">
			<div class="alert alert-danger" role="alert">${param.message}</div>
		</c:if>

		<form action="Controller" method="post" novalidate>
			<input type="hidden" name="command" value="do_auth" />
			<div class="mb-3">
				<label for="inputEmail" class="form-label">Email</label> <input
					type="email" class="form-control" id="inputEmail" name="email"
					required placeholder="Введите email" />
				<div class="invalid-feedback">Пожалуйста, введите корректный
					email.</div>
			</div>
			<div class="mb-3">
				<label for="inputPassword" class="form-label">Пароль</label> <input
					type="password" class="form-control" id="inputPassword"
					name="password" required placeholder="Введите пароль" />
				<div class="invalid-feedback">Пожалуйста, введите пароль.</div>
			</div>
			<button type="submit" class="btn btn-primary w-100">Войти</button>
		</form>
		<p class="mt-3 text-center">
			Нет аккаунта? <a href="Controller?command=page_registration">Зарегистрироваться</a>
		</p>
	</div>

	<footer>
		<div class="container">
			<small>© 2025 NewsPortal. Все права защищены.</small>
		</div>
	</footer>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script>
    // Простая валидация формы (Bootstrap 5)
    (() => {
        'use strict'
        const forms = document.querySelectorAll('form')
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add('was-validated')
            }, false)
        })
    })()
</script>

</body>
</html>