@param com.shevchenko.mentalHealthSupport.models.User currentUser = null
@param java.util.List<com.shevchenko.mentalHealthSupport.models.Diary> diaryEntries = java.util.Collections.emptyList()
@param java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

@template.layout(
title = "Особистий щоденник",
description = "Ваш особистий щоденник для відстеження емоційного стану",
showHero = false,
content = @`
    <div class="diary-container">
        <h1 class="diary-title">Особистий щоденник</h1>
        <p class="diary-description">Тут ви можете записувати свої думки, почуття та відслідковувати свій емоційний стан.</p>

        @if(currentUser == null)
            <div class="auth-prompt">
                <p>Для використання щоденника необхідно <a href="/login?redirect=/diary">увійти</a> в систему або <a href="/register">зареєструватись</a>.</p>
            </div>
        @else
            <div class="diary-emotions">
                <h2>Як ви почуваєтесь сьогодні?</h2>
                <form action="/diary/add" method="post" class="emotion-form">
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
                        <label for="diaryText">Записати думки та почуття:</label>
                        <textarea id="diaryText" name="content" rows="5" placeholder="Що ви відчуваєте? Що сталося сьогодні?"></textarea>

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

                        <button type="submit" class="btn diary-submit">Зберегти запис</button>
                    </div>
                </form>
            </div>

            <div class="diary-history">
                <h2>Ваші записи</h2>

                <div class="diary-filters">
                    <label>
                        <span>Показати записи за:</span>
                        <select id="timeFilter" onchange="filterEntries()">
                            <option value="all">Усі записи</option>
                            <option value="week">Останній тиждень</option>
                            <option value="month">Останній місяць</option>
                            <option value="year">Останній рік</option>
                        </select>
                    </label>

                    <label>
                        <span>Фільтр по настрою:</span>
                        <select id="moodFilter" onchange="filterEntries()">
                            <option value="all">Усі настрої</option>
                            <option value="great">Чудово</option>
                            <option value="good">Добре</option>
                            <option value="neutral">Нормально</option>
                            <option value="bad">Погано</option>
                            <option value="awful">Дуже погано</option>
                        </select>
                    </label>
                </div>

                <div class="mood-chart">
                    <h3>Динаміка вашого настрою</h3>
                    <div class="chart-container">
                        <!-- Тут буде відображатися графік настрою -->
                        <p class="chart-placeholder">Графік динаміки настрою буде відображено тут</p>
                    </div>
                </div>

                <div class="diary-entries">
                    @if(diaryEntries.isEmpty())
                        <p class="no-entries">У вас ще немає жодного запису в щоденнику. Створіть свій перший запис!</p>
                    @else
                        @for(var entry : diaryEntries)
                            <div class="diary-entry" data-mood="${entry.getMood()}" data-date="${entry.getCreatedAt().format(formatter)}">
                                <div class="entry-header">
                                        <span class="entry-mood">
                                            <span class="entry-mood" data-mood="${entry.getMood()}"></span>
                                        </span>
                                    <span class="entry-date">${entry.getCreatedAt().format(formatter)}</span>
                                    <span class="entry-privacy">${entry.isPrivate() ? "Приватний" : "Публічний"}</span>
                                </div>
                                <div class="entry-content">
                                    <p>${entry.getContent()}</p>
                                </div>
                                @if(!entry.getTags().isEmpty())
                                    <div class="entry-tags">
                                        @for(var tag : entry.getTags())
                                            <span class="tag">${tag}</span>
                                        @endfor
                                    </div>
                                @endif
                                <div class="entry-actions">
                                    <a href="/diary/edit/${entry.getId()}" class="btn btn-small">Редагувати</a>
                                    <a href="/diary/delete/${entry.getId()}" class="btn btn-small btn-danger" onclick="return confirm('Ви впевнені, що хочете видалити цей запис?')">Видалити</a>
                                </div>
                            </div>
                        @endfor
                    @endif
                </div>
            </div>
        @endif
    </div>

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

        .diary-history {
            margin-top: 3rem;
        }

        .diary-filters {
            display: flex;
            justify-content: space-between;
            margin: 1.5rem 0;
            padding: 1rem;
            background-color: var(--light);
            border-radius: 8px;
            flex-wrap: wrap;
        }

        .diary-filters label {
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .diary-filters select {
            padding: 0.5rem;
            border-radius: 4px;
            border: 1px solid #ddd;
        }

        .mood-chart {
            background-color: white;
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
            margin-bottom: 2rem;
        }

        .chart-container {
            height: 200px;
            width: 100%;
            background-color: var(--light);
            border-radius: 4px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-top: 1rem;
        }

        .chart-placeholder {
            color: #999;
            font-style: italic;
        }

        .diary-entries {
            display: grid;
            gap: 1.5rem;
        }

        .no-entries {
            text-align: center;
            padding: 2rem;
            background-color: var(--light);
            border-radius: 8px;
            font-style: italic;
            color: #666;
        }

        .diary-entry {
            border: 1px solid #ddd;
            border-radius: 8px;
            overflow: hidden;
        }

        .entry-header {
            display: flex;
            padding: 1rem;
            background-color: var(--light);
            align-items: center;
            justify-content: space-between;
        }

        .entry-mood {
            font-size: 1.5rem;
        }

        .entry-date {
            font-size: 0.9rem;
            color: #666;
        }

        .entry-privacy {
            font-size: 0.8rem;
            background-color: #ddd;
            padding: 0.2rem 0.5rem;
            border-radius: 4px;
        }

        .entry-content {
            padding: 1.5rem;
            background-color: white;
        }

        .entry-tags {
            padding: 0 1.5rem 1rem;
            display: flex;
            flex-wrap: wrap;
            gap: 0.5rem;
        }

        .tag {
            background-color: var(--secondary);
            color: white;
            padding: 0.2rem 0.6rem;
            border-radius: 20px;
            font-size: 0.8rem;
        }

        .entry-actions {
            padding: 1rem 1.5rem;
            background-color: #f9f9f9;
            display: flex;
            gap: 1rem;
        }

        .btn-small {
            padding: 0.3rem 0.8rem;
            font-size: 0.9rem;
        }

        .btn-danger {
            background-color: var(--danger);
        }

        @media (max-width: 768px) {
            .mood-selector {
                justify-content: center;
                gap: 1rem;
            }

            .diary-filters {
                flex-direction: column;
                gap: 1rem;
            }
        }
    </style>

    <script>
        function filterEntries() {
            const timeFilter = document.getElementById('timeFilter').value;
            const moodFilter = document.getElementById('moodFilter').value;

            const entries = document.querySelectorAll('.diary-entry');

            entries.forEach(entry => {
                const mood = entry.dataset.mood;
                const date = new Date(entry.dataset.date);
                const now = new Date();

                let showByTime = true;
                if (timeFilter === 'week') {
                    const oneWeekAgo = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000);
                    showByTime = date >= oneWeekAgo;
                } else if (timeFilter === 'month') {
                    const oneMonthAgo = new Date(now.getFullYear(), now.getMonth() - 1, now.getDate());
                    showByTime = date >= oneMonthAgo;
                } else if (timeFilter === 'year') {
                    const oneYearAgo = new Date(now.getFullYear() - 1, now.getMonth(), now.getDate());
                    showByTime = date >= oneYearAgo;
                }

                const showByMood = moodFilter === 'all' || mood === moodFilter;

                entry.style.display = (showByTime && showByMood) ? 'block' : 'none';
            });
        }

        // Допоміжні функції для шаблону
        function getMoodEmoji(mood) {
            switch(mood) {
                case "great": return "😄";
                case "good": return "🙂";
                case "neutral": return "😐";
                case "bad": return "😔";
                case "awful": return "😞";
                default: return "😐";
            }
        }

        document.addEventListener("DOMContentLoaded", function() {
            document.querySelectorAll('.entry-mood').forEach(span => {
                const mood = span.dataset.mood;
                span.textContent = getMoodEmoji(mood);
            });
        });

    </script>
`
)