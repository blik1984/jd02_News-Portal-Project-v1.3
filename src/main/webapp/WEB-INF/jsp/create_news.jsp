<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<title>Создание новости</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
    <main class="container mt-4">
        <h2>Создать новую новость</h2>
        <form method="post" action="Controller?command=add_news">
            <div class="mb-3">
                <label for="title" class="form-label">Заголовок</label>
                <input type="text" id="title" name="title" class="form-control" required />
            </div>
            <div class="mb-3">
                <label for="brief" class="form-label">Краткое описание</label>
                <textarea id="brief" name="brief" class="form-control" rows="2" required></textarea>
            </div>
            <div class="mb-3">
                <label for="content" class="form-label">Текст новости</label>
                <textarea id="content" name="content" class="form-control" rows="6" required></textarea>
            </div>
            <div class="mb-3">
                <label for="group" class="form-label">Группа новостей</label>
                <select id="group" name="group" class="form-select" required>
                    <c:forEach var="group" items="${newsGroups}">
                        <option value="${group}">${group}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="publishDateTime" class="form-label">Дата и время публикации</label>
                <input type="datetime-local" id="publishDateTime" name="publishDateTime" class="form-control" required />
            </div>
            <button type="submit" class="btn btn-primary">Сохранить</button>
            <a href="Controller?command=page_main" class="btn btn-secondary ms-2">Отмена</a>
        </form>
    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
