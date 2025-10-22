
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<title>${news.title}</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<style>
.category-badge {
	font-size: 0.8rem;
	background-color: #e9ecef;
	padding: 3px 8px;
	border-radius: 5px;
	margin-bottom: 10px;
	display: inline-block;
}

footer {
	background-color: #f8f9fa;
	padding: 20px 0;
	margin-top: 40px;
	text-align: center;
}

.news-content {
	font-size: 1.1rem;
	line-height: 1.7;
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
		<div class="mb-3">
			<a href="Controller?command=page_main"
				class="btn btn-sm btn-outline-secondary">&larr; Назад к списку
				новостей</a>
		</div>

		<div>
			<span class="category-badge">${news.group}</span>
			<h2 class="mt-2 mb-3">${news.title}</h2>
			<p class="fw-bold">${news.brief}</p>

			<hr>

			<div class="news-content">${news.content}</div>
		</div>
		<div class="comments-section">

			<!-- Форма добавления комментария -->
			<c:if test="${not empty sessionScope.auth}">
				<form method="post" action="Controller?command=add_comment">
					<input type="hidden" name="newsId" value="${news.id}" />
					<textarea name="commentText" placeholder="Оставьте комментарий..."
						required></textarea>
					<button type="submit">Отправить</button>
				</form>
			</c:if>

			<h3>Комментарии</h3>

			<!-- Список комментариев -->
			<c:forEach var="comment" items="${comments}">
				<div class="comment-block border rounded p-3 mb-3 bg-light">
					<p class="mb-1">
						<strong>${comment.userName}</strong> <small class="text-muted">
							— ${comment.createdAt}</small>
					</p>

					<c:choose>
						<c:when test="${editingCommentId == comment.id}">
							<!-- Форма редактирования комментария -->
							<form method="post" action="Controller">
								<input type="hidden" name="command" value="update_comment" /> <input
									type="hidden" name="commentId" value="${comment.id}" />
								<textarea name="commentText" required class="form-control mb-2">${comment.text}</textarea>
								<button type="submit" class="btn btn-sm btn-success">Сохранить</button>
								<a href="Controller?command=page_news&id=${news.id}"
									class="btn btn-sm btn-secondary">Отмена</a>
							</form>
						</c:when>
						<c:otherwise>
							<p>${comment.text}</p>

							<!-- Кнопки -->
							<c:if test="${comment.editable}">
								<form method="get" action="Controller" class="d-inline">
									<input type="hidden" name="command" value="page_news" /> <input
										type="hidden" name="id" value="${news.id}" /> <input
										type="hidden" name="editingCommentId" value="${comment.id}" />
									<button type="submit" class="btn btn-sm btn-outline-primary">Редактировать</button>
								</form>
							</c:if>

							<c:if test="${sessionScope.auth.role == 'ADMINISTRATOR'}">
								<form method="post" action="Controller" class="d-inline ms-2">
									<input type="hidden" name="command" value="delete_comment" />
									<input type="hidden" name="commentId" value="${comment.id}" />
									<button type="submit" class="btn btn-sm btn-outline-danger"
										onclick="return confirm('Удалить комментарий?')">Удалить</button>
								</form>
							</c:if>
						</c:otherwise>
					</c:choose>
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