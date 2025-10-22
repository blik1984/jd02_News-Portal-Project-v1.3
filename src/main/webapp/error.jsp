<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>Ошибка | Новостной Портал</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
        .news-card img {
            height: 180px;
            object-fit: cover;
        }

        .news-card {
            margin-bottom: 20px;
        }

        .category-section {
            margin-top: 40px;
        }

        .language-switcher button {
            margin-left: 5px;
        }

        footer {
            background-color: #f8f9fa;
            padding: 20px 0;
            margin-top: 40px;
            text-align: center;
        }

        .header-top {
            background-color: #f1f1f1;
            padding: 10px 0;
        }

        .error-container {
            margin-top: 60px;
            text-align: center;
        }

        .error-code {
            font-size: 120px;
            font-weight: bold;
            color: #dc3545;
        }

        .error-message {
            font-size: 24px;
            color: #555;
        }

        .error-description {
            margin-top: 10px;
            color: #777;
        }

        .btn-home {
            margin-top: 30px;
        }
    </style>
</head>
<body>

    <!-- Header -->
    <header class="header-top">
        <div class="container d-flex justify-content-between align-items-center">
            <div>
                <a href="Controller?command=page_index"
                   class="text-dark text-decoration-none fs-4 fw-bold">NewsPortal</a>
            </div>

            <!-- Language Switcher -->
            <div class="language-switcher">
                <span>Язык:</span>
                <a href="Controller/SwitchLanguage?lang=ru"><button class="btn btn-sm btn-outline-secondary">RU</button></a>
                <a href="Controller/SwitchLanguage?lang=en"><button class="btn btn-sm btn-outline-secondary">EN</button></a>
                <a href="Controller/SwitchLanguage?lang=by"><button class="btn btn-sm btn-outline-secondary">BY</button></a>
            </div>

            <!-- Auth Links -->
            <div>
                <a href="Controller?command=page_auth" class="btn btn-sm btn-primary me-2">Войти</a>
                <a href="Controller?command=page_registration" class="btn btn-sm btn-outline-primary">Регистрация</a>
            </div>
        </div>
    </header>

    <!-- Error Content -->
    <main class="container error-container">
        <div class="error-code">Хавайся ў бульбу</div>
        <div class="error-message">Произошла внутренняя ошибка</div>
        <div class="error-description">Попробуйте позже или вернитесь на главную страницу.</div>
        <a href="Controller?command=page_main" class="btn btn-primary btn-home">На главную</a>
    </main>

    <!-- Footer -->
    <footer>
        <div class="container">
            <p class="mb-0">
                © 2025 NewsPortal. Все права защищены. |
                <a href="Controller?command=page_privacy">Политика конфиденциальности</a>
            </p>
        </div>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>