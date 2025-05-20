@import org.springframework.security.web.csrf.CsrfToken
@import java.util.List
@import java.util.Set

@param String errorMessage = null
@param String successMessage = null
@param org.springframework.security.web.csrf.CsrfToken _csrf

@template.layout(
title = "Долучитись до спільноти",
description = "Створіть свій аккаунт на форумі ментальної підтримки",
showHero = false,
content = @`
    <div class="auth-container">
        <div class="auth-card">
            <div class="auth-header">
                <h1>Долучитись до спільноти</h1>
                <p class="auth-description">Приєднуйтесь до нашої спільноти взаємодопомоги та підтримки</p>
            </div>

            @if(errorMessage != null)
                <div class="alert alert-error">
                    <i class="icon-error"></i>
                    <span>${errorMessage}</span>
                </div>
            @endif

            @if(successMessage != null)
                <div class="alert alert-success">
                    <i class="icon-success"></i>
                    <span>${successMessage}</span>
                </div>
            @endif

            <form action="/register" method="POST" class="auth-form">
                @if(_csrf != null)
                    <input type="hidden" name="${_csrf.getParameterName()}" value="${_csrf.getToken()}"/>
                @endif
                <div class="form-group">
                    <label for="username">Ім'я користувача*</label>
                    <input type="text" id="username" name="username" required placeholder="Введіть ваше ім'я користувача">
                </div>

                <div class="form-group">
                    <label for="email">Електронна пошта*</label>
                    <input type="email" id="email" name="email" required placeholder="Введіть вашу електронну пошту">
                </div>

                <div class="form-group">
                    <label for="password">Пароль*</label>
                    <input type="password" id="password" name="password" required placeholder="Створіть надійний пароль">
                    <p class="form-hint">Мінімум 8 символів</p>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Підтвердження паролю*</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required placeholder="Повторіть пароль">
                </div>

                <div class="form-group">
                    <label for="avatar">Посилання на аватар</label>
                    <input type="url" id="avatar" name="avatar" placeholder="https://example.com/your-avatar.jpg">
                    <p class="form-hint">Посилання на зображення, яке буде вашим аватаром</p>
                </div>

                <div class="form-group">
                    <label for="firstName">Ім'я</label>
                    <textarea id="firstName" name="firstName" rows="1" placeholder="Вкажіть ваше ім'я"></textarea>
                </div>

                <div class="form-group">
                    <label for="lastName">Прізвище</label>
                    <textarea id="lastName" name="lastName" rows="1" placeholder="Вкажіть ваше прізвище"></textarea>
                </div>

                <div class="form-group">
                    <label for="age">Вік</label>
                    <input type="number" id="age" name="age" min="0" placeholder="Вкажіть ваш вік">
                </div>

                <div class="form-group">
                    <label for="gender">Стать</label>
                    <select id="gender" name="gender" required>
                        <option value="">Оберіть стать</option>
                        <option value="male">Чоловік</option>
                        <option value="female">Жінка</option>
                        <option value="none">Не хочу вказувати</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="bio">Про себе</label>
                    <textarea id="bio" name="bio" rows="3" placeholder="Розкажіть коротко про себе..."></textarea>
                </div>

                <div class="form-group form-checkbox">
                    <input type="checkbox" id="termsAgree" name="termsAgree" required>
                    <label for="termsAgree">Я погоджуюсь з <a href="/terms" class="link">умовами користування</a> та <a href="/privacy" class="link">політикою конфіденційності</a></label>
                </div>

                <button type="submit" class="btn btn-primary btn-block">Зареєструватись</button>
            </form>

            <div class="auth-footer">
                <p>Вже є акаунт? <a href="/login" class="link">Увійти</a></p>
            </div>
        </div>
    </div>
`,
isLoggedIn = false,
_csrf = _csrf
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

    .auth-header {
        text-align: center;
        margin-bottom: 30px;
    }

    .auth-header h1 {
        margin-bottom: 10px;
        color: #2c3e50;
        font-size: 28px;
    }

    .auth-description {
        color: #7f8c8d;
        font-size: 16px;
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

    .form-hint {
        font-size: 12px;
        color: #7f8c8d;
        margin-top: 4px;
        margin-bottom: 0;
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

    .auth-footer {
        text-align: center;
        margin-top: 25px;
        padding-top: 20px;
        border-top: 1px solid #eee;
        color: #7f8c8d;
    }

    .link {
        color: #3498db;
        text-decoration: none;
        transition: color 0.2s;
    }

    .link:hover {
        color: #2980b9;
        text-decoration: underline;
    }

    .alert {
        padding: 12px 15px;
        border-radius: 8px;
        margin-bottom: 20px;
        display: flex;
        align-items: center;
        gap: 10px;
    }

    .alert-error {
        background-color: #fee2e2;
        color: #b91c1c;
        border-left: 4px solid #ef4444;
    }

    .alert-success {
        background-color: #dcfce7;
        color: #166534;
        border-left: 4px solid #22c55e;
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
</style>