@param String errorMessage = null
@param String successMessage = null
@param String redirectUrl = null

@template.layout(
title = "Змінити пароль",
description = "Зміна паролю облікового запису на форумі ментальної підтримки",
showHero = false,
content = @`
    <div class="auth-container">
        <div class="auth-card">
            <div class="auth-header">
                <h1>Змінити пароль</h1>
                <p class="auth-description">Введіть вашу електронну адресу та новий пароль</p>
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

            <form action="/forgot-password" method="POST" class="auth-form">
                <div class="form-group">
                    <label for="username">Ім'я користувача*</label>
                    <input type="text" id="username" name="username" required placeholder="Введіть ваше ім'я користувача">
                </div>

                <div class="form-group">
                    <label for="password">Пароль*</label>
                    <input type="password" id="password1" name="password" required placeholder="Введіть ваш пароль">
                </div>

                <div class="form-group">
                    <label for="password">Повторіть пароль*</label>
                    <input type="password" id="password2" name="password" required placeholder="Повторіть ваш пароль">
                </div>


                @if(redirectUrl != null)
                    <input type="hidden" name="redirect" value="${redirectUrl}">
                @endif

                <button type="submit" class="btn btn-primary btn-block">Змінити</button>
            </form>


            <div class="auth-footer">
                <p>Ще немає облікового запису? <a href="/register" class="link">Зареєструватися</a></p>
            </div>
        </div>
    </div>
`
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
        max-width: 450px;
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
    input[type="password"] {
        width: 100%;
        padding: 12px 15px;
        border: 1px solid #ddd;
        border-radius: 8px;
        font-size: 15px;
        transition: border-color 0.2s;
    }

    input[type="text"]:focus,
    input[type="email"]:focus,
    input[type="password"]:focus {
        border-color: #4a6fa5;
        outline: none;
        box-shadow: 0 0 0 3px rgba(74, 111, 165, 0.2);
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
        background-color: #4a6fa5;
        color: white;
    }

    .btn-primary:hover {
        background-color: #3c5a84;
    }

    .btn-block {
        display: block;
        width: 100%;
    }

    .auth-links {
        text-align: center;
        margin-top: 15px;
    }

    .auth-footer {
        text-align: center;
        margin-top: 25px;
        padding-top: 20px;
        border-top: 1px solid #eee;
        color: #7f8c8d;
    }

    .link {
        color: #4a6fa5;
        text-decoration: none;
        transition: color 0.2s;
    }

    .link:hover {
        color: #3c5a84;
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
        .btn {
            padding: 10px 12px;
            font-size: 14px;
        }
    }
</style>