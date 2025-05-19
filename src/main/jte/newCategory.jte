@param org.springframework.security.web.csrf.CsrfToken _csrf
@param boolean isLoggedIn

@template.layout(
title = "Додати категорію",
description = "Додавання категорії",
showHero = false,
content = @`
    <div class="auth-container">
        <div class="auth-card">
            <form action="/category/new" method="POST" class="auth-form">
                <input type="hidden" name="${_csrf.getParameterName()}" value="${_csrf.getToken()}"/>
                <div class="form-group">
                    <label for="title">Введіть назву категорії</label>
                    <input type="text" id="title" name="title" required placeholder="Введіть назву">
                </div>
                <div class="form-group">
                    <label for="content">Опис</label>
                    <textarea id="content" name="content" rows="3" placeholder="Напишіть опис категорії"></textarea>
                </div>
                <div class="form-group">
                    <label for="image">Посилання на фото</label>
                    <textarea id="image" name="image" rows="1" placeholder="Додайте посилання на фото"></textarea>
                </div>
                <button type="submit" class="btn btn-primary btn-block">Додати</button>
            </form>
        </div>
    </div>
`,
isLoggedIn = isLoggedIn
)

<style>
    .auth-container {
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 40px 20px;
        min-height: 70vh;
    }

    .auth-card {
        background-color: #fff;
        border-radius: 12px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        width: 100%;
        max-width: 520px;
        padding: 30px;
    }

    .auth-header h1 {
        margin-bottom: 10px;
        color: #2c3e50;
        font-size: 28px;
    }


    .auth-form {
        margin-bottom: 20px;
    }

    .form-group {
        margin-bottom: 20px;
    }

    .form-group label {
        display: block;
        margin-bottom: 6px;
        font-weight: 500;
        color: #34495e;
    }

    input[type="text"],
    input[type="email"],
    input[type="password"],
    input[type="url"],
    textarea {
        width: 100%;
        padding: 12px 15px;
        border: 1px solid #ddd;
        border-radius: 8px;
        font-size: 15px;
        transition: border-color 0.2s;
    }

    input[type="text"]:focus,
    input[type="email"]:focus,
    input[type="password"]:focus,
    input[type="url"]:focus,
    textarea:focus {
        border-color: #3498db;
        outline: none;
        box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.2);
    }

    .form-checkbox {
        display: flex;
        align-items: flex-start;
        gap: 10px;
    }

    .form-checkbox input[type="checkbox"] {
        margin-top: 4px;
    }

    .form-checkbox label {
        font-weight: normal;
        margin-bottom: 0;
    }
    .btn {
        display: inline-block;
        padding: 12px 20px;
        border: none;
        border-radius: 8px;
        font-size: 16px;
        font-weight: 500;
        cursor: pointer;
        text-align: center;
        transition: all 0.2s;
    }

    .btn-primary {
        background-color: #3498db;
        color: white;
    }

    .btn-primary:hover {
        background-color: #2980b9;
    }

    .btn-block {
        display: block;
        width: 100%;
    }

    /* Респонсивність для мобільних пристроїв */
    @media (max-width: 576px) {
        .auth-card {
            padding: 20px;
        }

        .auth-header h1 {
            font-size: 24px;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"],
        input[type="url"],
        textarea,
        .btn {
            padding: 10px 12px;
            font-size: 14px;
        }
    }
    .tags-selector {
        margin: 1rem 0;
    }

    .tags-options {
        display: flex;
        flex-wrap: wrap;
        gap: 0.8rem;
        margin-top: 0.5rem;
    }

    .tags-options label {
        font-weight: normal;
        display: flex;
        align-items: center;
        gap: 0.3rem;
    }
</style>