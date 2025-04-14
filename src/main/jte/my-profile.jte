@import com.shevchenko.mentalHealthSupport.models.User
@import java.time.LocalDateTime
@import java.time.format.DateTimeFormatter

@param User user
@param String errorMessage = null
@param String successMessage = null
@param DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

@template.layout(
title = "Мій профіль",
description = "Перегляд та редагування вашого профілю на форумі ментальної підтримки",
showHero = false,
content = @`
    <div class="profile-container">
        <div class="profile-header">
            <h1>Мій профіль</h1>
            <p class="subtitle">Керуйте своїми даними та налаштуваннями</p>
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

        <div class="profile-content">
            <div class="profile-sidebar">
                <div class="profile-avatar">
                    @if(user.getAvatar() != null && !user.getAvatar().isEmpty())
                        <img src="${user.getAvatar()}" alt="${user.getUsername()}" class="avatar-img">
                    @else
                        <div class="avatar-placeholder">
                            <span>${user.getUsername().substring(0, 1).toUpperCase()}</span>
                        </div>
                    @endif
                </div>
                <div class="profile-stats">
                    <div class="stat-item">
                        <span class="stat-label">Дата реєстрації:</span>
                        <span class="stat-value">${user.getRegisteredAt().format(formatter)}</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-label">Дописи:</span>
                        <span class="stat-value">${user.getPosts() != null ? user.getPosts().size() : 0}</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-label">Коментарі:</span>
                        <span class="stat-value">${user.getComments() != null ? user.getComments().size() : 0}</span>
                    </div>
                </div>
                <div class="profile-actions">
                    <a href="/my-posts" class="action-btn">Мої дописи</a>
                    <a href="/my-comments" class="action-btn">Мої коментарі</a>
                    <a href="/bookmarks" class="action-btn">Збережені дописи</a>
                </div>
            </div>

            <div class="profile-main">
                <div class="profile-section">
                    <h2>Основна інформація</h2>
                    <form action="/profile/update" method="POST" class="profile-form">
                        <div class="form-group">
                            <label for="username">Ім'я користувача*</label>
                            <input type="text" id="username" name="username" value="${user.getUsername()}" required>
                        </div>

                        <div class="form-group">
                            <label for="email">Електронна пошта*</label>
                            <input type="email" id="email" name="email" value="${user.getEmail()}" required>
                        </div>

                        <div class="form-group">
                            <label for="avatar">Посилання на аватар</label>
                            <input type="url" id="avatar" name="avatar" value="${user.getAvatar() != null ? user.getAvatar() : ""}">
                            <p class="form-hint">Посилання на зображення, яке буде вашим аватаром</p>
                        </div>

                        <div class="form-group">
                            <label for="bio">Про себе</label>
                            <textarea id="bio" name="bio" rows="4">${user.getBio() != null ? user.getBio() : ""}</textarea>
                            <p class="form-hint">Розкажіть коротко про себе</p>
                        </div>

                        <button type="submit" class="btn btn-primary">Оновити профіль</button>
                    </form>
                </div>

                <div class="profile-section">
                    <h2>Зміна пароля</h2>
                    <form action="/profile/change-password" method="POST" class="profile-form">
                        <div class="form-group">
                            <label for="currentPassword">Поточний пароль*</label>
                            <input type="password" id="currentPassword" name="currentPassword" required>
                        </div>

                        <div class="form-group">
                            <label for="newPassword">Новий пароль*</label>
                            <input type="password" id="newPassword" name="newPassword" required>
                            <p class="form-hint">Мінімум 8 символів, включаючи літери та цифри</p>
                        </div>

                        <div class="form-group">
                            <label for="confirmNewPassword">Підтвердження нового паролю*</label>
                            <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>
                        </div>

                        <button type="submit" class="btn btn-primary">Змінити пароль</button>
                    </form>
                </div>

                <div class="profile-section danger-zone">
                    <h2>Небезпечна зона</h2>
                    <p>Дії в цій секції мають незворотні наслідки. Будьте обережні!</p>
                    <form action="/profile/delete" method="POST" class="profile-form" onsubmit="return confirm('Ви впевнені, що хочете видалити свій профіль? Ця дія незворотна.');">
                        <button type="submit" class="btn btn-danger">Видалити профіль</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
`
)

<style>
    .profile-container {
        max-width: 1000px;
        margin: 0 auto;
        padding: 30px 0;
    }

    .profile-header {
        text-align: center;
        margin-bottom: 30px;
    }

    .profile-header h1 {
        font-size: 32px;
        color: var(--dark);
        margin-bottom: 10px;
    }

    .subtitle {
        color: #7f8c8d;
        font-size: 16px;
    }

    .profile-content {
        display: flex;
        gap: 30px;
    }

    .profile-sidebar {
        flex: 1;
        max-width: 300px;
    }

    .profile-main {
        flex: 2;
    }

    .profile-avatar {
        text-align: center;
        margin-bottom: 20px;
    }

    .avatar-img {
        width: 150px;
        height: 150px;
        border-radius: 50%;
        object-fit: cover;
        border: 4px solid #fff;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    }

    .avatar-placeholder {
        width: 150px;
        height: 150px;
        border-radius: 50%;
        background-color: var(--primary);
        color: white;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 64px;
        font-weight: bold;
        margin: 0 auto;
        border: 4px solid #fff;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    }

    .profile-stats {
        background-color: #fff;
        border-radius: 10px;
        padding: 15px;
        margin-bottom: 20px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
    }

    .stat-item {
        display: flex;
        justify-content: space-between;
        padding: 8px 0;
        border-bottom: 1px solid #eee;
    }

    .stat-item:last-child {
        border-bottom: none;
    }

    .stat-label {
        color: #7f8c8d;
    }

    .stat-value {
        font-weight: 500;
        color: var(--dark);
    }

    .profile-actions {
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .action-btn {
        display: block;
        background-color: #f5f8fc;
        color: var(--primary);
        text-align: center;
        padding: 12px 10px;
        border-radius: 6px;
        text-decoration: none;
        font-weight: 500;
        transition: all 0.2s;
    }

    .action-btn:hover {
        background-color: var(--primary);
        color: white;
    }

    .profile-section {
        background-color: #fff;
        border-radius: 10px;
        padding: 25px;
        margin-bottom: 25px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
    }

    .profile-section h2 {
        font-size: 20px;
        margin-bottom: 20px;
        color: var(--dark);
        border-bottom: 1px solid #eee;
        padding-bottom: 10px;
    }

    .profile-form {
        display: flex;
        flex-direction: column;
        gap: 15px;
    }

    .form-group {
        margin-bottom: 15px;
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
        border-color: var(--primary);
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
        background-color: var(--primary);
        color: white;
    }

    .btn-primary:hover {
        background-color: #3c5a84;
    }

    .btn-danger {
        background-color: #e74c3c;
        color: white;
    }

    .btn-danger:hover {
        background-color: #c0392b;
    }

    .danger-zone {
        border: 1px solid #e74c3c;
    }

    .danger-zone h2 {
        color: #e74c3c;
    }

    .danger-zone p {
        margin-bottom: 15px;
        color: #7f8c8d;
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

    /* Респонсивність для планшетів */
    @media (max-width: 992px) {
        .profile-content {
            flex-direction: column;
        }

        .profile-sidebar {
            max-width: 100%;
            margin-bottom: 30px;
        }

        .profile-avatar {
            margin-bottom: 20px;
        }

        .profile-actions {
            flex-direction: row;
            flex-wrap: wrap;
        }

        .action-btn {
            flex: 1;
            min-width: 150px;
        }
    }

    /* Респонсивність для мобільних пристроїв */
    @media (max-width: 576px) {
        .profile-container {
            padding: 20px 15px;
        }

        .profile-header h1 {
            font-size: 24px;
        }

        .profile-section {
            padding: 15px;
        }

        .profile-actions {
            flex-direction: column;
        }

        .action-btn {
            width: 100%;
        }

        .avatar-img,
        .avatar-placeholder {
            width: 120px;
            height: 120px;
        }

        .avatar-placeholder {
            font-size: 48px;
        }
    }
</style>