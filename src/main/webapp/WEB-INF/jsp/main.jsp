<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<title>Список Новостей</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<style>
.news-card {
	margin-bottom: 20px;
}

.category-badge {
	font-size: 0.8rem;
	background-color: #e9ecef;
	padding: 3px 8px;
	border-radius: 5px;
	margin-bottom: 10px;
	display: inline-block;
}

.card-title {
	font-size: 1.2rem;
}

footer {
	background-color: #f8f9fa;
	padding: 20px 0;
	margin-top: 40px;
	text-align: center;
}
</style>
</head>
<body>

	<!-- Header -->
	<header class="header-top">
		<div
			class="container d-flex justify-content-between align-items-center">
			<div>
				<a href="Controller?command=page_main"
					class="text-dark text-decoration-none fs-4 fw-bold">NewsPortal</a>
			</div>
			<div class="language-switcher">
				<span>Язык:</span> <a href="//Controller/SwitchLanguage?lang=ru"><button
						class="btn btn-sm btn-outline-secondary">RU</button></a> <a
					href="//Controller/SwitchLanguage?lang=en"><button
						class="btn btn-sm btn-outline-secondary">EN</button></a> <a
					href="//Controller/SwitchLanguage?lang=by"><button
						class="btn btn-sm btn-outline-secondary">BY</button></a>
			</div>
			<div>
				<c:choose>
					<c:when test="${not empty sessionScope.auth}">
						<span class="me-3">Здравствуйте, ${sessionScope.auth.name}!</span>
						<a href="Controller?command=log_out"
							class="btn btn-sm btn-outline-danger">Выйти</a>
						<c:if
							test="${not empty sessionScope.auth and sessionScope.auth.role == 'ADMINISTRATOR'}">
							<a href="Controller?command=page_create_news"
								class="btn btn-sm btn-success ms-3">Добавить новость</a>
						</c:if>
					</c:when>
					<c:otherwise>
						<a href="Controller?command=page_auth"
							class="btn btn-sm btn-primary me-2">Войти</a>
						<a href="Controller?command=page_registration"
							class="btn btn-sm btn-outline-primary">Регистрация</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</header>

	<!-- Main Content -->
	<main class="container mt-4">
		<h2 class="mb-4">Все новости</h2>

		<div class="row">
			<c:forEach var="news" items="${requestScope.topNews}">
				<div class="col-md-4 news-card">
					<div class="card h-100">
						<div class="card-body d-flex flex-column">
							<span class="category-badge">${news.group}</span>
							<h5 class="card-title" style="font-size: 135%;">${news.title}</h5>
							<p class="card-text">${news.brief}</p>
							<div class="mt-auto">
								<c:choose>
									<c:when test="${not empty sessionScope.auth}">
										<a href="Controller?command=page_news&id=${news.id}"
											class="btn btn-sm btn-outline-secondary">Читать далее</a>
									</c:when>
									<c:otherwise>
										<span class="text-muted">Авторизуйтесь для просмотра
											новости</span>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</main>

	<!-- Footer -->
	<footer>
		<div class="container">
			<p class="mb-0">
				© 2025 NewsPortal. Все права защищены. | <a
					href="Controller?command=page_privacy">Политика
					конфиденциальности</a>
			</p>
		</div>
	</footer>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>