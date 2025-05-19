@import com.shevchenko.mentalHealthSupport.models.Category
@import com.shevchenko.mentalHealthSupport.models.Post
@import org.springframework.security.web.csrf.CsrfToken
@import java.util.List
@import java.time.format.DateTimeFormatter

<%-- Відображення даних (a) та іменовані параметри (f) --%>
@param List<Category> categories
@param List<Post> recentPosts
@param String userName = null
@param boolean isLoggedIn

<%-- Коментар: Це головна сторінка форуму ментальної підтримки (d) --%>



@template.layout(
title = "Головна сторінка",
description = "Форум ментальної підтримки - спільнота взаємодопомоги",
showHero = true,
content = @`

    @if(userName != null) <%-- If statement (b) --%>
        <div class="welcome-message">
            <h2>Вітаємо, ${userName}!</h2>
            <p>Раді бачити вас знову на нашому форумі.</p>
        </div>
    @endif

    <div class="intro">
        <h2>Наші категорії</h2>
        <p>На форумі наразі доступно ${categories.size()} категорій</p>
        <p>Хочеш побачити всі категорії? <a href="/categories" class="link">Переглянути</a></p>
    </div>

    <div class="categories-wrapper">
        <div class="categories">
            @for(Category category : categories)
                @if(categories.indexOf(category) < 3)
                    @template.categoryTemplate(Url = category.getImageUrl(), Name = category.getName(), Description = category.getDescription(),  DiscussionCount = category.getDiscussionCount(), Categoryid = category.getCategoryid())
                @endif
            @endfor
        </div>
    </div>

    <div class="recent-posts">
        <h2>Останні обговорення</h2>

        @if(recentPosts.isEmpty())
            <p class="no-posts">Ще немає публікацій. Будьте першим, хто розпочне обговорення!</p>
        @else
            <div class="post-list">
                @for(Post post : recentPosts)
                    @if(recentPosts.indexOf(post) < 5)
                        <div class="post-card">
                                <div class="post-header">
                                    <a href="/post/${post.getPostid()}" class="post-title">${post.getTitle()}</a>
                                    <span class="post-date">${post.getFormattedCreatedAt()}</span>
                                </div>
                                <p class="post-excerpt">${post.getContent()}</p>
                                <div class="post-footer">
                                    <div class="post-author">
                                        <span>Автор: ${post.getUser().getUsername()}</span>
                                    </div>
                                    <div class="post-stats">
                                        <span class="reply-count">0 відповідей</span>
                                    </div>
                                </div>
                            </div>
                    @endif
                @endfor
            </div>
        @endif
    </div>
`,
isLoggedIn = isLoggedIn
)

<style>



    .recent-posts {
        margin-top: 40px;
        padding: 0 10px;
    }

    .recent-posts h2 {
        margin-bottom: 20px;
        font-size: 24px;
        color: #333;
        border-bottom: 2px solid #3498db;
        padding-bottom: 10px;
    }

    .no-posts {
        text-align: center;
        padding: 30px;
        background: #f9f9f9;
        border-radius: 8px;
        color: #7f8c8d;
        font-style: italic;
    }

    .post-list {
        display: grid;
        gap: 20px;
    }

    .post-card {
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
        padding: 20px;
        transition: transform 0.2s ease, box-shadow 0.2s ease;
    }

    .post-card:hover {
        transform: translateY(-3px);
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.15);
    }

    .post-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 12px;
    }

    .post-title {
        font-size: 18px;
        font-weight: 600;
        color: #2c3e50;
        text-decoration: none;
        transition: color 0.2s ease;
    }

    .post-title:hover {
        color: #3498db;
    }

    .post-date {
        font-size: 14px;
        color: #7f8c8d;
    }

    .post-excerpt {
        color: #34495e;
        margin-bottom: 15px;
        line-height: 1.5;
    }

    .post-footer {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding-top: 12px;
        border-top: 1px solid #ecf0f1;
    }

    .post-author {
        display: flex;
        align-items: center;
        gap: 5px;
        color: #7f8c8d;
        font-size: 14px;
    }

    .post-stats {
        font-size: 14px;
        color: #7f8c8d;
    }

    .reply-count {
        background-color: #e8f4fc;
        padding: 3px 8px;
        border-radius: 30px;
        color: #3498db;
        font-weight: 500;
    }

    .post-tags {
        display: flex;
        flex-wrap: wrap;
        gap: 6px;
        margin-top: 12px;
    }

    .tag {
        background-color: #f0f4f7;
        color: #5d6d7e;
        padding: 3px 10px;
        border-radius: 15px;
        font-size: 12px;
    }

    /* Додавання респонсивності */
    @media (min-width: 768px) {
        .post-list {
            grid-template-columns: repeat(2, 1fr);
        }
    }

    @media (min-width: 1200px) {
        .post-list {
            grid-template-columns: repeat(3, 1fr);
        }
    }
</style>