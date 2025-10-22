<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8" />
<title>Регистрация - NewsPortal</title>
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

.register-container {
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

	<div class="register-container">
		<%
		String error = (String) request.getAttribute("errorMessage");
		%>
		<%
		if (error != null) {
		%>
		<div class="alert alert-danger text-center" role="alert">
			<%=error%>
		</div>
		<%
		}
		%>
		<h2 class="mb-4 text-center">Регистрация</h2>
		<form action="Controller" method="post" novalidate>
			<input type="hidden" name="command" value="do_registration" />
			<div class="mb-3">
				<label for="inputName" class="form-label">Имя</label> <input
					type="text" class="form-control" id="inputName" name="name"
					required placeholder="Введите имя" />
				<div class="invalid-feedback">Пожалуйста, введите имя.</div>
			</div>
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
			<div class="mb-3">
				<label for="inputPasswordConfirm" class="form-label">Подтвердите
					пароль</label> <input type="password" class="form-control"
					id="inputPasswordConfirm" name="passwordConfirm" required
					placeholder="Повторите пароль" />
				<div class="invalid-feedback">Пожалуйста, подтвердите пароль.
				</div>
			</div>
			<button type="submit" class="btn btn-primary w-100">Зарегистрироваться</button>
		</form>
		<p class="mt-3 text-center">
			Уже есть аккаунт? <a href="Controller?command=page_auth">Войти</a>
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
        const form = document.querySelector('form');
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            } else {
                // Проверка совпадения паролей
                const pass = document.getElementById('inputPassword').value;
                const passConfirm = document.getElementById('inputPasswordConfirm').value;
                if (pass !== passConfirm) {
                    event.preventDefault();
                    event.stopPropagation();
                    alert('Пароли не совпадают!');
                }
            }
            form.classList.add('was-validated');
        }, false);
    })()
</script>

</body>
</html>