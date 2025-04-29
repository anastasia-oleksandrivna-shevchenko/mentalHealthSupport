@param Long category

@template.layout(
title = "Додати пост",
description = "Додавання поста",
showHero = false,
content = @`
    <div class="auth-container">
        <div class="auth-card">
            <form action="/category/${category}/new" method="POST" class="auth-form">
                <div class="form-group">
                    <label for="title">Введіть назву поста</label>
                    <input type="text" id="title" name="title" required placeholder="Введіть назву">
                </div>
                <div class="form-group">
                    <label for="content">Контент</label>
                    <textarea id="content" name="content" rows="3" placeholder="Поділіться думками..."></textarea>
                </div>
                <div class="form-group form-checkbox">
                    <input type="checkbox" id="isAnonymous" name="isAnonymous">
                    <label for="isAnonymous">Анонімно</label>
                </div>
                <div class="tags-selector">
                    <label>Виберіть теги:</label>
                    <div class="tags-options">
                        <label><input type="checkbox" name="tags" value="mental health"> Ментальне здоров'я</label>
                        <label><input type="checkbox" name="tags" value="depression"> Депресія</label>
                        <label><input type="checkbox" name="tags" value="anxiety"> Занепокоєння</label>
                        <label><input type="checkbox" name="tags" value="self-care"> Самодопомога</label>
                        <label><input type="checkbox" name="tags" value="wellness"> Оздоровлення</label>
                        <label><input type="checkbox" name="tags" value="stress"> Стрес</label>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary btn-block">Додати</button>
            </form>
        </div>
    </div>
`)

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