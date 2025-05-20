@param com.shevchenko.mentalHealthSupport.models.User currentUser
@param com.shevchenko.mentalHealthSupport.models.Diary diaryEntry
@param java.time.format.DateTimeFormatter formatter
@param org.springframework.security.web.csrf.CsrfToken _csrf
@param boolean isLoggedIn

@template.layout(
title = "Особистий щоденник",
description = "Ваш особистий щоденник для відстеження емоційного стану",
showHero = false,
content = @`
    @if (currentUser == null || diaryEntry == null)
        <div class="diary-container">
            <h1 class="diary-title">Редагування запису щоденника</h1>
            <p class="diary-description">Внесіть зміни у свій запис та збережіть оновлену версію.</p>

            @if(currentUser == null)
                <div class="auth-prompt">
                    <p>Для використання щоденника необхідно <a href="/login?redirect=/diary">увійти</a> в систему або <a href="/register">зареєструватись</a>.</p>
                </div>
            @else
                <div class="not-found-prompt">
                    <p>Запис не знайдено або у вас немає доступу до цього запису.</p>
                    <a href="/diary" class="btn">Повернутися до щоденника</a>
                </div>
            @endif
        </div>
    @else
        <div class="diary-container">
            <h1 class="diary-title">Редагування запису щоденника</h1>
            <p class="diary-description">Внесіть зміни у свій запис та збережіть оновлену версію.</p>

            <div class="diary-edit-form">
                <form action="/diary/edit/${diaryEntry.getId()}" method="post" class="emotion-form">
                    <input type="hidden" name="${_csrf.getParameterName()}" value="${_csrf.getToken()}"/>
                    <div class="mood-selector">
                        <label>
                            <input type="radio" name="mood" value="great">
                            <span class="mood-emoji">😄</span>
                            <span class="mood-text">Чудово</span>
                        </label>
                        <label>
                            <input type="radio" name="mood" value="good">
                            <span class="mood-emoji">🙂</span>
                            <span class="mood-text">Добре</span>
                        </label>
                        <label>
                            <input type="radio" name="mood" value="neutral">
                            <span class="mood-emoji">😐</span>
                            <span class="mood-text">Нормально</span>
                        </label>
                        <label>
                            <input type="radio" name="mood" value="bad">
                            <span class="mood-emoji">😔</span>
                            <span class="mood-text">Погано</span>
                        </label>
                        <label>
                            <input type="radio" name="mood" value="awful">
                            <span class="mood-emoji">😞</span>
                            <span class="mood-text">Дуже погано</span>
                        </label>
                    </div>

                    <div class="diary-input">
                        <label for="diaryText">Зміст запису:</label>
                        <textarea id="diaryText" name="content" rows="5" placeholder="Що ви відчуваєте? Що сталося сьогодні?">${diaryEntry.getContent()}</textarea>

                        <div class="tags-selector">
                            <label>Виберіть теги:</label>
                            <div class="tags-options">
                                <label><input type="checkbox" name="tags" value="anxiety"> Тривога</label>
                                <label><input type="checkbox" name="tags" value="happiness"> Радість</label>
                                <label><input type="checkbox" name="tags" value="stress"> Стрес</label>
                                <label><input type="checkbox" name="tags" value="relaxation"> Розслаблення</label>
                                <label><input type="checkbox" name="tags" value="anger"> Гнів</label>
                                <label><input type="checkbox" name="tags" value="meditation"> Медитація</label>
                            </div>
                        </div>

                        <div class="privacy-option">
                            <label>
                                <input type="checkbox" name="isPrivate" value="true" checked>
                                Приватний запис (видно тільки вам)
                            </label>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn diary-submit">Зберегти зміни</button>
                            <a href="/diary" class="btn btn-secondary">Скасувати</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    @endif
    `,
isLoggedIn = isLoggedIn,
_csrf = _csrf
)
<style>
    .diary-container {
        background-color: white;
        border-radius: 8px;
        padding: 2rem;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }

    .diary-title {
        color: var(--primary);
        margin-bottom: 1rem;
        font-size: 2rem;
    }

    .diary-description {
        color: #666;
        margin-bottom: 2rem;
    }

    .auth-prompt {
        background-color: var(--light);
        padding: 2rem;
        border-radius: 8px;
        text-align: center;
        margin: 2rem 0;
    }

    .auth-prompt a {
        color: var(--primary);
        font-weight: bold;
        text-decoration: none;
    }

    .diary-emotions {
        margin-bottom: 2rem;
    }

    .mood-selector {
        display: flex;
        justify-content: space-between;
        margin: 1.5rem 0;
        flex-wrap: wrap;
    }

    .mood-selector label {
        display: flex;
        flex-direction: column;
        align-items: center;
        cursor: pointer;
        padding: 1rem;
        border-radius: 8px;
        transition: background-color 0.3s;
    }

    .mood-selector label:hover {
        background-color: #f0f0f0;
    }

    .mood-selector input[type="radio"] {
        display: none;
    }

    .mood-selector input[type="radio"]:checked + .mood-emoji {
        transform: scale(1.2);
    }

    .mood-emoji {
        font-size: 2rem;
        margin-bottom: 0.5rem;
        transition: transform 0.3s;
    }

    .mood-text {
        font-size: 0.9rem;
    }

    .diary-input {
        background-color: var(--light);
        padding: 1.5rem;
        border-radius: 8px;
    }

    .diary-input label {
        display: block;
        margin-bottom: 0.5rem;
        font-weight: bold;
    }

    .diary-input textarea {
        width: 100%;
        padding: 1rem;
        border: 1px solid #ddd;
        border-radius: 4px;
        resize: vertical;
        margin-bottom: 1rem;
        font-size: 1rem;
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

    .privacy-option {
        margin: 1rem 0;
    }

    .diary-submit {
        background-color: var(--primary);
        margin-top: 1rem;
        border: none;
        cursor: pointer;
        font-size: 1rem;
    }

    .diary-submit:hover {
        background-color: var(--secondary);
    }
</style>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        // Перевіряємо, чи була обрана хоча б одна опція настрою
        const moodOptions = document.querySelectorAll('input[name="mood"]');
        let anyChecked = Array.from(moodOptions).some(option => option.checked);

        // Якщо жодна опція не обрана, обираємо "Нормально" за замовчуванням
        if (!anyChecked) {
            const neutralOption = document.querySelector('input[name="mood"][value="neutral"]');
            if (neutralOption) {
                neutralOption.checked = true;
            }
        }
    });
</script>