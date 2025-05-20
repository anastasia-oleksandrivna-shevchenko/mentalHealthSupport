@param String title
@param String description = "Форум ментальної підтримки"
@param boolean showHero = true
@param gg.jte.Content content
@param boolean isLoggedIn
@param org.springframework.security.web.csrf.CsrfToken _csrf

<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title} - Підтримка</title>
    <meta name="description" content="${description}">
    <style>
        /* Основні стилі */
        :root {
            --primary: #4a6fa5;
            --secondary: #6b8cbc;
            --light: #f5f8fc;
            --dark: #2c3e50;
            --success: #4caf50;
            --info: #2196f3;
            --warning: #ff9800;
            --danger: #f44336;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Roboto', 'Arial', sans-serif;
        }

        body {
            background-color: #f0f4f8;
            color: #333;
            line-height: 1.6;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 1rem;
        }

        header {
            background-color: var(--primary);
            color: white;
            padding: 1rem 0;
        }

        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 1.8rem;
            font-weight: bold;
        }

        nav ul {
            display: flex;
            list-style: none;
        }

        nav ul li {
            margin-left: 1.5rem;
        }

        nav ul li a {
            color: white;
            text-decoration: none;
        }

        .hero {
            background-color: var(--secondary);
            color: white;
            text-align: center;
            padding: 3rem 0;
        }

        .main-content {
            padding: 2rem 0;
        }

        footer {
            background-color: var(--dark);
            color: white;
            padding: 2rem 0;
            margin-top: 2rem;
        }

        .btn {
            display: inline-block;
            background-color: var(--dark);
            color: white;
            padding: 0.75rem 1.5rem;
            text-decoration: none;
            border-radius: 4px;
        }


    </style>
</head>
<body>
<header>
    <div class="container">
        <div class="header-content">
            <div class="logo">MentalHealthSupport</div>
            <nav>
                <ul>
                    <li><a href="/">Головна</a></li>
                    <li><a href="/categories">Категорії</a></li>
                    <li><a href="/diary">Особистий щоденник</a></li>
                    <li><a href="/about">Про нас</a></li>
                    <li><a href="/profile">Мій профіль</a></li>
                    @if(isLoggedIn)
                        <form id="logoutForm" action="/logout" method="post" style="display: inline;">
                            @if(_csrf != null)
                                <input type="hidden" name="${_csrf.getParameterName()}" value="${_csrf.getToken()}"/>
                            @endif
                            <li><a href="#" onclick="document.getElementById('logoutForm').submit(); return false;">Вийти</a></li>
                        </form>
                    @else
                        <li><a href="/login">Увійти</a></li>
                    @endif
                </ul>
            </nav>
        </div>
    </div>
</header>

@if(showHero)
    <section class="hero">
        <div class="container">
            <h1>Форум ментальної підтримки</h1>
            <p>Безпечний простір для обговорення та підтримки психічного здоров'я.</p>
            <a href="/register" class="btn">Долучитися до спільноти</a>
        </div>
    </section>
@endif

<section class="main-content">
    <div class="container">
        ${content}
    </div>
</section>

<footer>
    <div class="container">
        <div class="footer-content">
            <p>&copy; 2025 Форум "MentalHealthSupport" — Всі права захищені</p>
            <p>Цей сайт не є заміною професійної медичної допомоги.</p>
        </div>
    </div>
</footer>
</body>
</html>