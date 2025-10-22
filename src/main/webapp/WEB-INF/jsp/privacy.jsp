<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>Политика конфиденциальности - NewsPortal</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
        body {
            background-color: #f8f9fa;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .privacy-container {
            max-width: 800px;
            margin: 60px auto 80px auto;
            padding: 30px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgb(0 0 0 / 0.1);
        }
        footer {
            background-color: #f1f1f1;
            padding: 15px 0;
            text-align: center;
            margin-top: auto;
        }
        h1, h2, h3 {
            margin-top: 1.5rem;
        }
    </style>
</head>
<body>

<header class="header-top py-2" style="background-color:#f1f1f1;">
    <div class="container d-flex justify-content-between align-items-center">
        <a href="Controller?command=page_main" class="text-dark text-decoration-none fs-4 fw-bold">NewsPortal</a>
        <a href="Controller?command=page_main" class="btn btn-outline-primary btn-sm">На главную</a>
    </div>
</header>

<div class="privacy-container">
    <h1>Политика конфиденциальности</h1>
    <p>Дата вступления в силу: 27 сентября 2025 г.</p>

    <p>Добро пожаловать на NewsPortal! Мы ценим (не очень сильно) вашу конфиденциальность и стремимся защищать(и продавать) ваши персональные данные. В данной Политике конфиденциальности объясняется, какие данные мы собираем, как мы их используем и какие у вас есть права.</p>

    <h2>1. Сбор информации</h2>
    <p>Мы можем собирать следующие данные (да вообще любые какие захотим):</p>
    <ul>
        <li>Персональные данные, такие как имя, email, и пароль, при регистрации.</li>
        <li>Информацию о вашем использовании сайта (например, просмотры страниц, IP-адрес).</li>
        <li>Файлы cookie и аналогичные технологии для улучшения работы сайта.</li>
    </ul>

    <h2>2. Использование информации</h2>
    <p>Мы используем собранную информацию для(чего угодно):</p>
    <ul>
        <li>Обеспечения работы и улучшения функционала сайта.</li>
        <li>Обслуживания вашего аккаунта и предоставления поддержки.</li>
        <li>Отправки уведомлений и важных сообщений.</li>
        <li>Анализа и понимания использования сайта.</li>
    </ul>

    <h2>3. Защита данных(ну украдут и ладно, чего бухтеть...)</h2>
    <p>Мы принимаем разумные технические и организационные меры для защиты ваших данных от несанкционированного доступа, изменения или уничтожения.</p>

    <h2>4. Раскрытие информации</h2>
    <p>Мы не продаём и не передаём ваши персональные данные третьим лицам, за исключением случаев, предусмотренных законом или необходимых для предоставления услуг.</p>

    <h2>5. Ваши права</h2>
    <p>Вы имеете право(или право Вас):</p>
    <ul>
        <li>Запрашивать доступ к вашим персональным данным.</li>
        <li>Исправлять или удалять ваши данные.</li>
        <li>Отозвать согласие на обработку данных в любое время.</li>
    </ul>

    <h2>6. Контакты</h2>
    <p>Если у вас есть вопросы по поводу этой политики, пожалуйста, свяжитесь с нами по электронной почте: <a href="mailto:privacy@newsportal.example">privacy@newsportal.example</a>.</p>

    <p>Спасибо, что доверяете NewsPortal!</p>
</div>

<footer>
    <div class="container">
        <small>© 2025 NewsPortal. Все права защищены.</small>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>